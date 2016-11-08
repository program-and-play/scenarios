import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
class Figure extends Actor{

    public static final String BODY_FILE = "character_body.png";
    public static final String STICK_FILE = "character_stab.png";
    public static final String CLOTHES_FILE = "character_kleidung.png";

    private Direction currentDirection = Direction.RIGHT;
    private HashMap<Direction, ArrayList<GreenfootImage>> imageContainer;

    public Figure() {

        this(BODY_FILE, STICK_FILE, CLOTHES_FILE);
    }

    public Direction getCurrentDirection(){
        return currentDirection;
    }


    public Figure(String body) {

        GreenfootImage bodyImage = new GreenfootImage(body);

        bodyImage.drawImage(bodyImage, 0, 0);

        imageContainer = loadImageForCharacter(bodyImage, 4, 4);
        setImage(imageContainer.get(currentDirection).get(0));
    }

    public Figure(String body, String stick, String clothes) {

        GreenfootImage bodyImage = new GreenfootImage(body);
        GreenfootImage clothesImage = new GreenfootImage(clothes);
        GreenfootImage stickImage = new GreenfootImage(stick);
        bodyImage.drawImage(clothesImage, 0, 0);
        bodyImage.drawImage(stickImage, 0, 0);
        bodyImage.scale(240, 240);

        imageContainer = loadImageForCharacter(bodyImage, 4, 4);
        setImage(imageContainer.get(currentDirection).get(0));
    }


    public EmptyWorld getWorld(){
        return (EmptyWorld) super.getWorld();
    }

    @Override
    public int getX(){
        return super.getX() - getWorld().getOffsetStartToX();
    }

    @Override
    public int getY(){
        return super.getY() - getWorld().getOffsetStartToY();
    }

    public void move() {
        if (theWorldsEnd(currentDirection, 1, this)) {
            //TODO warnung umschreiben
            showWarning("Kara can't move because of a tree!",
                    "Kara kann sich nicht bewegen wegen einem Baum!");
            return;
        }
        // Check for a tree
        //TODO Warnung für hindernisse schreiben
        //        if (treeFront()) {
        //            showWarning("Kara can't move because of a tree!",
        //                    "Kara kann sich nicht bewegen wegen einem Baum!");
        //            return;
        //        }
        //TODO Ein Push objekt bewegen
        // Check for a stone
        Stone mushroomFront = (Stone) getObjectInFront(currentDirection, 1, Stone.class);
        if (mushroomFront != null) {
            // Check if the mushroom could be pushed to the next field
            if (!theWorldsEnd(currentDirection, 1, mushroomFront)
                    && getObjectInFront(currentDirection, 2, Stone.class) == null
            /*getObjectInFront(getRotation(), EmptyWorld.FACTOR*2, Tree.class) == null*/
                    ) {
                // Push the mushroom
                moveActors(this, mushroomFront, currentDirection);
//                // Check if the mushroom is now on a leaf
//                mushroomFront.updateImage();
            } else {
                // Could not push the mushroom
                //TODO updaten den text
                showWarning(
                        "Kara can't move because he can't push the mushroom!",
                        "Kara kann sich nicht bewegen, da er den Pilz nicht schieben kann!");
                return;
            }
        } else {

            // Kara can move
            moveActor(this, currentDirection);
        }
        Greenfoot.delay(1);
    }


    /**
     * Kara turns left by 90 degrees <br>
     * <i>Kara dreht sich um 90° nach links</i>
     */
    public void turnLeft() {
        currentDirection = currentDirection.rotationLeft();
        resetImage();
        Greenfoot.delay(1);
    }

    /**
     * Kara turns right by 90 degrees <br>
     * <i>Kara dreht sich um 90° nach rechts</i>
     */
    public void turnRight() {
        currentDirection = currentDirection.rotationRight();
        resetImage();
        Greenfoot.delay(1);
    }

    public boolean worldEndFront() {
        return theWorldsEnd(currentDirection, 1, this);
    }

