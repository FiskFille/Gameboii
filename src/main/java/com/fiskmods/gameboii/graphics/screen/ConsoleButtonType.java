package com.fiskmods.gameboii.graphics.screen;

import java.awt.event.KeyEvent;

public enum ConsoleButtonType
{
    Z(KeyEvent.VK_Z),
    X(KeyEvent.VK_X),
    C(KeyEvent.VK_C),
    V(KeyEvent.VK_V);

    public final int keyCode;

    ConsoleButtonType(int key)
    {
        keyCode = key;
    }
}
