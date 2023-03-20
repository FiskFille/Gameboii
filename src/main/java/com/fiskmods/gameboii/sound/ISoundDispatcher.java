package com.fiskmods.gameboii.sound;

import com.fiskmods.gameboii.sound.Sound.Category;

public interface ISoundDispatcher
{
    ISoundInstance dispatch(Category category, float volume, float pitch);
}
