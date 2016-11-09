
import greenfoot.*;

import java.util.ArrayList;

/**
 * Mushrooms can be pushed by Kara if there is nothing behind them. The
 * Mushrooms must be pushed onto a target (the Leafs). If a Mushroom is on
 * target, a target image is shown.
 *
 * @author Marco Jakob (http://code.makery.ch)
 */
public class Stone extends Figure {

    public static final String BODY_FILE_ROSA = "lichtwesen_rosa_rund.png";

    public Stone() {
        super(new GreenfootImage(BODY_FILE_ROSA));
    }

    /**
     * Create a Mushroom.
     *
     * @param onTarget
     *            if true, the on-target-image is used.
     */
    public Stone(boolean onTarget) {
        if (onTarget) {
            showOnTargetImage();
        }
    }

    /**
     * If the Mushroom is on target (i.e. on a Leaf), the on-target-image,
     * otherwise the default image is shown.
     */
    public void updateImage() {
        if (getOneObjectAtOffset(0, 0, Stone.class) != null) {
            showOnTargetImage();
        } else {
            showDefaultImage();
        }
    }

    /**
     * Shows the default image.
     */
    private void showDefaultImage() {
        setImage(EmptyWorld.ICON_STONE);
    }

    /**
     * Shows the on-target-image.
     */
    private void showOnTargetImage() {
        //TODO hier muss was gemacht werden
//        setImage(EmptyWorld.ICON_STONE_ON_TARGET);
    }

    /**
     * This method is called when a new Mushroom is added to the world. It
     * checks whether it is ok to put it there.
     */
    protected void addedToWorld(World world) {
        if (getOneObjectAtOffset(0, 0, MyCharacter.class) != null
                || getOneObjectAtOffset(0, 0, Stone.class) != null
                /*|| getOneObjectAtOffset(0, 0, Tree.class) != null*/) {
            // There is something in the way, remove it again
            world.removeObject(this);
        } else {
            updateImage();
        }
    }

    /**
     * Overriding setLocation(...) of the Actor class to prevent dragging the
     * Mushroom on a Kara, Tree, or another Mushroom.
     */
    public void setLocation(int x, int y) {
        if (getWorld().getObjectsAt(x, y, MyCharacter.class).isEmpty()
                && getWorld().getObjectsAt(x, y, Stone.class).isEmpty()
               /* && getWorld().getObjectsAt(x, y, Tree.class).isEmpty()*/) {
            // Nothing is in the way, we can set the location
            super.setLocation(x, y);
            updateImage();
        }
    }

    public void setLocationWithoutOffset(int x, int y) {
        super.setLocation(x + getWorld().getOffsetStartToX(), y +getWorld().getOffsetStartToY());
        System.out.println("setLocationWithoutOffsetWithoutOffset");
    }

    public EmptyWorld getWorld(){
        return (EmptyWorld) super.getWorld();
    }




}
