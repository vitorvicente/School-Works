/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Game;

import java.util.*;

import Entities.LegendsHero;
import Entities.LegendsPlayer;
import Map.LegendsMap;
import Util.Printer;
import Util.State;
import Util.Abstraction.Game;

public abstract class RPG extends Game {

	private LegendsMap map;
	private LegendsPlayer player;

	private static Scanner in = new Scanner(System.in);

	/* ================ */
	/* Abstract Methods */
	/* ================ */

	public abstract void initObjects();

	public abstract void printActions(LegendsHero currHero);

	public abstract void updateStatus();

	public abstract void processUserInput();

	/* ============ */
	/* Game Methods */
	/* ============ */

	public void playGame() {
		processUserInput();
	}

	public void initPlayers() {
		setPlayer(new LegendsPlayer(1));
		initObjects();
		Printer.printSetMessage("instructions");
	}

	// Prompts user to confirm that they want to quit the game, and quits
	public void quit() {
		Printer.printMSG("Are you sure you want to quit? Press Q to confirm, press any other key to continue playing.");
		String quitting = in.nextLine();
		if (quitting.equalsIgnoreCase("Q")) {
			setStatus(State.QUIT);

		}
	}

	// Resets the Game
	public void reset() {
		initPlayers();
		updateStatus();
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public void setMap(LegendsMap map) {
		this.map = map;
	}

	public LegendsMap getMap() {
		return map;
	}

	public LegendsPlayer getPlayer() {
		return player;
	}

	public void setPlayer(LegendsPlayer player) {
		this.player = player;
	}

}