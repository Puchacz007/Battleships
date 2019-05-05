package battleship;

class Ship {


    Ship(int l,int w,int x,int y)
    {
        length = l;
    width= w ;
    xHeadLocation = x;
    yHeadLocation = y;
        hitpoints = length * width;
    }

    final int length;
    final int width;
    final int xHeadLocation;
    final int yHeadLocation;

    private int hitpoints;


public  boolean isDestroyed()
{
    return hitpoints <= 0;
}
public void isHit()
    {
        hitpoints--;
    }
}
