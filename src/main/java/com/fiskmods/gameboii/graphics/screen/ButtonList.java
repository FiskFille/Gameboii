package com.fiskmods.gameboii.graphics.screen;

import com.fiskmods.gameboii.games.batfish.BatfishSounds;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
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

    public ButtonBuilder builder()
    {
        return new ButtonBuilder();
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

    public class ButtonBuilder
    {
        private final List<ButtonFactory> buttons = new ArrayList<>();
        private ButtonLayout layout = ButtonLayout.IDENTITY;

        public ButtonBuilder add(ButtonFactory constructor)
        {
            buttons.add(constructor);
            return this;
        }

        public ButtonBuilder add(String text, Runnable onClick)
        {
            return add(Button.factory(text, onClick));
        }

        public ButtonBuilder addSlider(String name, Supplier<Float> get, Consumer<Float> set)
        {
            return add(Slider.factory(name, get, set));
        }

        public ButtonBuilder layout(ButtonLayout layout)
        {
            this.layout = layout;
            return this;
        }

        public void build(int x, int y, int width, int height)
        {
            Dimension dim = new Dimension(width, height);

            for (int i = 0; i < buttons.size(); ++i)
            {
                Point p = layout.transform(new Point(x, y), i, buttons.size() - 1);
                ButtonList.this.add(buttons.get(i).create(parent, new Rectangle(p, dim)));
            }
        }
    }
}