    //TODO etwas hinlegen
    //    /**
    //     * Kara puts down a leaf <br>
    //     * <i>Kara legt ein neues Kleeblatt an die Position, auf der er sich
    //     * befindet</i>
    //     */
    //    public void putLeaf() {
    //        if (!onLeaf()) {
    //            Leaf leaf = new Leaf();
    //            getWorld().addObject(leaf, getX(), getY());
    //            Greenfoot.delay(1);
    //        } else {
    //            showWarning("Kara can't put a leaf on top of another leaf!",
    //                    "Kara kann kein Kleeblatt auf ein Feld legen, auf dem schon eines ist!");
    //        }
    //    }
    //TODO etwas nehmen
    //    /**
    //     * Kara picks up a leaf <br>
    //     * <i>Kara entfernt ein unter ihm liegendes Kleeblatt</i>
    //     */
    //    public void removeLeaf() {
    //        Leaf leaf = (Leaf) getOneObjectAtOffset(0, 0, Leaf.class);
    //        if (leaf != null) {
    //            getWorld().removeObject(leaf);
    //            Greenfoot.delay(1);
    //        } else {
    //            showWarning("There is no leaf that Kara could remove here!",
    //                    "Kara kann hier kein Blatt auflesen!");
    //        }
    //    }

    //TODO überprüfen ob man auf was steht
    //    /**
    //     * Kara checks if he stands on a leaf <br>
    //     * <i>Kara schaut nach, ob er sich auf einem Kleeblatt befindet</i>
    //     *
    //     * @return true if Kara stands on a leaf, false otherwise
    //     */
    //    public boolean onLeaf() {
    //        return getOneObjectAtOffset(0, 0, Leaf.class) != null;
    //    }


