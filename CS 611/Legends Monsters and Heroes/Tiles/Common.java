/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Tiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import Game.LegendsHero;
import Game.LegendsItem;
import Game.LegendsMonster;
import Game.LegendsPlayer;
import Game.Creation.ItemCreation;
import Game.Creation.MonsterCreation;
import Game.Util.LegendsGameEntities;
import Game.Util.LegendsMapTile;
import Items.Armour;
import Items.Potion;
import Items.Spell;
import Items.Weapon;
import Util.Entity;
import Util.Random;

public class Common extends LegendsMapTile {

	// Configurable amount of monsters to generate each time. This number isn't for
	// the total lifespan, rather for each time monsters are generated, which is at
	// the tile creation, and whenever it runs out of monsters.
	public static final int AMOUNT_OF_MONSTERS_TO_GENERATE = 10;

	private ArrayList<LegendsMonster> monsters;

	private boolean encounterOccured;
	private boolean playersWon;
	private boolean inEncounter;

	private HashMap<LegendsHero, LegendsMonster> matches;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Common(int ID, int xCoord, int yCoord) {
		super(ID, xCoord, yCoord);
		this.generateMonsters();
		this.resetEncounterBooleans();
	}

	/* ================= */
	/* Main Game Methods */
	/* ================= */

	@Override
	public void onEntry(LegendsGameEntities entities) {
		this.resetEncounterBooleans();

		this.printer("Entered a Common Space (Coords " + this.getXCoord() + ", " + this.getYCoord() + ")");

		boolean generateMonsters = Math.random() < 0.5;
		this.setEncounterOccured(generateMonsters);

		if (!generateMonsters) {
			this.noEncounter(entities);
		} else {
			this.encounter(entities);
		}

		this.onExit(entities);
	}

	@Override
	public void onExit(LegendsGameEntities entities) {
		if (this.getPlayerQuit())
			return;

		if (!this.getEncounterOccured())
			return;

		if (!this.getPlayersWon())
			return;

		for (Entity e : entities.getEntities()) {
			for (LegendsHero hero : ((LegendsPlayer) e).getHeroes().getHeroes()) {
				hero.getHeroRelatedStats().regendHP(1);
				hero.getHeroRelatedStats().regendMana(1);
			}
		}

		this.printer("All heroes have regenerated their entire health and mana");

		this.setPlayerQuit(false);
		this.resetEncounterBooleans();
	}

	private void noEncounter(LegendsGameEntities entities) {
		this.printer("This space is clear of any enemies");
	}

