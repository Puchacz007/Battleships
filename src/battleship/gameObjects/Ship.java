package battleship.gameObjects;

public class Ship {


    private final int length;
    private final int width;

    public Ship(int l, int w)
    {
        length = l;
        width = w;
        hitpoints = length * width;
    }



    private int hitpoints;

    public boolean isDestroyed()
{
    return hitpoints <= 0;
}

    public void wasHit()
    {
        --hitpoints;
    }
}
