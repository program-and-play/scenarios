import greenfoot.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Write a description of class Background here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Background {

    // Levelscreen
    // Levelworld
    // Levelschatten

    private int width;
    private int height;

    private int nettoWidth;
    private int nettoHeight;

    private int offsetStartToX;
    private int offsetStartToY;
    private int offsetXToEnd;
    private int offsetYToEnd;
    private GreenfootImage levelScreen;
    private ArrayList<GreenfootImage> cellList;
    private GreenfootImage gameWorld;
    private GreenfootImage shadow;
    private GreenfootImage background;
    private final int CELL_SIZE;
    private boolean isDark;
    private WorldSetupBetter setup;


    public Background(WorldSetupBetter setup, int CELL_SIZE) {
        this.CELL_SIZE = CELL_SIZE;
        this.setup = setup;

        height = setup.getHeight();
        width = setup.getWidth();
        offsetStartToX = setup.getOffsetStartToX();
        offsetStartToY = setup.getOffsetStartToY();
        offsetXToEnd = setup.getOffsetXToEnd();
        offsetYToEnd = setup.getOffsetYToEnd();
        nettoWidth = offsetStartToX + width + offsetXToEnd;
        nettoHeight = offsetStartToY + height + offsetYToEnd;
        isDark = setup.isDark();

        cellList = initialCellList();
        levelScreen = createLevelScreen();
        gameWorld = createGameWorld();
        shadow = createShadow(new ArrayList<LightBeings>());

        background = createBackground();


    }

    private ArrayList<GreenfootImage> initialCellList(){
        ArrayList<GreenfootImage> tmpList = new ArrayList<GreenfootImage>();
        for (String path :setup.getCellPath()) {
            GreenfootImage tmpImage = new GreenfootImage(path);
            tmpImage.scale(CELL_SIZE,CELL_SIZE);
            tmpList.add(tmpImage);
        }
        return tmpList;
    }

    private GreenfootImage createLevelScreen(){
        GreenfootImage tmpLevelScreen = new GreenfootImage(setup.getLevelScreen());
        tmpLevelScreen.scale(nettoWidth * CELL_SIZE, nettoHeight * CELL_SIZE);
        return tmpLevelScreen;
    }

    private GreenfootImage createGameWorld() {
        GreenfootImage tmpGameWorld = new GreenfootImage(width * CELL_SIZE, height * CELL_SIZE);
        Random random = new Random();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tmpGameWorld.drawImage(cellList.get(random.nextInt(cellList.size() - 1)), j * CELL_SIZE, i * CELL_SIZE);
            }
        }
        return tmpGameWorld;
    }

    public GreenfootImage updateBackground(List<LightBeings> list) {
        // levelscreen + gameWorld + shadow
        background = new GreenfootImage(nettoWidth * CELL_SIZE , nettoHeight * CELL_SIZE);
        background.drawImage(levelScreen,0,0);
        background.drawImage(gameWorld, offsetStartToX* CELL_SIZE, offsetStartToY* CELL_SIZE);
        if(isDark)
            background.drawImage(createShadow(list), offsetStartToX* CELL_SIZE, offsetStartToY* CELL_SIZE);
        return background;
    }

    private GreenfootImage createBackground() {
        // levelscreen + gameWorld + shadow
        GreenfootImage tmpBackground = new GreenfootImage(nettoWidth * CELL_SIZE , nettoHeight * CELL_SIZE);
        tmpBackground.drawImage(levelScreen,0,0);
        tmpBackground.drawImage(gameWorld, offsetStartToX* CELL_SIZE, offsetStartToY* CELL_SIZE);
        if(isDark)
            tmpBackground.drawImage(shadow, offsetStartToX* CELL_SIZE, offsetStartToY* CELL_SIZE);
        return tmpBackground;
    }

    public GreenfootImage getBackground() {
        return background;
    }

    private GreenfootImage createShadow(List<LightBeings> list) {
        shadow = new GreenfootImage(width*CELL_SIZE,height*CELL_SIZE);
//TODO
        return shadow;
    }

}
/*
    public int getWidthWithoutOffset();

    public int getHeightWithoutOffset();

    public int getOffsetStartToX();

    public int getOffsetStartToY();

    public int getOffsetXToEnd();

    public int getOffsetYToEnd();

    public void addObject(Actor object, int x, int y);

    public void addObjectWithoutOffset(Actor object, int x, int y);

    public void removeObject(Actor object);

    public Cell[] loadImageGround(ArrayList<String> path);

    public GreenfootImage createBackground(Cell[] backgroundArray);

    public void createFieldBackground();

    protected void clearFieldBackground();

    protected void initActorsFromWorldSetup();


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
            return "Light: " + light.toString() + " Dark: " + dark.toString() + "\n";
        }
    }
}
*/