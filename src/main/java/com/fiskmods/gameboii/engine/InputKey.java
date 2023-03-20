package com.fiskmods.gameboii.engine;

import static java.awt.event.KeyEvent.*;

import java.util.ArrayList;
import java.util.List;

public class InputKey
{
    public static final List<InputKey> KEYS = new ArrayList<>();
    
    public static final InputKey UP = new InputKey(VK_UP, VK_W);
    public static final InputKey DOWN = new InputKey(VK_DOWN, VK_S);
    public static final InputKey LEFT = new InputKey(VK_LEFT, VK_A);
    public static final InputKey RIGHT = new InputKey(VK_RIGHT, VK_D);
    public static final InputKey SHIFT = new InputKey(VK_SHIFT);
    public static final InputKey Z = new InputKey(VK_Z);
    public static final InputKey X = new InputKey(VK_X);
    public static final InputKey C = new InputKey(VK_C);
    public static final InputKey V = new InputKey(VK_V);

    public final int[] keys;

    private boolean pressed;

    public InputKey(int... keyCodes)
    {
        keys = keyCodes;
        KEYS.add(this);
    }

    public boolean isPressed()
    {
        return pressed;
    }

    public void setPressed(boolean pressed)
    {
        this.pressed = pressed;
    }
}
