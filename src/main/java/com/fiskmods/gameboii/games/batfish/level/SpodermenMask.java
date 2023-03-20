package com.fiskmods.gameboii.games.batfish.level;

import java.awt.Graphics2D;

import com.fiskmods.gameboii.games.batfish.Batfish;
import com.fiskmods.gameboii.games.batfish.BatfishGraphics;
import com.fiskmods.gameboii.graphics.Screen;
import com.fiskmods.gameboii.level.LevelObject;
import com.fiskmods.gameboii.level.LivingLevelObject;
import com.fiskmods.gameboii.level.MovingLevelObject;

public class SpodermenMask extends MovingLevelObject
{
    public SpodermenMask(float x, float y)
    {
        super(x, y, 3, 4);
    }

    @Override
    public void draw(Graphics2D g2d, Screen screen, int x, int y, int screenWidth, int screenHeight, int scale)
    {
        screen.drawImage(g2d, BatfishGraphics.spodermen_mask, x, y, width * scale, height * scale);
    }

    @Override
    public void onUpdate()
    {
        if (Batfish.INSTANCE.worldPowerup > 0)
        {
            prevPosX = posX;
            prevPosY = posY;
        }
        else
        {
            if (onGround)
            {
                motionX *= 0.8;
            }

            super.onUpdate();
        }
    }

    @Override
    public boolean canCollideWith(LevelObject obj)
    {
        return !(obj instanceof LivingLevelObject);
    }
}
