package com.example.javafxwordle;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

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

    @FXML
    public GridPane gridPane;

    @FXML
    protected void onKeyPressed(KeyEvent keyEvent) {
        mainHelper.onKeyPressed(gridPane, keyEvent);
    }

    @FXML
    protected void onMouseClicked() {
        gridPane.requestFocus();
    }

    public static String getRandomWord() {
        return mainHelper.getRandomWord();
    }

    public static boolean guessWord(String winningWord) {
        return mainHelper.guessWord(winningWord);
    }
}