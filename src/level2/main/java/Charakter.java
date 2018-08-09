import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import util.ActorPosition;
import util.WeltSetup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Charakter extends Figur {

    private FigurTyp typ;

    protected Charakter(FigurTyp typ, ActorPosition startPosition) {
        super(createImage(typ.path), 4, 4, startPosition);
        this.typ = typ;
    }




}
