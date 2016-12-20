/**
 * Write a description of class Steinbeisser here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Steinbeisser extends Charakter
{
    private static Steinbeisser instance;

    protected static Steinbeisser getInstance () {
        if (Steinbeisser.instance == null) {
            Steinbeisser.instance = new Steinbeisser ();
        }
        return Steinbeisser.instance;
    }

    private Steinbeisser() {
        super(FigureTyp.Steinbeisser);
    }
    /**
     * Act - do whatever the Steinbeisser wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }

    public void eatStone(){
        //Direction direction
        // Charakter this
        // Figur stone

        //TODO
    }
}
