/*=====================================================*/
/* Project Title: Tic Tac Toe                          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/



public class Board {
	private final int SIZE = 3;
	private String[][] placements;
	
	public Board() {
		this.placements = new String[SIZE][SIZE];
		for (int i = 0; i < placements.length; i++)
			for (int j = 0; j < placements.length; j++)
				placements[i][j] = " ";
		
		this.printBoard();
	}
	
	public boolean placeChar(char symbol, int placement) {
		if (symbol != 'X' && symbol != 'O')
			throw new IllegalArgumentException("Invalid Symbol Char! Only symbols allowed are 'X' and 'O'.");
		
		int[] pos = this.getGridPositons(placement);
		
		if (this.placements[pos[0]][pos[1]] != " ")
			return false;
		
		this.placements[pos[0]][pos[1]] = symbol + "";
		
		this.printBoard();
		
		return true;
	}
	
	public boolean checkWin(int placement) {
		int[] pos = this.getGridPositons(placement);
		int x = pos[0];
		int y = pos[1];
		
		if (this.placements[x][0].equals(this.placements[x][1]) && this.placements[x][0].equals(this.placements[x][2]))
			return true;
		if (this.placements[0][y].equals(this.placements[1][y]) && this.placements[0][y].equals(this.placements[2][y]))
			return true;
		if ((x == y) && (this.placements[0][0].equals(this.placements[1][1]) && this.placements[0][0].equals(this.placements[2][2])))
			return true;
		if (((x+y) == 2) && (this.placements[0][2].equals(this.placements[1][1]) && this.placements[0][2].equals(this.placements[2][0])))
			return true;
		
		return false;
	}
	
	private void printBoard() {
		System.out.println();
		System.out.println("Current Board:");
		System.out.println("+--+--+--+");
		System.out.println("|" + this.placements[0][0] + " |" + this.placements[0][1] + " |" + this.placements[0][2] + " |");
		System.out.println("+--+--+--+");
		System.out.println("|" + this.placements[1][0] + " |" + this.placements[1][1] + " |" + this.placements[1][2] + " |");
		System.out.println("+--+--+--+");
		System.out.println("|" + this.placements[2][0] + " |" + this.placements[2][1] + " |" + this.placements[2][2] + " |");
		System.out.println("+--+--+--+");
		System.out.println();
	}
	
	private int[] getGridPositons(int placement) {
		switch (placement) {
			case 1:
				return new int[] {0,0};
			case 2:
				return new int[] {0,1};
			case 3:
				return new int[] {0,2};
			case 4:
				return new int[] {1,0};
			case 5:
				return new int[] {1,1};
			case 6:
				return new int[] {1,2};
			case 7:
				return new int[] {2,0};
			case 8:
				return new int[] {2,1};
			case 9:
				return new int[] {2,2};
			default:
				throw new IllegalArgumentException("Invalid Board Placement! Please pick a number between 1 and 9");
		}
	}

}
