package MasterMind;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class SceneTransition {
    static public void  makeTransition(Node currentScreen, String nextScene) throws IOException {
        Parent root = FXMLLoader.load(SceneTransition.class.getResource(nextScene));
        Scene GameScene = currentScreen.getScene();
//        root.translateXProperty().set(GameScene.getWidth());
        AnchorPane Base = (AnchorPane) GameScene.getRoot();
        StackPane SceneStack = (StackPane)Base.lookup("#SceneStack");
//        SceneStack.getChildren().add(root);
        Region Shutter1 = (Region)Base.lookup("#Shutter1");
        Region Shutter2 = (Region)Base.lookup("#Shutter2");

//        Timeline cogRot = new Timeline();
//        KeyValue kv = new KeyValue(Cog.rotateProperty(), Cog.getRotate() - 360, Interpolator.LINEAR);
//        KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
//        cogRot.getKeyFrames().add(kf);

        //Zamykanie ekranu
        Timeline anim_close = new Timeline();

        KeyValue kv = new KeyValue(Shutter1.translateYProperty(), 0, Interpolator.EASE_OUT);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.25), kv);
        anim_close.getKeyFrames().add(kf);

        kv = new KeyValue(Shutter2.translateYProperty(), 0, Interpolator.EASE_OUT);
        kf = new KeyFrame(Duration.seconds(0.25), kv);
        anim_close.getKeyFrames().add(kf);

        //Otwieranie ekranu
        Timeline anim_open = new Timeline();

        kv = new KeyValue(Shutter1.translateYProperty(), Shutter1.getMaxHeight(), Interpolator.EASE_OUT);
        kf = new KeyFrame(Duration.seconds(0.5), kv);
        anim_open.getKeyFrames().add(kf);

        kv = new KeyValue(Shutter2.translateYProperty(), -Shutter2.getMaxHeight(), Interpolator.EASE_IN);
        kf = new KeyFrame(Duration.seconds(0.5), kv);
        anim_open.getKeyFrames().add(kf);

        File file = new File("src/sounds/metal_glass.wav");
        Media errorSound = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(errorSound);
        anim_close.setOnFinished(e -> {
            SceneStack.getChildren().remove(currentScreen);
            SceneStack.getChildren().add(root);
            mediaPlayer.play();
            anim_open.play();
        });

        anim_open.setOnFinished(e -> {
            Shutter1.translateYProperty().bind(Shutter1.maxHeightProperty());
            Shutter2.translateYProperty().bind(Shutter2.maxHeightProperty().multiply(-1));
        });

        Shutter1.translateYProperty().unbind();
        Shutter2.translateYProperty().unbind();
        anim_close.play();
    }
}
