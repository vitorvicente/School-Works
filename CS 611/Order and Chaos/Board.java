import java.util.ArrayList;
import java.util.Scanner;

public class Board {
	private final int MAXMAX = 100;
	private final int MINMIN = 1;

	private int size;
	private String[][] placements;
	private ArrayList<Character> allowedCharacters;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Board() {
		this.promptBoardDetails();
		this.resetPlacements();
	}

	public Board(int size, ArrayList<Character> allowedCharacters) {
		this.setSize(size);
		this.setAllowedCharacters(allowedCharacters);
		this.resetPlacements();
	}

	public Board(int size, char[] allowedCharacters) {
		this.setSize(size);
		this.setAllowedCharacters(allowedCharacters);
		this.resetPlacements();
	}

	public Board(Board b) {
		this.setSize(b.getSize());
		this.setAllowedCharacters(b.getAllowedCharacters());
		this.resetPlacements();
	}

	/* ============ */
	/* Game Methods */
	/* ============ */

	/*
	 * This method serves as an interface with the user in case no board details
	 * were entered on creation it has two main features: getting board size, and
	 * getting the allowed characters both of which are entirely up to user input
	 */
	private void promptBoardDetails() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		int sizeAnswer = -1;
		String allowedCharAnswer = "";
		this.size = -1;

		System.out.println();
		System.out.println("Entering Board Setup!");
		System.out.println();

		boolean cont = true;
		while (cont) {
			System.out.println();
			System.out.println("Please enter the size of the board you want");
			System.out.println();

			sizeAnswer = scanner.nextInt();
			this.setSize(sizeAnswer);

			if (this.size != -1)
				cont = false;
		}

		cont = true;
		while (cont) {
			System.out.println();
			System.out.println("Please enter a character you want to allowed on this game (or enter 'exit' to leave)");
			System.out.println();

			allowedCharAnswer = scanner.next();

			switch (allowedCharAnswer) {
			case "exit":
				System.out.println("Exiting");
				cont = false;
				break;
			default:
				this.addAllowedCharacter(allowedCharAnswer);
				break;
			}
		}

