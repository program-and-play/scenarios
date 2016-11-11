import greenfoot.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


/**
 * Write a description of class LightBeings here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */


class LightBeings extends Figure {

    private ArrayList<GreenfootImage> bodyImages;
    private int colorPointer;
    protected LightBeings() {
        super(new GreenfootImage("lichtwesen_blau_rund.png"),4,4);

        bodyImages = new ArrayList<GreenfootImage>();
        bodyImages.add(new GreenfootImage("lichtwesen_blau_rund.png"));
        bodyImages.add(new GreenfootImage("lichtwesen_gelb_rund.png"));
        bodyImages.add(new GreenfootImage("lichtwesen_rosa_rund.png"));
        colorPointer = 0;
    }

    private ArrayList<GreenfootImage> getAnimationContainer() {
        return loadImageForAnimation(new GreenfootImage("lichtwesen_beschwören.png"),6);
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
        //getWorld().createFieldBackground();
    }

    protected void makeAnimation() {
        for (GreenfootImage img : getAnimationContainer()) {
            setImage(img);
            Greenfoot.delay(2);
        }
        this.resetImage();
    }

    public void leuchteRot(){
        colorPointer = 2;
        changeImage(bodyImages.get(colorPointer),4,4);

    }

    public void leuteLila(){
        colorPointer = 1;
        changeImage(bodyImages.get(colorPointer),4,4);
    }

    public void leuteBeigeCappuchino(){
        colorPointer = 0;
        changeImage(bodyImages.get(colorPointer),4,4);
    }

    public void wechselFarbe(){
        if(colorPointer >= bodyImages.size()-1)
            colorPointer = -1;
        changeImage(bodyImages.get(++colorPointer),4,4);
    }

}
