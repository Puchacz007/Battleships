package battleship.tests;

import battleship.gameObjects.AI;

import java.awt.*;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AITest {

    private static final int GRIDSIZE = 20;

    @org.junit.jupiter.api.Test
    void chooseTarget() {
        AI ai = new AI();
        for (int l = 0; l < 1000; l++) {


            boolean[][] alreadyShoot = new boolean[GRIDSIZE][GRIDSIZE];
            Point point = ai.chooseTarget(false, false, alreadyShoot, 3, 2);
            alreadyShoot[(int) point.getX()][(int) point.getY()] = true;
            boolean test;
            int x = (int) point.getX();
            int y = (int) point.getY();
            test = x >= 0 && x < GRIDSIZE && y >= 0 && y < GRIDSIZE;
            assertTrue(test);
        }

    }

    @org.junit.jupiter.api.Test
    void setUpShips() {
        for (int l = 0; l < 1000; l++) {
            AI ai = new AI();
            ai.setUpShips(0, 0, 0, 20, 0);
            int[][] returnTable = ai.getGrid().getGridTable();
            int[] testArray = new int[20];
            for (int i = 0; i < GRIDSIZE; i++) {

                for (int j = 0; j < GRIDSIZE; j++) {
                    for (int k = 0; k < GRIDSIZE; k++) {
                        if (returnTable[i][j] == k) ++testArray[k];
                    }
                }
            }
            for (int i = 0; i < GRIDSIZE; i++) {
                assertEquals(testArray[i], 6);
            }
        }
    }

    @org.junit.jupiter.api.Test
    void setUpShipsRandom() {
        for (int l = 0; l < 1000; l++) {
            int prNumber = new Random().nextInt(GRIDSIZE);
            int subNumber = new Random().nextInt(GRIDSIZE - prNumber);
            int crNumber = new Random().nextInt(GRIDSIZE - prNumber - subNumber);
            int carrNumber = new Random().nextInt(GRIDSIZE - prNumber - subNumber - crNumber);
            int capNumber = GRIDSIZE - prNumber - subNumber - crNumber - carrNumber;
            AI ai = new AI();
            ai.setUpShips(prNumber, subNumber, crNumber, carrNumber, capNumber);
            int[][] returnTable = ai.getGrid().getGridTable();
            int[] testArray = new int[20];
            for (int i = 0; i < GRIDSIZE; i++) {

                for (int j = 0; j < GRIDSIZE; j++) {
                    for (int k = 0; k < GRIDSIZE; k++) {
                        if (returnTable[i][j] == k) ++testArray[k];
                    }
                }
            }
            int gridPrNUmber = 0, gridSubNumber = 0, gridCruiserNumber = 0, gridCarrierNumber = 0, gridCapitalNumber = 0;
            for (int i = 0; i < GRIDSIZE; i++) {
                switch (testArray[i]) {
                    case 1:
                        ++gridPrNUmber;
                        break;
                    case 2:
                        ++gridSubNumber;
                        break;
                    case 3:
                        ++gridCruiserNumber;
                        break;
                    case 4:
                        ++gridCapitalNumber;
                        break;
                    case 6:
                        ++gridCarrierNumber;
                        break;
                }
            }
            assertEquals(prNumber, gridPrNUmber);
            assertEquals(subNumber, gridSubNumber);
            assertEquals(crNumber, gridCruiserNumber);
            assertEquals(capNumber, gridCapitalNumber);
            assertEquals(carrNumber, gridCarrierNumber);
        }
    }

}
