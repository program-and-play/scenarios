import util.AnzahlZugriffeErreichtExeption;

import java.util.ArrayList;

public final class TascheMitBegrenztemZugriff extends Tasche {

    private int anzahlMoeglicherZugriffe;

    private int anzahlZugriffe;

    /**
     *
     * @param inhalt
     * @param moeglicheZugriffe
     */
    public TascheMitBegrenztemZugriff(ArrayList<Schluessel> inhalt, int moeglicheZugriffe) {
        super(inhalt);
        this.anzahlMoeglicherZugriffe = moeglicheZugriffe;
        this.anzahlZugriffe = 0;
    }

    /**
     * @return
     */
    @Override
    public Schluessel greifeSchluessel() throws Exception {
        if(anzahlZugriffe >= anzahlMoeglicherZugriffe) {
            throw new AnzahlZugriffeErreichtExeption();
        }
        anzahlZugriffe += 1;
        return super.greifeSchluessel();
    }
}
