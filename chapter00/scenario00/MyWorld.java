import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Toolkit;
/**
 * Write a description of class MyWorld here.
 * 
 * @author Lukas Hettwer 
 * @version 30.09.2016
 */
public class MyWorld extends World
{
   // private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private final static int WIDTH = 30;
    private final static int HEIGHT = 15;
    private final static int PIXEL = 60;

    private String pathForGround = "images/grassSprites.png";
    
    private GreenfootImage background;
    
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {    
        super(WIDTH, HEIGHT, 20); 
        	//System.out.println(dim.height + ":" + dim.width);
        ArrayList<GreenfootImage> terrain = loadImageForWorld(pathForGround, 4,1);
 
        background = new GreenfootImage(WIDTH * PIXEL, HEIGHT * PIXEL);
        for(int i = 0; i < HEIGHT; i++)
        {
            for(int j = 0; j < WIDTH; j++)
            {
                GreenfootImage image =  new GreenfootImage(terrain.get((int) (Math.random()* terrain.size())));
                background.drawImage(image,j*PIXEL, i*PIXEL);
            }
        }
        setBackground(background);
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
