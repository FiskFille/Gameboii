package com.fiskmods.gameboii.graphics;

import java.awt.image.BufferedImage;
import java.util.function.Supplier;

import com.fiskmods.gameboii.Engine;

public class Resource
{
    protected Supplier<BufferedImage> supplier;

    private final int width;
    private final int height;

    public Resource(int width, int height, Supplier<BufferedImage> supplier)
    {
        this.width = width;
        this.height = height;
        this.supplier = supplier;
    }

    public Resource(int width, int height, BufferedImage[] data, int frames, int delay)
    {
        this(width, height, () -> data[(int) (Engine.getSystemTime() / 50 / delay % frames)]);
    }

    public Resource(BufferedImage image)
    {
        this(image.getWidth(), image.getHeight(), () -> image);
    }

    public BufferedImage get()
    {
        return supplier.get();
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}
