package com.fiskmods.gameboii.graphics.screen;

import com.fiskmods.gameboii.Engine;
import com.fiskmods.gameboii.engine.Dialogue;
import com.fiskmods.gameboii.graphics.Draw;
import com.fiskmods.gameboii.graphics.screen.style.ScreenStyle;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class ScreenDialogue extends Screen
{
    private static final Font TEXT_FONT = new Font("Calibri", Font.PLAIN, 22);
    private static final Font SPEAKER_FONT = new Font("Arial", Font.BOLD, 22);

    private final Screen prevScreen;
    private final boolean pauseGame;

    private Dialogue currDialogue;
    private boolean isSkippable = true;

    public ScreenDialogue(ScreenStyle style, Dialogue dialogue, boolean pause)
    {
        super(style);
        prevScreen = Engine.getScreen();
        currDialogue = dialogue;
        pauseGame = pause;
    }

    @Override
    public void initScreen()
    {
        addConsoleButton(ConsoleButtonType.C, "Next", () -> cycleDialogue(true));
        addConsoleButton(ConsoleButtonType.X, "Back", () -> cycleDialogue(false));

        if (isSkippable)
        {
            addConsoleButton(ConsoleButtonType.Z, "Exit", () -> Engine.displayScreen(prevScreen));
        }
    }

    public void setSkippable(boolean skippable)
    {
        if (skippable != isSkippable)
        {
            isSkippable = skippable;
            onOpenScreen();
        }
    }

    private void cycleDialogue(boolean next)
    {
        if (next)
        {
            if (currDialogue.next() != null)
            {
                currDialogue = currDialogue.next();
                currDialogue.executeAction(this);
            }
            else if (!isSkippable)
            {
                Engine.displayScreen(prevScreen);
            }
        }
        else if (currDialogue.prev != null)
        {
            currDialogue = currDialogue.prev;
            currDialogue.executeAction(this);
        }
    }

    @Override
    public void update()
    {
        if (prevScreen != null && !pauseGame)
        {
            prevScreen.update();
        }
    }

    @Override
    public void draw(Graphics2D g2d)
    {
        if (prevScreen != null)
        {
            prevScreen.draw(g2d);
        }
        else
        {
            drawBlank(g2d);
        }

        int b = 2;
        int w = 48;
        int h = g2d.getFontMetrics(TEXT_FONT).getHeight();
        Rectangle r = new Rectangle(0, 20, width, w + 24 + 2 * 10);

        if (currDialogue.text.length > 2)
        {
            r.height += (currDialogue.text.length - 2) * h;
        }

        g2d.setColor(Color.GRAY);
        g2d.fill(r);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(r.x + b, r.y + b, r.width - b * 2, r.height - b * 2);

        b = 10;
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(r.x + b, r.y + b + 24, w, w);
        Draw.image(g2d, currDialogue.speaker.resource.get(), r.x + b, r.y + b + 24, w, w, 5, -2, 15, 10 - 2);

        g2d.setFont(SPEAKER_FONT);
        fontRenderer.drawString(currDialogue.speaker.name, r.x + b, r.y + b + 16, 0xFFFFFF);

        for (int i = 0; i < currDialogue.text.length; ++i)
        {
            fontRenderer.drawString(currDialogue.text[i], r.x + w + b + 10, r.y + b + 42 + i * h, Color.WHITE, TEXT_FONT);
        }

        super.draw(g2d);
    }
}
