package battleship.gameObjects;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

public class AI implements Serializable {
    private static final int GRIDSIZE = 20;
    int targetLength = 0, targetWidth = 0;
    //  boolean direction = false; //false change length,true change width
    private final GameGrid shipsGrid;
    // private int maxShipLength = 3;
    //   private int maxShipWidth = 2;
    private boolean targetFound = false;
    private Point lastShot, potentialHit;
    private Vector<Point> potentialHits;
    private Vector<Point> hits;
    public AI()
    {
        potentialHits = new Vector<>();
        hits = new Vector<>();
        shipsGrid = new GameGrid();
        lastShot = new Point();

    }

    public int randomInt(int bound)
    {
        Random random = new Random();
      return   random.nextInt(bound);
    }

    public Point chooseTarget(boolean wasHit, boolean wasDestroyed, boolean[][] alreadyShoot, int maxShipLength, int maxShipWidth) {
        int x, y, number;
        if (wasDestroyed) { //last shoot destroyed targeted ship
            potentialHits.removeAllElements();
            hits.removeAllElements();
            targetFound = false;
            targetLength = 0;
            targetWidth = 0;
            do {
                x = randomInt(GRIDSIZE);
                y = randomInt(GRIDSIZE);
            } while (alreadyShoot[x][y]);
            lastShot.setLocation(x, y);
            return lastShot;

        }
        if (!wasHit && potentialHits.isEmpty() && !targetFound) // last shoot was miss and there is no target
        {
            do {
                x = randomInt(GRIDSIZE);
                y = randomInt(GRIDSIZE);
            } while (alreadyShoot[x][y]);
            lastShot.setLocation(x, y);


            return lastShot;
        }
        if (wasHit && potentialHits.isEmpty() && !targetFound) //first shoot was a hit
        {
            targetFound = true;
            x = (int) lastShot.getX();
            y = (int) lastShot.getY();
            potentialHit = new Point(x + 1, y);
            if (x + 1 < GRIDSIZE && !alreadyShoot[x + 1][y]) {

                potentialHits.addElement(potentialHit);
            }
            potentialHit = new Point(x - 1, y);
            if (x - 1 >= 0 && !alreadyShoot[x + -1][y]) {

                potentialHits.addElement(potentialHit);
            }
            potentialHit = new Point(x, y + 1);
            if (y + 1 < GRIDSIZE && !alreadyShoot[x][y + 1]) {


                potentialHits.addElement(potentialHit);
            }
            potentialHit = new Point(x, y - 1);
            if (y - 1 >= 0 && !alreadyShoot[x][y - 1]) {

                potentialHits.addElement(potentialHit);
            }
            hits.addElement(lastShot);
            targetLength = 1;
            targetWidth = 1;
            if (potentialHits.size() != 1) number = randomInt(potentialHits.size() - 1);
            else number = 0;
            lastShot = potentialHits.get(number);
            //   if (x != lastShot.getX()) direction = false;
            //  if (y != lastShot.getY()) direction = true;
            return lastShot;
        }
        if (!wasHit && !potentialHits.isEmpty() && targetFound)//last shoot was a miss but there is a target
        {
            potentialHits.remove(lastShot);
            if (potentialHits.size() != 1) number = randomInt(potentialHits.size() - 1);
            else number = 0;
            lastShot = potentialHits.get(number);
            return lastShot;
        }

        if (wasHit && !potentialHits.isEmpty() && targetFound) //last shoot was a hit and there is a target
        {
            hits.addElement(lastShot);
            x = (int) lastShot.getX();
            y = (int) lastShot.getY();
            Vector<Integer> yVector = new Vector<>(maxShipWidth);
            for (Point hit : hits) {
                number = (int) hit.getY();
                if (!yVector.contains(number)) yVector.add(number);
            }
            Vector<Integer> xVector = new Vector<>(maxShipLength);
            for (Point hit : hits) {
                number = (int) hit.getX();
                if (!xVector.contains(number)) xVector.add(number);
            }
            targetLength = xVector.size();
            targetWidth = yVector.size();

            // if (direction) ++targetWidth;
            // else ++targetLength;

            potentialHit = new Point(x + 1, y);
            if (!potentialHits.contains(potentialHit) && !hits.contains(potentialHit) && x + 1 < GRIDSIZE && !alreadyShoot[x + 1][y]) {

                potentialHits.addElement(potentialHit);
            }
            potentialHit = new Point(x - 1, y);
            if (!potentialHits.contains(potentialHit) && !hits.contains(potentialHit) && x - 1 >= 0 && !alreadyShoot[x - 1][y]) {

                potentialHits.addElement(potentialHit);
            }
            potentialHit = new Point(x, y + 1);
            if (!potentialHits.contains(potentialHit) && !hits.contains(potentialHit) && y + 1 < GRIDSIZE && !alreadyShoot[x][y + 1]) {

                potentialHits.addElement(potentialHit);
            }
            potentialHit = new Point(x, y - 1);
            if (!potentialHits.contains(potentialHit) && !hits.contains(potentialHit) && y - 1 >= 0 && !alreadyShoot[x][y - 1]) {

                potentialHits.addElement(potentialHit);
            }
            potentialHits.remove(lastShot);

            if (maxShipWidth == targetWidth && targetLength > maxShipWidth) {


                for (int i = 0; i < potentialHits.size(); i++) {
                    number = (int) potentialHits.get(i).getY();
                    if (!yVector.contains(number)) {
                        potentialHits.remove(i);
                        i = 0;
                    }
                }
            }
            if (maxShipLength == targetLength && targetWidth > maxShipLength) {

                for (int i = 0; i < potentialHits.size(); i++) {
                    number = (int) potentialHits.get(i).getY();
                    if (!xVector.contains(number)) {
                        potentialHits.remove(i);
                        i = 0;
                    }
                }
            }

            if (potentialHits.size() != 1) number = randomInt(potentialHits.size() - 1);
            else number = 0;
            lastShot = potentialHits.get(number);

            //    if (x != lastShot.getX()) direction = false;
            //     if (y != lastShot.getY()) direction = true;

            return lastShot;

        }


        return lastShot;
    }

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

                if (!chooseRotation()) {
                    temp = width;
                    width = length;
                    length = temp;
                }
                targetX = randomInt(GRIDSIZE - length);
                targetY = randomInt(GRIDSIZE - width);
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
