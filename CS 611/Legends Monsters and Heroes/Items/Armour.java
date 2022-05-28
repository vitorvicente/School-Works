/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Items;

import java.util.ArrayList;
import java.util.Arrays;

import Game.LegendsItem;
import Items.Util.ArmourType;

public class Armour extends LegendsItem {

	// Configurable list of item slots
	public static final ArrayList<String> POSSIBLE_SLOTS = new ArrayList<String>(
			Arrays.asList(new String[] { "Head", "Chest", "Legs", "Feet" }));

	private String slot;
	private ArmourType type;
	private int currentDurability;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Armour(int ID, String name, int cost, int minLevel, String slot, ArmourType type) {
		super(ID, name, cost, minLevel);
		this.setSlot(slot);
		this.setArmourType(type);
		this.regenDurability(1);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public String getSlot() {
		return this.slot;
	}

	public ArmourType getType() {
		return this.type;
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

	private void setSlot(String slot) {
		if (Armour.POSSIBLE_SLOTS.indexOf(slot) == -1) {
			System.out.println("Invalid slot provided!");
			return;
		}

		this.slot = slot;
	}

	private void setArmourType(ArmourType type) {
		this.type = type;
	}

}
