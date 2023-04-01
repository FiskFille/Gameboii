package com.fiskmods.gameboii.graphics.screen;

import java.util.function.Supplier;

public class ConsoleButton
{
    public final ConsoleButtonType type;
    public final Supplier<String> name;
    public final Runnable runnable;

    public ConsoleButton(ConsoleButtonType type, Supplier<String> name, Runnable runnable)
    {
        this.type = type;
        this.name = name;
        this.runnable = runnable;
    }
}
