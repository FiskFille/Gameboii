package com.fiskmods.gameboii;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public final class Cartridge
{
    private static final Map<String, Cartridge> REGISTRY = new HashMap<>();

    public final String id;
    final Supplier<?> supplier;

    public Cartridge(String id, Supplier<?> supplier)
    {
        this.id = id;
        this.supplier = supplier;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        else if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        Cartridge cartridge = (Cartridge) o;
        return id.equals(cartridge.id);
    }

    @Override
    public String toString()
    {
        return "Cartridge[" + id + "]";
    }

    public static void register(Cartridge cartridge)
    {
        if (REGISTRY.containsKey(cartridge.id))
        {
            throw new IllegalArgumentException("Gameboii game '" + cartridge.id + "' has already been registered!");
        }

        REGISTRY.put(cartridge.id, cartridge);
    }

    public static Collection<Cartridge> values()
    {
        return REGISTRY.values();
    }

    public static Cartridge get(String key)
    {
        return REGISTRY.get(key);
    }
}
