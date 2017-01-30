package util;

import greenfoot.*;
import interfaces.LichtwesenInterface;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.image.*;

/**
 * Der Hintergrund des Spiels.
 */
public class Hintergrund {

    // Levelscreen
    // Levelworld
    // Levelschatten

    private int width;
    private int height;

    private int outerWidth;
    private int outerHeight;
    private int offsetStartToX;
    private int offsetStartToY;

    private GreenfootImage levelScreen;
    private ArrayList<GreenfootImage> cellList;
    private ArrayList<GreenfootImage> lightCellList;
    private GreenfootImage gameWorld;
    private GreenfootImage background;
    private final int CELL_SIZE;
    private boolean isDark;
    private WeltSetup setup;
    private BufferedImage mask;
    private GreenfootImage[][] weltKacheln;


    public Hintergrund(WeltSetup setup, int CELL_SIZE) {
        this.CELL_SIZE = CELL_SIZE;
        this.setup = setup;

        height = setup.getHeight();
        width = setup.getWidth();


        offsetStartToX = setup.getOffsetStartToX();
        offsetStartToY = setup.getOffsetStartToY();
        outerWidth = setup.getOuterWidth();
        outerHeight = setup.getOuterHeight();
        isDark = setup.isDark();

        GreenfootImage baseMask = new GreenfootImage("light-mask.png");
        baseMask.scale(CELL_SIZE, CELL_SIZE);
        mask = baseMask.getAwtImage();

        cellList = initialCellList();
        if (isDark) {
            lightCellList = initialLightCellList();
        }
        levelScreen = createLevelScreen();
        gameWorld = createGameWorld();

        background = createBackground();
    }

    private ArrayList<GreenfootImage> initialCellList() {
        ArrayList<GreenfootImage> tmpList = new ArrayList<>();
        for (String path : setup.getCellPath()) {
            GreenfootImage tmpImage = new GreenfootImage(path);
            tmpImage.scale(CELL_SIZE, CELL_SIZE);
            tmpList.add(tmpImage);
        }
        return tmpList;
    }

    private ArrayList<GreenfootImage> initialLightCellList() {
        ArrayList<GreenfootImage> tmpList = new ArrayList<>();
        for (String path : setup.getCellPath()) {
            GreenfootImage tmpImage = new GreenfootImage(path.replace("dunkel", "hell"));
            tmpImage.scale(CELL_SIZE, CELL_SIZE);
            tmpList.add(tmpImage);
        }
        return tmpList;
    }

    private GreenfootImage createLevelScreen() {
        GreenfootImage tmpLevelScreen = defaultImage();
        tmpLevelScreen.scale(outerWidth * CELL_SIZE, outerHeight * CELL_SIZE);
        return tmpLevelScreen;
    }

    private GreenfootImage createGameWorld() {
        GreenfootImage tmpGameWorld = new GreenfootImage(width * CELL_SIZE, height * CELL_SIZE);
        Random random = new Random();
        weltKacheln = new GreenfootImage[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int foo = random.nextInt(cellList.size() - 1);
                weltKacheln[i][j] =lightCellList.get(foo);
                tmpGameWorld.drawImage(cellList.get(foo), j * CELL_SIZE, i * CELL_SIZE);

            }
        }
        return tmpGameWorld;
    }

    public GreenfootImage updateBackground(List<LichtwesenInterface> list) {
        Random random = new Random();
        // levelscreen + gameWorld + shadow
        background = new GreenfootImage(outerWidth * CELL_SIZE, outerHeight * CELL_SIZE);
        background.drawImage(levelScreen, 0, 0);
        background.drawImage(gameWorld, offsetStartToX * CELL_SIZE, offsetStartToY * CELL_SIZE);
        if (isDark) {
            for (LichtwesenInterface obj : list) {
                GreenfootImage cellImage = weltKacheln[obj.getY()][obj.getX()];//lightCellList.get(random.nextInt(cellList.size() - 1));
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
        GreenfootImage tmpBackground = new GreenfootImage(outerWidth * CELL_SIZE, outerHeight * CELL_SIZE);
        tmpBackground.drawImage(levelScreen, 0, 0);
        tmpBackground.drawImage(gameWorld, offsetStartToX * CELL_SIZE, offsetStartToY * CELL_SIZE);
        return tmpBackground;
    }

    public GreenfootImage defaultImage(){
        GreenfootImage foo = new GreenfootImage(setup.getLevelScreen());
        BufferedImage bi = foo.getAwtImage();
        Graphics2D ig2 = bi.createGraphics();

//        ig2.setBackground(Color.WHITE);
//        ig2.clearRect(0, 0, width, height);
//


        ig2.setPaint ( Color.white );
        ig2.fillRect ( 0, 0, bi.getWidth(), bi.getHeight() );

                foo.drawImage(new GreenfootImage(setup.getLevelScreen()), 0,0);
        return foo;
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