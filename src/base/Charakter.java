
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Write a description of class Charakter here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Charakter extends Figur {

    private FigureTyp typ;

    protected Charakter(FigureTyp typ) {
        super(createImage(typ.path), 4, 4);
        this.typ = typ;
    }

    public void move() {
        if (theWorldsEnd(getCurrentDirection(), 1, this)) {
            //TODO warnung umschreiben
            showWarning("Kara can't move because of a tree!",
                    "Kara kann sich nicht bewegen wegen einem Baum!");
            return;
        }

        Geysir geysir = (Geysir) getObjectInFront(getCurrentDirection(), 1, Geysir.class);
        // Check for a geysir
        if (geysir != null) {
            //TODO warnung umschreiben
            showWarning("Vor dir ist ein Geysir!", "");
            return;
        }

        //TODO Ein Push objekt bewegen
        // Check for a stone
        Stein stone = (Stein) getObjectInFront(getCurrentDirection(), 1, Stein.class);
        if (stone != null) {

            // Check if the mushroom could be pushed to the next field
            if (!theWorldsEnd(getCurrentDirection(), 1, stone) && getObjectInFront(getCurrentDirection(), 2, Figur.class) == null && typ == FigureTyp.Steinbeisser) {
                moveActors(getCurrentDirection(), this, stone);
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
            moveActors(getCurrentDirection(), this);
        }
        Greenfoot.delay(1);
    }

    /**
     * Moves the actor one step in the specified direction and animation his movements.
     *
     * @param actors    the actors to be moved
     * @param direction the direction to move
     */


    private void moveActors(Direction direction, Figur... actors) {
        if (direction.equals(Direction.LEFT) || direction.equals(Direction.UP)) {
            if (direction.equals(Direction.UP))
                for (Figur figure : actors) {
                    figure.setLocationWithoutOffset(figure.getX(),
                            modulo((figure.getY() - 1), getWorld().getSetup().getHeight()));
                }

            else if (direction.equals(Direction.LEFT))
                for (Figur figure : actors) {
                    figure.setLocationWithoutOffset(
                            modulo((figure.getX() - 1), getWorld().getSetup().getWidth()),
                            figure.getY());
                }

            for (int j = 0, k = 120; j < 5; j++, k = k - 24) {
                //TODO cell size besser bekommen
                changeAnimationImage(k, direction, actors);
                Greenfoot.delay(2);
            }
            for (Figur figure : actors) {
                figure.resetImage();
            }
        } else {
            for (int j = 0, k = 0; j < 5; j++, k = k + 24) {
                //TODO cell size besser bekommen
                changeAnimationImage(k, direction, actors);
                Greenfoot.delay(2);
            }

            if (direction.equals(Direction.DOWN))
                for (Figur figure : actors) {
                    figure.resetImage();
                    figure.setLocationWithoutOffset(figure.getX(),
                            modulo((figure.getY() + 1), getWorld().getSetup().getHeight()));
                }
            else if (direction.equals(Direction.RIGHT))
                for (Figur figure : actors) {
                    figure.resetImage();
                    figure.setLocationWithoutOffset(
                            modulo((figure.getX() + 1), getWorld().getSetup().getWidth()), figure.getY());
                }
        }

    }


    /**
     * Kara turns left by 90 degrees <br>
     * <i>Kara dreht sich um 90° nach links</i>
     */

    public void turnLeft() {
        setCurrentDirection(getCurrentDirection().rotationLeft());
        resetImage();
        Greenfoot.delay(1);
    }

    /**
     * Kara turns right by 90 degrees <br>
     * <i>Kara dreht sich um 90° nach rechts</i>
     */
    public void turnRight() {
        setCurrentDirection(getCurrentDirection().rotationRight());
        resetImage();
        Greenfoot.delay(1);
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

        switch (getCurrentDirection()) {
            case RIGHT:
                x = modulo((x + steps), getWorld().getSetup().getWidth());
                break;

            case DOWN:
                y = modulo((y + steps), getWorld().getSetup().getHeight());
                break;

            case LEFT:
                x = modulo((x - steps), getWorld().getSetup().getWidth());
                break;

            case UP:
                y = modulo((y - steps), getWorld().getSetup().getHeight());
                break;

            default: // Not a valid direction
                return null;
        }


        List<?> objects = getWorld().getPlayground().gibObjekteAuf(x, y, clazz);

        if (objects != null && objects.size() > 0) {
            return objects.get(0);
        } else {
            return null;
        }
    }

    public boolean worldEndFront() {
        return theWorldsEnd(getCurrentDirection(), 1, this);
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

    public enum FigureTyp {
        Zauber("character_body.png", "character_stab.png", "character_kleidung.png"), Steinbeisser("steinbeißer.png");

        public final String[] path;

        FigureTyp(String... path) {
            this.path = path;

        }

    }

}
