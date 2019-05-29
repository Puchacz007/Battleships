package battleship.controller;

import battleship.gameObjects.AI;
import battleship.gameObjects.GameGrid;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.awt.*;

public class MainGameController {

    private static final int GRIDSIZE = 20;
    private static final int EMPTY = -1;
    public Label misses;
    public Label accuracy;
    public Label shotsFired;
    public Label hits;
    public Label result;
    public AnchorPane mainWindow;
    @FXML
    private ImageView target; // target symbol
    boolean endgame = false;
    @FXML
    private GridPane computerGrid; //computerGrid to choose target and see result of shoot
    @FXML
    private GridPane yourGrid; // computerGrid to see computer shoot
    @FXML
    private AnchorPane menu;



    //  private int playerCrNumber,computerCrNumber;
    private int targetX,targetY;
    private GameGrid computerTargetGrid;
    private AI computer;
    private boolean[][] playerAlreadyShoot;
    private boolean[][] computerAlreadyShoot;
    private boolean wasDestroyed = false, wasHit = false;
    private final EventHandler<KeyEvent> setOnKeyPressedESC = event -> {

        if (event.getCode() == KeyCode.ESCAPE) {
            menu.setVisible(!menu.isVisible());
        }
    };
    @FXML
    private AnchorPane endgameScreen;
    private int numberOfPlayerShoots = 0;
    private int numberOfHits = 0;
    private int playerShipsNumber;
    //private  int AIShipsNumber[];
    private int[] playerShipsTypeNumber;
    private int computerShipsNumber;

    public void initialize()
    {
      computerTargetGrid=new GameGrid();
      computer = new AI();

        GridPane.setHalignment(target, HPos.CENTER);
        GridPane.setValignment(target, VPos.CENTER);
        playerAlreadyShoot = new boolean[GRIDSIZE][GRIDSIZE];
        computerAlreadyShoot = new boolean[GRIDSIZE][GRIDSIZE];
        mainWindow.setOnKeyPressed(setOnKeyPressedESC);

    }

