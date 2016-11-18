import greenfoot.*;

import java.util.*;
import java.lang.*;

/**
 * The playground class encapsulates actions which work directly on the playground.
 */
public class Playground {

	private EmptyWorld world;

	public Playground(EmptyWorld world) {
		this.world = world;
	}

	public void addObject(Actor object, int x, int y) {
        world.addObjectWithoutOffset(object, x, y);
    }

    public void removeObject(Actor object){
        world.removeObject(object);
    }

    public List<Actor> getObjectsAt(int x, int y, Class cls) {
    	return world.getObjectsAt(world.getOffsetStartToX() + x, world.getOffsetStartToY() + y, cls);
    }

}