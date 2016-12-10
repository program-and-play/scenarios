/**
 * Write a description of class Zauberer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Zauberer extends Charakter {
    private static Zauberer instance;

    protected static Zauberer getInstance () {
        if (Zauberer.instance == null) {
            Zauberer.instance = new Zauberer ();
        }
        return Zauberer.instance;
    }

    private Zauberer() {
        super(FigureTyp.Zauber);
    }

    /**
     * Act - do whatever the Zauberer wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        if (worldEndFront())
            turnRight();
        move();
        // Add your action code here.
    }




}
