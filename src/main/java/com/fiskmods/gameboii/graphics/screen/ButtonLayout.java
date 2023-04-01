package com.fiskmods.gameboii.graphics.screen;

import java.awt.Point;

@FunctionalInterface
public interface ButtonLayout
{
    ButtonLayout IDENTITY = (p, i, max) -> p;

    Point transform(Point point, int index, int maxIndex);

    default ButtonLayout andThen(ButtonLayout layout)
    {
        return (p, i, max) -> layout.transform(transform(p, i, max), i, max);
    }

    static ButtonLayout spaced(int x, int y)
    {
        return (p, i, max) -> new Point(p.x + x * i, p.y + y * i);
    }

    static ButtonLayout offsetLast(int x, int y)
    {
        return (p, i, max) -> i == max ? new Point(p.x + x, p.y + y) : p;
    }
}
