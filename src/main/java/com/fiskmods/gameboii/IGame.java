package com.fiskmods.gameboii;

import com.fiskmods.gameboii.graphics.screen.Screen;
import com.fiskmods.gameboii.resource.IResourceListener;

import java.util.function.Consumer;

public interface IGame
{
    void register(Consumer<IResourceListener> listeners);

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
