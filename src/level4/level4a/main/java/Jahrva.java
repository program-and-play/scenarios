import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import util.ActorPosition;
import util.Spielfeld;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Jahrva ist der Hauptcharacter dieses kleines Spiels.
 */

public final class Jahrva extends Charakter {
    private static Jahrva instance;

    private Jahrva(ActorPosition startPosition) {
        super(FigurTyp.Jahrva, startPosition);
    }

    @Override
    public void act() {
    }

    @Override
    public void laufen() {
        super.laufen();
    }

    @Override
    public void nachLinksDrehen() {
        super.nachLinksDrehen();
    }

    @Override
    public void nachRechtsDrehen() {
        super.nachRechtsDrehen();
    }

    /**
     * Erzeugt eine Instance von Jahrva, existiert schon eine Instance von Jahrva, wird dieser zurueck gegeben.
     *
     * @return Jahrva
     */

    protected static Jahrva erzeugeInstance(ActorPosition startPosition) {
        if (Jahrva.instance == null) {
            Jahrva.instance = new Jahrva(startPosition);
        }
        return Jahrva.instance;
    }

    public void erzeugeLichtwesen(int x, int y) {
        if (Factory.getSetup().getHeight() < y || Factory.getSetup().getWidth() < x || x < 0 || y < 0) {
            showWarning(
                    "", //Optional fuer den englischen Text.
                    "Jahrva kann kein Lichtwesen erzeugen, da die Indizes ausserhalb der Welt gewaehlt wurden.");
            return;
        }

        final String KOERPER_FILE = "character_body.png";
        final String ZAUBERSTAB_FILE = "stab_lichtwesenbeschwoeren.png";
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
        ActorPosition actorPosition = new ActorPosition();
        actorPosition.setActor("Lichtwesen");
        actorPosition.setX(x);
        actorPosition.setY(y);
        Lichtwesen tmp = new Lichtwesen(actorPosition);
        Spielfeld spielfeld = getWorld().erhalteSpielfeld();
        spielfeld.entferneObjekteAuf(x, y, Steinillusion.class);

        spielfeld.objektHinzufuegen(tmp, x, y);
        tmp.animiere();
    }

    private Scalen rufeScalen() {
        Scalen scalen = Scalen.getInstance();
        if (scalen != null) {
            return scalen;
        }
        ActorPosition actorPosition = findeFreiePosition();
        if (actorPosition == null) {
            return null;
        }

        scalen = Scalen.erzeugeInstance(actorPosition);
        getWorld().erhalteSpielfeld().objektHinzufuegen(scalen, actorPosition.getX(), actorPosition.getY());
        return scalen;
    }

    private ActorPosition findeFreiePosition() {
        ActorPosition actorPosition = new ActorPosition();
        int x = getX();
        int y = getY();
        Spielfeld spielfeld = getWorld().erhalteSpielfeld();
        if (spielfeld.gibObjekteAuf(x, y + 1, Actor.class).isEmpty()) {
            y = y + 1;
        } else {
            loop:
            for (int i = -1; i < 2; i++) {
                innerLoop:
                for (int j = -1; j < 2; j++) {
                    if (!(Factory.getSetup().getHeight() < y + j || Factory.getSetup().getWidth() < x + i || x + i < 0 || y + j < 0) && spielfeld.gibObjekteAuf(x + i, y + j, Actor.class).isEmpty()) {
                        x = x + i;
                        y = y + j;
                        break loop;
                    } else if (i == 1 && j == 1) {
                        return null;
                    }
                }
            }
        }
        actorPosition.setX(x);
        actorPosition.setY(y);

        return actorPosition;
    }
    public void geheUmYX(int xSchritte, int ySchritte){
        if(ySchritte<0){
            dreheNachSueden();
            geheSchritte(-ySchritte);
        }
        if(ySchritte>0){
            dreheNachNorden();
            geheSchritte(ySchritte);
        }
        if(xSchritte<0){
            dreheNachWesten();
            geheSchritte(-xSchritte);
        }
        if(xSchritte>0){
            dreheNachOsten();
            geheSchritte(xSchritte);
        }

    }
    /**
     * Geht zuerst x-Schritte nach Osten oder Westen und dann y-Schritte nach Norden oder Sueden
     * @param xSchritte
     * @param ySchritte
     */
    public void geheUmXY(int xSchritte, int ySchritte){
        if(xSchritte<0){
            dreheNachWesten();
            geheSchritte(-xSchritte);
        }
        if(xSchritte>0){
            dreheNachOsten();
            geheSchritte(xSchritte);
        }

        if(ySchritte<0){
            dreheNachSueden();
            geheSchritte(-ySchritte);
        }
        if(ySchritte>0){
            dreheNachNorden();
            geheSchritte(ySchritte);
        }
    }
    public void geheSchritte(int anzahl){
        for(int zaehler=1;zaehler<=anzahl;zaehler++){
            laufen();
        }
    }
    public void dreheNachWesten() {
        if (blickrichtung().istNorden()) {
            nachLinksDrehen();
        }
        if (blickrichtung().istSueden()) {
            nachRechtsDrehen();
        }
        if (blickrichtung().istOsten()) {
            for (int i = 0; i < 2; i++) {
                nachLinksDrehen();
            }
        }
    }

    public void dreheNachSueden() {
        if (blickrichtung().istWesten()) {
            nachLinksDrehen();
        }
        if (blickrichtung().istOsten()) {
            nachRechtsDrehen();
        }
        if (blickrichtung().istNorden()) {
            for (int i = 0; i < 2; i++) {
                nachLinksDrehen();
            }
        }
    }

    public void dreheNachNorden() {
        if (blickrichtung().istOsten()) {
            nachLinksDrehen();
        }
        if (blickrichtung().istWesten()) {
            nachRechtsDrehen();
        }
        if (blickrichtung().istSueden()) {
            for (int i = 0; i < 2; i++) {
                nachLinksDrehen();
            }
        }
    }
    public void dreheNachOsten(){
        if(blickrichtung().istNorden()){
            nachRechtsDrehen();
        }
        if(blickrichtung().istSueden()){
            nachLinksDrehen();
        }
        if(blickrichtung().istWesten()){
            nachRechtsDrehen();
            nachRechtsDrehen();
        }
    }

}
