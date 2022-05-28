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

import java.util.ArrayList;

import Entities.LegendsEntity;
import Entities.LegendsHero;
import Entities.LegendsMonster;
import Game.LegendsOfValor;
import Map.Tracks.Track;
import Util.Token;
import Util.Abstraction.Cell;

public abstract class Place extends Cell {

	private ArrayList<LegendsHero> heroesOnCell;
	private ArrayList<LegendsMonster> monstersOnCell;

	private boolean accessiblity;

	private Track currTrack;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Place(Track track, int row, int col, boolean accessiblity, Token design) {
		super(row, col, design);
		this.accessiblity = accessiblity;
		currTrack = track;
		heroesOnCell = new ArrayList<LegendsHero>();
		monstersOnCell = new ArrayList<LegendsMonster>();
	}

	public Place(int row, int col, boolean accessiblity, Token design, Token activeDesign) {
		super(row, col, design, activeDesign);
		this.accessiblity = accessiblity;
	}

	/* ================ */
	/* Abstract Methods */
	/* ================ */

	public abstract void activatePlace(LegendsEntity e, LegendsOfValor game);

	public abstract void showInfo();

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public void addHeroOnCell(LegendsHero h) {
		heroesOnCell.add(h);
	}

	public void addMonsterOnCell(LegendsMonster m) {
		monstersOnCell.add(m);
	}

	public void removeHeroOnCell(LegendsHero h) {
		heroesOnCell.remove(h);
	}

	public void removeMonsterOnCell(LegendsMonster m) {
		monstersOnCell.remove(m);
	}

	public boolean isAccessible() {
		return accessiblity;
	}

	public void setAccessibility(boolean accessiblity) {
		this.accessiblity = accessiblity;
	}

	public void setCurrTrack(Track newTrack) {
		currTrack = newTrack;
	}

	public Track getCurrTrack() {
		return currTrack;
	}

	public ArrayList<LegendsHero> getHeroesOnCell() {
		return heroesOnCell;
	}

	public ArrayList<LegendsMonster> getMonstersOnCell() {
		return monstersOnCell;
	}

}