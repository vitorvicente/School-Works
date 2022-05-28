/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Map.Places.Plains;

import java.util.*;

import Entities.LegendsEntity;
import Entities.LegendsHero;
import Entities.Util.Hero.LegendsHeroStats;
import Entities.Util.Monster.LegendsMonsterStats;
import Entities.LegendsMonster;
import Game.LegendsOfValor;
import Items.LegendsArmour;
import Items.LegendsItem;
import Items.LegendsPotion;
import Items.LegendsSpell;
import Items.LegendsWeapon;
import Map.Places.Place;
import Map.Tracks.Track;
import Util.Token;
import Util.Creation.ItemGenerator;
import Util.Printer;
import Util.State;

public class Plains extends Place {

	// Absolute possible list of Buffs that the specific Plains cell can have
	public static final ArrayList<String> POSSIBLE_BUFFS = new ArrayList<String>(
			Arrays.asList(new String[] { "Dexterity", "Strength", "Agility", "None" }));

	// Stat that this specific Plains cell boosts
	private String statToBoost;

	private ArrayList<LegendsHero> leftOverHeroes;
	private ArrayList<LegendsMonster> leftOverMonsters;
	private ArrayList<LegendsHero> deadHeroes;

	private HashMap<LegendsHero, LegendsMonster> matchings;

	private Scanner in;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Plains(Track track, int row, int col, Token plainToken, String statToBoost) {
		super(track, row, col, true, plainToken);
		this.setStatToBoost(statToBoost);

		in = new Scanner(System.in);

		leftOverHeroes = new ArrayList<LegendsHero>();
		leftOverMonsters = new ArrayList<LegendsMonster>();

		deadHeroes = new ArrayList<LegendsHero>();

		matchings = new HashMap<LegendsHero, LegendsMonster>();
	}

	/* ================= */
	/* Main Game Methods */
	/* ================= */

	/*
	 * This is the main entry point method, each time it's called, it checks to see
	 * if there is at least one Hero and one Monster in the cell, if so, it begins a
	 * fight.
	 */
	public void activatePlace(LegendsEntity e, LegendsOfValor game) {
		if (this.getHeroesOnCell().size() >= 1 && this.getMonstersOnCell().size() >= 1)
			fightSequence(game);
	}

	/*
	 * This is the actual fight sequence method, it makes the appropriate Monster v
	 * Hero matches, and deals with deaths/quitting/winning/losing. It also calls
	 * the fight method if none of these are needed.
	 */
	private void fightSequence(LegendsOfValor game) {
		Printer.printMSG("You have entered an encounter with a Monster!");

		makeMatchings();

		while (!matchings.isEmpty() && game.getStatus().equals(State.PLAYING)) {
			if (!leftOverHeroes.isEmpty() && !leftOverMonsters.isEmpty())
				reMatch();

			for (LegendsHero h : matchings.keySet())
				heroFight(h, matchings.get(h), game);
		}

		if (leftOverHeroes.size() > 0)
			Printer.printMSG("Heroes won the encounter!");
		else if (leftOverMonsters.size() > 0)
			Printer.printMSG("Monsters won the encounter!");
	}

	/*
	 * This method deals with the ins-and-outs of the fight, calling other methods
	 * to help.
	 * 
	 * It gets and performs the Hero move, and checks for Monster death, acting
	 * according (ie: either killing the monster, or letting it move).
	 * 
	 * It also deals with either entities dying, and all the requirements that come
	 * of it, including leveling, and leftover battles.
	 */
	private void heroFight(LegendsHero h, LegendsMonster m, LegendsOfValor game) {
		String nextMove = this.processUserInput();
		preformMove(nextMove, h, m, game);

		if (m.getEntityStats().getCurrHP() > 0) {
			attackHero(h, m);

			if (h.getEntityStats().getCurrHP() > 0) {
				h.getEntityStats().regenHealth(0.1);
				((LegendsHeroStats) h.getEntityStats()).regenMana(0.1);
			} else {
				this.leftOverMonsters.add(m);
				this.deadHeroes.add(h);

				this.matchings.remove(h);

				m.getEntityStats().addEXP(10 * h.getEntityStats().getLevel());
				if (m.getEntityStats().getEXP() >= m.getEntityStats().getLevel() * 10)
					m.levelUp();
			}
		} else {
			h.getEntityStats().regenHealth(1);
			((LegendsHeroStats) h.getEntityStats()).regenMana(1);
			this.leftOverHeroes.add(h);

			this.matchings.remove(h);

			h.getEntityStats().addEXP(10 * m.getEntityStats().getLevel());
			if (h.getEntityStats().getEXP() >= h.getEntityStats().getLevel() * 10)
				h.levelUp();
		}
	}

