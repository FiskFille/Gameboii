package com.fiskmods.gameboii.graphics.screen;

import com.fiskmods.gameboii.engine.InputKey;
import com.fiskmods.gameboii.graphics.screen.style.ButtonStyle;
import com.fiskmods.gameboii.graphics.screen.style.TextProvider;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Slider extends AbstractButton implements TextProvider
{
    private final ButtonStyle<Slider> style;

    private final String name;
    private final Supplier<Float> getter;
    private final Consumer<Float> setter;

    public Slider(Screen screen, Rectangle bounds, ButtonStyle<Slider> style, String name, Supplier<Float> get, Consumer<Float> set)
    {
        super(screen, bounds);
        this.style = style;
        this.name = name;
        getter = get;
        setter = set;
    }

    public static ButtonFactory factory(ButtonStyle<Slider> style, String name, Supplier<Float> get, Consumer<Float> set)
    {
        return (screen, bounds) -> new Slider(screen, bounds, style, name, get, set);
    }

    public static Function<ButtonStyle<Slider>, ButtonFactory> factory(String name, Supplier<Float> get, Consumer<Float> set)
    {
        return style -> (screen, bounds) -> new Slider(screen, bounds, style, name, get, set);
    }

    @Override
    public String getText()
    {
        return String.format("%s: %d%%", name, Math.round(getValue() * 100));
    }

    public float getValue()
    {
        return getter.get();
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
        style.draw(g2d, screen, this, selected);
    }
}
