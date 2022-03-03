package com.example.javafxwordle;

public class GameSettings {
    private static GameSettings INSTANCE;

    private GameSettings() {
    }

    public static GameSettings getInstance() {
        if (INSTANCE == null)
            INSTANCE = new GameSettings();
        return INSTANCE;
    }

    public int getMaxNumberOfGuesses() {
        return 6;
    }

    public int getWordLength() {
        return 5;
    }
}
