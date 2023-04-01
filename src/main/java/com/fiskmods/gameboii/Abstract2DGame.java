package com.fiskmods.gameboii;

import com.fiskmods.gameboii.graphics.GameboiiFont;
import com.fiskmods.gameboii.graphics.screen.Screen;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public abstract class Abstract2DGame implements IGame, ISaveObject
{
    protected BufferedImage canvas;
    protected Graphics2D graphics;

    public static GameboiiFont fontRenderer;
    public Screen currentScreen;

    public final int saveAllocation;
    public final int saveVersion;

    public Abstract2DGame(int allocation, int version)
    {
        saveAllocation = allocation;
        saveVersion = version;
    }

    @Override
    public void init(int width, int height)
    {
        //        TaskTimer.start("Bootup");
        //        TaskTimer.start("Pre-init");
        preInit(width, height);
        //        TaskTimer.stop("Pre-init");
        //        TaskTimer.start("Init screen");

        if (canvas == null || canvas.getWidth() != width || canvas.getHeight() != height)
        {
            canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            graphics = (Graphics2D) canvas.getGraphics();

            fontRenderer = new GameboiiFont(graphics);
            Engine.getDisplayScreen().init(canvas, width, height);

            if (currentScreen != null)
            {
                currentScreen.onOpenScreen();
            }
        }

        //        TaskTimer.stop("Init screen");
        //        TaskTimer.start("Post-init");
        //        TaskTimer.start("Post-init screen");

        if (currentScreen == null)
        {
            displayScreen(null);
        }

        //        TaskTimer.stop("Post-init screen");
        postInit(width, height);
        //        TaskTimer.stop("Post-init");
        //        TaskTimer.start("Calibrate screen");
        draw(1);
        //        TaskTimer.stop("Calibrate screen");
        //        TaskTimer.stop("Bootup");
    }

    public abstract void preInit(int width, int height);

    public abstract void postInit(int width, int height);

    @Override
    public void readSaveData(byte[] data)
    {
        ByteBuffer buf = ByteBuffer.wrap(data);
        int protocol = buf.get() & 0xFF;

        Engine.system().read(buf, protocol);
        read(buf, protocol);
    }

    @Override
    public byte[] writeSaveData()
    {
        ByteBuffer buf = ByteBuffer.allocate(saveAllocation);
        buf.put((byte) saveVersion);

        Engine.system().write(buf);
        write(buf);

        byte[] data = new byte[buf.position()];
        ((ByteBuffer) buf.rewind()).get(data);
        return data;
    }

    @Override
    public void keyTyped(char character, int keyCode)
    {
        if (currentScreen != null)
        {
            currentScreen.keyTyped(character, keyCode);
        }
    }

    @Override
    public void draw(float partialTicks)
    {
        if (canvas != null)
        {
            if (currentScreen != null)
            {
                try
                {
                    currentScreen.draw(graphics);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            Engine.getDisplayScreen().draw(canvas);
        }
    }

    @Override
    public void tick()
    {
        if (currentScreen != null)
        {
            currentScreen.update();
        }
    }

    @Override
    public void exit()
    {
        canvas = null;
        graphics = null;
        Engine.getDisplayScreen().clear();

        fontRenderer = null;
        currentScreen = null;
    }

    @Override
    public int getWidth()
    {
        return canvas != null ? canvas.getWidth() : 0;
    }

    @Override
    public int getHeight()
    {
        return canvas != null ? canvas.getHeight() : 0;
    }

    @Override
    public Screen getScreen()
    {
        return currentScreen;
    }

    @Override
    public void displayScreen(Screen screen)
    {
        if (screen == null)
        {
            screen = displayMenuScreen();
        }

        currentScreen = screen;
        screen.onOpenScreen();
    }

    public abstract Screen displayMenuScreen();
}
