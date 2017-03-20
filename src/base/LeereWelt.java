import greenfoot.*;
import interfaces.*;
import util.*;

import java.util.List;


public class LeereWelt extends World implements GreenfootWorld {
    private Spielfeld spielfeld;
    private Hintergrund hintergrund;


    public LeereWelt() {
        super(Factory.getSetup() != null ? Factory.getSetup().getOuterWidth() : 10, Factory.getSetup() != null ? Factory.getSetup().getOuterHeight() : 10, Factory.CELLSIZE);
        Factory.konstruiereWelt(this);
        List<Charakter> charakters = getObjects(Charakter.class);
        for (Charakter x : charakters) {
            x.reset();
        }
    }


    @Override
    public void act() {

    }


    /*-----  UNTERHALB DIESER ZEILE SIND NUR HELFERMETHODEN ----- */

    @Override
    public void addObject(Actor object, int x, int y) {
        super.addObject(object, x, y);
        Factory.addObject(object, x, y, this);
    }

    @Override
    public void removeObject(Actor object) {
        super.removeObject(object);
        Factory.removeObject(object, this);
    }

    public Spielfeld erhalteSpielfeld() {
        return spielfeld;
    }

    public void setSpielfeld(Spielfeld spielfeld) {
        this.spielfeld = spielfeld;
    }

    public Hintergrund getHintergrund() {
        return hintergrund;
    }

    public void setHintergrund(Hintergrund hintergrund) {
        this.hintergrund = hintergrund;
    }
}
