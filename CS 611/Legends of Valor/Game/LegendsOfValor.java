/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Game;

import java.util.*;

import Entities.LegendsHero;
import Entities.LegendsMonster;
import Items.LegendsArmour;
import Items.LegendsItem;
import Items.LegendsWeapon;
import Map.LegendsMap;
import Map.Places.Place;
import Map.Places.Market.MonsterNexus;
import Map.Places.Market.Nexus;
import Map.Places.Market.PlayerNexus;
import Util.Printer;
import Util.Random;
import Util.State;
import Util.Token;
import Util.Creation.EntityGenerator;

public class LegendsOfValor extends RPG {

	// These two values are used the Procedural generation of items and entities!
	public static int ENTITY_IDS;
	public static int ITEM_IDS;

	private ArrayList<LegendsMonster> monsters;

	private int roundCounter;

	private Iterator<LegendsHero> itr;
	private Scanner in;

	/* ===================== */
	/* Game Building Methods */
	/* ===================== */

	/*
	 * Initializes the game
	 */
	public void initObjects() {
		this.in = new Scanner(System.in);
		
		this.setMap(new LegendsMap(5, 8));
		itr = getPlayer().getTeam().iterator();
		monsters = new ArrayList<LegendsMonster>();
		generateMonsters(3);
		for (LegendsMonster m : monsters) {
			System.out.println(m.getName());
		}
		spawnEntities();
	}

	/*
	 * Spawns the entities on the map
	 */
	private void spawnEntities() {
		int trackCounter = 0;
		for (int i = 0; i < 3; i++) {
			Place heroSpawnPlace = this.getMap().getPlace(trackCounter, this.getMap().getMapDimen() - 1, 0);
			Place monsterSpawnPlace = this.getMap().getPlace(trackCounter, 0, 0);
			getPlayer().getTeam().getHeroes().get(i).setCurrPlace(heroSpawnPlace);
			heroSpawnPlace.addHeroOnCell(getPlayer().getTeam().getHeroes().get(i));
			getPlayer().getTeam().getHeroes().get(i).setSpawnPlace(heroSpawnPlace);
			monsters.get(i).setCurrPlace(monsterSpawnPlace);
			monsterSpawnPlace.addMonsterOnCell(monsters.get(i));
			monsters.get(i).setSpawnPlace(monsterSpawnPlace);

			trackCounter += 2;
		}
	}

	/*
	 * Generates a random set of toGenerate monsters, adding them to the monsters
	 * list
	 */
	public void generateMonsters(int toGenerate) {
		ArrayList<LegendsMonster> allMonsters = new ArrayList<LegendsMonster>();
		allMonsters = EntityGenerator.generateMonster(5);
		for (int i = 0; i < toGenerate; i++) {
			monsters.add(allMonsters.remove(Random.randomInt(0, allMonsters.size() - 1)));
			monsters.get(i).setToken(new Token("VoV"));
			monsters.get(i).setName(monsters.get(i).getName());
		}
	}

	/* ================= */
	/* Main Game Methods */
	/* ================= */

	/*
	 * Prints available actions for the current Hero
	 */
	public void printActions(LegendsHero currHero) {
		System.out.println();

		String leftAlignFormat = "| %-15s | %-4s |%n";

		System.out.format("+------------------------+%n");
		System.out.format("|         ACTIONS        |%n");
		System.out.format("+-----------------+------+%n");
		System.out.format(leftAlignFormat, "move up", "w");
		System.out.format(leftAlignFormat, "move down", "s");
		System.out.format(leftAlignFormat, "move left", "a");
		System.out.format(leftAlignFormat, "move right", "d");
		System.out.format(leftAlignFormat, "check info", "i");
		System.out.format(leftAlignFormat, "show map", "m");
		System.out.format(leftAlignFormat, "check inventory", "c");
		System.out.format(leftAlignFormat, "teleport", "t");
		System.out.format(leftAlignFormat, "back", "b");

		if (currHero.getCurrPlace() instanceof Nexus)
			System.out.format(leftAlignFormat, "enter market", "e");

		System.out.format(leftAlignFormat, "quit", "q");
		System.out.format("+-----------------+------+%n");

		System.out.println();
	}

