import greenfoot.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Der Zauber ist der Hauptcharacter dieses kleines Spiels.
 *
 * @author Lukas Hettwer
 *
 * @version 0.1
 */

public class Zauberer extends Charakter {
    private static Zauberer instance;

    /**
     * Erzeugt eine Instance vom Zauberer, existiert schon ein Zauberer, wird dieser zurueck gegeben.
     * @return Zauberer
     */

    protected static Zauberer erzeugeInstance () {
        if (Zauberer.instance == null) {
            Zauberer.instance = new Zauberer ();
        }
        return Zauberer.instance;
    }

    private Zauberer() {
        super(FigurTyp.Zauber);
    }

    /**
     * In der Act Methode wird das ausgefuehrt was der Zauberer tun soll. Diese Methode wird immer wieder aufgerufen,
     * wenn der 'Act' oder 'Run' Button gedrueckt wurde.
     */
    public void act() {
        if (istWeltzuEnde())
            rechtswendung();
        laufen();
        // Fuege hier deine "Befehle" fuer den Zauberer ein.
    }

    /**
     * Erzeugt ein Lichtwesen auf dem Spielfeld
     * @param x die horizontale Position
     * @param y die vertikale Position
     */

    public void erzeugeLichtwesen(int x, int y) {
        if (Factory.getSetup().getHeight() < x || Factory.getSetup().getWidth() < y || x < 0 || y < 0) {
            showWarning(
                    "", //Optional fuer den englischen Text.
                    "Der Zauber kann kein Lichtwesen erzeugen, da die Indizes außerhalb der Welt gewählt wurden.");
            return;
        }

        final String KOERPER_FILE = "character_body.png";
        final String ZAUBERSTAB_FILE = "stab_lichtwesenbeschwören.png";
        final String KLEIDUNG_FILE = "character_kleidung.png";
        GreenfootImage figurZaubern = new GreenfootImage(KOERPER_FILE);
        figurZaubern.drawImage(new GreenfootImage(ZAUBERSTAB_FILE), 0, 0);
        figurZaubern.drawImage(new GreenfootImage(KLEIDUNG_FILE), 0, 0);
        figurZaubern.scale(240, 240);

        HashMap<Direction, ArrayList<GreenfootImage>> bilderContainerZaubern = loadImage(figurZaubern, 4, 4);

        for (GreenfootImage img : bilderContainerZaubern.get(getCurrentDirection())) {
            setImage(img);
            Greenfoot.delay(2);
        }
        resetImage();
        Lichtwesen tmp = new Lichtwesen();
        getWorld().erhalteSpielfeld().objektHinzufuegen(tmp, x, y);
        tmp.bewegen();
    }





}
