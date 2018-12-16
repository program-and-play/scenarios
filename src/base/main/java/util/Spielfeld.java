package util;

import greenfoot.*;
import interfaces.GreenfootWorld;

import javax.swing.*;
import java.util.*;
import java.lang.*;
import java.util.stream.Collectors;

/**
 * Spielfeldklasse kapselt die Funktionalitaet des Spielfeldes von der Logik der Welt.
 */
public final class Spielfeld {
    
    private GreenfootWorld welt;
    private WeltSetup setup;
    
    public Spielfeld(GreenfootWorld welt, WeltSetup setup) {
        this.welt = welt;
        this.setup = setup;
    }
    
    public GreenfootWorld getWelt() {
        return welt;
    }
    
    public void objektHinzufuegen(Actor object, int x, int y) {
        if (x >= setup.getWidth())
            x = setup.getWidth() - 1;
        
        if (y >= setup.getHeight())
            y = setup.getHeight() - 1;
        
        if (x < 0 || y < 0) {
            String message = "<html>" +
                    "<p>Die Koordinaten muessen einen Wert groesser oder gleich 0 besitzen.</p>" +
                    "</html>";
            
            DialogUtils.showMessageDialogEdt(null, message, "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        welt.addObject(object, x + setup.getOffsetStartToX(), y + setup.getOffsetStartToY());
    }
    
    
    public void objektEntfernen(Actor object) {
        welt.removeObject(object);
    }
    
    public void entferneObjekteAuf(int x, int y, Class cls) {
        Actor actor = gibObjektAuf(x, y, cls);
        if(actor != null) {
            objektEntfernen(actor);
        }
    }

    public boolean isEmpty(int x, int y) {
        for (Class c : getAllActors().stream().map(a -> a.getClass()).distinct().collect(Collectors.toList())){
            if (gibObjektAuf(x,y, c)!= null)
                return false;
        }
        return true;
    }
    
    public List<Actor> gibObjekteAuf(int x, int y, Class cls) {
        return welt.getObjectsAt(setup.getOffsetStartToX() + x, setup.getOffsetStartToY() + y, cls);
    }
    
    public Actor gibObjektAuf(int x, int y, Class cls) {
        List<Actor> foo = welt.getObjectsAt(setup.getOffsetStartToX() + x, setup.getOffsetStartToY() + y, cls);
        if (foo.size() > 0)
            return foo.get(0);
        return null;
    }
    
    public List<Actor> getAllActors() {
        List<Actor> actor = new ArrayList<>();
        for (int y = 0; y < setup.getHeight(); y++) {
            for (int x = 0; x < setup.getWidth(); x++) {
                actor.addAll(gibObjekteAuf(x, y, Actor.class));
            }
        }
        return actor;
    }
    
}