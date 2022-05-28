/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Items;

import Game.LegendsItem;
import Items.Util.WeaponType;

public class Weapon extends LegendsItem {

	private WeaponType type;

	private int currentDurability;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Weapon(int ID, String name, int cost, int minLevel, WeaponType type) {
		super(ID, name, cost, minLevel);
		this.setType(type);
		this.regenDurability(1);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public WeaponType getType() {
		return this.type;
	}

	private void setType(WeaponType type) {
		this.type = type;
	}

	public int getCurrentDurability() {
		return currentDurability;
	}

	// Not used yet, but it would be a cool feature (ie: blacksmith tile)
	public void regenDurability(double regenDurabilityPercentage) {
		int toRegen = (int) Math.round(regenDurabilityPercentage * this.getType().getMaxDurability());

		this.currentDurability = Math.min(this.getType().getMaxDurability(), this.getCurrentDurability() + toRegen);
	}

	public boolean loseDurability(int toLose) {
		this.currentDurability = Math.max(0, this.getCurrentDurability() - toLose);

		return this.currentDurability == 0;
	}

}
