package MasterMind;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ScenaStart/ScenaStart.fxml"));
        primaryStage.setTitle("MasterMind");
        primaryStage.setScene(new Scene(root, 750, 900));
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(400);
        //primaryStage.setResizable(false);
        //Scene scena = new Scene(root);
        //primaryStage.setScene(scena);
        //primaryStage.minWidthProperty().bind(scena.heightProperty().divide(100/75));
        //primaryStage.minHeightProperty().bind(scena.widthProperty().multiply(100/75));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
