package com.example.javafxwordle;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.*;

import static com.example.javafxwordle.MainApplication.dictionaryWords;
import static com.example.javafxwordle.MainApplication.winningWords;

public class MainHelper {

    private static MainHelper INSTANCE = null;

    private final GameSettings gameSettings = GameSettings.getInstance();

    private int CURRENT_ROW = 1;
    private int CURRENT_COLUMN = 1;
    private final int MAX_COLUMN = 5;
    private final int MAX_ROW = 6;

    private MainHelper()  {}

    public static MainHelper getInstance() throws IOException {
        if (INSTANCE == null)
            INSTANCE = new MainHelper();
        return INSTANCE;
    }

    public void createGrid(GridPane gridPane) {
        for (int i = 1; i <= GameSettings.getInstance().getMaxNumberOfGuesses(); i++) {
            for (int j = 1; j <= GameSettings.getInstance().getWordLength(); j++) {
                Label label = new Label();
                label.getStyleClass().add("default-tile");
                gridPane.add(label, j, i);
            }
        }
    }

    private void setLabelText(GridPane gridPane, int searchRow, int searchColumn, String input) {
        Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label != null)
            label.setText(input);
    }

    private Label getLabel(GridPane gridPane, int searchRow, int searchColumn) {
        for (Node child : gridPane.getChildren()) {
            Integer r = GridPane.getRowIndex(child);
            Integer c = GridPane.getColumnIndex(child);
            int row = r == null ? 0 : r;
            int column = c == null ? 0 : c;
            if (row == searchRow && column == searchColumn && (child instanceof Label))
                return (Label) child;
        }
        return null;
    }

    private String getLabelText(GridPane gridPane, int searchRow, int searchColumn) {
        Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label != null)
            return label.getText();
        return null;
    }

    private void setLabelStyleClass(GridPane gridPane, int searchRow, int searchColumn, String styleclass) {
        Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label != null) {
            label.getStyleClass().add(styleclass);
        }
    }

    private void clearLabelStyleClass(GridPane gridPane, int searchRow, int searchColumn) {
        Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label != null) {
            label.getStyleClass().clear();
        }
    }

    private void colorRowLabels(GridPane gridPane, int searchRow, String winningWord) {
        for(int i = 1; i <= MAX_COLUMN; i++) {
            Label label = getLabel(gridPane, searchRow, i);
            String styleClass;
            if (label != null) {
                char currentCharacter = label.getText().charAt(0);
                if (winningWord.charAt(i - 1) == currentCharacter) {
                    styleClass = "correct-letter";
                }
                else if (winningWord.contains(label.getText())) {
                    styleClass = "present-letter";
                }
                else {
                    styleClass = "wrong-letter";
                }
                clearLabelStyleClass(gridPane, searchRow, i);
                setLabelStyleClass(gridPane, searchRow, i, styleClass);
            }
        }
    }

    private String getWordFromCurrentRow(GridPane gridPane) {
        StringBuilder input = new StringBuilder();
        for (int i = 1; i <= MAX_COLUMN; i++)
            input.append(getLabelText(gridPane, CURRENT_ROW, i));
        return input.toString();
    }

    public void onKeyPressed(GridPane gridPane, KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
            if ((CURRENT_COLUMN == MAX_COLUMN || CURRENT_COLUMN == 1)
                    && !Objects.equals(getLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN), "")) {
                setLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN, "");
                clearLabelStyleClass(gridPane, CURRENT_ROW, CURRENT_COLUMN);
                setLabelStyleClass(gridPane, CURRENT_ROW, CURRENT_COLUMN, "default-tile");
            } else if ((CURRENT_COLUMN > 1 && CURRENT_COLUMN < MAX_COLUMN)
                    || (CURRENT_COLUMN == MAX_COLUMN && Objects.equals(getLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN), ""))) {
                CURRENT_COLUMN--;
                setLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN, "");
                clearLabelStyleClass(gridPane, CURRENT_ROW, CURRENT_COLUMN);
                setLabelStyleClass(gridPane, CURRENT_ROW, CURRENT_COLUMN, "default-tile");
            }

        } else if (keyEvent.getCode().isLetterKey()) {
            // this is to make it so that when the user types a letter but the row is full
            // it doesn't change the last letter instead
            if (CURRENT_COLUMN != MAX_COLUMN || Objects.equals(getLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN), "")) {
                setLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN, keyEvent.getText().toUpperCase());
                setLabelStyleClass(gridPane, CURRENT_ROW, CURRENT_COLUMN, "tile-with-letter");
                if (CURRENT_COLUMN < MAX_COLUMN)
                    CURRENT_COLUMN++;
            }
        }

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (CURRENT_ROW <= MAX_ROW && CURRENT_COLUMN == MAX_COLUMN) {
                String guess = getWordFromCurrentRow(gridPane).toLowerCase();
                if (isValidGuess(guess)) {
                    // replace this with actual guess when ready
                    colorRowLabels(gridPane, CURRENT_ROW, "OASIS");
                    CURRENT_ROW++;
                    CURRENT_COLUMN = 1;
                }
            }
        }
    }

    /**
     * @return random word from winningWords
     */
    public String getRandomWord() {
        return winningWords.get(new Random().nextInt(winningWords.size()));
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
