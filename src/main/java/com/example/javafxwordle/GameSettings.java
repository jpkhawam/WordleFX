package com.example.javafxwordle;

public class GameSettings {
    private int maxNumberOfGuesses = 6;
    private int wordLength = 5;
    private Difficulty difficulty = Difficulty.Easy;
    private boolean colorBlindMode = false;
    private boolean darkMode = false;

    private static GameSettings INSTANCE;

    private GameSettings() {
    }

    public static GameSettings getInstance() {
        if (INSTANCE == null)
            INSTANCE = new GameSettings();
        return INSTANCE;
    }

    public int getMaxNumberOfGuesses() {
        return maxNumberOfGuesses;
    }

    public void setMaxNumberOfGuesses(int maxNumberOfGuesses) {
        this.maxNumberOfGuesses = maxNumberOfGuesses;
    }

    public int getWordLength() {
        return wordLength;
    }

    public void setWordLength(int wordLength) {
        this.wordLength = wordLength;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isColorBlindMode() {
        return colorBlindMode;
    }

    public void setColorBlindMode(boolean colorBlindMode) {
        this.colorBlindMode = colorBlindMode;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }
}
