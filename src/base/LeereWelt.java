


import greenfoot.*;
import interfaces.*;
import util.*;
import javax.swing.*;

/**
 * Basisklasse der Welt.
 */
public class LeereWelt extends World implements GreenfootWorld {


    private Spielfeld spielfeld;
    private Hintergrund hintergrund;

    // Size of one cell
    public static final int CELL_SIZE = 60;

    /**
     * Eine neue Welt erstellen.
     */
    public LeereWelt() {
        super(Factory.getSetup() != null ? Factory.getSetup().getOuterWidth(): 10, Factory.getSetup() != null ? Factory.getSetup().getOuterHeight() : 10, CELL_SIZE);

        if (Factory.getSetup() == null) {
            DialogUtils.showMessageDialogEdt(null, DialogUtils.setupNullMessage, "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        spielfeld = new Spielfeld(this, Factory.getSetup());
        hintergrund = new Hintergrund(Factory.getSetup(), CELL_SIZE);

        setBackground(hintergrund.getBackground());

        Factory.initActorsFromWorldSetup( Factory.getSetup(),  spielfeld);
    }

    //New
    @Override
    public void addObject(Actor object, int x, int y) {
        super.addObject(object, x, y);
        if (Factory.getSetup().isDark() && object instanceof Lichtwesen) {
            hintergrund.updateBackground(getObjects(LichtwesenInterface.class));
            setBackground(hintergrund.getBackground());
        }
        if(object instanceof Animation)
            Factory.addAnimationObject((Animation) object);
    }

    @Override
    public void removeObject(Actor object) {
        super.removeObject(object);
        if (Factory.getSetup().isDark() && object instanceof Lichtwesen) {
            hintergrund.updateBackground(getObjects(LichtwesenInterface.class));
            setBackground(hintergrund.getBackground());
        }

        if(object instanceof Animation)
            Factory.removeAnimationObject((Animation) object);
    }


    protected WeltSetup getSetup() {
        return Factory.getSetup();
    }

    public Spielfeld erhalteSpielfeld() {
        return spielfeld;
    }

}
