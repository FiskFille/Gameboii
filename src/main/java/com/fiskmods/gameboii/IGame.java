package com.fiskmods.gameboii;

import com.fiskmods.gameboii.graphics.Screen;

public interface IGame
{
    void register();

    void init(int width, int height);

    void readSaveData(byte[] data) throws Exception;

    byte[] writeSaveData() throws Exception;

    void keyTyped(char character, int keyCode);

    void draw(float partialTicks);

    void tick();

    void exit();

    int getWidth();

    int getHeight();

    Screen getScreen();

    void displayScreen(Screen screen);
}
