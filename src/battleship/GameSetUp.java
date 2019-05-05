package battleship;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameSetUp {
    @FXML
    private GridPane cruiser;
    @FXML
    private Label crNumberLabel;
    @FXML
    private GridPane grid;

    private double orgTranslateX, orgTranslateY; //dragged object translation
    private double orgSceneX, orgSceneY; // dragged object original location
    private GameGrid playerGrid;
    private int crNumber = 0, currentCruiserNumber, shipLength, shipWidth;
    private boolean dragged = false;

    public void startMainGame(ActionEvent actionEvent) throws IOException {
        if (currentCruiserNumber > 0) return;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_game.fxml"));

        Parent root = loader.load();
        MainGameController mainGameController = loader.getController();
        mainGameController.transferDataToMainGame(crNumber,playerGrid);

        Scene newGameScene = new Scene(root);
        Stage newGameStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        newGameStage.setScene(newGameScene);

        newGameStage.show();
    }

    public void initialize() {
        cruiser.setOnMousePressed(shipOnMousePressedEventHandler);
        cruiser.setOnMouseDragged(shipOnMouseDraggedEventHandler);
        cruiser.setOnMouseReleased(shipOnMouseReleasedEventHandler);
        playerGrid = new GameGrid();
        shipWidth = 1;
        shipLength = 3;

    }

    private void rotate() {

        if (cruiser.getRotate() == 0) cruiser.setRotate(90);
        else cruiser.setRotate(0);
        int temp = shipLength;
        shipLength = shipWidth;
        shipWidth = temp;
    }

    void transferDataToSetUp(String data) {
        crNumberLabel.setText(data);
        crNumber = Integer.parseInt(data);
        currentCruiserNumber=crNumber;
    }

    private final EventHandler<MouseEvent> shipOnMousePressedEventHandler
            = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if(currentCruiserNumber<=0) return;
            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();
            orgTranslateX = ((GridPane) event.getSource()).getTranslateX();
            orgTranslateY = ((GridPane) event.getSource()).getTranslateY();
            dragged = true;
        }
    };
    private final EventHandler<MouseEvent> shipOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(currentCruiserNumber<=0) return;
                    double offsetX = event.getSceneX() - orgSceneX;
                    double offsetY = event.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;
                    cruiser.getScene().setOnKeyPressed(setOnKeyPressed);
                    ((GridPane) event.getSource()).setTranslateX(newTranslateX);
                    ((GridPane) event.getSource()).setTranslateY(newTranslateY);
                }
            };
    private final EventHandler<MouseEvent> shipOnMouseReleasedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(currentCruiserNumber<=0) return;
                    int x = (int) ((cruiser.getLayoutX() + event.getSceneX() - orgSceneX));
                    int y = (int) ((cruiser.getLayoutY() + event.getSceneY() - orgSceneY));
                    if (cruiser.getRotate() == 90) {
                        x += 30;
                        y -= 30;
                    }
                    x = x / 30;
                    y = y / 30;

                    if (x >= 0 && x < 20 && y >= 0 && y < 20 && playerGrid.isAvailable(x, y, shipLength, shipWidth)) {

                        markShip(x, y, shipLength, shipWidth);
                        currentCruiserNumber--;
                        crNumberLabel.setText(Integer.toString(currentCruiserNumber));
                    } else {
                        System.out.println("bad grid drop");
                    }

                    ((GridPane) event.getSource()).setTranslateX(orgTranslateX);
                    ((GridPane) event.getSource()).setTranslateY(orgTranslateY);

                    if (cruiser.getRotate() == 90) rotate();
                    dragged = false;
                }
            };

    private void markShip(int x, int y, int length, int width) {
        playerGrid.markShipLocation(x, y, length, width);
        Pane pane;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                pane = new Pane();
                pane.setStyle("-fx-background-color: green;");
                pane.setPrefSize(28, 28);
                pane.setMaxSize(28, 28);
                GridPane.setHalignment(pane, HPos.CENTER);
                GridPane.setValignment(pane, VPos.CENTER);
                grid.add(pane, x + i, y + j);
            }
        }
    }

    private final EventHandler<KeyEvent> setOnKeyPressed = event -> {

        if (event.getCode() == KeyCode.R && dragged)
            rotate();
    };



}