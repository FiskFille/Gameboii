package com.fiskmods.gameboii;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

import com.fiskmods.gameboii.graphics.IResourceLoader;
import com.fiskmods.gameboii.graphics.Resource;
import com.fiskmods.gameboii.graphics.ResourceAnimated;
import com.fiskmods.gameboii.sound.ISoundDispatcher;
import com.fiskmods.gameboii.sound.Sound;
import com.fiskmods.gameboii.sound.Sound.Category;

public abstract class GameboiiSystem implements ISaveObject, IResourceLoader
{
    private static final BufferedImage MISSING_IMAGE = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);

    static
    {
        int i = -16777216;
        int j = -524040;

        MISSING_IMAGE.setRGB(0, 0, i);
        MISSING_IMAGE.setRGB(1, 0, j);
        MISSING_IMAGE.setRGB(0, 1, j);
        MISSING_IMAGE.setRGB(1, 1, i);
    }

    private float[] volumes = new float[Category.values().length];

    public GameboiiSystem()
    {
        for (int i = 0; i < volumes.length; ++i)
        {
            volumes[i] = 1;
        }
    }

    public float getVolume(Category category)
    {
        return volumes[category.ordinal()];
    }

    public void setVolume(Category category, float volume)
    {
        volumes[category.ordinal()] = volume;
    }

    public abstract void quit();

    protected abstract void onLoad(Cartridge cartridge);

    protected abstract void onSave(Cartridge cartridge, byte[] data) throws Exception;

    public abstract void setPartialTicks(float f);

    public abstract float partialTicks();

    @Override
    public void read(ByteBuffer buf, int protocol)
    {
        for (int i = 0; i < volumes.length; ++i)
        {
            volumes[i] = (buf.get() & 0xFF) / 255F;
        }
    }

    @Override
    public void write(ByteBuffer buf)
    {
        for (float volume : volumes)
        {
            buf.put((byte) (volume * 255));
        }
    }

    @Override
    public Resource load(String path, int width, int height)
    {
        try
        {
            BufferedImage image = loadImage(path);

            if (image != null)
            {
                return new Resource(width, height, () -> image);
            }

            System.err.println(String.format("Failed to load resource '%s'", path));
        }
        catch (IOException e)
        {
            System.err.println(String.format("Failed to load resource '%s'", path));
            e.printStackTrace();
        }

        AffineTransform tx = AffineTransform.getScaleInstance(width / 2.0, height / 2.0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        return new Resource(width, height, () -> op.filter(MISSING_IMAGE, null));
    }

    @Override
    public Resource loadGIF(String path, int width, int height, int delay)
    {
        Resource resource = load(path, width, height);
        BufferedImage image = resource.get();

        if (image == MISSING_IMAGE)
        {
            return resource;
        }

        BufferedImage[] data = getAnimationData(image, width, height);
        return new Resource(width, height, data, data.length, delay);
    }

    @Override
    public ResourceAnimated loadAnimation(String path, int width, int height)
    {
        Resource resource = load(path, width, height);
        BufferedImage image = resource.get();

        if (image == MISSING_IMAGE)
        {
            return new ResourceAnimated(width, height, image);
        }

        return new ResourceAnimated(width, height, getAnimationData(image, width, height));
    }

    private BufferedImage[] getAnimationData(BufferedImage image, int width, int height)
    {
        float scale = (float) image.getWidth() / width;
        BufferedImage[] data = new BufferedImage[GameboiiMath.floor(image.getHeight() / height / scale)];

        for (int i = 0; i < data.length; ++i)
        {
            data[i] = image.getSubimage(0, GameboiiMath.floor(i * height * scale), GameboiiMath.floor(image.getWidth() * scale), GameboiiMath.floor(height * scale));
        }

        return data;
    }

    @Override
    public Sound loadSound(String path, Category category)
    {
        try
        {
            ISoundDispatcher data = loadSoundData(path);

            if (data != null)
            {
                return new Sound(() -> data, category);
            }

            System.err.println(String.format("Failed to load sound '%s'", path));
        }
        catch (IOException e)
        {
            System.err.println(String.format("Failed to load sound '%s'", path));
            e.printStackTrace();
        }

        return new Sound(null, category);
    }

    @Override
    public Sound loadSound(String path, int variations, Category category)
    {
        try
        {
            ISoundDispatcher[] array = new ISoundDispatcher[variations];
            Random rand = new Random();

            for (int i = 1; i <= array.length; ++i)
            {
                ISoundDispatcher data = loadSoundData(path + i);

                if (data == null)
                {
                    System.err.println(String.format("Failed to load sound '%s'", path + i));
                    return new Sound(null, category);
                }

                array[i - 1] = data;
            }

            return new Sound(() -> array[rand.nextInt(variations)], category);
        }
        catch (IOException e)
        {
            System.err.println(String.format("Failed to load sound '%s'", path));
            e.printStackTrace();
        }

        return new Sound(null, category);
    }
}
