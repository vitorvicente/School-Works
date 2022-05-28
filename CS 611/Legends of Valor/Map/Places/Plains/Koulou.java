/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Map.Places.Plains;

import Map.Tracks.Track;
import Util.Token;

public class Koulou extends Plains {

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Koulou(Track track, int row, int col, Token plainToken) {
		super(track, row, col, plainToken, "Strength");
	}

}