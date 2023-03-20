package com.fiskmods.gameboii.engine;

import java.util.function.Supplier;

import com.fiskmods.gameboii.graphics.Resource;

public class DialogueSpeaker
{
    public final String name;
    public final Supplier<Resource> resource;

    public DialogueSpeaker(String name, Supplier<Resource> resource)
    {
        this.name = name;
        this.resource = resource;
    }

    public DialogueSpeaker(String name, Resource resource)
    {
        this(name, () -> resource);
    }
}