    void transferDataToMainGame(int prNumber, int subNumber, int crNumber, int carrNumber, int capNumber, GameGrid playerGrid)
    {
        //playerCrNumber=crNumber;
        // computerCrNumber=crNumber;
        //AIShipsNumber=new int[5];
        playerShipsTypeNumber = new int[5];
        //   AIShipsNumber[0]=prNumber;
        // AIShipsNumber[1]=subNumber;
        // AIShipsNumber[2]=crNumber;
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

    public void setTarget(MouseEvent mouseEvent) {
        if (endgame) return;
        if (target.isVisible()) computerGrid.getChildren().remove(target);

        int mouseX= (int) mouseEvent.getX();
        int mouseY= (int) mouseEvent.getY();
        mouseX=mouseX/30;
        mouseY=mouseY/30;
        targetX=mouseX;
        targetY=mouseY;
        if (playerAlreadyShoot[targetX][targetY]) {
            System.out.println("You shoot this target already");
            return;
        }



        target.setVisible(true);

        computerGrid.add(target, mouseX, mouseY);

    }

    public  void fire()
    {
        if (endgame) return;
        if(target.isVisible()) {
            target.setVisible(false);
            computerGrid.getChildren().remove(target);

            //Node node =  getNodeByRowColumnIndex(targetX,targetY,computerGrid);


            //  Pane pane =new Pane();
            // pane.setPrefSize(27,27);
            //pane.setMaxSize(27,27);
            //GridPane.setHalignment(pane, HPos.CENTER);
            //GridPane.setValignment(pane, VPos.CENTER);
            Pane pane = (Pane) computerGrid.getChildren().get(targetY * GRIDSIZE + targetX);
            if (computer.getGrid().isHit(targetX,targetY))
            {
                pane.setStyle("-fx-background-color: red;");
                //  pane.setStyle("-fx-background-color: red;");
                // computerGrid.add(pane,targetX,targetY);
                ++numberOfHits;
                if(computer.getGrid().isDestroyed(targetX,targetY))
                {
                    --computerShipsNumber;
                    int[] targetArray = computer.getGrid().wasDestroyed(targetX, targetY);
                    int j, i;
                    for (i = 0; i < targetArray[2]; i++)
                    {
                        if (targetArray[1] != 0) {
                            pane = (Pane) computerGrid.getChildren().get((targetArray[1] - 1) * GRIDSIZE + targetArray[0] + i);
                            playerAlreadyShoot[targetArray[0] + i][targetArray[1] - 1] = true;
                            pane.setStyle("-fx-background-color: cyan;");
                        }
                        for (j = 0; j < targetArray[3]; j++)
                        {
                            //node =getNodeByRowColumnIndex(targetArray[0]+i,targetArray[1]+j,computerGrid);

                            pane = (Pane) computerGrid.getChildren().get((targetArray[1] + j) * GRIDSIZE + targetArray[0] + i);
                            pane.setStyle("-fx-background-color: black;");


                        }
                        if (i == 0 && targetArray[0] != 0) {
                            for (int k = -1; k <= targetArray[3]; k++) {
                                if (targetArray[1] == 0 && k == -1) ++k;
                                if (targetArray[1] + targetArray[3] == GRIDSIZE && k == targetArray[3]) break;
                                pane = (Pane) computerGrid.getChildren().get((targetArray[1] + k) * GRIDSIZE + targetArray[0] - 1);
                                pane.setStyle("-fx-background-color: cyan;");
                                playerAlreadyShoot[targetArray[0] - 1][targetArray[1] + k] = true;
                            }
                        }
                        if (i == targetArray[2] - 1 && (targetArray[0] + targetArray[2]) != (GRIDSIZE)) {
                            for (int k = -1; k <= targetArray[3]; k++) {
                                if (targetArray[1] == 0 && k == -1) ++k;
                                if (targetArray[1] + targetArray[3] == GRIDSIZE && k == targetArray[3]) break;
                                pane = (Pane) computerGrid.getChildren().get((targetArray[1] + k) * GRIDSIZE + targetArray[0] + targetArray[2]);
                                pane.setStyle("-fx-background-color: cyan;");
                                playerAlreadyShoot[targetArray[0] + targetArray[2]][targetArray[1] + k] = true;
                            }
                        }
                        if (targetArray[1] + targetArray[3] != GRIDSIZE) {
                            pane = (Pane) computerGrid.getChildren().get((targetArray[1] + targetArray[3]) * GRIDSIZE + targetArray[0] + i);
                            pane.setStyle("-fx-background-color: cyan;");
                            playerAlreadyShoot[targetArray[0] + i][targetArray[1] + targetArray[3]] = true;
                        }
                    }
                }


            }else
            {
                pane.setStyle("-fx-background-color: Cyan;");
                // pane.setStyle("-fx-background-color: Cyan;");
                //computerGrid.add(pane,targetX,targetY);

            }
            ++numberOfPlayerShoots;
            playerAlreadyShoot[targetX][targetY] = true;
            //   int iteration=0;

            //  targetX = computer.randomInt(20);
            //  targetY = computer.randomInt(20);
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
            // ++iteration;

            //  while (computerAlreadyShoot[targetX][targetY]);
            pane = (Pane) yourGrid.getChildren().get(targetY * GRIDSIZE + targetX);
            wasDestroyed = false;
            wasHit = false;
            //  pane.setPrefSize(14,14);
            //  pane.setMaxSize(14,14);
            //  GridPane.setHalignment(pane, HPos.CENTER);
            //   GridPane.setValignment(pane, VPos.CENTER);
            if(computerTargetGrid.isHit(targetX,targetY))
            {
                wasHit = true;
                pane.setStyle("-fx-background-color: red;");
                // yourGrid.add(pane,targetX,targetY);
                if(computerTargetGrid.isDestroyed(targetX,targetY))
                {
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
                    for(int i=0;i<targetArray[2];i++)
                    {
                        if (targetArray[1] != 0) {
                            pane = (Pane) yourGrid.getChildren().get((targetArray[1] - 1) * GRIDSIZE + targetArray[0] + i);
                            computerAlreadyShoot[targetArray[0] + i][targetArray[1] - 1] = true;
                            pane.setStyle("-fx-background-color: cyan;");
                        }
                        for(int j=0;j<targetArray[3];j++) {
                            //node =getNodeByRowColumnIndex(targetArray[0]+i,targetArray[1]+j,computerGrid);

                            pane = (Pane) yourGrid.getChildren().get((targetArray[1] + j) * GRIDSIZE + targetArray[0] + i);
                            pane.setStyle("-fx-background-color: black;");


                        }
                        if (i == 0 && targetArray[0] != 0) {
                            for (int k = -1; k <= targetArray[3]; k++) {
                                if (targetArray[1] == 0 && k == -1) ++k;
                                if (targetArray[1] + targetArray[3] == GRIDSIZE && k == targetArray[3]) break;
                                pane = (Pane) yourGrid.getChildren().get((targetArray[1] + k) * GRIDSIZE + targetArray[0] - 1);
                                pane.setStyle("-fx-background-color: cyan;");
                                computerAlreadyShoot[targetArray[0] - 1][targetArray[1] + k] = true;
                            }
                        }
                        if (i == targetArray[2] - 1 && (targetArray[0] + targetArray[2]) != (GRIDSIZE)) {
                            for (int k = -1; k <= targetArray[3]; k++) {
                                if (targetArray[1] == 0 && k == -1) ++k;
                                if (targetArray[1] + targetArray[3] == GRIDSIZE && k == targetArray[3]) break;
                                pane = (Pane) yourGrid.getChildren().get((targetArray[1] + k) * GRIDSIZE + targetArray[0] + targetArray[2]);
                                pane.setStyle("-fx-background-color: cyan;");
                                computerAlreadyShoot[targetArray[0] + targetArray[2]][targetArray[1] + k] = true;
                            }
                        }
                        if (targetArray[1] + targetArray[3] != GRIDSIZE) {
                            pane = (Pane) yourGrid.getChildren().get((targetArray[1] + targetArray[3]) * GRIDSIZE + targetArray[0] + i);
                            pane.setStyle("-fx-background-color: cyan;");
                            computerAlreadyShoot[targetArray[0] + i][targetArray[1] + targetArray[3]] = true;
                        }
                    }
                }
            }else
            {
                pane.setStyle("-fx-background-color: Cyan;");
                // yourGrid.add(pane,targetX,targetY);
            }

            computerAlreadyShoot[targetX][targetY] = true;
            targetX = -1;
            targetY = -1;
            checkVictoryConditions();
        }else
        {
            System.out.println("No selected target");
        }

    }
    public void test()
    {
        Pane pane;
           for(int i=0;i<20;i++) {

               for(int j=0;j<20;j++)
               {
               pane = new Pane();
               pane.setPrefSize(27, 27);
               pane.setMaxSize(27, 27);
               GridPane.setHalignment(pane, HPos.CENTER);
               GridPane.setValignment(pane, VPos.CENTER);
               if (computer.getGrid().isHit(i, j)) {
                   pane.setStyle("-fx-background-color: red;");
                   computerGrid.add(pane, i, j);


               } else {

                   pane.setStyle("-fx-background-color: Cyan;");
                   computerGrid.add(pane, i, j);
               }
                   pane =new Pane();
                   pane.setPrefSize(14,14);
                   pane.setMaxSize(14,14);
                   GridPane.setHalignment(pane, HPos.CENTER);
                   GridPane.setValignment(pane, VPos.CENTER);
                   if(computerTargetGrid.isHit(i,j))
                   {
                       pane.setStyle("-fx-background-color: red;");
                       yourGrid.add(pane,i,j);
                   }else
                   {
                       pane.setStyle("-fx-background-color: Cyan;");
                       yourGrid.add(pane,i,j);
                   }
               }
           }
    }

    public void showPlayerShips() {
        int[][] table = computerTargetGrid.getGridTable();
        for (int i = 0; i < GRIDSIZE; i++) {
            for (int j = 0; j < GRIDSIZE; j++) {
                if (table[i][j] != EMPTY) {
                    Pane pane = (Pane) yourGrid.getChildren().get(j * GRIDSIZE + i);
                    pane.setStyle("-fx-background-color: green;");
                }
            }
        }
    }

    public void showStats() {
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
        showEndScreen();
    }

    public void showAllAIShips() {
        for (int i = 0; i < GRIDSIZE; i++) {
            for (int j = 0; j < GRIDSIZE; j++) {
                Pane pane = (Pane) computerGrid.getChildren().get(j * GRIDSIZE + i);
                if (pane.getStyle().isEmpty()) {
                    if (computer.getGrid().isHit(i, j)) {
                        pane.setStyle("-fx-background-color: yellow;");
                    } else {
                        pane.setStyle("-fx-background-color: Cyan;");
                    }
                }
            }

        }

    }


    public void saveGame() {

    }

    public void exit() {
        System.exit(0);
    }

    public void checkVictoryConditions() {
        if (computerShipsNumber == 0 && playerShipsNumber == 0) {
            result.setText("DRAW !!!");
            showEndScreen();
        } else if (computerShipsNumber == 0) {
            result.setText("VICTORY !!!");
            showEndScreen();
        } else if (playerShipsNumber == 0) {
            result.setText("DEFEAT !!!");
            showEndScreen();
        }

    }

    public void showEndScreen() {
        if (target.isVisible()) target.setVisible(false);
        mainWindow.setOnKeyPressed(null);
        menu.setVisible(false);
        showStats();
        endgameScreen.setVisible(true);
        endgame = true;

        //stop game
    }

    public void load() {

    }

}
