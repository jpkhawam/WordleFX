package com.example.javafxwordle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MainApplication extends Application {

    public static final ArrayList<String> winningWords = new ArrayList<>();
    public static final ArrayList<String> dictionaryWords = new ArrayList<>();

    private static Stage stageReference;

    @Override
    public void start(Stage stage) throws IOException {
        initializeWordLists();
        stageReference = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Parent root = fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();
        mainController.createGrid();
        mainController.createKeyboard();
        mainController.getRandomWord();

        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        Scene scene = new Scene(root, 480, 725);
        stage.setMinWidth(480);
        stage.setMinHeight(725);
        stage.setMaxWidth(screenWidth);
        stage.setMaxHeight(screenHeight);
        stage.setTitle("WordleFX");
        stage.setScene(scene);
        stage.show();

        mainController.gridRequestFocus();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void quit() {
        stageReference.close();
    }

    public void initializeWordLists() throws IOException {
        FileReader winningWordsFileReader =
                new FileReader("src/main/resources/com/example/javafxwordle/winning-words.txt");
        FileReader dictionaryWordsFileReader =
                new FileReader("src/main/resources/com/example/javafxwordle/dictionary.txt");
        BufferedReader winningWordsReader =
                new BufferedReader(winningWordsFileReader);
        BufferedReader dictionaryWordsReader =
                new BufferedReader(dictionaryWordsFileReader);

        String input;
        while ((input = winningWordsReader.readLine()) != null)
            winningWords.add(input);
        while ((input = dictionaryWordsReader.readLine()) != null)
            dictionaryWords.add(input);
    }

}