	private void encounter(LegendsGameEntities entities) {
		this.setMatches(new HashMap<LegendsHero, LegendsMonster>());
		for (Entity e : entities.getEntities()) {
			ArrayList<LegendsHero> heroes = ((LegendsPlayer) e).getHeroes().getHeroes();
			int amountToGet = heroes.size();
			for (int i = 0; i < amountToGet; i++)
				this.getMatches().put(heroes.get(i), this.getRandomMonster());
		}

		this.printer("You have encountered enemies!!");
		this.printEnemyStats();
		this.printer("Engagement Beginning!");

		int heroesAlive = this.getMatches().keySet().size();
		int monstersAlive = this.getMatches().keySet().size();
		ArrayList<LegendsHero> noOpponentHeroes = new ArrayList<LegendsHero>();
		ArrayList<LegendsMonster> noOpponentMonsters = new ArrayList<LegendsMonster>();

		// Rounds
		while (true) {

			// For each player in the game (useful for future implementations)
			for (Entity e : entities.getEntities()) {
				// Get Player Heroes
				ArrayList<LegendsHero> heroes = ((LegendsPlayer) e).getHeroes().getHeroes();

				// For each hero
				for (LegendsHero hero : heroes) {
					if (hero.getHeroRelatedStats().getCurrentHP() <= 0 || this.getMatches().get(hero) == null)
						continue;

					this.printer("Current Round Players: " + hero + " vs " + this.getMatches().get(hero));
					this.printer(hero + "'s class" + hero.getEntityClass() + hero + "'s stats: \n"
							+ hero.getHeroRelatedStats());
					this.printer(this.getMatches().get(hero) + "'s class" + this.getMatches().get(hero).getEntityClass()
							+ "\n" + this.getMatches().get(hero) + "'s stats: \n"
							+ this.getMatches().get(hero).getMonsterRelatedStats());

					String move = this.getNextMove(e, hero);
					if (move.equals("Revive")) {
						LegendsHero revived = this.revive(e, hero, heroes);
						revived.getHeroRelatedStats().regendHP(1);
						revived.getHeroRelatedStats().regendMana(1);

						if (noOpponentMonsters.size() == 0)
							noOpponentHeroes.add(revived);
						else
							this.getMatches().replace(revived, noOpponentMonsters.remove(0));

						LegendsPlayer revivedEntity = this.findEntity(entities.getEntities(), revived);
						revivedEntity.getGameRelatedStats().addTimesRevived();
						((LegendsPlayer) e).getGameRelatedStats().addFriendliesRevived();
						hero.getGameRelatedStats().addFriendliesRevived();
						revived.getGameRelatedStats().addTimesRevived();
					} else
						this.preformMove(e, hero, move);

					this.preformMonsterMove(e, hero);

					if (this.getPlayerQuit())
						break;

					LegendsMonster monster = this.getMatches().get(hero);

					if (monster.getMonsterRelatedStats().getCurrentHP() <= 0) {
						this.printer(monster + " has died!");
						monstersAlive--;
						hero.getGameRelatedStats().addKill();
						monster.getGameRelatedStats().addDeaths();
						((LegendsPlayer) e).getGameRelatedStats().addKill();

						hero.getHeroRelatedStats().addMoney(100 * monster.getMonsterRelatedStats().getLevel());
						boolean leveledUp = hero.getHeroRelatedStats().addExp(2);

						if (leveledUp)
							this.levelUp(e, hero);

						if (noOpponentMonsters.size() != 0)
							this.getMatches().replace(hero, noOpponentMonsters.remove(0));
						else {
							noOpponentHeroes.add(hero);
							this.getMatches().replace(hero, null);
						}
					}

					if (hero.getHeroRelatedStats().getCurrentHP() <= 0) {
						this.printer(hero + " has died!");

						hero.getGameRelatedStats().addDeaths();
						((LegendsPlayer) e).getGameRelatedStats().addDeaths();
						monster.getGameRelatedStats().addKill();
						heroesAlive--;

						if (noOpponentHeroes.size() != 0)
							this.getMatches().replace(noOpponentHeroes.get(0), monster);
						else
							noOpponentMonsters.add(this.getMatches().get(hero));

						this.getMatches().replace(hero, null);
					}

					if (hero.getHeroRelatedStats().getCurrentHP() > 0)
						this.regenPostRound(e, hero);
				}

				if (this.getPlayerQuit())
					break;

			}

			if (heroesAlive != 0)
				this.printer(
						"Round over! All heroes that are alive have regenerated 10% of their health, and 10% of their mana! "
								+ heroesAlive + " " + monstersAlive);

			if (heroesAlive == 0)
				break;
			if (monstersAlive == 0)
				break;
			if (this.getPlayerQuit() == true)
				break;
		}

		// All round are over, move on!
		this.setPlayersWon(heroesAlive != 0);
		String result = "Players won!";
		if (!this.getPlayersWon())
			result = "Monster Won!";

		this.printer("Encounter over! " + result);
	}

	/* ======================== */
	/* Util Heroes Game Methods */
	/* ======================== */

