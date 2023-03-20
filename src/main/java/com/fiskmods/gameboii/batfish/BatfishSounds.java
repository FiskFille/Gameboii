package com.fiskmods.gameboii.batfish;

import static com.fiskmods.gameboii.sound.Sound.Category.*;

import com.fiskmods.gameboii.graphics.IResourceListener;
import com.fiskmods.gameboii.graphics.IResourceLoader;
import com.fiskmods.gameboii.sound.Sound;

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
    public void reload(IResourceLoader loader)
    {
        click = loader.loadSound("batfish/click", EFFECT);
        coin = loader.loadSound("batfish/coin", EFFECT);
        death = loader.loadSound("batfish/death", EFFECT);
        explode = loader.loadSound("batfish/explode", 4, EFFECT);
        pop = loader.loadSound("batfish/pop", EFFECT);
        scream = loader.loadSound("batfish/scream", EFFECT);
        whistle = loader.loadSound("batfish/whistle", EFFECT);
        woodbreak = loader.loadSound("batfish/woodbreak", EFFECT);
        world = loader.loadSound("batfish/world", EFFECT);

        title = loader.loadSound("batfish/title", MUSIC);
    }
}
