package com.fiskmods.gameboii.games.batfish.screen;

import com.fiskmods.gameboii.Engine;
import com.fiskmods.gameboii.games.batfish.Batfish;
import com.fiskmods.gameboii.graphics.GameboiiFont;
import com.fiskmods.gameboii.graphics.screen.ButtonLayout;
import com.fiskmods.gameboii.graphics.screen.ConsoleButtonType;
import com.fiskmods.gameboii.graphics.screen.Screen;

import java.awt.Color;
import java.awt.Graphics2D;

public class ScreenPause extends Screen
{
    private final Screen prevScreen;

    public ScreenPause(Screen screen)
    {
        prevScreen = screen;
    }

    @Override
    public void initScreen()
    {
        int x = width / 2 - 200;
        int y = 140;
        buttonList.builder()
                .add("Resume", () -> Engine.displayScreen(prevScreen))
                .add("Shop", () -> Engine.displayScreen(new ScreenShopMain()))
                .add("Options", () -> Engine.displayScreen(new ScreenOptions(prevScreen)))
                .add("Quit to Title", () ->
                {
                    Batfish.INSTANCE.player.reset();
                    Engine.displayScreen(null);
                })
                .layout(ButtonLayout.offsetLast(0, 45))
                .build(x, y, 400, 40);

        addConsoleButton(ConsoleButtonType.X, "Select", buttonList::press);
    }

    @Override
    public void draw(Graphics2D g2d)
    {
        if (prevScreen != null)
        {
            prevScreen.draw(g2d);
            g2d.setColor(new Color(0, 0, 0, 0.5F));
            g2d.fillRect(0, 0, width, height);
        }
        else
        {
            drawDefaultBackground(g2d);
        }

        String s = "Paused";
        g2d.setFont(GameboiiFont.SHOP_TITLE);
        fontRenderer.drawStringWithShadow(s, (width - fontRenderer.getStringWidth(s)) / 2, 70, 0xFFFFFF, 0);
        super.draw(g2d);
    }
}
