/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Classes;

import java.util.ArrayList;
import java.util.Arrays;

import Classes.Monster.Dragon;
import Classes.Monster.Exoskeleton;
import Classes.Monster.Spirit;

public class LegendsMonsterClass extends LegendsEntityClass {

	// List holding all the possible Monster Classes
	public static final ArrayList<LegendsMonsterClass> POSSIBLE_CLASSES = new ArrayList<LegendsMonsterClass>(
			Arrays.asList(new LegendsMonsterClass[] { new Dragon(), new Spirit(), new Exoskeleton() }));

	private double damageBoost;
	private double defenseBoost;
	private double dodgeBoost;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsMonsterClass(String className) {
		super(className);
		this.setDamageBoost(1);
		this.setDefenseBoost(1);
		this.setDodgeBoost(1);
	}

	public LegendsMonsterClass(String className, double damage, double defense, double dodge) {
		super(className);
		this.setDamageBoost(damage);
		this.setDefenseBoost(defense);
		this.setDodgeBoost(dodge);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public double getDamageBoost() {
		return damageBoost;
	}

	public void setDamageBoost(double damageBoost) {
		this.damageBoost = damageBoost;
	}

	public double getDefenseBoost() {
		return defenseBoost;
	}

	public void setDefenseBoost(double defenseBoost) {
		this.defenseBoost = defenseBoost;
	}

	public double getDodgeBoost() {
		return dodgeBoost;
	}

	public void setDodgeBoost(double dodgeBoost) {
		this.dodgeBoost = dodgeBoost;
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	public boolean equals(LegendsMonsterClass otherClass) {
		return (this.getDamageBoost() == otherClass.getDamageBoost()
				&& this.getDefenseBoost() == otherClass.getDefenseBoost()
				&& this.getDodgeBoost() == otherClass.getDodgeBoost());
	}

}
