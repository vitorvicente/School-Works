/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Entities.Classes;

import java.util.ArrayList;
import java.util.Arrays;

import Entities.Classes.Instances.Paladin;
import Entities.Classes.Instances.Sorcerer;
import Entities.Classes.Instances.Warrior;

public class LegendsHeroClass extends LegendsEntityClass {

	// List holding all the possible Hero Classes
	public static final ArrayList<LegendsHeroClass> POSSIBLE_CLASSES = new ArrayList<LegendsHeroClass>(
			Arrays.asList(new LegendsHeroClass[] { new Warrior(), new Paladin(), new Sorcerer() }));

	private double dexterityBoost;
	private double agilityBoost;
	private double strengthBoost;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsHeroClass(String className) {
		super(className);
		this.setDexterityBoost(1);
		this.setAgilityBoost(1);
		this.setStrengthBoost(1);
	}

	public LegendsHeroClass(String className, double dexterity, double agility, double strength) {
		super(className);
		this.setDexterityBoost(dexterity);
		this.setAgilityBoost(agility);
		this.setStrengthBoost(strength);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public double getDexterityBoost() {
		return dexterityBoost;
	}

	public void setDexterityBoost(double dexterityBoost) {
		this.dexterityBoost = dexterityBoost;
	}

	public double getAgilityBoost() {
		return agilityBoost;
	}

	public void setAgilityBoost(double agilityBoost) {
		this.agilityBoost = agilityBoost;
	}

	public double getStrengthBoost() {
		return strengthBoost;
	}

	public void setStrengthBoost(double strengthBoost) {
		this.strengthBoost = strengthBoost;
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	public boolean equals(LegendsHeroClass otherClass) {
		return (this.getAgilityBoost() == otherClass.getAgilityBoost()
				&& this.getDexterityBoost() == otherClass.getDexterityBoost()
				&& this.getStrengthBoost() == otherClass.getStrengthBoost());
	}
}
