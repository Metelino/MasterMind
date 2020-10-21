package sample;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Arrays;
import java.util.Random;
import javafx.scene.input.MouseEvent;


public class Controller {
    @FXML
    private BorderPane menu;
    @FXML
    private GridPane siatka;
    @FXML
    private GridPane check;
    @FXML
    private Button start;
    @FXML
    private Button size1;
    @FXML
    private Button size2;
    @FXML
    private Button size3;

    private final Color[] colors = {Color.RED, Color.BLUE, Color.ORANGE, Color.GREEN, Color.YELLOW, Color.PURPLE};
    private volatile Integer[] WinColors = new Integer[4];
    private volatile Integer[] CurColors = new Integer[4];
    private int maxRow;
    private int curRow;

    public void initialize(){
        Arrays.fill(CurColors, -1);
    }

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
                       flaga = false;
                }
                if(flaga)
                    break;
            }
        }
        System.out.printf("%d:%d:%d:%d", WinColors[0], WinColors[1], WinColors[2], WinColors[3]);
    }

    public void colorCheck(){
        for(int i = 0; i < 4; i++)
        {
            int red_count = 0;
            boolean red = false;
            boolean white = false;
            for(int j = 0; j < 4; j++){
                if(CurColors[i] == WinColors[j]) {
                    white = true;
                    if (i == j) {
                        red = true;
                        red_count++;
                    }
                }
            if(red){
                for(Node item : siatka.getChildren()) {
                    if(GridPane.getColumnIndex(item) == 0 && GridPane.getRowIndex(item) == curRow){
                        ((Circle)((GridPane)item).getChildren().get(i)).setFill(Color.RED);
                    }
                }
            }
            else if(white)
                for(Node item : siatka.getChildren()) {
                    if(GridPane.getColumnIndex(item) == 0 && GridPane.getRowIndex(item) == curRow){
                        ((Circle)((GridPane)item).getChildren().get(i)).setFill(Color.WHITE);
                    }
                }
            }
        }
    }

    public void hideMenu(){
        size1.setVisible(false);
        size1.setManaged(false);
        size2.setVisible(false);
        size2.setManaged(false);
        size3.setVisible(false);
        size3.setManaged(false);
        menu.getTop().setVisible(false);
        menu.getTop().setManaged(false);
    }

    public void chooseSize() {
        start.setVisible(false);
        start.setManaged(false);
        size1.setVisible(true);
        size1.setManaged(true);
        size2.setVisible(true);
        size2.setManaged(true);
        size3.setVisible(true);
        size3.setManaged(true);
    }

    public void setSize1(){
        hideMenu();
        maxRow = 8;
        curRow = maxRow-1;
        setWinColors();
        drawGame();
    }

    public void setSize2(){
        hideMenu();
        maxRow = 10;
        curRow = maxRow-1;
        setWinColors();
        drawGame();
    }

    public void setSize3() {
        hideMenu();
        maxRow = 12;
        curRow = maxRow-1;
        setWinColors();
        drawGame();
    }

    public void setGameFieldhandler(){
        for(Node item : siatka.getChildren())
        {
            if(GridPane.getColumnIndex(item) != 0 && GridPane.getRowIndex(item) == curRow)
            {
                item.setOnMouseClicked((MouseEvent e) -> {
                    int col = GridPane.getColumnIndex(item) - 1;
                    Object obj = e.getSource();
                    if(obj instanceof Circle) {
                        if(CurColors[col]+1 >= colors.length)
                            CurColors[col] = -1;
                        ((Circle) item).setFill(colors[++CurColors[col]]);
                        System.out.printf("%d%d%d%d\n", CurColors[0], CurColors[1], CurColors[2], CurColors[3]);
                    }
                });
            }
        }
    }

    public void disableGameFieldhandler(){
        for(Node item : siatka.getChildren()){
            if(GridPane.getColumnIndex(item) != 0 && GridPane.getRowIndex(item) == curRow + 1) {
                item.setMouseTransparent(true);
            }
        }
    }

    public void drawGame(){
        for(int j = 0; j < maxRow; j++) {

            GridPane checkbox = new GridPane();
            checkbox.setHgap(3);
            checkbox.setVgap(3);
            for(int i = 0; i < 2; i++)
                for(int k = 0; k < 2; k++) {
                    Circle check_field = new Circle();
                    check_field.setRadius(10);
                    check_field.setFill(Color.GREY);
                    check_field.setStroke(Color.BLACK);
                    check_field.setStrokeWidth(3);
                    checkbox.getChildren().add(check_field);
                    GridPane.setColumnIndex(check_field, i);
                    GridPane.setRowIndex(check_field, k);
                }

            siatka.getChildren().add(checkbox);
            GridPane.setRowIndex(checkbox, j);
            GridPane.setColumnIndex(checkbox, 0);
            GridPane.setHalignment(checkbox, HPos.CENTER);
            GridPane.setValignment(checkbox, VPos.CENTER);

            for (int i = 0; i < 4; i++) {

                Circle input_field = new Circle();
                input_field.setRadius(35);
                input_field.setFill(Color.GREY);
                input_field.setStroke(Color.BLACK);
                input_field.setStrokeWidth(3);

                siatka.getChildren().add(input_field);
                GridPane.setColumnIndex(input_field, i+1);
                GridPane.setRowIndex(input_field, j);
                GridPane.setHalignment(input_field, HPos.CENTER);
                GridPane.setValignment(input_field, VPos.CENTER);
            }
        }

        Button submit = new Button();
        submit.setText("Submit");
        GridPane.setRowIndex(submit, maxRow -1);
        GridPane.setColumnIndex(submit, 6);
        submit.setOnMousePressed((MouseEvent e) -> {
            //System.out.println("dziala");
            colorCheck();
            GridPane.setRowIndex(submit, --curRow);
            disableGameFieldhandler();
            setGameFieldhandler();
            for(Node item : siatka.getChildren()){
                if(GridPane.getColumnIndex(item) != 0 && GridPane.getColumnIndex(item) != 6 &&
                        GridPane.getRowIndex(item) == curRow) {
                    int col = GridPane.getColumnIndex(item)-1;
                    ((Circle)item).setFill(colors[CurColors[col]]);
                }
            }
        });
        siatka.getChildren().add(submit);
        setGameFieldhandler();
    }
}
