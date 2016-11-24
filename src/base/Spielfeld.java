import greenfoot.*;

import java.util.*;
import java.lang.*;

/**
 * Spielfeldklasse kapselt die Funktionalität des Spielfeldes von der Logik der Welt.
 */
public class Spielfeld {

	private LeereWelt welt;

	public Spielfeld(LeereWelt welt) {
		this.welt = welt;
	}

	public void objektHinzufuegen(Actor object, int x, int y) {
        welt.addObjectWithoutOffset(object, x, y);
    }

    public void objektEntfernen(Actor object){
        welt.removeObject(object);
    }

    public List<Actor> gibObjekteAuf(int x, int y, Class cls) {
    	return welt.getObjectsAt(welt.getOffsetStartToX() + x, welt.getOffsetStartToY() + y, cls);
    }

}