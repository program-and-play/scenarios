
import greenfoot.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

class Lichtwesen extends Figur {
    private String[] path = { "lichtwesen_neu_beige.png", "lichtwesen_neu_gelb.png",
            "lichtwesen_neu_lila.png", "lichtwesen_neu_rosa.png", "lichtwesen_neu_rot.png","lichtwesen_neu_blau.png"};

    private ArrayList<GreenfootImage> bodyImages;
    private int colorPointer;

    protected Lichtwesen() {
        super(new GreenfootImage("lichtwesen_neu_blau.png"));
        bodyImages = new ArrayList<GreenfootImage>();
        for (int i = 0; i < path.length; i++) {
            GreenfootImage tmp = new GreenfootImage(path[i]);
            //TODO muss besser gehen die groesse der Pixel zu bekommen
            tmp.scale(60, 60);
            bodyImages.add(tmp);
        }
        colorPointer = 0;
    }

    private ArrayList<GreenfootImage> getAnimationContainer() {
        return loadImageForAnimation(new GreenfootImage("lichtwesen_beschwören.png"), 6);
    }

    protected void makeAnimation() {
        for (GreenfootImage img : getAnimationContainer()) {
            setImage(img);
            Greenfoot.delay(2);
        }
        this.resetImage();
    }

    public void leuteBeigeCappuchino() {
        colorPointer = 0;
        setImage(bodyImages.get(colorPointer));
    }

    public void leuchteGelb() {
        colorPointer = 1;
        setImage(bodyImages.get(colorPointer));

    }

    public void leuteLila() {
        colorPointer = 2;
        setImage(bodyImages.get(colorPointer));
    }

    public void leuteRosa() {
        colorPointer = 3;
        setImage(bodyImages.get(colorPointer));
    }

    public void leuchteRot() {
        colorPointer = 4;
        setImage(bodyImages.get(colorPointer));

    }

    public void leuchteBlau() {
        colorPointer = 5;
        setImage(bodyImages.get(colorPointer));

    }

    public void wechselFarbe() {
        if (colorPointer >= bodyImages.size() - 1)
            colorPointer = -1;
        setImage(bodyImages.get(++colorPointer));
    }

}
