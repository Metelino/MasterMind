package MasterMind.ScenaWinLose;

import MasterMind.GameInfo;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
        AnchorPane Base = (AnchorPane) winLoseScene.getRoot();
        StackPane SceneStack = (StackPane)Base.lookup("#SceneStack");
        //root.scaleXProperty().bind(SceneStack.widthProperty().divide(750));
        //root.scaleYProperty().bind(SceneStack.heightProperty().divide(750));
        SceneStack.getChildren().add(root);
        Node Cog = Base.lookup("#Cog");

        Timeline cogRot = new Timeline();
        KeyValue kv = new KeyValue(Cog.rotateProperty(), Cog.getRotate() - 360, Interpolator.LINEAR);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
        cogRot.getKeyFrames().add(kf);

        Timeline trans = new Timeline();
        kv = new KeyValue(WinLoseScreen.translateYProperty(), -WinLoseScreen.getHeight(), Interpolator.EASE_OUT);
        kf = new KeyFrame(Duration.seconds(0.5), kv);
        trans.getKeyFrames().add(kf);

        kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_OUT);
        kf = new KeyFrame(Duration.seconds(0.5), kv);
        trans.getKeyFrames().add(kf);
        trans.setOnFinished(e -> {
            SceneStack.getChildren().remove(WinLoseScreen);
        });
        cogRot.play();
        trans.play();
    }
}
