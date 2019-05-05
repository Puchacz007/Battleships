package battleship.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void exit() {
        System.exit(0);
    }

    public void newGame(ActionEvent actionEvent) throws IOException {
        Parent gameSettingsParent = FXMLLoader.load(getClass().getResource("view/game_settings.fxml"));
        Scene  gameSettingsScene = new Scene(gameSettingsParent);
        Stage gameSettingStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        gameSettingStage.setScene(gameSettingsScene);
        gameSettingStage.show();

    }

    public void loadGame(ActionEvent actionEvent)throws IOException {
        Parent loadGameParent = FXMLLoader.load(getClass().getResource("view/load_game.fxml"));
        Scene  loadGameScene = new Scene(loadGameParent);
        Stage loadGameStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        loadGameStage.setScene(loadGameScene);
        loadGameStage.show();
    }
}
