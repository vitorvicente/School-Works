/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Game;

import java.util.ArrayList;

import Classes.LegendsEntityClass;
import Classes.LegendsHeroClass;
import Game.Util.LegendsEntity;
import Game.Util.Hero.LegendsHeroArmour;
import Game.Util.Hero.LegendsHeroGameStats;
import Game.Util.Hero.LegendsHeroHeroStats;
import Game.Util.Hero.LegendsHeroInventory;
import Items.Spell;
import Items.Weapon;

public class LegendsHero extends LegendsEntity {

	private LegendsHeroGameStats gameRelatedStats;
	private LegendsHeroClass heroClass;
	private LegendsHeroHeroStats heroRelatedStats;
	private LegendsHeroInventory heroInventory;
	private LegendsHeroArmour heroArmour;
	private Weapon equippedWeapon;
	private ArrayList<Spell> knownSpells;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsHero(int ID, String name, LegendsHeroGameStats gameRelatedStats, LegendsEntityClass heroClass,
			LegendsHeroHeroStats heroRelatedStats, LegendsHeroInventory heroInventory, LegendsHeroArmour heroArmour,
			Weapon equippedWeapon, ArrayList<Spell> knownSpells) {
		super(ID, name);
		this.setGameRelatedStats(gameRelatedStats);
		this.setEntityClass(heroClass);
		this.setHeroRelatedStats(heroRelatedStats);
		this.setHeroInventory(heroInventory);
		this.setHeroArmour(heroArmour);
		this.setEquippedWeapon(equippedWeapon);
		this.setKnownSpells(knownSpells);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	private void setGameRelatedStats(LegendsHeroGameStats gameRelatedStats) {
		this.gameRelatedStats = gameRelatedStats;
	}

	private void setHeroRelatedStats(LegendsHeroHeroStats heroRelatedStats) {
		this.heroRelatedStats = heroRelatedStats;
	}

	private void setHeroInventory(LegendsHeroInventory heroInventory) {
		this.heroInventory = heroInventory;
	}

	private void setHeroArmour(LegendsHeroArmour heroArmour) {
		this.heroArmour = heroArmour;
	}

	@Override
	public void setEntityClass(LegendsEntityClass eClass) {
		if (!(eClass instanceof LegendsHeroClass)) {
			System.out.println("Invalid Entity Class Type!");
			return;
		}

		this.heroClass = (LegendsHeroClass) eClass;
	}

	public LegendsHeroGameStats getGameRelatedStats() {
		return this.gameRelatedStats;
	}

	public LegendsHeroClass getHeroClass() {
		return heroClass;
	}

	public LegendsHeroHeroStats getHeroRelatedStats() {
		return heroRelatedStats;
	}

	public LegendsHeroInventory getHeroInventory() {
		return heroInventory;
	}

	public LegendsHeroArmour getHeroArmor() {
		return heroArmour;
	}

	@Override
	public LegendsEntityClass getEntityClass() {
		return this.heroClass;
	}

	public Weapon getEquippedWeapon() {
		return equippedWeapon;
	}

	private void setEquippedWeapon(Weapon equippedWeapon) {
		this.equippedWeapon = equippedWeapon;
	}

	public ArrayList<Spell> getKnownSpells() {
		return knownSpells;
	}

	private void setKnownSpells(ArrayList<Spell> knownSpells) {
		this.knownSpells = knownSpells;
	}

	public void learnSpells(Spell spell) {
		this.knownSpells.add(spell);
	}

	public void deleteEquippedWeapon() {
		this.equippedWeapon = null;
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	public void switchWeapon(Weapon toSwitch) {
		this.getHeroInventory().removeItem(toSwitch);
		if (this.getEquippedWeapon() != null) {
			this.getHeroInventory().addItem(this.getEquippedWeapon());
		}
		this.setEquippedWeapon(toSwitch);
	}

}
