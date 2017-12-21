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
        //sequenz();
        //einParameter();
        zweiParameter();
    }
    private void sequenz(){
        laufen();

        nachRechtsDrehen();
        laufen();
        laufen();

        nachLinksDrehen();
        laufen();

        nachRechtsDrehen();
        laufen();

        nachLinksDrehen();
        laufen();
        laufen();
        laufen();

        nachLinksDrehen();
        laufen();
        laufen();
        laufen();

        nachRechtsDrehen();
        laufen();
        laufen();

        nachRechtsDrehen();
        laufen();
        laufen();
        laufen();
        laufen();

    }
    private void einParameter(){
        geheSchritte(1);

        nachRechtsDrehen();
        geheSchritte(2);

        nachLinksDrehen();
        geheSchritte(1);

        nachRechtsDrehen();
        geheSchritte(1);

        nachLinksDrehen();
        geheSchritte(3);

        nachLinksDrehen();
        geheSchritte(3);

        nachRechtsDrehen();
        geheSchritte(2);

        nachRechtsDrehen();
        geheSchritte(4);
    }
    private void zweiParameter(){
        geheUmXY(1,-2);
        geheUmXY(1,-1);
        geheUmXY(3,3);
        geheUmXY(2,-4);
    }

    /*
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

    public void dreheNachSueden() {
        if (blickrichtung().istWesten()) {
            nachLinksDrehen();
        }
        if (blickrichtung().istOsten()) {
            nachRechtsDrehen();
        }
        if (blickrichtung().istNorden()) {
            for (int i = 0; i < 2; i++) {
                nachLinksDrehen();
            }
        }
    }

    public void dreheNachNorden() {
        if (blickrichtung().istOsten()) {
            nachLinksDrehen();
        }
        if (blickrichtung().istWesten()) {
            nachRechtsDrehen();
        }
        if (blickrichtung().istSueden()) {
            for (int i = 0; i < 2; i++) {
                nachLinksDrehen();
            }
        }
    }
    */

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
