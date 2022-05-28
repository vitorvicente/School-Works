/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Util;

public class Printer {

	/*
	 * Prints a given message with padding
	 */
	public static void printMSG(String msg) {
		System.out.println();
		System.out.println(msg);
		System.out.println();
	}

	/*
	 * Prints a set default message when given a token, this allows for greater
	 * Flexibility, ease of use, and consistency across the game
	 */
	public static void printSetMessage(String code) {
		String msg = "";

		switch (code) {
		case "invalidMove":
			msg = "You can't reach this location! Try moving elsewhere.";
			break;
		case "invalidResponse":
			msg = "Invalid response! Trying again!";
			break;
		case "invalidClass":
			msg = "Invalid Entity Class Type!";
			break;
		case "invalidStats":
			msg = "Invalid Entity Stats Type!";
			break;
		case "invalidArmourType":
			msg = "Invalid Armour Type Provided!";
			break;
		case "invalidAbility":
			msg = "Invalid Ability Provided!";
			break;
		case "numberTooSmall":
			msg = "Entered number is too small!";
			break;
		case "numberTooLarge":
			msg = "Entered number is too large!";
			break;
		case "illegalItemChoice":
			msg = "You cannot use this item here!";
			break;
		case "won":
			msg = "Congratulations, you have won the game!";
			break;
		case "lost":
			msg = "Mission Failed! " + "\n" + "You'll get them next time!";
			break;
		case "quit":
			msg = "You have quit the game!" + "\n" + "All progress will be lost";
			break;
		case "goodbye":
			break;
		case "welcome":
			msg = "==================================================================== \n" + "Welcome to Legends Of Valor! \n"
					+ "A game by Jack Giunta, Victoria Burke, and Vitor Vicente \n"
					+ "====================================================================";
			break;
		case "instructions":
			msg = "Instructions to come!";
			break;
		}

		if (!msg.equals(""))
			Printer.printMSG(msg);

	}

	/*
	 * Prints a helper line for columns with a specified length
	 */
	public static void printHelperLine(int amount) {
		System.out.print("+");
		for (int i = 0; i < amount; i++) {
			System.out.print("-");
		}
		System.out.println("+");

	}

}
