package com.example.javafxwordle;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.util.*;

import static com.example.javafxwordle.MainApplication.dictionaryWords;
import static com.example.javafxwordle.MainApplication.winningWords;

public class MainHelper {

    private static MainHelper INSTANCE = null;

    private int CURRENT_ROW = 1;
    private int CURRENT_COLUMN = 1;
    private final int MAX_COLUMN = 5;
    private final int MAX_ROW = 6;
    private String wordToGuess = "OASIS";

    private MainHelper() {
    }

    public static MainHelper getInstance() {
        if (INSTANCE == null)
            INSTANCE = new MainHelper();
        return INSTANCE;
    }

    public void createGrid(GridPane gridPane) {
        for (int i = 1; i <= MAX_ROW; i++) {
            for (int j = 1; j <= MAX_COLUMN; j++) {
                Label label = new Label();
                label.getStyleClass().add("default-tile");
                gridPane.add(label, j, i);
            }
        }
    }

    public void createKeyboard(GridPane keyboardRow_first, GridPane keyboardRow_second, GridPane keyboardRow_third) {
        String[] firstRowLetters = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"};
        String[] secondRowLetters = {"A", "S", "D", "F", "G", "H", "J", "K", "L"};
        String[] thirdRowLetters = {"↵", "Z", "X", "C", "V", "B", "N", "M", "←"};

        for (int i = 0; i < firstRowLetters.length; i++) {
            Label label = new Label();
            label.getStyleClass().add("keyboardTile");
            label.setText(firstRowLetters[i]);
            keyboardRow_first.add(label, i, 1);
        }
        for (int i = 0; i < secondRowLetters.length; i++) {
            Label label = new Label();
            label.getStyleClass().add("keyboardTile");
            label.setText(secondRowLetters[i]);
            keyboardRow_second.add(label, i, 2);
        }
        for (int i = 0; i < thirdRowLetters.length; i++) {
            Label label = new Label();
            if ( i == 0 || i == thirdRowLetters.length - 1)
                label.getStyleClass().add("keyboardTileBackspace");
            else
                label.getStyleClass().add("keyboardTile");
            label.setText(thirdRowLetters[i]);
            keyboardRow_third.add(label, i, 3);
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
        for (int i = 1; i <= MAX_COLUMN; i++) {
            Label label = getLabel(gridPane, searchRow, i);
            String styleClass;
            if (label != null) {
                char currentCharacter = label.getText().charAt(0);
                if (winningWord.charAt(i - 1) == currentCharacter) {
                    styleClass = "correct-letter";
                } else if (winningWord.contains(label.getText())) {
                    styleClass = "present-letter";
                } else {
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
                    colorRowLabels(gridPane, CURRENT_ROW, wordToGuess.toUpperCase());
                    CURRENT_ROW++;
                    CURRENT_COLUMN = 1;
                }
            }
        }
    }

    public void getRandomWord() {
        wordToGuess = winningWords.get(new Random().nextInt(winningWords.size()));
    }

    private boolean isValidGuess(String guess) {
        return binarySearch(winningWords, guess) || binarySearch(dictionaryWords, guess);
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

}
