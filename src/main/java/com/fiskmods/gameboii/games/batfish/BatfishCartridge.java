package com.fiskmods.gameboii.games.batfish;

import com.fiskmods.gameboii.Cartridge;

public class BatfishCartridge
{
    public static final Cartridge INSTANCE = new Cartridge("batfish", () -> Batfish.INSTANCE);
}