	/*
	 * This is the main method for the entire game, it processes whatever input the
	 * user entered, and proceeds to execute that move if it can (ie: if the move is
	 * legal at the time)
	 * 
	 * It also does a few other things, as this method is called each time a hero is
	 * moved, it keeps track of how many moves have occurred, hence keeping track of
	 * rounds. Once the time comes, it'll attempt to respawn heroes, and spawn new
	 * monsters. It also moves the monsters when time comes
	 */
	public void processUserInput() {
		this.roundCounter++;

		if (roundCounter % (3 * 10) == 0)
			spawnNewMonsters();
		if (roundCounter % (3 * 15) == 0)
			respawnHeroes();

		LegendsHero currHero = itr.next();
		int currRowID = currHero.getCurrPlace().getRowID();
		int currColID = currHero.getCurrPlace().getColID();

		if (currHero.getEntityStats().getCurrHP() <= 0)
			return;

		boolean cont = true;
		while (cont) {
			this.getMap().print();

			Printer.printMSG("What would you like to do with " + currHero.getName() + "?");

			printActions(currHero);

			String input = in.nextLine();
			System.out.println();

			switch (input.toLowerCase()) {
			case "w":
				if (currRowID != 0) {
					currHero.updatePosition(currRowID - 1, currColID, this);
					cont = false;
				} else
					Printer.printSetMessage("invalidMove");

				break;
			case "s":
				if (currRowID != this.getMap().getMapDimen() - 1) {
					currHero.updatePosition(currRowID + 1, currColID, this);
					cont = false;
				} else
					Printer.printSetMessage("invalidMove");

				break;
			case "a":
				if (currColID != 0) {
					currHero.updatePosition(currRowID, currColID - 1, this);
					cont = false;
				} else
					Printer.printSetMessage("invalidMove");

				break;
			case "d":
				if (currColID != this.getMap().getMapDimen() - 1) {
					currHero.updatePosition(currRowID, currColID + 1, this);
					cont = false;
				} else
					Printer.printSetMessage("invalidMove");

				break;
			case "i":
				showInfo();
				cont = false;
				break;
			case "m":
				break;
			case "e":
				if (currHero.getCurrPlace() instanceof Nexus)
					currHero.getCurrPlace().activatePlace(currHero, this);

				break;
			case "c":
				checkInventory(currHero);
				break;
			case "q":
				quit();
				cont = false;
				break;
			case "t":
				currHero.teleport(this);
				cont = false;
				break;
			case "b":
				currHero.resetPosition();
				cont = false;
				break;
			default:
				Printer.printSetMessage("invalidResponse");
				break;
			}
		}

		// If this is the last hero, then move the monsters
		if (currHero.getID() == getPlayer().getTeam().getHeroes().get(getPlayer().getTeam().getHeroes().size() - 1)
				.getID()) {
			this.moveMonsters();
		}
	}

	/* ====================== */
	/* Game Hero Move Methods */
	/* ====================== */

