/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Items;

import Game.LegendsItem;
import Items.Util.SpellType;

public class Spell extends LegendsItem {

	private SpellType type;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Spell(int ID, String name, int cost, int minLevel, SpellType type) {
		super(ID, name, cost, minLevel);
		this.setType(type);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public SpellType getType() {
		return this.type;
	}

	private void setType(SpellType type) {
		this.type = type;
	}

}
