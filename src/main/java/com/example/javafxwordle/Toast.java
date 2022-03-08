package com.example.javafxwordle;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.kordamp.bootstrapfx.BootstrapFX;

public final class Toast {

    public static void makeText(Stage ownerStage) {
        Stage stage = new Stage();
        stage.initOwner(ownerStage);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);

        Text text = new Text("Not in word list");
        text.setFill(Color.WHITE);
        text.getStyleClass().setAll("h4");

        StackPane root = new StackPane(text);
        root.setStyle("-fx-background-radius: 5; " +
                "-fx-background-color: rgba(0, 0, 0, 0.8); -fx-padding: 10;");
        root.setOpacity(0);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
        stage.show();

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(750), root);
        fadeTransition.setFromValue(0.75);
        fadeTransition.setToValue(1);
        fadeTransition.setOnFinished(e -> stage.close());
        fadeTransition.play();
    }
}
