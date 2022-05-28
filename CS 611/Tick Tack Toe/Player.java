/*=====================================================*/
/* Project Title: Tic Tac Toe                          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/



public class Player {
	private String name;
	private char symbol;
	private int wins;
	private int losses;
	
	public Player(String name) {
		this.setName(name);
		this.resetPoints();
	}
	
	public Player(String name, char symbol) {
		this.setName(name);
		this.resetPoints();
		this.setSymbol(symbol);
	}
	
	public void setName(String name) {
		if (name.length() > 16)
			throw new IllegalArgumentException("Player name too long! Names only allowed up to 16 characters!");
	}
	
	
	public void setSymbol(char symbol) {
		if (symbol != 'X' && symbol != 'O')
			throw new IllegalArgumentException("Invalid Symbol Char! Only symbols allowed are 'X' and 'O'.");
		
		this.symbol = symbol;
	}
	
	public void resetPoints() {
		this.losses = 0;
		this.wins = 0;
	}
	
	public void addWin() {
		this.wins += 1;
	}
	
	public void addLoss() {
		this.losses += 1;
	}
	
	public String getName() { return this.name; }
	public char getSymbol() { return this.symbol; }
	public int getWins() { return this.wins; }
	public int getLosses() { return this.losses; }
}
