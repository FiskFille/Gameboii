package com.fiskmods.gameboii.level;

import java.awt.Graphics2D;
import java.util.Random;

import com.fiskmods.gameboii.graphics.Resource;
import com.fiskmods.gameboii.graphics.Screen;

public abstract class LivingLevelObject extends MovingLevelObject
{
    public int ticksExisted;

    public boolean facing;
    public float walkAmount;
    public float walkDelta;

    public LivingLevelObject(float x, float y, int width, int height, Random rand)
    {
        super(x, y + height, width, height);

        if (rand != null)
        {
            ticksExisted = rand.nextInt(20);
            facing = rand.nextBoolean();
        }
    }

    public void drawBody(Graphics2D g2d, Screen screen, int x, int y, int scale, Resource resource, int frameX, int frameY, int w, int h)
    {
        int srcX1 = frameX * w;
        int srcY1 = frameY * h;
        int srcX2 = srcX1 + w;

        if (facing)
        {
            int i = srcX1;
            srcX1 = srcX2;
            srcX2 = i;
        }

        x -= (w - width) / 2 * scale;
        screen.drawImage(g2d, resource, x, y, w * scale, h * scale, srcX1, srcY1, srcX2, srcY1 + h);
    }

    @Override
    public void onUpdate()
    {
        ++ticksExisted;
        super.onUpdate();
        onLivingUpdate();

        if (!hasGravity())
        {
            float f = 0.8F;
            motionX *= f;
            motionY *= f;
        }
        else if (onGround)
        {
            motionX *= 0.75;
        }
        else
        {
            motionX *= 0.8;
        }

        float prev = walkAmount;
        walkAmount += Math.abs(prevPosX - posX) / 4;
        walkAmount %= 2;
        walkDelta = Math.abs(walkAmount - prev);
    }

    public abstract void onLivingUpdate();

    @Override
    public void move(float x, float y)
    {
        if (x > 0)
        {
            facing = false;
        }
        else if (x < 0)
        {
            facing = true;
        }

        super.move(x, y);
    }
}
