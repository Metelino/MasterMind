package MasterMind.ScenaGra;

import MasterMind.GameInfo;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;

public class GraController extends GameInfo {
    @FXML
    GridPane GameGrid;
    @FXML
    TilePane Palette;
    //@FXML
    //Pane PaletteAnchor;
    @FXML
    VBox GameScreen;
    @FXML
    StackPane GameStack;

    private int CurRow;
    private final ArrayList<Color> COLORS = new ArrayList<>(Arrays.asList(Color.RED, Color.BLUE, Color.ORANGE, Color.GREEN,
            Color.YELLOW, Color.PURPLE, Color.WHITE, Color.CYAN, Color.BROWN, Color.DARKKHAKI));
    private final Integer[] WinColors = new Integer[5];
    private volatile Integer[][] CurColors;
    private Circle ChosenGameField;
    private Integer ChosenRow;
    private Integer ChosenCol;

    public void setWinColors(){
        Random rand = new Random();
        for(int i = 0; i < Columns; i++) {
            while(true) {
                boolean flaga = true;
                //WinColors[i] = rand.nextInt(colors.length);
                WinColors[i] = rand.nextInt(ColorsLen);
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
        //System.out.printf("%d:%d:%d:%d\n", WinColors[0], WinColors[1], WinColors[2], WinColors[3]);
        for(Integer i : WinColors)
            System.out.printf("%d:", i);
        System.out.print("\n");
    }

    public boolean WinCheck(){
        ArrayList<Integer> order = new ArrayList<>(Columns);
        for(int i = 0; i < Columns; i++)
            order.add(i);
        Collections.shuffle(order);

        int RedCount = 0;

        TilePane CurrentCheckField = null;
        for(Node item : GameGrid.getChildren()) {
            if(GridPane.getColumnIndex(item) == 0 && GridPane.getRowIndex(item) == CurRow){
                CurrentCheckField = (TilePane)item;
            }
        }

        for(int i = 0; i < Columns; i++)
        {
            boolean red = false;
            boolean white = false;
            for(int j = 0; j < Columns; j++){
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
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
        trans.getKeyFrames().add(kf);
        trans.play();
        return (RedCount == Columns);
    }

    public void addGameFieldhandler(Circle item, int col, int row){
        item.setOnMouseClicked((MouseEvent e) -> {
            ChosenGameField = item;
            ChosenCol = col;
            ChosenRow = row;
//            Point2D Zaczep = item.localToScene(item.getCenterX(), item.getCenterY());
//            Palette.setTranslateX(Zaczep.getX() - GameScreen.getScene().getWidth()/2 + Palette.getWidth()/2);
//            Palette.setTranslateY(Zaczep.getY() - GameScreen.getScene().getHeight()/2 - Palette.getHeight()/2);
            Point2D Zaczep = item.localToScene(item.getCenterX(), item.getCenterY());
            Zaczep = GameStack.sceneToLocal(Zaczep);
            Palette.setTranslateX(Zaczep.getX() - GameScreen.getScene().getWidth()/2 + Palette.getWidth()/2);
            Palette.setTranslateY(Zaczep.getY() - GameScreen.getScene().getHeight()/2 - Palette.getHeight()/2);
//            Palette.setTranslateX(Zaczep.getX() - GameScreen.getScene().getWidth()/2 + Palette.getWidth()/2);
//            Palette.setTranslateY(Zaczep.getY() - GameScreen.getScene().getHeight()/2 - Palette.getHeight()/2);

//            Palette.setTranslateX(item.getTranslateX() + Palette.getWidth()/2);
//            Palette.setTranslateY(item.getTranslateY() - Palette.getHeight()/2);


            //PaletteAnchor.setMouseTransparent(false);
            Palette.setVisible(true);
            Palette.setMouseTransparent(false);

            for(int i = 0; i < Columns; i++)
                if(CurColors[row][i] != -1) {
                    Palette.getChildren().get(CurColors[row][i]).setMouseTransparent(true);
                    Palette.getChildren().get(CurColors[row][i]).setVisible(false);
                }
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
        CurColors = new Integer[Rows][Columns];
        for(Integer[] arr : CurColors)
            Arrays.fill(arr, -1);
        setWinColors();

        //Wypełenienie palety kolorami
        if(ColorsLen == 6)
            Palette.setPrefColumns(3);
        else if(ColorsLen == 8)
            Palette.setPrefColumns(4);
        else
            Palette.setPrefColumns(5);
        for(int i = 0; i < ColorsLen; i++){
            Rectangle ColorField = new Rectangle();
            ColorField.setFill(COLORS.get(i));
            ColorField.setWidth(25);
            ColorField.setHeight(25);
            ColorField.setStroke(Color.BLACK);
            ColorField.setStrokeWidth(1);
            ColorField.setOnMouseClicked((e) -> {
                ChosenGameField.setFill(ColorField.getFill());
                //PaletteAnchor.setMouseTransparent(true);
                Palette.setVisible(false);
                Palette.setMouseTransparent(true);
                Palette.getChildren().forEach((item) -> {
                    item.setMouseTransparent(false);
                    item.setVisible(true);
                });
                CurColors[ChosenRow][ChosenCol] = COLORS.indexOf(ColorField.getFill());
                for(Integer color : CurColors[ChosenRow])
                    System.out.printf("%d:", color);
                System.out.print("\r");
            });
            Palette.getChildren().add(ColorField);
        }

        //Scena gry
        for(int i=0; i < Rows; i++)
        {
            //Pole sprawdzające
            TilePane Checkbox = new TilePane();
            //Checkbox.setMinSize(100, 50);
            //Checkbox.setTranslateX(GameScreen.getParent().sceneToLocal(0,0).getX());
            Checkbox.setVgap(2);
            Checkbox.setHgap(2);
            if(Columns != 5)
                Checkbox.setPrefColumns(2);
            else
                Checkbox.setPrefColumns(3);
            Checkbox.getStyleClass().add("CheckBox");
            Checkbox.translateXProperty().set(-350);
            Checkbox.setVisible(false);
            Checkbox.setAlignment(Pos.CENTER);
            Checkbox.getStyleClass().add("checkbox");

            for(int j = 0; j < Columns; j++)
            {
                Circle PoleCheck = new Circle();
                PoleCheck.setRadius(8);
                PoleCheck.getStyleClass().add("checkField");
                Checkbox.getChildren().add(PoleCheck);
            }

            //Pole gry
            HBox Row = new HBox();
            Row.setAlignment(Pos.CENTER);
            Row.getStyleClass().add("GameBox");
            Row.setSpacing(5);
            for(int j = 0; j < Columns; j++)
            {
                Circle PoleGry = new Circle();
                addGameFieldhandler(PoleGry, j, i);
                PoleGry.setRadius(20);
                //PoleGry.scaleXProperty().bind(GameScreen.widthProperty().divide(750));
                //PoleGry.scaleYProperty().bind(GameScreen.heightProperty().divide(900));
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
            //Checkbox.translateXProperty().set(Checkbox.screenToLocal(0, 0).getX());
            //System.out.println(Row.sceneToLocal(Row.getLayoutX(), Row.getLayoutY()));
        }
    }

    public void transitionToWinLose() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/MasterMind/ScenaWinLose/ScenaWinLose.fxml"));
        Scene GameScene = GameScreen.getScene();
        root.translateXProperty().set(GameScene.getWidth());
        AnchorPane Base = (AnchorPane) GameScene.getRoot();
        StackPane SceneStack = (StackPane)Base.lookup("#SceneStack");
        SceneStack.getChildren().add(root);
        Node Cog = Base.lookup("#Cog");


        Timeline cogRot = new Timeline();
        KeyValue kv = new KeyValue(Cog.rotateProperty(), Cog.getRotate() - 360, Interpolator.LINEAR);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
        cogRot.getKeyFrames().add(kf);

        Timeline trans = new Timeline();
        kv = new KeyValue(GameStack.translateXProperty(), -GameStack.getWidth(), Interpolator.EASE_OUT);
        kf = new KeyFrame(Duration.seconds(0.5), kv);
        trans.getKeyFrames().add(kf);

        kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_OUT);
        kf = new KeyFrame(Duration.seconds(0.5), kv);
        trans.getKeyFrames().add(kf);
        trans.setOnFinished(e -> SceneStack.getChildren().remove(GameStack));
        cogRot.play();
        trans.play();
    }
}
