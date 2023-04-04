package com.fiskmods.gameboii;

import com.fiskmods.gameboii.resource.GameResourceLoader;
import com.fiskmods.gameboii.resource.IResourceListener;

import java.util.*;
import java.util.function.Supplier;

public final class Cartridge
{
    private static final Map<String, Cartridge> REGISTRY = new HashMap<>();

    public final String id;
    private final Supplier<?> supplier;

    final List<IResourceListener> listeners = new ArrayList<>();
    final Map<Integer, Runnable> triggers = new HashMap<>();

    public Cartridge(String id, Supplier<?> supplier)
    {
        this.id = id;
        this.supplier = supplier;
    }

    void init()
    {
        IGame game = get();
        game.register(listeners::add);
    }

    public void reloadResources()
    {
        GameResourceLoader loader = new GameResourceLoader(this, Engine.system());
        listeners.forEach(t -> t.load(loader));
    }

    public IGame get()
    {
        return (IGame) supplier.get();
    }

    public void registerTrigger(int id, Runnable trigger)
    {
        triggers.put(id, trigger);
    }

    public void trigger(int id)
    {
        Runnable trigger = triggers.get(id);

        if (trigger != null)
        {
            trigger.run();
        }
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
