import greenfoot.World;
import util.Spielfeld;

public class Amulett {
    private static Amulett instance;

    private Spielfeld welt;

    /**
     * Erzeugt eine Instance vom Amulett, existiert schon ein Amulett, wird dieser zurueck gegeben.
     *
     * @return Amulett
     */

    public static Amulett erzeugeInstance(Spielfeld welt) {
        if (Amulett.instance == null) {
            Amulett.instance = new Amulett(welt);
        }
        return Amulett.instance;
    }

    public Amulett(Spielfeld welt) {
        this.welt = welt;
    }

    /**
     * Erzeugt ein Steinbeisser auf dem Spielfeld
     *
     * @return Steinbeisser
     */

    public Steinbeisser rufeSteinbeißer() {
        Steinbeisser beisser = Steinbeisser.erzeugeInstance();
        welt.objektHinzufuegen(beisser,0,0);
        return beisser;
    }


}
