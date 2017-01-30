import greenfoot.Actor;
import greenfoot.World;
import interfaces.Animation;
import interfaces.GreenfootWorld;
import interfaces.LichtwesenInterface;
import util.DialogUtils;
import util.Hintergrund;
import util.Spielfeld;

import javax.swing.*;

public class LeereWelt extends World implements GreenfootWorld {
    public static final int CELL_SIZE = 60;

    private Spielfeld spielfeld;
    private Hintergrund hintergrund;


    public LeereWelt() {
        super(Factory.getSetup() != null ? Factory.getSetup().getOuterWidth() : 10, Factory.getSetup() != null ? Factory.getSetup().getOuterHeight() : 10, Factory.CELLSIZE);
        konstruiereWelt();
    }

    @Override
    public void act() {

    }


    /*-----  UNTERHALB DIESER ZEILE SIND NUR HELFERMETHODEN ----- */

    private void konstruiereWelt() {
        if (Factory.getSetup() == null) {
            DialogUtils.showMessageDialogEdt(null, DialogUtils.setupNullMessage, "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        setPaintOrder(Factory.PAINT_ORDER);

        spielfeld = new Spielfeld(this, Factory.getSetup());
        hintergrund = new Hintergrund(Factory.getSetup(), Factory.CELLSIZE);

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
        if (object instanceof Animation)
            Factory.addAnimationObject((Animation) object);
    }

    @Override
    public void removeObject(Actor object) {
        super.removeObject(object);
        if (Factory.getSetup().isDark() && object instanceof Lichtwesen) {
            hintergrund.updateBackground(getObjects(LichtwesenInterface.class));
            setBackground(hintergrund.getBackground());
        }

        if (object instanceof Animation)
            Factory.removeAnimationObject((Animation) object);
    }

    public Spielfeld erhalteSpielfeld() {
        return spielfeld;
    }

}
