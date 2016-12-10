import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import interfaces.*;

import java.util.List;

/**
 * Write a description of class Geysir here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Geysir extends Figur implements Animation {
    private List<GreenfootImage> list;
    public Geysir(){
        super();
        list =  getAnimationContainer();
    }

    @Override
    public void updateImage() {
        makeAnimation();
    }

    @Override
    public void makeAnimation() {
        for (int i= 0; i< 2; i++)
        for (GreenfootImage img : getAnimationContainer()) {
            setImage(img);
            Greenfoot.delay(2);
        }
        this.resetImage();
    }

    private List<GreenfootImage> getAnimationContainer() {
        return loadImageForAnimation(new GreenfootImage("geysir_animation.png"), 2);
    }

}
