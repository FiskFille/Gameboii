package com.fiskmods.gameboii.graphics.screen;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class AbstractButton
{
    protected final Screen screen;

    public final Rectangle bounds;
    protected int id;

    public AbstractButton(Screen screen, Rectangle bounds)
    {
        this.screen = screen;
        this.bounds = bounds;
    }

    public int id()
    {
        return id;
    }

    public void keyInput(int keyCode)
    {
    }

    public void onPressed()
    {
    }

    public abstract void draw(Graphics2D g2d, boolean selected);
}
