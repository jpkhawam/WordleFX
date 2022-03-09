package com.example.javafxwordle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.util.Objects;

public class ScoreWindow {

    private ScoreWindow() {
    }

    public static BooleanProperty resetGame = new SimpleBooleanProperty(false);
    public static BooleanProperty quitApplication = new SimpleBooleanProperty(false);

    public static void display(boolean guessed, String winningWord) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setMaxWidth(500);
        stage.setMaxHeight(460);

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);

        Label mainLabel = new Label();
        if (guessed) {
            mainLabel.setText("           You won! \n The winning word was");
            mainLabel.getStyleClass().setAll("lead", "big-font");
        } else {
            mainLabel.setText("           You lost! \n The winning word was");
            mainLabel.getStyleClass().setAll("big-font");
        }
        Label winningWordLabel = new Label(winningWord.toUpperCase());
        winningWordLabel.getStyleClass().setAll("h2", "strong");

        VBox buttonsVBox = new VBox(5);
        buttonsVBox.setAlignment(Pos.CENTER);

        Button playAgainButton = new Button("PLAY AGAIN");
        playAgainButton.getStyleClass().setAll("btn", "btn-primary");
        playAgainButton.setOnMouseClicked(me -> {
            resetGame.set(true);
            stage.close();
        });

        Button quitButton = new Button("  QUIT");
        quitButton.getStyleClass().setAll("btn", "btn-warning");
        quitButton.setOnMouseClicked(me -> {
            resetGame.set(false);
            quitApplication.set(true);
            stage.close();
        });

        buttonsVBox.getChildren().addAll(playAgainButton, quitButton);

        root.getChildren().addAll(mainLabel, winningWordLabel, buttonsVBox);
        Scene scene = new Scene(root, 300, 260);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets()
                .add(Objects.requireNonNull(ScoreWindow.class.getResource("wordle.css"))
                        .toExternalForm());
        stage.getIcons().add(new Image(Objects.requireNonNull(ScoreWindow.class.getResourceAsStream("images/icon.png"))));
        stage.setScene(scene);
        stage.showAndWait();
    }
}
