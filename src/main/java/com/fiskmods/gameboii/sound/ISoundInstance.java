package com.fiskmods.gameboii.sound;

public interface ISoundInstance
{
    ISoundInstance NULL = new ISoundInstance()
    {
        @Override
        public void stop()
        {
        }

        @Override
        public void setVolume(float volume)
        {
        }
    };

    void stop();

    void setVolume(float volume);
}
