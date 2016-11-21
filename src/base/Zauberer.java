import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Write a description of class Zauberer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Zauberer extends SubFigure {
    private static Zauberer instance;

    protected static Zauberer getInstance () {
        if (Zauberer.instance == null) {
            Zauberer.instance = new Zauberer ();
        }
        return Zauberer.instance;
    }
    /**
     * Act - do whatever the Zauberer wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    private Zauberer(){
        super();
    }

    public void act() {
        if (worldEndFront())
            turnRight();
        move();
        // Add your action code here.
    }




}
