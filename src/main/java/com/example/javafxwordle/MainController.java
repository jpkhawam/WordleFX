package com.example.javafxwordle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MainController {

    protected static final ArrayList<String> winningWords = new ArrayList<>();
    protected static final ArrayList<String> dictionaryWords = new ArrayList<>();
    private static final GameSettings gameSettings = GameSettings.getInstance();

    private static FileReader winningWordsFileReader;
    private static FileReader dictionaryWordsFileReader;
    static {
        try {
            winningWordsFileReader = new FileReader("src/main/resources/com/example/javafxwordle/winning-words.txt");
            dictionaryWordsFileReader = new FileReader("src/main/resources/com/example/javafxwordle/dictionary.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected static final BufferedReader winningWordsReader = new BufferedReader(winningWordsFileReader);
    protected static final BufferedReader dictionaryWordsReader = new BufferedReader(dictionaryWordsFileReader);

    public static boolean guessWord(String winningWord) {
        Scanner scanner = new Scanner(System.in);
        boolean guessed = false;
        String[] colors = new String[gameSettings.getWordLength()];
        int numberOfGuesses = 0;
        // these are the letters that the user already guessed and are wrong
        ArrayList<Character> wrongLetters = new ArrayList<>();

        // TODO: add list for orange characters here. Make sure to remove letter if it turns green
        // TODO: also add a list for green letters. To style the on screen keyboard

        // HARD MODE VARIABLES //
        // used to store on each guess if any letter from wrongLetters was used
        ArrayList<Character> usedWrongLetters = new ArrayList<>();
        // IMPOSSIBLE MODE VARIABLES //
        // outOfPlaceLetters[i] contains the letters tried at i that are in the winningWord but not at i
        ArrayList<ArrayList<Character>> outOfPlaceLetters = new ArrayList<>();
        for (int i = 0; i < gameSettings.getWordLength(); i++)
            outOfPlaceLetters.add(new ArrayList<>());

        while (!guessed && numberOfGuesses < gameSettings.getMaxNumberOfGuesses()) {
            System.out.print("Guess #" + (numberOfGuesses + 1) + ": ");
            String userGuess = scanner.next();
            if (userGuess.equals(winningWord)) {
                guessed = true;
                Arrays.fill(colors, "GREEN");
                System.out.println(Arrays.toString(colors));
                System.out.println("Congratulations, you win!");
            } else if (!(binarySearch(winningWords, userGuess) || binarySearch(dictionaryWords, userGuess))) {
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
        if (!guessed)
            System.out.println("Sorry, you lose");
        return guessed;
    }

    // Since the words are sorted it is better to use binarySearch than something like list.contains()
    public static boolean binarySearch(ArrayList<String> list, String string) {
        int low = 0, high = list.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparison = string.compareTo(list.get(mid));
            // Check if string is present at mid
            if (comparison == 0) return true;
            // If string is greater, ignore left half
            if (comparison > 0) low = mid + 1;
                // If string is smaller, ignore right half
            else high = mid - 1;
        }
        return false;
    }

    public static boolean containsAnyLetterFromList(String userGuess, ArrayList<Character> wrongLetters, ArrayList<Character> charactersUsed) {
        charactersUsed = new ArrayList<>();
        for (int i = 0; i < userGuess.length(); i++) {
            if (wrongLetters.contains(userGuess.charAt(i))) charactersUsed.add(userGuess.charAt(i));
        }
        return charactersUsed.size() > 0;
    }

}