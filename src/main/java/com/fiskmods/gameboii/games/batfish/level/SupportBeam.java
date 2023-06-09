package com.fiskmods.gameboii.games.batfish.level;

import java.awt.Graphics2D;

import com.fiskmods.gameboii.games.batfish.BatfishGraphics;
import com.fiskmods.gameboii.graphics.Draw;
import com.fiskmods.gameboii.graphics.screen.Screen;
import com.fiskmods.gameboii.level.LevelObject;

public class SupportBeam extends LevelObject
{
    public SupportBeam(float x, float y)
    {
        super(x, y, 10, BatfishSection.HEIGHT);
    }

    @Override
    public void draw(Graphics2D g2d, Screen screen, int x, int y, int screenWidth, int screenHeight, int scale)
    {
        for (int h = 0; h < height; h += 16)
        {
            Draw.image(g2d, BatfishGraphics.support_beam, x, y + h * scale, width * scale, Math.min(16, height - h) * scale);
        }
    }
}
