package battleship;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MainGameController {

    @FXML
    public ImageView target; // target symbol
    @FXML
    public GridPane grid; //grid to choose target and see result of shoot
    @FXML
    public GridPane yourGrid; // grid to see computer shoot

    private int playerCrNumber,computerCrNumber;
    private int targetX,targetY;
    private  GameGrid computerTargetGrid;
    private AI computer;
    public void initialize()
    {
      //targetGrid =new GameGrid();
      computerTargetGrid=new GameGrid();
      computer = new AI();

        GridPane.setHalignment(target, HPos.CENTER);
        GridPane.setValignment(target, VPos.CENTER);
    }

    void transferDataToMainGame(int crNumber,GameGrid playerGrid)
    {
        playerCrNumber=crNumber;
        computerCrNumber=crNumber;
        computer.setUpShips(computerCrNumber);
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


            }else
            {

                pane.setStyle("-fx-background-color: Cyan;");
               grid.add(pane,targetX,targetY);

            }
            System.out.println("Computer turn");
            targetX = computer.chooseTarget(20);
            targetY = computer.chooseTarget(20);

            pane =new Pane();
            pane.setPrefSize(14,14);
            pane.setMaxSize(14,14);
            GridPane.setHalignment(pane, HPos.CENTER);
            GridPane.setValignment(pane, VPos.CENTER);
            if(computerTargetGrid.isHit(targetX,targetY))
            {
                pane.setStyle("-fx-background-color: red;");
                yourGrid.add(pane,targetX,targetY);
            }else
            {
                pane.setStyle("-fx-background-color: Cyan;");
                yourGrid.add(pane,targetX,targetY);
            }
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
}
