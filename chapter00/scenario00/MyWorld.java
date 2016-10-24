import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Color;
import java.util.Random;
/**
 * Write a description of class MyWorld here.
 * 
 * @author Lukas Hettwer 
 * @version 08.10.2016
 */
public class MyWorld extends World
{
    // private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private final  int WIDTH;
    private final  int HEIGHT;
    private static final int PIXEL = 20;
    private static final int FACTOR = 3;
    private static final int pixelForImage = PIXEL*FACTOR;

    private static int offsetX = 3*FACTOR;
    private static int offsetY = 3*FACTOR;

    private static int extendX = 3*FACTOR;
    private static int extendY = 3*FACTOR;

    private String pathForGround = "images/grassSprites.png";
    private String pathForBackground = "images/backgroundTest.png";
    private GreenfootImage themeImage = new GreenfootImage("images/themeImage.png"); // 5 links 3 rechts 3 oben
    private ArrayList<ArrayList<GreenfootImage>> backgroundAry;
    private boolean isDark;
    private ArrayList<GreenfootImage> terrain;
    Random randnum = new Random();

    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {    
        this(true,6,4); //Is the world a dark place?
    }

    public MyWorld(boolean isDark, int WIDTH, int HEIGHT)
    {    
        super(WIDTH*FACTOR+offsetX+extendX, HEIGHT*FACTOR+offsetY+extendY, PIXEL);
        this.WIDTH = WIDTH*FACTOR;
        this.HEIGHT = HEIGHT*FACTOR;

        this.isDark = isDark;

        themeImage.scale((this.WIDTH+offsetX+extendX)*PIXEL, (this.HEIGHT+offsetY+extendY)*PIXEL);

        terrain = loadImageForWorld(pathForGround, 4,1);

        backgroundAry = new ArrayList<ArrayList<GreenfootImage>>();

        this.addObject(MyCharacter.getInstance(), 3,2);

        drawBackground();
        
        MyCharacter.getInstance().moveForward();
        MyCharacter.getInstance().moveForward();
    }
    
        /**
     * Act - do whatever the MyCharacter wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
                this.addObject(MyCharacter.getInstance(), 0,0);
                MyCharacter.getInstance().moveForward();
                System.out.println("Hello ");
    }  
    

    @Override
    public void addObject(Actor object, int x, int y){

        super.addObject(object, x*FACTOR+offsetX+1, y*FACTOR+offsetY+1);
    }

    public void drawBackground(){
        GreenfootImage backgroundShadow = new GreenfootImage((WIDTH) * pixelForImage, (HEIGHT) * pixelForImage);
        GreenfootImage background = new GreenfootImage((WIDTH + offsetX+extendX) * PIXEL, (HEIGHT + offsetY+extendY) * PIXEL);
        background.drawImage(themeImage, 0, 0);

        randnum.setSeed(123456789);
        for(int i = 0; i < HEIGHT/FACTOR; i++)
        {
            for(int j = 0; j < WIDTH/FACTOR; j++)
            {
                background.drawImage(terrain.get(randnum.nextInt(terrain.size())  ),  offsetX/FACTOR*pixelForImage+ j *pixelForImage, offsetX/FACTOR*pixelForImage+ i *pixelForImage);
            }
        }

        if(isDark){
            GreenfootImage image =  new GreenfootImage(PIXEL,PIXEL);
            image.setColor(Color.BLACK);
            image.fill();
            image.setTransparency(100);

            int[][] shadow = new int[WIDTH][HEIGHT];
            for (Object obj : getObjects(Character.class))
            {
                Character pers = (Character) obj; 
                if(pers.isShining()){
                    int x = pers.getX()-offsetX;
                    int y = pers.getY()-offsetY;
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
                        backgroundShadow.drawImage(image,(j)*PIXEL, (i)*PIXEL );
                    }
                }

            }

            background.drawImage(backgroundShadow, offsetY*PIXEL,offsetX*PIXEL);
            setBackground(background);
        }
    }

    public ArrayList<GreenfootImage> loadImageForWorld(String path, int NumOfCellHorizontal,int NumOfCellVertical) {
        ArrayList<GreenfootImage> worldImage = new ArrayList<GreenfootImage>();
        GreenfootImage loaded = new GreenfootImage(path);

        loaded.scale(pixelForImage*NumOfCellHorizontal, pixelForImage*NumOfCellVertical);

        BufferedImage loadedBuf = loaded.getAwtImage();

        for (int i = 0; i * pixelForImage < loaded.getHeight(); i++) {
            for (int j = 0; j * pixelForImage < loaded.getWidth(); j++) {
                BufferedImage bufImage = loadedBuf.getSubimage(j * pixelForImage, i * pixelForImage, pixelForImage, pixelForImage);
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
        return pixelForImage;
    }

    public static int getFactor(){
        return FACTOR;
    }

    public boolean isPositionOk(Character c, Character.Direction direction){
        switch (direction) {
            case DOWN:
            return c.getY()+2 < offsetY + HEIGHT;
            case UP:
            return c.getY()-2 > offsetY;
            case LEFT:
            return c.getX()-2 > offsetX;
            case RIGHT:
            return c.getX()+2 < offsetX + WIDTH;
            default: return false;
            //return c.getX() > offsetX && c.getX() < offsetX + WIDTH +1 && c.getY() > offsetY && c.getY() < offsetY + HEIGHT +1;
        }
    }

}
