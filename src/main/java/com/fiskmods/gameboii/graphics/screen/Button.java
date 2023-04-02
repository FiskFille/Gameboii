package com.fiskmods.gameboii.graphics.screen;

import com.fiskmods.gameboii.graphics.screen.style.ButtonStyle;
import com.fiskmods.gameboii.graphics.screen.style.TextProvider;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.function.Function;

public class Button extends AbstractButton implements TextProvider
{
    private final ButtonStyle<Button> style;

    private final String text;
    private final Runnable onPressed;

    public Button(Screen screen, Rectangle bounds, ButtonStyle<Button> style, String text, Runnable onPressed)
    {
        super(screen, bounds);
        this.text = text;
        this.style = style;
        this.onPressed = onPressed;
    }

    public static ButtonFactory factory(ButtonStyle<Button> style, String text, Runnable onPressed)
    {
        return (screen, size) -> new Button(screen, size, style, text, onPressed);
    }

    public static Function<ButtonStyle<Button>, ButtonFactory> factory(String text, Runnable onPressed)
    {
        return style -> (screen, size) -> new Button(screen, size, style, text, onPressed);
    }

    @Override
    public String getText()
    {
        return text;
    }

    @Override
    public void onPressed()
    {
        onPressed.run();
    }

    @Override
    public void draw(Graphics2D g2d, boolean selected)
    {
        style.draw(g2d, screen, this, selected);
    }
}
