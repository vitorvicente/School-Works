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
import Util.Token;

public class Boundary extends Track {
	
	private Place[] places;

	/* =================== */
	/* Constructor Methods */
	/* =================== */
	
	public Boundary(int boundID, int length) {
		super(boundID);
		places = new Place[length];
		for (int i = 0; i < places.length; i++) {
			places[i] = new Nature(this, i, 0, new Token("XXX"));
		}
	}
	
	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */
	
	public Place getPlace(int row, int col) {
		return places[row];
	}

}