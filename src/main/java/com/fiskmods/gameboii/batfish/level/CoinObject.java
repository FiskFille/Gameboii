package com.fiskmods.gameboii.batfish.level;

import java.awt.Graphics2D;

import com.fiskmods.gameboii.batfish.BatfishGraphics;
import com.fiskmods.gameboii.batfish.BatfishSounds;
import com.fiskmods.gameboii.graphics.Screen;
import com.fiskmods.gameboii.level.LevelObject;

public class CoinObject extends LevelObject
{
    public CoinObject(double x, double y)
    {
        super(x, y, 8, 8);
    }

    @Override
    public void draw(Graphics2D g2d, Screen screen, int x, int y, int screenWidth, int screenHeight, int scale)
    {
        screen.drawImage(g2d, BatfishGraphics.coin, x, y, width * scale, height * scale);
    }

    @Override
    public void onCollideWith(LevelObject obj)
    {
        if (obj instanceof BatfishPlayer && !isDead)
        {
            ++((BatfishPlayer) obj).currentCoins;
            BatfishSounds.coin.play(1, 1);
            destroy();
        }
    }

    @Override
    public boolean canCollideWith(LevelObject obj)
    {
        return false;
    }
}
