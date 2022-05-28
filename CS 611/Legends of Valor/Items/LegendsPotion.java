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

public class LegendsPotion extends LegendsItem {
	// Configurable lists of possible buffs and debuffs
	public static final ArrayList<String> POSSIBLE_BUFFS = new ArrayList<String>(
			Arrays.asList(new String[] { "Dexterity", "Strength", "Agility" }));
	public static final ArrayList<String> POSSIBLE_DEBUFFS = new ArrayList<String>(
			Arrays.asList(new String[] { "Defense", "Strength", "Dodge" }));

	// Absolute maximum multiplier
	public static final double ABSOLUTE_MAX_MULTIPLIER = 10;

	private boolean isBuff;
	private String targetAbility;
	private double multiplier;

	private boolean used;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsPotion(int ID, String name, int cost, int minLevel, boolean isBuff, String targetAbility,
			double multiplier) {
		super(ID, name, cost, minLevel);
		this.used = false;
		this.setIsBuff(isBuff);
		this.setTargetAbility(targetAbility);
		this.setMultiplier(multiplier);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public boolean isUsed() {
		return used;
	}

	public void use() {
		this.used = true;
	}

	public boolean getIsBuff() {
		return isBuff;
	}

	private void setIsBuff(boolean isBuff) {
		this.isBuff = isBuff;
	}

	public String getTargetAbility() {
		return targetAbility;
	}

	private void setTargetAbility(String targetAbility) {
		if ((this.getIsBuff() && LegendsPotion.POSSIBLE_BUFFS.indexOf(targetAbility) == -1)
				|| (!this.getIsBuff() && LegendsPotion.POSSIBLE_DEBUFFS.indexOf(targetAbility) == -1)) {
			System.out.println("Invalid Target Ability!");
			return;
		}
		this.targetAbility = targetAbility;
	}

	public double getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(double multiplier) {
		if (multiplier > LegendsPotion.ABSOLUTE_MAX_MULTIPLIER) {
			System.out.println("Multiplier given is too high!");
			return;
		}

		this.multiplier = multiplier;
	}
}
