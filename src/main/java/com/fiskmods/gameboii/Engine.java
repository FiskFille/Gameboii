package com.fiskmods.gameboii;

import com.fiskmods.gameboii.graphics.IDisplayScreen;
import com.fiskmods.gameboii.graphics.screen.Screen;

public class Engine
{
    private static GameboiiSystem system;
    private static IDisplayScreen displayScreen;

    private static IGame currentGame;
    private static Cartridge currentCartridge;

    public static void init(GameboiiSystem system, IDisplayScreen displayScreen)
    {
        Engine.system = system;
        Engine.displayScreen = displayScreen;

        for (Cartridge cartridge : Cartridge.values())
        {
            cartridge.init();
        }
    }

    public static void reloadResources()
    {
        for (Cartridge cartridge : Cartridge.values())
        {
            cartridge.reloadResources();
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
        return cartridge != null ? cartridge.get() : null;
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
}
