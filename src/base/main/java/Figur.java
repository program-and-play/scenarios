import greenfoot.*;
import util.ActorPosition;
import util.DialogUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;


class Figur extends Actor {
    
    private int imagePointer;
    
    private Direction currentDirection = Direction.RIGHT;
    
    private HashMap<Direction, ArrayList<GreenfootImage>> imageContainer;
    
    private ActorPosition startPosition;
    
    protected Figur(FigurTyp figur, ActorPosition startPosition) {
        this(createImage(figur.path),startPosition);
    }
    
    protected Figur(GreenfootImage image, ActorPosition startPosition) {
        this(image, 1, 1,startPosition);
    }
    
    protected Figur(GreenfootImage image, int sceneX, int sceneY, ActorPosition startPosition) {
        imageContainer = loadImage(image, sceneX, sceneY);
        resetImage();
        this.startPosition = startPosition;
    }
    
    protected Figur(ActorPosition startPosition) {
        imageContainer = loadImage(getImage(), 1, 1);
        resetImage();
        this.startPosition = startPosition;
    }
    
    protected void reset() {
        setCurrentDirection(Direction.RIGHT);
        resetImage();
    }
    
    protected static GreenfootImage createImage(String... path) {
        if (path.length < 1)
            throw new IllegalArgumentException("Error: Es wurde keinen Path fuer ein Bild uebergeben!");
        
        GreenfootImage bodyImage = new GreenfootImage(path[0]);
        for (String x : path) {
            bodyImage.drawImage(new GreenfootImage(x), 0, 0);
        }
        return bodyImage;
    }
    
    public int getImagePointer() {
        return imagePointer;
    }
    
    public void setImagePointer(int imagePointer) {
        this.imagePointer = imagePointer;
    }
    
    public Direction getCurrentDirection() {
        return currentDirection;
    }
    
    public void setCurrentDirection(Direction direction) {
        currentDirection = direction;
    }
    
    public LeereWelt getWorld() {
        return (LeereWelt) super.getWorld();
    }
    
    @Override
    public int getX() {
        return super.getX() - Factory.getSetup().getOffsetStartToX();
    }
    
    @Override
    public int getY() {
        return super.getY() - Factory.getSetup().getOffsetStartToY();
    }
    
    public HashMap<Direction, ArrayList<GreenfootImage>> getImageContainer() {
        return imageContainer;
    }
    
    public void setImageContainer(HashMap<Direction, ArrayList<GreenfootImage>> neuerContainer) {
        imageContainer = neuerContainer;
    }
    
    protected boolean theWorldsEnd(Direction direction, int steps, Actor actor) {
        int x = actor.getX();
        int y = actor.getY();
        
        switch (direction) {
            case RIGHT:
                return x + steps >= Factory.getSetup().getWidth();
            case DOWN:
                return y + steps >= Factory.getSetup().getHeight();
            case LEFT:
                return x - steps < 0;
            case UP:
                return y - steps < 0;
            default: // Not a valid direction
                return false;
        }
    }
    
    protected void stop() {
        Greenfoot.stop();
    }
    
    
    protected void showWarning(String englishMessage, String germanMessage) {
        String message = "<html>" + englishMessage + "<p>" + germanMessage + "</html>";
        Object[] options = {"OK", "Exit Program"};
        int choice = DialogUtils.showOptionDialogEdt(null, message, "Warning",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
                options, options[0]);
        
        if (choice == 1) {
            // Emergency stop. Greenfoot should restart after this.
            System.exit(0);
        } else {
            // Stop. This will still finish the act()-method.
            Greenfoot.stop();
            // Repaint, otherwise the world might keep displaying a dialog in
            // some cases
            getWorld().repaint();
        }
    }
    
    public void setLocationWithoutOffset(int x, int y) {
        super.setLocation(x + Factory.getSetup().getOffsetStartToX(), y + Factory.getSetup().getOffsetStartToY());
    }
    
