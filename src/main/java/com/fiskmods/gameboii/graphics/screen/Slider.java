package com.fiskmods.gameboii.graphics.screen;

import com.fiskmods.gameboii.engine.InputKey;
import com.fiskmods.gameboii.games.batfish.BatfishGraphics;
import com.fiskmods.gameboii.graphics.GameboiiFont;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Slider extends AbstractButton
{
    private final String name;
    private final Supplier<Float> getter;
    private final Consumer<Float> setter;

    public Slider(Screen screen, Rectangle bounds, String name, Supplier<Float> get, Consumer<Float> set)
    {
        super(screen, bounds);
        this.name = name;
        getter = get;
        setter = set;
    }

    public static ButtonFactory factory(String name, Supplier<Float> get, Consumer<Float> set)
    {
        return (screen, bounds) -> new Slider(screen, bounds, name, get, set);
    }

    @Override
    public void keyInput(int keyCode)
    {
        float incr = InputKey.SHIFT.isPressed() ? 0.01F : 0.1F;
        float f = getter.get();

        if (f > 0 && (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A))
        {
            setter.accept(Math.max(f - incr, 0));
        }
        else if (f < 1 && (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D))
        {
            setter.accept(Math.min(f + incr, 1));
        }
    }

    @Override
    public void draw(Graphics2D g2d, boolean selected)
    {
        float f = getter.get();
        int w = 10, i = selected ? 1 : 0;
        int x2 = bounds.x + w + (int) ((bounds.width - w * 2) * f);

        Screen.drawImage(g2d, BatfishGraphics.buttons, bounds.x, bounds.y, bounds.width, bounds.height, 0, 40, 200, 60);
        Screen.drawImage(g2d, BatfishGraphics.buttons, x2 - w, bounds.y, w, bounds.height, 0, i * 20, w / 2, (i + 1) * 20);
        Screen.drawImage(g2d, BatfishGraphics.buttons, x2, bounds.y, w, bounds.height, 200 - w / 2, i * 20, 200, (i + 1) * 20);

        String text = String.format("%s: %d%%", name, Math.round(f * 100));
        g2d.setFont(GameboiiFont.BUTTON_TEXT);
        g2d.setColor(selected ? Color.YELLOW : Color.WHITE);
        g2d.drawString(text, bounds.x + bounds.width / 2 - screen.fontRenderer.getStringWidth(text) / 2, bounds.y + 28);
    }
}
