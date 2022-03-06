package com.example.javafxwordle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);

        Label label = new Label();
        if (guessed) {
            label.setText("Congrats!");
            label.getStyleClass().setAll("lead", "text-success", "big-font");
        } else {
            label.setText("Sorry! The winning word was " + winningWord.toUpperCase());
            label.getStyleClass().setAll("lead", "text-warning", "big-font");
        }

        Button playAgain = new Button("PLAY AGAIN");
        playAgain.getStyleClass().setAll("btn", "btn-primary");
        playAgain.setOnMouseClicked(me -> {
            resetGame.set(true);
            stage.close();
        });

        Button quitButton = new Button("QUIT");
        quitButton.getStyleClass().setAll("btn", "btn-warning");
        quitButton.setOnMouseClicked(me -> {
            resetGame.set(false);
            quitApplication.set(true);
            stage.close();
        });

        root.getChildren().addAll(label, playAgain, quitButton);
        Scene scene = new Scene(root, 300, 260);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets()
                .add(Objects.requireNonNull(ScoreWindow.class.getResource("wordle.css"))
                        .toExternalForm());
        stage.setScene(scene);
        stage.showAndWait();
    }
}
