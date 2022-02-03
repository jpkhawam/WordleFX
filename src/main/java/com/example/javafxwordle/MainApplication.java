package com.example.javafxwordle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

import static com.example.javafxwordle.MainController.*;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));

        String input;
        while ((input = winningWordsReader.readLine()) != null)
            winningWords.add(input);
        while ((input = dictionaryWordsReader.readLine()) != null)
            dictionaryWords.add(input);

        Random random = new Random();
        String winningWord = winningWords.get(random.nextInt(winningWords.size()));
        guessWord(winningWord);

        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        Scene scene = new Scene(fxmlLoader.load(), screenWidth, screenHeight);
        stage.setTitle("Wordle!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}