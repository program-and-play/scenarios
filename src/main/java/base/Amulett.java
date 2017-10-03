import util.ActorPosition;
import util.Spielfeld;

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
     * Erzeugt ein Scalen auf dem Spielfeld
     *
     * @return Scalen
     */

    public Scalen rufeSteinbeisser() {
        ActorPosition actorPosition = new ActorPosition();
        actorPosition.setActor("Scalen");
        actorPosition.setX(0);
        actorPosition.setY(0);
        Scalen beisser = Scalen.erzeugeInstance(actorPosition);
        welt.objektHinzufuegen(beisser,0,0);
        return beisser;
    }


}
