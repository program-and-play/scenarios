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
    public static final String BODY_FILE_BLAU = "lichtwesen_blau_rund.png";
    public static final String BODY_FILE_GELB = "lichtwesen_gelb_rund.png";
    public static final String BODY_FILE_ROSA = "lichtwesen_rosa_rund.png";

    public LightBeings() {
        super(new GreenfootImage(BODY_FILE_BLAU),4,4);
    }

    /**
     * Act - do whatever the LightBeings wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {

    }

    public ArrayList<GreenfootImage> getAnimationContainer() {
        return loadImageForAnimation(new GreenfootImage("lichtwesen_beschwören.png"),6);
    }

    public void changeColor(String color) {
        switch (color) {
            case "BLUE":
                changeImage(BODY_FILE_BLAU);
                break;
            case "YELLOW":
                changeImage(BODY_FILE_GELB);
                break;
            case "RED":
                changeImage(BODY_FILE_ROSA);
                break;
        }

    }

    public void makeAnimation() {
        for (GreenfootImage img : getAnimationContainer()) {
            setImage(img);
            Greenfoot.delay(2);
        }
        this.resetImage();
    }

    public ArrayList<GreenfootImage> loadImageForAnimation(GreenfootImage bodyImage, int numOfCellHorizontal) {
        //TODO hier ein richtigen Import von der Cellsize der World
        int PIXEL = 60;
        GreenfootImage loaded = bodyImage;
        loaded.scale(PIXEL * numOfCellHorizontal, PIXEL);
        BufferedImage loadedBuf = loaded.getAwtImage();

            ArrayList<GreenfootImage> animation = new ArrayList<GreenfootImage>();
            for (int j = 0; j < loaded.getWidth(); j = j + PIXEL) {

                BufferedImage bufImage = loadedBuf.getSubimage(j, 0, PIXEL, PIXEL);
                GreenfootImage gImage = new GreenfootImage(bufImage.getWidth(), bufImage.getHeight());
                BufferedImage gBufImg = gImage.getAwtImage();
                Graphics2D graphics = (Graphics2D) gBufImg.getGraphics();
                graphics.drawImage(bufImage, null, 0, 0);

                animation.add(gImage);
            }


        return animation;
    }



}
