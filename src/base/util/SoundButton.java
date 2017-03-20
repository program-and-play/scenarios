package util;

import greenfoot.*;

/**
 *
 */
public final class SoundButton extends Actor {

    private GreenfootImage soundOnImage = new GreenfootImage("sound_on.png");
    private GreenfootImage soundOffImage = new GreenfootImage("sound_off.png");

    private boolean on;
    private static SoundButton instance;

    /**
     * Erzeugt eine Instance von Jahrva, existiert schon eine Instance von Jahrva, wird dieser zurueck gegeben.
     * @return Jahrva
     */

    public static SoundButton erzeugeInstance (boolean on) {
        if (SoundButton.instance == null) {
            SoundButton.instance = new SoundButton(on);
        }
        return SoundButton.instance;
    }

    private SoundButton(boolean on) {
        this.on = on;
        soundOnImage.scale(60,60);
        soundOffImage.scale(60,60);
        setImage(soundOnImage);
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            toggle();
        }
    }

    public boolean isOn() {
        return on;
    }

    private void setOn() {
        on = true;
        setImage(soundOnImage);
    }

    private void setOff() {
        on = false;
        setImage(soundOffImage);
    }

    public void toggle() {
        if(on) {
            setOff();
        } else {
            setOn();
        }
    }
    
}