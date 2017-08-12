import util.LeereTascheException;

import java.util.ArrayList;
import java.util.Random;

/**
 * Implementierung einer Tasche
 */
public class Tasche {

    private ArrayList<Schluessel> inhalt;

    public Tasche(ArrayList<Schluessel> inhalt) {
        this.inhalt = inhalt;
    }

    /**
     *
     * @return ein zufaelliger Schluessel
     */
    public Schluessel greifeSchluessel() throws Exception {
        int anzahl = inhalt.size();
        if(anzahl <= 0) {
            throw new LeereTascheException();
        }
        return inhalt.remove(new Random().nextInt(anzahl));
    }
}
