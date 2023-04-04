package com.fiskmods.gameboii.graphics.screen.style;

import com.fiskmods.gameboii.graphics.Draw;
import com.fiskmods.gameboii.resource.ImageResource;
import com.fiskmods.gameboii.graphics.screen.AbstractButton;
import com.fiskmods.gameboii.graphics.screen.Screen;
import com.fiskmods.gameboii.graphics.screen.Slider;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
public interface ButtonStyle<T extends AbstractButton>
{
    void draw(Graphics2D g2d, Screen screen, T button, boolean selected);

    default ButtonStyle<T> andThen(ButtonStyle<T> style)
    {
        return (g2d, screen, button, selected) ->
        {
            draw(g2d, screen, button, selected);
            style.draw(g2d, screen, button, selected);
        };
    }

    static <T extends AbstractButton> ButtonStyle<T> flat(Function<Boolean, Color> color)
    {
        return (g2d, screen, button, selected) ->
        {
            Rectangle b = button.bounds;
            g2d.setColor(color.apply(selected));
            g2d.fillRect(b.x, b.y, b.width, b.height);
        };
    }

    static <T extends AbstractButton> ButtonStyle<T> flat(Color color)
    {
        return flat(selected -> color);
    }

    static <T extends AbstractButton> ButtonStyle<T> uv(Supplier<ImageResource> texture, int u, int v, int texW, int texH)
    {
        return (g2d, screen, button, selected) ->
        {
            Rectangle b = button.bounds;
            Draw.image(g2d, texture.get(), b.x, b.y, b.width, b.height, u, v, u + texW, v + texH);
        };
    }

    static <T extends AbstractButton> ButtonStyle<T> bg(Supplier<ImageResource> texture, int u, int v, int texW, int texH)
    {
        return (g2d, screen, button, selected) ->
        {
            int i = selected ? 1 : 0;
            Rectangle b = button.bounds;
            Draw.image(g2d, texture.get(), b.x, b.y, b.width, b.height,
                    u, v + i * texH, u + texW, v + (i + 1) * texH);
        };
    }

    static <T extends AbstractButton & TextProvider> ButtonStyle<T> text(Font font, Function<Boolean, Color> color, int yOffset)
    {
        return (g2d, screen, button, selected) ->
        {
            Rectangle b = button.bounds;
            g2d.setFont(font);
            g2d.setColor(color.apply(selected));
            g2d.drawString(button.getText(), b.x + b.width / 2 - screen.fontRenderer.getStringWidth(button.getText()) / 2, b.y + yOffset);
        };
    }

    static <T extends Slider> ButtonStyle<T> sliderPeg(Supplier<ImageResource> texture, int width, int u, int v, int texW, int texH)
    {
        return (g2d, screen, button, selected) ->
        {
            Rectangle b = button.bounds;
            ImageResource tex = texture.get();
            int i = selected ? 1 : 0;
            int x2 = b.x + width + (int) ((b.width - width * 2) * button.getValue());
            Draw.image(g2d, tex, x2 - width, b.y, width, b.height, u, v + i * texH, u + width / 2, v + (i + 1) * texH);
            Draw.image(g2d, tex, x2, b.y, width, b.height, u + texW - width / 2, v + i * texH, u + texW, v + (i + 1) * texH);
        };
    }
}
