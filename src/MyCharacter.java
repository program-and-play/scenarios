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
public class MyCharacter extends Figure {
    /**
     * Act - do whatever the MyCharacter wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        if (worldEndFront())
            turnRight();
        move();
        // Add your action code here.
    }



    public void createLightBeings(int x, int y) {
        if(getWorld().getWidth() < x || getWorld().getHeight() < y|| x < 0 || y < 0){
            //TODO einen besseren text
            showWarning(
                    "Kara can't move because he can't push the mushroom!",
                    "Kara kann sich nicht bewegen, da er den Pilz nicht schieben kann!");
            return;
        }
        final String BODY_FILE = "character_body.png";
        final String STICK_FILE = "stab_lichtwesenbeschwören.png";
        final String CLOTHES_FILE = "character_kleidung.png";
        GreenfootImage bodyImageForMagic = new GreenfootImage(BODY_FILE);
        bodyImageForMagic.drawImage(new GreenfootImage(CLOTHES_FILE), 0, 0);
        bodyImageForMagic.drawImage(new GreenfootImage(STICK_FILE), 0, 0);
        bodyImageForMagic.scale(240, 240);

        HashMap<Direction, ArrayList<GreenfootImage>> imageContainerForMagic = loadImageForCharacter(bodyImageForMagic, 4, 4);

        for (GreenfootImage img :imageContainerForMagic.get(getCurrentDirection())) {
            setImage(img);
            Greenfoot.delay(2);
        }
        resetImage();
        LightBeings tmp = new LightBeings();
        getWorld().addObject(tmp,x,y);
        tmp.makeAnimation();
    }
}
