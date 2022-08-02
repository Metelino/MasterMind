package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ConfigController extends GameInfo {
    @FXML
    Button Gra;
    @FXML
    ToggleGroup Wiersze;
    @FXML
    ToggleGroup Kolumny;
    @FXML
    CheckBox RepeatingCheck;
    @FXML
    VBox ConfigScreen;

    public void initialize(){
        Rows = 8;
        Columns = 4;
        ColorsLen = 8;
        Kolumny.selectedToggleProperty().addListener((e) -> {
            if(Kolumny.getSelectedToggle() == Kolumny.getToggles().get(0)) {
                Columns = 3;
                ColorsLen = 6;
            }
            else if (Kolumny.getSelectedToggle() == Kolumny.getToggles().get(1)){
                Columns = 4;
                ColorsLen = 8;
            }
            else{
                Columns = 5;
                ColorsLen = 10;
            }
            System.out.println(Columns);
        });
        Wiersze.selectedToggleProperty().addListener((e) -> {
            if (Wiersze.getSelectedToggle() == Wiersze.getToggles().get(0))
                Rows = 6;
            else if (Wiersze.getSelectedToggle() == Wiersze.getToggles().get(1))
                Rows = 8;
            else
                Rows = 10;
            //System.out.println(Rows);
        });
        RepeatingCheck.selectedProperty().addListener((e) -> {
            if (RepeatingCheck.isSelected())
                Repeating = true;
            else
                Repeating = false;
            System.out.println(Repeating);
        });

    }


    @FXML
    public void transitionToGame() throws IOException {
        SceneTransition.makeTransition(ConfigScreen, "ScenaGra.fxml");
    }
}
