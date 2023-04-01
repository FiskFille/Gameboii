package com.fiskmods.gameboii.games.batfish.screen;

import com.fiskmods.gameboii.Engine;
import com.fiskmods.gameboii.games.batfish.Batfish;
import com.fiskmods.gameboii.games.batfish.BatfishGraphics;
import com.fiskmods.gameboii.graphics.GameboiiFont;
import com.fiskmods.gameboii.graphics.screen.ButtonLayout;
import com.fiskmods.gameboii.graphics.screen.ConsoleButtonType;
import com.fiskmods.gameboii.graphics.screen.Screen;

import java.awt.Graphics2D;

public class ScreenMainMenu extends Screen
{
    @Override
    public void initScreen()
    {
        int x = width / 2 - 200;
        int y = 184;
        buttonList.builder()
                .add("Start Game", () -> Engine.displayScreen(new ScreenIngame(true)))
                .add("Shop", () -> Engine.displayScreen(new ScreenShopMain()))
                .add("Options", () -> Engine.displayScreen(new ScreenOptions(null)))
                .add("Quit", Engine.system()::quit)
                .layout(ButtonLayout.spaced(0, 45))
                .build(x, y, 400, 40);

        addConsoleButton(ConsoleButtonType.X, "Select", buttonList::press);
    }

    @Override
    public void draw(Graphics2D g2d)
    {
        drawDefaultBackground(g2d);
        drawCenteredImage(g2d, BatfishGraphics.logo, width / 2, 80, 436, 84);

        String s = "Copyright FiskFille 2014";
        g2d.setFont(GameboiiFont.BUTTON_TEXT);
        fontRenderer.drawString(s, width - fontRenderer.getStringWidth(s) - 15, height - 10, 0xFFFFFF);

        drawCoinCount(g2d, width / 2 - 180, 151, Batfish.INSTANCE.player.totalCoins, false);
        super.draw(g2d);
    }
}
