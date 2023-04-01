package com.fiskmods.gameboii.graphics.screen;

import java.awt.Rectangle;

@FunctionalInterface
public interface ButtonFactory
{
    AbstractButton create(Screen screen, Rectangle bounds);
}
