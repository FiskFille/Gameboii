package com.fiskmods.gameboii.games.batfish.screen;

import com.fiskmods.gameboii.Engine;
import com.fiskmods.gameboii.games.batfish.Batfish;
import com.fiskmods.gameboii.games.batfish.BatfishGraphics;
import com.fiskmods.gameboii.games.batfish.BatfishSounds;

import java.awt.Color;
import java.awt.Graphics2D;

public class ScreenLoading extends BatfishScreen
{
    private int topText;
    private int ticks;

    public ScreenLoading()
    {
        super(STYLE);
    }

    @Override
    public void update()
    {
        ++ticks;

        if (ticks > 150)
        {
            Engine.displayScreen(new ScreenMainMenu());
            Batfish.INSTANCE.titleThemeTicks = 0;
        }
        else if (ticks > 20)
        {
            if (ticks < 80)
            {
                ++topText;
            }
            else if (ticks == 90)
            {
                BatfishSounds.coin.play(1, 0.5F);
            }
        }
        else if (ticks == 20)
        {
            BatfishSounds.scream.play(1, 1);
        }
    }

    @Override
    public void draw(Graphics2D g2d)
    {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        if (topText > 0)
        {
            g2d.setFont(BatfishGraphics.GAME_OVER);
            int x = width / 2;
            int y = height / 4;

            String s = "";
            float w = 1 - Math.min(Math.max(ticks - 80, 0) / 10F, 1);

            if (w < 255)
            {
                int i = 0;
                g2d.setColor(new Color(0, 0, 0, w));

                while (true)
                {
                    if (i >= topText || fontRenderer.getStringWidth(s + "A") > width)
                    {
                        g2d.drawString(s, x - fontRenderer.getStringWidth(s) / 2, y += 40);
                        s = "";

                        if (i >= topText)
                        {
                            break;
                        }
                    }

                    s += "A";
                    ++i;
                }
            }

            w = Math.min(Math.max(ticks - 90, 0) / 20F, 1);
            s = "STUDIOS";

            g2d.setColor(new Color(1, 0, 0, w));
            g2d.drawString(s, x - fontRenderer.getStringWidth(s) / 2, height / 2);
        }
    }
}
