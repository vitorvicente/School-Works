/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Entities.Util;

import Entities.Classes.LegendsEntityClass;

public abstract class LegendsEntityStats {

	private int exp;
	private int level;
	private int hp;
	private int maxHP;
	private boolean alive;
	
	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsEntityStats(int exp, int level, int maxHP) {
		this.exp = exp;
		this.level = level;
		this.maxHP = maxHP;
		this.hp = maxHP;
		this.alive = true;
	}
	
	/* ================ */
	/* Abstract Methods */
	/* ================ */
	
	public abstract void levelUp(LegendsEntityClass eClass);

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */
	
	public void resetStats() {
		this.exp = 0;
		this.level = 1;
		this.hp = 0;
		this.maxHP = 0;
		this.alive = true;
	}

	public boolean addEXP(int toIncrease) {
		this.exp += toIncrease;

		return this.exp >= this.level*10;
	}
	
	public void increaseLevel() {
		this.level++;
	}

	public void regenHealth(double percent) {
		this.hp = Math.min(this.maxHP, ((int) Math.round(this.maxHP * percent)) + this.hp);
	}

	public boolean takeDamage(int toTake) {
		this.hp = Math.max(0, this.hp - toTake);

		this.setAlive(this.hp != 0);
		return this.alive;
	}
	
	public void increaseMaxHP(int toIncrease) {
		this.maxHP += toIncrease;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean isAlive() {
		return this.alive;
	}

	public int getEXP() {
		return this.exp;
	}

	public int getLevel() {
		return this.level;
	}

	public int getCurrHP() {
		return this.hp;
	}

	public int getMaxHP() {
		return this.maxHP;
	}
}
