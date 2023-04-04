package com.fiskmods.gameboii.resource;

import com.fiskmods.gameboii.Cartridge;
import com.fiskmods.gameboii.GameboiiMath;
import com.fiskmods.gameboii.sound.ISoundDispatcher;
import com.fiskmods.gameboii.sound.Sound;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class GameResourceLoader
{
    private static final BufferedImage MISSING_IMAGE = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);

    static
    {
        int i = 0xFF000000;
        int j = 0xFFF800F8;
        MISSING_IMAGE.setRGB(0, 0, i);
        MISSING_IMAGE.setRGB(1, 0, j);
        MISSING_IMAGE.setRGB(0, 1, j);
        MISSING_IMAGE.setRGB(1, 1, i);
    }

    private final Cartridge cartridge;
    private final IResourceProvider provider;

    public GameResourceLoader(Cartridge cartridge, IResourceProvider provider)
    {
        this.cartridge = cartridge;
        this.provider = provider;
    }

    public InputStream getInputStream(String path) throws IOException
    {
        return provider.getInputStream(cartridge, path);
    }

    public ISoundDispatcher loadSoundData(String path) throws IOException
    {
        return provider.loadSoundData(cartridge, path);
    }

    public BufferedImage loadImage(String path) throws IOException
    {
        InputStream in = getInputStream(path);
        return in != null ? ImageIO.read(in) : null;
    }

    public ImageResource load(String path, int width, int height)
    {
        try
        {
            BufferedImage image = loadImage(path);

            if (image != null)
            {
                return new ImageResource(width, height, () -> image);
            }

            System.err.println(String.format("Failed to load resource '%s' for cartridge %s", path, cartridge.id));
        }
        catch (IOException e)
        {
            System.err.println(String.format("Failed to load resource '%s' for cartridge %s", path, cartridge.id));
            e.printStackTrace();
        }

        AffineTransform tx = AffineTransform.getScaleInstance(width / 2.0, height / 2.0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return new ImageResource(width, height, () -> op.filter(MISSING_IMAGE, null));
    }

    public ImageResource loadGIF(String path, int width, int height, int delay)
    {
        ImageResource resource = load(path, width, height);
        BufferedImage image = resource.get();

        if (image == MISSING_IMAGE)
        {
            return resource;
        }

        BufferedImage[] data = getAnimationData(image, width, height);
        return new ImageResource(width, height, data, data.length, delay);
    }

    public AnimatedImageResource loadAnimation(String path, int width, int height)
    {
        ImageResource resource = load(path, width, height);
        BufferedImage image = resource.get();

        if (image == MISSING_IMAGE)
        {
            return new AnimatedImageResource(width, height, image);
        }

        return new AnimatedImageResource(width, height, getAnimationData(image, width, height));
    }

    private BufferedImage[] getAnimationData(BufferedImage image, int width, int height)
    {
        float scale = (float) image.getWidth() / width;
        BufferedImage[] data = new BufferedImage[GameboiiMath.floor((float) image.getHeight() / height / scale)];

        for (int i = 0; i < data.length; ++i)
        {
            data[i] = image.getSubimage(0, GameboiiMath.floor(i * height * scale), GameboiiMath.floor(image.getWidth() * scale), GameboiiMath.floor(height * scale));
        }

        return data;
    }

    public Sound loadSound(String path, Sound.Category category)
    {
        try
        {
            ISoundDispatcher data = loadSoundData(path);

            if (data != null)
            {
                return new Sound(() -> data, category);
            }

            System.err.println(String.format("Failed to load sound '%s' for cartridge %s", path, cartridge.id));
        }
        catch (IOException e)
        {
            System.err.println(String.format("Failed to load sound '%s' for cartridge %s", path, cartridge.id));
            e.printStackTrace();
        }

        return new Sound(null, category);
    }

    public Sound loadSound(String path, int variations, Sound.Category category)
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
                    System.err.println(String.format("Failed to load sound '%s' for cartridge %s", path + i, cartridge.id));
                    return new Sound(null, category);
                }

                array[i - 1] = data;
            }

            return new Sound(() -> array[rand.nextInt(variations)], category);
        }
        catch (IOException e)
        {
            System.err.println(String.format("Failed to load sound '%s' for cartridge %s", path, cartridge.id));
            e.printStackTrace();
        }

        return new Sound(null, category);
    }
}
