package battleship.controller;

import battleship.gameObjects.GameGrid;
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

    private final EventHandler<MouseEvent> shipOnMouseDraggedEventHandler =
            new EventHandler<>() {
                @Override
                public void handle(MouseEvent event) {
                    if (!dragged) return;

                    double offsetX = event.getSceneX() - orgSceneX;
                    double offsetY = event.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;


                    ((GridPane) event.getSource()).setTranslateX(newTranslateX);
                    ((GridPane) event.getSource()).setTranslateY(newTranslateY);
                }
            };
    public Label capitalNr;
    @FXML
    public Label carrierNr;
    @FXML
    public Label submarineNr;
    @FXML
    public Label patrolNr;
    @FXML
    public GridPane patrol;
    @FXML
    public GridPane submarine;
    @FXML
    public GridPane capital;
    @FXML
    private GridPane cruiser;
    @FXML
    public GridPane carrier;
    @FXML
    private GridPane grid;

    private double orgTranslateX, orgTranslateY; //dragged object translation
    private double orgSceneX, orgSceneY; // dragged object original location
    private GameGrid playerGrid;
    @FXML
    private Label cruiserNr;
    private int crNumber, carrNumber, subNumber, capNumber, prNumber, shipLength, shipWidth;
    private boolean dragged = false;
    private Object source;
    private final EventHandler<MouseEvent> shipOnMouseReleasedEventHandler =
            new EventHandler<>() {
                @Override
                public void handle(MouseEvent event) {
                    if (!dragged) return;

                    int x = (int) (((GridPane) event.getSource()).getLayoutX() + event.getSceneX() - orgSceneX);
                    int y = (int) (((GridPane) event.getSource()).getLayoutY() + event.getSceneY() - orgSceneY);

                    if (((GridPane) event.getSource()).getRotate() == 90) {
                        //   x += 30;
                        //  y -= 30;

                    }
                    x = x / 30;
                    y = y / 30;

                    if (x >= 0 && x < 20 && y >= 0 && y < 20 && playerGrid.isAvailable(x, y, shipLength, shipWidth)) {

                        if (((GridPane) event.getSource()).getRotate() == 0) markShip(x, y, shipLength, shipWidth);
                        else markShip(x, y, shipWidth, shipLength);
                        String text;
                        int number;


                        switch (shipLength) {
                            case 1:
                                text = patrolNr.getText();
                                number = Integer.parseInt(text);
                                --number;
                                patrolNr.setText(Integer.toString(number));
                                break;
                            case 2:
                                text = submarineNr.getText();
                                number = Integer.parseInt(text);
                                --number;
                                submarineNr.setText(Integer.toString(number));
                                break;
                            case 3:
                                if (shipWidth == 1) {
                                    text = cruiserNr.getText();
                                    number = Integer.parseInt(text);
                                    --number;
                                    cruiserNr.setText(Integer.toString(number));
                                } else if (shipWidth == 2) {
                                    text = carrierNr.getText();
                                    number = Integer.parseInt(text);
                                    --number;
                                    carrierNr.setText(Integer.toString(number));
                                }
                                break;
                            case 4:
                                text = capitalNr.getText();
                                number = Integer.parseInt(text);
                                --number;
                                capitalNr.setText(Integer.toString(number));
                                break;
                        }

                    } else {
                        System.out.println("bad grid drop");
                    }
                    ((GridPane) event.getSource()).setTranslateX(orgTranslateX);
                    ((GridPane) event.getSource()).setTranslateY(orgTranslateY);

                    if (((GridPane) event.getSource()).getRotate() == 90) rotate();
                    dragged = false;
                }
            };
    private final EventHandler<KeyEvent> setOnKeyPressed = event -> {

        if (event.getCode() == KeyCode.R && dragged) {
            rotate();
        }

    };
    private final EventHandler<MouseEvent> shipOnMousePressedEventHandler
            = new EventHandler<>() {
        @Override
        public void handle(MouseEvent event) {
            // if (currentCruiserNumber <= 0) return;

            shipLength = ((GridPane) event.getSource()).getColumnCount();
            shipWidth = ((GridPane) event.getSource()).getRowCount();
            source = event.getSource();

            String text;
            int number;

            switch (shipLength) {
                case 1:
                    text = patrolNr.getText();
                    number = Integer.parseInt(text);
                    if (number == 0) return;
                    patrol.getScene().setOnKeyPressed(setOnKeyPressed);
                    break;
                case 2:
                    text = submarineNr.getText();
                    number = Integer.parseInt(text);
                    if (number == 0) return;
                    submarine.getScene().setOnKeyPressed(setOnKeyPressed);
                    break;
                case 3:
                    if (shipWidth == 1) {
                        text = cruiserNr.getText();
                        number = Integer.parseInt(text);
                        if (number == 0) return;
                        cruiser.getScene().setOnKeyPressed(setOnKeyPressed);
                    } else if (shipWidth == 2) {
                        text = carrierNr.getText();
                        number = Integer.parseInt(text);
                        if (number == 0) return;
                        carrier.getScene().setOnKeyPressed(setOnKeyPressed);
                    }
                    break;
                case 4:
                    text = capitalNr.getText();
                    number = Integer.parseInt(text);
                    if (number == 0) return;
                    capital.getScene().setOnKeyPressed(setOnKeyPressed);
                    break;
            }
            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();
            orgTranslateX = ((GridPane) event.getSource()).getTranslateX();
            orgTranslateY = ((GridPane) event.getSource()).getTranslateY();
            dragged = true;
        }
    };
    private int shipNumber = 0;

    public void initialize() {


        cruiser.setOnMousePressed(shipOnMousePressedEventHandler);
        cruiser.setOnMouseDragged(shipOnMouseDraggedEventHandler);
        cruiser.setOnMouseReleased(shipOnMouseReleasedEventHandler);

        capital.setOnMousePressed(shipOnMousePressedEventHandler);
        capital.setOnMouseDragged(shipOnMouseDraggedEventHandler);
        capital.setOnMouseReleased(shipOnMouseReleasedEventHandler);

        patrol.setOnMousePressed(shipOnMousePressedEventHandler);
        patrol.setOnMouseDragged(shipOnMouseDraggedEventHandler);
        patrol.setOnMouseReleased(shipOnMouseReleasedEventHandler);

        carrier.setOnMousePressed(shipOnMousePressedEventHandler);
        carrier.setOnMouseDragged(shipOnMouseDraggedEventHandler);
        carrier.setOnMouseReleased(shipOnMouseReleasedEventHandler);

        submarine.setOnMousePressed(shipOnMousePressedEventHandler);
        submarine.setOnMouseDragged(shipOnMouseDraggedEventHandler);
        submarine.setOnMouseReleased(shipOnMouseReleasedEventHandler);

        playerGrid = new GameGrid();


    }

    private void rotate() {

        if (((GridPane) source).getRotate() == 0) ((GridPane) source).setRotate(90);
        else ((GridPane) source).setRotate(0);
    }

    void transferDataToSetUp(String crData, String carrData, String subData, String capData, String prData) {
        cruiserNr.setText(crData);
        crNumber = Integer.parseInt(crData);

        carrierNr.setText(carrData);
        carrNumber = Integer.parseInt(carrData);

        submarineNr.setText(subData);
        subNumber = Integer.parseInt(subData);

        capitalNr.setText(capData);
        capNumber = Integer.parseInt(capData);

        patrolNr.setText(prData);
        prNumber = Integer.parseInt(prData);
        shipNumber = crNumber + carrNumber + subNumber + capNumber + prNumber;
    }

    public void startMainGame(ActionEvent actionEvent) throws IOException {
        //if (currentCruiserNumber > 0) return;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/main_game.fxml"));

        Parent root = loader.load();
        MainGameController mainGameController = loader.getController();
        mainGameController.transferDataToMainGame(prNumber, subNumber, crNumber, carrNumber, capNumber, playerGrid);

        Scene newGameScene = new Scene(root);
        Stage newGameStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        newGameStage.setScene(newGameScene);

        newGameStage.show();
    }

    private void markShip(int x, int y, int length, int width) {
        playerGrid.addShip(x, y, length, width);
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



}