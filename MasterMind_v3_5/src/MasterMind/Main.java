package MasterMind;

import MasterMind.AppStart;
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
        primaryStage.setScene(new Scene(root, 850, 900));
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(500);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

//public class Main{
//    public static void main(String[] args) {
//        AppStart.main(args);
//    }
//}
