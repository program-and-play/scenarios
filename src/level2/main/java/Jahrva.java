import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import util.ActorPosition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Jahrva ist der Hauptcharacter dieses kleines Spiels.
 *
 */

public final class Jahrva extends Charakter {
    private static Jahrva instance;
    
    private Jahrva(ActorPosition startPosition) {
        super(FigurTyp.Jahrva, startPosition);
    }

    @Override
    public void act() {
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
}
