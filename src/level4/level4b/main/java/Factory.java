import greenfoot.Actor;
import greenfoot.Greenfoot;
import interfaces.Animation;
import util.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class Factory {

    public static final int CELLSIZE = 60;
    public static final Class<?>[] PAINT_ORDER = {
            Jahrva.class};
    private static final String WORLD_SETUP_FILE = "WeltSetup.json";
    private static final Animator animator = Animator.getInstance();
    private static WeltSetup setupFactory;
    private static LeereWelt leereWelt;

    static {
        Factory.createWorldSetup();
        animator.start();

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

        leereWelt = welt;

        Factory.initActorsFromWorldSetup(Factory.getSetup(), welt.erhalteSpielfeld());
    }

    protected static void addAnimationObject(Animation animation) {
        animator.addAnimation(animation);
    }

    protected static void removeAnimationObject(Animation animation) {
        animator.removeAnimation(animation);
    }

    protected static void laufen(Charakter charakter) {


        charakter.moveActors(charakter.getCurrentDirection(), charakter);

        checkWin(charakter);
    }

    private static void geschafft() {
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                String message = "<html>" + "" + "<p>" + "Du hast es geschafft!" + "</html>";
                Object[] options = {"Reset"};
                int choice = DialogUtils.showOptionDialogEdt(null, message, "Hinweis",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                        options, options[0]);
                resetWorld();
            }
        });
        thread.start();
    }

    private static boolean stehtCharakterAufDemZiel(Charakter charakter) {
        return Factory.getSetup().getZielPositions().stream().filter(e -> e.getX() == charakter.getX() && e.getY() == charakter.getY()).findFirst().isPresent();
    }

    private static void resetWorld() {
        Spielfeld spielfeld = leereWelt.erhalteSpielfeld();
        List<Actor> actors = spielfeld.getAllActors();
        for (Actor a : actors) {

            spielfeld.objektEntfernen(a);
            ActorPosition position = ((Figur) a).getActorPosition();
            ((Figur) a).reset();
            spielfeld.objektHinzufuegen(a, position.getX(), position.getY());
        }
    }

    public static void addObject(Actor object, int x, int y, LeereWelt welt) {
        if (object instanceof Animation)
            Factory.addAnimationObject((Animation) object);
    }

    public static void removeObject(Actor object, LeereWelt welt) {

        if (object instanceof Animation)
            Factory.removeAnimationObject((Animation) object);
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
        Scalen.resetScalen();
        for (ActorPosition actorPosition : setup.getActors()) {
            Actor tmp = null;
            switch (actorPosition.getActor()) {
                case "Jahrva":
                    tmp = Jahrva.erzeugeInstance(actorPosition);
                    break;
                case "Stein":
                    tmp = new Stein(actorPosition);
                    break;
                case "Steinillusion":
                    tmp = new Steinillusion(actorPosition);
                    break;
                case "Lichtwesen":
                    tmp = new Lichtwesen(actorPosition);
                    break;
                case "Scalen":
                    tmp = Scalen.erzeugeInstance(actorPosition);
                    break;

            }

            if (tmp != null)
                playground.objektHinzufuegen(tmp, actorPosition.getX(), actorPosition.getY());
            if (tmp instanceof Animation)
                animator.addAnimation((Animation) tmp);
        }

        SoundButton x = SoundButton.erzeugeInstance(Factory.getSetup().isMute());
        playground.getWelt().addObject(x, setup.getOuterWidth(), setup.getOuterHeight());
    }

    public static void checkWin(Charakter character) {
        Spielfeld feld = leereWelt.erhalteSpielfeld();
        for (int y =0;y<5;y++) {
            if (feld.gibObjektAuf(0,y, Stein.class)==null) {
                return;
            }
        }
        if (stehtCharakterAufDemZiel(character)) {
            geschafft();
        }
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
