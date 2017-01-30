import greenfoot.Actor;
import greenfoot.Greenfoot;
import interfaces.Animation;
import util.*;

import java.io.File;
import java.io.IOException;


/**
 * Created by lukashettwer on 26.11.16.
 */
public class Factory {

    private static WeltSetup setupFactory;

    private static final String WORLD_SETUP_FILE = "WeltSetup.json";

    private static final Animator animator = Animator.getInstance();

    public static final int CELLSIZE = 60;

    public static final Class<?>[] PAINT_ORDER = {
            Lichtwesen.class,
            Zauberer.class,
            Steinbeisser.class,
            Stein.class};

    static {
        Factory.createWorldSetup();
        animator.start();

        Greenfoot.setSpeed(Factory.getSetup().getSpeed());
    }

    protected static void addAnimationObject(Animation animation) {
        animator.addAnimation(animation);
    }

    protected static void removeAnimationObject(Animation animation) {
        animator.removeAnimation(animation);
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
                case "Stein":
                    tmp = new Stein();
                    break;
                case "Lichtwesen":
                    tmp = new Lichtwesen();
                    break;
                case "Steinbeisser":
                    tmp = Steinbeisser.erzeugeInstance();
                    break;
                case "Geysir":
                    tmp = new Geysir();
                    break;

            }

            if (tmp != null)
                playground.objektHinzufuegen(tmp, actorPosition.getX(), actorPosition.getY());
            if (tmp instanceof Animation)
                animator.addAnimation((Animation) tmp);
        }

        SoundButton x = new SoundButton(Factory.getSetup().isMute());
        playground.getWelt().addObject(x, setup.getOuterWidth(), setup.getOuterHeight());
    }


}
