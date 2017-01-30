import greenfoot.Greenfoot;
import util.WeltSetup;

import java.util.List;

public class Charakter extends Figur {

    private FigurTyp typ;

    protected Charakter(FigurTyp typ) {
        super(createImage(typ.path), 4, 4);
        this.typ = typ;
    }

    public void laufen() {
        if (theWorldsEnd(getCurrentDirection(), 1, this)) {
            showWarning("", //Optional fuer den englischen Text.
                    "Der Charakter kann sich nicht bewegen, da die Welt zu Ende ist!");
            return;
        }

        Factory.laufen(this);
        Greenfoot.delay(1);
    }

    public FigurTyp getTyp() {
        return typ;
    }

    interface Aktion { void apply(Aktioner arg1); }

    interface Aktioner{void aktion();}

    protected void moveActors(Direction direction, Figur... actors){
       moveActors(direction, null ,(z)-> {}, actors);
    }

    /**
     * Beweget den/die Actor(s) um einen Schritt in die ausgewaehlte Richtung, dabei wird die Bewegung animiert.
     *
     * @param actors    der/die Actor(s) der/die bewegt werden sollen
     * @param direction in welche Richtung  sie gehen sollen
     */
    protected void moveActors(Direction direction, Aktioner x, Aktion op, Figur... actors) {
        WeltSetup foo = Factory.getSetup();
        if (direction.equals(Direction.LEFT) || direction.equals(Direction.UP)) {
            if (direction.equals(Direction.UP))
                for (Figur figure : actors) {
                    figure.setLocationWithoutOffset(figure.getX(),
                            modulo((figure.getY() - 1), foo.getHeight()));
                }

            else if (direction.equals(Direction.LEFT))
                for (Figur figure : actors) {
                    figure.setLocationWithoutOffset(
                            modulo((figure.getX() - 1), foo.getWidth()),
                            figure.getY());
                }

            for (int j = 0, k = getWorld().getCellSize()*2; j < 5; j++, k = k - getWorld().getCellSize()*2/5) {
                changeAnimationImage(k, direction, actors);
                op.apply(x);
                Greenfoot.delay(2);
            }
            for (Figur figure : actors) {
                figure.resetImage();
            }
        } else {
            for (int j = 0, k = 0; j < 5; j++, k = k + getWorld().getCellSize()*2/5) {
                changeAnimationImage(k, direction, actors);
                op.apply(x);
                Greenfoot.delay(2);
            }

            if (direction.equals(Direction.DOWN))
                for (Figur figure : actors) {
                    figure.resetImage();
                    figure.setLocationWithoutOffset(figure.getX(),
                            modulo((figure.getY() + 1), foo.getHeight()));
                }
            else if (direction.equals(Direction.RIGHT))
                for (Figur figure : actors) {
                    figure.resetImage();
                    figure.setLocationWithoutOffset(
                            modulo((figure.getX() + 1), foo.getWidth()), figure.getY());
                }
        }

    }


    /**
     * Charakter dreht sich um 90° nach links
     */

    public void linkswendung() {
        setCurrentDirection(getCurrentDirection().rotationLeft());
        resetImage();
        Greenfoot.delay(1);
    }

    /**
     * Charakter dreht sich um 90° nach rechts
     */

    public void rechtswendung() {
        setCurrentDirection(getCurrentDirection().rotationRight());
        resetImage();
        Greenfoot.delay(1);
    }


    /**
     * Ein spezielle Modulo-Operator der ausschliesslich positive Zahlen zurueck gibt.
     *
     * @param a der erste Wert
     * @param b der zweite Wert
     * @return Das Ergebnis der Modulo-Pperation
     */
    private int modulo(int a, int b) {
        return (a % b + b) % b;
    }


    protected Object getObjectInFront(Direction direction, int steps, Class<?> clazz) {
        int x = getX();
        int y = getY();
        WeltSetup foo = Factory.getSetup();

        switch (direction) {
            case RIGHT:
                x = modulo((x + steps), foo.getWidth());
                break;

            case DOWN:
                y = modulo((y + steps), foo.getHeight());
                break;

            case LEFT:
                x = modulo((x - steps), foo.getWidth());
                break;

            case UP:
                y = modulo((y - steps), foo.getHeight());
                break;

            default: // Not a valid direction
                return null;
        }


        List<?> objects = getWorld().erhalteSpielfeld().gibObjekteAuf(x, y, clazz);

        if (objects != null && objects.size() > 0) {
            return objects.get(0);
        } else {
            return null;
        }
    }

    /**
     * Ueberprueft, ob die Welt vor dem Character, zu Ende ist.
     *
     * @return boolean
     */

    public boolean istWeltzuEnde() {
        return theWorldsEnd(getCurrentDirection(), 1, this);
    }

}
