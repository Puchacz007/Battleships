package battleship.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.BasicConfigurator;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BasicConfigurator.configure();

        Parent root = FXMLLoader.load(getClass().getResource("view/main.fxml"));
        primaryStage.setTitle("Battleships Warfare");
        primaryStage.setScene(new Scene(root,300,300));

        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMaxHeight(primaryStage.getHeight());
        primaryStage.setMaxWidth((primaryStage.getWidth()));
    }


    public static void main(String[] args) {
        launch(args);
    }


}
