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
        gameSettingStage.setMinHeight(440);
        gameSettingStage.setMinWidth(415);
        gameSettingStage.setMaxWidth(415);
        gameSettingStage.setMaxHeight(440);
        gameSettingStage.setHeight(440);
        gameSettingStage.setWidth(415);
    }

    public void loadGame(ActionEvent actionEvent)throws IOException {
        Parent loadGameParent = FXMLLoader.load(getClass().getResource("view/load_game.fxml"));
        Scene  loadGameScene = new Scene(loadGameParent);
        Stage loadGameStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        loadGameStage.setScene(loadGameScene);
        loadGameStage.setMinHeight(340);
        loadGameStage.setMinWidth(315);
        loadGameStage.setMaxWidth(315);
        loadGameStage.setMaxHeight(340);
        loadGameStage.setHeight(340);
        loadGameStage.setWidth(315);
        loadGameStage.show();
    }

    public void highScores(ActionEvent actionEvent) throws IOException {
        Parent loadGameParent = FXMLLoader.load(getClass().getResource("view/high_scores.fxml"));
        Scene highScoresScene = new Scene(loadGameParent);
        Stage highScoresStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        highScoresStage.setScene(highScoresScene);
        highScoresStage.setMinHeight(340);
        highScoresStage.setMinWidth(315);
        highScoresStage.setMaxWidth(315);
        highScoresStage.setMaxHeight(340);
        highScoresStage.setHeight(340);
        highScoresStage.setWidth(315);
        highScoresStage.show();
    }
}
