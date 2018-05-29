import util.ActorPosition;

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
       super.act();
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


}
