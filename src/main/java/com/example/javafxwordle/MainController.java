package com.example.javafxwordle;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class MainController {

    public MainController() throws IOException {

    }

    private final MainHelper mainHelper = MainHelper.getInstance();

    @FXML
    public GridPane gridPane;


    public void createGrid() {
        mainHelper.createGrid(gridPane);
    }

    public void gridRequestFocus() {
        gridPane.requestFocus();
    }

    @FXML
    protected void onKeyPressed(KeyEvent keyEvent) {
        mainHelper.onKeyPressed(gridPane, keyEvent);
    }

    public String getRandomWord() {
        return mainHelper.getRandomWord();
    }

    public boolean guessWord(String winningWord) {
        return mainHelper.guessWord(winningWord);
    }
}