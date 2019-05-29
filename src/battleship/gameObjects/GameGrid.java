package battleship.gameObjects;

import java.util.Vector;

public class GameGrid {


    private static final int GRIDSIZE = 20;
    private static final int EMPTY = -1;
    private final int[][] gridTable;
    private int id = 0;
    private int shipsNumber = 0;
    private Vector<Ship> shipsVector = new Vector<>();
    public GameGrid() {
        gridTable = new int[GRIDSIZE][GRIDSIZE];
        for (int i = 0; i < GRIDSIZE; i++) {
            for (int j = 0; j < GRIDSIZE; j++) {
                gridTable[i][j] = EMPTY;
            }
        }

    }


    public boolean isHit(int x, int y) {//- 2
        int shipId = gridTable[x][y];
        if (shipId != EMPTY) {
            shipsVector.get(shipId).wasHit();
            return true;
        } else return false;

    }
    public boolean isDestroyed(int x,int y)
    {
        int shipId = gridTable[x][y];
        return shipsVector.get(shipId).isDestroyed();
    }

    public int destroyedShipSize(int x, int y) {
        int shipId = gridTable[x][y];
        return shipsVector.get(shipId).getSize();
    }
    public int[] wasDestroyed(int x,int y)
    {
        int shipId = gridTable[x][y];
        int[] returnArray = new int[4];
        returnArray[0]=shipsVector.get(shipId).getxHead();
        returnArray[1]=shipsVector.get(shipId).getyHead();
        returnArray[2]=shipsVector.get(shipId).getLength();
        returnArray[3]=shipsVector.get(shipId).getWidth();
        return returnArray;
    }

    public boolean isAvailable(int x, int y, int length, int width) {



        if (x + length > 20 || y + width > 20) return false;
        if (x + length == 20) {
            --length;
        }
        if (y + width == 20) {
            --width;
        }
        int i = -1;
        if (x == 0) ++i;
        for ( ; i <= length; i++) //checking if row for ship is empty on chosen location on gridTable
        {

            int j = -1;
            if (y == 0) ++j;
            for (; j <= width; j++) //checking if column for ship is empty on chosen location on gridTable
            {
                if (gridTable[x + i][y + j] != EMPTY) return false;
            }
        }

        return true;
    }

    private void markShipLocation(int x, int y, int length, int width) {

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                gridTable[x + i][y + j] = id;
            }
        }
        ++id;
    }

    public void addShip(int x, int y, int length, int width) {
        shipsVector.addElement(new Ship(x,y,length, width));
        markShipLocation(x, y, length, width);

    }

    public int[][] getGridTable() {
        return gridTable;
    }
}