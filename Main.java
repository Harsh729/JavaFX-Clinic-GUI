package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        MainWindowController obj=new MainWindowController();
        obj.onStart();
        VBox vBox = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        primaryStage.setTitle("Clinic Software");
        primaryStage.setScene(new Scene(vBox, 800, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
