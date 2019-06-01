package battleship.gameObjects;

import java.io.Serializable;

public class GameSave implements Serializable {


    private GameGrid computerTargetGrid;
    private AI computer;
    private boolean[][] playerAlreadyShoot;
    private boolean[][] computerAlreadyShoot;
    private boolean wasDestroyed, wasHit;
    private float saveTime;
    private int numberOfPlayerShoots;
    private int numberOfHits;
    private int playerShipsNumber;
    private int[] playerShipsTypeNumber;
    private int computerShipsNumber;

    public GameSave(GameGrid gameGrid, AI ai, boolean[][] playerShots, boolean[][] computerShoots, boolean destroy, boolean hit, float time,
                    int numberOfShoots, int numberOfPlayerHits, int plShipsNr, int aiShipsNr, int[] playerShipsNumberByType) {
        computerTargetGrid = gameGrid;
        computer = ai;
        playerAlreadyShoot = playerShots;
        computerAlreadyShoot = computerShoots;
        wasDestroyed = destroy;
        wasHit = hit;
        saveTime = time;
        numberOfPlayerShoots = numberOfShoots;
        numberOfHits = numberOfPlayerHits;
        playerShipsNumber = plShipsNr;
        computerShipsNumber = aiShipsNr;
        playerShipsTypeNumber = playerShipsNumberByType;
    }

    public AI getComputer() {
        return computer;
    }

    public boolean[][] getComputerAlreadyShoot() {
        return computerAlreadyShoot;
    }

    public boolean[][] getPlayerAlreadyShoot() {
        return playerAlreadyShoot;
    }

    public GameGrid getComputerTargetGrid() {
        return computerTargetGrid;
    }

    public int getComputerShipsNumber() {
        return computerShipsNumber;
    }

    public int getNumberOfHits() {
        return numberOfHits;
    }

    public int getNumberOfPlayerShoots() {
        return numberOfPlayerShoots;
    }

    public int getPlayerShipsNumber() {
        return playerShipsNumber;
    }

    public int[] getPlayerShipsTypeNumber() {
        return playerShipsTypeNumber;
    }

    public float getSaveTime() {
        return saveTime;
    }

    public boolean getWasDestroyed() {
        return wasDestroyed;
    }

    public boolean getWasHit() {
        return wasHit;
    }
}
