package com.example.javafxwordle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.util.Objects;

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
    @FXML
    public ImageView githubImage;

    public void createGrid() {
        mainHelper.createGrid(gridPane);
        title.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        title.getStyleClass().setAll("h1", "strong");
      //  createImages();
    }

    public void createImages() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/GitHub-Mark-32px.png")));
        githubImage.setImage(image);
        githubImage.setX(10);
        githubImage.setY(15);
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

    public void showHelp(MouseEvent mouseEvent) {
        HelpWindow.display();
    }
}