	/*
	 * This method shows the info of all Heroes and Monsters at play!
	 */
	private void showInfo() {
		System.out.println();
		System.out.format("+------------------------+%n");
		System.out.format("|        HERO INFO       |%n");
		System.out.format("+------------------------+%n");
		Printer.printHelperLine(127);
		System.out.printf(
				"|                        NAME                        | LEVEL |  HP   |  MANA   |  COINS  |  EXP  |   DEX   | AGILITY | STRENGTH | %n");
		Printer.printHelperLine(127);
		for (LegendsHero h : getPlayer().getTeam().getHeroes()) {
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
		for (LegendsMonster m : monsters) {
			System.out.print(m);
			Printer.printHelperLine(105);
		}

		System.out.println();
	}

	/*
	 * This method is a bit more advanced than the checkInventory method in the
	 * Inventory class, as it gets the return of that method, and proceeds to use it
	 * if it is usable (ie: equips armour and weapons).
	 */
	public void checkInventory(LegendsHero h) {
		LegendsItem item = h.getInventory().accessInventory();

		if (item instanceof LegendsWeapon) {
			h.getInventory().replaceEquippedWeapon((LegendsWeapon) item);
		} else if (item instanceof LegendsArmour) {
			LegendsArmour castedItem = (LegendsArmour) item;
			LegendsArmour priorItem = null;

			switch (castedItem.getSlot()) {
			case "Head":
				priorItem = h.getInventory().getEquippedArmour().getHeadPiece();
				h.getInventory().getEquippedArmour().setHeadPiece(castedItem);
				break;
			case "Chest":
				priorItem = h.getInventory().getEquippedArmour().getChestPiece();
				h.getInventory().getEquippedArmour().setChestPiece(castedItem);
				break;
			case "Legs":
				priorItem = h.getInventory().getEquippedArmour().getLegPiece();
				h.getInventory().getEquippedArmour().setLegPiece(castedItem);
				break;
			case "Feet":
				priorItem = h.getInventory().getEquippedArmour().getFeetPiece();
				h.getInventory().getEquippedArmour().setFeetPiece(castedItem);
				break;
			}

			if (priorItem != null)
				h.getInventory().add(priorItem);
		} else
			Printer.printSetMessage("illegalItemChoice");
	}

	/* ========================= */
	/* Game Monster Move Methods */
	/* ========================= */

	public void moveMonsters() {
		for (LegendsMonster m : monsters) {
			m.updatePosition(m.getCurrPlace().getRowID() + 1, m.getCurrPlace().getColID(), this);
		}
	}

	/* ======================= */
	/* Game Respawning Methods */
	/* ======================= */

	private void respawnHeroes() {
		for (LegendsHero h : getPlayer().getTeam().getHeroes()) {
			if (h.getEntityStats().getCurrHP() <= 0) {
				h.respawn();
			}
		}
	}

	private void spawnNewMonsters() {
		int priorSize = monsters.size();
		this.generateMonsters(3);
		int trackCounter = 0;
		for (int i = priorSize; i < 3 + priorSize; i++) {
			Place monsterSpawnPlace = this.getMap().getPlace(trackCounter, 0, 0);
			monsters.get(i).setCurrPlace(monsterSpawnPlace);
			monsters.get(i).setSpawnPlace(monsterSpawnPlace);
			monsterSpawnPlace.addMonsterOnCell(monsters.get(i));

			trackCounter += 2;
		}
	}

	/* ================= */
	/* Game Util Methods */
	/* ================= */

	/*
	 * This method runs after every hero move, to check if any of them have won, or
	 * if the Monsters have won. It also deals with deaths.
	 */
	public void updateStatus() {
		for (LegendsHero h : getPlayer().getTeam().getHeroes()) {
			if (h.getEntityStats().getCurrHP() <= 0) {
				h.getCurrPlace().removeHeroOnCell(h);
			} else if (h.getCurrPlace() instanceof MonsterNexus) {
				this.setStatus(State.WIN);
			}
		}
		
		if (this.getStatus() == State.WIN)
			return;

		for (int i = 0; i < monsters.size(); i++) {
			if (monsters.get(i).getEntityStats().getCurrHP() <= 0) {
				monsters.get(i).getCurrPlace().removeMonsterOnCell(monsters.get(i));
				monsters.remove(i);

				i--;
			} else if (monsters.get(i).getCurrPlace() instanceof PlayerNexus) {
				this.setStatus(State.LOSE);
			}
		}
	}
}
