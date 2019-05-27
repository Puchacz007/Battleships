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

    public int randomInt(int bound)
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

                    x = randomInt(4);

                    y = randomInt(4);

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
            x = randomInt(bound);
            y = randomInt(bound);
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
        int length = -1;
        int width = -1;
        int temp;
        int size = prNumber + subNumber + crNumber + carrNumber + capNumber;
        int shipType = -1;//-1=no ship,0=patrol,1=sub,2=cruiser,3=carrier,4=capital


        while (size > 0) {
            int bound = 0;
            if (prNumber > 0) ++bound;
            if (subNumber > 0) ++bound;
            if (crNumber > 0) ++bound;
            if (carrNumber > 0) ++bound;
            if (capNumber > 0) ++bound;
            shipType = randomInt(bound);

            switch (shipType) {
                case 0:
                    length = 1;
                    width = 1;

                    if (prNumber > 0) {
                        --prNumber;
                        break;
                    }
                case 1:
                    length = 2;
                    width = 1;
                    if (subNumber > 0) {
                        --subNumber;
                        break;
                    }
                case 2:
                    length = 3;
                    width=1;
                    if (crNumber > 0) {
                        --crNumber;
                        break;
                    }
                case 3:
                    length = 3;
                    width = 2;
                    if (carrNumber > 0) {
                        --carrNumber;
                        break;
                    }
                case 4:
                    length = 4;
                    width = 1;
                    if (capNumber > 0) {
                        --capNumber;
                        break;
                    }
                    break;
            }
            //   for(int i = 0;i<crNumber;i++) {

            do {
                errorExit++;
                targetX = randomInt(18);
                targetY = randomInt(18);
                if (!chooseRotation()) {
                    temp = width;
                    width = length;
                    length = temp;
                }
                if (shipsGrid.isAvailable(targetX, targetY, length, width)) break;
                temp = length;
                length = width;
                width = temp;

                if (errorExit == 1000000) return;

            }
            while (!shipsGrid.isAvailable(targetX, targetY, length, width));

            shipsGrid.addShip(targetX, targetY, length, width);
            --size;
        }
        //}

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
