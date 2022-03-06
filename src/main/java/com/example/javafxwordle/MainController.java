package com.example.javafxwordle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import org.kordamp.bootstrapfx.BootstrapFX;

public class MainController {

    private final MainHelper mainHelper = MainHelper.getInstance();

    @FXML
    public Label title;
    @FXML
    public GridPane gridPane;
    @FXML
    public GridPane keyboardRow1;
    @FXML
    public GridPane keyboardRow2;
    @FXML
    public GridPane keyboardRow3;

    public void createGrid() {
        mainHelper.createGrid(gridPane);
        title.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        title.getStyleClass().setAll("h1");
    }

    public void createKeyboard() {
        mainHelper.createKeyboard(keyboardRow1, keyboardRow2, keyboardRow3);
    }

    public void gridRequestFocus() {
        gridPane.requestFocus();
    }

    @FXML
    protected void onKeyPressed(KeyEvent keyEvent) {
        mainHelper.onKeyPressed(gridPane, keyboardRow1, keyboardRow2, keyboardRow3, keyEvent);
    }

    public void getRandomWord() {
        mainHelper.getRandomWord();
    }
}
