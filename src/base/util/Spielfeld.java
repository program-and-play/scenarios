package util;

import greenfoot.*;
import interfaces.GreenfootWorld;
import util.DialogUtils;

import javax.swing.*;
import java.util.*;
import java.lang.*;
import util.WeltSetup;

/**
 * Spielfeldklasse kapselt die Funktionalität des Spielfeldes von der Logik der Welt.
 */
public class Spielfeld {

	private GreenfootWorld welt;
    private WeltSetup setup;

	public Spielfeld(GreenfootWorld welt, WeltSetup setup) {
		this.welt = welt;
        this.setup = setup;
	}

	public void objektHinzufuegen(Actor object, int x, int y) {
        //TODO
        if(x >= setup.getWidth())
            x = setup.getWidth()-1;

        if(y >= setup.getHeight() )
            y = setup.getHeight()-1;

        if(x < 0 || y < 0) {
            String message = "<html>" +
                    "<p>Die Koordinaten müssen einen Wert größer oder gleich 0 besitzen.</p>" +
                    "</html>";

            DialogUtils.showMessageDialogEdt(null, message, "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        welt.addObject(object, x + setup.getOffsetStartToX(), y + setup.getOffsetStartToY());
    }


    public void objektEntfernen(Actor object){
        welt.removeObject(object);
    }

    public List<Actor> gibObjekteAuf(int x, int y, Class cls) {
    	return welt.getObjectsAt(setup.getOffsetStartToX() + x, setup.getOffsetStartToY() + y, cls);
    }

}