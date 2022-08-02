package org.openjfx;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;

import java.io.IOException;

public class StartController {
    private final String metal = getClass().getResource("metal.css").toExternalForm();
    private final String wood = getClass().getResource("wood.css").toExternalForm();
    private final Double InitHeight = 900.0;
    private final Double InitWidth = 850.0;
    private final DoubleProperty WidthScale = new SimpleDoubleProperty(1.0);
    private final DoubleProperty HeightScale = new SimpleDoubleProperty(1.0);
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
    ToggleGroup Styl;
    @FXML
    Region Shutter1;
    @FXML
    Region Shutter2;

    @FXML
    public void initialize(){
        Scale skala = new Scale();
        //Shutter1.scaleXProperty().bind(Base.widthProperty().divide(InitWidth));
        //Shutter1.scaleYProperty().bind(Base.heightProperty().divide(InitHeight));
        Shutter1.maxHeightProperty().bind(Base.heightProperty().divide(2));
        Shutter2.maxHeightProperty().bind(Base.heightProperty().divide(2));
//        Shutter1.translateYProperty().bind(Base.heightProperty().divide(2));
//        Shutter2.translateYProperty().bind(Base.heightProperty().divide(-2));
        Shutter1.translateYProperty().bind(Shutter1.maxHeightProperty());
        Shutter2.translateYProperty().bind(Shutter2.maxHeightProperty().multiply(-1));
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
        SceneTransition.makeTransition(StartScreen, "ScenaConfig.fxml");
    }
}
