import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import interfaces.*;
import util.WeltSetup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public final class Geysir extends Figur implements Animation {
    private HashMap<Direction, ArrayList<GreenfootImage>> animation;

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


}
