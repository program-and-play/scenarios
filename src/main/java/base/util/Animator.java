package util;

import interfaces.Animation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public final class Animator extends Thread
{
    private static Animator instance;


    public static Animator getInstance () {
        if (Animator.instance == null) {
            Animator.instance = new Animator ();
        }
        return Animator.instance;
    }

    private List<Animation> animationsList;
    private Random randomGenerator = new Random();

    private Animator()
    {
        animationsList = new ArrayList<>();
    }

    private Animator(Animation... animations)
    {
        animationsList = Arrays.asList(animations);
    }

    @Override
    public void run() {

        while (!isInterrupted()){

            if(animationsList.size() > 0) {
                makeAnimation(getAnimation(randomGenerator.nextInt(animationsList.size())));
                try {
                    sleep(randomGenerator.nextInt(2000));
                } catch (InterruptedException e) {
                    interrupt();
                }
            }
        }
    }

    public synchronized void addAnimation(Animation animation) {
        animationsList.add(animation);
    }

    public synchronized void removeAnimation(Animation animation) {
        animationsList.remove(animation);
    }

    public synchronized Animation getAnimation(int index) {
        return animationsList.get(index);
    }

    public synchronized void makeAnimation(Animation animation) {
        animation.makeAnimation();
    }


}
