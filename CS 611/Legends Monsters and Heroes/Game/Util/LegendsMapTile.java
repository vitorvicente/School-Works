/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Game.Util;

import Game.LegendsHero;
import Util.Entity;

public abstract class LegendsMapTile {

	private int ID;
	private int xCoord;
	private int yCoord;

	private boolean playerQuit;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsMapTile(int ID, int xCoord, int yCoord) {
		this.setID(ID);
		this.setXCoord(xCoord);
		this.setYCoord(yCoord);
		this.setPlayerQuit(false);
	}

	/* ================ */
	/* Abstract Methods */
	/* ================ */

	public abstract void onEntry(LegendsGameEntities entities);

	public abstract void onExit(LegendsGameEntities entities);

	public abstract String getNextMove(Entity e, LegendsHero hero);

	public abstract void preformMove(Entity e, LegendsHero hero, String move);

	public abstract void quit();

	public abstract void displayInfo();

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	private void setID(int ID) {
		this.ID = ID;
	}

	public int getID() {
		return this.ID;
	}

	public void setXCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	public int getXCoord() {
		return this.xCoord;
	}

	public void setYCoord(int yCoord) {
		this.yCoord = yCoord;
	}

	public int getYCoord() {
		return this.yCoord;
	}

	public boolean getPlayerQuit() {
		return playerQuit;
	}

	public void setPlayerQuit(boolean playerQuit) {
		this.playerQuit = playerQuit;
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	public void printer(String message) {
		System.out.println();
		System.out.println(message);
		System.out.println();
	}
}
