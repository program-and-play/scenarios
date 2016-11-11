import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Write a description of class MyCharacter here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MyCharacter extends SubFigure {
    private static MyCharacter instance;

    protected static MyCharacter getInstance () {
        if (MyCharacter.instance == null) {
            MyCharacter.instance = new MyCharacter ();
        }
        return MyCharacter.instance;
    }
    /**
     * Act - do whatever the MyCharacter wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    private MyCharacter(){
        super();
    }

    public void act() {
        if (worldEndFront())
            turnRight();
        move();
        // Add your action code here.
    }




}
