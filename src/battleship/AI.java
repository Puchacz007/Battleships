package battleship;

import java.util.Random;

class AI {
    private final GameGrid shipsGrid;
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
        int length;
        int width;
        int temp;
        for(int i = 0;i<crNumber;i++) {

            do {
               errorExit++;
                targetX = chooseTarget(18);
                targetY = chooseTarget(18);
                if(chooseRotation())
                {
                    length = 3;
                    width=1;
                }else
                {
                    width=3;
                    length = 1;
                }
                if (shipsGrid.isAvailable(targetX, targetY, length, width)) break;
                temp = length;
                length = width;
                width=temp;

                if (errorExit==1000000)return;

            }
            while (!shipsGrid.isAvailable(targetX, targetY, length, width));

            shipsGrid.markShipLocation(targetX, targetY, length, width);
        }

    }

    private boolean chooseRotation()//true - horizontal / false - vertical
    {
        Random random = new Random();
        return random.nextBoolean();
    }
    public GameGrid getGrid()
    {
        return  shipsGrid;
    }

}
