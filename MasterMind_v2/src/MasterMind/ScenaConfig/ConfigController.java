package MasterMind.ScenaConfig;

import MasterMind.GameInfo;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;

public class ConfigController extends GameInfo{

    @FXML
    Button Gra;
    @FXML
    ToggleGroup Wiersze;
    @FXML
    ToggleGroup Kolumny;
    @FXML
    VBox ConfigScreen;

    public void initialize(){
        Rows = 8;
        Kolumny.selectedToggleProperty().addListener((e) -> {
            if(Kolumny.getSelectedToggle() == Kolumny.getToggles().get(0))
                Columns = 3;
            else
                Columns = 4;
            System.out.println(Columns);
        });
        Wiersze.selectedToggleProperty().addListener((e) -> {
            if (Wiersze.getSelectedToggle() == Wiersze.getToggles().get(0))
                Rows = 6;
            else if (Wiersze.getSelectedToggle() == Wiersze.getToggles().get(1))
                Rows = 8;
            else
                Rows = 10;
            System.out.println(Rows);
        });
    }

    @FXML
    public void transitionToGame() throws IOException {
        //System.out.println("dziala");
        Parent root = FXMLLoader.load(getClass().getResource("/MasterMind/ScenaGra/ScenaGra.fxml"));
        Scene ConfigScene = Gra.getScene();
        StackPane Base = (StackPane)ConfigScene.getRoot();
        root.translateXProperty().set(ConfigScene.getWidth());
        Base.getChildren().add(root);
        root.toFront();

        Timeline trans = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_OUT);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        trans.getKeyFrames().add(kf);
        trans.setOnFinished(e -> {
            Base.getChildren().remove(ConfigScreen);
        });
        trans.play();
    }
}
