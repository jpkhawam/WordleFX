<p align="center">
  
<img src="https://github.com/jpkhawam/WordleFX/blob/master/images/github.png" align="center" alt="screenshot" />
  
</p>

------

## A Wordle clone, built in JavaFX, with Java, FXML and CSS. 
<p align="center">
<img src="https://github.com/jpkhawam/WordleFX/blob/master/images/screenshot-1.png" alt="screenshot" height="500"/> <img src="https://github.com/jpkhawam/WordleFX/blob/master/images/screenshot-2.png" alt="screenshot" height="500"/> 
</p>

------

# How does it work?
I took the wordle list of winning words (words that'll come up some day, pre-NYT acquisition), and the list of all recognized words that the user is able to guess.
On launch, a random word will be picked from the winning list, rather than having one word per day only.

# Installation

1- Make sure you have a Java Runtime Environment (JRE) downloaded that includes JavaFX. I would recommend [Bellsoft Liberica 17](https://bell-sw.com/pages/downloads/#/java-17-lts). (**NOTE** you need the **FULL** JRE or JDK, not the standard, you can change in the drop down which version you are downloading).
If you don't plan on developing anything in Java, download the full JRE, else download the full JDK.

2- Once your JRE or JDK is downloaded, download WordleFX-v1.1.0.jar from [the releases page](https://github.com/jpkhawam/WordleFX/releases/tag/v1.1.0).

3- On Linux and MacOS, open your terminal/console app, in Windows open CMD. You must navigate in your terminal to where you downloaded the .jar file. You can either do that by right clicking inside the folder where it is downloaded and select "Open in Terminal", or navigate to it from where your terminal currently is (for example, if it is downloaded in /Downloads, the command `cd Downloads` navigates you there.) Then, to run the application, run this command:

`java -jar WordleFX-v1.1.0.jar` (or whatever the .jar file is named).

Closing the terminal would also close the application.

This should run it. If it doesn't, make sure you have restarted after downloading your JRE.

# What is missing?
Well, the animations in JavaFX are... _weird_. I can not have each letter wait for the other to reveal itself, so for the sake of not looking like a 2007 Powerpoint, letters will fade in/fade out when guessed. Also, dark mode, colorblind mode, and Statistics were not made.

## Code explanation
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
