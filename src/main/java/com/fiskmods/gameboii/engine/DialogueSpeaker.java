package com.fiskmods.gameboii.engine;

import java.util.function.Supplier;

import com.fiskmods.gameboii.resource.ImageResource;

public class DialogueSpeaker
{
    public final String name;
    public final Supplier<ImageResource> resource;

    public DialogueSpeaker(String name, Supplier<ImageResource> resource)
    {
        this.name = name;
        this.resource = resource;
    }

    public DialogueSpeaker(String name, ImageResource resource)
    {
        this(name, () -> resource);
    }
}
