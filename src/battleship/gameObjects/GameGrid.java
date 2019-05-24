package battleship.gameObjects;

import java.util.Vector;

public class GameGrid {


    private static final int GRIDSIZE = 20;
    private static final int EMPTY = -1;
    private static final int MISS = -2;
    private final int[][] gridTable;
    private int id = 0;
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
        shipsVector.addElement(new Ship(length, width));
        markShipLocation(x, y, length, width);
    }
}