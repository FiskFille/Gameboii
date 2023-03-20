package com.fiskmods.gameboii.batfish.screen;

import java.awt.Color;
import java.awt.Graphics2D;

import com.fiskmods.gameboii.Engine;
import com.fiskmods.gameboii.graphics.GameboiiFont;
import com.fiskmods.gameboii.graphics.Screen;
import com.fiskmods.gameboii.sound.Sound.Category;

public class ScreenOptions extends Screen
{
    private final Screen bottomScreen, returnScreen;

    public ScreenOptions(Screen bottom)
    {
        returnScreen = Engine.getScreen();
        bottomScreen = bottom;
    }

    @Override
    public void initScreen()
    {
        int x = width / 2 - 200;
        int y = 140;
        int i = 0;

        for (Category category : Category.values())
        {
            new Slider(x, y + 45 * i, 400, 40, category.name, category::getVolume, category::setVolume);
            ++i;
        }

        new Button(x, y + 45 * i + 20, 400, 40, "Back", () -> Engine.displayScreen(returnScreen));
        cycleButtons(-1);

        addConsoleButton(ConsoleButtonType.X, "Select", this::pressButton);
    }

    @Override
    public void draw(Graphics2D g2d)
    {
        if (bottomScreen != null)
        {
            bottomScreen.draw(g2d);
            g2d.setColor(new Color(0, 0, 0, 0.5F));
            g2d.fillRect(0, 0, width, height);
        }
        else
        {
            drawDefaultBackground(g2d);
        }

        String s = "Options";
        g2d.setFont(GameboiiFont.SHOP_TITLE);
        fontRenderer.drawStringWithShadow(s, (width - fontRenderer.getStringWidth(s)) / 2, 70, 0xFFFFFF, 0);
        super.draw(g2d);
    }
}
