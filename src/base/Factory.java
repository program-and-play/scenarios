import greenfoot.Actor;
import greenfoot.Greenfoot;
import interfaces.Animation;
import util.Animator;
import util.DialogUtils;
import util.Spielfeld;
import util.WeltSetup;

import javax.swing.*;
import java.io.File;
import java.io.IOException;


/**
 * Created by lukashettwer on 26.11.16.
 */
public class Factory {

private static WeltSetup setupFactory;

    private static final String WORLD_SETUP_FILE = "WeltSetup.json";

    private static final Animator animator = new Animator();

    static {
        Factory.createWorldSetup();
        animator.start();
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
                    tmp = Zauberer.getInstance();
                    break;
                case "Stein":
                    tmp = new Stein();
                    break;
                case "Lichtwesen":
                    tmp = new Lichtwesen();
                    break;
                case "Steinbeisser":
                    tmp = Steinbeisser.getInstance();
                    break;
                case "Geysir":
                    tmp = new Geysir();
                    break;

            }

            if (tmp != null)
                playground.objektHinzufuegen(tmp, actorPosition.getX(), actorPosition.getY());
            if(tmp instanceof Animation)
                animator.setAnimation((Animation) tmp);
        }


    }


}
