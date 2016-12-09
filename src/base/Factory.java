import greenfoot.Actor;
import util.DialogUtils;
import util.WeltSetup;

import javax.swing.*;
import java.io.File;
import java.io.IOException;


/**
 * Created by lukashettwer on 26.11.16.
 */
public class Factory {

    public static WeltSetup createWorldSetup(String path){
        // This code is executed when the class is loaded,
        // BEFORE the constructor is called
        //TODO das muss besser ausgelagert werden
        WeltSetup setup = null;
        try {
            File file = WeltSetup.findMatchingFiles(path, LeereWelt.class);
            if (file != null) {
                setup = WeltSetup.createWorldSetup(WeltSetup.readAllLines(file));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return setup;
    }

    public static void initActorsFromWorldSetup(WeltSetup setup, Spielfeld playground) {
        for (WeltSetup.ActorPosition actorPosition : setup.getActors()) {
            Actor tmp = null;
            switch (actorPosition.getActor()) {
                case "Zauberer":
                    tmp = Zauberer.getInstance();
                    break;
                case "Stein":
                    tmp = new Stein();
                    break;
                case "Lichtwesen":
                    tmp = new Lichtwesen();
                    break;
            }
            if (tmp != null)
                playground.objektHinzufuegen(tmp, actorPosition.getX(), actorPosition.getY());
        }


    }


}
