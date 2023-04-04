package com.fiskmods.gameboii.graphics;

import com.fiskmods.gameboii.resource.ImageResource;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Draw
{
    public static void image(Graphics2D g2d, ImageResource resource, int x, int y)
    {
        image(g2d, resource, x, y, resource.getWidth(), resource.getHeight());
    }

    public static void image(Graphics2D g2d, ImageResource resource, int x, int y, int scale)
    {
        image(g2d, resource, x, y, resource.getWidth() * scale, resource.getHeight() * scale);
    }

    public static void imageCentered(Graphics2D g2d, ImageResource resource, int x, int y)
    {
        image(g2d, resource, x - resource.getWidth() / 2, y - resource.getHeight() / 2);
    }

    public static void image(Graphics2D g2d, ImageResource resource, int x, int y, int width, int height)
    {
        g2d.drawImage(resource.get(), x, y, width, height, null);
    }

    public static void imageCentered(Graphics2D g2d, ImageResource resource, int x, int y, int width, int height)
    {
        image(g2d, resource, x - width / 2, y - height / 2, width, height);
    }

    public static void image(Graphics2D g2d, ImageResource resource, int x, int y, int width, int height, int srcX1, int srcY1, int srcX2, int srcY2)
    {
        g2d.drawImage(resource.get(), x, y, width + x, height + y, srcX1, srcY1, srcX2, srcY2, null);
    }

    public static void image(Graphics2D g2d, ImageResource resource, int x, int y, int width, int height, Rectangle src)
    {
        image(g2d, resource, x, y, width, height, (int) src.getMinX(), (int) src.getMinY(), (int) src.getMaxX(), (int) src.getMaxY());
    }

    public static void imageCentered(Graphics2D g2d, ImageResource resource, int x, int y, int width, int height, int srcX1, int srcY1, int srcX2, int srcY2)
    {
        image(g2d, resource, x - width / 2, y - height / 2, width, height, srcX1, srcY1, srcX2, srcY2);
    }

    public static void imageCentered(Graphics2D g2d, ImageResource resource, int x, int y, int width, int height, Rectangle src)
    {
        imageCentered(g2d, resource, x, y, width, height, (int) src.getMinX(), (int) src.getMinY(), (int) src.getMaxX(), (int) src.getMaxY());
    }
}
