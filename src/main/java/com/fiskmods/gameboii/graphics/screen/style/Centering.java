package com.fiskmods.gameboii.graphics.screen.style;

import java.awt.Dimension;
import java.awt.Point;
import java.util.function.BiFunction;

public enum Centering
{
    NONE((p, dim) -> p),
    X((p, dim) -> new Point(p.x - dim.width / 2, p.y)),
    Y((p, dim) -> new Point(p.x, p.y - dim.height / 2)),
    XY((p, dim) -> new Point(p.x - dim.width / 2, p.y - dim.height / 2));

    private final BiFunction<Point, Dimension, Point> function;

    Centering(BiFunction<Point, Dimension, Point> function)
    {
        this.function = function;
    }

    public Point apply(Point point, Dimension dimension)
    {
        return function.apply(point, dimension);
    }

    public Point apply(Point point, int width, int height)
    {
        return apply(point, new Dimension(width, height));
    }
}
