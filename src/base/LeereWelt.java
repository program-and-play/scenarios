


import greenfoot.*;
import util.*;

import javax.swing.*;
import java.util.List;

/**
 * Basisklasse der Welt.
 */
public class LeereWelt extends World {

    private static final String WORLD_SETUP_FILE = "WeltSetup.json";
    private static WeltSetup setup;
    private Spielfeld playground;
    private Hintergrund background;

    // Size of one cell
    public static final int CELL_SIZE = 60;

    static {
        setup = Factory.createWorldSetup(WORLD_SETUP_FILE);
    }

    /**
     * Eine neue Welt erstellen.
     */
    public LeereWelt() {
        // Create the new world
        super(setup != null ? setup.getOuterWidth(): 10, setup != null ? setup.getOuterHeight() : 10, CELL_SIZE);
        //  Warn that there was no WORLD_SETUP_FILE specified.
        //TODO  Namen der Welt und weitere details ändern

        if (setup == null) {
            DialogUtils.showMessageDialogEdt(null, DialogUtils.setupNullMessage, "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        playground = new Spielfeld(this, setup);
        background = new Hintergrund(setup, CELL_SIZE);

        //  TODO Paintorder reparieren
        //        setPaintOrder(PAINT_ORDER)

        //TODO entscheide welchen default speed gesetzt werden soll
        Greenfoot.setSpeed(setup.getSpeed());
        addObject(new SoundButton(setup.isMute()), getWidth(), getHeight());

        setBackground(background.getBackground());
        // Initialize actors
        Factory.initActorsFromWorldSetup( setup,  playground);
    }

    //New
    @Override
    public void addObject(Actor object, int x, int y) {
        super.addObject(object, x, y);
        if (setup.isDark() && object instanceof Lichtwesen) {
             background.updateBackground(getObjects(Lichtwesen.class));
            setBackground(background.getBackground());
        }
    }

    @Override
    public void removeObject(Actor object) {
        super.removeObject(object);
        if (setup.isDark() && object instanceof Lichtwesen) {
            background.updateBackground(getObjects(Lichtwesen.class));
            setBackground(background.getBackground());
        }
    }



    protected WeltSetup getSetup() {
        return setup;
    }

    public void muteSound() {
        setup.setMute(true);
        save();
    }

    public void unmuteSound() {
        setup.setMute(false);
        save();
    }

    public void save() {
        WeltSetup.saveWorldSetup(setup);
    }

    public Spielfeld getPlayground() {
        return playground;
    }

}
