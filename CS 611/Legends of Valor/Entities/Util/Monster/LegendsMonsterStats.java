/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Entities.Util.Monster;

import Entities.Classes.LegendsEntityClass;
import Entities.Classes.LegendsMonsterClass;
import Entities.Util.LegendsEntityStats;
import Util.Printer;

public class LegendsMonsterStats extends LegendsEntityStats {

	private int defense;
	private int strength;
	private double dodge;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsMonsterStats(int exp, int level, int maxHP, int defense, int strength, double dodge) {
		super(exp, level, maxHP);
		this.defense = defense;
		this.strength = strength;
		this.dodge = dodge;
	}

	/* ============ */
	/* Game Methods */
	/* ============ */

	/*
	 * Levels up the monster, increasing their stats as needed.
	 */
	@Override
	public void levelUp(LegendsEntityClass eClass) {
		LegendsMonsterClass classType = (LegendsMonsterClass) eClass;

		this.increaseLevel();
		int level = this.getLevel();

		this.increaseMaxHP(100 * level - this.getMaxHP());
		this.strength = (int) Math.round(this.strength * classType.getDamageBoost() + 1.05);
		this.defense = (int) Math.round(this.defense * classType.getDefenseBoost() + 1.05);
		this.dodge = (int) Math.round(this.dodge * classType.getDodgeBoost() + 1.05);

	}

	/*
	 * Applies a specific debuff and multiplier
	 */
	public void applyDebuff(String targetAbility, double multiplier) {
		switch (targetAbility) {
		case "Strength":
			this.strength = Math.max(0, (int) Math.round(this.strength * (1 - multiplier)));
			break;
		case "Defense":
			this.defense = Math.max(0, (int) Math.round(this.defense * (1 - multiplier)));
			break;
		case "Dodge":
			this.dodge = Math.max(0, (int) Math.round(this.dodge * (1 - multiplier)));
			break;
		default:
			Printer.printSetMessage("invalidAbility");
		}
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public int getDefense() {
		return defense;
	}

	public int getStrength() {
		return strength;
	}

	public double getDodge() {
		return dodge;
	}

	@Override
	public void resetStats() {
		super.resetStats();
		this.defense = 0;
		this.dodge = 0;
		this.strength = 0;
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	public String toString() {
		return "Current EXP: " + this.getEXP() + " | Current Level: " + this.getLevel() + " | HP: " + this.getCurrHP()
				+ "/" + this.getMaxHP() + " \n" + "Defense: " + this.getDefense() + " | Strength: " + this.getStrength()
				+ " | Dodge: " + this.getDodge();
	}
}
