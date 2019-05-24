package battleship.gameObjects;

public class Ship {


    private final int length;
    private final int width;
    private  final  int size;
    private  final int xHead;
    private  final int yHead;
    public Ship(int x,int y,int l, int w)
    {
        length = l;
        width = w;
        hitpoints = length * width;
        size = hitpoints;
        xHead=x;
        yHead=y;
    }

    public int getSize() {
        return size;
    }

    public  int getLength()
    {
        return length;
    }
    public int getWidth()
    {
        return width;
    }

    public int getxHead() {
        return xHead;
    }

    public int getyHead() {
        return yHead;
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
