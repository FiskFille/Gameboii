package com.fiskmods.gameboii;

import com.fiskmods.gameboii.resource.IResourceProvider;
import com.fiskmods.gameboii.sound.Sound.Category;

import java.nio.ByteBuffer;
import java.util.Arrays;

public abstract class GameboiiSystem implements ISaveObject, IResourceProvider
{
    private final float[] volume = new float[Category.values().length];

    public GameboiiSystem()
    {
        Arrays.fill(volume, 1);
    }

    public float getVolume(Category category)
    {
        return volume[category.ordinal()];
    }

    public void setVolume(Category category, float volume)
    {
        this.volume[category.ordinal()] = volume;
    }

    public abstract void quit();

    protected abstract void onLoad(Cartridge cartridge);

    protected abstract void onSave(Cartridge cartridge, byte[] data) throws Exception;

    public abstract void setPartialTicks(float f);

    public abstract float partialTicks();

    @Override
    public void read(ByteBuffer buf, int protocol)
    {
        for (int i = 0; i < volume.length; ++i)
        {
            volume[i] = (buf.get() & 0xFF) / 255F;
        }
    }

    @Override
    public void write(ByteBuffer buf)
    {
        for (float volume : volume)
        {
            buf.put((byte) (volume * 255));
        }
    }
}
