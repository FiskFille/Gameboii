package com.fiskmods.gameboii;

import java.util.function.Supplier;

import com.fiskmods.gameboii.batfish.Batfish;

public enum Cartridge
{
    BATFISH("batfish", () -> Batfish.INSTANCE),
//    ALPACA("alpaca", () -> AlpacaGame.INSTANCE)
    ;

    public final String id;
    final Supplier<?> supplier;

    Cartridge(String id, Supplier<?> supplier)
    {
        this.id = id;
        this.supplier = supplier;
    }

    public static Cartridge get(int damage)
    {
        return values()[Math.min(Math.max(damage, 0), values().length - 1)];
    }
}
