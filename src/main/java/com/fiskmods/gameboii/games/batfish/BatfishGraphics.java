package com.fiskmods.gameboii.games.batfish;

import com.fiskmods.gameboii.games.batfish.level.BatfishPlayer.Skin;
import com.fiskmods.gameboii.games.batfish.level.PowerupObject;
import com.fiskmods.gameboii.resource.ImageResource;
import com.fiskmods.gameboii.resource.GameResourceLoader;
import com.fiskmods.gameboii.resource.IResourceListener;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Locale;

public enum BatfishGraphics implements IResourceListener
{
    INSTANCE;

    public static ImageResource logo;
    public static ImageResource game_over;

    public static ImageResource buttons;
    public static ImageResource stone;
    public static ImageResource coin;
    public static ImageResource console_buttons;
    public static ImageResource support_beam;
    public static ImageResource builder;
    public static ImageResource powerups;
    public static ImageResource robo_spodermen;
    public static ImageResource spodermen_mask;
    public static ImageResource old_spice;
    public static ImageResource warning_tape;

    public static ImageResource[] player;
    public static ImageResource[] floors;

    public static int[] sky_gradient;

    public static final Font BUTTON_TEXT = new Font("Calibri", Font.PLAIN, 26);
    public static final Font SHOP_TITLE = new Font("Maiandra GD", Font.PLAIN, 50);
    public static final Font DEFAULT = new Font("Arial Black", Font.BOLD, 30);
    public static final Font GAME_OVER = new Font("Arial Black", Font.PLAIN, 50);

    @Override
    public void load(GameResourceLoader loader)
    {
        logo = loader.loadGIF("textures/logo.png", 109, 21, 2);
        game_over = loader.load("textures/game_over.png", 113, 20);

        buttons = loader.load("textures/button.png", 200, 60);
        stone = loader.load("textures/stone.png", 16, 16);
        coin = loader.loadGIF("textures/coin.png", 8, 8, 4);
        console_buttons = loader.load("textures/console_buttons.png", 52, 52);
        support_beam = loader.load("textures/support_beam.png", 10, 16);
        builder = loader.load("textures/builder.png", 32, 32);
        powerups = loader.load("textures/powerups.png", 8, 8 * PowerupObject.Type.values().length);
        robo_spodermen = loader.load("textures/player/robo_spodermen.png", 4 * 20, 2 * 20);
        spodermen_mask = loader.load("textures/spodermen_mask.png", 3, 4);
        old_spice = loader.load("textures/old_spice.png", 18, 28);
        warning_tape = loader.load("textures/warning_tape.png", 26, 16);

        floors = new ImageResource[] {loader.load("textures/floor.png", 26, 18), loader.load("textures/floor_wood.png", 26, 18)};
        player = new ImageResource[Skin.values().length];

        for (Skin skin : Skin.values())
        {
            player[skin.ordinal()] = skin.resource.apply(loader, "textures/player/" + skin.name().toLowerCase(Locale.ROOT) + ".png");
        }

        try
        {
            BufferedImage image = loader.loadImage("textures/sky_gradient.png");
            sky_gradient = new int[] {0x7AADFF};

            if (image != null)
            {
                sky_gradient = new int[image.getWidth()];

                for (int i = 0; i < sky_gradient.length; ++i)
                {
                    sky_gradient[i] = image.getRGB(i, 0);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
