package com.fiskmods.gameboii;

import java.util.Random;

public class GameboiiMath
{
    public static double interpolate(double a, double b, double progress)
    {
        return a + (b - a) * progress;
    }

    public static float interpolate(float a, float b, float progress)
    {
        return (float) interpolate(a, b, (double) progress);
    }

    public static double interpolate(double curr, double prev)
    {
        return interpolate(prev, curr, Engine.system().partialTicks());
    }

    public static float interpolate(float curr, float prev)
    {
        return interpolate(prev, curr, Engine.system().partialTicks());
    }

    public static int floor(float f)
    {
        int i = (int) f;
        return f < i ? i - 1 : i;
    }

    public static int floor(double d)
    {
        int i = (int) d;
        return d < i ? i - 1 : i;
    }

    public static int ceil(float f)
    {
        int i = (int) f;
        return f > i ? i + 1 : i;
    }

    public static int ceil(double d)
    {
        int i = (int) d;
        return d > i ? i + 1 : i;
    }

    public static int clamp(int value, int min, int max)
    {
        return value < min ? min : (value > max ? max : value);
    }

    public static float clamp(float value, float min, float max)
    {
        return value < min ? min : (value > max ? max : value);
    }

    public static double clamp(double value, double min, double max)
    {
        return value < min ? min : (value > max ? max : value);
    }
    
    public static int getRandomIntegerInRange(Random rand, int min, int max)
    {
        return min >= max ? min : rand.nextInt(max - min + 1) + min;
    }
}
