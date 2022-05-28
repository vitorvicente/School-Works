/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Game;

import java.util.ArrayList;

import Game.Creation.MapCreation;
import Game.Util.LegendsMapTile;
import Tiles.Inaccessible;
import Util.Random;

public class LegendsMap {

	// Configurable percentages of tiles to generate
	public static final double PERCENTAGE_OF_COMMON = 0.5;
	public static final double PERCENTAGE_OF_MARKET = 0.3;
	public static final double PERCENTAGE_OF_INACCESSIBLE = 0.2;

	private LegendsMapTile[][] map;
	private int size;
	private LegendsMapTile currentCell;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsMap(int size) {
		this.initializeBoard(size);
		this.constructMap();
	}

	/* ============ */
	/* Game Methods */
	/* ============ */

	private void initializeBoard(int size) {
		this.setSize(size);
		this.map = new LegendsMapTile[size][size];
	}

	private void constructMap() {
		ArrayList<LegendsMapTile> cells = MapCreation.generateCells(this.size * this.size);

		this.setCurrentCell(null);

		for (int i = 0; i < this.size; i++)
			for (int j = 0; j < size; j++) {
				LegendsMapTile randomCell = cells.remove(Random.randomInt(0, cells.size() - 1));
				randomCell.setXCoord(i);
				randomCell.setYCoord(j);
				this.setCell(i, j, randomCell);
			}

		for (int i = 0; i < this.size; i++) {
			for (int j = 0; i < this.size; j++)
				if (!(this.getCell(i, j) instanceof Inaccessible)) {
					this.setCurrentCell(this.getCell(i, j));
					break;
				}
			if (this.getCurrentCell() != null)
				break;
		}
	}

	public void shuffleCell(int x, int y) {
		this.setCell(x, y, MapCreation.generateCells(1).get(0));
	}

	public boolean move(String movement) {
		int toMoveX = this.getCurrentCell().getXCoord();
		int toMoveY = this.getCurrentCell().getYCoord();

		switch (movement) {
		case "W":
			toMoveX--;
			break;
		case "S":
			toMoveX++;
			break;
		case "A":
			toMoveY--;
			break;
		case "D":
			toMoveY++;
			break;
		}

		if (toMoveX < 0 || toMoveX >= this.getSize() || toMoveY < 0 || toMoveY >= this.getSize())
			return false;
		else if (this.getCell(toMoveX, toMoveY) instanceof Inaccessible)
			return false;

		this.setCurrentCell(this.getCell(toMoveX, toMoveY));
		return true;
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public int getSize() {
		return size;
	}

	private void setSize(int size) {
		this.size = size;
	}

	private void setCell(int x, int y, LegendsMapTile tile) {
		this.map[x][y] = tile;
	}

	public LegendsMapTile[][] getMap() {
		return this.map;
	}

	public LegendsMapTile getCell(int x, int y) {
		return this.map[x][y];
	}

	public void setCurrentCell(LegendsMapTile cell) {
		this.currentCell = cell;
	}

	public LegendsMapTile getCurrentCell() {
		return this.currentCell;
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	public String toString() {
		String response = "";
		String divider = "";

		int currentX = this.getCurrentCell().getXCoord();
		int currentY = this.getCurrentCell().getYCoord();

		for (int i = 0; i < this.getSize(); i++)
			divider += "+---";
		divider += "+";

		for (int i = 0; i < this.getSize(); i++) {
			response += divider;
			response += "\n";
			for (int j = 0; j < this.getSize(); j++) {
				if (i == currentX && j == currentY)
					response += "| X ";
				else
					response += "| " + this.getCell(i, j) + " ";
			}
			response += "|\n";
		}
		response += divider;

		return response;
	}

}
