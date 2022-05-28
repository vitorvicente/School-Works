/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Items;

import java.util.ArrayList;
import java.util.Arrays;

public class LegendsArmour extends LegendsItem {

	// List of all possible Armour Slots
	public static final ArrayList<String> POSSIBLE_SLOTS = new ArrayList<String>(
			Arrays.asList(new String[] { "Head", "Chest", "Legs", "Feet" }));

	// Theoretical limits to armour
	public static final int ABSOLUTE_MAX_DURABILITY = 5000;
	public static final double ABSOLUTE_MAX_DEFENSE_MULTIPLIER = 0.8;

	private String slot;

	private int currentDurability;
	private int maxDurability;

	private double defenseMultiplier;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsArmour(int ID, String name, int cost, int minLevel, double defenseMultiplier, int maxDurability,
			String slot) {
		super(ID, name, cost, minLevel);
		this.setDefenseMultiplier(Math.min(defenseMultiplier, LegendsArmour.ABSOLUTE_MAX_DEFENSE_MULTIPLIER));
		this.setMaxDurability(Math.min(maxDurability, LegendsArmour.ABSOLUTE_MAX_DURABILITY));
		this.regenDurability(1);
		this.setSlot(slot);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public String getSlot() {
		return this.slot;
	}

	public int getCurrentDurability() {
		return currentDurability;
	}

	public void regenDurability(double regenDurabilityPercentage) {
		int toRegen = (int) Math.round(regenDurabilityPercentage * this.getMaxDurability());

		this.currentDurability = Math.min(this.getMaxDurability(), this.getCurrentDurability() + toRegen);
	}

	public boolean loseDurability(int toLose) {
		this.currentDurability = Math.max(0, this.getCurrentDurability() - toLose);

		return this.currentDurability == 0;
	}

	private void setSlot(String slot) {
		if (LegendsArmour.POSSIBLE_SLOTS.indexOf(slot) == -1) {
			System.out.println("Invalid slot provided!");
			return;
		}

		this.slot = slot;
	}

	private void setDefenseMultiplier(double defenseMutiplier) {
		if (defenseMutiplier > LegendsArmour.ABSOLUTE_MAX_DEFENSE_MULTIPLIER) {
			System.out.println("Defense multiplier value exceeds the maximum allowed value!");
			return;
		}
		this.defenseMultiplier = defenseMutiplier;
	}

	private void setMaxDurability(int maxDurability) {
		if (maxDurability > LegendsArmour.ABSOLUTE_MAX_DURABILITY) {
			System.out.println("Max durability value exceeds the maximum allowed value!");
			return;
		}
		this.maxDurability = maxDurability;
	}

	public double getDefenseMultiplier() {
		return this.defenseMultiplier;
	}

	public int getMaxDurability() {
		return maxDurability;
	}
}
