package battleship.controller;

import battleship.gameObjects.AI;
import battleship.gameObjects.GameGrid;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MainGameController {

    private static final int GRIDSIZE = 20;
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

           Pane pane =new Pane();
           pane.setPrefSize(27,27);
           pane.setMaxSize(27,27);
            GridPane.setHalignment(pane, HPos.CENTER);
            GridPane.setValignment(pane, VPos.CENTER);
            if (computer.getGrid().isHit(targetX,targetY))
            {
                pane.setStyle("-fx-background-color: red;");
                grid.add(pane,targetX,targetY);

                if(computer.getGrid().isDestroyed(targetX,targetY))
                {
                    int[] targetArray = computer.getGrid().wasDestroyed(targetX, targetY);
                    for(int i=0;i<targetArray[2];i++)
                    {
                        for(int j=0;j<targetArray[3];j++)
                        {
                           Node node =getNodeByRowColumnIndex(targetArray[0]+i,targetArray[1]+j,grid);
                            node.setStyle("-fx-background-color: black;");


                        }
                    }
                }


            }else
            {

                pane.setStyle("-fx-background-color: Cyan;");
               grid.add(pane,targetX,targetY);

            }
            playerAlreadyShoot[targetX][targetY] = true;

            do {
                targetX = computer.chooseTarget(20);
                targetY = computer.chooseTarget(20);
            }
            while (computerAlreadyShoot[targetX][targetY]);
            pane =new Pane();
            pane.setPrefSize(14,14);
            pane.setMaxSize(14,14);
            GridPane.setHalignment(pane, HPos.CENTER);
            GridPane.setValignment(pane, VPos.CENTER);
            if(computerTargetGrid.isHit(targetX,targetY))
            {
                pane.setStyle("-fx-background-color: red;");
                yourGrid.add(pane,targetX,targetY);
                if(computerTargetGrid.isDestroyed(targetX,targetY))
                {
                    int[] targetArray = computerTargetGrid.wasDestroyed(targetX, targetY);
                    for(int i=0;i<targetArray[2];i++)
                    {
                        for(int j=0;j<targetArray[3];j++)
                        {
                            Node node =getNodeByRowColumnIndex(targetArray[0]+i,targetArray[1]+j,yourGrid);
                            node.setStyle("-fx-background-color: black;");


                        }
                    }
                }
            }else
            {
                pane.setStyle("-fx-background-color: Cyan;");
                yourGrid.add(pane,targetX,targetY);
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
    public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }
}
