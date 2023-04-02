package com.fiskmods.gameboii.graphics.screen;

import com.fiskmods.gameboii.Abstract2DGame;
import com.fiskmods.gameboii.Engine;
import com.fiskmods.gameboii.graphics.GameboiiFont;
import com.fiskmods.gameboii.graphics.screen.style.ScreenStyle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class Screen
{
    private final List<ConsoleButton> consoleButtons = new ArrayList<>();
    protected final ButtonList buttonList = new ButtonList(this);

    public GameboiiFont fontRenderer;
    public int width;
    public int height;

    public final ScreenStyle style;

    public Screen(ScreenStyle style)
    {
        this.style = style;
    }

    protected void addConsoleButton(ConsoleButtonType type, Supplier<String> name, Runnable action)
    {
        consoleButtons.add(new ConsoleButton(type, name, action));
    }

    protected void addConsoleButton(ConsoleButtonType button, String name, Runnable action)
    {
        addConsoleButton(button, () -> name, action);
    }

    public void onOpenScreen()
    {
        fontRenderer = Abstract2DGame.fontRenderer;
        width = Engine.getWidth();
        height = Engine.getHeight();

        consoleButtons.clear();
        buttonList.clear();
        initScreen();
    }

    public void initScreen()
    {
    }

    public void update()
    {
    }

    public boolean hasFocus()
    {
        return Engine.getScreen() == this;
    }

    public void playButtonPressSound()
    {
    }

    public void keyTyped(char character, int keyCode)
    {
        buttonList.keyTyped(keyCode);

        for (ConsoleButton button : consoleButtons)
        {
            if (keyCode == button.type.keyCode)
            {
                button.runnable.run();
                playButtonPressSound();
                return;
            }
        }
    }

    public void draw(Graphics2D g2d)
    {
        buttonList.draw(g2d);

        if (style.getConsoleStyle() != null && !consoleButtons.isEmpty() && hasFocus())
        {
            style.getConsoleStyle().draw(g2d, this, consoleButtons);
        }
    }

    public void drawBlank(Graphics2D g2d)
    {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);
    }
}
