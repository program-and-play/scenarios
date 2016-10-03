import greenfoot.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
/**
 * Write a description of class Character here.
 * 
 * @author Lukas Hettwer 
 * @version 30.09.2016
 */
public class Character extends Actor 
{
    private static final Character OBJ = new Character(); 
           
      public static Character getInstance() { 
        return OBJ; 
      } 
      
    
    private int animationPointer;
    private HashMap<Direction, ArrayList<GreenfootImage>> body;
    private Direction currentDirection;
    private String pathForBody = "images/characterSpritesAnimation.png";
    /**
     * Constructor for objects of class Character
     */
    private Character()
    {
        body = loadImageForCharacter(pathForBody, 8,4);
        currentDirection = Direction.RIGHT;
        setImage(body.get(currentDirection).get(0));
        animationPointer = 0;
    }

    public HashMap<Direction, ArrayList<GreenfootImage>> loadImageForCharacter(String path, int NumOfCellHorizontal,int NumOfCellVertical) 
    {
        int PIXEL = MyWorld.getPixel();
        HashMap<Direction, ArrayList<GreenfootImage>> animationMap = new HashMap<Direction, ArrayList<GreenfootImage>>();
        GreenfootImage loaded = new GreenfootImage(path);
        loaded.scale(PIXEL*NumOfCellHorizontal, PIXEL*NumOfCellVertical);
        BufferedImage loadedBuf = loaded.getAwtImage();
        int i = 0;
        for (Direction dir : Direction.values()) {
            ArrayList<GreenfootImage> animation = new ArrayList<GreenfootImage>();
            for (int j = 0; j  < loaded.getWidth(); j = j + PIXEL) {

                BufferedImage bufImage = loadedBuf.getSubimage(j , i , PIXEL, PIXEL);
                GreenfootImage gImage = new GreenfootImage(bufImage.getWidth(), bufImage.getHeight());
                BufferedImage gBufImg = gImage.getAwtImage();
                Graphics2D graphics = (Graphics2D)gBufImg.getGraphics();
                graphics.drawImage(bufImage, null, 0, 0);

                animation.add(gImage);
            }
            animationMap.put(dir, animation);
            i = i + PIXEL;
        }

        return animationMap;
    }

    public void moveForward() {
        switch (currentDirection) {
            case RIGHT:
            animation();
            this.setLocation(Math.floorMod((this.getX() + 1), getWorld().getWidth()), this.getY());
            animation();
            this.setLocation(Math.floorMod((this.getX() + 1), getWorld().getWidth()), this.getY());
            animation();
            this.setLocation(Math.floorMod((this.getX() + 1), getWorld().getWidth()), this.getY());
            animation();
            break;
            case LEFT:
            animation();
            this.setLocation(Math.floorMod((this.getX() - 1), getWorld().getWidth()), this.getY());
            animation();
            this.setLocation(Math.floorMod((this.getX() - 1), getWorld().getWidth()), this.getY());
            animation();
            this.setLocation(Math.floorMod((this.getX() - 1), getWorld().getWidth()), this.getY());
            animation();
            break;

            case DOWN:
            animation();
            this.setLocation(this.getX() , Math.floorMod((this.getY() + 1) , getWorld().getHeight()));
            animation();
            this.setLocation(this.getX() , Math.floorMod((this.getY() + 1) , getWorld().getHeight()));
            animation();
            this.setLocation(this.getX() , Math.floorMod((this.getY() + 1) , getWorld().getHeight()));
            animation();
            break;

            case UP:
            animation();
            this.setLocation(this.getX() , Math.floorMod((this.getY() - 1) , getWorld().getHeight()));
            animation();
            this.setLocation(this.getX() , Math.floorMod((this.getY() - 1) , getWorld().getHeight()));
            animation();
            this.setLocation(this.getX() , Math.floorMod((this.getY() - 1) , getWorld().getHeight()));
            animation();
            break;

            default:
            break;
        }
    }

    public void animation(){
        for (int i = 0; i < 2; i++) {
            ++animationPointer;
            if(animationPointer >= body.get(currentDirection).size())
                animationPointer = 0; 

            setImage(body.get(currentDirection).get(animationPointer));
            Greenfoot.delay(2);
        }
    }

    public void turnLeft() {
        currentDirection = currentDirection.rotationLeft();
        setImage(body.get(currentDirection).get(0));
    }

    public void turnRight() {
        currentDirection = currentDirection.rotationRight();
        setImage(body.get(currentDirection).get(0));
    }

    public void turnAround() {
        currentDirection = currentDirection.rotationLeft().rotationLeft();
        setImage(body.get(currentDirection).get(0));
    }

    public enum Direction {
        RIGHT, LEFT, UP, DOWN;

        public Direction rotationLeft()
        {
            switch (this) {
                case RIGHT:
                return UP;

                case DOWN:
                return RIGHT;

                case UP:
                return LEFT;

                case LEFT:
                return DOWN;
                default:
                return null;
            }
        }

        public Direction rotationRight()
        {
            switch (this) {
                case RIGHT:
                return DOWN;

                case DOWN:
                return LEFT;

                case UP:
                return RIGHT;

                case LEFT:
                return UP;
                default:
                return null;
            }
        }
    }

}
