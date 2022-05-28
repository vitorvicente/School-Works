/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Game.Util.Hero;

import java.util.ArrayList;

import Game.LegendsItem;

public class LegendsHeroInventory {

	private ArrayList<LegendsItem> inventory;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsHeroInventory() {
		this.resetInventory();
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public void resetInventory() {
		this.inventory = new ArrayList<LegendsItem>();
	}

	public void removeItem(LegendsItem item) {
		this.getInventory().remove(item);
	}

	public void removeItem(int itemID) {
		for (LegendsItem i : this.getInventory()) {
			if (i.getID() == itemID) {
				this.getInventory().remove(i);
			}
		}
	}

	public void addItem(LegendsItem item) {
		this.inventory.add(item);
	}

	public ArrayList<LegendsItem> getInventory() {
		return this.inventory;
	}
}
