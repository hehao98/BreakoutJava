# BreakoutJava
## Game

A Java Clone of the Classic Atari Breakout Game.

![](resource/game.gif)

Player's objective is to use the ball to eliminate as many blocks as possible, but he/she has to use the paddle to prevent the ball from falling. Press Enter to start. If you fails, the game will automatically reset to its original state.

Thank learnopengl.com for providing free image resources.

See `game.gif` for a result representation. `Breakout.jar` is the executable(for JVM 1.8 or higher).

## Code

`BreakoutWindow` extends from `JFrame`  and is the main window class of this game. It handles user input and graphics.

`BreakoutGame` is the core game class that implements game logic. It stores and updates game state in each frame.

`Physics` contains utility functions for collision resolution and detection.

Other classes are all game object classes, which should be self-explanatary.

## About

Author: 何昊, 1600012742

Github Link: https://github.com/hehao98/BreakoutJava