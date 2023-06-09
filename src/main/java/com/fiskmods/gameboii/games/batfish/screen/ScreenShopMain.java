package com.fiskmods.gameboii.games.batfish.screen;

import com.fiskmods.gameboii.Engine;
import com.fiskmods.gameboii.games.batfish.Batfish;
import com.fiskmods.gameboii.games.batfish.BatfishGraphics;
import com.fiskmods.gameboii.games.batfish.BatfishSounds;
import com.fiskmods.gameboii.games.batfish.level.BatfishPlayer;
import com.fiskmods.gameboii.games.batfish.level.BatfishPlayer.Skin;
import com.fiskmods.gameboii.graphics.Draw;
import com.fiskmods.gameboii.graphics.screen.ConsoleButtonType;
import com.fiskmods.gameboii.graphics.screen.Screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class ScreenShopMain extends BatfishScreen
{
    private final Screen prevScreen;

    public Skin selectedSkin;

    public ScreenShopMain()
    {
        super(STYLE);
        selectedSkin = Batfish.INSTANCE.player.getSkin();
        prevScreen = Batfish.INSTANCE.currentScreen;
    }

    @Override
    public void initScreen()
    {
        addConsoleButton(ConsoleButtonType.X, () -> Batfish.INSTANCE.player.hasSkin(selectedSkin) ? "Select" : "Buy", () ->
        {
            if (Batfish.INSTANCE.player.hasSkin(selectedSkin))
            {
                Batfish.INSTANCE.player.setSkin(selectedSkin);
            }
            else if (Batfish.INSTANCE.player.unlock(selectedSkin))
            {
                BatfishSounds.coin.play(1, 1);
            }
        });
        addConsoleButton(ConsoleButtonType.Z, "Back", () -> Engine.displayScreen(prevScreen));
    }

    @Override
    public void draw(Graphics2D g2d)
    {
        drawBrickBackground(g2d);

        String s = "Shop";
        g2d.setFont(BatfishGraphics.SHOP_TITLE);
        fontRenderer.drawStringWithShadow(s, (width - fontRenderer.getStringWidth(s)) / 2, 50, 0xFFFFFF, 0);

        BatfishPlayer player = Batfish.INSTANCE.player;
        int srcX1 = 0;
        int srcX2 = srcX1 + 20;

        for (int i = selectedSkin.ordinal; i > 0; --i)
        {
            Skin skin = Skin.SKINS[i - 1];
            Rectangle rect = new Rectangle(width / 2 - (selectedSkin.ordinal - i + 1) * 80, height / 3, 80, 80);

            if (player.isSkinAvailable(skin))
            {
                Draw.imageCentered(g2d, skin.getResource(), rect.x, rect.y + (20 - skin.height) * 4, rect.width, rect.height, srcX1, 0, srcX2, 20);
            }
            else
            {
                g2d.setColor(Color.BLACK);
                g2d.fillRect(rect.x - rect.width / 2, rect.y + (20 - skin.height) * 4 - rect.height / 2, rect.width, rect.height);
            }

            if (skin == player.getSkin())
            {
                g2d.setColor(Color.BLUE);
                g2d.fillRect(rect.x - rect.width / 2, rect.y + rect.height / 2 + 5, rect.width, 5);
            }
        }

        for (int i = selectedSkin.ordinal + 1; i < Skin.SKINS.length; ++i)
        {
            Skin skin = Skin.SKINS[i];
            Rectangle rect = new Rectangle(width / 2 + (i - selectedSkin.ordinal) * 80, height / 3, 80, 80);

            if (player.isSkinAvailable(skin))
            {
                Draw.imageCentered(g2d, skin.getResource(), rect.x, rect.y + (20 - skin.height) * 4, rect.width, rect.height, srcX1, 0, srcX2, 20);
            }
            else
            {
                g2d.setColor(Color.BLACK);
                g2d.fillRect(rect.x - rect.width / 2, rect.y + (20 - skin.height) * 4 - rect.height / 2, rect.width, rect.height);
            }

            if (skin == player.getSkin())
            {
                g2d.setColor(Color.BLUE);
                g2d.fillRect(rect.x - rect.width / 2, rect.y + rect.height / 2 + 5, rect.width, 5);
            }
        }

        Draw.imageCentered(g2d, selectedSkin.getResource(), width / 2, height / 3 + (20 - selectedSkin.height) * 8, 160, 160, srcX1, 0, srcX2, 20);
        drawCoinCount(g2d, 34, 34, player.totalCoins, false);
        //        drawImage(g2d, player.skin.getResource(), 10, 80, 80, 80);

        s = selectedSkin.name;
        g2d.setFont(BatfishGraphics.DEFAULT);
        fontRenderer.drawStringWithShadow(s, (width - fontRenderer.getStringWidth(s)) / 2, height / 3 + 120, 0xFFFFFF, 0, 1);
        //        g2d.setFont(BatfishFont.DEFAULT);
        //        fontRenderer.drawStringWithShadow(String.format("%s %s/%s", selectedSkin.name, selectedSkin.ordinal + 1, Skin.values().length), width / 2 - 80, height / 2 - 130, 0xffffff, 0);

        if (!player.hasSkin(selectedSkin))
        {
            g2d.setFont(BatfishGraphics.DEFAULT);
            drawCoinCount(g2d, width / 2, height / 3 + 165, selectedSkin.price, true);
        }

        super.draw(g2d);
    }

    @Override
    public void keyTyped(char character, int keyCode)
    {
        super.keyTyped(character, keyCode);

        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A)
        {
            cycleSkin(-1);
        }
        else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D)
        {
            cycleSkin(1);
        }
    }

    private void cycleSkin(int offset)
    {
        int i = selectedSkin.ordinal + offset;

        if (i < 0)
        {
            i = Skin.SKINS.length - 1;
        }
        else if (i > Skin.SKINS.length - 1)
        {
            i = 0;
        }

        selectedSkin = Skin.SKINS[i];

        if (!Batfish.INSTANCE.player.isSkinAvailable(selectedSkin))
        {
            cycleSkin(offset);
        }
    }
}
