package com.fiskmods.gameboii;

import java.util.HashMap;
import java.util.Map;

import com.fiskmods.gameboii.graphics.IDisplayScreen;
import com.fiskmods.gameboii.graphics.Screen;

public class Engine
{
    private static GameboiiSystem system;
    private static IDisplayScreen displayScreen;

    private static IGame currentGame;
    private static Cartridge currentCartridge;

    private static Map<Cartridge, Map<Integer, Runnable>> triggers = new HashMap<>();

    public static void init(GameboiiSystem system, IDisplayScreen displayScreen)
    {
        Engine.system = system;
        Engine.displayScreen = displayScreen;

        for (Cartridge cartridge : Cartridge.values())
        {
            Engine.get(cartridge).register();
        }
    }

    public static GameboiiSystem system()
    {
        return system;
    }

    public static IDisplayScreen getDisplayScreen()
    {
        return displayScreen;
    }

    public static long getSystemTime()
    {
        return System.currentTimeMillis();
    }

    public static IGame get(Cartridge cartridge)
    {
        return cartridge != null ? (IGame) cartridge.supplier.get() : null;
    }

    private static boolean set(Cartridge cartridge)
    {
        if (currentCartridge != cartridge)
        {
            currentCartridge = cartridge;
            currentGame = get(cartridge);

            return true;
        }

        return false;
    }

    public static void start(Cartridge cartridge, int width, int height)
    {
        boolean load = set(cartridge);
        currentGame.init(width, height);

        if (load)
        {
            system.onLoad(cartridge);
        }
    }

    public static void save()
    {
        if (currentCartridge != null)
        {
            try
            {
                byte[] data = currentGame.writeSaveData();
                system.onSave(currentCartridge, data);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void exit()
    {
        if (currentGame != null)
        {
            save();
            currentGame.exit();
        }

        currentCartridge = null;
        currentGame = null;
    }

    public static int getWidth()
    {
        return currentGame != null ? currentGame.getWidth() : 0;
    }

    public static int getHeight()
    {
        return currentGame != null ? currentGame.getHeight() : 0;
    }

    public static Screen getScreen()
    {
        return currentGame != null ? currentGame.getScreen() : null;
    }

    public static void displayScreen(Screen screen)
    {
        if (currentCartridge != null)
        {
            currentGame.displayScreen(screen);
        }
    }

    public static void registerTrigger(Cartridge cartridge, int id, Runnable runnable)
    {
        triggers.computeIfAbsent(cartridge, t -> new HashMap<>()).put(id, runnable);
    }

    public static void trigger(Cartridge cartridge, int id)
    {
        Map<Integer, Runnable> map = triggers.get(cartridge);
        Runnable r;

        if (map != null && (r = map.get(id)) != null)
        {
            r.run();
        }
    }
}
