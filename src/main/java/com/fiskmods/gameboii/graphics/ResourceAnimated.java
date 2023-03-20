package com.fiskmods.gameboii.graphics;

import java.awt.image.BufferedImage;

import com.fiskmods.gameboii.GameboiiMath;

public class ResourceAnimated extends Resource
{
    private float progress;

    public ResourceAnimated(int width, int height, BufferedImage... data)
    {
        super(width, height, null);
        supplier = () -> data[Math.min(GameboiiMath.floor(progress * (data.length + 1)), data.length) % data.length];
    }

    public ResourceAnimated frame(float f)
    {
        progress = f;
        return this;
    }
}
