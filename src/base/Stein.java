
import greenfoot.*;

import java.util.ArrayList;


public class Stein extends Figur implements Charakter.Aktioner{

    ArrayList<GreenfootImage> zerbechenBild;
    int i;

    public Stein() {
        super(FigurTyp.Stein);
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