	@Override
	public void showInfo() {
		System.out.println();

		System.out.format("+------------------------+%n");
		System.out.format("|        HERO INFO       |%n");
		System.out.format("+------------------------+%n");
		Printer.printHelperLine(127);
		System.out.printf(
				"|                        NAME                        | LEVEL |  HP   |  MANA   |  COINS  |  EXP  |   DEX   | AGILITY | STRENGTH | %n");
		Printer.printHelperLine(127);
		for (LegendsHero h : getHeroesOnCell()) {
			System.out.print(h);
			Printer.printHelperLine(127);
		}

		System.out.println();

		System.out.format("+------------------------+%n");
		System.out.format("|       MONSTER INFO     |%n");
		System.out.format("+------------------------+%n");
		Printer.printHelperLine(105);
		System.out.printf(
				"|                        NAME                        | LEVEL |  HP   |  EXP  | DODGE | DEFENSE | STRENGTH | %n");
		Printer.printHelperLine(105);
		for (LegendsMonster m : getMonstersOnCell()) {
			System.out.print(m);
			Printer.printHelperLine(105);
		}

		System.out.println();
	}

	/* ====================== */
	/* Main Hero Game Methods */
	/* ====================== */

	private void printActions() {
		System.out.println();

		String leftAlignFormat = "| %-15s | %-4s |%n";

		System.out.format("+------------------------+%n");
		System.out.format("|         ACTIONS        |%n");
		System.out.format("+-----------------+------+%n");
		System.out.format(leftAlignFormat, "check info", "i");
		System.out.format(leftAlignFormat, "check inventory", "c");
		System.out.format(leftAlignFormat, "attack monster", "a");
		System.out.format(leftAlignFormat, "cast spell", "s");
		System.out.format(leftAlignFormat, "revive teammate", "r");
		System.out.format(leftAlignFormat, "quit", "q");
		System.out.format("+-----------------+------+%n");

		System.out.println();
	}

	/*
	 * This method gets the actual input for next action from the user
	 */
	private String processUserInput() {
		while (true) {
			Printer.printMSG("What would you like to do?");

			printActions();

			String input = in.nextLine();
			switch (input.toLowerCase()) {
			case "i":
			case "c":
			case "a":
			case "s":
			case "r":
			case "q":
				return input.toLowerCase();
			default:
				Printer.printSetMessage("invalidResponse");
				break;
			}
		}
	}

	/*
	 * This method performs the actual move for the Hero in question, by calling the
	 * helper methods for each specific move, and dealing with the consequences when
	 * needed.
	 */
	private void preformMove(String nextMove, LegendsHero h, LegendsMonster m, LegendsOfValor game) {
		switch (nextMove) {
		case "i":
			showInfo();
			break;
		case "c":
			LegendsItem returnItem = h.getInventory().accessInventory();
			if (returnItem != null)
				useItem(returnItem, h, m);
			break;
		case "a":
			attackMonster(h, m);
			break;
		case "s":
			castSpell(h, m);
			break;
		case "r":
			revive(h);
			break;
		case "q":
			game.quit();
			break;
		default:
			Printer.printSetMessage("invalidResponse");
			break;
		}
	}

	/* ======================== */
	/* Helper Hero Game Methods */
	/* ======================== */

