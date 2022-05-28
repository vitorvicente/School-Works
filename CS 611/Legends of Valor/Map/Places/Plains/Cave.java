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

public class Cave extends Plains {

	/* =================== */
	/* Constructor Methods */
	/* =================== */
	
	public Cave(Track track, int row, int col, Token plainToken) {
		super(track, row, col, plainToken, "Agility");
	}

}