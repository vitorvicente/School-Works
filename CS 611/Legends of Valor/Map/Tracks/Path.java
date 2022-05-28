/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Map.Tracks;

import Map.Places.Nature;
import Map.Places.Place;
import Map.Places.Market.Market;
import Map.Places.Plains.Plains;
import Util.Token;

public class Path extends Track {

	private Place[] places;
	private Place currPlace;

	private int length;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Path(int laneID, int length) {
		super(laneID);
		this.length = length;
		places = new Place[length];
		initPlaces();
	}

	/* ============ */
	/* Game Methods */
	/* ============ */

	private void initPlaces() {
		for (int r = 1; r < places.length - 1; r++) {
			Place p = generateLocation(r, 0);
			places[r] = p;
		}
	}

	public Place generateLocation(int r, int c) {
		double rng = Math.random() * 10;
		if (rng > 5)
			return new Plains(this, r, c, new Token("==="), "None");
		else if (rng > 2)
			return new Market(this, r, c, new Token("i^i"));
		else
			return new Nature(this, r, c, new Token("XXX"));
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public Place getPlace(int row, int col) {
		return places[row];
	}

	public Place getCurrPlace() {
		return currPlace;
	}

	public void setCurrPlace(int row, int col) {
		currPlace = places[row];
	}

	public Place[] getRawPlaces() {
		return places;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int i) {
		length = i;
	}

}
