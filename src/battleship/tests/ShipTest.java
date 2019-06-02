package battleship.tests;

import battleship.gameObjects.Ship;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShipTest {

    @Test
    void getSize() {
        Ship ship = new Ship(5, 6, 3, 2);
        assertEquals(ship.getSize(), 6);
    }

    @Test
    void getLength() {
        Ship ship = new Ship(5, 6, 3, 2);
        assertEquals(ship.getLength(), 3);
    }

    @Test
    void getWidth() {
        Ship ship = new Ship(5, 6, 3, 2);
        assertEquals(ship.getWidth(), 2);
    }

    @Test
    void getxHead() {
        Ship ship = new Ship(5, 6, 3, 2);
        assertEquals(ship.getxHead(), 5);
    }

    @Test
    void getyHead() {
        Ship ship = new Ship(5, 6, 3, 2);
        assertEquals(ship.getyHead(), 6);
    }

    @Test
    void isDestroyed() {
        Ship ship = new Ship(7, 2, 2, 1);
        ship.wasHit();
        ship.wasHit();
        assertTrue(ship.isDestroyed());
    }


}