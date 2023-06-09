package com.fiskmods.gameboii.games.batfish.level;

import java.awt.Graphics2D;

import com.fiskmods.gameboii.games.batfish.Batfish;
import com.fiskmods.gameboii.games.batfish.BatfishGraphics;
import com.fiskmods.gameboii.graphics.Draw;
import com.fiskmods.gameboii.graphics.screen.Screen;
import com.fiskmods.gameboii.level.LevelObject;
import com.fiskmods.gameboii.level.MovingLevelObject;

public class OldSpice extends MovingLevelObject
{
    public OldSpice(float x, float y)
    {
        super(x, y, 18, 28);
    }

    @Override
    public void draw(Graphics2D g2d, Screen screen, int x, int y, int screenWidth, int screenHeight, int scale)
    {
        Draw.image(g2d, BatfishGraphics.old_spice, x, y, width * scale, height * scale);
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
            if (posX > Batfish.INSTANCE.player.posX)
            {
                motionX -= 0.05;
            }

            motionX *= 0.95;
            motionY *= 0.98;
            super.onUpdate();
        }
    }

    @Override
    public boolean hasGravity()
    {
        return false;
    }

    @Override
    public boolean canCollideWith(LevelObject obj)
    {
        return false;
    }

    @Override
    public int depthPlane()
    {
        return -2;
    }
}
