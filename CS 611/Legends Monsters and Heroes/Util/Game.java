/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Util;

import java.util.Iterator;
import java.util.Scanner;

public abstract class Game {
	
	private String gameName;

	private String instructions;
	private String welcomeMSG;
	private String goodbyeMSG;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Game(String gameName) {
		this.setName(gameName);
	}

	public Game(String gameName, int o) {
		this.setName(gameName);
	}

	/* ============ */
	/* Game Methods */
	/* ============ */

	public abstract void setupGameEntities();

	public abstract void play();

	public void won(Entity t) {
		Entity winningEntity = null;

		Iterator<Entity> itr = this.getGameEntities().iterator();
		while (itr.hasNext()) {
			Entity e = itr.next();

			if (e.equals(t)) {
				e.addWin();
				winningEntity = e;
			} else {
				e.addLoss();
			}
		}

		winningEntity.announceWin();

		this.printer("scores");
	}

	public void staleMate() {
		System.out.println();
		System.out.println("A Stale Mate has been reached in the game.");
		System.out.println();

		Iterator<Entity> itr = this.getGameEntities().iterator();
		while (itr.hasNext()) {
			itr.next().addDraw();
		}

		this.printer("scores");
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	// Abstract getter for the GameEntities object
	public abstract GameEntities getGameEntities();

	// Abstract setter for the GameEntities object
	public abstract void setGameEntities(GameEntities g);

	// Name setters/getters
	public String getName() {
		return this.gameName;
	}

	public void setName(String gameName) {
		this.gameName = gameName;
	}

	// Messages setters/getters
	public String getWelcomeMSG() {
		return this.welcomeMSG;
	}

	public void setWelcomeMSG(String welcomeMSG) {
		this.welcomeMSG = welcomeMSG;
	}

	public String getInstructions() {
		return this.instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getGoodbyeMSG() {
		return this.goodbyeMSG;
	}

	public void setGoodbyeMSG(String goodbyeMSG) {
		this.goodbyeMSG = goodbyeMSG;
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	public boolean getYesNo(String message) throws Exception {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String input = "";
		int timeoutCounter = 0;

		while (true) {
			if (timeoutCounter > 10000)
				throw new Exception("Request timed out!");
			timeoutCounter += 1;

			System.out.println();
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

	public void printer(String printGoal) {
		System.out.println();

		switch (printGoal) {
		case "welcome":
			System.out.println(this.getWelcomeMSG());
			break;
		case "goodbye":
			System.out.println(this.getGoodbyeMSG());
			break;
		case "instructions":
			System.out.println(this.getInstructions());
			break;
		case "scores":
			System.out.println("Current Scores:");
			Iterator<Entity> itr = this.getGameEntities().iterator();
			while (itr.hasNext())
				System.out.println(itr.next().getScores());
			break;
		default:
			System.out.println(printGoal);
			break;
		}

		System.out.println();
	}
}