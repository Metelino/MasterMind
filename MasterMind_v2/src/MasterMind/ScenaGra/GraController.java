package MasterMind.ScenaGra;

import MasterMind.GameInfo;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;

public class GraController extends GameInfo {
    @FXML
    GridPane GameGrid;
    @FXML
    VBox GameScreen;
    private int CurRow;
    private final Color[] colors = {Color.RED, Color.BLUE, Color.ORANGE, Color.GREEN, Color.YELLOW, Color.PURPLE};
    private volatile Integer[] WinColors = new Integer[4];
    private volatile Integer[][] CurColors;

    public void setWinColors(){
        Random rand = new Random();
        for(int i = 0; i < 4; i++) {
            while(true) {
                boolean flaga = true;
                WinColors[i] = rand.nextInt(colors.length);
                if(i == 0)
                    break;
                for(int j = 0; j < i; j++)
                {
                    if(WinColors[i] == WinColors[j])
                    {
                        flaga = false;
                        break;
                    }
                }
                if(flaga)
                    break;
            }
        }
        System.out.printf("%d:%d:%d:%d\n", WinColors[0], WinColors[1], WinColors[2], WinColors[3]);
    }

    public boolean WinCheck(){
        ArrayList<Integer> order = new ArrayList<>(4);
        for(int i = 0; i < 4; i++)
            order.add(i);
        Collections.shuffle(order);

        int RedCount = 0;
        TilePane CurrentCheckField = null;
        for(Node item : GameGrid.getChildren()) {
            if(GridPane.getColumnIndex(item) == 0 && GridPane.getRowIndex(item) == CurRow){
                CurrentCheckField = (TilePane)item;
            }
        }
        for(int i = 0; i < 4; i++)
        {
            boolean red = false;
            boolean white = false;
            for(int j = 0; j < 4; j++){
                if(CurColors[CurRow][i] == WinColors[j]) {
                    white = true;
                    if (i == j) {
                        red = true;
                        RedCount++;
                    }
                }
                if(red){
                    ((Circle)CurrentCheckField.getChildren().get(order.get(i))).setFill(Color.RED);
                }
                else if(white)
                    ((Circle)CurrentCheckField.getChildren().get(order.get(i))).setFill(Color.WHITE);
            }
        }
        CurrentCheckField.setVisible(true);
        Timeline trans = new Timeline();
        KeyValue kv = new KeyValue(CurrentCheckField.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.25), kv);
        trans.getKeyFrames().add(kf);
        trans.play();
        return (RedCount == 4);
    }

    public void addGameFieldhandler(Circle item, int col, int row){
        item.setOnMouseClicked((MouseEvent e) -> {
            if(++CurColors[row][col] >= colors.length)
                CurColors[row][col] = 0;
            while(true){
                boolean isValid = true;
                for(int i = 0; i < 4; i++)
                {
                    if(col != i)
                        if(CurColors[row][col] == CurColors[row][i]) {
                            if(++CurColors[row][col] >= colors.length)
                                CurColors[row][col] = 0;
                            isValid = false;
                    }
                }
                if(isValid)
                    break;
            }
            item.setFill(colors[CurColors[row][col]]);
            System.out.printf("%d:%d:%d:%d\r", CurColors[row][0], CurColors[row][1], CurColors[row][2], CurColors[row][3]);
        });
    }

    public void disableClicking(){
        for(Node item : GameGrid.getChildren()){
            if(GridPane.getColumnIndex(item) == 1 && GridPane.getRowIndex(item) == CurRow) {
                item.setMouseTransparent(true);
            }
        }
    }

    @FXML
    public void checkButton() throws IOException{
            boolean IsValid = true;
            for(int i : CurColors[CurRow]){
                if(i == -1) {
                    IsValid = false;
                    break;
                }
            }
            if(IsValid) {
                disableClicking();
                Win = WinCheck();
                if(Win || --CurRow < 0)
                    transitionToWinLose();
            }
    }

    public void initialize(){
        CurRow = Rows - 1;
        CurColors = new Integer[Rows][4];
        for(Integer[] arr : CurColors)
            Arrays.fill(arr, -1);
        setWinColors();
        //Scena gry
        for(int i=0; i < Rows; i++)
        {
            //Pole sprawdzające
            TilePane Checkbox = new TilePane();
            Checkbox.setPrefColumns(2);
            Checkbox.getStyleClass().add("CheckBox");
            Checkbox.translateXProperty().set(-300);
            Checkbox.setVisible(false);
            Checkbox.setAlignment(Pos.CENTER);
            Checkbox.getStyleClass().add("checkbox");
            for(int j=0; j<2; j++)
                for(int k = 0; k < 2; k++)
                {
                    Circle PoleCheck = new Circle();
                    PoleCheck.setRadius(10);
                    PoleCheck.getStyleClass().add("checkField");
                    Checkbox.getChildren().add(PoleCheck);
                }

            //Pole gry
            HBox Row = new HBox();
            Row.getStyleClass().add("GameBox");
            Row.setSpacing(5);
            for(int j=0; j < 4; j++)
            {
                Circle PoleGry = new Circle();
                addGameFieldhandler(PoleGry, j, i);
                PoleGry.setRadius(25);
                PoleGry.getStyleClass().add("gameField");
                Row.getChildren().add(PoleGry);
            }

            //Dodanie pól do siatki
            GameGrid.getChildren().add(Row);
            GridPane.setColumnIndex(Row, 1);
            GridPane.setRowIndex(Row, i);
            GameGrid.getChildren().add(Checkbox);
            GridPane.setColumnIndex(Checkbox, 0);
            GridPane.setRowIndex(Checkbox, i);
        }
    }

    public void transitionToWinLose() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/MasterMind/ScenaWinLose/ScenaWinLose.fxml"));
        Scene winLoseScene = GameScreen.getScene();
        root.translateXProperty().set(winLoseScene.getWidth());
        StackPane Base = (StackPane) winLoseScene.getRoot();
        Base.getChildren().add(root);

        Timeline trans = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_OUT);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
        trans.getKeyFrames().add(kf);
        trans.setOnFinished(e -> Base.getChildren().remove(GameScreen));
        trans.play();
    }
}
