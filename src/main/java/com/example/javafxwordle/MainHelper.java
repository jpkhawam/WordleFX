package com.example.javafxwordle;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MainHelper {

    private static MainHelper INSTANCE = null;
    private final ArrayList<String> winningWords = new ArrayList<>();
    private final ArrayList<String> dictionaryWords = new ArrayList<>();
    private final GameSettings gameSettings = GameSettings.getInstance();
    private int CURRENT_ROW_NUMBER = 1;
    private int CURRENT_COLUMN_NUMBER = 1;

    private MainHelper() throws IOException {
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

    public static MainHelper getInstance() throws IOException {
        if (INSTANCE == null)
            INSTANCE = new MainHelper();
        return INSTANCE;
    }

    private void modifyTile(GridPane gridPane, int searchRow, int searchColumn, String input) {
        for (Node child : gridPane.getChildren()) {
            Integer r = GridPane.getRowIndex(child);
            Integer c = GridPane.getColumnIndex(child);
            int row = r == null ? 0 : r;
            int column = c == null ? 0 : c;
            if (row == searchRow && column == searchColumn && (child instanceof Label)) {
                Label tile = (Label) child;
                tile.setText(input);
                break;
            }
        }
    }

    private String getTileText(GridPane gridPane, int searchRow, int searchColumn) {
        for (Node child : gridPane.getChildren()) {
            Integer r = GridPane.getRowIndex(child);
            Integer c = GridPane.getColumnIndex(child);
            int row = r == null ? 0 : r;
            int column = c == null ? 0 : c;
            if (row == searchRow && column == searchColumn && (child instanceof Label))
                return ((Label) child).getText();
        }
        return null;
    }

    private String getWordFromRow(GridPane gridPane) {
        StringBuilder input = new StringBuilder();
        for (int i = 1; i <= 5; i++)
            input.append(getTileText(gridPane, CURRENT_ROW_NUMBER, i));
        return input.toString();
    }

    public void onKeyPressed(GridPane gridPane, KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
            if (getTileText(gridPane, CURRENT_ROW_NUMBER, CURRENT_COLUMN_NUMBER) == null)
                if (CURRENT_COLUMN_NUMBER > 1)
                    CURRENT_COLUMN_NUMBER--;
            modifyTile(gridPane, CURRENT_ROW_NUMBER, CURRENT_COLUMN_NUMBER, null);
        }

        else if (keyEvent.getCode().isLetterKey()) {
            // this is to make it so that when the user types a letter but the row is full
            // it doesn't change the last letter instead
            if (CURRENT_COLUMN_NUMBER == 5 && getTileText(gridPane, CURRENT_ROW_NUMBER, CURRENT_COLUMN_NUMBER) == null)
                modifyTile(gridPane, CURRENT_ROW_NUMBER, CURRENT_COLUMN_NUMBER, keyEvent.getText().toUpperCase());
            else if (CURRENT_COLUMN_NUMBER < 6) {
                modifyTile(gridPane, CURRENT_ROW_NUMBER, CURRENT_COLUMN_NUMBER, keyEvent.getText().toUpperCase());
                CURRENT_COLUMN_NUMBER++;
            }
        }

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (CURRENT_ROW_NUMBER < 7 && CURRENT_COLUMN_NUMBER == 5) {
                String guess = getWordFromRow(gridPane).toLowerCase();
                if (isValidGuess(guess)) {
                    CURRENT_ROW_NUMBER++;
                    CURRENT_COLUMN_NUMBER = 1;
                }
            }
        }
    }

    /**
     * @return random word from winningWords
     */
    public String getRandomWord() {
        Random random = new Random();
        return winningWords.get(random.nextInt(winningWords.size()));
    }

    private boolean isValidGuess(String guess) {
        return binarySearch(winningWords, guess) || binarySearch(dictionaryWords, guess);
    }

    /**
     * Function responsible for handling user guessing
     *
     * @param winningWord word that the user is supposed to guess
     * @return true if successfully guessed, false otherwise
     */
    public boolean guessWord(String winningWord) {
        Scanner scanner = new Scanner(System.in);
        boolean guessed = false;
        String[] colors = new String[gameSettings.getWordLength()];
        int numberOfGuesses = 0;
        // these are the letters that the user already guessed and are wrong
        ArrayList<Character> wrongLetters = new ArrayList<>();

        // TODO: add list for orange characters here. Make sure to remove letter if it turns green
        // TODO: also add a list for green letters. To style the on screen keyboard

        /* HARD MODE VARIABLES */
        // used to store on each guess if any letter from wrongLetters was used
        ArrayList<Character> usedWrongLetters = new ArrayList<>();
        /* IMPOSSIBLE MODE VARIABLES */
        // outOfPlaceLetters[i] contains the letters tried at i that are in the winningWord but not at i
        ArrayList<ArrayList<Character>> outOfPlaceLetters = new ArrayList<>();
        for (int i = 0; i < gameSettings.getWordLength(); i++)
            outOfPlaceLetters.add(new ArrayList<>());

        while (!guessed && numberOfGuesses < gameSettings.getMaxNumberOfGuesses()) {
            System.out.print("Guess #" + (numberOfGuesses + 1) + ": ");
            String userGuess = scanner.next().toLowerCase();
            if (userGuess.equals(winningWord)) {
                guessed = true;
                Arrays.fill(colors, "GREEN");
                System.out.println(Arrays.toString(colors));
            } else if (!isValidGuess(userGuess)) {
                System.out.println("Word is not in dictionary");
            } else if (gameSettings.getDifficulty().equals(Difficulty.Hard) || gameSettings.getDifficulty().equals(Difficulty.Impossible)) {
                if (containsAnyLetterFromList(userGuess, wrongLetters, usedWrongLetters))
                    System.out.println("You cannot use any of these letters: " + usedWrongLetters);
                if (gameSettings.getDifficulty().equals(Difficulty.Impossible)) {
                    for (ArrayList<Character> list : outOfPlaceLetters)
                        if (containsAnyLetterFromList(userGuess, list, usedWrongLetters))
                            System.out.println("Cannot use an orange letter in the same spot twice");
                }
            } else {
                for (int i = 0; i < userGuess.length(); i++) {
                    if (userGuess.charAt(i) == winningWord.charAt(i))
                        colors[i] = "GREEN";
                    else if (winningWord.contains("" + userGuess.charAt(i))) {
                        colors[i] = "Orange";
                        outOfPlaceLetters.get(i).add(userGuess.charAt(i));
                    } else {
                        colors[i] = "grey";
                        wrongLetters.add(userGuess.charAt(i));
                    }
                }
                numberOfGuesses++;
                System.out.println(Arrays.toString(colors));
            }
        }
        return guessed;
    }

    /**
     * performs a standard binary search
     *
     * @param list   list of words to search from
     * @param string word to search for in list
     * @return true if found, false otherwise
     */
    public boolean binarySearch(ArrayList<String> list, String string) {
        int low = 0, high = list.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparison = string.compareTo(list.get(mid));
            /* Check if string is present at mid */
            if (comparison == 0) return true;
            /* If string is greater, ignore left half */
            if (comparison > 0) low = mid + 1;
                /* If string is smaller, ignore right half */
            else high = mid - 1;
        }
        return false;
    }

    /**
     * checks if a word contains any letter from a given list. this is to prevent the user from
     * entering a letter guessed incorrectly already, depending on difficulty
     *
     * @param userGuess      the word the user entered
     * @param wrongLetters   letters that are wrong
     * @param charactersUsed passed to store the letters used in this invocation (if any)
     * @return true if any letters were used, false otherwise
     */
    public boolean containsAnyLetterFromList(String userGuess, ArrayList<Character> wrongLetters,
                                             ArrayList<Character> charactersUsed) {
        charactersUsed = new ArrayList<>();
        for (int i = 0; i < userGuess.length(); i++) {
            if (wrongLetters.contains(userGuess.charAt(i))) charactersUsed.add(userGuess.charAt(i));
        }
        return charactersUsed.size() > 0;
    }
}