	/*
	 * This method preforms a physical attack on the Monster, dealing with the
	 * intricate requirements for stats/damage/hp/etc...
	 */
	private void attackMonster(LegendsHero h, LegendsMonster m) {
		double dodgeChance = ((LegendsMonsterStats) m.getEntityStats()).getDodge() * 0.1;
		if (Math.random() > dodgeChance) {
			int weaponDamage = 200;
			if (h.getInventory().getEquippedWeapon() != null)
				weaponDamage = h.getInventory().getEquippedWeapon().getDamage();

			double strengthMultiplier = (statToBoost.equals("Strength")) ? 0.15 : 0.05;
			weaponDamage += (int) Math
					.round(((LegendsHeroStats) h.getEntityStats()).getStrength() * strengthMultiplier);

			int finalDamage = weaponDamage - ((LegendsMonsterStats) m.getEntityStats()).getDefense();

			if (finalDamage < 0)
				finalDamage = 0;

			m.getEntityStats().takeDamage(finalDamage);

			String weaponName = "their fists";
			if (h.getInventory().getEquippedWeapon() != null) {
				weaponName = h.getInventory().getEquippedWeapon().getName();

				h.getInventory().getEquippedWeapon().loseDurability(1);

				if (h.getInventory().getEquippedWeapon().getCurrentDurability() <= 0) {
					Printer.printMSG(h.getName() + "'s weapon " + h.getInventory().getEquippedWeapon().getName()
							+ " has been broken!");
					h.getInventory().replaceEquippedWeapon(null);
				}
			}

			Printer.printMSG(h.getName() + " has attacked " + m.getName() + " using " + weaponName + " and dealing "
					+ finalDamage + " damage!");

		} else
			Printer.printMSG(m.getName() + " has dodged " + h.getName() + "'s attack!");

	}

	/*
	 * This method deals with the leftovers of using the selected item.
	 */
	private void useItem(LegendsItem item, LegendsHero h, LegendsMonster m) {
		if (item instanceof LegendsArmour)
			h.getInventory().switchArmour((LegendsArmour) item);
		else if (item instanceof LegendsWeapon)
			h.getInventory().replaceEquippedWeapon((LegendsWeapon) item);
		else if (item instanceof LegendsPotion)
			this.usePotion(((LegendsPotion) item), h, m);
		else
			Printer.printSetMessage("illegalItemChoice");
	}

	/*
	 * The following three methods deal with the use of a Potion, either on an Ally,
	 * or a Monster (since in this game they can do both).
	 * 
	 * For the usage on an enemy, it simply uses it on the enemy the Hero is matched
	 * with, applying the debuff.
	 * 
	 * For the usage on a friendly, it'll first display a list of Friendlies on the
	 * board, and allow the selection of one, before doing the same thing it does if
	 * it were a monster.
	 */
	private void usePotion(LegendsPotion p, LegendsHero h, LegendsMonster m) {
		if (p.getIsBuff())
			this.usePotionOnFriendly(p, h);
		else
			this.usePotionOnEnemy(p, h, m);
	}

	private void usePotionOnEnemy(LegendsPotion p, LegendsHero h, LegendsMonster m) {
		switch (p.getTargetAbility()) {
		case "Strength":
		case "Defense":
		case "Dodge":
			((LegendsMonsterStats) m.getEntityStats()).applyDebuff(p.getTargetAbility(), p.getMultiplier());

			Printer.printMSG(
					h.getName() + " used " + p.getName() + " to debuff " + m.getName() + " 's " + p.getTargetAbility());
			break;
		default:
			Printer.printSetMessage("invalidAbility");
			break;
		}
	}

	private void usePotionOnFriendly(LegendsPotion p, LegendsHero h) {
		boolean flag = true;
		while (flag) {
			Printer.printMSG("Please pick an ally to assist!");

			int i = 0;
			for (LegendsHero hero : matchings.keySet()) {
				System.out.println("[" + i + "] " + hero.getName());
				i++;
			}
			System.out.println();

			int choice = in.nextInt();

			if (choice >= 0 && choice < matchings.keySet().size()) {
				LegendsHero choiceHero = (LegendsHero) matchings.keySet().toArray()[choice];

				switch (p.getTargetAbility()) {
				case "Strength":
				case "Defense":
				case "Dodge":
					((LegendsHeroStats) choiceHero.getEntityStats()).applyBuff(p.getTargetAbility(), p.getMultiplier());

					Printer.printMSG(h.getName() + " used " + p.getName() + " to boost " + choiceHero.getName() + " 's "
							+ p.getTargetAbility());

					flag = false;
					break;
				default:
					Printer.printSetMessage("invalidAbility");
					break;
				}
			} else
				Printer.printSetMessage("invalidResponse");
		}
	}

