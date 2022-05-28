##########################################
#   Assignment #2 for CS611 Spring '21   #
#   Code by: Vitor Vicente               #
##########################################


How to compile? 

Inside the provided folder, run "javac *.java".

How to run?

After compilation, proceed to run "java Main", and you will begin to run the games. The games are coded in a way that you'll be prompted on several occasion for what you want to do.
First you'll be prompted for which of the games you'd like to play, after that, to whether or not you'll like to play with teams.
Then you'll enter the game, and you'll be prompted for the required details to create players/teams. After that you get to play the game!



Class Description:

- Main.java - This is the main entry point to the entire assignment, and is built to get which game you'd like to play, and whether or not you want to play using teams. After that you will go on to play the game!
- Board.java - Board object class, holds all of the required internals to have an actual Game Board, and is abstracted enough to include several different games!
- Entity.java - Abstract parent class for the playable Entities (Players & Teams), has some of the internal workings that every playable character needs.
- Player.java - Player Object class, this class is actually quite small since the Entity class does most of the work, but it still has some specifics regarding naming.
- Team.java - Team Object class, this class is an expansion of the Entity class, and has a lot of the needed inner workings for team play.
- Game.java - Abstract parent class for game objects (TicTacToe & OrderAndChaos), the class provided not only a lot of the inner workings for the games, but two _required_ abstract methods for every game.
- TicTacToe.java - TicTacToe game class, extends Game, and implements some specific methods to TicTacToe, including the implementation of the abstract methods inherited.
- OrderAndChaos.java - OrderAndChaos game class, extends Game, and implements some specific methods to OrderAndChaos, including the implementation of the abstract methods inherited.