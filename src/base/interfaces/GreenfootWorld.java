package interfaces;

import greenfoot.Actor;

import java.util.Collection;
import java.util.List;


public interface GreenfootWorld {

    public void addObject(Actor object, int x, int y);

    public <A> List<A> getObjectsAt(int x, int y, Class<A> cls);

    public void removeObject(Actor object);
}
