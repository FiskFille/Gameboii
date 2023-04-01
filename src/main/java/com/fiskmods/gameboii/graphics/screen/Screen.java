package com.fiskmods.gameboii.graphics.screen;

import com.fiskmods.gameboii.Abstract2DGame;
import com.fiskmods.gameboii.Engine;
import com.fiskmods.gameboii.games.batfish.BatfishGraphics;
import com.fiskmods.gameboii.games.batfish.BatfishSounds;
import com.fiskmods.gameboii.graphics.GameboiiFont;
import com.fiskmods.gameboii.graphics.Resource;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

public abstract class Screen
{
    private final List<ConsoleButton> consoleButtons = new ArrayList<>();
    protected final ButtonList buttonList = new ButtonList(this);

    public GameboiiFont fontRenderer;
    protected int width;
    protected int height;

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

    public void draw(Graphics2D g2d)
    {
        buttonList.draw(g2d);

        if (!consoleButtons.isEmpty() && Engine.getScreen() == this)
        {
            g2d.setColor(new Color(0, 0, 0, 0.5F));

            int x = 0;
            int y = height - 60;
            g2d.fillRect(x, y, width, 40);

            x = width - 40;
            y += 27;
            g2d.setFont(GameboiiFont.BUTTON_TEXT);

            for (ConsoleButton button : consoleButtons)
            {
                String s = button.name.get();
                x -= fontRenderer.getStringWidth(s);
                fontRenderer.drawString(s, x, y, 0xFFFFFF);

                s = button.type.name();
                x -= 22;
                Rectangle rect = new Rectangle(button.type.ordinal() % 2 * 26, button.type.ordinal() / 2 * 26, 26, 26);
                drawCenteredImage(g2d, BatfishGraphics.console_buttons, x, y - 8, 26, 26, rect);
                fontRenderer.drawStringWithShadow(s, x - fontRenderer.getStringWidth(s) / 2, y, 0xFFFFFF, 0);
                x -= 45;
            }
        }
    }

    public void keyTyped(char character, int keyCode)
    {
        buttonList.keyTyped(keyCode);

        for (ConsoleButton button : consoleButtons)
        {
            if (keyCode == button.type.keyCode)
            {
                button.runnable.run();
                BatfishSounds.click.play(1, 1);
                return;
            }
        }
    }

    public void update()
    {
    }

    public void drawDefaultBackground(Graphics2D g2d)
    {
        final int size = 48;

        for (int x = 0; x < width / size; ++x)
        {
            for (int y = 0; y < height / size; ++y)
            {
                drawImage(g2d, BatfishGraphics.stone, size * x, size * y, size, size);
            }
        }
    }

    public void drawCoinCount(Graphics2D g2d, int x, int y, int coins, boolean center)
    {
        g2d.setFont(GameboiiFont.DEFAULT);
        String s = String.format(Locale.ROOT, "%,d", coins);

        if (center)
        {
            x -= (25 + 2 + fontRenderer.getStringWidth(s)) / 2;
            y -= 16;
        }

        fontRenderer.drawStringWithShadow(s, x + 25, y + 11, 0xFFCD21, 0x3F2700, 2);
        drawCenteredImage(g2d, BatfishGraphics.coin, x, y, 32, 32);
    }

    public static void drawImage(Graphics2D g2d, Resource resource, int x, int y)
    {
        drawImage(g2d, resource, x, y, resource.getWidth(), resource.getHeight());
    }

    public static void drawImage(Graphics2D g2d, Resource resource, int x, int y, int scale)
    {
        drawImage(g2d, resource, x, y, resource.getWidth() * scale, resource.getHeight() * scale);
    }

    public static void drawCenteredImage(Graphics2D g2d, Resource resource, int x, int y)
    {
        drawImage(g2d, resource, x - resource.getWidth() / 2, y - resource.getHeight() / 2);
    }

    public static void drawImage(Graphics2D g2d, Resource resource, int x, int y, int width, int height)
    {
        g2d.drawImage(resource.get(), x, y, width, height, null);
    }

    public static void drawCenteredImage(Graphics2D g2d, Resource resource, int x, int y, int width, int height)
    {
        drawImage(g2d, resource, x - width / 2, y - height / 2, width, height);
    }

    public static void drawImage(Graphics2D g2d, Resource resource, int x, int y, int width, int height, int srcX1, int srcY1, int srcX2, int srcY2)
    {
        g2d.drawImage(resource.get(), x, y, width + x, height + y, srcX1, srcY1, srcX2, srcY2, null);
    }

    public static void drawImage(Graphics2D g2d, Resource resource, int x, int y, int width, int height, Rectangle src)
    {
        drawImage(g2d, resource, x, y, width, height, (int) src.getMinX(), (int) src.getMinY(), (int) src.getMaxX(), (int) src.getMaxY());
    }

    public static void drawCenteredImage(Graphics2D g2d, Resource resource, int x, int y, int width, int height, int srcX1, int srcY1, int srcX2, int srcY2)
    {
        drawImage(g2d, resource, x - width / 2, y - height / 2, width, height, srcX1, srcY1, srcX2, srcY2);
    }

    public static void drawCenteredImage(Graphics2D g2d, Resource resource, int x, int y, int width, int height, Rectangle src)
    {
        drawCenteredImage(g2d, resource, x, y, width, height, (int) src.getMinX(), (int) src.getMinY(), (int) src.getMaxX(), (int) src.getMaxY());
    }
}
