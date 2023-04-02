package com.fiskmods.gameboii.graphics.screen.style;

import com.fiskmods.gameboii.graphics.screen.AbstractButton;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ScreenStyle
{
    private final ButtonStyle<AbstractButton> defaultStyle;
    private final Map<Class<?>, ButtonStyle<?>> buttonStyles;

    private final ConsoleButtonStyle consoleButtonStyle;

    private ScreenStyle(ButtonStyle<AbstractButton> defaultStyle, Map<Class<?>, ButtonStyle<?>> buttonStyles, ConsoleButtonStyle consoleButtonStyle)
    {
        this.defaultStyle = defaultStyle;
        this.buttonStyles = buttonStyles;
        this.consoleButtonStyle = consoleButtonStyle;
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractButton> ButtonStyle<T> getStyle(Class<T> type)
    {
        ButtonStyle<?> style = buttonStyles.get(type);

        if (style != null)
        {
            return (ButtonStyle<T>) style;
        }
        else if (type.getSuperclass() == AbstractButton.class)
        {
            buttonStyles.put(type, defaultStyle);
            return (ButtonStyle<T>) defaultStyle;
        }

        return getStyle((Class<T>) type.getSuperclass());
    }

    public ConsoleButtonStyle getConsoleStyle()
    {
        return consoleButtonStyle;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static class Builder
    {
        private ButtonStyle<AbstractButton> defaultButtonStyle = ButtonStyle.flat(selected -> selected ? Color.WHITE : Color.LIGHT_GRAY);
        private final Map<Class<?>, ButtonStyle<?>> buttonStyles = new HashMap<>();

        private ConsoleButtonStyle consoleButtonStyle;

        public Builder defaultButton(ButtonStyle<AbstractButton> style)
        {
            defaultButtonStyle = style;
            return this;
        }

        public <T extends AbstractButton> Builder button(Class<T> type, ButtonStyle<T> style)
        {
            buttonStyles.put(type, style);
            return this;
        }

        public Builder consoleButtons(ConsoleButtonStyle style)
        {
            consoleButtonStyle = style;
            return this;
        }

        public ScreenStyle build()
        {
            return new ScreenStyle(defaultButtonStyle, buttonStyles, consoleButtonStyle);
        }
    }
}