	/*
	 * The two methods below make up the revive sequence. The first method is the
	 * core revive, it calls the second to get the Hero to revive, and then revives
	 * them.
	 * 
	 * The second method simply allows the Player to pick which Hero to revive.
	 */
	private void revive(LegendsHero h) {
		if (this.deadHeroes.isEmpty()) {
			Printer.printMSG("No heroes are dead!");
			return;
		}

		LegendsHero toRevive = this.pickRevive();

		Printer.printMSG(h.getName() + " has revived " + toRevive.getName());

		((LegendsHeroStats) toRevive.getEntityStats()).regenHealth(1);
		((LegendsHeroStats) toRevive.getEntityStats()).regenMana(1);
		this.leftOverHeroes.add(toRevive);
	}

	private LegendsHero pickRevive() {

		Printer.printMSG("Currently dead Heroes:");

		for (int i = 0; i < this.deadHeroes.size(); i++)
			System.out.println("[" + i + "] " + deadHeroes.get(i).getName());

		Printer.printMSG("Please enter the number of the Hero you want to revive:");

		while (true) {
			int picked = in.nextInt();
			if (picked >= 0 && picked < deadHeroes.size())
				return deadHeroes.remove(picked);

			Printer.printSetMessage("invalidResponse");
		}
	}

	/*
	 * The following three methods make up the castSpell section. Together they
	 * handle the entire sequence of picking and casting a spell on a Friendly or
	 * Enemy.
	 * 
	 * The first method is the core, it picks what spell to use, and then moves to
	 * either the Friendly or Enemy methods in relation to that choice.
	 * 
	 * The Enemy casts the spell on the Monster the Hero is engaged with, it also
	 * deals with all the requirements for damage/dodging/boosts etc...
	 * 
	 * Meanwhile the Friendly method allows for the choice of which Friendly to
	 * boost, and deals with the required necessities.
	 */
	private void castSpell(LegendsHero h, LegendsMonster m) {
		LegendsSpell spellToCast = h.getInventory().pickSpell();

		if (spellToCast == null)
			return;

		if (spellToCast.getIsBuff())
			this.castSpellOnFriendly(spellToCast, h);
		else
			this.castSpellOnEnemy(spellToCast, h, m);
	}

	private void castSpellOnEnemy(LegendsSpell spellToCast, LegendsHero h, LegendsMonster m) {
		double dodgeChance = ((LegendsMonsterStats) m.getEntityStats()).getDodge() * 0.1;

		if (Math.random() > dodgeChance && spellToCast != null) {
			if (((LegendsHeroStats) h.getEntityStats()).getCurrMana() < spellToCast.getManaRequired())
				Printer.printMSG(h.getName() + " does not have enough Mana to cast " + spellToCast.getName());
			else {
				((LegendsHeroStats) h.getEntityStats()).spendMana(spellToCast.getManaRequired());

				int spellBaseDamage = (int) Math.round(spellToCast.getMaxDamage()
						* (h.getEntityStats().getLevel() / ItemGenerator.LEVEL_TO_UNLOCK_EVERYTHING));

				double dexMultiplier = (statToBoost.equals("Dexterity")) ? 1.10 : 1.0;

				int dexDamage = (int) Math.round(spellBaseDamage
						+ ((((LegendsHeroStats) h.getEntityStats()).getDexterity() * dexMultiplier) / 10000.0)
								* spellBaseDamage);

				int damage = dexDamage - ((LegendsMonsterStats) m.getEntityStats()).getDefense();

				Printer.printMSG(h.getName() + " attacked " + m.getName() + " using " + spellToCast.getName()
						+ " dealing " + damage + " damage to them!");

				switch (spellToCast.getTargetAbility()) {
				case "Strength":
				case "Defense":
				case "Dodge":
					((LegendsMonsterStats) m.getEntityStats()).applyDebuff(spellToCast.getTargetAbility(),
							spellToCast.getMultiplier());

					Printer.printMSG(h.getName() + " attacked " + m.getName() + " using " + spellToCast.getName()
							+ " and applied a debuff to their " + spellToCast.getTargetAbility());

					break;
				default:
					Printer.printSetMessage("invalidAbility");
					break;
				}
			}
		} else
			Printer.printMSG(m.getName() + " dodged the magic attack from " + h.getName());
	}

