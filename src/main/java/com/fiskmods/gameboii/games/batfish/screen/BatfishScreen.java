package com.fiskmods.gameboii.games.batfish.screen;

import com.fiskmods.gameboii.games.batfish.BatfishGraphics;
import com.fiskmods.gameboii.games.batfish.BatfishSounds;
import com.fiskmods.gameboii.graphics.Draw;
import com.fiskmods.gameboii.graphics.screen.Button;
import com.fiskmods.gameboii.graphics.screen.Screen;
import com.fiskmods.gameboii.graphics.screen.Slider;
import com.fiskmods.gameboii.graphics.screen.style.ButtonStyle;
import com.fiskmods.gameboii.graphics.screen.style.ScreenStyle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Locale;

public abstract class BatfishScreen extends Screen
{
    protected static final int BRICK_SIZE = 48;

    public static final ScreenStyle STYLE = ScreenStyle.builder()
            .consoleButtons(BatfishConsoleButtonStyle.INSTANCE)
            .button(Button.class,
                    ButtonStyle.<Button>bg(() -> BatfishGraphics.buttons, 0, 0, 200, 20)
                            .andThen(ButtonStyle.text(BatfishGraphics.BUTTON_TEXT, selected -> selected ? Color.YELLOW : Color.WHITE, 28))
            ).button(Slider.class,
                    ButtonStyle.<Slider>uv(() -> BatfishGraphics.buttons, 0, 40, 200, 20)
                            .andThen(ButtonStyle.sliderPeg(() -> BatfishGraphics.buttons, 10, 0, 0, 200, 20))
                            .andThen(ButtonStyle.text(BatfishGraphics.BUTTON_TEXT, selected -> selected ? Color.YELLOW : Color.WHITE, 28))
            ).build();

    public BatfishScreen(ScreenStyle style)
    {
        super(style);
    }

    @Override
    public void playButtonPressSound()
    {
        BatfishSounds.click.play(1, 1);
    }

    public void drawBrickBackground(Graphics2D g2d)
    {
        for (int x = 0; x < width / BRICK_SIZE; ++x)
        {
            for (int y = 0; y < height / BRICK_SIZE; ++y)
            {
                Draw.image(g2d, BatfishGraphics.stone, BRICK_SIZE * x, BRICK_SIZE * y, BRICK_SIZE, BRICK_SIZE);
            }
        }
    }

    public void drawCoinCount(Graphics2D g2d, int x, int y, int coins, boolean center)
    {
        g2d.setFont(BatfishGraphics.DEFAULT);
        String s = String.format(Locale.ROOT, "%,d", coins);

        if (center)
        {
            x -= (25 + 2 + fontRenderer.getStringWidth(s)) / 2;
            y -= 16;
        }

        fontRenderer.drawStringWithShadow(s, x + 25, y + 11, 0xFFCD21, 0x3F2700, 2);
        Draw.imageCentered(g2d, BatfishGraphics.coin, x, y, 32, 32);
    }
}
