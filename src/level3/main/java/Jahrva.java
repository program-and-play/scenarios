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
    public boolean istBlickrichtungNorden(){
        return blickrichtung().istNorden();
    }
    public boolean istBlickrichtungSueden(){
        return blickrichtung().istSueden();
    }
    public boolean istBlickrichtungWesten(){
        return blickrichtung().istWesten();
    }
    public boolean istBlickrichtungOsten(){
        return blickrichtung().istOsten();
    }

    public void dreheNachSueden(){
        super.dreheNachSueden();
    }

    public void dreheNachWesten(){
        super.dreheNachWesten();
    }


}
