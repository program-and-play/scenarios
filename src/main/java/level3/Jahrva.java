import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Jahrva ist der Hauptcharacter dieses kleines Spiels.
 *
 */

public final class Jahrva extends Charakter {
    private static Jahrva instance;
    
    private Jahrva() {
        super(FigurTyp.Jahrva);
    }

    /**
     * Erzeugt eine Instance von Jahrva, existiert schon eine Instance von Jahrva, wird dieser zurueck gegeben.
     * @return Jahrva
     */

    protected static Jahrva erzeugeInstance () {
        if (Jahrva.instance == null) {
            Jahrva.instance = new Jahrva();
        }
        return Jahrva.instance;
    }
    
    @Override
    public void act() {
        super.act();
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
        Lichtwesen tmp = new Lichtwesen();
        getWorld().erhalteSpielfeld().objektHinzufuegen(tmp, x, y);
        tmp.animiere();
    }

    public int random(int minZahl, int maxZahl){
        return new Random().nextInt(maxZahl - minZahl + 1) + minZahl;
    }





}
