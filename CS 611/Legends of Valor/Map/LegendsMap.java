/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Map;

import Map.Tracks.Boundary;
import Map.Tracks.Lane;
import Map.Tracks.Track;
import Util.Abstraction.Map;

public class LegendsMap extends Map {

	private int totalCells;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsMap(int numTracks, int length) {
		super(numTracks, length);
	}

	/* ============ */
	/* Game Methods */
	/* ============ */

	public void initBoard() {
		this.setMap(new Track[this.getNumTracks()]);
		this.getMap()[0] = new Lane(0, this.getMapDimen());
		for (int i = 1, index = 1; i < this.getNumTracks(); i += 2, index++) {
			this.getMap()[i] = new Boundary(-1, this.getMapDimen());
			this.getMap()[i + 1] = new Lane(index, this.getMapDimen());
		}
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public int getTotalCells() {
		return totalCells;
	}

	public void setTotalCells(int i) {
		totalCells = i;
	}

}