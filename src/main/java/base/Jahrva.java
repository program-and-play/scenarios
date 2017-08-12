import util.WeltSetup;

/**
 * Jahrva ist der Hauptcharacter dieses kleines Spiels.
 *
 */

public final class Jahrva extends Charakter {
    private static Jahrva instance;

    
    private Jahrva(WeltSetup.ActorPosition actorPosition) {
        super(FigurTyp.Jahrva,actorPosition);
    }

    /**
     * Erzeugt eine Instance von Jahrva, existiert schon eine Instance von Jahrva, wird dieser zurueck gegeben.
     * @return Jahrva
     * @param actorPosition
     */

    protected static Jahrva erzeugeInstance(WeltSetup.ActorPosition actorPosition) {
        if (Jahrva.instance == null) {
            Jahrva.instance = new Jahrva(actorPosition);
        }
        return Jahrva.instance;
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
}
