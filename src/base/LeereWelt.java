import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.World;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Write a description of class LeereWelt here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LeereWelt extends World {

    private boolean worldIsDark;

    private static final String WORLD_SETUP_FILE = "WeltSetup.json";

    private ArrayList<String> GROUNDPATH;

    private GreenfootImage levelScreen;

    private Spielfeld playground;

    // Size of one cell
    public static final int CELL_SIZE = 60;

    //TODO die richtigen Anzeigebilder für die class in dem Anzeigebaum von Greenfoot integrieren.

    // all actor icons (this is a trick to get the default image that was chosen for actors)
    public static final GreenfootImage ICON_CHARACTER = new Figur().getImage();
    public static final GreenfootImage ICON_LEAF = new Lichtwesen().getImage();
    public static final GreenfootImage ICON_STONE = new Stein().getImage();
    //TODO funktioniert nicht
    //   public static final GreenfootImage ICON_STONE_ON_TARGET = findOnTargetImage(ICON_STONE, "_on_target");

    //    public static final GreenfootImage ICON_TREE = new Tree().getImage();
    //    public static final GreenfootImage ICON_KARA = new Kara().getImage();
    //    public static final GreenfootImage ICON_MY_KARA = new MyKara().getImage();

    public static final String WORLD_SETUP_TITLE_KEY = "World:";
    public static final String KARA_DIRECTION_KEY = "Kara:";

    private int randomValue;

    private int offsetStartToX;

    private int offsetStartToY;

    private int offsetXToEnd;

    private int offsetYToEnd;

    private Hintergrund background;

    private static WeltSetup setup;

    static {
        // This code is executed when the class is loaded,
        // BEFORE the constructor is called
        //TODO das muss besser ausgelagert werden
        try {
            File file = WeltSetup.findMatchingFiles(WORLD_SETUP_FILE, LeereWelt.class);
            if (file != null) {
                List<String> tmp = WeltSetup.readAllLines(file);
                setup = WeltSetup.createWorldSetup(tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a world for Kara.
     */
    public LeereWelt() {
        // Create the new world
        super(setup != null ? (setup.getWidth() + setup.getOffsetStartToX() + setup.getOffsetXToEnd()) : 10,
                setup != null ? (setup.getHeight() + setup.getOffsetStartToY() + setup.getOffsetYToEnd()) : 10, CELL_SIZE);
        //  Warn that there was no WORLD_SETUP_FILE specified.
        //TODO  Namen der Welt und weitere details ändern

        if (setup == null) {
            String message = "<html>" +
                    "<p>Could not initialize world. Either specify a valid world setup file in KaraWorld or instantiate <br>" +
                    "a subclass of KaraWorld (right-click on world, e.g. GameScreen, and choose new). </p><br><p><i>" +
                    "Konnte keine Welt laden. Entweder muss eine gueltige world setup Datei in KaraWorld definiert werden oder eine <br>" +
                    "Subklasse von KaraWelt muss instanziiert werden (Rechtsklick auf die Welt, z.B. GameScreen, und new auswaehlen).</i></p>" +
                    "</html>";

            DialogUtils.showMessageDialogEdt(null, message, "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }


        offsetStartToX = setup.getOffsetStartToX();
        offsetStartToY = setup.getOffsetStartToY();
        offsetXToEnd = setup.getOffsetXToEnd();
        offsetYToEnd = setup.getOffsetYToEnd();

        playground = new Spielfeld(this);


        //  TODO Paintorder reparieren
        //        setPaintOrder(PAINT_ORDER)

        //TODO entscheide welchen default speed gesetzt werden soll
        Greenfoot.setSpeed(setup.getSpeed());
        addObject(new SoundButton(true), getWidth(), getHeight());

        background = new Hintergrund(setup,CELL_SIZE);
        setBackground(background.getBackground());
        // Initialize actors
        initActorsFromWorldSetup();
    }

    //New
    @Override
    public void addObject(Actor object, int x, int y) {
        super.addObject(object, x, y);
        if(object instanceof Lichtwesen) {
            background.updateBackground(getObjects(Lichtwesen.class));
            setBackground(background.getBackground());
        }
    }

    public void addObjectWithoutOffset(Actor object, int x, int y) {
        addObject(object, x + offsetStartToX, y + offsetStartToY);
    }

    @Override
    public void removeObject(Actor object){
        super.removeObject(object);
        if(worldIsDark && object instanceof Lichtwesen) {
            background.updateBackground(getObjects(Lichtwesen.class));
            setBackground(background.getBackground());
        }
    }

    public int getWidthWithoutOffset() {
        return super.getWidth() - offsetStartToX - offsetXToEnd;
    }

    public int getHeightWithoutOffset() {
        return super.getHeight() - offsetStartToY - offsetYToEnd;
    }

    public int getOffsetStartToX() {
        return offsetStartToX;
    }

    public int getOffsetStartToY() {
        return offsetStartToY;
    }

    public int getOffsetXToEnd() {
        return offsetXToEnd;
    }

    public int getOffsetYToEnd() {
        return offsetYToEnd;
    }

    /**
     * Initializes the actors based on actor information in the specified
     * {@link WorldSetup}.
     */
    protected void initActorsFromWorldSetup() {
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
                addObjectWithoutOffset(tmp, actorPosition.getX(), actorPosition.getY());
        }

    }

    /**
     * Prints the world setup to the console.
     */
    public void printWorldSetupToConsole() {
        System.out.println(";-------------------------- START --------------------------");
        System.out.println(";--------------------------- END ---------------------------\n");
    }


    public void muteSound(){
        setup.setMute(true);
        save();
    }

    public void unmuteSound(){
        setup.setMute(false);
        save();
    }

    public void save(){
        WeltSetup.saveWorldSetup(setup);
    }

    public Spielfeld getPlayground() {
        return playground;
    }

    /**
     * Utility class for managing dialogs to ensure they are opened in the
     * Event Dispatch Thread.
     */
    public static class DialogUtils {

        /**
         * Calls {@link JOptionPane#showMessageDialog(Component, Object, String, int)} and ensures
         * it is called on the Event Dispatch Thread.
         */
        public static void showMessageDialogEdt(final Component parentComponent,
                                                final Object message, final String title, final int messageType) {

            Runnable task = new Runnable() {
                @Override
                public void run() {
                    JOptionPane.showMessageDialog(parentComponent, message, title,
                            messageType);
                }
            };

            try {
                if (EventQueue.isDispatchThread()) {
                    task.run(); // Already on Event Dispatch Thread, so we can just run it.
                } else {
                    EventQueue.invokeAndWait(task);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        /**
         * Calls {@link JOptionPane#showInputDialog(Object)} and ensures it is called on
         * the Event Dispatch Thread.
         */
        public static String showInputDialogEdt(final Object message) {
            String result = "";

            FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return JOptionPane.showInputDialog(message);
                }
            });

            try {
                if (EventQueue.isDispatchThread()) {
                    task.run(); // Already on Event Dispatch Thread, so we can just run it.
                } else {
                    EventQueue.invokeAndWait(task);
                }
                result = task.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            return result;
        }

        /**
         * Calls  and ensures it is called on
         * the Event Dispatch Thread.
         */
        public static Object showInputDialogEdt(final Component parentComponent,
                                                final Object message,
                                                final String title, final int messageType, final Icon icon,
                                                final Object[] selectionValues, final Object initialSelectionValue) {
            Object result = "";

            FutureTask<Object> task = new FutureTask<Object>(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    return JOptionPane.showInputDialog(parentComponent, message, title, messageType, icon, selectionValues, initialSelectionValue);
                }
            });

            try {
                if (EventQueue.isDispatchThread()) {
                    task.run(); // Already on Event Dispatch Thread, so we can just run it.
                } else {
                    EventQueue.invokeAndWait(task);
                }
                result = task.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            return result;
        }

        /**
         * Calls {@link JOptionPane#showConfirmDialog(Component, Object, String, int)}
         * and ensures it is called on the Event Dispatch Thread.
         */
        public static int showConfirmDialogEdt(final Component parentComponent,
                                               final Object message, final String title, final int optionType) {
            Integer result = 0;

            FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return JOptionPane.showConfirmDialog(parentComponent, message, title, optionType);
                }
            });

            try {
                if (EventQueue.isDispatchThread()) {
                    task.run(); // Already on Event Dispatch Thread, so we can just run it.
                } else {
                    EventQueue.invokeAndWait(task);
                }
                result = task.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            return result;
        }


        /**
         * Calls
         * and ensures it is called on the Event Dispatch Thread.
         */
        public static int showOptionDialogEdt(final Component parentComponent,
                                              final Object message,
                                              final String title, final int optionType, final int messageType,
                                              final Icon icon, final Object[] options, final Object initialValue) {
            Integer result = 0;

            FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return JOptionPane.showOptionDialog(parentComponent, message, title, optionType, messageType, icon, options, initialValue);
                }
            });

            try {
                if (EventQueue.isDispatchThread()) {
                    task.run(); // Already on Event Dispatch Thread, so we can just run it.
                } else {
                    EventQueue.invokeAndWait(task);
                }
                result = task.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            return result;
        }

        /**
         * Calls {@link JFileChooser#showSaveDialog(Component)} and ensures it is called on
         * the Event Dispatch Thread.
         *
         * @param fileChooser the file chooser where <code>showSaveDialog</code> should be called on.
         * @param parent      the parent component of the dialog; can be <code>null</code>
         * @return the return state of the file chooser on popdown:
         * <ul>
         * <li>JFileChooser.CANCEL_OPTION
         * <li>JFileChooser.APPROVE_OPTION
         * <li>JFileChooser.ERROR_OPTION if an error occurs or the
         * dialog is dismissed
         * </ul>
         */
        public static int showSaveDialogEdt(final JFileChooser fileChooser, final Component parent) {
            int result = 0;

            FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return fileChooser.showSaveDialog(parent);
                }
            });

            try {
                if (EventQueue.isDispatchThread()) {
                    task.run(); // Already on Event Dispatch Thread, so we can just run it.
                } else {
                    EventQueue.invokeAndWait(task);
                }
                result = task.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            return result;
        }

    }

}
