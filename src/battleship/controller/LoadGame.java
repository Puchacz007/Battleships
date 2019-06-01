package battleship.controller;

import battleship.gameObjects.GameSave;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoadGame {

    public ListView<String> listView;
    File[] listOfFiles;

    public void initialize() {
        showSavedGames();
    }

    public void showSavedGames() {
        File folder = new File("./src/battleship/controller/view/resources/saves/");
        listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
                listView.getItems().add(listOfFiles[i].getName());
            }
        }
    }

    public void load(ActionEvent actionEvent) throws IOException, ClassNotFoundException {

        ObservableList<Integer> index = listView.getSelectionModel().getSelectedIndices();

        FileInputStream fileIn = new FileInputStream("./src/battleship/controller/view/resources/saves/" + listOfFiles[index.get(0)].getName());
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        GameSave gameSave = (GameSave) objectIn.readObject();
        System.out.println("The Object  was succesfully read");
        objectIn.close();
        startLoadedGame(gameSave, actionEvent);
    }

    public void startLoadedGame(GameSave gameSave, ActionEvent actionEvent) throws IOException {

        FXMLLoader saveLoader = new FXMLLoader(getClass().getResource("view/main_game.fxml"));

        Parent saveGameLoader = saveLoader.load();
        MainGameController mainGameController = saveLoader.getController();
        mainGameController.transferLoadGame(gameSave);
        Scene newGameScene = new Scene(saveGameLoader);
        Stage newGameStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        newGameStage.setScene(newGameScene);
        newGameStage.show();
        newGameStage.setMinHeight(640);
        newGameStage.setMinWidth(1020);
    }

    public void ret(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/main.fxml"));
        Scene primaryScene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
}
