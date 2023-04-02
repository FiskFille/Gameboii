package com.fiskmods.gameboii.graphics.screen.style;

import com.fiskmods.gameboii.graphics.screen.ConsoleButton;
import com.fiskmods.gameboii.graphics.screen.Screen;

import java.awt.Graphics2D;
import java.util.List;

@FunctionalInterface
public interface ConsoleButtonStyle
{
    void draw(Graphics2D g2d, Screen screen, List<ConsoleButton> buttons);
}
