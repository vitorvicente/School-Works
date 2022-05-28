/*=====================================================*/
/* Project Title: Tic Tac Toe                          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/



import java.util.Scanner;

public class Game {
	private Player p1;
	private Player p2;
	private Board b;
	
	
	public Game() {
		this.welcome();
		this.createPlayers();
		this.howToPlay();
		
		while(true) {
			this.startGame();
			
			boolean again = false;
			try {
				again = this.getYesNo("Would you like to play again?");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (!again)
				break;
		}
		
		this.end();
	}
	

	/* ============ */
	/* Game Methods */
	/* ============ */
	
	private void startGame() {
		this.b = new Board();
		
		boolean currentPlayer = true;
		while (true) {
			if (currentPlayer) {
				//Player #1
				int lastMove = this.getPlacement(1);
				boolean won = this.b.checkWin(lastMove);
				if (won) {
					this.won(1);
					break;
				}
			} else {
				//Player #2
				int lastMove = this.getPlacement(2);
				boolean won = this.b.checkWin(lastMove);
				if (won) {
					this.won(2);
					break;
				}
			}
			
			currentPlayer = ! currentPlayer;
		} 
	}
	
	private void createPlayers() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		
		System.out.println("Please enter the name of Player #1:");
		String p1Name = scanner.nextLine();
		System.out.println("Please enter the name of Player #2:");
		String p2Name = scanner.nextLine();
		
		System.out.println();
		boolean selectOwnSymbols = false;
		try {
			selectOwnSymbols = this.getYesNo("Would you like to select your own symbols?");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		if (selectOwnSymbols) {
			System.out.println("Please enter the symbol for Player #1: ('X'/'O')");
			String p1Symbol = scanner.nextLine();
			
			switch (p1Symbol) {
				case "X":
					this.p1 = new Player(p1Name, 'X');
					this.p2 = new Player(p2Name, 'O');
					break;
				case "O":
					this.p1 = new Player(p1Name, 'O');
					this.p2 = new Player(p2Name, 'X');
					break;
				default:
					throw new IllegalArgumentException("Invalid Symbol Char! Only symbols allowed are 'X' and 'O'.");
			}
		} else {
			if (Math.random() > 0.5) {
				this.p1 = new Player(p1Name, 'X');
				this.p2 = new Player(p2Name, 'O');
			} else {
				this.p1 = new Player(p1Name, 'O');
				this.p2 = new Player(p2Name, 'X');
			}
		}
		
		System.out.println();
		System.out.println("Player #1 Symbol: " + p1.getSymbol());
		System.out.println("Player #2 Symbol: " + p2.getSymbol());
		System.out.println();
	}
	
	private int getPlacement(int playerID) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		int placement = -1;
		char symbol = 'a';
		
		if (playerID == 1)
			symbol = p1.getSymbol();
		else
			symbol = p2.getSymbol();
		
		while (true) {
			System.out.println("Player #" + playerID + " please select a placeable square:");
			placement = scanner.nextInt();
			
			boolean placed = this.b.placeChar(symbol, placement);
			
			if (placed)
				return placement;
			else
				System.out.println("Position occupied, re-trying! \n");
		}
	}
	
	private void won(int playerID) {
		
		System.out.println();
		System.out.println("Player #" + playerID + " has won the game!!");
		System.out.println();
		
		if (playerID == 1) {
			this.p1.addWin();
			this.p2.addLoss();
		} else {
			this.p2.addWin();
			this.p1.addLoss();
		}
		
		System.out.println("Current Scores:");
		System.out.println("Player #1: " + this.p1.getWins() + " Wins | " + this.p1.getLosses() + " Losses");
		System.out.println("Player #2: " + this.p2.getWins() + " Wins | " + this.p2.getLosses() + " Losses");
		System.out.println();
	}
	
	
	/* ================ */
	/* Printing Methods */
	/* ================ */
	
	private void welcome() {
		System.out.println("Welcome to TicTacToe!");
		System.out.println();
	}
	
	private void howToPlay() {
		System.out.println("We are about to begin, each player will take a turn placing a symbol.");
		System.out.println("To place a symbol you'll be prompted to enter an integer between 1 and 9, each represent a square on the board:");
		System.out.println("+--+--+--+");
		System.out.println("|1 |2 |3 |");
		System.out.println("+--+--+--+");
		System.out.println("|4 |5 |6 |");
		System.out.println("+--+--+--+");
		System.out.println("|7 |8 |9 |");
		System.out.println("+--+--+--+");
		System.out.println();
	}
	
	private void end() {
		System.out.println();
		System.out.println("Thank you for playing Tic Tac Toe!");
		System.out.println();
		System.out.println("=============================");
		System.out.println("A game by Victor Vicente");
		System.out.println("Made for BU CS611, Spring '21");
		System.out.println("=============================");
		System.out.println();
	}
	
	
	/* =========== */
	/* Aux Methods */
	/* =========== */
	
	private boolean getYesNo(String message) throws Exception {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String input = "";
		int timeoutCounter = 0;
		
		while (true) {
			if (timeoutCounter > 10000)
				throw new Exception("Request timed out!");
			timeoutCounter += 1;
			
			System.out.println(message + " (Y/N)");
			input = scanner.nextLine();
			
			switch (input) {
				case "Y":
				case "y":
					return true;
				case "N":
				case "n":
					return false;
				default:
					System.out.println("Invalid Input! Only 'Y', 'N', 'y', 'n' are allowed!");
			}
		}
	}
}
