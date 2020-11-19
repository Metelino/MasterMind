package MasterMind.ScenaStart;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class StartController {
    private final String metal = getClass().getResource("/MasterMind/metal.css").toExternalForm();
    private final String wood = getClass().getResource("/MasterMind/wood.css").toExternalForm();
    private final Double InitHeight = 900.0;
    private final Double InitWidth = 750.0;
    private DoubleProperty WidthScale = new SimpleDoubleProperty(1.0);
    private DoubleProperty HeightScale = new SimpleDoubleProperty(1.0);
    @FXML
    StackPane Base;
    @FXML
    AnchorPane BaseAnchor;
    @FXML
    StackPane SceneStack;
    @FXML
    VBox StartScreen;
    @FXML
    Button Start;
    @FXML
    Pane Cog;
    @FXML
    ToggleGroup Styl;
    @FXML
    ToggleGroup Resolution;

    @FXML
    public void initialize(){
        Scale skala = new Scale();
        Cog.scaleXProperty().bind(Base.widthProperty().divide(InitWidth));
        Cog.scaleYProperty().bind(Base.heightProperty().divide(InitHeight));
//        SceneStack.scaleXProperty().bind(Base.widthProperty().divide(750));
//        SceneStack.scaleYProperty().bind(Base.heightProperty().divide(900));
        WidthScale.bind(BaseAnchor.widthProperty().divide(InitWidth));
        HeightScale.bind(BaseAnchor.heightProperty().divide(InitHeight));

//        ChangeListener<Number> StageSizeListener = (observable, oldValue, newValue) ->{
//            skala.setPivotX(SceneStack.getWidth()/2);
//            skala.setPivotY(SceneStack.getHeight()/2);
//            skala.setX(Base.getWidth() / InitWidth);
//            skala.setY(Base.getHeight() / InitHeight);
//            //SceneStack.getTransforms().setAll(skala);
//            //SceneStack.getTransforms().setAll(skala);
//            SceneStack.getChildren().forEach((Node e) -> {e.getTransforms().setAll(skala);});
//        };
//        SceneStack.widthProperty().addListener(StageSizeListener);
//        SceneStack.heightProperty().addListener(StageSizeListener);

        SceneStack.getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(Change<? extends Node> change) {
                while (change.next())
                    for (Node added : change.getAddedSubList()) {
                        added.scaleXProperty().bind(WidthScale);
                        added.scaleYProperty().bind(HeightScale);
                    }

            }
            }
        );


//        Resolution.selectedToggleProperty().addListener((e) -> {
//            if(Resolution.getSelectedToggle() == Resolution.getToggles().get(0)) {
//                Base.getScene().getWindow().setHeight(900);
//                Base.getScene().getWindow().setWidth(750);
//                System.out.println("maly");
//            }
//            else{
//                Base.getScene().getWindow().setHeight(1100);
//                Base.getScene().getWindow().setWidth(900);
//                System.out.println("duzy");
//            }
//        });

        Styl.selectedToggleProperty().addListener((e) -> {
            if(Styl.getSelectedToggle() == Styl.getToggles().get(0)) {
                BaseAnchor.getStylesheets().remove(wood);
                BaseAnchor.getStylesheets().add(metal);
            }
            else{
                BaseAnchor.getStylesheets().remove(metal);
                BaseAnchor.getStylesheets().add(wood);
            }
        });

    }

    @FXML
    public void transitionToConfig() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/MasterMind/ScenaConfig/ScenaConfig.fxml"));
        Scene StartScene = Start.getScene();
        //Stage ekran = (Stage)StartScene.getWindow();
        root.translateYProperty().set(StartScene.getHeight());
        SceneStack.getChildren().add(root);

        Timeline cogRot = new Timeline();
        KeyValue kv = new KeyValue(Cog.rotateProperty(), -360, Interpolator.LINEAR);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
        cogRot.getKeyFrames().add(kf);


        Timeline trans = new Timeline();
        kv = new KeyValue(StartScreen.translateYProperty(), -StartScene.getHeight(), Interpolator.EASE_OUT);
        kf = new KeyFrame(Duration.seconds(0.5), kv);
        trans.getKeyFrames().add(kf);

        kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_OUT);
        kf = new KeyFrame(Duration.seconds(0.5), kv);
        trans.getKeyFrames().add(kf);
        trans.setOnFinished(e -> {
            SceneStack.getChildren().remove(StartScreen);
        });
        trans.play();
        cogRot.play();
    }
}
