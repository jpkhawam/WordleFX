package com.example.javafxwordle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));

        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        Scene scene = new Scene(fxmlLoader.load(), screenWidth, screenHeight);
     //   MainController.gridPane.requestFocus();
        stage.setTitle("Wordle!");
        stage.setScene(scene);
        stage.show();

//        String winningWord = MainController.getRandomWord();
//
//        if (MainController.guessWord(winningWord))
//            System.out.println("Congratulations, you win!");
//        else
//            System.out.println("Sorry, you lose");
    }

    public static void main(String[] args) {
        launch();
    }
}