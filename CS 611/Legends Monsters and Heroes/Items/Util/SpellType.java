/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Items.Util;

import java.util.ArrayList;
import java.util.Arrays;

public class SpellType {

	// Configurable lists of possible buffs and debuffs
	public static final ArrayList<String> POSSIBLE_BUFFS = new ArrayList<String>(
			Arrays.asList(new String[] { "Dexterity", "Strength", "Agility" }));
	public static final ArrayList<String> POSSIBLE_DEBUFFS = new ArrayList<String>(
			Arrays.asList(new String[] { "Defense", "Strength", "Dodge" }));

	// Absolute maximums and minimums for damage, mana cost and damage
	public static final double ABSOLUTE_MAX_MULTIPLIER = 10;

	public static final double ABSOLUTE_MAX_DAMAGE = 1000;
	public static final double ABSOLUTE_MIN_DAMAGE = 0;

	public static final double ABSOLUTE_MAX_MANA_COST = 1000;

	private int damageMin;
	private int damageMax;

	private int manaCost;

	private boolean isOffensive;
	private String targetAbility;
	private double multiplier;
	
	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public SpellType(int damageMin, int damageMax, int manaCost, boolean isOffensive, String targetAbility,
			double multiplier) {
		this.setDamageMin(damageMin);
		this.setDamageMax(damageMax);
		this.setManaCost(manaCost);
		this.setIsOffensive(isOffensive);
		this.setTargetAbility(targetAbility);
		this.setMultiplier(multiplier);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */
	
	public int getDamageMin() {
		return damageMin;
	}

	public int getDamageMax() {
		return damageMax;
	}

	public int getManaCost() {
		return manaCost;
	}

	public boolean getIsOffensive() {
		return this.isOffensive;
	}

	public String getTargetAbility() {
		return this.targetAbility;
	}

	public double getMultiplier() {
		return this.multiplier;
	}

	private void setDamageMin(int damageMin) {
		if (damageMin < SpellType.ABSOLUTE_MIN_DAMAGE) {
			System.out.println("Damage value is below the minimum allowed value!");
			return;
		}
		this.damageMin = damageMin;
	}

	private void setDamageMax(int damageMax) {
		if (damageMax > SpellType.ABSOLUTE_MAX_DAMAGE) {
			System.out.println("Damage value exceeds the maximum allowed value!");
			return;
		}
		this.damageMax = damageMax;
	}

	private void setManaCost(int manaCost) {
		if (manaCost > SpellType.ABSOLUTE_MAX_MANA_COST) {
			System.out.println("Mana Cost value exceeds the maximum allowed value!");
			return;
		}
		this.manaCost = manaCost;
	}

	private void setIsOffensive(boolean isOffensive) {
		this.isOffensive = isOffensive;
	}

	private void setTargetAbility(String targetAbility) {
		if ((!this.getIsOffensive() && SpellType.POSSIBLE_BUFFS.indexOf(targetAbility) == -1)
				|| (this.getIsOffensive() && SpellType.POSSIBLE_DEBUFFS.indexOf(targetAbility) == -1)) {
			System.out.println("Invalid Target Ability!");
			return;
		}
		this.targetAbility = targetAbility;
	}

	private void setMultiplier(double multiplier) {
		if (multiplier > SpellType.ABSOLUTE_MAX_MULTIPLIER) {
			System.out.println("Multiplier given is too high!");
			return;
		}

		this.multiplier = multiplier;
	}

}
