import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Color;
/**
 * Write a description of class MyWorld here.
 * 
 * @author Lukas Hettwer 
 * @version 07.10.2016
 */
public class MyWorld extends World
{
    // private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private final  int WIDTH;
    private final  int HEIGHT;
    private static final int PIXEL = 60;

    private String pathForGround = "images/grassSprites.png";


    private ArrayList<Character> shinigCharacter;
    private ArrayList<ArrayList<GreenfootImage>> backgroundAry;
    private boolean isDark;

    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {    
        this(true,30,15); //Is the world a dark place?
    }

    public MyWorld(boolean isDark, int WIDTH, int HEIGHT)
    {    
        super(WIDTH, HEIGHT, PIXEL/3);
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;

        this.isDark = isDark;
        //System.out.println(dim.height + ":" + dim.width);
        shinigCharacter = new ArrayList<Character>();
        ArrayList<GreenfootImage> terrain = loadImageForWorld(pathForGround, 4,1);

        backgroundAry = new ArrayList<ArrayList<GreenfootImage>>();

        for(int i = 0; i < HEIGHT/3; i++)
        {
            backgroundAry.add(i, new ArrayList<GreenfootImage>());
            for(int j = 0; j < WIDTH/3; j++)
            {
                GreenfootImage image =  new GreenfootImage(terrain.get((int) (Math.random()* terrain.size())));
                backgroundAry.get(i).add(j,image);
            }
        }
        this.addObject(MyCharacter.getInstance(), 7, 7);
        drawBackground();
    }

    public void addObject(Actor object, int x, int y)
    {
        super.addObject(object,x,y);     
        if(object instanceof Character && ((Character)object).isShining())
        {
            shinigCharacter.add((Character)object);
        }

    }

    public void drawBackground(){
        GreenfootImage backgroundShadow = new GreenfootImage(WIDTH/3 * PIXEL, HEIGHT/3 * PIXEL);
        GreenfootImage background = new GreenfootImage(WIDTH/3 * PIXEL, HEIGHT/3 * PIXEL);
        for(int i = 0; i < HEIGHT/3; i++)
        {
            for(int j = 0; j < WIDTH/3; j++)
            {
                background.drawImage(backgroundAry.get(i).get(j),j*PIXEL, i*PIXEL);
            }
        }

        if(isDark){
            GreenfootImage image =  new GreenfootImage(PIXEL/3,PIXEL/3);
            image.setColor(Color.BLACK);
            image.fill();
            image.setTransparency(100);

            int[][] shadow = new int[WIDTH][HEIGHT];
            for (Object obj : getObjects(Character.class))
            {
                Character pers = (Character) obj; 
                if(pers.isShining()){
                    int x = pers.getX();
                    int y = pers.getY();
                    for(int k = -4; k <=4; k++)
                    {
                        for(int h = -4; h <=4; h++)
                        {   if(!(x+h < 0 || x+h >= WIDTH || y+k < 0 || y+k >= HEIGHT))
                                shadow[x+h][y+k] = 1;
                        }
                    }
                }
            }

            for(int i = 0; i < HEIGHT; i++)
            {
                for(int j = 0; j < WIDTH; j++)
                {
                    if(shadow[j][i] != 1){
                        backgroundShadow.drawImage(image,j*PIXEL/3, i*PIXEL/3);
                    }
                }

            }

            background.drawImage(backgroundShadow, 0,0);
            setBackground(background);
        }
    }

    public ArrayList<GreenfootImage> loadImageForWorld(String path, int NumOfCellHorizontal,int NumOfCellVertical) {
        ArrayList<GreenfootImage> worldImage = new ArrayList<GreenfootImage>();
        GreenfootImage loaded = new GreenfootImage(path);

        loaded.scale(PIXEL*NumOfCellHorizontal, PIXEL*NumOfCellVertical);

        BufferedImage loadedBuf = loaded.getAwtImage();

        for (int i = 0; i * PIXEL < loaded.getHeight(); i++) {
            for (int j = 0; j * PIXEL < loaded.getWidth(); j++) {
                BufferedImage bufImage = loadedBuf.getSubimage(j * PIXEL, i * PIXEL, PIXEL, PIXEL);
                GreenfootImage gImage = new GreenfootImage(bufImage.getWidth(), bufImage.getHeight());
                BufferedImage gBufImg = gImage.getAwtImage();
                Graphics2D graphics = (Graphics2D)gBufImg.getGraphics();
                graphics.drawImage(bufImage, null, 0, 0);

                worldImage.add(gImage);
            }
        }
        return worldImage;
    }

    public static int getPixel(){
        return PIXEL;
    }

}
