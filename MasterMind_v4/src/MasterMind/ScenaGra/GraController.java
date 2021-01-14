package MasterMind.ScenaGra;

import MasterMind.GameInfo;

import MasterMind.SceneTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

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
//    @FXML
//    Pane Piston;
//    @FXML
//    Label Turns;

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
        if (Repeating)
            for(int i = 0; i < Columns; i++)
                WinColors[i] = rand.nextInt(ColorsLen);
        else
            for(int i = 0; i < Columns; i++) {
                while(true) {
                    boolean flaga = true;
                    WinColors[i] = rand.nextInt(ColorsLen);
                    if(i == 0)
                        break;
                    for(int j = 0; j < i; j++)
                        if(WinColors[i] == WinColors[j])
                        {
                            flaga = false;
                            break;
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

//    public boolean WinCheck(){
//        ArrayList<Integer> order = new ArrayList<>(Columns);
//        for(int i = 0; i < Columns; i++)
//            order.add(i);
//        Collections.shuffle(order);
//
//        TilePane CurrentCheckField = null;
//        for(Node item : GameGrid.getChildren()) {
//            if(GridPane.getColumnIndex(item) == 0 && GridPane.getRowIndex(item) == CurRow){
//                CurrentCheckField = (TilePane)item;
//            }
//        }
//
//        int RedCount = 0;
//        Boolean[] ColorsChecked = {false, false, false, false};
//        for(int i = 0; i < Columns; i++)
//        {
//            boolean red = false;
//            boolean white = false;
//            for(int j = 0; j < Columns; j++){
//                if((CurColors[CurRow][i] == WinColors[j]) && (!ColorsChecked[j])) {
//                    ColorsChecked[j] = true;
//                    white = true;
//                    if (i == j) {
//                        red = true;
//                        RedCount++;
//                    }
//                }
//                if(red){
//                    ((Circle)CurrentCheckField.getChildren().get(order.get(i))).setFill(Color.RED);
//                    break;
//                }
//                else if(white){
//                    ((Circle)CurrentCheckField.getChildren().get(order.get(i))).setFill(Color.WHITE);
//                    break;
//                }
//            }
//        }
//        return (RedCount == Columns);
//    }

    public boolean WinCheck(){
        int RedCount = 0;
        Boolean[] Win = {false, false, false, false};
        Boolean[] Current = {false, false, false, false};
        Integer[] Colors = {0,0,0,0};

        //Sprawdzanie czerwonych
        for(int i = 0; i < Columns; i++)
            if((CurColors[CurRow][i] == WinColors[i])) {
                Win[i] = true;
                Current[i] = true;
                Colors[i] = 1;
            }

        //Sprawdzanie białych
        for(int i = 0; i < Columns; i++)
            for(int j = 0; j < Columns; j++) {
                if (!(Win[j] || Current[i]))
                    if ((CurColors[CurRow][i] == WinColors[j])) {
                        Win[j] = true;
                        Current[i] = true;
                        Colors[i] = 2;
                        break;
                    }
            }

        prepCheckField(Colors);
        for(int i = 0; i < Columns; i++)
            if(Colors[i] != 1)
                return false;
        return true;
    }

    private void prepCheckField(Integer[] Colors){
        ArrayList<Integer> order = new ArrayList<>(Columns);
        for(int i = 0; i < Columns; i++)
            order.add(i);
        Collections.shuffle(order);

        TilePane CurrentCheckField = null;
        for(Node item : GameGrid.getChildren())
            if(GridPane.getColumnIndex(item) == 0 && GridPane.getRowIndex(item) == CurRow)
                CurrentCheckField = (TilePane)item;

        for(int i = 0; i < Columns; i++)
            if(Colors[i] == 1)
                ((Circle)CurrentCheckField.getChildren().get(order.get(i))).setFill(Color.RED);
            else if(Colors[i] == 2)
                ((Circle)CurrentCheckField.getChildren().get(order.get(i))).setFill(Color.WHITE);
    }

    public void addGameFieldhandler(Circle item, int col, int row){
        File file = new File("src/sounds/punch.wav");
        Media errorSound = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(errorSound);
        item.setOnMouseClicked((MouseEvent e) -> {
            ChosenGameField = item;
            ChosenCol = col;
            ChosenRow = row;

            Point2D Zaczep = new Point2D(item.getCenterX(), item.getCenterY());
            //System.out.println(Zaczep);
            Zaczep = item.localToScene(Zaczep);
            //System.out.println(Zaczep);
            Zaczep = Palette.sceneToLocal(Zaczep);
            Zaczep = Palette.localToParent(Zaczep);
            //System.out.println(Zaczep);
            Palette.setTranslateX(Zaczep.getX() - GameScreen.getScene().getWidth()/2 + Palette.getWidth()/2);
            Palette.setTranslateY(Zaczep.getY() - GameScreen.getScene().getHeight()/2 - Palette.getHeight()/2);
            Palette.setVisible(true);
            Palette.setMouseTransparent(false);

            if(!Repeating)
                for(int i = 0; i < Columns; i++)
                    if(CurColors[row][i] != -1) {
                        Palette.getChildren().get(CurColors[row][i]).setMouseTransparent(true);
                        Palette.getChildren().get(CurColors[row][i]).setVisible(false);
                    }
            mediaPlayer.play();
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
        File fileError = new File("src/sounds/error.wav");
        Media errorSound = new Media(fileError.toURI().toString());
        MediaPlayer errorPlayer = new MediaPlayer(errorSound);
        boolean IsValid = true;
        for(int i : CurColors[CurRow]){
            if(i == -1) {
                IsValid = false;
                errorPlayer.play();
                break;
            }
        }
        if(IsValid) {
            disableClicking();
            Win = WinCheck();
            if(Win || CurRow-1 < 0)
                transitionToWinLose();
            else{
                TilePane CurrentCheckField = null;
                for(Node item : GameGrid.getChildren()) {
                    if(GridPane.getColumnIndex(item) == 0 && GridPane.getRowIndex(item) == CurRow){
                        CurrentCheckField = (TilePane)item;
                    }
                }
                CurrentCheckField.setVisible(true);
                Timeline in = new Timeline();
                KeyValue kv = new KeyValue(CurrentCheckField.translateXProperty(), 0, Interpolator.EASE_OUT);
                KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
                in.getKeyFrames().add(kf);

                File fileSuccess = new File("src/sounds/success_1.mp3");
                Media successSound = new Media(fileSuccess.toURI().toString());
                MediaPlayer successPlayer = new MediaPlayer(successSound);
                in.setOnFinished(e -> {
                    successPlayer.play();
                });
                in.play();
            }
            CurRow--;
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
        for(int i=0; i < Rows; i++) {
            //Pole sprawdzające
            TilePane Checkbox = new TilePane();
            //Checkbox.setMinSize(100, 50);
            //Checkbox.setTranslateX(GameScreen.getParent().sceneToLocal(0,0).getX());
            Checkbox.setVgap(2);
            Checkbox.setHgap(2);
            if (Columns != 5)
                Checkbox.setPrefColumns(2);
            else
                Checkbox.setPrefColumns(3);

            Checkbox.getStyleClass().add("CheckBox");
            Checkbox.translateXProperty().set(-350);
            Checkbox.setVisible(false);
            Checkbox.setAlignment(Pos.CENTER);
            Checkbox.getStyleClass().add("checkbox");

            for (int j = 0; j < Columns; j++) {
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
            for (int j = 0; j < Columns; j++) {
                Circle PoleGry = new Circle();
                addGameFieldhandler(PoleGry, j, i);
                PoleGry.setRadius(25);
                //PoleGry.scaleXProperty().bind(GameScreen.widthProperty().divide(750));
                //PoleGry.scaleYProperty().bind(GameScreen.heightProperty().divide(900));
                PoleGry.getStyleClass().add("gameField");
                Row.getChildren().add(PoleGry);
            }

            Label Number = new Label();
            Number.getStyleClass().add("number");
            Number.setText(Integer.toString(Rows-i));

            //Dodanie pól do siatki
            GameGrid.getChildren().add(Row);
            GridPane.setColumnIndex(Row, 1);
            GridPane.setRowIndex(Row, i);
            GameGrid.getChildren().add(Checkbox);
            GridPane.setColumnIndex(Checkbox, 0);
            GridPane.setRowIndex(Checkbox, i);
            GameGrid.getChildren().add(Number);
            GridPane.setColumnIndex(Number, 2);
            GridPane.setRowIndex(Number, i);
        }
    }

    @FXML
    public void transitionToWinLose() throws IOException {
        SceneTransition.makeTransition(GameStack, "/MasterMind/ScenaWinLose/ScenaWinLose.fxml");
    }
}
