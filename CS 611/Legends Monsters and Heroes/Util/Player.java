/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Util;

import java.util.Scanner;

public class Player extends Entity {

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	/*
	 * There are _a few_ of constructors here, and although I'll likely only use 1
	 * or 2, the reason why I added all of them is because I think it'd be a
	 * perfectly reasonable assumption that this class could be "constructed" using
	 * any individual one of these.
	 */

	public Player(int ID) {
		super(ID, "Player #" + ID);
		String name = this.getInitialName();
		this.setName(name);
	}

	public Player(int ID, String name) {
		super(ID, name);
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	/*
	 * This method is here to allow for a custom name to be inputed by the player in
	 * case non was previously provided. I decided to do this for Players and not
	 * for Teams because I feel like it's nice to have some level of customization
	 * over players, where on teams it is not that necessary.
	 */

	private String getInitialName() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String answer = "";
		String yN = "";
		boolean cont = true;
		boolean contTwo = true;

		while (cont) {
			contTwo = true;
			System.out.println();
			System.out.println("Please enter the name for " + this.getName() + ":");

			answer = input.nextLine();

			while (contTwo) {
				System.out.println();
				System.out.println("Entered name: " + answer);
				System.out.println("Are you happy with this name? (Y/N)");

				yN = input.nextLine();

				switch (yN) {
				case "Y":
				case "y":
					cont = false;
					contTwo = false;
					break;
				case "N":
				case "n":
					System.out.println("Prompting for name again...");
					contTwo = false;
					break;
				default:
					System.out.println("Invalid Input!! Only inputs allowed are: 'Y'/'y'/'N'/'n'");
					break;
				}
			}
		}

		return answer;
	}
}