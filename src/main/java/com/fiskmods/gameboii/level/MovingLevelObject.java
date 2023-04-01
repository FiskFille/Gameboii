package com.fiskmods.gameboii.level;

import com.fiskmods.gameboii.GameboiiMath;
import com.fiskmods.gameboii.engine.BoundingBox;

import java.util.List;

public abstract class MovingLevelObject extends LevelObject
{
    public float motionX;
    public float motionY;

    public boolean onGround;
    public boolean isCollidedHorizontally;
    public boolean isCollidedVertically;

    public float stepHeight;

    public MovingLevelObject(float x, float y, int width, int height)
    {
        super(x, y, width, height);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (level != null)
        {
            float mx = GameboiiMath.clamp(motionX, -0.01F, 0.01F);
            float my = GameboiiMath.clamp(motionY, -0.01F, 0.01F);
            move(motionX, motionY);

            for (LevelObject obj : level.getIntersectingObjects(this, boundingBox.addCoord(mx, my)))
            {
                onCollideWith(obj);
                obj.onCollideWith(this);
            }

            if (hasGravity())
            {
                float f = 0.9F;
                motionY -= 1.5;
                motionX *= f;
                motionY *= f;
            }
        }
    }

    public boolean hasGravity()
    {
        return true;
    }

    public void move(float x, float y)
    {
        List<BoundingBox> list = level.getCollidingBoundingBoxes(this, boundingBox.addCoord(x, y));
        float x1 = x;
        float y1 = y;

        for (BoundingBox element : list)
        {
            y = element.calculateYOffset(boundingBox, y);
        }

        boundingBox.offset(0, y);

        for (BoundingBox element : list)
        {
            x = element.calculateXOffset(boundingBox, x);
        }

        boundingBox.offset(x, 0);

        if (stepHeight > 0 && y1 != y && y1 < 0 && x != x1)
        {
            float x2 = x;
            float y2 = y;
            x = x1;
            y = stepHeight;
            BoundingBox bb = boundingBox.copy();
            list = level.getCollidingBoundingBoxes(this, boundingBox.addCoord(x, y));

            for (BoundingBox element : list)
            {
                y = element.calculateYOffset(boundingBox, y);
            }

            boundingBox.offset(0, y);
            x /= 1.3F;

            for (BoundingBox element : list)
            {
                x = element.calculateXOffset(boundingBox, x);
            }

            boundingBox.offset(x, 0);
            y = -stepHeight;

            for (BoundingBox element : list)
            {
                y = element.calculateYOffset(boundingBox, y);
            }

            boundingBox.offset(0, y);

            if (x2 * x2 >= x * x)
            {
                x = x2;
                y = y2;
                boundingBox.setBB(bb);
            }
        }

        onGround = y1 != y && y1 < 0;
        isCollidedHorizontally = x1 != x;
        isCollidedVertically = y1 != y;

        if (Math.abs(boundingBox.minX - Math.round(boundingBox.minX)) < 1E-3)
        {
            boundingBox.minX = Math.round(boundingBox.minX);
        }

        if (Math.abs(boundingBox.maxX - Math.round(boundingBox.maxX)) < 1E-3)
        {
            boundingBox.maxX = Math.round(boundingBox.maxX);
        }

        if (Math.abs(boundingBox.minY - Math.round(boundingBox.minY)) < 1E-3)
        {
            boundingBox.minY = Math.round(boundingBox.minY);
        }

        if (Math.abs(boundingBox.maxY - Math.round(boundingBox.maxY)) < 1E-3)
        {
            boundingBox.maxY = Math.round(boundingBox.maxY);
        }

        if (x1 != x)
        {
            motionX = 0;
        }

        if (y1 != y)
        {
            motionY = 0;
        }

        posX = (boundingBox.minX + boundingBox.maxX) / 2;
        posY = boundingBox.maxY;
    }

    public void setPosition(float x, float y)
    {
        posX = prevPosX = x;
        posY = prevPosY = y;
        setSize(width, height);
    }
}
