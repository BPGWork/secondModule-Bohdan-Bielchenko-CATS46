package com.ua.javaRush.islandSimulation.domain.entity;

import java.util.Map;

// Map EMOJI Entities
public class Emoji {
    private static final Map<String, String> EMOJI = Map.ofEntries(
            Map.entry("Plant",    "\uD83C\uDF40"),
            Map.entry("Bear",    "🐻"),
            Map.entry("Wolf",    "🐺"),
            Map.entry("Snake",   "🐍"),
            Map.entry("Fox",     "🦊"),
            Map.entry("Eagle",   "🦅"),
            Map.entry("Buffalo", "🐃"),
            Map.entry("Horse",   "🐎"),
            Map.entry("Deer",    "🦌"),
            Map.entry("Boar",    "🐗"),
            Map.entry("Sheep",   "🐑"),
            Map.entry("Goat",    "🐐"),
            Map.entry("Rabbit",  "🐇"),
            Map.entry("Duck",    "🦆"),
            Map.entry("Mouse",   "🐁"),
            Map.entry("Caterpillar", "🐛")
    );

    private Emoji () {}

    public static Map<String, String> getEMOJI() {
        return EMOJI;
    }
}
