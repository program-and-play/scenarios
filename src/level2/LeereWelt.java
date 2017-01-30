import greenfoot.Actor;
import greenfoot.World;
import interfaces.GreenfootWorld;
import interfaces.LichtwesenInterface;
import util.DialogUtils;
import util.Hintergrund;
import util.Spielfeld;

import javax.swing.*;

/**
 * Basisklasse der Welt.
 */
public class LeereWelt extends World implements GreenfootWorld {
    public static final int CELL_SIZE = 60;

    private Spielfeld spielfeld;
    private Hintergrund hintergrund;

    /**
     * Eine neue Welt erstellen.
     */
    public LeereWelt() {
        super(Factory.getSetup() != null ? Factory.getSetup().getOuterWidth() : 10, Factory.getSetup() != null ? Factory.getSetup().getOuterHeight() : 10, CELL_SIZE);
        konstruiereWelt();
    }

    @Override
    public void act() {

    }


    /*-----  UNTERHALB DIESER ZEILE SIND NUR EINFACH HELFERMETHODEN ----- */

    private void konstruiereWelt() {
        if (Factory.getSetup() == null) {
            DialogUtils.showMessageDialogEdt(null, DialogUtils.setupNullMessage, "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        spielfeld = new Spielfeld(this, Factory.getSetup());
        hintergrund = new Hintergrund(Factory.getSetup(), CELL_SIZE);

        setBackground(hintergrund.getBackground());

        Factory.initActorsFromWorldSetup(Factory.getSetup(), spielfeld);
    }

    @Override
    public void addObject(Actor object, int x, int y) {
        super.addObject(object, x, y);
        if (Factory.getSetup().isDark() && object instanceof Lichtwesen) {
            hintergrund.updateBackground(getObjects(LichtwesenInterface.class));
            setBackground(hintergrund.getBackground());
        }
    }

    @Override
    public void removeObject(Actor object) {
        super.removeObject(object);
        if (Factory.getSetup().isDark() && object instanceof Lichtwesen) {
            hintergrund.updateBackground(getObjects(LichtwesenInterface.class));
            setBackground(hintergrund.getBackground());
        }

    }

    public Spielfeld erhalteSpielfeld() {
        return spielfeld;
    }

}
