import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.World;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.io.IOException;
import java.util.Random;

class EmptyWorld extends World {

    public static final String WORLD_SETUP_FILE = "WorldSetup.txt";
    //TODO das muss besser gehen
 //   public String[] GROUNDPATH = {"Lv2_tile1_hell.png","Lv2_tile2_hell.png","Lv2_tile3_hell.png","Lv2_tile4_hell.png"};// Java
    public String[] GROUNDPATH = ["Lv2_tile1_hell.png","Lv2_tile2_hell.png","Lv2_tile3_hell.png","Lv2_tile4_hell.png"]; // Groovy

    // Size of one cell
    public static final int CELL_SIZE = 60;

    // background icon
    // TODO icon background um mehrer icons erweitern
    private static GreenfootImage ICON_BACKGROUND_FIELD;

    //TODO die richtigen Anzeigebilder für die class in dem Anzeigebaum von Greenfoot integrieren.

    // all actor icons (this is a trick to get the default image that was chosen for actors)
    public static final GreenfootImage ICON_CHARACTER = new MyCharacter().getImage();
    //public static final GreenfootImage ICON_LEAF = new Leaf().getImage();
    //    public static final GreenfootImage ICON_MUSHROOM = new Mushroom().getImage();
    //    public static final GreenfootImage ICON_MUSHROOM_ON_TARGET = findOnTargetImage(ICON_MUSHROOM, "_on_target");
    //    public static final GreenfootImage ICON_TREE = new Tree().getImage();
    //    public static final GreenfootImage ICON_KARA = new Kara().getImage();
    //    public static final GreenfootImage ICON_MY_KARA = new MyKara().getImage();

    public static final String WORLD_SETUP_TITLE_KEY = "World:";
    public static final String KARA_DIRECTION_KEY = "Kara:";

    public static final String DIRECTION_RIGHT = "right";
    public static final String DIRECTION_DOWN = "down";
    public static final String DIRECTION_LEFT = "left";
    public static final String DIRECTION_UP = "up";

    public static final int FACTOR = 5;

    //    public static final Class<?>[] PAINT_ORDER = {
    //        //TODO Hier fehlt die richtige Paintorder, konstrukt funktioniert nicht  Tipp: benutze die [] klammern dafür
    //        MyCharacter.class
    //    };
    //
    private static final WorldSetup worldSetup;

    static {
        // This code is executed when the class is loaded,
        // BEFORE the constructor is called
        if (WORLD_SETUP_FILE == null) {
            // WORLD_SETUP_FILE is null --> world must be set up by subclass.
            worldSetup = null;
        } else {
            worldSetup = loadWorldSetupFromFile(WORLD_SETUP_FILE);
        }
    }

