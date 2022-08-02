package org.openjfx;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;

import java.io.IOException;

public class WinLoseController extends GameInfo {

    @FXML
    VBox WinLoseScreen;
    @FXML
    Button NewGame;
    @FXML
    Label WinTekst;

    public void initialize(){
        if(Win)
            WinTekst.setText("Congratulations! You Won!");
        else
            WinTekst.setText("Better luck next time. Try Again!");
        Win = false;
    }

    @FXML
    public void transitionToConfig() throws IOException {
        SceneTransition.makeTransition(WinLoseScreen, "ScenaConfig.fxml");
    }
}
