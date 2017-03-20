import greenfoot.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Der Steinbeisser ist der Sidekick von Jahrva. Er ist ein hilfreicher Begleiter, mit coolen Faehigkeiten.
 *
 * @author Lukas Hettwer
 * @version 0.1
 */

public class Steinbeisser extends Charakter {
    private static Steinbeisser instance;
    private int zerstoerteSteine;
    private int maxZerstoerteSteine;

    /**
     * Erzeugt eine Instance vom Steinbeisser, existiert schon ein Steinbeisser, wird dieser zurueck gegeben.
     *
     * @return Steinbeisser
     */

    public static Steinbeisser erzeugeInstance() {
        if (Steinbeisser.instance == null) {
            Steinbeisser.instance = new Steinbeisser();
        }
        return Steinbeisser.instance;
    }

    private Steinbeisser() {
        super(FigurTyp.Steinbeisser);
        zerstoerteSteine = 0;
        maxZerstoerteSteine = 3;
    }

    /**
     * In der Act Methode wird das ausgefuehrt was der Steinbeisser tun soll. Diese Methode wird immer wieder aufgerufen,
     * wenn der 'Act' oder 'Run' Button gedrueckt wurde.
     */
    public void act() {
        // Fuege hier deine "Befehle" fuer den Steinbeisser ein.
    }

    /**
     * Zerbeisst einen Stein direkt vor dem Steinbeisser, wenn dort einer Liegt.
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
            showWarning("", "Die Welt ist zu Ende, der Steinbeisser kann keinen Stein außerhalb der Welt zerbeissen!");
            return;
        }
        if (foo == null) {
            showWarning("", "Vor dem Steinbeisser befindet sich nicht ein Stein!");
            return;
        }
        if(++zerstoerteSteine > maxZerstoerteSteine){
            showWarning("", "Der Steinbeisser kann maximal " + maxZerstoerteSteine + " Steine zerstören!");
            return;
        }
        final String KOERPER_FILE = "steinbeißer animation_neu.png";
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
}
