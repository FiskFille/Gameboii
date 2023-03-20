package com.fiskmods.gameboii.engine;

import java.awt.Point;
import java.util.Objects;

public class Point2f
{
    public float xCoord, yCoord;

    public Point2f(float x, float y)
    {
        xCoord = x;
        yCoord = y;
    }

    public Point2f()
    {
        this(0, 0);
    }

    public Point2f(Point p)
    {
        this(p.x, p.y);
    }

    public Point2f normalize()
    {
        float l = length();
        return l < 1.0E-4F ? new Point2f() : new Point2f(xCoord / l, yCoord / l);
    }

    public double dotProduct(Point2f p)
    {
        return xCoord * p.xCoord + yCoord * p.yCoord;
    }

    public Point2f add(float x, float y)
    {
        return new Point2f(xCoord + x, yCoord + y);
    }

    public float squareDistanceTo(float x, float y)
    {
        x -= xCoord;
        y -= yCoord;
        return x * x + y * y;
    }

    public float squareDistanceTo(Point2f p)
    {
        return squareDistanceTo(p.xCoord, p.yCoord);
    }

    public float distanceTo(Point2f p)
    {
        return (float) Math.sqrt(squareDistanceTo(p));
    }

    public float length()
    {
        return (float) Math.sqrt(xCoord * xCoord + yCoord * yCoord);
    }

    public Point2f getIntermediateWithXValue(Point2f p, float x)
    {
        float dx = p.xCoord - xCoord;

        if (dx * dx < 1E-7F)
        {
            return null;
        }

        float f = (x - xCoord) / dx;
        float dy = p.yCoord - yCoord;
        return f >= 0 && f <= 1 ? new Point2f(xCoord + dx * f, yCoord + dy * f) : null;
    }

    public Point2f getIntermediateWithYValue(Point2f p, float y)
    {
        float dy = p.yCoord - yCoord;

        if (dy * dy < 1E-7F)
        {
            return null;
        }

        float f = (y - yCoord) / dy;
        float dx = p.xCoord - xCoord;
        return f >= 0 && f <= 1 ? new Point2f(xCoord + dx * f, yCoord + dy * f) : null;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(xCoord, yCoord);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        else if (!(obj instanceof Point2f))
        {
            return false;
        }

        Point2f other = (Point2f) obj;
        return Float.floatToIntBits(xCoord) == Float.floatToIntBits(other.xCoord) && Float.floatToIntBits(yCoord) == Float.floatToIntBits(other.yCoord);
    }

    @Override
    public String toString()
    {
        return "(" + xCoord + ", " + yCoord + ")";
    }
}
