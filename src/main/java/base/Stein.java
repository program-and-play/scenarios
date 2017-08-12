
import greenfoot.*;
import util.WeltSetup;

import java.util.ArrayList;


public final class Stein extends Figur implements Charakter.Aktioner{

    ArrayList<GreenfootImage> zerbechenBild;
    int i;

    public Stein(WeltSetup.ActorPosition startPosition) {
        super(FigurTyp.Stein, startPosition);
        zerbechenBild = loadImageForAnimation( new GreenfootImage("stein_animation.png"),  4);
        i = 0;
    }

    protected void zerbreche() {
        if(++i < zerbechenBild.size())
        setImage(zerbechenBild.get(i));
    }


    @Override
    public void aktion() {
        zerbreche();
    }
}
