package battleship;

public class GameGrid {

    private boolean[][] gridTable;

    GameGrid() {
        gridTable = new boolean[20][20];

    }


    public boolean isHit(int x, int y) {
        return gridTable[x][y];
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
                if (gridTable[x + i][y + j]) return false;
            }
        }

        return true;
    }

    public void markShipLocation(int x, int y, int length, int width) {

        for (int i = 0; i < length; i++)
        {
            for (int j = 0; j < width; j++)
            {
                gridTable[x+i][y+j]=true;
            }
        }
    }
}