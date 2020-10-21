package MasterMind.ScenaStart;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.Buffer;

public class StartController {
    @FXML
    StackPane Base;
    @FXML
    VBox StartScreen;
    @FXML
    Button Start;
    @FXML
    public void transitionToConfig() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/MasterMind/ScenaConfig/ScenaConfig.fxml"));
        Scene StartScene = Start.getScene();
        root.translateYProperty().set(StartScene.getHeight());
        Base.getChildren().add(root);

        Timeline trans = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_OUT);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
        trans.getKeyFrames().add(kf);
        trans.setOnFinished(e -> {
            Base.getChildren().remove(StartScreen);
        });
        trans.play();
    }
}
