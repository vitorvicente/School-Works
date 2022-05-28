/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Tiles;

import Game.LegendsHero;
import Game.Util.LegendsGameEntities;
import Game.Util.LegendsMapTile;
import Util.Entity;

public class Inaccessible extends LegendsMapTile {

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Inaccessible(int ID, int xCoord, int yCoord) {
		super(ID, xCoord, yCoord);
	}

	/* ============ */
	/* Game Methods */
	/* ============ */

	/*
	 * Due to the nature of this tile, all these methods are unused, but regardless
	 * must be here due to the use of the abstract class.
	 */

	@Override
	public void onEntry(LegendsGameEntities entities) {
		// NA
	}

	@Override
	public void onExit(LegendsGameEntities entities) {
		// NA
	}

	@Override
	public String getNextMove(Entity e, LegendsHero hero) {
		// NA
		return null;
	}

	@Override
	public void preformMove(Entity e, LegendsHero hero, String move) {
		// NA
	}

	@Override
	public void quit() {
		// NA
	}

	@Override
	public void displayInfo() {
		// NA
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	public String toString() {
		return "I";
	}
}
