package com.fiskmods.gameboii.games.batfish.screen;

import com.fiskmods.gameboii.Engine;
import com.fiskmods.gameboii.graphics.GameboiiFont;
import com.fiskmods.gameboii.graphics.screen.ButtonLayout;
import com.fiskmods.gameboii.graphics.screen.ButtonList;
import com.fiskmods.gameboii.graphics.screen.ConsoleButtonType;
import com.fiskmods.gameboii.graphics.screen.Screen;
import com.fiskmods.gameboii.sound.Sound;

import java.awt.Color;
import java.awt.Graphics2D;

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
        ButtonList.ButtonBuilder builder = buttonList.builder();

        for (Sound.Category category : Sound.Category.values())
        {
            builder.addSlider(category.name, category::getVolume, category::setVolume);
        }

        int x = width / 2 - 200;
        int y = 140;
        builder.add("Back", () -> Engine.displayScreen(returnScreen))
                .layout(ButtonLayout.spaced(0, 45)
                        .andThen(ButtonLayout.offsetLast(0, 20)))
                .build(x, y, 400, 40);

        buttonList.cycle(-1);
        addConsoleButton(ConsoleButtonType.X, "Select", buttonList::press);
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
