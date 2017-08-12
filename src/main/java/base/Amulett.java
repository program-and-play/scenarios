import greenfoot.World;
import util.Spielfeld;
import util.WeltSetup;

public final class Amulett {
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

    public Steinbeisser rufeSteinbeisser() {
        WeltSetup.ActorPosition actorPosition = new WeltSetup.ActorPosition();
        actorPosition.setActor("Steinbeisser");
        actorPosition.setX(0);
        actorPosition.setY(0);
        Steinbeisser beisser = Steinbeisser.erzeugeInstance(actorPosition);
        welt.objektHinzufuegen(beisser,0,0);
        return beisser;
    }


}
