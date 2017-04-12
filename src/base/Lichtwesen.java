
import greenfoot.*;
import interfaces.*;
import util.LichtwesenFarbe;

import java.util.ArrayList;

import static util.LichtwesenFarbe.*;

public final class Lichtwesen extends Figur implements LichtwesenInterface {
    LichtwesenFarbe farbe = BLAU;
    ArrayList<GreenfootImage> bewegung = loadImageForAnimation(new GreenfootImage("lichtwesen_beschwoeren.png"), 6);

    public Lichtwesen() {
        aktualisiereBild();
    }

    /**
     * Das Lichtwesen bewegt sich auf der Stelle.
     */

    public void animiere() {
        for (GreenfootImage img : bewegung) {
            setImage(img);
            Greenfoot.delay(2);
        }
        aktualisiereBild();
    }

    /**
     * Aendert das Aussehen des Leuchtwesen in Beige Cappuchino.
     */

    public void leuchteBeigeCappuchino() {
        farbe = BEIGE;
        aktualisiereBild();
    }

    /**
     * Aendert das Aussehen des Leuchtwesen in Gelb.
     */

    public void leuchteGelb() {
        farbe = GELB;
        aktualisiereBild();
    }

    /**
     * Aendert das Aussehen des Leuchtwesen in Lila.
     */

    public void leuteLila() {
        farbe = LILA;
        aktualisiereBild();
    }

    /**
     * Aendert das Aussehen des Leuchtwesen in Rosa.
     */

    public void leuteRosa() {
        farbe = ROSA;
        aktualisiereBild();
    }

    /**
     * Aendert das Aussehen des Leuchtwesen in Rot.
     */

    public void leuchteRot() {
        farbe = ROT;
        aktualisiereBild();
    }

    /**
     * Aendert das Aussehen des Leuchtwesen in Blau.
     */

    public void leuchteBlau() {
        farbe = BLAU;
        aktualisiereBild();
    }

    /**
     * Aendert die Farbe des Leuchtwesen.
     */

    public void wechselFarbe() {
        farbe = farbe.next();
        aktualisiereBild();
    }

    /**
     * Aktualisiert das Bild des Lichtwesen.
     */

    private void aktualisiereBild() {
        setImage(farbe.bild());
    }

}
