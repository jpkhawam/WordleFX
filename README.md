<p align="center">
  
<img src="https://github.com/jpkhawam/WordleFX/blob/master/images/github.png" align="center" alt="screenshot" />
  
</p>

------

## A Wordle clone, built in JavaFX, with plain Java, FXML and CSS. 
<p align="center">
<img src="https://github.com/jpkhawam/WordleFX/blob/master/images/screenshot-1.png" alt="screenshot" height="500"/> <img src="https://github.com/jpkhawam/WordleFX/blob/master/images/screenshot-2.png" alt="screenshot" height="500"/> 
</p>

------

# How does it work?
I took the wordle list of winning words (words that'll come up some day, pre-NYT acquisition), and the list of all recognized words that the user is able to guess.
On launch, a random word will be picked from the winning list, rather than having one word per day only.

# Why?
Why not?

# Installation

1- Make sure you have a Java Runtime Environment (JRE) downloaded that includes JavaFX. I would recommend the [Bellsoft Liberica JDK 11](https://bell-sw.com/pages/downloads/#/java-11-lts). It is free and open source. If you don't plan on developing anything in Java, download the standard JRE, else download the JDK.

2- Once your JRE or JDK is downloaded, download WordleFX-v1.0.0.jar from [the releases page](https://github.com/jpkhawam/WordleFX/releases/tag/v1.0.0).

3- On Linux and MacOS, open your terminal/console app, in Windows open CMD, and type the following command

`java -jar WordleFX-v1.0.0.jar` (or whatever the .jar file is named).

This should run it. If it doesn't, make sure you have restarted after downloading Java, else something went wrong with the installation.

# What is missing?
Well, the animations in JavaFX are... _weird_. I can not have each letter wait for the other to reveal itself, so for the sake of not looking like a 2007 Powerpoint, letters will fade in/fade out when guessed. Also, I have yet to add dark mode, or colorblind mode. I'm colorblind myself but seeing well so it's alright for now, it is needed more for dark mode. Statistics and such are also not made yet, might do it soon.

## Code explanation if anyone needs it
- `MainApplication.java` is responsible for starting the app, and for calling the relevant methods in the controller to build some UI elements like the grid and keyboard.
- `MainController.java` is the controller for the main class above. I tried to keep code minimal inside it for easy debugging. 
- `MainHelper.java` has the "logic" needed by the controller to work. It is there to keep the controller clean.
- `main-view.xml` is the layout file that `MainController` handles.
- `wordle.css` contains the relevant CSS for the app.
- `ScoreWindow.java` is the window that appears when a round ends.
- `Toast.java` is used to show "Word not in list" if the user is guessing an invalid word. It is a simple Pane with a Fade in/out transition.
- `dictionary.txt` contains all the words the user may guess
- `winning-words.txt` contains all the words that the app will choose between. Do note I sorted them so that I could use a binarySearch instead of something like .contains().

## What needs fixing?
Besides missing the dark/colorblind modes and statistics, Pseudoclasses could be implemented to better switch between node styles. Also, the Toast appearing stops you from typing until it is gone (after a second). Also, there isn't an easy way to make a listener for the on screen keyboard clicks. The best is getting where the mouse click coordinates were and then checking which label was there, I will pass on doing that nonsense though.
