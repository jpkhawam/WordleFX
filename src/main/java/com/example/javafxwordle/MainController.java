package com.example.javafxwordle;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class MainController {

    private final MainHelper mainHelper = MainHelper.getInstance();

    @FXML
    public GridPane gridPane;
    @FXML
    public GridPane keyboardRow_first;
    @FXML
    public GridPane keyboardRow_second;
    @FXML
    public GridPane keyboardRow_third;

    public void createGrid() {
        mainHelper.createGrid(gridPane);
    }

    public void createKeyboard() {
        mainHelper.createKeyboard(keyboardRow_first, keyboardRow_second, keyboardRow_third);
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
}
