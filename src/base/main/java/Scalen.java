import greenfoot.*;
import util.ActorPosition;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Der Scalen ist der Sidekick von Jahrva. Er ist ein hilfreicher Begleiter, mit coolen Faehigkeiten.
 *
 */

public class Scalen extends Charakter {

    private static Scalen instance;
    private int zerstoerteSteine;
    private int maxZerstoerteSteine;
    private static boolean isPresent=false;
    
    private Scalen(ActorPosition startPosition) {
        super(FigurTyp.Steinbeisser, startPosition);
        zerstoerteSteine = 0;
        maxZerstoerteSteine = 3;
    }

    public static void resetScalen() {
        setIsPresent(false);
        instance=null;
    }

    public static boolean isPresent() {
        return isPresent;
    }

    public static void setIsPresent(boolean isPresent) {
        Scalen.isPresent = isPresent;
    }

    /**
     * Erzeugt eine Instance vom Scalen, existiert schon ein Scalen, wird dieser zurueck gegeben.
     *
     * @return Scalen
     */

    public static Scalen erzeugeInstance(ActorPosition startPosition) {
        if (Scalen.instance == null) {
            Scalen.instance = new Scalen(startPosition);
            isPresent = true;
        }
        return Scalen.instance;
    }

    /**
     * Zerbeisst einen Stein direkt vor dem Scalen, wenn dort einer Liegt.
     */

    public void zerbeisseStein() {
        Stein foo;
        switch (getCurrentDirection()) {
            case RIGHT:
                foo = (Stein)  getWorld().erhalteSpielfeld().gibObjektAuf(getX() + 1, getY(), Stein.class);
                break;
            case DOWN:
                foo = (Stein) getWorld().erhalteSpielfeld().gibObjektAuf(getX(), getY() + 1, Stein.class);
                break;
            case LEFT:
                foo = (Stein)  getWorld().erhalteSpielfeld().gibObjektAuf(getX() - 1, getY(), Stein.class);
                break;
            case UP:
                foo = (Stein)  getWorld().erhalteSpielfeld().gibObjektAuf(getX(), getY() - 1, Stein.class);
                break;
            default: // Not a valid direction
                foo = null;
                break;
        }
        if (istWeltzuEnde()) {
            showWarning("", "Die Welt ist zu Ende, der Scalen kann keinen Stein ausserhalb der Welt zerbeissen!",true);
            return;
        }
        if (foo == null) {
            showWarning("", "Vor Scalen befindet sich kein Stein!",true);
            return;
        }
        if (foo.getClass() == Steinillusion.class) {
            showWarning("", "Scalen beisst ins Leere!Das ist kein echter Stein!",true);
            return;
        }
        if(++zerstoerteSteine > maxZerstoerteSteine){
            showWarning("", "Der Scalen kann maximal " + maxZerstoerteSteine + " Steine zerstoeren!",true);
            return;
        }
        final String KOERPER_FILE = "steinbeisser animation_neu.png";
        GreenfootImage figurSteinbeisser = new GreenfootImage(KOERPER_FILE);
        figurSteinbeisser.scale(240, 240);
        HashMap<Direction, ArrayList<GreenfootImage>> speicherStandartContainer = getImageContainer();
        int pointer = getImagePointer();

        setImageContainer(loadImage(figurSteinbeisser, 4, 4));
        setImagePointer(0);
        moveActors(getCurrentDirection(),  foo, (Aktioner::aktion), this);

        setImageContainer(speicherStandartContainer);
        setImagePointer(pointer);
        getWorld().removeObject(foo);
    }

    public static Scalen getInstance() {
        return instance;
    }
}
