/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Items;

import Game.LegendsItem;
import Items.Util.PotionType;

public class Potion extends LegendsItem {

	private PotionType type;
	private boolean used;

	/* =================== */
	/* Constructor Methods */
	/* =================== */
	
	public Potion(int ID, String name, int cost, int minLevel, PotionType type) {
		super(ID, name, cost, minLevel);
		this.used = false;
		this.setType(type);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */
	
	public PotionType getType() {
		return type;
	}

	public void setType(PotionType type) {
		this.type = type;
	}

	public boolean isUsed() {
		return used;
	}

	public void use() {
		this.used = true;
	}

}
