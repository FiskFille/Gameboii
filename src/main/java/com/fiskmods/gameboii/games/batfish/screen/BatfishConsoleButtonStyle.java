package com.fiskmods.gameboii.games.batfish.screen;

import com.fiskmods.gameboii.games.batfish.BatfishGraphics;
import com.fiskmods.gameboii.graphics.Draw;
import com.fiskmods.gameboii.graphics.screen.ConsoleButton;
import com.fiskmods.gameboii.graphics.screen.Screen;
import com.fiskmods.gameboii.graphics.screen.style.ConsoleButtonStyle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;

public enum BatfishConsoleButtonStyle implements ConsoleButtonStyle
{
    INSTANCE;

    @Override
    public void draw(Graphics2D g2d, Screen screen, List<ConsoleButton> buttons)
    {
        int y = screen.height - 60;
        g2d.setColor(new Color(0, 0, 0, 0.5F));
        g2d.fillRect(0, y, screen.width, 40);

        int x = screen.width - 40;
        y += 27;
        g2d.setFont(BatfishGraphics.BUTTON_TEXT);

        for (ConsoleButton button : buttons)
        {
            String s = button.name.get();
            x -= screen.fontRenderer.getStringWidth(s);
            screen.fontRenderer.drawString(s, x, y, 0xFFFFFF);

            s = button.type.name();
            x -= 22;
            Rectangle rect = new Rectangle(button.type.ordinal() % 2 * 26, button.type.ordinal() / 2 * 26, 26, 26);
            Draw.imageCentered(g2d, BatfishGraphics.console_buttons, x, y - 8, 26, 26, rect);
            screen.fontRenderer.drawStringWithShadow(s, x - screen.fontRenderer.getStringWidth(s) / 2, y, 0xFFFFFF, 0);
            x -= 45;
        }
    }
}