		System.out.println();
		System.out.println("Leaving board setup!");
		System.out.println();
	}
	
	// Returns the formatted board in a string, this is how it'll be printed
	public String toString() {
		String response = "";
		String divider = "";

		for (int i = 0; i < this.getSize(); i++)
			divider += "+--";
		divider += "+";

		for (int i = 0; i < this.getSize(); i++) {
			response += divider;
			response += "\n";
			for (int j = 0; j < this.getSize(); j++) {
				response += "|" + this.getRawPlacements()[i][j] + " ";
			}
			response += "|\n";
		}
		response += divider;

		return response;
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public int getSize() {
		return this.size;
	}

	// This method checks to make sure the size given is within the absolute limits
	public void setSize(int size) {
		if (size < this.getAbsoluteMinSize()) {
			System.out.println(
					"Board Size Value given is too small, minimum size allowed is " + this.getAbsoluteMinSize());
			return;
		}
		if (size > this.getAbsoluteMaxSize()) {
			System.out.println(
					"Board Size Value given is too large, maximum size allowed is " + this.getAbsoluteMaxSize());
			return;
		}

		this.size = size;
	}

	// Mostly just helpful methods
	public int getAbsoluteMinSize() {
		return this.MINMIN;
	}

	public int getAbsoluteMaxSize() {
		return this.MAXMAX;
	}

	// Return the raw placement array in case it's needed
	public String[][] getRawPlacements() {
		return this.placements;
	}

	// Place a new piece using String
	public boolean placePiece(String piece, int placement) {
		int[] coords = this.getPlacementCoords(placement);

		if (piece.length() > 1) {
			System.out.println("Invalid Piece! Cannot provide strings of length > 1");
			return false;
		}
		
		piece = piece.toUpperCase();

		if (!this.isCharAllowed(piece.charAt(0))) {
			System.out.println("Invalid Piece! Provided piece is not allowed for this board!");
			return false;
		}

		if (!this.isValidPlacement(placement)) {
			System.out.println("Invalid Placement Provided!");
			return false;
		}

		if (!this.getRawPlacements()[coords[0]][coords[1]].equals(" ")) {
			System.out.println("Board Placement provided already occupied!!");
			return false;
		}

		this.placements[coords[0]][coords[1]] = piece.charAt(0) + "";
		
		return true;
	}

	// Place a new piece using char
	public boolean placePiece(char piece, int placement) {
		int[] coords = this.getPlacementCoords(placement);
		
		piece = Character.toUpperCase(piece);

		if (!this.isCharAllowed(piece)) {
			System.out.println("Invalid Piece! Provided piece is not allowed for this board!");
			return false;
		}

		if (!this.isValidPlacement(placement)) {
			System.out.println("Invalid Placement Provided!");
			return false;
		}

		if (!this.getRawPlacements()[coords[0]][coords[1]].equals(" ")) {
			System.out.println("Board Placement provided already occupied!");
			return false;
		}

		this.placements[coords[0]][coords[1]] = piece + "";
		
		return true;
	}

	// Remove a piece via placement
	public void removePiece(int placement) {
		int[] coords = this.getPlacementCoords(placement);

		if (!this.isValidPlacement(placement)) {
			System.out.println("Invalid Placement Provided!");
			return;
		}

		if (this.getRawPlacements()[coords[0]][coords[1]].equals(" ")) {
			System.out.println("Board Placement provided is already empty!");
			return;
		}

		this.placements[coords[0]][coords[1]] = " ";

	}

	// Resets the placements array, also initializes it if needed
	public void resetPlacements() {
		if (this.placements == null) 
			this.placements = new String[this.getSize()][this.getSize()];

		for (int i = 0; i < this.getSize(); i++)
			for (int j = 0; j < this.getSize(); j++)
				this.placements[i][j] = " ";
	}

	// Get allowed characters to be placed on the board
	public ArrayList<Character> getAllowedCharacters() {
		return this.allowedCharacters;
	}

	// Generalized setter method using ArrayList
	public void setAllowedCharacters(ArrayList<Character> allowedCharacters) {
		if (this.getAllowedCharacters() == null)
			this.allowedCharacters = new ArrayList<Character>();

		for (char c : allowedCharacters) {
			this.addAllowedCharacter(Character.toUpperCase(c));
		}
	}

	// Generalized setter method using arrays
	public void setAllowedCharacters(char[] allowedCharacters) {
		if (this.getAllowedCharacters() == null)
			this.allowedCharacters = new ArrayList<Character>();

		for (char c : allowedCharacters) {
			this.addAllowedCharacter(Character.toUpperCase(c));
		}
	}

	// Add allowed character using char
	public void addAllowedCharacter(char piece) {
		if (this.getAllowedCharacters() == null)
			this.allowedCharacters = new ArrayList<Character>();
		
		piece = Character.toUpperCase(piece);

		if (this.isCharAllowed(piece)) {
			System.out.println("Provided Piece is already allowed on this board!");
			return;
		}

		this.allowedCharacters.add(piece);
	}

	// Add allowed character using String
	public void addAllowedCharacter(String piece) {
		if (this.getAllowedCharacters() == null)
			this.allowedCharacters = new ArrayList<Character>();

		if (piece.length() > 1) {
			System.out.println("Invalid Piece! Cannot provide strings of length > 1");
			return;
		}

		piece = piece.toUpperCase();
		
		if (this.isCharAllowed(piece.charAt(0))) {
			System.out.println("Provided Piece is already allowed on this board!");
			return;
		}

		this.allowedCharacters.add(piece.charAt(0));
	}

	// Remove allowed character using char
	public void removeAllowedCharacter(char piece) {
		if (this.getAllowedCharacters() == null || this.getAllowedCharacters().isEmpty()) {
			System.out.println("Allowed Character Array Empty!");
			return;
		}

		piece = Character.toUpperCase(piece);
		
		if (!this.isCharAllowed(piece)) {
			System.out.println("Provided Piece is already not allowed on this board!");
			return;
		}

		this.allowedCharacters.add(piece);
	}

	// Remove allowed character using String
	public void removeAllowedCharacter(String piece) {
		if (this.getAllowedCharacters() == null || this.getAllowedCharacters().isEmpty()) {
			System.out.println("Allowed Character Array Empty!");
			return;
		}

		if (piece.length() > 1) {
			System.out.println("Invalid Piece! Cannot provide strings of length > 1");
			return;
		}
		
		piece = piece.toUpperCase();

		if (this.isCharAllowed(piece.charAt(0))) {
			System.out.println("Provided Piece is already not allowed on this board!");
			return;
		}

		this.allowedCharacters.add(piece.charAt(0));
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	/*
	 * Both of these methods are to assist with adding/removing Entities, they also
	 * implement some custom implementation of methods that technically already
	 * exist, but could fail in-use for these cases, so I just coded my own.
	 */
	private boolean isCharAllowed(char c) {
		if (this.indexOfChar(c) == -1)
			return false;
		else
			return true;
	}

	private int indexOfChar(char c) {
		if (this.getAllowedCharacters() == null || this.getAllowedCharacters().isEmpty()) {
			return -1;
		}

		int index = 0;

		for (Character cc : this.getAllowedCharacters()) {
			if (cc.equals(c)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	// Helper method to get the placement coords in accordance to basic math
	public int[] getPlacementCoords(int placement) {
		int newPlacement = placement - 1;
		int x = newPlacement / this.getSize();
		int y = newPlacement % this.getSize();

		return new int[] { x, y };
	}

	private boolean isValidPlacement(int placement) {
		int[] coords = this.getPlacementCoords(placement);
		return !(placement < 1) || !(coords[0] >= this.getSize() || coords[1] >= this.getSize());
	}
}
