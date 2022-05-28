/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Items.Util;

public class ArmourType {

	// Absolute maximums for durability and defense multiplier
	public static final int ABSOLUTE_MAX_DURABILITY = 5000;
	public static final double ABSOLUTE_MAX_DEFENSE_MULTIPLIER = 0.8;

	private double defenseMultiplier;
	private int maxDurability;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public ArmourType(double defenseMultiplier, int maxDurability) {
		this.setDefenseMultiplier(Math.min(defenseMultiplier, ArmourType.ABSOLUTE_MAX_DEFENSE_MULTIPLIER));
		this.setMaxDurability(Math.min(maxDurability, ArmourType.ABSOLUTE_MAX_DURABILITY));
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	private void setDefenseMultiplier(double defenseMutiplier) {
		if (defenseMutiplier > ArmourType.ABSOLUTE_MAX_DEFENSE_MULTIPLIER) {
			System.out.println("Defense multiplier value exceeds the maximum allowed value!");
			return;
		}
		this.defenseMultiplier = defenseMutiplier;
	}

	private void setMaxDurability(int maxDurability) {
		if (maxDurability > ArmourType.ABSOLUTE_MAX_DURABILITY) {
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
