package com.fiskmods.gameboii.resource;

import com.fiskmods.gameboii.GameboiiMath;

import java.awt.image.BufferedImage;

public class AnimatedImageResource extends ImageResource
{
    private float progress;

    public AnimatedImageResource(int width, int height, BufferedImage... data)
    {
        super(width, height, null);
        supplier = () -> data[Math.min(GameboiiMath.floor(progress * (data.length + 1)), data.length) % data.length];
    }

    public AnimatedImageResource frame(float f)
    {
        progress = f;
        return this;
    }
}
