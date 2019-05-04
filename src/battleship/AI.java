package battleship;

import javafx.scene.layout.GridPane;

import java.util.Random;

public class AI {
    private GameGrid shipsGrid;
    AI()
    {
        shipsGrid = new GameGrid();
    }
    public int chooseTarget(int bound)
    {
        Random random = new Random();
      return   random.nextInt(bound);
    }
  /*  public int chooseTargetY()
    {
        Random random = new Random();
        return   random.nextInt(20);
    }*/
    public  void setUpShips(int crNumber)
    {
        int targetX;
        int targetY;
        int errorExit=0;
        int lenght;
        int width;
        int temp;
        for(int i = 0;i<crNumber;i++) {

            do {
               errorExit++;
                targetX = chooseTarget(18);
                targetY = chooseTarget(18);
                if(chooseRotation())
                {
                    lenght=3;
                    width=1;
                }else
                {
                    width=3;
                    lenght=1;
                }
                if(shipsGrid.isAvailable(targetX, targetY, lenght, width))break;
                temp=lenght;
                lenght=width;
                width=temp;

                if (errorExit==1000000)return;

            }
            while (!shipsGrid.isAvailable(targetX, targetY, lenght, width));

            shipsGrid.markShipLocation(targetX, targetY, lenght, width);
        }

    }
    public boolean chooseRotation()//true - horizontal / false - vertical
    {
        Random random = new Random();
        return random.nextBoolean();
    }
    public GameGrid getGrid()
    {
        return  shipsGrid;
    }

}
