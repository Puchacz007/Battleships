package battleship.gameObjects;

import java.awt.*;
import java.util.Random;
import java.util.Vector;

public class AI {
    private static final int GRIDSIZE = 20;
    private static final int MAXSHIPSLENGHT = 4;
    private static final int MAXSHIPSWIDTH = 2;
    private final GameGrid shipsGrid;
    private boolean lastShotHit = false;
    private Point lastShot;
    private Vector<Point> hits;
    private Vector<Point> miss;
    public AI()
    {
        hits = new Vector<>();
        shipsGrid = new GameGrid();
    }
    public int chooseTarget(int bound)
    {
        Random random = new Random();
      return   random.nextInt(bound);
    }

    public Point chooseTarget(int bound, boolean wasHit, boolean wasDestroyed) {
        int x, y;
        boolean alreadyShoot = false;
        if (wasDestroyed) {
            hits.removeAllElements();

        } else if (wasHit) {

            hits.addElement(lastShot);
            if (hits.size() == 1) {
                do {
                    Random random = new Random();
                    x = random.nextInt(4);
                    random = new Random();
                    y = random.nextInt(4);

                    x += lastShot.getX();
                    y += lastShot.getY();
                    lastShot.setLocation(x, y);
                    for (int i = 0; i < miss.size(); i++) {
                        if (miss.get(i) == lastShot) {
                            alreadyShoot = true;
                            break;
                        }
                        if (i == miss.size() - 1) alreadyShoot = false;
                    }

                } while (alreadyShoot);

            } else {
                int diffX = 0, diffY = 0;
                for (int i = 0; i < hits.size() - 1; i++) {
                    if (hits.get(i).getX() != hits.get(i + 1).getX()) {
                        ++diffX;
                    }
                    if (hits.get(i).getY() != hits.get(i + 1).getY()) {
                        ++diffY;
                    }
                }
            }
        } else if (hits.isEmpty()) {
            Random random = new Random();
            x = random.nextInt(bound);
            random = new Random();
            y = random.nextInt(bound);
            lastShot.setLocation(x, y);
        } else {

        }


        return lastShot;
    }
  /*  public int chooseTargetY()
    {
        Random random = new Random();
        return   random.nextInt(20);
    }*/
  public void setUpShips(int prNumber, int subNumber, int crNumber, int carrNumber, int capNumber)
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

            shipsGrid.addShip(targetX, targetY, length, width);

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
