/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Game.Util.Monster;

public class LegendsMonsterMonsterStats {

	// Absolute max Dodge %% so that Monsters always end up taking SOME damage
	public static final double ABSOLUTE_MAX_DODGE = 0.9;

	private int maxHP;
	private int currentHP;

	private int defense;
	private int strength;
	private double dodge;

	private int exp;
	private int level;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsMonsterMonsterStats(int maxHP, int defense, int strength, double dodge, int level) {
		this.maxHP = maxHP;
		this.setDefense(defense);
		this.setStrength(strength);
		this.setDodge(dodge);
		this.level = level;
		this.exp = 0;
		this.currentHP = maxHP;
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public void increaseMaxHP(int toIncrease) {
		this.maxHP += toIncrease;
	}

	public boolean takeDamage(int damage) {
		int updatedHP = this.getCurrentHP() - damage;
		if (updatedHP < 0) {
			this.currentHP = 0;
			return true;
		}

		this.currentHP = updatedHP;
		return false;
	}

	public void regendHP(double percentageToRegen) {
		int toRegen = (int) Math.round(percentageToRegen * this.getMaxHP());

		this.currentHP = Math.min(this.getMaxHP(), this.getCurrentHP() + toRegen);
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public void setDodge(double dodge) {
		if (dodge > LegendsMonsterMonsterStats.ABSOLUTE_MAX_DODGE) {
			System.out.println("Dodge value provided too high!");
			return;
		}

		this.dodge = dodge;
	}

	public int getMaxHP() {
		return this.maxHP;
	}

	public int getCurrentHP() {
		return this.currentHP;
	}

	public int getDefense() {
		return this.defense;
	}

	public int getStrength() {
		return this.strength;
	}

	public double getDodge() {
		return this.dodge;
	}

	public int getExp() {
		return this.exp;
	}

	public int getLevel() {
		return this.level;
	}

	public boolean addExp(int toAdd) {
		this.exp += toAdd;

		if (this.getExp() >= this.getLevel() * 10)
			return true;
		else
			return false;
	}

	public void increaseLevel() {
		this.level++;
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	public String toString() {
		String response = "";

		response += "HP: " + this.getCurrentHP() + "/" + this.getMaxHP() + "\n";
		response += "Defense: " + this.getDefense() + "\n";
		response += "Dodge: " + this.getDodge() + "\n";
		response += "Strength: " + this.getStrength() + "\n";
		response += "Monster Level: " + this.getLevel();

		return response;
	}
}
