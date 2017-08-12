import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import interfaces.*;
import util.WeltSetup;

import java.util.*;
import java.util.function.Consumer;


public final class Geysir extends Figur implements Animation {
    private HashMap<Direction, ArrayList<GreenfootImage>> animation;
    
    private int index = 0;
    
    
    public Geysir(WeltSetup.ActorPosition startPosition) {
        super(FigurTyp.Gysir, startPosition);
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
    
    @Override
    public void reset() {
        resetImage();
    }
    
    @Override
    public void setNext() {
        index = index == 1 ? 0 : 1;
        setImage(animation.get(getCurrentDirection()).get(index));
        Greenfoot.delay(2);
    }
}
