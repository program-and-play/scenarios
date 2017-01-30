import greenfoot.Actor;
import greenfoot.Greenfoot;
import interfaces.LichtwesenInterface;
import util.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;


/**
 * Created by lukashettwer on 26.11.16.
 */
public class Factory {

    private static WeltSetup setupFactory;

    private static final String WORLD_SETUP_FILE = "WeltSetup.json";


    public static final int CELLSIZE = 60;

    public static final Class<?>[] PAINT_ORDER = {
            Lichtwesen.class,
            Zauberer.class};

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
        for (WeltSetup.ActorPosition actorPosition : setup.getActors()) {
            Actor tmp = null;
            switch (actorPosition.getActor()) {
                case "Zauberer":
                    tmp = Zauberer.erzeugeInstance();
                    break;
                case "Lichtwesen":
                    tmp = new Lichtwesen();
                    break;
            }

            if (tmp != null)
                playground.objektHinzufuegen(tmp, actorPosition.getX(), actorPosition.getY());
        }

        SoundButton x = new SoundButton(Factory.getSetup().isMute());
        playground.getWelt().addObject(x, setup.getOuterWidth(), setup.getOuterHeight());
    }


}
