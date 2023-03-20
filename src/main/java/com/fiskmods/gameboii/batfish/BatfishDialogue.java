package com.fiskmods.gameboii.batfish;

import java.awt.Color;
import java.util.ArrayList;

import com.fiskmods.gameboii.Engine;
import com.fiskmods.gameboii.batfish.level.BatfishPlayer;
import com.fiskmods.gameboii.batfish.level.SpodermenObject;
import com.fiskmods.gameboii.batfish.screen.ScreenCredits;
import com.fiskmods.gameboii.engine.Dialogue;
import com.fiskmods.gameboii.engine.DialogueBuilder;
import com.fiskmods.gameboii.engine.DialogueSpeaker;
import com.fiskmods.gameboii.graphics.ScreenDialogue;
import com.fiskmods.gameboii.level.Level;
import com.fiskmods.gameboii.level.LevelObject;

public interface BatfishDialogue
{
    Color RED = new Color(0xFF3030);
    DialogueBuilder BUILDER = new DialogueBuilder(RED);

    DialogueSpeaker PLAYER = new DialogueSpeaker("You", () -> Batfish.INSTANCE.player.getSkin().getResource());
    DialogueSpeaker FISKFILLE = speaker(BatfishPlayer.Skin.FISK);
    DialogueSpeaker SPODERMEN = speaker(BatfishPlayer.Skin.SPODERMEN);
    DialogueSpeaker ROBO_SPODERMEN = new DialogueSpeaker("Robo-Spodermen", BatfishGraphics.robo_spodermen);

    Dialogue PRO_1 = BUILDER.build(FISKFILLE, null, "Your <h>long-lost brother</h> has invited you out for lunch.");
    Dialogue PRO_2 = BUILDER.build(FISKFILLE, PRO_1, "He's currently out doing his daily superhero activities\nat the top of this building, at <h>%1$.0f m.</h>", () -> new Object[] {Batfish.SPACE_ALTITUDE});
    Dialogue PRO_3 = BUILDER.build(FISKFILLE, PRO_2, "Go find him!");

    Dialogue TUTORIAL_BLADE = BUILDER.build(FISKFILLE, null, "This is a <h>blade</h> powerup. It will save you once from\ncolliding with a <h>wooden</h> floor.");
    Dialogue TUTORIAL_BOMB = BUILDER.build(FISKFILLE, null, "This is a <h>bomb</h> powerup. It will save you once from\ncolliding with <h>any</h> floor.");
    Dialogue TUTORIAL_WORLD = BUILDER.build(FISKFILLE, null, "This is a <h>world</h> powerup. It will allow you to stop\ntime upon pressing <h>C</h>.");

    Dialogue EPI_1 = BUILDER.build(SPODERMEN, null, "<h>%1$s</h>! Took you long enough!", () -> new Object[] {Batfish.INSTANCE.player.getSkin().name});
    Dialogue EPI_2 = BUILDER.build(PLAYER, EPI_1, "Sorry, got stuck in traffic. This thing definitely doesn't\nhold up to building regulations.");
    Dialogue EPI_3 = BUILDER.build(SPODERMEN, EPI_2, "Do you ever plan on actually using your powers for\ngood, instead of just bashing others?");
    Dialogue EPI_4 = BUILDER.build(PLAYER, EPI_3, "Using my powers for good? Like what, becoming a\nsuperhero? <h>Like you?</h>");
    Dialogue EPI_5 = BUILDER.build(SPODERMEN, EPI_4, "There are other ways...").setAction(t -> t.setSkippable(false));
    Dialogue EPI_6 = BUILDER.build(SPODERMEN, EPI_5, "You know - people - they smell.");
    Dialogue EPI_7 = BUILDER.build(SPODERMEN, EPI_6, "They <h>stink</h>, quite frankly. Absolutely <h>disgusting</h>.");
    Dialogue EPI_8 = BUILDER.build(SPODERMEN, EPI_7, "But that <h>doesn't have to</h> be the world we live in...");
    Dialogue EPI_9 = BUILDER.build(PLAYER, EPI_8, "This doesn't sound like you...");
    Dialogue EPI_10 = BUILDER.build(SPODERMEN, EPI_9, "The world could smell like <h>the man your man could</h>\n<h>smell like</h>.");
    Dialogue EPI_11 = BUILDER.build(PLAYER, EPI_10, "<h>Who are you?!</h>");
    Dialogue EPI_12 = BUILDER.build(SPODERMEN, EPI_11, "You were expecting <h>Spodermen</h>, but it was me--");
    Dialogue EPI_13 = BUILDER.build(ROBO_SPODERMEN, EPI_12, "-- <h>ROBO-SPODERMEN</h>!").setAction(BatfishDialogue::unmaskSpodermen);
    Dialogue EPI_14 = BUILDER.build(PLAYER, EPI_13, "Wait, it's all Old Spice?").setAction(t -> armSpodermen(false));
    Dialogue EPI_15 = BUILDER.build(ROBO_SPODERMEN, EPI_14, "<h>Always has been.</h>").setAction(t -> armSpodermen(true));
    Dialogue EPI_16 = BUILDER.build(ROBO_SPODERMEN, EPI_15, "<h>Always has been.</h>").setAction(t -> Engine.displayScreen(new ScreenCredits(t)));

    static DialogueSpeaker speaker(BatfishPlayer.Skin skin)
    {
        return new DialogueSpeaker(skin.name, skin.getResource());
    }
    
    static void armSpodermen(boolean armed)
    {
        Level level = Batfish.INSTANCE.player.level;

        if (level != null)
        {
            for (LevelObject obj : level.objects)
            {
                if (obj instanceof SpodermenObject)
                {
                    ((SpodermenObject) obj).pointGun(armed);
                }
            }
        }
    }

    static void unmaskSpodermen(ScreenDialogue screen)
    {
        Level level = Batfish.INSTANCE.player.level;

        if (level != null)
        {
            for (LevelObject obj : new ArrayList<>(level.objects))
            {
                if (obj instanceof SpodermenObject)
                {
                    ((SpodermenObject) obj).unmask();
                }
            }
        }
    }
}
