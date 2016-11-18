import greenfoot.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.image.*;

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
    private ArrayList<GreenfootImage> lightCellList;
    private GreenfootImage gameWorld;
    private GreenfootImage background;
    private final int CELL_SIZE;
    private boolean isDark;
    private WorldSetupBetter setup;
    private BufferedImage mask;


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

        GreenfootImage baseMask = new GreenfootImage("light-mask.png");
        baseMask.scale(CELL_SIZE, CELL_SIZE);
        mask = baseMask.getAwtImage();

        cellList = initialCellList();
        if(isDark) {
            lightCellList = initialLightCellList();
        }
        levelScreen = createLevelScreen();
        gameWorld = createGameWorld();

        background = createBackground();
    }

    private ArrayList<GreenfootImage> initialCellList(){
        ArrayList<GreenfootImage> tmpList = new ArrayList<GreenfootImage>();
        for (String path : setup.getCellPath()) {
            GreenfootImage tmpImage = new GreenfootImage(path);
            tmpImage.scale(CELL_SIZE,CELL_SIZE);
            tmpList.add(tmpImage);
        }
        return tmpList;
    }

    private ArrayList<GreenfootImage> initialLightCellList(){
        ArrayList<GreenfootImage> tmpList = new ArrayList<GreenfootImage>();
        for (String path : setup.getCellPath()) {
            GreenfootImage tmpImage = new GreenfootImage(path.replace("dunkel", "hell"));
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
        Random random = new Random();
        // levelscreen + gameWorld + shadow
        background = new GreenfootImage(nettoWidth * CELL_SIZE , nettoHeight * CELL_SIZE);
        background.drawImage(levelScreen,0,0);
        background.drawImage(gameWorld, offsetStartToX * CELL_SIZE, offsetStartToY* CELL_SIZE);
        if(isDark) {
            for (LightBeings obj : list) {
                GreenfootImage cellImage = lightCellList.get(random.nextInt(cellList.size() - 1));
                applyGrayscaleMaskToAlpha(cellImage.getAwtImage(), mask);
                background.drawImage(
                    cellImage,
                    (offsetStartToX + obj.getX()) * CELL_SIZE,
                    (offsetStartToY + obj.getY()) * CELL_SIZE
                );
            }
        }
        return background;
    }

    public GreenfootImage getBackground() {
        return background;
    }

    private GreenfootImage createBackground() {
        // levelscreen + gameWorld
        GreenfootImage tmpBackground = new GreenfootImage(nettoWidth * CELL_SIZE , nettoHeight * CELL_SIZE);
        tmpBackground.drawImage(levelScreen,0,0);
        tmpBackground.drawImage(gameWorld, offsetStartToX* CELL_SIZE, offsetStartToY* CELL_SIZE);
        return tmpBackground;
    }


    public void applyGrayscaleMaskToAlpha(BufferedImage image, BufferedImage mask) {
        int width = image.getWidth();
        int height = image.getHeight();

        int[] imagePixels = image.getRGB(0, 0, width, height, null, 0, width);
        int[] maskPixels = mask.getRGB(0, 0, width, height, null, 0, width);

        for (int i = 0; i < imagePixels.length; i++) {
            int color = imagePixels[i] & 0x00ffffff; // Mask preexisting alpha
            int alpha = maskPixels[i] << 24; // Shift blue to alpha
            imagePixels[i] = color | alpha;
        }

        image.setRGB(0, 0, width, height, imagePixels, 0, width);
    }

}