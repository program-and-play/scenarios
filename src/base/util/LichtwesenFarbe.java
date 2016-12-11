package util;

import greenfoot.GreenfootImage;

public enum LichtwesenFarbe {
    BEIGE, GELB, LILA, ROSA, ROT, BLAU;

	public LichtwesenFarbe next() {
        LichtwesenFarbe[] values = values();
        return values[(ordinal()+1) % values.length];
	}

	private String bildName() {
        String name = name().toLowerCase();
        return String.format("lichtwesen_neu_%s.png", name);
    }

    public GreenfootImage bild() {
        GreenfootImage tmp = new GreenfootImage(bildName());
        tmp.scale(60, 60);
        return tmp;
    }
}