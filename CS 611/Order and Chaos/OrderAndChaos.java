import java.util.Scanner;

public class OrderAndChaos extends Game {
	private int order;
	private int chaos;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public OrderAndChaos(String gameName) {
		super(gameName, new Board(6, new char[] { 'X', 'O' }));

		this.setMessages();

		this.printer("welcome");

		this.entitiesSetup();
		this.play();
	}

	public OrderAndChaos(String gameName, boolean teamsAllowed) {
		super(gameName, new Board(6, new char[] { 'X', 'O' }), teamsAllowed);

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
		e1.addPiece('X');
		e2.addPiece('X');
		e1.addPiece('O');
		e2.addPiece('O');

		this.addEntity(e1);
		this.addEntity(e2);

		this.pickChaosOrder();
	}

	private void setMessages() {
		String welcome = "Welcome to Order & Chaos!";

		String instructions = "We are about to begin, each player will take a turn placing a symbol. \n"
				+ "To place a symbol you'll be prompted to enter an integer between 1 and 36, each represent a square on the board: \n"
				+ "+--+--+--+--+--+--+ \n" + "|1 |2 |3 |4 |5 |6 | \n" + "+--+--+--+--+--+--+ \n"
				+ "|7 |8 |9 |10|11|12| \n" + "+--+--+--+--+--+--+ \n" + "|13|14|15|16|17|18| \n"
				+ "+--+--+--+--+--+--+ \n" + "|19|20|21|22|23|24| \n" + "+--+--+--+--+--+--+ \n"
				+ "|25|26|27|28|29|30| \n" + "+--+--+--+--+--+--+ \n" + "|31|32|33|34|35|36| \n"
				+ "+--+--+--+--+--+--+ \n" + "\n"
				+ "After entering the integer, you'll be prompted to pick between 'O' and 'X'"
				+ " whichever symbol you ender will be put into the spot sellected before.";

		String goodbye = "Thank you for playing Order & Chaos \n" + "\n" + "============================= \n"
				+ "A game by Victor Vicente \n" + "Made for BU CS611, Spring '21 \n" + "=============================";

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
				this.won(this.getEntity(this.getOrder()));
				break;
			}

