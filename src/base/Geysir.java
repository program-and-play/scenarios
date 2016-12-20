import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import interfaces.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Write a description of class Geysir here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Geysir extends Figur implements Animation {
    private HashMap<Direction, ArrayList<GreenfootImage>> animation;

    public Geysir() {
        super();
        animation = loadImage(new GreenfootImage("geysir_animation.png"), 2, 1);
    }

    @Override
    public void makeAnimation() {
        for (int i = 0; i < 2; i++) {
            for (GreenfootImage img : animation.get(getCurrentDirection())) {
                setImage(img);
                Greenfoot.delay(2);
            }
        }
        this.resetImage();
    }


}