	/*
	 * Main util methods to both get a Hero's next move, and perform it (outside of
	 * Revive, which is performed elsewhere). The First get's the String for the
	 * Move, and returns it, and the second filters the String, and preforms the
	 * move.
	 */
	@Override
	public String getNextMove(Entity e, LegendsHero hero) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String input = "";
		while (true) {
			this.printer(e.getName() + " please enter the fighting action for " + hero.getName()
					+ " (Possible action: 'Attack'/'Spell'/'Inventory'/'I'/'Q'/'Revive')");
			input = scanner.nextLine();

			if (input.equalsIgnoreCase("Attack"))
				return "Attack";
			else if (input.equals("Spell"))
				return "Spell";
			else if (input.equalsIgnoreCase("Inventory"))
				return "Inventory";
			else if (input.equalsIgnoreCase("I"))
				return "I";
			else if (input.equalsIgnoreCase("Q"))
				return "Q";
			else if (input.equalsIgnoreCase("Revive"))
				return "Revive";
			else
				this.printer("Invalid input, trying again!");
		}
	}

	@Override
	public void preformMove(Entity e, LegendsHero hero, String move) {
		if (move.equals("I"))
			this.displayInfo();
		else if (move.equals("Q"))
			this.quit();
		else if (move.equals("Attack"))
			this.attack(e, hero);
		else if (move.equals("Spell"))
			this.castSpell(e, hero);
		else if (move.equals("Inventory"))
			this.accessInventory(e, hero);
	}

	/*
	 * The two methods used for Reviving purposes, the first to get the Hero they
	 * want to revive, by presenting them with a list of heroes with 0 health, and
	 * prompting the user to pick one, returning it.
	 * 
	 * The second serves to find the Entity (Player) the Hero belongs to, so we can
	 * add the proper statistic.
	 */
	private LegendsHero revive(Entity e, LegendsHero h, ArrayList<LegendsHero> heroes) {
		ArrayList<LegendsHero> deadHeroes = new ArrayList<LegendsHero>();

		for (LegendsHero he : heroes)
			if (he.getHeroRelatedStats().getCurrentHP() == 0)
				deadHeroes.add(he);

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String input = "";

		this.printer(e.getName() + " please pick which Hero " + h
				+ " will revive: (either enter the number representing the hero, or 'l' to leave)");

		int i = 0;
		for (LegendsHero s : deadHeroes) {
			System.out.println("[" + i + "] " + s);
			i++;
		}

		if (i == 0)
			this.printer("No heroes need reviving!");

		input = scanner.nextLine();

		if (input.equals("l"))
			return null;
		else {
			int heroIndex = Integer.valueOf(input);
			return deadHeroes.get(heroIndex);
		}
	}

	private LegendsPlayer findEntity(ArrayList<Entity> entities, LegendsHero hero) {

		for (Entity e : entities)
			for (LegendsHero h : ((LegendsPlayer) e).getHeroes().getHeroes())
				if (h.equals(hero))
					return ((LegendsPlayer) e);

		return null;
	}

	/*
	 * Display Info method, prints out information for all current Heroes and
	 * Monsters at Play
	 */

	@Override
	public void displayInfo() {
		this.printer("Printing Information");

		System.out.println("Hero Information");
		for (LegendsHero hero : this.getMatches().keySet())
			this.printer(hero + "'s class" + hero.getEntityClass() + "\n" + hero + "'s stats: \n"
					+ hero.getHeroRelatedStats());

		System.out.println();

		System.out.println("Monster Information");
		this.printEnemyStats();

		System.out.println();
	}

	/*
	 * Quits the game for all Players
	 */

	@Override
	public void quit() {
		this.resetEncounterBooleans();
		this.setPlayerQuit(true);
	}

	/*
	 * Method to perform regular attack with currently equipped weapon, or the
	 * Hero's fists. Deals with damage dealt by the Hero, received by the Monster,
	 * and durability of the weapon.
	 */

	public void attack(Entity e, LegendsHero h) {
		LegendsMonster monster = this.getMatches().get(h);

		double dodgeChance = monster.getMonsterRelatedStats().getDodge() * 0.1;
		if (Math.random() > dodgeChance) {
			int weaponDamage = 100;
			if (h.getEquippedWeapon() != null)
				weaponDamage = h.getEquippedWeapon().getType().getDamage();

			weaponDamage += (int) Math.round(h.getHeroRelatedStats().getStrength() * 0.05);
			int damage = weaponDamage - monster.getMonsterRelatedStats().getDefense();

			if (damage < 0)
				damage = 0;

			monster.getMonsterRelatedStats().takeDamage(damage);
			if (h.getEquippedWeapon() != null) {
				h.getEquippedWeapon().loseDurability(1);

				if (h.getEquippedWeapon().getCurrentDurability() <= 0) {
					this.printer(h + "'s weapon has broken!");
					h.deleteEquippedWeapon();
				}
			}

			String weaponName = "their fists";
			if (h.getEquippedWeapon() != null)
				weaponName = h.getEquippedWeapon().getName();

			this.printer(h.getName() + " attacked " + monster.getName() + " using " + weaponName + " dealing " + damage
					+ " damage to them!");
		} else {
			this.printer(monster.getName() + " dodged the attack from " + h.getName());
		}
	}

	/*
	 * Two methods used for Heroes to cast spells, the first is the general one,
	 * which gets a spell, and deals with damage/buffs/debuffs according to spell
	 * type.
	 * 
	 * The second method allows the Hero to pick which of their Known Spells to use.
	 */

	private void castSpell(Entity e, LegendsHero h) {
		Spell spellToCast = this.pickSpell(e, h);
		LegendsMonster monster = this.getMatches().get(h);

		double dodgeChance = monster.getMonsterRelatedStats().getDodge() * 0.1;

		if (spellToCast == null || Math.random() > dodgeChance) {
			this.printer(monster.getName() + " dodged the magic attack from " + h.getName());
		} else {
			if (h.getHeroRelatedStats().getCurrentMana() < spellToCast.getType().getManaCost()) {
				this.printer(h.getName() + " does not have enough Mana to cast " + spellToCast);
			} else {
				h.getHeroRelatedStats().spendMana(spellToCast.getType().getManaCost());
				int spellBaseDamage = (int) Math.round(spellToCast.getType().getDamageMax()
						* (h.getHeroRelatedStats().getLevel() / ItemCreation.LEVEL_TO_UNLOCK_EVERYTHING));
				int damage = (int) Math
						.round(spellBaseDamage + (h.getHeroRelatedStats().getDexterity() / 10000.0) * spellBaseDamage)
						- monster.getMonsterRelatedStats().getDefense();

				this.printer(h.getName() + " attacked " + monster.getName() + " using " + spellToCast.getName()
						+ " dealing " + damage + " damage to them!");
			}
		}
	}

	private Spell pickSpell(Entity e, LegendsHero h) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String input = "";

		this.printer(e.getName() + " please pick which of " + h.getName()
				+ "'s spells you would like to use: (enter either the spell number, or 'l' to leave)");

		int i = 0;
		for (Spell s : h.getKnownSpells()) {
			System.out.println("[" + i + "] " + s);
			i++;
		}

		if (i == 0)
			System.out.println("No know Spells!");

		input = scanner.nextLine();

		if (input.equalsIgnoreCase("l"))
			return null;
		else {
			int spellIndex = Integer.valueOf(input);
			return h.getKnownSpells().get(spellIndex);
		}

	}

	/*
	 * Three methods that allow a Hero to access their inventory, pick an item, and
	 * use it.
	 * 
	 * The first method gets the picked item, and then depending on what it is, uses
	 * it. If its armour or a weapon, it replaces the currently equipped ones, if
	 * it's a potion it goes to another method.
	 * 
	 * The second method allows Heroes to select which Item to use, presenting them
	 * a list of the items in their inventory, and returning the picked one.
	 * 
	 * The Final method utilizes the Potion selected, dealing with Buffs/Debuffs and
	 * spending the Potion.
	 */

	private void accessInventory(Entity e, LegendsHero h) {
		LegendsItem itemToUse = this.pickItem(e, h);

		if (itemToUse instanceof Potion) {
			this.usePotion((Potion) itemToUse, e, h);
		} else if (itemToUse instanceof Armour) {
			h.getHeroInventory().removeItem(itemToUse.getID());
			Armour castedItem = (Armour) itemToUse;

			Armour itemToReplace = null;
			if (castedItem.getSlot().equals("Head")) {
				itemToReplace = h.getHeroArmor().getHeadPiece();
				h.getHeroArmor().setHeadPiece(castedItem);
				this.printer("Replacing your current Head Piece Armour (" + itemToReplace + ") with " + castedItem);

			} else if (castedItem.getSlot().equals("Chest")) {
				itemToReplace = h.getHeroArmor().getChestPiece();
				h.getHeroArmor().setChestPiece(castedItem);
				this.printer("Replacing your current Chest Piece Armour (" + itemToReplace + ") with " + castedItem);

			} else if (castedItem.getSlot().equals("Legs")) {
				itemToReplace = h.getHeroArmor().getLegPiece();
				h.getHeroArmor().setLegPiece(castedItem);
				this.printer("Replacing your current Leg Piece Armour (" + itemToReplace + ") with " + castedItem);

			} else {
				itemToReplace = h.getHeroArmor().getFeetPiece();
				h.getHeroArmor().setFeetPiece(castedItem);
				this.printer("Replacing your current Feet Piece Armour (" + itemToReplace + ") with " + castedItem);
			}

			h.getHeroInventory().addItem(itemToReplace);

		} else if (itemToUse instanceof Weapon) {
			h.getHeroInventory().removeItem(itemToUse.getID());
			Weapon castedItem = (Weapon) itemToUse;

			this.printer("Replaced your current equipped weapon (" + h.getEquippedWeapon() + ") with " + castedItem);
			h.switchWeapon(castedItem);
		}

	}

	private LegendsItem pickItem(Entity e, LegendsHero h) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String input = "";

		this.printer(e.getName() + " please pick which of " + h.getName()
				+ "'s items you would like to use: (enter either the spell number, or 'l' to leave)");

		int i = 0;
		for (LegendsItem s : h.getHeroInventory().getInventory()) {
			System.out.println("[" + i + "] " + s);
			i++;
		}

		if (i == 0)
			System.out.println("Empty Inventory!");

		input = scanner.nextLine();

		if (input.equalsIgnoreCase("l"))
			return null;
		else {
			int itemIndex = Integer.valueOf(input);
			return h.getHeroInventory().getInventory().get(itemIndex);
		}
	}

	private void usePotion(Potion p, Entity e, LegendsHero h) {
		p.use();
		h.getHeroInventory().removeItem(p.getID());

		if (p.getType().getIsBuff()) {
			String ability = p.getType().getTargetAbility();
			if (ability.equals("Dexterity"))
				h.getHeroRelatedStats().increaseDexterity(
						(int) Math.round(h.getHeroRelatedStats().getDexterity() * p.getType().getMultiplier()));
			else if (ability.equals("Strength"))
				h.getHeroRelatedStats().increaseStrength(
						(int) Math.round(h.getHeroRelatedStats().getStrength() * p.getType().getMultiplier()));
			else
				h.getHeroRelatedStats().increaseAgility(
						(int) Math.round(h.getHeroRelatedStats().getAgility() * p.getType().getMultiplier()));

			this.printer(h.getName() + " used the " + p.getName() + " potion to increase their " + ability + " by"
					+ p.getType().getMultiplier() * 100);
		} else {
			String ability = p.getType().getTargetAbility();
			LegendsMonster monster = this.getMatches().get(h);

			if (ability.equals("Defense"))
				monster.getMonsterRelatedStats()
						.setDefense((int) Math.round(monster.getMonsterRelatedStats().getDefense()
								- monster.getMonsterRelatedStats().getDefense() * p.getType().getMultiplier()));
			else if (ability.equals("Strength"))
				monster.getMonsterRelatedStats()
						.setStrength((int) Math.round(monster.getMonsterRelatedStats().getStrength()
								- monster.getMonsterRelatedStats().getStrength() * p.getType().getMultiplier()));
			else
				monster.getMonsterRelatedStats().setDodge((int) Math.round(monster.getMonsterRelatedStats().getDodge()
						- monster.getMonsterRelatedStats().getDodge() * p.getType().getMultiplier()));

			this.printer(h.getName() + " used the " + p.getName() + " potion to decrease " + monster.getName() + "'s "
					+ ability + " by" + p.getType().getMultiplier() * 100);
		}
	}

	/* ========================= */
	/* Util Monster Game Methods */
	/* ========================= */

	/*
	 * This method just gets a Monster to attack a specific Hero, dealing with the
	 * given damage/dodging/defense/etc...
	 */
	private void preformMonsterMove(Entity e, LegendsHero h) {
		LegendsMonster monster = this.getMatches().get(h);

		if (monster.getMonsterRelatedStats().getCurrentHP() <= 0)
			return;

		double dodge = h.getHeroRelatedStats().getAgility() * 0.002;
		if (Math.random() > dodge) {
			double headMultiplier = 0.0;
			if (h.getHeroArmor().getHeadPiece() != null)
				headMultiplier = h.getHeroArmor().getHeadPiece().getType().getDefenseMultiplier();

			double chestMultiplier = 0.0;
			if (h.getHeroArmor().getChestPiece() != null)
				chestMultiplier = h.getHeroArmor().getChestPiece().getType().getDefenseMultiplier();

			double legMultiplier = 0.0;
			if (h.getHeroArmor().getLegPiece() != null)
				legMultiplier = h.getHeroArmor().getLegPiece().getType().getDefenseMultiplier();

			double feetMultiplier = 0.0;
			if (h.getHeroArmor().getFeetPiece() != null)
				feetMultiplier = h.getHeroArmor().getFeetPiece().getType().getDefenseMultiplier();

			double averageMultiplier = (headMultiplier + chestMultiplier + legMultiplier + feetMultiplier) / 4.0;
			int damage = monster.getMonsterRelatedStats().getStrength()
					- (int) Math.round(monster.getMonsterRelatedStats().getStrength() * averageMultiplier);

			if (damage < 0)
				damage = 0;

			h.getHeroArmor()
					.takeDamage((int) Math.round(monster.getMonsterRelatedStats().getStrength() * averageMultiplier));

			h.getHeroRelatedStats().takeDamage(damage);

			this.printer(h.getName() + " took " + damage + " damage from " + monster.getName());
		} else {
			this.printer(h.getName() + " dodged an attack from " + monster.getName());
		}
	}

	/* ====================== */
	/* Util Misc Game Methods */
	/* ====================== */

	/*
	 * Levels up a Hero, recalculating everything
	 */
	private void levelUp(Entity e, LegendsHero h) {
		this.printer(h + " has leveled up!");
		int currentAgility = h.getHeroRelatedStats().getAgility();
		int currentStrength = h.getHeroRelatedStats().getStrength();
		int currentDexterity = h.getHeroRelatedStats().getDexterity();
		int currentMaxMana = h.getHeroRelatedStats().getMaxMana();
		int currentMaxHP = h.getHeroRelatedStats().getMaxHP();

		h.getHeroRelatedStats().increaseAgility((int) Math
				.round(currentAgility * 0.05 + (currentAgility * h.getHeroClass().getAgilityBoost() - currentAgility)));
		h.getHeroRelatedStats().increaseStrength((int) Math.round(
				currentStrength * 0.05 + (currentStrength * h.getHeroClass().getStrengthBoost() - currentStrength)));
		h.getHeroRelatedStats().increaseDexterity((int) Math.round(currentDexterity * 0.05
				+ (currentDexterity * h.getHeroClass().getDexterityBoost() - currentDexterity)));
		h.getHeroRelatedStats().increaseMaxHP((int) Math.round(0.1 * currentMaxHP));
		h.getHeroRelatedStats().increaseMaxMana((int) Math.round(0.1 * currentMaxMana));

		h.getHeroRelatedStats().regendHP(1);
		h.getHeroRelatedStats().regendMana(1);
	}

	/*
	 * Regens a hero's HP and Mana 10% post round
	 */
	private void regenPostRound(Entity e, LegendsHero h) {
		if (h.getHeroRelatedStats().getCurrentHP() <= 0)
			return;

		h.getHeroRelatedStats().regendHP(0.10);
		h.getHeroRelatedStats().regendMana(0.10);
	}

	/*
	 * Prints the stats of all Monsters
	 */
	private void printEnemyStats() {
		System.out.println();
		System.out.println("Enemy Stats:");

		for (LegendsMonster monster : this.getMatches().values())
			if (monster != null)
				this.printer(monster + "'s class" + monster.getEntityClass() + "\n" + monster + "'s stats: \n"
						+ monster.getMonsterRelatedStats());

		System.out.println();
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	private void generateMonsters() {
		this.setMonsters(MonsterCreation.generateMonster(Common.AMOUNT_OF_MONSTERS_TO_GENERATE));
	}

	private void setMonsters(ArrayList<LegendsMonster> monsters) {
		this.monsters = monsters;
	}

	public ArrayList<LegendsMonster> getMonsters() {
		return this.monsters;
	}

	private LegendsMonster getRandomMonster() {
		if (this.getMonsters().size() == 0)
			this.generateMonsters();

		return this.monsters.remove(Random.randomInt(0, this.getMonsters().size()));
	}

	private void resetEncounterBooleans() {
		this.setEncounterOccured(false);
		this.setPlayersWon(false);
		this.setInEncounter(false);
	}

	private void setInEncounter(boolean inEncounter) {
		this.inEncounter = inEncounter;
	}

	private void setEncounterOccured(boolean encounterOccured) {
		this.encounterOccured = encounterOccured;
	}

	private void setPlayersWon(boolean playersWon) {
		this.playersWon = playersWon;
	}

	public boolean getPlayersWon() {
		return this.playersWon;
	}

	public boolean getEncounterOccured() {
		return this.encounterOccured;
	}

	@SuppressWarnings("unused")
	private boolean getInEncounter() {
		return this.inEncounter;
	}

	public HashMap<LegendsHero, LegendsMonster> getMatches() {
		return this.matches;
	}

	private void setMatches(HashMap<LegendsHero, LegendsMonster> matches) {
		this.matches = matches;
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	public String toString() {
		return " ";
	}
}
