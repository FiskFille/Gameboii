package com.fiskmods.gameboii.games.batfish;

import com.fiskmods.gameboii.resource.GameResourceLoader;
import com.fiskmods.gameboii.resource.IResourceListener;
import com.fiskmods.gameboii.sound.Sound;

import static com.fiskmods.gameboii.sound.Sound.Category.EFFECT;
import static com.fiskmods.gameboii.sound.Sound.Category.MUSIC;

public enum BatfishSounds implements IResourceListener
{
    INSTANCE;

    public static Sound click;
    public static Sound coin;
    public static Sound death;
    public static Sound explode;
    public static Sound pop;
    public static Sound scream;
    public static Sound whistle;
    public static Sound woodbreak;
    public static Sound world;

    public static Sound title;

    @Override
    public void load(GameResourceLoader loader)
    {
        click = loader.loadSound("click", EFFECT);
        coin = loader.loadSound("coin", EFFECT);
        death = loader.loadSound("death", EFFECT);
        explode = loader.loadSound("explode", 4, EFFECT);
        pop = loader.loadSound("pop", EFFECT);
        scream = loader.loadSound("scream", EFFECT);
        whistle = loader.loadSound("whistle", EFFECT);
        woodbreak = loader.loadSound("woodbreak", EFFECT);
        world = loader.loadSound("world", EFFECT);

        title = loader.loadSound("title", MUSIC);
    }
}