    //TODO überprüfen ob vor/links/rechts was blockiert
    //    /**
    //     * Kara checks if there is a tree in front of him <br>
    //     * <i>Kara schaut nach, ob sich ein Baum vor ihm befindet</i>
    //     *
    //     * @return true if there is a tree in front of Kara, false otherwise
    //     */
    //    public boolean treeFront() {
    //        return getObjectInFront(getRotation(), 1, Tree.class) != null;
    //    }
    //TODO überprüfen ob vor/links/rechts was blockiert
    //    /**
    //     * Kara checks if there is a tree on his left side <br>
    //     * <i>Kara schaut nach, ob sich ein Baum links von ihm befindet</i>
    //     *
    //     * @return true if Kara has a tree on his left, false otherwise
    //     */
    //    public boolean treeLeft() {
    //        return getObjectInFront(modulo(getRotation() - 90, 360), 1, Tree.class) != null;
    //    }
    //TODO überprüfen ob vor/links/rechts was blockiert
    //    /**
    //     * Kara checks if there is a tree on his right side <br>
    //     * <i>Kara schaut nach, ob sich ein Baum rechts von ihm befindet</i>
    //     *
    //     * @return true if Kara has a tree on his right, false otherwise
    //     */
    //    public boolean treeRight() {
    //        return getObjectInFront(modulo(getRotation() + 90, 360), 1, Tree.class) != null;
    //    }
    //TODO überprüfen ob vor/links/rechts was blockiert
    //    /**
    //     * Kara checks if there is a mushroom in front of him <br>
    //     * <i>Kara schaut nach, ob er einen Pilz vor sich hat</i>
    //     *
    //     * @return true if a mushroom is in front of a Kara, false otherwise
    //     */
    //    public boolean mushroomFront() {
    //        return getObjectInFront(getRotation(), 1, Mushroom.class) != null;
    //    }

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
        int choice = EmptyWorld.DialogUtils.showOptionDialogEdt(null, message, "Warning",
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

//    /**
//     * Finds an object in the specified direction.
//     *
//     * @param direction the direction in which to look for the object
//     * @param steps     number of cells to look ahead (1 means the next field, etc.)
//     * @param clazz     the (actor) class to look for
//     * @return the object that was found or null if none was found
//     */
//    //TODO diese Methode löschen, weil ersatz mit Direction existiert schon
//    protected Object getObjectInFront(int direction, int steps, Class<?> clazz) {
//        int x = getX();
//        int y = getY();
//
//        // Change x und y depending on the direction
//        switch (direction) {
//            case DIRECTION_RIGHT:
//                x = modulo((x + steps), getWorld().getWidth());
//                break;
//
//            case DIRECTION_DOWN:
//                y = modulo((y + steps), getWorld().getHeight());
//                break;
//
//            case DIRECTION_LEFT:
//                x = modulo((x - steps), getWorld().getWidth());
//                break;
//
//            case DIRECTION_UP:
//                y = modulo((y - steps), getWorld().getHeight());
//                break;
//
//            default: // Not a valid direction
//                return null;
//        }
//
//        List<?> objects = getWorld().getObjectsAt(x, y, clazz);
//
//        if (objects != null && objects.size() > 0) {
//            return objects.get(0);
//        } else {
//            return null;
//        }
//    }

    /**
     * Finds an object in the specified direction.
     *
     * @param direction the direction in which to look for the object (Direction enum)
     * @param steps     number of cells to look ahead (1 means the next field, etc.)
     * @param clazz     the (actor) class to look for
     * @return the object that was found or null if none was found
     */
    protected Object getObjectInFront(Direction direction, int steps, Class<?> clazz) {
        int x = getX();
        int y = getY();

        switch (currentDirection) {
            case RIGHT:         // java
                x = modulo((x + steps), getWorld().getWidth());
                break;

            case DOWN:         // java
                y = modulo((y + steps), getWorld().getHeight());
                break;

            case LEFT:         // java
                x = modulo((x - steps), getWorld().getWidth());
                break;

            case UP:         // java
                y = modulo((y - steps), getWorld().getHeight());
                break;

            default: // Not a valid direction
                return null;
        }


        List<?> objects = getWorld().getObjectsAt(x, y, clazz);

        if (objects != null && objects.size() > 0) {
            return objects.get(0);
        } else {
            return null;
        }
    }


    /**
     * Moves the actor one step in the specified direction and animation his movements.
     *
     * @param actor     the actor to be moved
     * @param stone     the stone to be moved
     * @param direction the direction to move
     */
    private void moveActors(Figure actor, Stone stone, Direction direction) {
        int cell = 60;
        int k = 0;
        switch (direction) {
            case RIGHT:
                for (int j = 1; j <= 6; j++) {
                    //TODO cell size besser bekommen
                    k = k + 20;
                    addOffsetImage(actor, actor.imageContainer.get(currentDirection).get(j % 4), k, direction);
                    addOffsetImage(stone, stone.image, k, direction);
                    Greenfoot.delay(2);
                }

                actor.resetImage();
                stone.resetImage();
                actor.setLocationWithoutOffset(
                        modulo((actor.getX() + 1), getWorld().getWidth()),
                        actor.getY());
                stone.setLocationWithoutOffset(
                        modulo((stone.getX() + 1), getWorld().getWidth()),
                        stone.getY());
                break;

            case DOWN:
                for (int j = 1; j <= 6; j++) {
                    //TODO cell size besser bekommen
                    k = k + 20;
                    addOffsetImage(actor, actor.imageContainer.get(currentDirection).get(j % 4), k, direction);
                    addOffsetImage(stone, stone.image, k, direction);
                    Greenfoot.delay(2);
                }

                actor.resetImage();
                stone.resetImage();
                actor.setLocationWithoutOffset(actor.getX(),
                        modulo((actor.getY() + 1), getWorld().getHeight()));
                stone.setLocationWithoutOffset(stone.getX(),
                        modulo((stone.getY() + 1), getWorld().getHeight()));
                break;

            case LEFT:
                k = 120;
                actor.setLocationWithoutOffset(
                        modulo((actor.getX() - 1), getWorld().getWidth()),
                        actor.getY());
                stone.setLocationWithoutOffset(
                        modulo((stone.getX() - 1), getWorld().getWidth()),
                        stone.getY());
                for (int j = 1; j <= 6; j++) {
                    //TODO cell size besser bekommen
                    k = k - 20;
                    addOffsetImage(actor, actor.imageContainer.get(currentDirection).get(j % 4), k, direction);
                    addOffsetImage(stone, stone.image, k, direction);
                    Greenfoot.delay(2);
                }

                actor.resetImage();
                stone.resetImage();


                break;

            case UP:
                k = 120;
                actor.setLocationWithoutOffset(actor.getX(),
                        modulo((actor.getY() - 1), getWorld().getHeight()));
                stone.setLocationWithoutOffset(stone.getX(),
                        modulo((stone.getY() - 1), getWorld().getHeight()));
                for (int j = 1; j <= 6; j++) {
                    //TODO cell size besser bekommen
                    k = k - 20;
                    addOffsetImage(actor, actor.imageContainer.get(currentDirection).get(j % 4), k, direction);
                    addOffsetImage(stone, stone.image, k, direction);
                    Greenfoot.delay(2);
                }

                actor.resetImage();
                stone.resetImage();
                break;

            default: // Not a valid direction
                break;
        }
    }


    public void setLocationWithoutOffset(int x, int y) {
        super.setLocation(x + getWorld().getOffsetStartToX(), y +getWorld().getOffsetStartToY());
        System.out.println("setLocationWithoutOffsetWithoutOffset");
    }

    public void setLocation(int x, int y) {
        super.setLocation(x , y);
        getWorld().createFieldBackground();
    }



    /**
     * Moves the actor one step in the specified direction and animation his movements.
     *
     * @param actor     the actor to be moved
     * @param direction the direction to move
     */
    private void moveActor(Figure actor, Direction direction) {
        int cell = 60;
        int k = 0;
        switch (direction) {
            case RIGHT:
                for (int j = 1; j <= 6; j++) {
                    //TODO cell size besser bekommen
                    k = k + 20;
                    addOffsetImage(actor, actor.imageContainer.get(currentDirection).get(j % 4), k, direction);
                    Greenfoot.delay(2);
                }

                actor.resetImage();
                System.out.println(modulo((actor.getX() + 1), getWorld().getWidth()));
                actor.setLocationWithoutOffset(
                        modulo((actor.getX() + 1), getWorld().getWidth()),
                        actor.getY());

                break;

            case DOWN:
                for (int j = 1; j <= 6; j++) {
                    //TODO cell size besser bekommen
                    k = k + 20;
                    addOffsetImage(actor, actor.imageContainer.get(currentDirection).get(j % 4), k, direction);

                    Greenfoot.delay(2);
                }

                actor.resetImage();

                actor.setLocationWithoutOffset(actor.getX(),
                        modulo((actor.getY() + 1), getWorld().getHeight()));
                break;

            case LEFT:
                k = 120;
                actor.setLocationWithoutOffset(
                        modulo((actor.getX() - 1), getWorld().getWidth()),
                        actor.getY());
                for (int j = 1; j <= 6; j++) {
                    //TODO cell size besser bekommen
                    k = k - 20;
                    addOffsetImage(actor, actor.imageContainer.get(currentDirection).get(j % 4), k, direction);

                    Greenfoot.delay(2);
                }

                actor.resetImage();


                break;

            case UP:
                k = 120;
                actor.setLocationWithoutOffset(actor.getX(),
                        modulo((actor.getY() - 1), getWorld().getHeight()));
                for (int j = 1; j <= 6; j++) {
                    //TODO cell size besser bekommen
                    k = k - 20;
                    addOffsetImage(actor, actor.imageContainer.get(currentDirection).get(j % 4), k, direction);

                    Greenfoot.delay(2);
                }

                actor.resetImage();

                break;

            default: // Not a valid direction
                break;
        }
    }

    /**
     * A special modulo operation that never returns a negative number. This is
     * necessary to always stay inside the grid of the world.
     * <p>
     * The Java modulo operation would return -1 for something like -1%10, but
     * we would need 9.
     * <p>
     * Note: Depending on the programming language, the modulo operation for
     * negative numbers is defined differently.
     *
     * @param a the first operand
     * @param b the second operand
     * @return the result of the modulo operation, always positive
     */
    private int modulo(int a, int b) {
        return (a % b + b) % b;
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

    protected boolean theWorldsEnd(Direction direction, int steps, Actor actor) {
        int x = actor.getX();
        int y = actor.getY();
        System.out.println("Actor X " + x + " Actor y " + y);

        switch (currentDirection) {
            case RIGHT:         // java
                return x + steps >= getWorld().getWidth();

            case DOWN:         // java

                return y + steps >= getWorld().getHeight();

            case LEFT:         // java

                return x - steps < 0;

            case UP:         // java

                return y - steps < 0;

            default: // Not a valid direction
                return false;
        }
    }

    public void addOffsetImage(Actor actor, GreenfootImage image, int offset, Direction direction) {
        GreenfootImage tmp;
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
        setImage(imageContainer.get(currentDirection).get(0));
    }

    public void changeImage(String body) {
        imageContainer = loadImageForCharacter(new GreenfootImage(body), 4, 4);
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

