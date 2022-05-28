/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Game.Creation;

import java.util.ArrayList;

import Game.LegendsGame;
import Game.LegendsMap;
import Game.Util.LegendsMapTile;
import Tiles.Common;
import Tiles.Inaccessible;
import Tiles.Marketplace;

public class MapCreation {

	/*
	 * Static Method to generate a certain amount of cells provided, splitting them
	 * into the different types by the percentage set in LegendsMap.
	 * 
	 * There is some math here to assure that the number of cells generated is
	 * EXACTLY the number requested, so no errors occur.
	 * 
	 * After the math is done, three specific methods to create lists of each Tile
	 * Type are called, and all these lists are added up and returned.
	 */
	public static ArrayList<LegendsMapTile> generateCells(int amountToGenerate) {
		int amountOfCommon = (int) Math.round(amountToGenerate * LegendsMap.PERCENTAGE_OF_COMMON);
		int amountOfMarket = (int) Math.round(amountToGenerate * LegendsMap.PERCENTAGE_OF_MARKET);
		int amountOfInaccessible = (int) Math.round(amountToGenerate * LegendsMap.PERCENTAGE_OF_INACCESSIBLE);

		int checker = 0;
		while (amountOfCommon + amountOfMarket + amountOfInaccessible > amountToGenerate) {
			if (checker == 0) {
				amountOfCommon--;
				checker = 1;
			} else if (checker == 1) {
				amountOfMarket--;
				checker = 2;
			} else {
				amountOfInaccessible--;
				checker = 0;
			}
		}

		checker = 0;
		while (amountOfCommon + amountOfMarket + amountOfInaccessible < amountToGenerate) {
			if (checker == 0) {
				amountOfCommon++;
				checker = 1;
			} else if (checker == 1) {
				amountOfMarket++;
				checker = 2;
			} else {
				amountOfInaccessible++;
				checker = 0;
			}
		}

		ArrayList<LegendsMapTile> cells = new ArrayList<LegendsMapTile>();
		cells.addAll(MapCreation.generateCommon(amountOfCommon));
		cells.addAll(MapCreation.generateMarket(amountOfMarket));
		cells.addAll(MapCreation.generateInaccessible(amountOfInaccessible));

		return cells;
	}

	// Generate a list of Common Tiles
	private static ArrayList<LegendsMapTile> generateCommon(int amountToGenerate) {
		ArrayList<LegendsMapTile> cells = new ArrayList<LegendsMapTile>();

		for (int i = 0; i < amountToGenerate; i++) {
			cells.add(new Common(LegendsGame.TILE_IDS, 0, 0));
			LegendsGame.TILE_IDS++;
		}

		return cells;
	}

	// Generate a list of Marketplace Tiles
	private static ArrayList<LegendsMapTile> generateMarket(int amountToGenerate) {
		ArrayList<LegendsMapTile> cells = new ArrayList<LegendsMapTile>();

		for (int i = 0; i < amountToGenerate; i++) {
			cells.add(new Marketplace(LegendsGame.TILE_IDS, 0, 0));
			LegendsGame.TILE_IDS++;
		}

		return cells;
	}

	// Generate a list of Inaccessible Tiles
	private static ArrayList<LegendsMapTile> generateInaccessible(int amountToGenerate) {
		ArrayList<LegendsMapTile> cells = new ArrayList<LegendsMapTile>();

		for (int i = 0; i < amountToGenerate; i++) {
			cells.add(new Inaccessible(LegendsGame.TILE_IDS, 0, 0));
			LegendsGame.TILE_IDS++;
		}

		return cells;
	}

}
