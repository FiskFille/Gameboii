package com.fiskmods.gameboii.batfish;

import java.nio.ByteBuffer;

import com.fiskmods.gameboii.Abstract2DGame;
import com.fiskmods.gameboii.Engine;
import com.fiskmods.gameboii.batfish.level.BatfishPlayer;
import com.fiskmods.gameboii.batfish.screen.ScreenLoading;
import com.fiskmods.gameboii.batfish.screen.ScreenMainMenu;
import com.fiskmods.gameboii.graphics.Screen;
import com.fiskmods.gameboii.sound.ISoundInstance;

public class Batfish extends Abstract2DGame
{
    public static final Batfish INSTANCE = new Batfish();

    public static final double DIFFICULTY_SPEED = 1000;
    public static final double SPACE_ALTITUDE = 8000;

    public static final int TRIGGER_COMPLETE = 0;
    public static final int TRIGGER_SPODERMEN = 1;

    public BatfishPlayer player;

    public int titleThemeTicks = -1;
    public int worldPowerup;

    private boolean launched;
    
    public static ISoundInstance titleTheme;

    public Batfish()
    {
        super(1024, 1);
    }

    @Override
    public void register()
    {
        BatfishGraphics.INSTANCE.reload(Engine.system());
        BatfishSounds.INSTANCE.reload(Engine.system());
    }

    @Override
    public void read(ByteBuffer buf, int protocol)
    {
        player.read(buf, protocol);
    }

    @Override
    public void write(ByteBuffer buf)
    {
        player.write(buf);
    }

    @Override
    public void preInit(int width, int height)
    {
        if (player == null)
        {
            player = new BatfishPlayer();
        }
    }

    @Override
    public void postInit(int width, int height)
    {
    }

    @Override
    public void tick()
    {
        super.tick();

        if (titleThemeTicks > 0)
        {
            --titleThemeTicks;
        }
        else if (titleThemeTicks == 0)
        {
            if (titleTheme != null)
            {
                titleTheme.stop();
            }
            
            titleTheme = BatfishSounds.title.play(1, 1);
            titleThemeTicks = 1160;
        }

        if (worldPowerup > 0)
        {
            --worldPowerup;
        }
    }

    @Override
    public void exit()
    {
        super.exit();
        player = null;
        launched = false;
        titleThemeTicks = -1;
        worldPowerup = 0;
    }

    @Override
    public Screen displayMenuScreen()
    {
        if (!launched)
        {
            launched = true;
            return new ScreenLoading();
        }

        return new ScreenMainMenu();
    }
    
    public void stopTitleTheme()
    {
        titleThemeTicks = -1;
        
        if (titleTheme != null)
        {
            titleTheme.stop();
            titleTheme = null;
        }
    }
}
