import java.util.Scanner;

public class TicTacToe extends Game {

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public TicTacToe(String gameName) {
		super(gameName, new Board(3, new char[] { 'X', 'O' }));
		
		this.setMessages();

		this.printer("welcome");

		this.entitiesSetup();
		this.play();
	}

	public TicTacToe(String gameName, boolean teamsAllowed) {
		super(gameName, new Board(3, new char[] { 'X', 'O' }), teamsAllowed);
		
		this.setMessages();

		this.printer("welcome");
		
		this.entitiesSetup();
		this.play();
	}

	/* ============ */
	/* Game Methods */
	/* ============ */

	/*
	 * This method is inherited from the Game class, and does the necessary setup
	 * required to play TicTacToe, including the creation of teams/players, and the
	 * attribution of their symbols.
	 */
	@Override
	protected void entitiesSetup() {
		this.setMaxEntities(2);
		this.setMaxPlayersPerTeam(100);

		Entity e1 = null;
		Entity e2 = null;

		if (this.getTeamsAllowed()) {
			e1 = new Team(1);
			e2 = new Team(2);
		} else {
			e1 = new Player(1);
			e2 = new Player(2);
		}

		char[] symbols = this.setSymbols(e1.getName());

		e1.addPiece(symbols[0]);
		e2.addPiece(symbols[1]);

		System.out.println();
		System.out.println(e1.getName() + " Symbol: " + symbols[0]);
		System.out.println(e2.getName() + " Symbol: " + symbols[1]);
		System.out.println();

		this.addEntity(e1);
		this.addEntity(e2);
	}
	
	private void setMessages() {
		String welcome = "Welcome to TicTacToe!";
		
		String instructions = "We are about to begin, each player will take a turn placing a symbol. \n"
				+ "To place a symbol you'll be prompted to enter an integer between 1 and 9, each represent a square on the board: \n"
				+ "+--+--+--+ \n"
				+ "|1 |2 |3 | \n"
				+ "+--+--+--+ \n"
				+ "|4 |5 |6 | \n"
				+ "+--+--+--+ \n"
				+ "|7 |8 |9 | \n"
				+ "+--+--+--+";
		
		String goodbye = "Thank you for playing Tic Tac Toe! \n"
				+ "\n"
				+ "============================= \n"
				+ "A game by Victor Vicente \n"
				+ "Made for BU CS611, Spring '21 \n"
				+ "=============================";
		
		this.setWelcomeMSG(welcome);
		this.setInstructions(instructions);
		this.setGoodbyeMSG(goodbye);
	}

	/*
	 * This is the main "play" method given from the Game class, in this
	 * implementation the method first calls the innerGame method (which does the
	 * heavy work), and awaits for the game to be over. Once it is, it'll prompt the
	 * players for whether or not they want to play another game.
	 */
	@Override
	protected void play() {
		this.printer("instructions");

		while (true) {
			this.innerGame();

			boolean again = false;
			try {
				again = this.getYesNo("Would you like to play again?");
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (!again)
				break;
			else
				this.getBoard().resetPlacements();
		}

		this.printer("goodbye");
	}

	/*
	 * Main inner game method that cycles through the two players letting each of
	 * them play, and then checking for a winner. The method also include a
	 * provision for when all the squares are full and so a stale mate has been
	 * reached.
	 */
	private void innerGame() {
		int currentPlayer = 1;
		Entity currentEntity = null;
		int piecesPlaced = 0;
		this.printBoard();

		while (true) {
			currentEntity = this.getEntity(currentPlayer);

			int move = this.playerMove(currentEntity);
			piecesPlaced++;

			boolean won = this.checkWin(move);
			if (won) {
				this.won(currentEntity);
				break;
			}

			if (piecesPlaced == 9) {
				this.staleMate();
				break;
			}

			if (currentPlayer == 1)
				currentPlayer = 2;
			else
				currentPlayer = 1;
		}
	}

	/*
	 * This method prompts whatever entity is having its turn to enter a placeable
	 * square, and then proceeds to place the Entity's symbol on that square
	 */
	private int playerMove(Entity entity) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		int placement = -1;
		char symbol = entity.getPieces().get(0);

		while (true) {
			System.out.println(entity.getName() + " please select a placeable square:");
			placement = scanner.nextInt();

			boolean placed = this.getBoard().placePiece(symbol, placement);

			if (placed) {
				this.printBoard();
				return placement;
			} else
				System.out.println("Re-trying! \n");
		}
	}

	/*
	 * The checkWin method follows basic rules of TicTacToe, checking both
	 * horizontal and vertical as well as diagonal if needed returning true if the
	 * most recent placement resulted in a win or false if it didnt.
	 */
	private boolean checkWin(int placement) {
		int[] pos = this.getBoard().getPlacementCoords(placement);
		String[][] currentBoard = this.getBoard().getRawPlacements();
		int x = pos[0];
		int y = pos[1];

		if ((currentBoard[x][0].equals(currentBoard[x][1])) && (currentBoard[x][1].equals(currentBoard[x][2])))
			return true;
		if ((currentBoard[0][y].equals(currentBoard[1][y])) && (currentBoard[1][y].equals(currentBoard[2][y])))
			return true;
		if ((x == y)
				&& ((currentBoard[0][0].equals(currentBoard[1][1])) && (currentBoard[1][1].equals(currentBoard[2][2]))))
			return true;
		if (((x + y) == 2)
				&& ((currentBoard[0][2].equals(currentBoard[1][1])) && (currentBoard[1][1].equals(currentBoard[2][0]))))
			return true;

		return false;
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	// Simple method to print the Game Board in a more styled way.
	private void printBoard() {
		System.out.println();
		System.out.println("Current Board");
		System.out.println(this.getBoard());
		System.out.println();
	}

	// This method will prompt users to whether or not they would like to select
	// their own piece, or have one attributed to it randomly
	private char[] setSymbols(String p1Name) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		char c1 = 'X';
		char c2 = 'O';

		boolean selectOwnSymbols = false;
		try {
			selectOwnSymbols = this.getYesNo("Would you like to select your own symbols?");
		} catch (Exception e) {
			e.printStackTrace();
			return new char[] { c1, c2 };
		}

		if (selectOwnSymbols) {
			System.out.println("Please enter the symbol for " + p1Name + ": ('X'/'O')");
			String p1Symbol = scanner.nextLine();

			switch (p1Symbol) {
			case "X":
				break;
			case "O":
				c1 = 'O';
				c2 = 'X';
				break;
			default:
				throw new IllegalArgumentException("Invalid Symbol Char! Only symbols allowed are 'X' and 'O'.");
			}
		} else {
			if (Math.random() > 0.5) {
				c1 = 'O';
				c2 = 'X';
			}
		}

		return new char[] { c1, c2 };
	}

}
