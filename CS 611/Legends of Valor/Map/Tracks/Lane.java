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

import Map.Places.Place;
import Map.Places.Market.MonsterNexus;
import Map.Places.Market.PlayerNexus;
import Map.Places.Plains.Bush;
import Map.Places.Plains.Cave;
import Map.Places.Plains.Koulou;
import Map.Places.Plains.Plains;
import Util.Token;

public class Lane extends Track {

	private Place[][] places;
	private int length;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Lane(int laneID, int length) {
		super(laneID);
		this.length = length;
		initPlaces();
	}

	/* ============ */
	/* Game Methods */
	/* ============ */

	private void initPlaces() {
		places = new Place[length][2];
		
		places[0][0] = new MonsterNexus(this, 0, 0, new Token("~M~"));
		places[0][1] = new MonsterNexus(this, 0, 1, new Token("~M~"));
		
		for (int r = 1; r < places.length - 1; r++) {
			for (int c = 0; c < places[r].length; c++) {
				Place p = generateLocation(r, c);
				places[r][c] = p;
			}
		}
		
		places[length - 1][0] = new PlayerNexus(this, length - 1, 0, new Token("~H~"));
		places[length - 1][1] = new PlayerNexus(this, length - 1, 1, new Token("~H~"));
	}

	public Place generateLocation(int r, int c) {
		double rng = Math.random() * 10;
		if (rng > 6) 
			return new Plains(this, r, c, new Token("==="), "None");
		else if (rng > 4)
			return new Bush(this, r, c, new Token("***"));
		else if (rng > 2)
			return new Koulou(this, r, c, new Token("K-K"));
		else
			return new Cave(this, r, c, new Token("CCC"));
	}

	/* ============ */
	/* Game Methods */
	/* ============ */

	public Place getPlace(int row, int col) {
		if (row >= length || col >= 2)
			return null;
	
		return places[row][col];
	}

	public Place[][] getRawPlaces() {
		return places;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int i) {
		length = i;
	}
}