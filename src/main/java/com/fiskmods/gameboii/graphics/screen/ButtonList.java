package com.fiskmods.gameboii.graphics.screen;

import com.fiskmods.gameboii.games.batfish.BatfishSounds;
import com.fiskmods.gameboii.graphics.screen.style.ButtonStyle;
import com.fiskmods.gameboii.graphics.screen.style.Centering;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ButtonList
{
    private final List<AbstractButton> list = new ArrayList<>();
    private final Screen parent;

    private int selectedId;

    public ButtonList(Screen parent)
    {
        this.parent = parent;
    }

    public void clear()
    {
        list.clear();
        selectedId = 0;
    }

    public void add(AbstractButton button)
    {
        button.id = list.size();
        list.add(button);
    }

    public Builder builder()
    {
        return new Builder();
    }

    public int getSelectedId()
    {
        return selectedId;
    }

    public void cycle(int offset)
    {
        selectedId += offset;
        int size = list.size();

        while (selectedId < 0)
        {
            selectedId += size;
        }

        while (selectedId >= size)
        {
            selectedId -= size;
        }
    }

    public void press()
    {
        if (list.size() > 0)
        {
            list.get(selectedId).onPressed();
        }

        BatfishSounds.click.play(1, 1);
    }

    public void keyTyped(int keyCode)
    {
        if (!list.isEmpty())
        {
            if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S)
            {
                cycle(1);
                return;
            }
            else if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W)
            {
                cycle(-1);
                return;
            }
            else if (keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_SPACE)
            {
                press();
                return;
            }

            if (selectedId >= 0 && selectedId < list.size())
            {
                list.get(selectedId).keyInput(keyCode);
            }
        }
    }

    public void draw(Graphics2D g2d)
    {
        list.forEach(t -> t.draw(g2d, t.id == selectedId));
    }

    public class Builder
    {
        private final List<ButtonFactory> buttons = new ArrayList<>();

        private ButtonLayout layout = ButtonLayout.IDENTITY;
        private Centering centering = Centering.NONE;

        public Builder button(ButtonFactory factory)
        {
            buttons.add(factory);
            return this;
        }

        public <T extends AbstractButton> Builder add(Class<T> type, Function<ButtonStyle<T>, ButtonFactory> factoryFunction)
        {
            buttons.add(factoryFunction.apply(parent.style.getStyle(type)));
            return this;
        }

        public Builder button(String text, Runnable onPressed)
        {
            return add(Button.class, Button.factory(text, onPressed));
        }

        public Builder slider(String name, Supplier<Float> get, Consumer<Float> set)
        {
            return add(Slider.class, Slider.factory(name, get, set));
        }

        public Builder layout(ButtonLayout layout)
        {
            this.layout = layout;
            return this;
        }

        public Builder center(Centering centering)
        {
            this.centering = centering;
            return this;
        }

        public void build(int x, int y, int width, int height)
        {
            Dimension dim = new Dimension(width, height);
            Point root = centering.apply(new Point(x, y), dim);

            for (int i = 0; i < buttons.size(); ++i)
            {
                Point p = layout.transform(root, i, buttons.size() - 1);
                ButtonList.this.add(buttons.get(i).create(parent, new Rectangle(p, dim)));
            }
        }
    }
}
