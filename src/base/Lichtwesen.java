
import greenfoot.*;
import interfaces.*;
import util.LichtwesenFarbe;

import java.util.ArrayList;


class Lichtwesen extends Figur implements LichtwesenInterface {
    private LichtwesenFarbe farbe;

    public Lichtwesen() {
        farbe = LichtwesenFarbe.BLAU;
        aktualisiereBild();
    }

    private ArrayList<GreenfootImage> getAnimationContainer() {
        return loadImageForAnimation(new GreenfootImage("lichtwesen_beschwören.png"), 6);
    }

    protected void makeAnimation() {
        for (GreenfootImage img : getAnimationContainer()) {
            setImage(img);
            Greenfoot.delay(2);
        }
        this.resetImage();
    }

    public void leuchteBeigeCappuchino() {
        farbe = LichtwesenFarbe.BEIGE;
        aktualisiereBild();
    }

    public void leuchteGelb() {
        farbe = LichtwesenFarbe.GELB;
        aktualisiereBild();

    }

    public void leuteLila() {
        farbe = LichtwesenFarbe.LILA;
        aktualisiereBild();
    }

    public void leuteRosa() {
        farbe = LichtwesenFarbe.ROSA;
        aktualisiereBild();
    }

    public void leuchteRot() {
        farbe = LichtwesenFarbe.ROT;
        aktualisiereBild();

    }

    public void leuchteBlau() {
        farbe = LichtwesenFarbe.BLAU;
        aktualisiereBild();

    }

    public void wechselFarbe() {
        farbe = farbe.next();
        aktualisiereBild();
    }

    private void aktualisiereBild() {
        setImage(farbe.bild());
    }

}
