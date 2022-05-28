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

import Map.Places.Place;
import Map.Tracks.Boundary;
import Map.Tracks.Lane;
import Map.Tracks.Track;

public abstract class Map {

	private Track[] map;
	private int numTracks;
	private int mapDimen;
	private int totalCells;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Map(int numTracks, int length) {
		this.mapDimen = length;
		this.numTracks = numTracks;
		totalCells = mapDimen * mapDimen;
		initBoard();
	}

	/* ================ */
	/* Abstract Methods */
	/* ================ */

	public abstract void initBoard();

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public int getMapDimen() {
		return mapDimen;
	}

	public Track[] getMap() {
		return map;
	}

	public void setMap(Track[] map) {
		this.map = map;
	}

	public Place getPlace(int laneID, int row, int col) {
		return map[laneID].getPlace(row, col);
	}

	public int getNumTracks() {
		return this.numTracks;
	}

	public int getTotalCells() {
		return this.totalCells;
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	/*
	 * The methods below print the map in a fancy way
	 */
	public void print() {
		for (int k = 0; k < mapDimen; k++) {
			for (int i = 0; i < mapDimen; i++) {
				System.out.print("+-------+");
			}

			String[] placeTokens = new String[mapDimen];
			String[] monsterTokens = new String[mapDimen];
			String[] heroTokens = new String[mapDimen];

			int d = 0;
			for (int i = 0; i < numTracks; i++) {

				Track currentTrack = map[i];
				if (currentTrack instanceof Lane) {
					placeTokens[d] = (currentTrack.getPlace(k, 0).getToken().getDesign());
					placeTokens[d + 1] = (currentTrack.getPlace(k, 1).getToken().getDesign());

					if (currentTrack.getPlace(k, 0).getMonstersOnCell().size() > 0)
						monsterTokens[d] = currentTrack.getPlace(k, 0).getMonstersOnCell().get(0).getToken()
								.getDesign();
					if (currentTrack.getPlace(k, 1).getMonstersOnCell().size() > 0)
						monsterTokens[d + 1] = currentTrack.getPlace(k, 1).getMonstersOnCell().get(0).getToken()
								.getDesign();
					if (currentTrack.getPlace(k, 0).getHeroesOnCell().size() > 0)
						heroTokens[d] = currentTrack.getPlace(k, 0).getHeroesOnCell().get(0).getToken().getDesign();
					if (currentTrack.getPlace(k, 1).getHeroesOnCell().size() > 0)
						heroTokens[d + 1] = currentTrack.getPlace(k, 1).getHeroesOnCell().get(0).getToken().getDesign();

					d += 2;
				}

				if (currentTrack instanceof Boundary) {
					placeTokens[d] = (currentTrack.getPlace(k, 0).getToken().getDesign());
					d++;
				}

			}

			System.out.println();

			for (int i = 0; i < mapDimen; i++) {
				if (placeTokens[i] != null)
					System.out.print("|  " + placeTokens[i] + "  |");
				else
					System.out.print("|       |");
			}

			System.out.println();

			for (int i = 0; i < mapDimen; i++) {
				if (monsterTokens[i] != null)
					System.out.print("|" + monsterTokens[i] + " ");
				else
					System.out.print("|    ");

				if (heroTokens[i] != null)
					System.out.print(heroTokens[i] + "|");
				else
					System.out.print("   |");
			}

			System.out.println();
		}

		for (int i = 0; i < mapDimen; i++)
			System.out.print("+-------+");

		System.out.println();
	}

}