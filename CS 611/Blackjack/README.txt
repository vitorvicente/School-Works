##########################################
#   Assignment #3 for CS611 Spring '21   #
#   Code by: Vitor Vicente               #
##########################################

How to compile? 

Inside the provided folder, run "javac *.java".

How to run?

After compilation, proceed to run "java Main", and you will begin to run the game straight away! Once in the game, you'll be prompted to enter details about players etc.. and then begin! There will also be instructions printed on screen.


Class Description:
- Main.java - Main class, just an entry point to the game, doesn't do anything else.
- BlackJack.java - This is the main class for the BlackJack game, it extends the Game class, and implements the specifications needed to play.
- Entity.java - Abstract parent class for the playable Entities (Players & Teams), has some of the internal workings that every playable character needs.
- Player.java - Player Object class, this class is actually quite small since the Entity class does most of the work, but it still has some specifics regarding naming.
- Team.java - Team Object class, this class is an expansion of the Entity class, and has a lot of the needed inner workings for team play (this isn't used in this game but I kept it anyway).
- Game.java - Abstract parent class for game objects (TicTacToe & OrderAndChaos), the class provided not only a lot of the inner workings for the games, but two _required_ abstract methods for every game.
- Card.java - Card Object Class, sets out some basis for Card objects.
- CardHand.java - CardHand Object class that sets out basis for CardHands for players in games, it sets out some useful methods used there.
- CardPlayer - This is a further extension of the Player class, that sets out some requirements for Card Game Players that aren't needed for every type of Player.


Design Issues:

The main design decision I made was to limit the number of Splits to 1 per round, I really tried abstracting this, but it was messing with my head too much, so I decided to hardcode this to a maximum, you can think of it as House Rules at my Casino.
Furthermore, I decided to set the starting balance of each player to $25k, and the minimum bet per round is $10 USD. The player will never be allowed to get below the minimum amount.