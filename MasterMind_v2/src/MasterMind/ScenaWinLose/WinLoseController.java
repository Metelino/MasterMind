package MasterMind.ScenaWinLose;

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
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

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
        Parent root = FXMLLoader.load(getClass().getResource("/MasterMind/ScenaConfig/ScenaConfig.fxml"));
        Scene winLoseScene = WinLoseScreen.getScene();
        root.translateYProperty().set(winLoseScene.getHeight());
        StackPane Base = (StackPane) winLoseScene.getRoot();
        Base.getChildren().add(root);

        Timeline trans = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_OUT);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
        trans.getKeyFrames().add(kf);
        trans.setOnFinished(e -> {
            Base.getChildren().remove(WinLoseScreen);
        });
        trans.play();
    }
}