	private void castSpellOnFriendly(LegendsSpell spellToCast, LegendsHero h) {
		boolean flag = true;
		while (flag) {
			Printer.printMSG("Please pick an ally to assist!");
			int i = 0;
			for (LegendsHero hero : matchings.keySet()) {
				System.out.println("[" + i + "] " + hero.getName());
				i++;
			}
			System.out.println();

			int choice = in.nextInt();

			if (choice >= 0 && choice < matchings.keySet().size()) {
				LegendsHero choiceHero = (LegendsHero) matchings.keySet().toArray()[choice];

				switch (spellToCast.getTargetAbility()) {
				case "Strength":
				case "Defense":
				case "Dodge":
					((LegendsHeroStats) choiceHero.getEntityStats()).applyBuff(spellToCast.getTargetAbility(),
							spellToCast.getMultiplier());

					Printer.printMSG(h.getName() + " used " + spellToCast.getName() + " to boost "
							+ choiceHero.getName() + " 's " + spellToCast.getTargetAbility());

					flag = false;
					break;
				default:
					Printer.printSetMessage("invalidAbility");
					break;
				}
			} else
				Printer.printSetMessage("invalidResponse");
		}
	}

	/* ========================= */
	/* Main Monster Game Methods */
	/* ========================= */

	private void attackHero(LegendsHero h, LegendsMonster m) {
		double agilityMultiplier = (statToBoost.equals("Agility")) ? 1.10 : 1.0;
		double dodgeChance = ((LegendsHeroStats) h.getEntityStats()).getAgility() * agilityMultiplier * 0.002;

		if (Math.random() > dodgeChance) {
			double defenseMultiplier = h.getInventory().getEquippedArmour().getMultiplier();

			int damage = ((LegendsMonsterStats) m.getEntityStats()).getStrength()
					- ((int) Math.round(((LegendsMonsterStats) m.getEntityStats()).getStrength() * defenseMultiplier));

			if (damage < 0)
				damage = 0;

			h.getInventory().getEquippedArmour().takeDamage(
					(int) Math.round(((LegendsMonsterStats) m.getEntityStats()).getStrength() * defenseMultiplier));

			h.getEntityStats().takeDamage(damage);

			Printer.printMSG(m.getName() + " has attacked " + h.getName() + " dealing " + damage + " damage!");
		} else
			Printer.printMSG(h.getName() + " has dodged " + m.getName() + "'s attack!");
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public String getStatToBoost() {
		return statToBoost;
	}

	private void setStatToBoost(String statToBoost) {
		if (Plains.POSSIBLE_BUFFS.indexOf(statToBoost) == -1) {
			System.out.println("Invalid Stat To Boost!");
			return;
		}
		this.statToBoost = statToBoost;
	}

	/* ==================== */
	/* Aux Matching Methods */
	/* ==================== */

	/*
	 * Adjusts current matchings if there are both leftover Heroes and Monsters
	 */
	private void reMatch() {
		while ((!leftOverHeroes.isEmpty()) && (!leftOverMonsters.isEmpty()))
			this.matchings.put(leftOverHeroes.remove(0), leftOverMonsters.remove(0));
	}

	/*
	 * Makes the initial Matchings
	 */
	private void makeMatchings() {
		@SuppressWarnings("unchecked")
		ArrayList<LegendsMonster> copyMonsters = (ArrayList<LegendsMonster>) this.getMonstersOnCell().clone();
		@SuppressWarnings("unchecked")
		ArrayList<LegendsHero> copyHeroes = (ArrayList<LegendsHero>) this.getHeroesOnCell().clone();
		while ((!copyHeroes.isEmpty()) && (!copyMonsters.isEmpty())) {
			this.matchings.put(copyHeroes.remove(0), copyMonsters.remove(0));
		}

		for (LegendsHero h : copyHeroes)
			this.leftOverHeroes.add(h);

		for (LegendsMonster m : copyMonsters)
			this.leftOverMonsters.add(m);
	}
}