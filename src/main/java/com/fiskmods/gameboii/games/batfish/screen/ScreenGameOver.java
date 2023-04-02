package com.fiskmods.gameboii.games.batfish.screen;

import com.fiskmods.gameboii.Engine;
import com.fiskmods.gameboii.GameboiiMath;
import com.fiskmods.gameboii.games.batfish.Batfish;
import com.fiskmods.gameboii.games.batfish.BatfishGraphics;
import com.fiskmods.gameboii.games.batfish.BatfishSounds;
import com.fiskmods.gameboii.games.batfish.level.BatfishPlayer.Skin;
import com.fiskmods.gameboii.graphics.Draw;
import com.fiskmods.gameboii.graphics.GameboiiFont;
import com.fiskmods.gameboii.graphics.screen.ButtonLayout;
import com.fiskmods.gameboii.graphics.screen.ConsoleButtonType;
import com.fiskmods.gameboii.graphics.screen.style.Centering;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import static java.awt.image.AffineTransformOp.TYPE_BILINEAR;
import static java.awt.image.AffineTransformOp.TYPE_NEAREST_NEIGHBOR;

public class ScreenGameOver extends BatfishScreen
{
    private final double altitude;
    private final int coinsCollected;

    private int ticks;

    public ScreenGameOver(double posY, int coins)
    {
        super(STYLE);
        altitude = posY / 9;
        coinsCollected = coins;
    }

    @Override
    public void initScreen()
    {
        Batfish.INSTANCE.stopTitleTheme();

        if (ticks >= 75)
        {
            buttonList.builder()
                    .button("Play Again", () ->
                    {
                        Engine.displayScreen(new ScreenIngame(false));
                        Batfish.INSTANCE.titleThemeTicks = 0;
                    })
                    .button("Quit to Title", () ->
                    {
                        Engine.displayScreen(null);
                        Batfish.INSTANCE.titleThemeTicks = 0;
                    })
                    .layout(ButtonLayout.spaced(0, 45))
                    .center(Centering.X)
                    .build(width / 2, 274, 400, 40);

            addConsoleButton(ConsoleButtonType.X, "Select", buttonList::press);
        }
        else
        {
            BatfishSounds.death.play(1, 1);
        }
    }

    @Override
    public void update()
    {
        if (++ticks == 75)
        {
            onOpenScreen();
        }
    }

    @Override
    public void draw(Graphics2D g2d)
    {
        g2d.setColor(new Color(0x510000));
        g2d.fillRect(0, 0, width, height);

        Skin skin = Batfish.INSTANCE.player.getSkin();
        BufferedImage image = skin.getResource().get();
        image = image.getSubimage(0, 0, 20, skin.height);

        double scale = 2 + Math.log(ticks);
        double rot = 40;

        if (ticks > 18)
        {
            double d = Math.log(ticks - 18);
            rot = 20 + d * 2;
            scale = 20 + d * 2;
        }
        else if (ticks > 10)
        {
            double d = Math.log(ticks - 10);
            rot = -40 - d * 2;
            scale = 12 + d * 2;
        }

        scale = Math.max(scale, 1);
        AffineTransformOp scaleOp = new AffineTransformOp(AffineTransform.getScaleInstance(scale, scale), TYPE_NEAREST_NEIGHBOR);
        AffineTransformOp rotateOp = new AffineTransformOp(AffineTransform.getRotateInstance(Math.toRadians(rot), image.getWidth() / 2F * scale, image.getHeight() / 2F * scale), TYPE_BILINEAR);
        image = rotateOp.filter(scaleOp.filter(image, null), null);

        if (ticks > 18)
        {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) GameboiiMath.clamp(1 - Math.log(ticks - 18) * 0.2, 0.2, 1)));
        }

        g2d.drawImage(image, (width - image.getWidth()) / 2, height / 5 * 3 - image.getHeight() / 2, image.getWidth(), image.getHeight(), null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

        if (ticks >= 75)
        {
            Draw.imageCentered(g2d, BatfishGraphics.game_over, width / 2, 100, 113 * 4, 20 * 4);

            g2d.setFont(GameboiiFont.BUTTON_TEXT);
            fontRenderer.drawString(String.format("You flew %.1f m", altitude), width / 2 - 200, 230, 0xFFFFFF);
            drawCoinCount(g2d, width / 2 - 184, 190, coinsCollected, false);
            super.draw(g2d);
        }
    }
}
