import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyCharacter here.
 * 
 * @author Lukas Hettwer 
 * @version 07.10.2016
 */
public class MyCharacter extends Character
{
        
    private static final MyCharacter OBJ = new MyCharacter(); 
           
      protected static MyCharacter getInstance() { 
        return OBJ; 
      } 
      
      private MyCharacter()
      {
          super();
        }
    
    /**
     * Act - do whatever the MyCharacter wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }    
}
