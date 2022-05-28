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

public class LegendsSpell extends LegendsItem {
	
	// Configurable lists of possible buffs and debuffs
	public static final ArrayList<String> POSSIBLE_BUFFS = new ArrayList<String>(
			Arrays.asList(new String[] { "Dexterity", "Strength", "Agility" }));
	public static final ArrayList<String> POSSIBLE_DEBUFFS = new ArrayList<String>(
			Arrays.asList(new String[] { "Defense", "Strength", "Dodge" }));

	// Absolute limits to Spells
	public static final double ABSOLUTE_MAX_MULTIPLIER = 10;
	public static final double ABSOLUTE_MAX_DAMAGE = 1000;
	public static final double ABSOLUTE_MAX_MANA_COST = 1000;

	private int manaRequired;
	private int maxDamage;

	private String targetAbility;
	private boolean isBuff;

	private double multiplier;

	/* =================== */
	/* Constructor Methods */
	/* =================== */
	
	public LegendsSpell(int ID, String name, int cost, int minLevel, int manaRequired, int maxDamage,
			String targetAbility, boolean isBuff, double multiplier) {
		super(ID, name, cost, minLevel);
		this.setMaxDamage(maxDamage);
		this.setManaRequired(manaRequired);
		this.setIsBuff(isBuff);
		this.setTargetAbility(targetAbility);
		this.setMultiplier(multiplier);
	}
	
	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public int getManaRequired() {
		return manaRequired;
	}

	private void setManaRequired(int manaRequired) {
		this.manaRequired = manaRequired;
	}

	public int getMaxDamage() {
		return maxDamage;
	}

	private void setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
	}

	public String getTargetAbility() {
		return targetAbility;
	}

	private void setTargetAbility(String targetAbility) {
		this.targetAbility = targetAbility;
	}

	public boolean getIsBuff() {
		return isBuff;
	}

	private void setIsBuff(boolean isBuff) {
		this.isBuff = isBuff;
	}

	public double getMultiplier() {
		return multiplier;
	}

	private void setMultiplier(double multiplier) {
		this.multiplier = multiplier;
	}

}
