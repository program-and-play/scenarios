
public class Amulett  
{
    private static Amulett instance;

    /**
     * Erzeugt eine Instance vom Amulett, existiert schon ein Amulett, wird dieser zurueck gegeben.
     *
     * @return Amulett
     */

    public static Amulett erzeugeInstance() {
        if (Amulett.instance == null) {
            Amulett.instance = new Amulett();
        }
        return Amulett.instance;
    }

    public Amulett()
    {

    }

    /**
     * Erzeugt ein Steinbeisser auf dem Spielfeld
     *
     * @return Steinbeisser
     */

    public Steinbeisser erzeugeSteinbeisser() {
        Steinbeisser beisser = Steinbeisser.erzeugeInstance();
        return beisser;
    }


}
