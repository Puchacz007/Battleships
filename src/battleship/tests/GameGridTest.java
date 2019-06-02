package battleship.tests;

import battleship.gameObjects.GameGrid;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameGridTest {
    private static final int GRIDSIZE = 20;

    @Test
    void isHit() {
        GameGrid gameGrid = new GameGrid();
        gameGrid.addShip(5, 6, 3, 1);
        assertTrue(gameGrid.isHit(5, 6, false));
        assertTrue(gameGrid.isHit(6, 6, false));
        assertTrue(gameGrid.isHit(7, 6, false));

        assertFalse(gameGrid.isHit(8, 6, false));
        assertFalse(gameGrid.isHit(4, 6, false));

        assertFalse(gameGrid.isHit(5, 7, false));
        assertFalse(gameGrid.isHit(6, 7, false));
        assertFalse(gameGrid.isHit(7, 7, false));

        assertFalse(gameGrid.isHit(5, 8, false));
        assertFalse(gameGrid.isHit(6, 8, false));
        assertFalse(gameGrid.isHit(7, 8, false));


        assertFalse(gameGrid.isHit(0, 6, false));
        assertFalse(gameGrid.isHit(0, 0, false));
        assertFalse(gameGrid.isHit(2, 2, false));
    }

    @Test
    void isHitRandom() {
        for (int i = 0; i < 10000; i++) {
            GameGrid gameGrid = new GameGrid();
            int x = new Random().nextInt(GRIDSIZE - 1);
            int y = new Random().nextInt(GRIDSIZE - 1);
            int length = new Random().nextInt(4);
            if (length == 0) ++length;
            while (GRIDSIZE - x < length) --length;
            int width = 1;
            if (length == 3) {
                width = new Random().nextInt(1);
                ++width;
                while (GRIDSIZE - y < width) --width;
                break;
            }
            gameGrid.addShip(x, y, length, width);

            for (int k = x; k < length + x; k++) {
                for (int j = y; j < width + y; j++) {
                    assertTrue(gameGrid.isHit(k, y, false));
                }
            }
        }

    }

    @Test
    void isDestroyed() {
        GameGrid gameGrid = new GameGrid();
        gameGrid.addShip(5, 6, 3, 1);
        assertTrue(gameGrid.isHit(5, 6, true));
        assertTrue(gameGrid.isHit(6, 6, true));
        assertTrue(gameGrid.isHit(7, 6, true));

        assertTrue(gameGrid.isDestroyed(5, 6));
        assertTrue(gameGrid.isDestroyed(6, 6));
        assertTrue(gameGrid.isDestroyed(7, 6));

        assertFalse(gameGrid.isDestroyed(8, 6));
        assertFalse(gameGrid.isDestroyed(4, 6));

        assertFalse(gameGrid.isDestroyed(5, 7));
        assertFalse(gameGrid.isDestroyed(6, 7));
        assertFalse(gameGrid.isDestroyed(7, 7));

        assertFalse(gameGrid.isDestroyed(5, 8));
        assertFalse(gameGrid.isDestroyed(6, 8));
        assertFalse(gameGrid.isDestroyed(7, 8));


        assertFalse(gameGrid.isDestroyed(0, 6));
        assertFalse(gameGrid.isDestroyed(0, 0));
        assertFalse(gameGrid.isDestroyed(2, 2));
    }

    @Test
    void destroyedShipSize() {
        GameGrid gameGrid = new GameGrid();
        gameGrid.addShip(5, 6, 3, 1);
        assertEquals(gameGrid.destroyedShipSize(5, 6), 3);
    }

    @Test
    void wasDestroyed() {
        GameGrid gameGrid = new GameGrid();
        gameGrid.addShip(5, 6, 3, 1);

        int[] returnArray = gameGrid.wasDestroyed(5, 6);

        assertEquals(returnArray[0], 5);
        assertEquals(returnArray[1], 6);
        assertEquals(returnArray[2], 3);
        assertEquals(returnArray[3], 1);
    }

    @Test
    void isAvailable() {
        GameGrid gameGrid = new GameGrid();
        gameGrid.addShip(5, 6, 3, 1);
        gameGrid.addShip(10, 11, 1, 3);
        assertFalse(gameGrid.isAvailable(4, 6, 3, 1));
        assertFalse(gameGrid.isAvailable(5, 6, 3, 1));
        assertFalse(gameGrid.isAvailable(6, 6, 3, 1));
        assertFalse(gameGrid.isAvailable(7, 6, 3, 1));
        assertFalse(gameGrid.isAvailable(8, 6, 3, 1));

        assertFalse(gameGrid.isAvailable(4, 7, 3, 1));
        assertFalse(gameGrid.isAvailable(5, 7, 3, 1));
        assertFalse(gameGrid.isAvailable(6, 7, 3, 1));
        assertFalse(gameGrid.isAvailable(7, 7, 3, 1));
        assertFalse(gameGrid.isAvailable(8, 7, 3, 1));

        assertFalse(gameGrid.isAvailable(4, 5, 3, 1));
        assertFalse(gameGrid.isAvailable(5, 5, 3, 1));
        assertFalse(gameGrid.isAvailable(6, 5, 3, 1));
        assertFalse(gameGrid.isAvailable(7, 5, 3, 1));
        assertFalse(gameGrid.isAvailable(8, 5, 3, 1));

        assertTrue(gameGrid.isAvailable(4, 4, 3, 1));
        assertTrue(gameGrid.isAvailable(5, 4, 3, 1));
        assertTrue(gameGrid.isAvailable(6, 4, 3, 1));
        assertTrue(gameGrid.isAvailable(7, 4, 3, 1));
        assertTrue(gameGrid.isAvailable(8, 4, 3, 1));


    }

    @Test
    void isAvailableRandom() {
        for (int i = 0; i < 10000; i++) {
            GameGrid gameGrid = new GameGrid();
            int x = new Random().nextInt(GRIDSIZE - 1);
            int y = new Random().nextInt(GRIDSIZE - 1);
            int length = new Random().nextInt(4);
            if (length == 0) ++length;
            while (GRIDSIZE - x < length) --length;
            int width = 1;
            if (length == 3) {
                width = new Random().nextInt(1);
                ++width;
                while (GRIDSIZE - y < width) --width;
                break;
            }
            gameGrid.addShip(x, y, length, width);

            for (int k = x - 1; k < length + x + 1; k++) {
                for (int j = y - 1; j < width + y + 1; j++) {
                    assertFalse(gameGrid.isAvailable(x, y, length, width));
                }
            }
        }
    }

    @Test
    void addShip() {
        GameGrid gameGrid = new GameGrid();
        gameGrid.addShip(5, 6, 3, 1);
        gameGrid.addShip(10, 11, 1, 3);
        int[][] table = gameGrid.getGridTable();
        assertEquals(table[5][6], 0);
        assertEquals(table[6][6], 0);
        assertEquals(table[7][6], 0);

        assertEquals(table[10][11], 1);
        assertEquals(table[10][12], 1);
        assertEquals(table[10][13], 1);


    }

    @Test
    void addShipRandom() {
        for (int i = 0; i < 10000; i++) {
            GameGrid gameGrid = new GameGrid();
            int x = new Random().nextInt(GRIDSIZE - 1);
            int y = new Random().nextInt(GRIDSIZE - 1);
            int length = new Random().nextInt(4);
            if (length == 0) ++length;
            while (GRIDSIZE - x < length) --length;
            int width = 1;
            if (length == 3) {
                width = new Random().nextInt(1);
                ++width;
                while (GRIDSIZE - y < width) --width;
                break;
            }
            gameGrid.addShip(x, y, length, width);
            int[][] table = gameGrid.getGridTable();

            for (int k = x; k < length + x; k++) {
                for (int j = y; j < width + y; j++) {
                    assertEquals(table[k][j], 0);
                }
            }
        }
    }
}