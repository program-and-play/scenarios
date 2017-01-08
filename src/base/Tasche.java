import util.LeereTascheException;

import java.util.ArrayList;
import java.util.Random;

/**
 * Implementierung einer Tasche
 */
public class Tasche {

    private ArrayList<Schlüssel> inhalt;

    public Tasche(ArrayList<Schlüssel> inhalt) {
        this.inhalt = inhalt;
    }

    /**
     *
     * @return ein zufälliger Schlüssel
     */
    public Schlüssel greifeSchlüssel() throws LeereTascheException {
        int anzahl = inhalt.size();
        if(anzahl <= 0) {
            throw new LeereTascheException();
        }
        return inhalt.remove(new Random().nextInt(anzahl));
    }
}