    /**
     * Creates a world for Kara.
     */
    public EmptyWorld() {
        // Create the new world
        super(worldSetup != null ? (worldSetup.getWidth() * FACTOR): 40, worldSetup != null ? (worldSetup.getHeight()*FACTOR ): 40, CELL_SIZE/FACTOR);
        //  Warn that there was no WORLD_SETUP_FILE specified.
        //TODO  Namen der Welt und weitere details ändern
        System.out.println("Hier");
        if (worldSetup == null) {
            String message = "<html>" +
                    "<p>Could not initialize world. Either specify a valid world setup file in KaraWorld or instantiate <br>" +
                    "a subclass of KaraWorld (right-click on world, e.g. GameScreen, and choose new). </p><br><p><i>" +
                    "Konnte keine Welt laden. Entweder muss eine gueltige world setup Datei in KaraWorld definiert werden oder eine <br>" +
                    "Subklasse von KaraWelt muss instanziiert werden (Rechtsklick auf die Welt, z.B. GameScreen, und new auswaehlen).</i></p>" +
                    "</html>";

            EmptyWorld.DialogUtils.showMessageDialogEdt(null, message, "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }

        ICON_BACKGROUND_FIELD = createBackground(loadImageGround(GROUNDPATH));

        createFieldBackground();

        //  TODO Paintorder reparieren
        //        setPaintOrder(PAINT_ORDER)
        //TODO entscheide welchen default speed gesetzt werden soll
        Greenfoot.setSpeed(100);

        // Initialize actors
        prepare();
    }

    /**
     * Creates an empty world for Kara with specified width and height.
     *
     * @param worldWidth
     *            Number of horizontal cells
     * @param worldHeight
     *            Number of vertical cells
     */
    public EmptyWorld(int worldWidth, int worldHeight) {
        // Create the new world
        super(worldWidth*FACTOR, worldHeight*FACTOR, CELL_SIZE/FACTOR);
        ICON_BACKGROUND_FIELD = createBackground(loadImageGround(GROUNDPATH));
        createFieldBackground();

        //  TODO Paintorder reparieren
        //        setPaintOrder(PAINT_ORDER)
        Greenfoot.setSpeed(100);

        // Initialize actors
        prepare();
    }

    /**
     * Loads the world setup from the specified file. If it cannot be found, a
     * warning message is displayed and a default world setup is loaded.
     *
     * @param worldFile
     *            The filename of the world setup file, relative to the class,
     *            relative to the package root or relative to the project root.
     *            Wildcards (? or *) may be used.
     * @return
     */
    private static WorldSetup loadWorldSetupFromFile(String worldFile) {
        WorldSetup[] worldSetups = null;
        try {
            worldSetups = WorldSetup.parseFromFile(worldFile, MyCharacter.class, WORLD_SETUP_TITLE_KEY,
                    KARA_DIRECTION_KEY);

            if (worldSetups == null || worldSetups.length == 0) {
                if (worldSetups == null || worldSetups.length == 0) {
                    String message = "<html>" + "Could not load world setup from file: <p><i>" +
                            "Konnte keine Welt laden von der Datei: " +
                            "</i><p><p>" + worldFile +
                            "<p><p>(A world-file must start with \"" + WORLD_SETUP_TITLE_KEY +
                            "\")</html>";
                    EmptyWorld.DialogUtils.showMessageDialogEdt(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (IOException e) {
            String message = "<html>" + "Could not find world setup file: <p><i>" +
                    "Konnte die world setup Datei nicht finden: " +
                    "</i><p><p>" + worldFile + "</html>";

            EmptyWorld.DialogUtils.showMessageDialogEdt(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
        }

        WorldSetup result = null;

        if (worldSetups != null) {
            if (worldSetups.length == 1) {
                result = worldSetups[0];
            } else if (worldSetups.length > 1) {
                // User must choose from a list of world setups
                result = (WorldSetup) EmptyWorld.DialogUtils.showInputDialogEdt(null,
                        "<html>Please Choose a World: <p><i>Bitte waehle eine Welt:</i>",
                        "Choose World",
                        JOptionPane.QUESTION_MESSAGE, null,
                        worldSetups, // Array of choices
                        worldSetups[0]); // Initial choice
            }
        }

        if (result != null) {
            return result;
        } else {
            // return a default world setup
            return new WorldSetup.Builder(WORLD_SETUP_TITLE_KEY)
                    .setWidth(10)
                    .setHeight(10)
                    .setTitle("Kara Default")
                    .build();
        }

    }
    //New
    public Cell[] loadImageGround(String[] path) {
        Cell[] worldImage = new Cell[path.length];

        for (int i = 0; i < path.length; i++) {
            Cell tmp;
            if (path[i].contains("hell"))
                tmp = new Cell("images/" + path[i], "images/" + path[i].replace("hell", "dunkel"));
            else
                tmp = new Cell("images/" + path[i]);


            worldImage[i] = tmp.scale(CELL_SIZE, CELL_SIZE);
        }
        return worldImage;
    }
    //New
    public GreenfootImage createBackground(Cell[] backgroundArray) {
        GreenfootImage background = new GreenfootImage((int)(worldSetup.getWidth() * CELL_SIZE), (int) (worldSetup.getWidth()* CELL_SIZE));

        Random random = new Random();
        //TODO hier wirklich von worldSetup statt die werte von Emptyworld zu bekommen ist nicht so gut
        for (int i = 0; i < worldSetup.getHeight(); i++) {
            for (int j = 0; j < worldSetup.getWidth(); j++) {
                background.drawImage(backgroundArray[random.nextInt(backgroundArray.length - 1)].getDark(), j * CELL_SIZE, i * CELL_SIZE);
            }
        }
        return background;
    }
    //TODO
    private void updateShadow() {}

    /**
     * Initializes the background tiles with the field icon.
     */
    protected void createFieldBackground() {
        //TODO die logik funktioniert so noch nicht
        updateShadow();
        setBackground(ICON_BACKGROUND_FIELD);
    }

    /**
     * Removes the background tiles.
     */
    protected void clearFieldBackground() {
        setBackground((GreenfootImage) null);
    }

    /**
     * Prepares the world, i.e. creates all initial actors. If a world setup is
     * available, it is loaded.
     * <p>
     * This method may be overridden by subclasses to provide their own means to
     * initialize actors, e.g. by calling {@link #addObject()}.
     */
    protected void prepare() {
        if (worldSetup != null) {
            initActorsFromWorldSetup(worldSetup);
        }
    }

    /**
     * Initializes the actors based on actor information in the specified
     * {@link WorldSetup}.
     */
    protected void initActorsFromWorldSetup(WorldSetup worldSetup) {
        for (int y = 0; y < worldSetup.getHeight(); y++) {
            for (int x = 0; x < worldSetup.getWidth(); x++) {
                switch (worldSetup.getActorTypeAt(x, y)) {
                    case WorldSetup.KARA:
                        addObject(new MyCharacter(), x, y);
                        break;
                //TODO   hier alle nötigen Actoren einfügen
                //                    case WorldSetup.KARA:
                //                        addObject(new MyKara(), x, y);
                //                        break;
                //                    case WorldSetup.TREE:
                //                        addObject(new Tree(), x, y);
                //                        break;
                //                    case WorldSetup.LEAF:
                //                        addObject(new Leaf(), x, y);
                //                        break;
                //                    case WorldSetup.MUSHROOM:
                //                        addObject(new Mushroom(), x, y);
                //                        break;
                //                    case WorldSetup.MUSHROOM_LEAF:
                //                        addObject(new Mushroom(true), x, y);
                //                        addObject(new Leaf(), x, y);
                //                        break;
                //                    case WorldSetup.KARA_LEAF:
                //                        addObject(new MyKara(), x, y);
                //                        addObject(new Leaf(), x, y);
                //                        break;
                }
            }
        }

        String karaDirection = worldSetup.getAttribute(KARA_DIRECTION_KEY);
        if (karaDirection != null) {

            if (karaDirection.equalsIgnoreCase(DIRECTION_DOWN)) {
                for (Object actor : getObjects(MyCharacter.class)) {
                    MyCharacter kara = (MyCharacter) actor;
                    kara.turnRight();
                }
            } else if (karaDirection.equalsIgnoreCase(DIRECTION_LEFT)) {
                for (Object actor : getObjects(MyCharacter.class)) {
                    MyCharacter kara = (MyCharacter) actor;
                    kara.turnRight();
                    kara.turnRight();
                }
            } else if (karaDirection.equalsIgnoreCase(DIRECTION_UP)) {
                for (Object actor : getObjects(MyCharacter.class)) {
                    MyCharacter kara = (MyCharacter) actor;
                    kara.turnLeft();
                }
            }
            // DIRECTION_RIGHT is the original direction - do nothing
        }
    }

    /**
     * Prints the world setup to the console.
     */
    //TODO toASCIIText() muss repariert werden
    public void printWorldSetupToConsole() {
        System.out.println(";-------------------------- START --------------------------");
        // System.out.println(toASCIIText());
        System.out.println(";--------------------------- END ---------------------------\n");
    }
    //TODO verstehen was diese Methode macht
    ///**
    // * Saves the world setup to a file that the user can choose.
    // */
    //    public void saveWorldSetupToFile() {
    //        try {
    //            WorldSetup.FileUtils.saveToFileWithDialog(toASCIIText());
    //        } catch (IOException exception) {
    //            exception.printStackTrace();
    //        }
    //    }
    //
    //    /**
    //     * Creates an ASCII-representation of all the actors in the world.
    //     *
    //     * @return the world as ASCII text
    //     */
    //    @SuppressWarnings("unchecked")
    //    protected String toASCIIText() {
    //        return WorldSetup.createFromActors(getObjects(null), getWidth(), getHeight(),
    //                WORLD_SETUP_TITLE_KEY).toASCIIText(true);
    //    }
    //
    //    /**
    //     * Retrieves the filename of the default image and adds the on-target
    //     * postfix to the filename to load the on-target-image. If the on target
    //     * image cannot be found, the default image is returned.
    //     */
    //    private static GreenfootImage findOnTargetImage(GreenfootImage defaultImage, String onTargetPostfix) {
    //        // Since a GreenfootImage only has a toString method we must parse it to
    //        // get the file name
    //        Pattern pattern = Pattern.compile("Image file name: (.+)(\\.+\\S{3,4})\\s+Image url");
    //        Matcher matcher = pattern.matcher(defaultImage.toString());
    //
    //        if (matcher.find()) {
    //            String fileName = matcher.group(1) + onTargetPostfix
    //            + matcher.group(2);
    //            try {
    //                return new GreenfootImage(fileName);
    //            } catch (Exception e) {
    //                System.out.println("Could not find the file: " + fileName
    //                        + " (the default file is used)");
    //                return defaultImage;
    //            }
    //        } else {
    //            // Could not find the on target image --> use default
    //            return defaultImage;
    //        }
    //    }
    //
    //

    /**
     * Utility class for managing dialogs to ensure they are opened in the
     * Event Dispatch Thread.
     */
    public static class DialogUtils {

        /**
         * Calls {@link JOptionPane#showMessageDialog(java.awt.Component, Object, String, int)} and ensures
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
         * Calls {@link JOptionPane#showInputDialog(Component, Object, String, int, Icon, Object[], Object)} and ensures it is called on
         * the Event Dispatch Thread.
         */
        public static Object showInputDialogEdt(final Component parentComponent,
                                                final Object message, final String title, final int messageType, final Icon icon,
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
         * Calls {@link JOptionPane#showOptionDialog(Component, Object, String, int, int, javax.swing.Icon, Object[], Object)}
         * and ensures it is called on the Event Dispatch Thread.
         */
        public static int showOptionDialogEdt(final Component parentComponent,
                                              final Object message, final String title, final int optionType, final int messageType,
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
         * @param parent the parent component of the dialog; can be <code>null</code>
         * @return the return state of the file chooser on popdown:
         * <ul>
         * <li>JFileChooser.CANCEL_OPTION
         * <li>JFileChooser.APPROVE_OPTION
         * <li>JFileChooser.ERROR_OPTION if an error occurs or the
         *                  dialog is dismissed
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

    public class Cell {
        private GreenfootImage light;
        private GreenfootImage dark;

        public Cell(String lightString, String darkString) {
            this.dark = new GreenfootImage(darkString);
            this.light = new GreenfootImage(lightString);
        }

        public Cell(String lightString) {
            this.dark = new GreenfootImage(lightString);
            this.light = this.dark;
        }

        public GreenfootImage getLight() {
            return light;
        }

        public GreenfootImage getDark() {
            return dark;
        }

        public Cell scale(int width, int height) {
            light.scale(width, height);
            dark.scale(width, height);
            return this;
        }

        public String toString() {
            return "Light: " + light.toString()+ " Dark: " + dark.toString()+"\n";
        }
    }
}
