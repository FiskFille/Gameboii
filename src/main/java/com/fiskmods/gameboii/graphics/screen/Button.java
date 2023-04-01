package com.fiskmods.gameboii.graphics.screen;

import com.fiskmods.gameboii.games.batfish.BatfishGraphics;
import com.fiskmods.gameboii.graphics.GameboiiFont;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Button extends AbstractButton
{
    public final String text;
    private final Runnable runnable;

    public Button(Screen screen, Rectangle bounds, String buttonText, Runnable onClick)
    {
        super(screen, bounds);
        text = buttonText;
        runnable = onClick;
    }

    public static ButtonFactory factory(String buttonText, Runnable onClick)
    {
        return (screen, size) -> new Button(screen, size, buttonText, onClick);
    }

    @Override
    public void onPressed()
    {
        runnable.run();
    }

    @Override
    public void draw(Graphics2D g2d, boolean selected)
    {
        int i = selected ? 1 : 0;
        Screen.drawImage(g2d, BatfishGraphics.buttons, bounds.x, bounds.y, bounds.width, bounds.height, 0, i * 20, 200, (i + 1) * 20);

        g2d.setFont(GameboiiFont.BUTTON_TEXT);
        g2d.setColor(selected ? Color.YELLOW : Color.WHITE);
        g2d.drawString(text, bounds.x + bounds.width / 2 - screen.fontRenderer.getStringWidth(text) / 2, bounds.y + 28);
    }
}
