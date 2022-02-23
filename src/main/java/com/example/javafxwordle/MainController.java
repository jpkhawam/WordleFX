package com.example.javafxwordle;

import java.io.IOException;

public class MainController {
    private static MainHelper mainHelper;

    static {
        try {
            mainHelper = MainHelper.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getRandomWord() {
        return mainHelper.getRandomWord();
    }

    public static boolean guessWord(String winningWord) {
        return mainHelper.guessWord(winningWord);
    }
}