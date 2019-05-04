package battleship;

import java.util.Vector;

public class Ship {


    Ship(int l,int w,int x,int y)
    {
    lenght = l;
    width= w ;
    xHeadLocation = x;
    yHeadLocation = y;
    hitpoints=lenght*width;
    }
    int lenght, width;
    int xHeadLocation,yHeadLocation;

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
