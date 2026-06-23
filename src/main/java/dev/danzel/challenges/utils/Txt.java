package dev.danzel.challenges.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class Txt {
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    public static Component text(String text) {
        return MINI_MESSAGE.deserialize(text);
    }
}
