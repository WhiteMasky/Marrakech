## Code Review

Reviewed by: **Yichi Zhang**, u7748799

Reviewing code written by: **Yiping Yan**, u7724469

Component: gui/Game.java

### Comments 

**What are the best features of this code?**

The provided code is a JavaFX application for game "Marrakech",it gives us a visible graphical user interface of Marrakech, user can play Marrakech game through the GUI

**Is the code well-documented?**

Yes, there are many comments through over the code, explaining the functions implemented by code

**Is the program decomposition (class and method structure) appropriate?**

Yes, `displayState()` is responsible for updating the game state based on a given string.   `makeControls()` starts defining controls such as buttons and combo boxes

**Does it follow Java code conventions (for example, are methods and variables properly named), and is the style consistent throughout?** 

Yes, it takes good use of standard naming conventions for Java classes, methods, and variables. Class names should start with an uppercase letter, and method and variable names should start with a lowercase letter. 

**If you suspect an error in the code, suggest a particular situation in which the program will not function correctly.** 

We found a bug in the code, when there is no player in the game, it will lead to an null pointer error, we want to fix it with an if-judgement, if no player is in the game, then the pop-up window will not start.


