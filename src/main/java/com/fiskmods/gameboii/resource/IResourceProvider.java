package com.fiskmods.gameboii.resource;

import com.fiskmods.gameboii.Cartridge;
import com.fiskmods.gameboii.sound.ISoundDispatcher;

import java.io.IOException;
import java.io.InputStream;

public interface IResourceProvider
{
    InputStream getInputStream(Cartridge cartridge, String path) throws IOException;

    ISoundDispatcher loadSoundData(Cartridge cartridge, String path) throws IOException;
}
