package com.example.javafxwordle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

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