import greenfoot.Actor;
import greenfoot.Greenfoot;
import interfaces.LichtwesenInterface;
import util.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;



public class Factory {

    public static final int CELLSIZE = 60;
    public static final Class<?>[] PAINT_ORDER = {
            Lichtwesen.class,
            Jahrva.class};
    private static final String WORLD_SETUP_FILE = "WeltSetup.json";
    private static WeltSetup setupFactory;

    static {
        Factory.createWorldSetup();

        Greenfoot.setSpeed(Factory.getSetup().getSpeed());
    }

    protected static void konstruiereWelt(LeereWelt welt) {
        if (Factory.getSetup() == null) {
            DialogUtils.showMessageDialogEdt(null, DialogUtils.setupNullMessage, "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        welt.setPaintOrder(Factory.PAINT_ORDER);

        welt.setSpielfeld(new Spielfeld(welt, Factory.getSetup()));
        welt.setHintergrund(new Hintergrund(Factory.getSetup(), Factory.CELLSIZE));

        welt.setBackground(welt.getHintergrund().getBackground());

        Factory.initActorsFromWorldSetup(Factory.getSetup(), welt.erhalteSpielfeld());
    }

    protected static void laufen(Charakter ch) {
            ch.moveActors(ch.getCurrentDirection(), ch);
        
    }

    public static void addObject(Actor object, int x, int y, LeereWelt welt) {
        if (Factory.getSetup().isDark() && object instanceof Lichtwesen) {
            welt.getHintergrund().updateBackground(welt.getObjects(LichtwesenInterface.class));
            welt.setBackground(welt.getHintergrund().getBackground());
        }
    }

    public static void removeObject(Actor object, LeereWelt welt) {
        if (Factory.getSetup().isDark() && object instanceof Lichtwesen) {
            welt.getHintergrund().updateBackground(welt.getObjects(LichtwesenInterface.class));
            welt.setBackground(welt.getHintergrund().getBackground());
        }
    }

    public static WeltSetup getSetup() {
        return setupFactory;
    }

    public static WeltSetup createWorldSetup() {
        WeltSetup setup = null;
        try {
            File file = WeltSetup.findMatchingFiles(WORLD_SETUP_FILE, LeereWelt.class);
            if (file != null) {
                setup = WeltSetup.createWorldSetup(WeltSetup.readAllLines(file));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        setupFactory = setup;
        return setup;
    }

    public static void initActorsFromWorldSetup(WeltSetup setup, Spielfeld playground) {
        for (ActorPosition actorPosition : setup.getActors()) {
            Actor tmp = null;
            switch (actorPosition.getActor()) {
                case "Jahrva":
                    tmp = Jahrva.erzeugeInstance(actorPosition);
                    break;
                case "Lichtwesen":
                    tmp = new Lichtwesen(actorPosition);
                    break;
            }

            if (tmp != null)
                playground.objektHinzufuegen(tmp, actorPosition.getX(), actorPosition.getY());
        }

        SoundButton x = SoundButton.erzeugeInstance(Factory.getSetup().isMute());
        playground.getWelt().addObject(x, setup.getOuterWidth(), setup.getOuterHeight());
    }
    
    public void muteSound() {
        setupFactory.setMute(true);
        save();
    }
    
    public void unmuteSound() {
        setupFactory.setMute(false);
        save();
    }
    
    public void save() {
        WeltSetup.saveWorldSetup(setupFactory);
    }


}
