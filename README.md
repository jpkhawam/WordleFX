# WordleFX

### A simple Wordle clone, built in JavaFX, with plain Java, FXML and CSS.
<img src="https://github.com/jpkhawam/WordleFX/blob/master/images/screenshot-1.png" alt="screenshot" height="550"/> <img src="https://github.com/jpkhawam/WordleFX/blob/master/images/screenshot-2.png" alt="screenshot" height="550"/>
------
# How does it work?
I took the wordle list of winning words (words that'll come up some day, pre-NYT acquisition), and the list of all recognized words that the user is able to guess.
On launch, a random word will be picked from the winning list, rather than having one word per day only.

# Why?
Why not?

# What is missing?
Well, the animations in JavaFX are... _weird_. I can not have each letter wait for the other to reveal itself, so for the sake of not looking like a 2007 Powerpoint, letters will fade in/fade out when guessed. Also, I have yet to add dark mode, or colorblind mode. I'm colorblind myself but seeing well so it's alright for now, it is needed more for dark mode. Statistics and such are also not made yet, might do it soon.

## Code explanation if anyone needs it
MainApplication.java is responsible for starting the app, and for calling the relevant methods in the controller to build some UI elements like the grid and keyboard.
- `MainController.java` is the controller for the main class above. I tried to keep code minimal inside it for easy debugging. 
- `MainHelper.java` has the "logic" needed by the controller to work. It is there to keep the controller clean.
- `main-view.xml` is the layout file that `MainController` handles.
- `wordle.css` contains the relevant CSS for the app.

- `ScoreWindow.java` is the window that appears when a round ends.
- `Toast.java` is used to show "Word not in list" if the user is guessing an invalid word. It is a simple Pane with a Fade in/out transition.

- `dictionary.txt` contains all the words the user may guess
- `winning-words.txt` contains all the words that the app will choose between. Do note I sorted them so that I could use a binarySearch instead of something like .contains().

## What needs fixing?
Besides missing the dark/colorblind modes and statistics, Pseudoclasses could be implemented to better switch between node styles. Also, the Toast appearing stops you from typing until it is gone (after a second). 
