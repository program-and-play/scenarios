package util;

import interfaces.Animation;

import java.util.*;


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
        setDaemon(true);
    }

    private Animator(Animation... animations)
    {
        animationsList = Arrays.asList(animations);
    }

    @Override
    public void run() {

        while (!isInterrupted()){

            if(animationsList.size() > 0) {
                Set<Animation> animations = getRandomAnimationsSet();
                for (int i = 0; i < 4; i++) {
                    for (Animation animation: animations) {
                        animation.setNext();
                    }
                }
    
                for (Animation animation:animations) {
                    animation.reset();
                }
                
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
    
    private Set<Animation> getRandomAnimationsSet(){
        int count = randomGenerator.nextInt(animationsList.size());
        Set<Animation> set = new HashSet<>();
        for (int i = 0; i < count; i++) {
            set.add(getAnimation(randomGenerator.nextInt(animationsList.size())));
        }
        return set;
    }
    

}
