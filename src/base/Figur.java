 

 

import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import util.DialogUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * DIRECTION_RIGHT is the original direction - do nothing
 * <p>
 * This is the superclass for all Karas containing the basic Kara methods.
 * Programs should only be written in subclasses (e.g. MyKara).
 * <p>
 * <p>
 * <i>Diese Klasse ist die Oberklasse fuer alle Karas und enthaelt die
 * Grundfunktionen von Kara. Programme sollten nur in den Unterklassen wie
 * MyKara geschrieben werden.</i>
 *
 * @author Marco Jakob (http://code.makery.ch)
 */
class Figur extends Actor {

    private int imagePointer;

    private Direction currentDirection = Direction.RIGHT;

    private HashMap<Direction, ArrayList<GreenfootImage>> imageContainer;

    protected Figur(GreenfootImage image) {
        imageContainer = loadImageForCharacter(image);
        resetImage();
    }

    protected Figur(GreenfootImage image, int sceneX, int sceneY) {
        imageContainer = loadImageForCharacter(image, sceneX, sceneY);
        resetImage();
    }

    protected Figur() {
        imageContainer = loadImageForCharacter(getImage());
        resetImage();
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction direction) {
        currentDirection = direction;
    }

    public LeereWelt getWorld() {
        return (LeereWelt) super.getWorld();
    }

    @Override
    public int getX() {
        return super.getX() - getWorld().getSetup().getOffsetStartToX();
    }

    @Override
    public int getY() {
        return super.getY() - getWorld().getSetup().getOffsetStartToY();
    }

    public HashMap<Direction, ArrayList<GreenfootImage>> getImageContainer() {
        return imageContainer;
    }

    protected boolean theWorldsEnd(Direction direction, int steps, Actor actor) {
        int x = actor.getX();
        int y = actor.getY();
        System.out.println("Actor X " + x + " Actor y " + y);

        switch (direction) {
            case RIGHT:         // java
                return x + steps >= getWorld().getSetup().getWidth();

            case DOWN:         // java

                return y + steps >= getWorld().getSetup().getHeight();

            case LEFT:         // java

                return x - steps < 0;

            case UP:         // java

                return y - steps < 0;

            default: // Not a valid direction
                return false;
        }
    }

    /**
     * Stops the simulation cycle (the act()-method is finished first) <br>
     * <i>Stoppt die Simulation (die act()-Methode wird noch bis unten ausgefuehrt)</i>
     */
    protected void stop() {
        Greenfoot.stop();
    }

    /*----- END OF STANDARD KARA METHODS! BELOW ARE JUST SOME HELPER METHODS ----- */

    /**
     * Shows a popup with a warning message containing both the english or
     * german message.
     */
    protected void showWarning(String englishMessage, String germanMessage) {
        String message = "<html>" + englishMessage + "<p><i>" + germanMessage + "</i></html>";
        Object[] options = {"OK", "Exit Program"};
        int choice = DialogUtils.showOptionDialogEdt(null, message, "Warning",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
                options, options[0]);

        if (choice == 1) {
            // Emergency stop. Greenfoot should restart after this.
            System.exit(0);
        } else {
            // Stop. This will still finish the act()-method.
            Greenfoot.stop();
            // Repaint, otherwise the world might keep displaying a dialog in
            // some cases
            getWorld().repaint();
        }
    }

    public void setLocationWithoutOffset(int x, int y) {
        super.setLocation(x + getWorld().getSetup().getOffsetStartToX(), y + getWorld().getSetup().getOffsetStartToY());
        System.out.println("setLocationWithoutOffsetWithoutOffset");
    }

    public HashMap<Direction, ArrayList<GreenfootImage>> loadImageForCharacter(GreenfootImage bodyImage, int NumOfCellHorizontal, int NumOfCellVertical) {
        //TODO hier ein richtigen Import von der Cellsize der World
        int PIXEL = 60;
        HashMap<Direction, ArrayList<GreenfootImage>> animationMap = new HashMap<Direction, ArrayList<GreenfootImage>>();
        GreenfootImage loaded = bodyImage;
        loaded.scale(PIXEL * NumOfCellHorizontal, PIXEL * NumOfCellVertical);
        BufferedImage loadedBuf = loaded.getAwtImage();
        int i = 0;
        for (Direction dir : Direction.values()) {
            ArrayList<GreenfootImage> animation = new ArrayList<GreenfootImage>();
            for (int j = 0; j < loaded.getWidth(); j = j + PIXEL) {

                BufferedImage bufImage = loadedBuf.getSubimage(j, i, PIXEL, PIXEL);
                GreenfootImage gImage = new GreenfootImage(bufImage.getWidth(), bufImage.getHeight());
                BufferedImage gBufImg = gImage.getAwtImage();
                Graphics2D graphics = (Graphics2D) gBufImg.getGraphics();
                graphics.drawImage(bufImage, null, 0, 0);

                animation.add(gImage);
            }
            animationMap.put(dir, animation);
            i = i + PIXEL;
        }

        return animationMap;
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

    public HashMap<Direction, ArrayList<GreenfootImage>> loadImageForCharacter(GreenfootImage bodyImage) {
        //TODO hier ein richtigen Import von der Cellsize der World
        int PIXEL = 60;
        HashMap<Direction, ArrayList<GreenfootImage>> animationMap = new HashMap<Direction, ArrayList<GreenfootImage>>();
        GreenfootImage loaded = bodyImage;
        loaded.scale(PIXEL, PIXEL);
        for (Direction dir : Direction.values()) {
            ArrayList<GreenfootImage> animation = new ArrayList<GreenfootImage>();
            animation.add(loaded);
            animationMap.put(dir, animation);
        }
        return animationMap;
    }

    public void changeAnimationImage(Figur actor, int offset, Direction direction){
        GreenfootImage tmp;
        if(imagePointer >= getImageContainer().get(direction).size()-1)
            imagePointer = 0;
        else
            imagePointer++;
        GreenfootImage image = getImageContainer().get(direction).get(imagePointer);
        switch (direction) {
            case RIGHT:
                tmp = new GreenfootImage(image.getWidth() + offset, image.getHeight());
                tmp.drawImage(image, offset, 0);
                break;
            case LEFT:
                tmp = new GreenfootImage(image.getWidth() + offset, image.getHeight());
                tmp.drawImage(image, offset, 0);
                break;
            case UP:
                tmp = new GreenfootImage(image.getWidth(), image.getHeight() + offset);
                tmp.drawImage(image, 0, offset);
                break;
            case DOWN:
                tmp = new GreenfootImage(image.getWidth(), image.getHeight() + offset);
                tmp.drawImage(image, 0, offset);
                break;
            default:
                tmp = new GreenfootImage(image.getWidth() + 60, image.getHeight() + 60);
                break;
        }

        actor.setImage(tmp);
    }

    public void resetImage() {
        imagePointer = 0;
        setImage(imageContainer.get(currentDirection).get(0));
    }

//TODO wird in Lichtwesen gebraucht, ob so gut?
    public void changeImage(GreenfootImage image, int sceneX, int sceneY) {
        imageContainer = loadImageForCharacter(image, sceneX, sceneY);
        setImage(imageContainer.get(currentDirection).get(0));
    }


    public enum Direction {
        RIGHT, LEFT, UP, DOWN;

        public Direction rotationLeft() {
            switch (this) {
                case RIGHT:
                    return UP;

                case DOWN:
                    return RIGHT;

                case UP:
                    return LEFT;

                case LEFT:
                    return DOWN;
                default:
                    return null;
            }
        }

        public Direction rotationRight() {
            switch (this) {
                case RIGHT:
                    return DOWN;

                case DOWN:
                    return LEFT;

                case UP:
                    return RIGHT;

                case LEFT:
                    return UP;
                default:
                    return null;
            }
        }

    }

}

