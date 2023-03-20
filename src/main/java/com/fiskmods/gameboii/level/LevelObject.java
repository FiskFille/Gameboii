package com.fiskmods.gameboii.level;

import java.awt.Graphics2D;
import java.util.List;

import com.fiskmods.gameboii.engine.BoundingBox;
import com.fiskmods.gameboii.graphics.Screen;

public abstract class LevelObject
{
    public BoundingBox boundingBox;
    public Level level;

    public int width;
    public int height;

    public float posX;
    public float posY;
    public float prevPosX;
    public float prevPosY;

    public boolean isDead;

    public LevelObject(float x, float y, int width, int height)
    {
        posX = prevPosX = x;
        posY = prevPosY = y;
        setSize(width, height);
    }

    public abstract void draw(Graphics2D g2d, Screen screen, int x, int y, int screenWidth, int screenHeight, int scale);

    public final boolean tick()
    {
        if (isDead)
        {
            return true;
        }

        onUpdate();
        return false;
    }

    public void onUpdate()
    {
        prevPosX = posX;
        prevPosY = posY;
    }

    public void onCollideWith(LevelObject obj)
    {
    }

    public boolean canCollideWith(LevelObject obj)
    {
        return true;
    }

    public void addCollisionBoxes(List<BoundingBox> list, BoundingBox colliding)
    {
        addCollisionBox(boundingBox, list, colliding);
    }

    protected void addCollisionBox(BoundingBox box, List<BoundingBox> list, BoundingBox colliding)
    {
        if (colliding == null || box.intersectsWith(colliding))
        {
            list.add(box);
        }
    }

    public static boolean canObjectsCollide(LevelObject o1, LevelObject o2)
    {
        return o1 != o2 && o1.canCollideWith(o2) && o2.canCollideWith(o1);
    }

    public int depthPlane()
    {
        return 0;
    }

    public void destroy()
    {
        isDead = true;
    }

    public void setSize(int w, int h)
    {
        boundingBox = BoundingBox.getBoundingBox(-w / 2, -h, w / 2, 0);
        boundingBox.offset(posX, posY);
        width = w;
        height = h;
    }

    public void onAttacked(LevelObject source)
    {
    }

    public float distanceToSq(float x, float y)
    {
        x -= posX;
        y -= posY;
        return x * x + y * y;
    }

    public float distanceTo(float x, float y)
    {
        return (float) Math.sqrt(distanceToSq(x, y));
    }
}
