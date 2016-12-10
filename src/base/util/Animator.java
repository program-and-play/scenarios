package util;

import interfaces.Animation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Write a description of class Animator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Animator extends Thread
{

    private List<Animation> animationsList;
    private Random randomGenerator = new Random();

    public Animator()
    {
        animationsList = new ArrayList<>();
    }

    public Animator(Animation... animations)
    {
        animationsList = Arrays.asList(animations);
    }

    @Override
    public void run() {
        while (!isInterrupted()){
            getAnimation(randomGenerator.nextInt(animationsList.size())).makeAnimation();
            try {
                sleep(randomGenerator.nextInt(2000));
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }

    public synchronized void setAnimation(Animation animation) {
        animationsList.add(animation);
    }

    public synchronized Animation getAnimation(int index) {
        return animationsList.get(index);
    }
}
