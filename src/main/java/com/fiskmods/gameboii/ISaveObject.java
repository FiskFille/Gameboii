package com.fiskmods.gameboii;

import java.nio.ByteBuffer;

public interface ISaveObject
{
    void read(ByteBuffer buf, int protocol);

    void write(ByteBuffer buf);
}