			if (piecesPlaced == 32) {
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
		char symbol;

		while (true) {
			System.out.println(entity.getName() + " please select a placeable square:");
			placement = scanner.nextInt();

			System.out.println();
			System.out.println(entity.getName() + " please select a symbol:");
			symbol = scanner.next().charAt(0);

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

		if ((y == 0 || y == 1 || y == 2 || y == 3 || y == 4) && ((currentBoard[x][0].equals(currentBoard[x][1]))
				&& (currentBoard[x][1].equals(currentBoard[x][2])) && (currentBoard[x][2].equals(currentBoard[x][3]))
				&& (currentBoard[x][3].equals(currentBoard[x][4]))))
			return true;
		if ((y == 5 || y == 1 || y == 2 || y == 3 || y == 4) && ((currentBoard[x][1].equals(currentBoard[x][2]))
				&& (currentBoard[x][2].equals(currentBoard[x][3])) && (currentBoard[x][3].equals(currentBoard[x][4]))
				&& (currentBoard[x][4].equals(currentBoard[x][5]))))
			return true;
		if ((x == 0 || x == 1 || x == 2 || x == 3 || x == 4) && ((currentBoard[0][y].equals(currentBoard[1][y]))
				&& (currentBoard[1][y].equals(currentBoard[2][y])) && (currentBoard[2][y].equals(currentBoard[3][y]))
				&& (currentBoard[3][y].equals(currentBoard[4][y]))))
			return true;
		if ((x == 5 || x == 1 || x == 2 || x == 3 || x == 4) && ((currentBoard[1][y].equals(currentBoard[2][y]))
				&& (currentBoard[2][y].equals(currentBoard[3][y])) && (currentBoard[3][y].equals(currentBoard[4][y]))
				&& (currentBoard[4][y].equals(currentBoard[5][y]))))
			return true;

		if (x == y) {
			if ((x == 0 || x == 1 || x == 2 || x == 3 || x == 4) && ((currentBoard[0][0].equals(currentBoard[1][1]))
					&& (currentBoard[1][1].equals(currentBoard[2][2]))
					&& (currentBoard[2][2].equals(currentBoard[3][3]))
					&& (currentBoard[3][3].equals(currentBoard[4][4]))))
				return true;
			if ((x == 1 || x == 2 || x == 3 || x == 4 || x == 5) && ((currentBoard[1][1].equals(currentBoard[2][2]))
					&& (currentBoard[2][2].equals(currentBoard[3][3]))
					&& (currentBoard[3][3].equals(currentBoard[4][4]))
					&& (currentBoard[4][4].equals(currentBoard[5][5]))))
				return true;
		}

		if ((x + y) == 5) {
			if ((x == 0 || x == 1 || x == 2 || x == 3 || x == 4) && ((currentBoard[0][5].equals(currentBoard[1][4]))
					&& (currentBoard[1][4].equals(currentBoard[2][3]))
					&& (currentBoard[2][3].equals(currentBoard[3][2]))
					&& (currentBoard[3][2].equals(currentBoard[4][1]))))
				return true;
			if ((x == 1 || x == 2 || x == 3 || x == 4 || x == 5) && ((currentBoard[1][4].equals(currentBoard[2][3]))
					&& (currentBoard[2][3].equals(currentBoard[3][4]))
					&& (currentBoard[3][4].equals(currentBoard[4][1]))
					&& (currentBoard[5][0].equals(currentBoard[5][0]))))
				return true;
		}

		if ((x + 1 == y) && ((currentBoard[0][1].equals(currentBoard[1][2]))
				&& (currentBoard[1][2].equals(currentBoard[2][3])) && (currentBoard[2][3].equals(currentBoard[3][4]))
				&& (currentBoard[3][4].equals(currentBoard[4][5]))))
			return true;

		if ((y + 1 == x) && ((currentBoard[1][0].equals(currentBoard[2][1]))
				&& (currentBoard[2][1].equals(currentBoard[3][2])) && (currentBoard[3][2].equals(currentBoard[4][3]))
				&& (currentBoard[4][3].equals(currentBoard[5][4]))))
			return true;

		if ((x + y == 4) && ((currentBoard[4][0].equals(currentBoard[3][1]))
				&& (currentBoard[3][1].equals(currentBoard[2][2])) && (currentBoard[2][2].equals(currentBoard[1][3]))
				&& (currentBoard[1][3].equals(currentBoard[0][4]))))
			return true;

		if ((x + y == 6) && ((currentBoard[5][1].equals(currentBoard[4][2]))
				&& (currentBoard[4][2].equals(currentBoard[3][3])) && (currentBoard[3][3].equals(currentBoard[2][4]))
				&& (currentBoard[2][4].equals(currentBoard[1][5]))))
			return true;

		return false;
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public void setOrder(int entityID) {
		Entity e = this.getEntity(entityID);
		if (e == null) {
			System.out.println("Invalid Entity ID! Entity not in game.");
			return;
		}

		this.order = entityID;
	}

	public void setChaos(int entityID) {
		Entity e = this.getEntity(entityID);
		if (e == null) {
			System.out.println("Invalid Entity ID! Entity not in game.");
			return;
		}

		this.chaos = entityID;
	}

	public int getOrder() {
		return this.order;
	}

	public int getChaos() {
		return this.chaos;
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

	private void pickChaosOrder() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		int o = 1;
		int c = 2;

		boolean selectOwnRole = false;
		try {
			selectOwnRole = this.getYesNo("Would you like to select your own role?");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		System.out.println();

		if (selectOwnRole) {
			System.out.println("Please enter the role for " + this.getEntity(1).getName() + ": ('O'/'C')");
			String p1Symbol = scanner.nextLine();

			switch (p1Symbol) {
			case "O":
			case "o":
			case "order":
			case "Order":
				break;
			case "C":
			case "c":
			case "chaos":
			case "Chaos":
				o = 2;
				c = 1;
				break;
			default:
				throw new IllegalArgumentException("Invalid Role Char! Only roles allowed are 'C'/'O'");
			}
		} else {
			if (Math.random() > 0.5) {
				o = 2;
				c = 1;
			}
		}

		this.setOrder(o);
		this.setChaos(c);
	}

}
