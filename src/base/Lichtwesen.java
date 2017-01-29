
import greenfoot.*;
import interfaces.*;
import util.LichtwesenFarbe;

import java.util.ArrayList;


class Lichtwesen extends Figur implements LichtwesenInterface {
    private LichtwesenFarbe farbe;
    private ArrayList<GreenfootImage> bewegung;

    public Lichtwesen() {
        farbe = LichtwesenFarbe.BLAU;
        bewegung = loadImageForAnimation(new GreenfootImage("lichtwesen_beschwören.png"), 6);
        aktualisiereBild();
    }

    /**
     * Das Lichtwesen bewegt sich auf der Stelle.
     */

    public void bewegen() {
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
        farbe = LichtwesenFarbe.BEIGE;
        aktualisiereBild();
    }

    /**
     * Aendert das Aussehen des Leuchtwesen in Gelb.
     */

    public void leuchteGelb() {
        farbe = LichtwesenFarbe.GELB;
        aktualisiereBild();
    }

    /**
     * Aendert das Aussehen des Leuchtwesen in Lila.
     */

    public void leuteLila() {
        farbe = LichtwesenFarbe.LILA;
        aktualisiereBild();
    }

    /**
     * Aendert das Aussehen des Leuchtwesen in Rosa.
     */

    public void leuteRosa() {
        farbe = LichtwesenFarbe.ROSA;
        aktualisiereBild();
    }

    /**
     * Aendert das Aussehen des Leuchtwesen in Rot.
     */

    public void leuchteRot() {
        farbe = LichtwesenFarbe.ROT;
        aktualisiereBild();
    }

    /**
     * Aendert das Aussehen des Leuchtwesen in Blau.
     */

    public void leuchteBlau() {
        farbe = LichtwesenFarbe.BLAU;
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
