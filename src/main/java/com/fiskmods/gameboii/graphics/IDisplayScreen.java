package com.fiskmods.gameboii.graphics;

import java.awt.image.BufferedImage;

public interface IDisplayScreen
{
    void init(BufferedImage canvas, int width, int height);

    void draw(BufferedImage canvas);

    void clear();
}
