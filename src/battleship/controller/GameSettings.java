package battleship.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class GameSettings {

    public Label crNumber;

    public void startGame(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/game_set_up.fxml"));

        Parent newGameParent = loader.load();
        GameSetUp gameSetUp =loader.getController() ;
        gameSetUp.transferDataToSetUp(crNumber.getText());




        Scene newGameScene = new Scene(newGameParent);
        Stage newGameStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        newGameStage.setScene(newGameScene);

        newGameStage.show();
        newGameStage.setMinHeight(640);
        newGameStage.setMinWidth(1020);
       // newGameStage.setMaxHeight(600);
        //newGameStage.setMaxWidth(800);
    }
  
    private int increase(int i) {
        ++i;
        return i;
    }

    private int decrease(int i) {
        if (i > 0) --i;
        return i;
    }
    private void counter(Label label,boolean operation) //0 - decrease 1 - increase
    {
        String text= label.getText();
        int i = Integer.parseInt(text);
        if(operation)
        i=increase(i);
        else i=decrease(i);
        label.setText(Integer.toString(i));
    }
    public void increaseCruiser() {
       counter(crNumber,true);
    }

    public void decreaseCruiser() {
        counter(crNumber,false);
    }
}
