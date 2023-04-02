package com.fiskmods.gameboii.games.batfish.screen;

import com.fiskmods.gameboii.Engine;
import com.fiskmods.gameboii.games.batfish.Batfish;
import com.fiskmods.gameboii.games.batfish.BatfishGraphics;
import com.fiskmods.gameboii.graphics.Draw;
import com.fiskmods.gameboii.graphics.screen.ButtonLayout;
import com.fiskmods.gameboii.graphics.screen.ConsoleButtonType;
import com.fiskmods.gameboii.graphics.screen.style.Centering;

import java.awt.Graphics2D;

public class ScreenMainMenu extends BatfishScreen
{
    public ScreenMainMenu()
    {
        super(STYLE);
    }

    @Override
    public void initScreen()
    {
        buttonList.builder()
                .button("Start Game", () -> Engine.displayScreen(new ScreenIngame(true)))
                .button("Shop", () -> Engine.displayScreen(new ScreenShopMain()))
                .button("Options", () -> Engine.displayScreen(new ScreenOptions(null)))
                .button("Quit", Engine.system()::quit)
                .layout(ButtonLayout.spaced(0, 45))
                .center(Centering.X)
                .build(width / 2, 184, 400, 40);

        addConsoleButton(ConsoleButtonType.X, "Select", buttonList::press);
    }

    @Override
    public void draw(Graphics2D g2d)
    {
        drawBrickBackground(g2d);
        Draw.imageCentered(g2d, BatfishGraphics.logo, width / 2, 80, 436, 84);

        String s = "Copyright FiskFille 2014";
        g2d.setFont(BatfishGraphics.BUTTON_TEXT);
        fontRenderer.drawString(s, width - fontRenderer.getStringWidth(s) - 15, height - 10, 0xFFFFFF);

        drawCoinCount(g2d, width / 2 - 180, 151, Batfish.INSTANCE.player.totalCoins, false);
        super.draw(g2d);
    }
}
