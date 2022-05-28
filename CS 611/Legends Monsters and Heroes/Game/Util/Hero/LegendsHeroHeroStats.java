/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Game.Util.Hero;

public class LegendsHeroHeroStats {

	private int maxHP;
	private int currentHP;
	private int maxMana;
	private int currentMana;

	private int level;
	private int exp;

	private int coins;

	private int dexterity;
	private int strength;
	private int agility;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsHeroHeroStats(int maxHP, int maxMana, int level, int coins, int dexterity, int strength,
			int agility) {
		this.setMaxHP(maxHP);
		this.setCurrentHP(maxHP);
		this.setMaxMana(maxMana);
		this.setCurrentMana(maxMana);
		this.setLevel(level);
		this.setExp(0);
		this.setCoins(coins);
		this.setDexterity(dexterity);
		this.setStrength(strength);
		this.setAgility(agility);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	private void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	private void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}

	private void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}

	private void setCurrentMana(int currentMana) {
		this.currentMana = currentMana;
	}

	private void setLevel(int level) {
		this.level = level;
	}

	private void setExp(int exp) {
		this.exp = exp;
	}

	private void setCoins(int coins) {
		this.coins = coins;
	}

	private void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}

	private void setStrength(int strength) {
		this.strength = strength;
	}

	private void setAgility(int agility) {
		this.agility = agility;
	}

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

	// Special setter to regen a certain %% of HP
	public void regendHP(double percentageToRegen) {
		int toRegen = (int) Math.round(percentageToRegen * this.getMaxHP());

		this.currentHP = Math.min(this.getMaxHP(), this.getCurrentHP() + toRegen);
	}

	public void increaseMaxMana(int toIncrease) {
		this.maxMana += toIncrease;
	}

	public boolean spendMana(int toSpend) {
		int updatedMana = this.getCurrentMana() - toSpend;
		if (updatedMana < 0) {
			return false;
		}

		this.currentMana = updatedMana;
		return true;
	}

	// Special setter to regen a certain %% of Mana
	public void regendMana(double percentageToRegen) {
		int toRegen = (int) Math.round(percentageToRegen * this.getMaxMana());

		this.currentMana = Math.min(this.getMaxMana(), this.getCurrentMana() + toRegen);
	}

	public boolean addExp(int toAdd) {
		this.exp += toAdd;

		if (this.getExp() >= this.getLevel() * 10) {
			this.setExp(this.getExp() - this.getLevel() * 10);
			this.increaseLevel();
			return true;
		} else
			return false;
	}

	public void increaseLevel() {
		this.level++;
	}

	public void addMoney(int toAdd) {
		this.coins += toAdd;
	}

	public boolean spendMoney(int toSpend) {
		int updatedCoins = this.getCoins() - toSpend;

		if (updatedCoins < 0) {
			return false;
		} else {
			this.coins = updatedCoins;
			return true;
		}
	}

	public void increaseDexterity(int toIncrease) {
		this.dexterity += toIncrease;
	}

	public void increaseStrength(int toIncrease) {
		this.strength += toIncrease;
	}

	public void increaseAgility(int toIncrease) {
		this.agility += toIncrease;
	}

	public int getMaxHP() {
		return this.maxHP;
	}

	public int getCurrentHP() {
		return this.currentHP;
	}

	public int getMaxMana() {
		return this.maxMana;
	}

	public int getCurrentMana() {
		return this.currentMana;
	}

	public int getLevel() {
		return this.level;
	}

	public int getExp() {
		return this.exp;
	}

	public int getCoins() {
		return this.coins;
	}

	public int getDexterity() {
		return this.dexterity;
	}

	public int getStrength() {
		return this.strength;
	}

	public int getAgility() {
		return this.agility;
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	public String toString() {
		String response = "";

		response += "HP: " + this.getCurrentHP() + "/" + this.getMaxHP() + "\n";
		response += "Mana: " + this.getCurrentMana() + "/" + this.getMaxMana() + "\n";
		response += "Dexterity: " + this.getDexterity() + "\n";
		response += "Agility: " + this.getAgility() + "\n";
		response += "Strength: " + this.getStrength() + "\n";
		response += "Exp: " + this.getExp() + "/" + this.getLevel() * 10 + "\n";
		response += "Hero Level: " + this.getLevel() + "\n";
		response += "Coins: " + this.getCoins();

		return response;
	}

}
