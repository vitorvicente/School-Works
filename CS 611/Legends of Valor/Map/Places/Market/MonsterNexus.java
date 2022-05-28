/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Map.Places.Market;

import Entities.LegendsEntity;
import Entities.LegendsHero;
import Game.LegendsOfValor;
import Map.Tracks.Track;
import Util.Printer;
import Util.Token;

public class MonsterNexus extends Nexus {

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public MonsterNexus(Track track, int row, int col, Token nexusType) {
		super(track, row, col, nexusType);
	}

	/* ============ */
	/* Game Methods */
	/* ============ */

	/*
	 * Overrider method to make sure that Heroes don't activate Monster Nexus
	 * Markets, and that the reaching of the enemy spawn point is properly marked
	 */
	@Override
	public void activatePlace(LegendsEntity h, LegendsOfValor game) {
		if (h instanceof LegendsHero)
			Printer.printMSG("You have reached an Enemy Nexus!");
	}
}
