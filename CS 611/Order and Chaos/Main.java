import java.util.Scanner;

public class Main {
	
	// Main method that starts whichever game needed
	public static void main(String[] args) {
		
		String game = Main.getGame();
		boolean teams = Main.getTeams();
		
		if(game.equals("TicTacToe")) {
			TicTacToe t = new TicTacToe("TicTacToe", teams);
		} else {
			OrderAndChaos o = new OrderAndChaos("Order & Chaos", teams);
		}
	}
	
	// Prompts user to pick one of the available games to play!
	private static String getGame() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String response = "";
		boolean cont = true;
		
		while(cont) {
			System.out.println();
			System.out.println("Which game would you like to play? ('OrderAndChaos'/'TicTacToe')");
			
			response = scanner.next();
			
			switch(response) {
			case"TicTacToe":
			case"tictactoe":
			case"t":
			case"T":
				response = "TicTacToe";
				cont = false;
				break;
			case"OrderAndChaos":
			case"orderandchaos":
			case"o":
			case"O":
				response = "OrderAndChaos";
				cont = false;
				break;
			default:
				System.out.println("Invalid Input! Only allowed inputs are 'OrderAndChaos'/'TicTacToe'. Retrying!");
				break;
			}
		}
		
		return response;
	}
	
	// Prompts the users to choose whether or not to use Teams while playing.
	private static boolean getTeams() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String input = "";

		while (true) {
			System.out.println();
			System.out.println("Would you like to play this game with Teams?" + " (Y/N)");
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