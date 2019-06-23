package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CreateLabWorkMain extends Application {

    public void start(Stage primaryStage)
    {
        try
        {
            AnchorPane root = FXMLLoader.load(getClass().getResource("CreateLabWork.fxml"));
            primaryStage.setTitle("Create LabWork");
            primaryStage.setScene(new Scene(root,400,375));
            primaryStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String args[])
    {
        launch(args);
    }
}
