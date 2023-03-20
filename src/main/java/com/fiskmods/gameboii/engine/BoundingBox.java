package com.fiskmods.gameboii.engine;

public class BoundingBox
{
    public float minX;
    public float minY;
    public float maxX;
    public float maxY;

    public static BoundingBox getBoundingBox(float minX, float minY, float maxX, float maxY)
    {
        return new BoundingBox(minX, minY, maxX, maxY);
    }

    protected BoundingBox(float minX, float minY, float maxX, float maxY)
    {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public BoundingBox setBounds(float minX, float minY, float maxX, float maxY)
    {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        return this;
    }

    public BoundingBox addCoord(float x, float y)
    {
        float f0 = minX;
        float f1 = minY;
        float f2 = maxX;
        float f3 = maxY;

        if (x < 0)
        {
            f0 += x;
        }

        if (x > 0)
        {
            f2 += x;
        }

        if (y < 0)
        {
            f1 += y;
        }

        if (y > 0)
        {
            f3 += y;
        }

        return getBoundingBox(f0, f1, f2, f3);
    }

    public BoundingBox expand(float x, float y)
    {
        return getBoundingBox(minX - x, minY - y, maxX + x, maxY + y);
    }

    public BoundingBox merge(BoundingBox box)
    {
        float f0 = Math.min(minX, box.minX);
        float f1 = Math.min(minY, box.minY);
        float f2 = Math.max(maxX, box.maxX);
        float f3 = Math.max(maxY, box.maxY);

        return getBoundingBox(f0, f1, f2, f3);
    }

    public BoundingBox getOffsetBoundingBox(float x, float y)
    {
        return getBoundingBox(minX + x, minY + y, maxX + x, maxY + y);
    }

    public boolean intersectsWith(BoundingBox box)
    {
        return box.maxX > minX && box.minX < maxX && box.maxY > minY && box.minY < maxY;
    }

    public BoundingBox offset(float x, float y)
    {
        minX += x;
        minY += y;
        maxX += x;
        maxY += y;
        return this;
    }

    public boolean isPointInside(Point2f p)
    {
        return p.xCoord > minX && p.xCoord < maxX && p.yCoord > minY && p.yCoord < maxY;
    }

    public float getAverageEdgeLength()
    {
        float f0 = maxX - minX;
        float f1 = maxY - minY;
        return (f0 + f1) / 2;
    }

    public BoundingBox contract(float x, float y)
    {
        return getBoundingBox(minX + x, minY + y, maxY - y, maxX - x);
    }

    public BoundingBox copy()
    {
        return getBoundingBox(minX, minY, maxX, maxY);
    }

    public void setBB(BoundingBox box)
    {
        minX = box.minX;
        minY = box.minY;
        maxX = box.maxX;
        maxY = box.maxY;
    }

    public float calculateXOffset(BoundingBox box, float f0)
    {
        if (box.maxY > minY && box.minY < maxY)
        {
            float f1;

            if (f0 > 0 && box.maxX <= minX)
            {
                f1 = minX - box.maxX;

                if (f1 < f0)
                {
                    f0 = f1;
                }
            }

            if (f0 < 0 && box.minX >= maxX)
            {
                f1 = maxX - box.minX;

                if (f1 > f0)
                {
                    f0 = f1;
                }
            }

            return f0;
        }
        else
        {
            return f0;
        }
    }

    public float calculateYOffset(BoundingBox box, float f0)
    {
        if (box.maxX > minX && box.minX < maxX)
        {
            float f1;

            if (f0 > 0 && box.maxY <= minY)
            {
                f1 = minY - box.maxY;

                if (f1 < f0)
                {
                    f0 = f1;
                }
            }

            if (f0 < 0 && box.minY >= maxY)
            {
                f1 = maxY - box.minY;

                if (f1 > f0)
                {
                    f0 = f1;
                }
            }

            return f0;
        }
        else
        {
            return f0;
        }
    }

    public Point2f calculateIntercept(Point2f a, Point2f b)
    {
        Point2f x0 = a.getIntermediateWithXValue(b, minX);
        Point2f x1 = a.getIntermediateWithXValue(b, maxX);
        Point2f y0 = a.getIntermediateWithYValue(b, minY);
        Point2f y1 = a.getIntermediateWithYValue(b, maxY);

        if (!isPointInY(x0))
        {
            x0 = null;
        }

        if (!isPointInY(x1))
        {
            x1 = null;
        }

        if (!isPointInX(y0))
        {
            y0 = null;
        }

        if (!isPointInX(y1))
        {
            y1 = null;
        }

        Point2f result = null;

        if (x0 != null && (result == null || a.squareDistanceTo(x0) < a.squareDistanceTo(result)))
        {
            result = x0;
        }

        if (x1 != null && (result == null || a.squareDistanceTo(x1) < a.squareDistanceTo(result)))
        {
            result = x1;
        }

        if (y0 != null && (result == null || a.squareDistanceTo(y0) < a.squareDistanceTo(result)))
        {
            result = y0;
        }

        if (y1 != null && (result == null || a.squareDistanceTo(y1) < a.squareDistanceTo(result)))
        {
            result = y1;
        }

        return result;
    }

    private boolean isPointInY(Point2f p)
    {
        return p != null && p.yCoord >= minY && p.yCoord <= maxY;
    }

    private boolean isPointInX(Point2f p)
    {
        return p != null && p.xCoord >= minX && p.xCoord <= maxX;
    }

    public Point2f center()
    {
        return new Point2f((minX + maxX) / 2, (minY + maxY) / 2);
    }

    @Override
    public String toString()
    {
        return "box[" + minX + ", " + minY + " -> " + maxX + ", " + maxY + "]";
    }
}
