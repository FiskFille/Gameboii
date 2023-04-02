package com.fiskmods.gameboii.games.batfish.screen;

import com.fiskmods.gameboii.Engine;
import com.fiskmods.gameboii.games.batfish.Batfish;
import com.fiskmods.gameboii.games.batfish.BatfishGraphics;
import com.fiskmods.gameboii.graphics.screen.ButtonLayout;
import com.fiskmods.gameboii.graphics.screen.ConsoleButtonType;
import com.fiskmods.gameboii.graphics.screen.Screen;
import com.fiskmods.gameboii.graphics.screen.style.Centering;

import java.awt.Color;
import java.awt.Graphics2D;

public class ScreenPause extends BatfishScreen
{
    private final Screen prevScreen;

    public ScreenPause(Screen screen)
    {
        super(STYLE);
        prevScreen = screen;
    }

    @Override
    public void initScreen()
    {
        buttonList.builder()
                .button("Resume", () -> Engine.displayScreen(prevScreen))
                .button("Shop", () -> Engine.displayScreen(new ScreenShopMain()))
                .button("Options", () -> Engine.displayScreen(new ScreenOptions(prevScreen)))
                .button("Quit to Title", () ->
                {
                    Batfish.INSTANCE.player.reset();
                    Engine.displayScreen(null);
                })
                .layout(ButtonLayout.spaced(0, 45)
                        .andThen(ButtonLayout.offsetLast(0, 20)))
                .center(Centering.X)
                .build(width / 2, 140, 400, 40);

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
            drawBrickBackground(g2d);
        }

        String s = "Paused";
        g2d.setFont(BatfishGraphics.SHOP_TITLE);
        fontRenderer.drawStringWithShadow(s, (width - fontRenderer.getStringWidth(s)) / 2, 70, 0xFFFFFF, 0);
        super.draw(g2d);
    }
}
