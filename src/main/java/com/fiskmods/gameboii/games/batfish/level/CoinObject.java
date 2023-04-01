package com.fiskmods.gameboii.games.batfish.level;

import java.awt.Graphics2D;

import com.fiskmods.gameboii.games.batfish.BatfishGraphics;
import com.fiskmods.gameboii.games.batfish.BatfishSounds;
import com.fiskmods.gameboii.graphics.screen.Screen;
import com.fiskmods.gameboii.level.LevelObject;

public class CoinObject extends LevelObject
{
    public CoinObject(float x, float y)
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
