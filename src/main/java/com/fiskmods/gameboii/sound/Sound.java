package com.fiskmods.gameboii.sound;

import java.util.function.Supplier;

import com.fiskmods.gameboii.Engine;

public class Sound
{
    private final Supplier<ISoundDispatcher> sound;
    public final Category category;

    public Sound(Supplier<ISoundDispatcher> sound, Category category)
    {
        this.sound = sound;
        this.category = category;
    }

    public ISoundInstance play(float volume, float pitch)
    {
        return sound != null ? sound.get().dispatch(category, volume, pitch) : ISoundInstance.NULL;
    }

    public enum Category
    {
        EFFECT("Sounds"),
        MUSIC("Music");

        public final String name;

        Category(String name)
        {
            this.name = name;
        }

        public float getVolume()
        {
            return Engine.system().getVolume(this);
        }

        public void setVolume(float volume)
        {
            Engine.system().setVolume(this, volume);
        }
    }
}
