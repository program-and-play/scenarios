import greenfoot.Actor;
import greenfoot.Greenfoot;
import util.SoundButton;
import util.Spielfeld;
import util.WeltSetup;

import java.io.File;
import java.io.IOException;


/**
 * Created by lukashettwer on 26.11.16.
 */
public class Factory {

private static WeltSetup setupFactory;

    private static final String WORLD_SETUP_FILE = "WeltSetup.json";


    static {
        Factory.createWorldSetup();

        //TODO entscheide welchen default speed gesetzt werden soll
        Greenfoot.setSpeed(Factory.getSetup().getSpeed());

        //TODO Paintorder reparieren
        //        setPaintOrder(PAINT_ORDER)
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

    public static WeltSetup createWorldSetup(){
        // This code is executed when the class is loaded,
        // BEFORE the constructor is called
        //TODO das muss besser ausgelagert werden
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
        playground.getWelt().addObject(x, setup.getOuterWidth(),setup.getOuterHeight());
    }


}
