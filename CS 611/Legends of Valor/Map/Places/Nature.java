/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Map.Places;

import Entities.LegendsEntity;
import Game.LegendsOfValor;
import Map.Tracks.Track;
import Util.Token;

public class Nature extends Place {

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Nature(Track track, int row, int col, Token natureToken) {
		super(track, row, col, false, natureToken);
	}

	/* ============ */
	/* Game Methods */
	/* ============ */

	/*
	 * Both of the following methods are inherited from Place, however, they are not
	 * actually needed for Nature places, since they cannot be accessed.
	 */
	public void activatePlace(LegendsEntity e, LegendsOfValor game) {
		return;
	}

	@Override
	public void showInfo() {
		return;
	}

}