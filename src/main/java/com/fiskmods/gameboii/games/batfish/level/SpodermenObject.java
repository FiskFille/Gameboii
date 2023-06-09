package com.fiskmods.gameboii.games.batfish.level;

import static com.fiskmods.gameboii.games.batfish.level.BatfishPlayer.Skin.*;

import java.awt.Graphics2D;
import java.util.Random;

import com.fiskmods.gameboii.Engine;
import com.fiskmods.gameboii.games.batfish.Batfish;
import com.fiskmods.gameboii.games.batfish.BatfishDialogue;
import com.fiskmods.gameboii.games.batfish.BatfishGraphics;
import com.fiskmods.gameboii.games.batfish.screen.ScreenIngame;
import com.fiskmods.gameboii.graphics.screen.Screen;
import com.fiskmods.gameboii.level.LevelObject;
import com.fiskmods.gameboii.level.LivingLevelObject;

public class SpodermenObject extends LivingLevelObject
{
    public boolean unmasked;
    public boolean gunPointed;

    public SpodermenObject(float x, float y, Random rand)
    {
        super(x, y, 20, SPODERMEN.height, rand);
    }

    @Override
    public void draw(Graphics2D g2d, Screen screen, int x, int y, int screenWidth, int screenHeight, int scale)
    {
        drawBody(g2d, screen, x, y, scale, unmasked ? BatfishGraphics.robo_spodermen : SPODERMEN.getResource(), walkDelta > 0.01 ? 2 + (int) walkAmount % 2 : ticksExisted / 8 % 2, gunPointed ? 1 : 0, 20, 20);
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
            super.onUpdate();
        }
    }

    @Override
    public void onLivingUpdate()
    {
    }

    public void unmask()
    {
        if (!unmasked)
        {
            SpodermenMask mask = new SpodermenMask(posX, posY);
            OldSpice ship = new OldSpice(Engine.getWidth() / ScreenIngame.SCALE / 2 + 18, posY + 40);

            mask.motionX += (facing ? -1 : 1) * 6;
            mask.motionY += 3;
            ship.motionY += 0.2;

            level.addObject(mask);
            level.addObject(ship);
            unmasked = true;
        }
    }

    public void pointGun(boolean pointed)
    {
        gunPointed = pointed;
    }

    @Override
    public void onCollideWith(LevelObject obj)
    {
        if (obj instanceof BatfishPlayer)
        {
            BatfishPlayer player = (BatfishPlayer) obj;

            facing = !player.facing;
            player.startDialogue(BatfishDialogue.EPI_1, false);
        }
    }

    @Override
    public boolean canCollideWith(LevelObject obj)
    {
        return !(obj instanceof BatfishPlayer);
    }
}
