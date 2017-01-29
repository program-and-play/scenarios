/**
 * Der Steinbeisser ist der Sidekick des Zaubers. Er ist ein hilfreicher Begleiter, mit coolen Faehigkeiten.
 *
 * @author Lukas Hettwer
 *
 * @version 0.1
 */

public class Steinbeisser extends Charakter
{
    private static Steinbeisser instance;

    /**
     * Erzeugt eine Instance vom Steinbeisser, existiert schon ein Steinbeisser, wird dieser zurueck gegeben.
     * @return Steinbeisser
     */

    protected static Steinbeisser erzeugeInstance () {
        if (Steinbeisser.instance == null) {
            Steinbeisser.instance = new Steinbeisser ();
        }
        return Steinbeisser.instance;
    }

    private Steinbeisser() {
        super(FigurTyp.Steinbeisser);
    }

    /**
     * In der Act Methode wird das ausgefuehrt was der Zauberer tun soll. Diese Methode wird immer wieder aufgerufen,
     * wenn der 'Act' oder 'Run' Button gedrueckt wurde.
     */
    public void act() 
    {
        // Fuege hier deine "Befehle" fuer den Steinbeisser ein.
    }

    /**
     * Zerbeisst einen Stein direkt vor dem Steinbeisser, wenn dort einer Liegt.
     */

    public void zerbeisseStein(){
        //Direction direction
        // Charakter this
        // Figur stone
        //TODO
    }
}
