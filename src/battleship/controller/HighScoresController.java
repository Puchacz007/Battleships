package battleship.controller;

import battleship.gameObjects.HighScores;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HighScoresController {

    private static final int HIGHSCOREGRIDCOLUMNS = 6;
    private static final int HIGHSCOREGRIDSROWS = 11;
    public GridPane highScoresTable;

    public void initialize() {
        showHighScores();

    }

    public void ret(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/main.fxml"));
        Scene primaryScene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    private void showHighScores() {
        HighScores highScores = new HighScores();
        String[] lines = highScores.getLines();
        for (int j = 1; j < HIGHSCOREGRIDSROWS; j++) {
            if (lines[j - 1] == null) break;
            String[] stats = lines[j - 1].split(" ");
            Label label = (Label) highScoresTable.getChildren().get(j * HIGHSCOREGRIDCOLUMNS + 1);
            label.setText(stats[0]);// write nickname
            label = (Label) highScoresTable.getChildren().get(j * HIGHSCOREGRIDCOLUMNS + 2);
            label.setText(stats[1]);// write number of hits
            label = (Label) highScoresTable.getChildren().get(j * HIGHSCOREGRIDCOLUMNS + 3);
            label.setText(stats[2]);// write number of shots
            label = (Label) highScoresTable.getChildren().get(j * HIGHSCOREGRIDCOLUMNS + 4);
            label.setText(stats[3] + "%");// write accuracy
            label = (Label) highScoresTable.getChildren().get(j * HIGHSCOREGRIDCOLUMNS + 5);
            String elapsedTimeMin2f = String.format("%.2f", Float.parseFloat(stats[4]));
            label.setText(elapsedTimeMin2f + " min");// write game time

        }
    }


}
