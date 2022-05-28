/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Util.Abstraction;

import Util.Printer;
import Util.State;

public abstract class Game {

	private State status;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Game() {
		Printer.printSetMessage("welcome");
		
		initPlayers();
		status = State.PLAYING;

		while (status == State.PLAYING) {
			playGame();
			updateStatus();
		}

		if (status == State.LOSE)
			Printer.printSetMessage("lost");
		else if (status == State.WIN)
			Printer.printSetMessage("won");
		else
			Printer.printMSG("quit");
		
		Printer.printSetMessage("goodbye");
	}

	/* ================ */
	/* Abstract Methods */
	/* ================ */

	public abstract void initObjects();

	public abstract void initPlayers();

	public abstract void playGame();

	public abstract void updateStatus();

	public abstract void reset();

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public State getStatus() {
		return status;
	}

	public void setStatus(State s) {
		status = s;
	}

}
