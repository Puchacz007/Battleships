package battleship.controller;

import battleship.gameObjects.AI;
import battleship.gameObjects.GameGrid;
import battleship.gameObjects.GameSave;
import battleship.gameObjects.HighScores;
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MainGameController {

    private static final int GRIDSIZE = 20;
    private static final int EMPTY = -1;
    public Label misses;
    public Label accuracy;
    public Label shotsFired;
    public Label hits;
    public Label result;
    public AnchorPane mainWindow;
    static Logger logger = Logger.getLogger(MainGameController.class);
    @FXML
    private ImageView target; // target symbol
    private boolean endgame = false;
    @FXML
    private GridPane computerGrid; //computerGrid to choose target and see result of shoot
    @FXML
    private GridPane yourGrid; // computerGrid to see computer shoot
    @FXML
    private AnchorPane menu;
    private final EventHandler<KeyEvent> setOnKeyPressedESC = event -> {

        if (event.getCode() == KeyCode.ESCAPE) {
            menu.setVisible(!menu.isVisible());
        }
    };
    
    private int targetX,targetY;
    private GameGrid computerTargetGrid;
    private AI computer;
    private boolean[][] playerAlreadyShoot;
    private boolean[][] computerAlreadyShoot;
    @FXML
    private AnchorPane endgameScreen;
    public TextField saveName;
    public Pane nicknamePane;
    private int numberOfPlayerShoots = 0;
    private int numberOfHits = 0;
    private int playerShipsNumber;
    private int[] playerShipsTypeNumber;
    private int computerShipsNumber;
    public TextField nicknamePicker;
    private boolean wasDestroyed, wasHit;
    private long startTime;
    private float saveTime;
    private final EventHandler<KeyEvent> nickInputENTER = event -> {

        if (event.getCode() == KeyCode.ENTER) {
            HighScores highScores = new HighScores();
            String nick = nicknamePicker.getCharacters().toString();
            highScores.addHighScore(playerShipsNumber, numberOfPlayerShoots, numberOfHits, startTime, saveTime, nick);
            nicknamePane.setVisible(false);
            showEndScreen();
        }
    };

    public void initialize()
    {
        mainWindow.setOnKeyPressed(setOnKeyPressedESC);
        GridPane.setHalignment(target, HPos.CENTER);
        GridPane.setValignment(target, VPos.CENTER);
        startTime = System.currentTimeMillis();
        nicknamePicker.setOnKeyPressed(nickInputENTER);
        saveName.setOnKeyPressed(setOnKeyPressedESC);
    }

    public void setTarget(MouseEvent mouseEvent) {
        if (endgame) return;
        if (target.isVisible()) computerGrid.getChildren().remove(target);

        int mouseX = (int) mouseEvent.getX();
        int mouseY = (int) mouseEvent.getY();
        mouseX = mouseX / 30;
        mouseY = mouseY / 30;
        targetX = mouseX;
        targetY = mouseY;
        if (playerAlreadyShoot[targetX][targetY]) {
            logger.info("You shoot this target already");
            System.out.println("You shoot this target already");
            return;
        }


        target.setVisible(true);

        computerGrid.add(target, mouseX, mouseY);

    }

    void transferDataToMainGame(int prNumber, int subNumber, int crNumber, int carrNumber, int capNumber, GameGrid playerGrid) {
        wasDestroyed = false;
        wasHit = false;
        computerTargetGrid = new GameGrid();
        computer = new AI();
        playerAlreadyShoot = new boolean[GRIDSIZE][GRIDSIZE];
        computerAlreadyShoot = new boolean[GRIDSIZE][GRIDSIZE];
        saveTime = 0;
        playerShipsTypeNumber = new int[5];
        computerShipsNumber = prNumber + capNumber + crNumber + carrNumber + capNumber;
        playerShipsNumber = prNumber + capNumber + crNumber + carrNumber + capNumber;
        playerShipsTypeNumber[0] = prNumber;//1,1
        playerShipsTypeNumber[1] = subNumber;//2,1
        playerShipsTypeNumber[2] = crNumber;//3,1
        playerShipsTypeNumber[3] = capNumber;//4,1
        playerShipsTypeNumber[4] = carrNumber;//3,2
        computer.setUpShips(prNumber, subNumber, crNumber, carrNumber, capNumber);
        computerTargetGrid = playerGrid;
        showPlayerShips();
    }

    public  void fire()
    {
        if (endgame) return;
        if(target.isVisible()) {
            target.setVisible(false);
            computerGrid.getChildren().remove(target);
            Pane pane = (Pane) computerGrid.getChildren().get(targetY * GRIDSIZE + targetX);
            if (computer.getGrid().isHit(targetX, targetY, true))
            {

                ++numberOfHits;
                if(computer.getGrid().isDestroyed(targetX,targetY)) {
                    --computerShipsNumber;
                    int[] targetArray = computer.getGrid().wasDestroyed(targetX, targetY);
                    int j, i;
                    for (i = 0; i < targetArray[2]; i++) {
                        if (targetArray[1] != 0) {
                            pane = (Pane) computerGrid.getChildren().get((targetArray[1] - 1) * GRIDSIZE + targetArray[0] + i);
                            playerAlreadyShoot[targetArray[0] + i][targetArray[1] - 1] = true;
                            pane.setStyle("-fx-background-color: cyan;-fx-border-color: black;");
                        }
                        for (j = 0; j < targetArray[3]; j++) {
                            pane = (Pane) computerGrid.getChildren().get((targetArray[1] + j) * GRIDSIZE + targetArray[0] + i);
                            pane.setStyle("-fx-background-color: black;-fx-border-color: black;");


                        }
                        if (i == 0 && targetArray[0] != 0) {
                            for (int k = -1; k <= targetArray[3]; k++) {
                                if (targetArray[1] == 0 && k == -1) ++k;
                                if (targetArray[1] + targetArray[3] == GRIDSIZE && k == targetArray[3]) break;
                                pane = (Pane) computerGrid.getChildren().get((targetArray[1] + k) * GRIDSIZE + targetArray[0] - 1);
                                pane.setStyle("-fx-background-color: cyan;-fx-border-color: black;");
                                playerAlreadyShoot[targetArray[0] - 1][targetArray[1] + k] = true;
                            }
                        }
                        if (i == targetArray[2] - 1 && (targetArray[0] + targetArray[2]) != (GRIDSIZE)) {
                            for (int k = -1; k <= targetArray[3]; k++) {
                                if (targetArray[1] == 0 && k == -1) ++k;
                                if (targetArray[1] + targetArray[3] == GRIDSIZE && k == targetArray[3]) break;
                                pane = (Pane) computerGrid.getChildren().get((targetArray[1] + k) * GRIDSIZE + targetArray[0] + targetArray[2]);
                                pane.setStyle("-fx-background-color: cyan;-fx-border-color: black;");
                                playerAlreadyShoot[targetArray[0] + targetArray[2]][targetArray[1] + k] = true;
                            }
                        }
                        if (targetArray[1] + targetArray[3] != GRIDSIZE) {
                            pane = (Pane) computerGrid.getChildren().get((targetArray[1] + targetArray[3]) * GRIDSIZE + targetArray[0] + i);
                            pane.setStyle("-fx-background-color: cyan;-fx-border-color: black;");
                            playerAlreadyShoot[targetArray[0] + i][targetArray[1] + targetArray[3]] = true;
                        }
                    }
                } else {
                    pane.setStyle("-fx-background-color: red;-fx-border-color: black;");

                }


            }else
            {
                pane.setStyle("-fx-background-color: Cyan;-fx-border-color: black;");

            }
            ++numberOfPlayerShoots;
            playerAlreadyShoot[targetX][targetY] = true;
            int maxWidth, maxLength;
            if (playerShipsTypeNumber[4] > 0) maxWidth = 2;
            else maxWidth = 1;
            if (playerShipsTypeNumber[3] > 0) maxLength = 4;
            else if (playerShipsTypeNumber[4] > 0 || playerShipsTypeNumber[2] > 0) maxLength = 3;
            else if (playerShipsTypeNumber[1] > 0) maxLength = 2;
            else maxLength = 1;


            Point target = computer.chooseTarget(wasHit, wasDestroyed, computerAlreadyShoot, maxLength, maxWidth);
            targetX = (int) target.getX();
            targetY = (int) target.getY();
            pane = (Pane) yourGrid.getChildren().get(targetY * GRIDSIZE + targetX);
            wasDestroyed = false;
            wasHit = false;
            if (computerTargetGrid.isHit(targetX, targetY, true))
            {
                wasHit = true;
                if (computerTargetGrid.isDestroyed(targetX, targetY)) {
                    --playerShipsNumber;
                    int size = computerTargetGrid.destroyedShipSize(targetX, targetY);
                    switch (size) {
                        case 1:
                            --playerShipsTypeNumber[0];
                            break;
                        case 2:
                            --playerShipsTypeNumber[1];
                            break;
                        case 3:
                            --playerShipsTypeNumber[2];
                            break;
                        case 4:
                            --playerShipsTypeNumber[3];
                            break;
                        case 6:
                            --playerShipsTypeNumber[4];
                            break;
                    }
                    wasDestroyed = true;
                    int[] targetArray = computerTargetGrid.wasDestroyed(targetX, targetY);
                    for (int i = 0; i < targetArray[2]; i++) {
                        if (targetArray[1] != 0) {
                            pane = (Pane) yourGrid.getChildren().get((targetArray[1] - 1) * GRIDSIZE + targetArray[0] + i);
                            computerAlreadyShoot[targetArray[0] + i][targetArray[1] - 1] = true;
                            pane.setStyle("-fx-background-color: cyan;-fx-border-color: black;");
                        }
                        for (int j = 0; j < targetArray[3]; j++) {

                            pane = (Pane) yourGrid.getChildren().get((targetArray[1] + j) * GRIDSIZE + targetArray[0] + i);
                            pane.setStyle("-fx-background-color: black;-fx-border-color: black;");


                        }
                        if (i == 0 && targetArray[0] != 0) {
                            for (int k = -1; k <= targetArray[3]; k++) {
                                if (targetArray[1] == 0 && k == -1) ++k;
                                if (targetArray[1] + targetArray[3] == GRIDSIZE && k == targetArray[3]) break;
                                pane = (Pane) yourGrid.getChildren().get((targetArray[1] + k) * GRIDSIZE + targetArray[0] - 1);
                                pane.setStyle("-fx-background-color: cyan;-fx-border-color: black;");
                                computerAlreadyShoot[targetArray[0] - 1][targetArray[1] + k] = true;
                            }
                        }
                        if (i == targetArray[2] - 1 && (targetArray[0] + targetArray[2]) != (GRIDSIZE)) {
                            for (int k = -1; k <= targetArray[3]; k++) {
                                if (targetArray[1] == 0 && k == -1) ++k;
                                if (targetArray[1] + targetArray[3] == GRIDSIZE && k == targetArray[3]) break;
                                pane = (Pane) yourGrid.getChildren().get((targetArray[1] + k) * GRIDSIZE + targetArray[0] + targetArray[2]);
                                pane.setStyle("-fx-background-color: cyan;-fx-border-color: black;");
                                computerAlreadyShoot[targetArray[0] + targetArray[2]][targetArray[1] + k] = true;
                            }
                        }
                        if (targetArray[1] + targetArray[3] != GRIDSIZE) {
                            pane = (Pane) yourGrid.getChildren().get((targetArray[1] + targetArray[3]) * GRIDSIZE + targetArray[0] + i);
                            pane.setStyle("-fx-background-color: cyan;-fx-border-color: black;");
                            computerAlreadyShoot[targetArray[0] + i][targetArray[1] + targetArray[3]] = true;
                        }
                    }
                } else {
                    pane.setStyle("-fx-background-color: red;-fx-border-color: black;");
                }
            }else
            {
                pane.setStyle("-fx-background-color: Cyan;-fx-border-color: black;");
            }

            computerAlreadyShoot[targetX][targetY] = true;
            targetX = -1;
            targetY = -1;
            checkVictoryConditions();
        }else
        {
            logger.info("No selected target");
            System.out.println("No selected target");
        }

    }

    private void showPlayerShips() {
        int[][] table = computerTargetGrid.getGridTable();
        for (int i = 0; i < GRIDSIZE; i++) {
            for (int j = 0; j < GRIDSIZE; j++) {
                if (table[i][j] != EMPTY) {
                    Pane pane = (Pane) yourGrid.getChildren().get(j * GRIDSIZE + i);
                    pane.setStyle("-fx-background-color: green;-fx-border-color: black;");
                }
            }
        }
    }

    private void showStats() {
        float shotsAccuracy = 0;
        if (numberOfPlayerShoots > 0) shotsAccuracy = (float) 100 * numberOfHits / numberOfPlayerShoots;
        String numberAsString = String.format("%.2f", shotsAccuracy);
        shotsFired.setText("Shots fired = " + numberOfPlayerShoots);
        hits.setText("Number of hits = " + numberOfHits);
        int missesNumber = numberOfPlayerShoots - numberOfHits;
        misses.setText("Number of misses = " + missesNumber);
        accuracy.setText("Shots accuracy = " + numberAsString + "%");
    }

    public void surrender() {
        showAllAIShips();
        result.setText("DEFEAT !!!");
        menu.setVisible(false);
        nicknamePane.setVisible(true);
        endgame = true;

    }

    private void showAllAIShips() {
        for (int i = 0; i < GRIDSIZE; i++) {
            for (int j = 0; j < GRIDSIZE; j++) {
                Pane pane = (Pane) computerGrid.getChildren().get(j * GRIDSIZE + i);
                if (pane.getStyle().equals("-fx-border-color: black;")) {
                    if (computer.getGrid().isHit(i, j, true)) {
                        pane.setStyle("-fx-background-color: yellow;-fx-border-color: black;");
                    } else {
                        pane.setStyle("-fx-background-color: Cyan;-fx-border-color: black;");
                    }
                }
            }

        }

    }

    public void exit() {
        System.exit(0);
    }

    public void saveGame() throws IOException {

        long elapsedTimeMillis = System.currentTimeMillis() - startTime;
        float elapsedTimeMin = elapsedTimeMillis / (60 * 1000F);
        GameSave gameSave = new GameSave(computerTargetGrid, computer, playerAlreadyShoot, computerAlreadyShoot, wasDestroyed, wasHit, elapsedTimeMin,
                numberOfPlayerShoots, numberOfHits, playerShipsNumber, computerShipsNumber, playerShipsTypeNumber);
        String name = saveName.getCharacters().toString();
        FileOutputStream fileOut = new FileOutputStream("./src/battleship/controller/view/resources/saves/" + name + ".dat");
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(gameSave);
        logger.debug("The object was succesfully written to file");
        // System.out.println("The Object  was succesfully written to a file");

        objectOut.close();
    }

    private void checkVictoryConditions() {
        if (computerShipsNumber <= 0 && playerShipsNumber <= 0) {
            result.setText("DRAW !!!");
            showEndScreen();
        } else if (computerShipsNumber <= 0) {
            result.setText("VICTORY !!!");
            nicknamePane.setVisible(true);
            endgame = true;
            menu.setVisible(false);
        } else if (playerShipsNumber <= 0) {
            result.setText("DEFEAT !!!");
            showEndScreen();
        }

    }

    private void showEndScreen() {
        if (target.isVisible()) target.setVisible(false);
        mainWindow.setOnKeyPressed(null);
        menu.setVisible(false);
        showStats();
        endgameScreen.setVisible(true);
        endgame = true;
    }

    public void transferLoadGame(GameSave gameSave) {
        computerTargetGrid = gameSave.getComputerTargetGrid();
        computer = gameSave.getComputer();
        playerAlreadyShoot = gameSave.getPlayerAlreadyShoot();
        computerAlreadyShoot = gameSave.getComputerAlreadyShoot();
        computerShipsNumber = gameSave.getComputerShipsNumber();
        playerShipsNumber = gameSave.getPlayerShipsNumber();
        numberOfPlayerShoots = gameSave.getNumberOfPlayerShoots();
        numberOfHits = gameSave.getNumberOfHits();
        playerShipsTypeNumber = gameSave.getPlayerShipsTypeNumber();
        saveTime = gameSave.getSaveTime();
        wasHit = gameSave.getWasHit();
        wasDestroyed = gameSave.getWasDestroyed();
        setsSavedGrids();
    }

    private void setsSavedGrids() {
        showPlayerShips();
        for (int targetX = 0; targetX < GRIDSIZE; targetX++) {
            for (int targetY = 0; targetY < GRIDSIZE; targetY++) {

                if (playerAlreadyShoot[targetX][targetY]) {
                    Pane pane = (Pane) computerGrid.getChildren().get(targetY * GRIDSIZE + targetX);
                    if (computer.getGrid().isHit(targetX, targetY, false)) {


                        if (computer.getGrid().isDestroyed(targetX, targetY)) {
                            pane.setStyle("-fx-background-color: black;-fx-border-color: black;");

                        } else {
                            pane.setStyle("-fx-background-color: red;-fx-border-color: black;");
                        }
                    } else {
                        pane.setStyle("-fx-background-color: Cyan;-fx-border-color: black;");

                    }
                }
                if (computerAlreadyShoot[targetX][targetY]) {
                    Pane pane = (Pane) yourGrid.getChildren().get(targetY * GRIDSIZE + targetX);
                    if (computerTargetGrid.isHit(targetX, targetY, false)) {

                        if (computerTargetGrid.isDestroyed(targetX, targetY)) {
                            pane.setStyle("-fx-background-color: black;-fx-border-color: black;");
                        } else {
                            pane.setStyle("-fx-background-color: red;-fx-border-color: black;");
                        }
                    } else {
                        pane.setStyle("-fx-background-color: Cyan;-fx-border-color: black;");
                    }
                }
            }
        }
        targetX = -1;
        targetY = -1;
    }

    public void load(ActionEvent actionEvent) throws IOException {

        Parent loadGameParent = FXMLLoader.load(getClass().getResource("view/load_game.fxml"));
        Scene loadGameScene = new Scene(loadGameParent, 300, 300);
        Stage loadGameStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        loadGameStage.setScene(loadGameScene);
        loadGameStage.show();
        loadGameStage.setMinHeight(340);
        loadGameStage.setMinWidth(315);
        loadGameStage.setMaxWidth(315);
        loadGameStage.setMaxHeight(340);
        loadGameStage.setHeight(340);
        loadGameStage.setWidth(315);
    }

    public void ret(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/main.fxml"));
        Scene primaryScene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(primaryScene);
        primaryStage.show();
        primaryStage.setMinHeight(340);
        primaryStage.setMinWidth(315);
        primaryStage.setMaxWidth(315);
        primaryStage.setMaxHeight(340);
        primaryStage.setHeight(340);
        primaryStage.setWidth(315);
    }
}
