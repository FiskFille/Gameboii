package com.fiskmods.gameboii.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.fiskmods.gameboii.sound.ISoundDispatcher;
import com.fiskmods.gameboii.sound.Sound;
import com.fiskmods.gameboii.sound.Sound.Category;

public interface IResourceLoader
{
    BufferedImage loadImage(String path) throws IOException;

    Resource load(String path, int width, int height);

    Resource loadGIF(String path, int width, int height, int delay);

    ResourceAnimated loadAnimation(String path, int width, int height);
    
    ISoundDispatcher loadSoundData(String path) throws IOException;
    
    Sound loadSound(String path, Category category);
    
    Sound loadSound(String path, int variations, Category category);
}
