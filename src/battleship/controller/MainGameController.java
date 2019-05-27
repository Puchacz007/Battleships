package battleship.controller;

import battleship.gameObjects.AI;
import battleship.gameObjects.GameGrid;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MainGameController {

    private static final int GRIDSIZE = 20;
    private static final int EMPTY = -1;
    @FXML
    private ImageView target; // target symbol
    @FXML
    private GridPane grid; //grid to choose target and see result of shoot
    @FXML
    private GridPane yourGrid; // grid to see computer shoot

    //  private int playerCrNumber,computerCrNumber;
    private int targetX,targetY;
    private GameGrid computerTargetGrid;
    private AI computer;
    private boolean[][] playerAlreadyShoot;
    private boolean[][] computerAlreadyShoot;
    public void initialize()
    {
      computerTargetGrid=new GameGrid();
      computer = new AI();

        GridPane.setHalignment(target, HPos.CENTER);
        GridPane.setValignment(target, VPos.CENTER);
        playerAlreadyShoot = new boolean[GRIDSIZE][GRIDSIZE];
        computerAlreadyShoot = new boolean[GRIDSIZE][GRIDSIZE];

    }

    void transferDataToMainGame(int prNumber, int subNumber, int crNumber, int carrNumber, int capNumber, GameGrid playerGrid)
    {
        //playerCrNumber=crNumber;
        // computerCrNumber=crNumber;
        computer.setUpShips(prNumber, subNumber, crNumber, carrNumber, capNumber);
        computerTargetGrid = playerGrid;
        showPlayerShips();
    }

    public void setTarget(MouseEvent mouseEvent) {
       if(target.isVisible()) grid.getChildren().remove(target);

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

       grid.add(target,mouseX,mouseY);

    }
    public  void fire()
    {
        if(target.isVisible()) {
            target.setVisible(false);
            grid.getChildren().remove(target);

            //Node node =  getNodeByRowColumnIndex(targetX,targetY,grid);


            //  Pane pane =new Pane();
            // pane.setPrefSize(27,27);
            //pane.setMaxSize(27,27);
            //GridPane.setHalignment(pane, HPos.CENTER);
            //GridPane.setValignment(pane, VPos.CENTER);
            Pane pane = (Pane) grid.getChildren().get(targetY * 20 + targetX);
            if (computer.getGrid().isHit(targetX,targetY))
            {
                pane.setStyle("-fx-background-color: red;");
                //  pane.setStyle("-fx-background-color: red;");
                // grid.add(pane,targetX,targetY);

                if(computer.getGrid().isDestroyed(targetX,targetY))
                {
                    int[] targetArray = computer.getGrid().wasDestroyed(targetX, targetY);
                    int j, i;
                    for (i = 0; i < targetArray[2]; i++)
                    {
                        if (targetArray[1] != 0) {
                            pane = (Pane) grid.getChildren().get((targetArray[1] - 1) * 20 + targetArray[0] + i);
                            playerAlreadyShoot[targetArray[0] + i][targetArray[1] - 1] = true;
                            pane.setStyle("-fx-background-color: cyan;");
                        }
                        for (j = 0; j < targetArray[3]; j++)
                        {
                            //node =getNodeByRowColumnIndex(targetArray[0]+i,targetArray[1]+j,grid);

                            pane = (Pane) grid.getChildren().get((targetArray[1] + j) * 20 + targetArray[0] + i);
                            pane.setStyle("-fx-background-color: black;");


                        }
                        if (i == 0 && targetArray[0] != 0) {
                            for (int k = -1; k <= targetArray[3]; k++) {
                                if (targetArray[1] == 0 && k == -1) ++k;
                                if (targetArray[1] + targetArray[3] == GRIDSIZE && k == targetArray[3]) break;
                                pane = (Pane) grid.getChildren().get((targetArray[1] + k) * 20 + targetArray[0] - 1);
                                pane.setStyle("-fx-background-color: cyan;");
                                playerAlreadyShoot[targetArray[0] - 1][targetArray[1] + k] = true;
                            }
                        }
                        if (i == targetArray[2] - 1 && (targetArray[0] + targetArray[2]) != (GRIDSIZE)) {
                            for (int k = -1; k <= targetArray[3]; k++) {
                                if (targetArray[1] == 0 && k == -1) ++k;
                                if (targetArray[1] + targetArray[3] == GRIDSIZE && k == targetArray[3]) break;
                                pane = (Pane) grid.getChildren().get((targetArray[1] + k) * 20 + targetArray[0] + targetArray[2]);
                                pane.setStyle("-fx-background-color: cyan;");
                                playerAlreadyShoot[targetArray[0] + targetArray[2]][targetArray[1] + k] = true;
                            }
                        }
                        if (targetArray[1] + targetArray[3] != GRIDSIZE) {
                            pane = (Pane) grid.getChildren().get((targetArray[1] + targetArray[3]) * 20 + targetArray[0] + i);
                            pane.setStyle("-fx-background-color: cyan;");
                            playerAlreadyShoot[targetArray[0] + i][targetArray[1] + targetArray[3]] = true;
                        }
                    }
                }


            }else
            {
                pane.setStyle("-fx-background-color: Cyan;");
                // pane.setStyle("-fx-background-color: Cyan;");
                //grid.add(pane,targetX,targetY);

            }
            playerAlreadyShoot[targetX][targetY] = true;

            do {
                targetX = computer.randomInt(20);
                targetY = computer.randomInt(20);
            }
            while (computerAlreadyShoot[targetX][targetY]);
            pane = (Pane) yourGrid.getChildren().get(targetY * 20 + targetX);

            //  pane.setPrefSize(14,14);
            //  pane.setMaxSize(14,14);
            //  GridPane.setHalignment(pane, HPos.CENTER);
            //   GridPane.setValignment(pane, VPos.CENTER);
            if(computerTargetGrid.isHit(targetX,targetY))
            {
                pane.setStyle("-fx-background-color: red;");
                // yourGrid.add(pane,targetX,targetY);
                if(computerTargetGrid.isDestroyed(targetX,targetY))
                {
                    int[] targetArray = computer.getGrid().wasDestroyed(targetX, targetY);
                    for(int i=0;i<targetArray[2];i++)
                    {
                        if (targetArray[1] != 0) {
                            pane = (Pane) yourGrid.getChildren().get((targetArray[1] - 1) * 20 + targetArray[0] + i);
                            computerAlreadyShoot[targetArray[0] + i][targetArray[1] - 1] = true;
                            pane.setStyle("-fx-background-color: cyan;");
                        }
                        for(int j=0;j<targetArray[3];j++) {
                            //node =getNodeByRowColumnIndex(targetArray[0]+i,targetArray[1]+j,grid);

                            pane = (Pane) yourGrid.getChildren().get((targetArray[1] + j) * 20 + targetArray[0] + i);
                            pane.setStyle("-fx-background-color: black;");


                        }
                        if (i == 0 && targetArray[0] != 0) {
                            for (int k = -1; k <= targetArray[3]; k++) {
                                if (targetArray[1] == 0 && k == -1) ++k;
                                if (targetArray[1] + targetArray[3] == GRIDSIZE && k == targetArray[3]) break;
                                pane = (Pane) yourGrid.getChildren().get((targetArray[1] + k) * 20 + targetArray[0] - 1);
                                pane.setStyle("-fx-background-color: cyan;");
                                computerAlreadyShoot[targetArray[0] - 1][targetArray[1] + k] = true;
                            }
                        }
                        if (i == targetArray[2] - 1 && (targetArray[0] + targetArray[2]) != (GRIDSIZE)) {
                            for (int k = -1; k <= targetArray[3]; k++) {
                                if (targetArray[1] == 0 && k == -1) ++k;
                                if (targetArray[1] + targetArray[3] == GRIDSIZE && k == targetArray[3]) break;
                                pane = (Pane) yourGrid.getChildren().get((targetArray[1] + k) * 20 + targetArray[0] + targetArray[2]);
                                pane.setStyle("-fx-background-color: cyan;");
                                computerAlreadyShoot[targetArray[0] + targetArray[2]][targetArray[1] + k] = true;
                            }
                        }
                        if (targetArray[1] + targetArray[3] != GRIDSIZE) {
                            pane = (Pane) yourGrid.getChildren().get((targetArray[1] + targetArray[3]) * 20 + targetArray[0] + i);
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
                   grid.add(pane, i, j);


               } else {

                   pane.setStyle("-fx-background-color: Cyan;");
                   grid.add(pane, i, j);
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
                    Pane pane = (Pane) yourGrid.getChildren().get(j * 20 + i);
                    pane.setStyle("-fx-background-color: green;");
                }
            }
        }
    }
}
