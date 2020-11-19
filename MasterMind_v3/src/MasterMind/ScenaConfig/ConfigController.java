package MasterMind.ScenaConfig;

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
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
    }

    @FXML
    public void transitionToGame() throws IOException {
        //System.out.println("dziala");
        Parent root = FXMLLoader.load(getClass().getResource("/MasterMind/ScenaGra/ScenaGra.fxml"));
        Scene ConfigScene = Gra.getScene();
        AnchorPane Base = (AnchorPane) ConfigScene.getRoot();
        StackPane SceneStack = (StackPane)Base.lookup("#SceneStack");
        root.translateXProperty().set(ConfigScene.getWidth());
        //root.scaleXProperty().bind(SceneStack.widthProperty().divide(750));
        //root.scaleYProperty().bind(SceneStack.heightProperty().divide(900));
        SceneStack.getChildren().add(root);
        Node Cog = Base.lookup("#Cog");
        //CogAnchor.toFront();

        //Node Cog = ((Pane)CogAnchor).getChildren().get(0);
        Timeline cogRot = new Timeline();
        KeyValue kv = new KeyValue(Cog.rotateProperty(), Cog.getRotate() - 360, Interpolator.EASE_OUT);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
        cogRot.getKeyFrames().add(kf);

        Timeline trans = new Timeline();
        kv = new KeyValue(ConfigScreen.translateXProperty(), -ConfigScene.getWidth(), Interpolator.EASE_OUT);
        kf = new KeyFrame(Duration.seconds(0.5), kv);
        trans.getKeyFrames().add(kf);

        kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_OUT);
        kf = new KeyFrame(Duration.seconds(0.5), kv);
        trans.getKeyFrames().add(kf);
        trans.setOnFinished(e -> {
            SceneStack.getChildren().remove(ConfigScreen);
        });
        cogRot.play();
        trans.play();
    }
}
