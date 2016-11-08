import greenfoot.*;

/**
 *
 */
public class SoundButton extends Actor {

    private GreenfootImage soundOnImage = new GreenfootImage("sound_on.png");
    private Greenfootimage soundOffImage = new GreenfootImage("sound_off.png");

    private boolean on;

    public Button() {
        setImage(soundOnImage);
    }

    public void act() {
        if (mousePressed(this)) {
            toggle();
        }
    }

    private boolean isOn() {
        return true;
    }

    private void setOn() {
        on = true;
        setImage(soundOnImage);
    }

    private void setOff() {
        on = false;
        setImage(soundOffImage);
    }

    private void toggle() {
        if(on) {
            setOff();
        } else {
            setOn();
        }
    }
}