    public HashMap<Direction, ArrayList<GreenfootImage>> loadImage(GreenfootImage bodyImage, int NumOfCellHorizontal, int NumOfCellVertical) {
        int PIXEL = Factory.CELLSIZE;
        HashMap<Direction, ArrayList<GreenfootImage>> animationMap = new HashMap<>();
        GreenfootImage loaded = bodyImage;
        loaded.scale(PIXEL * NumOfCellHorizontal, PIXEL * NumOfCellVertical);
        BufferedImage loadedBuf = loaded.getAwtImage();
        int i = 0;
        
        for (Direction dir : Direction.values()) {
            ArrayList<GreenfootImage> animation = new ArrayList<>();
            
            for (int j = 0; j < loaded.getWidth(); j = j + PIXEL) {
                BufferedImage bufImage = loadedBuf.getSubimage(j, i, PIXEL, PIXEL);
                GreenfootImage gImage = new GreenfootImage(bufImage.getWidth(), bufImage.getHeight());
                BufferedImage gBufImg = gImage.getAwtImage();
                Graphics2D graphics = (Graphics2D) gBufImg.getGraphics();
                graphics.drawImage(bufImage, null, 0, 0);
                animation.add(gImage);
            }
            
            animationMap.put(dir, animation);
            if (i + PIXEL < loaded.getHeight())
                i = i + PIXEL;
        }
        return animationMap;
    }
    
    public ArrayList<GreenfootImage> loadImageForAnimation(GreenfootImage bodyImage, int numOfCellHorizontal) {
        int PIXEL = Factory.CELLSIZE;
        GreenfootImage loaded = bodyImage;
        loaded.scale(PIXEL * numOfCellHorizontal, PIXEL);
        BufferedImage loadedBuf = loaded.getAwtImage();
        
        ArrayList<GreenfootImage> animation = new ArrayList<>();
        for (int j = 0; j < loaded.getWidth(); j = j + PIXEL) {
            
            BufferedImage bufImage = loadedBuf.getSubimage(j, 0, PIXEL, PIXEL);
            GreenfootImage gImage = new GreenfootImage(bufImage.getWidth(), bufImage.getHeight());
            BufferedImage gBufImg = gImage.getAwtImage();
            Graphics2D graphics = (Graphics2D) gBufImg.getGraphics();
            graphics.drawImage(bufImage, null, 0, 0);
            
            animation.add(gImage);
        }
        return animation;
    }
    
    public void changeAnimationImage(int offset, Direction direction, Figur... actorArray) {
        for (Figur actor : actorArray) {
            GreenfootImage tmp;
            int imagePointer = actor.getImagePointer();
            if (imagePointer >= actor.getImageContainer().get(direction).size() - 1)
                actor.setImagePointer(0);
            else
                actor.setImagePointer(imagePointer++);
            
            GreenfootImage image = actor.getImageContainer().get(direction).get(imagePointer);
            if (direction.equals(Direction.LEFT) || direction.equals(Direction.RIGHT)) {
                tmp = new GreenfootImage(image.getWidth() + offset, image.getHeight());
                tmp.drawImage(image, offset, 0);
            } else {
                tmp = new GreenfootImage(image.getWidth(), image.getHeight() + offset);
                tmp.drawImage(image, 0, offset);
            }
            actor.setImage(tmp);
        }
    }
    
    public void resetImage() {
        imagePointer = 0;
        setImage(imageContainer.get(currentDirection).get(0));
    }
    
    public ActorPosition getActorPosition() {
        return startPosition;
    }
    
    public enum Direction {
        RIGHT, LEFT, UP, DOWN;
        
        public Direction rotationLeft() {
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
        
        public Direction rotationRight() {
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
    
    public enum FigurTyp {
        Jahrva("character_body.png", "character_stab.png", "character_kleidung.png"),
        Steinbeisser("steinbeisser.png"),
        Stein("stein.png"),
        Gysir("geysir.png"),
        Rune("rune.png");
        
        public final String[] path;
        
        FigurTyp(String... path) {
            this.path = path;
            
        }
        
    }
    
}

