/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Util.Creation;

import java.util.ArrayList;
import java.util.Scanner;

import Entities.LegendsHero;
import Entities.LegendsMonster;
import Entities.Classes.LegendsHeroClass;
import Entities.Classes.LegendsMonsterClass;
import Entities.Classes.Instances.Paladin;
import Entities.Classes.Instances.Sorcerer;
import Entities.Classes.Instances.Warrior;
import Entities.Util.Hero.LegendsHeroStats;
import Entities.Util.Monster.LegendsMonsterStats;
import Game.LegendsOfValor;
import Util.Printer;
import Util.Random;

public class EntityGenerator {

	/*
	 * Static Method to generate a list of monsters of size
	 * (PossibleMonsterClasses.size()*amountOfEach). This list will then be used to
	 * randomly pick Monsters for encounters.
	 * 
	 * Each Monster will have a class, and randomly generated statistic relating to
	 * their class and level.
	 */
	public static ArrayList<LegendsMonster> generateMonster(int amountOfEach) {
		ArrayList<LegendsMonster> monsters = new ArrayList<LegendsMonster>();

		ArrayList<LegendsMonsterClass> localCopyMonsterClasses = LegendsMonsterClass.POSSIBLE_CLASSES;

		for (int i = 0; i < amountOfEach; i++) {
			for (LegendsMonsterClass classType : localCopyMonsterClasses) {
				monsters.add(new LegendsMonster(LegendsOfValor.ENTITY_IDS, Names.getName(classType),
						EntityGenerator.getMonsterStats(classType, 1, 20), classType));
				LegendsOfValor.ENTITY_IDS++;
			}
		}

		return monsters;
	}

	/*
	 * Static Method to generate a list of monsters of size
	 * (PossibleMonsterClasses.size()*amountOfEach). This list will then be used to
	 * randomly pick Monsters for encounters.
	 * 
	 * Each Monster will have a class, and randomly generated statistic relating to
	 * their class and level.
	 * 
	 * Creation is limited to the specific maxLevel.
	 */
	public static ArrayList<LegendsMonster> generateMonster(int amountOfEach, int maxLevel) {
		ArrayList<LegendsMonster> monsters = new ArrayList<LegendsMonster>();

		ArrayList<LegendsMonsterClass> localCopyMonsterClasses = LegendsMonsterClass.POSSIBLE_CLASSES;

		for (int i = 0; i < amountOfEach; i++) {
			for (LegendsMonsterClass classType : localCopyMonsterClasses) {
				monsters.add(new LegendsMonster(LegendsOfValor.ENTITY_IDS, Names.getName(classType),
						EntityGenerator.getMonsterStats(classType, 1, maxLevel), classType));
				LegendsOfValor.ENTITY_IDS++;
			}
		}

		return monsters;
	}

	/*
	 * Method to generate random Monster Stats based on the Monster Class, all based
	 * around the Monster's level, and following the basic math of the handout given
	 * to us.
	 */
	private static LegendsMonsterStats getMonsterStats(LegendsMonsterClass classType, int minLevel, int maxLevel) {
		int level = Random.randomInt(minLevel, maxLevel);
		int maxHP = 100 * level;
		int strength = (int) Math
				.round(Random.randomInt(50, 150) * (Math.pow(classType.getDamageBoost() + 0.05, level)));
		int defense = (int) Math.round(Random.randomInt(0, 50) * (Math.pow(classType.getDefenseBoost() + 0.05, level)));
		double dodge = Math.max(Math
				.min(Random.randomDouble(0.0, 1.0) * (Math.pow(classType.getDodgeBoost() + 0.05, level)) - 1.0, 0.9),
				0.0001);

		return new LegendsMonsterStats(0, level, maxHP, defense, strength, dodge);
	}

	/*
	 * Static Method to randomly generate a list of heroes of a certain length
	 * (PossibleClasses.size()*amountOfEach) This list is then used to randomly pick
	 * heroes from. The generated heroes get random stats and names, and empty
	 * inventory, armour, current weapon, and known spells.
	 */
	public static ArrayList<LegendsHero> generateHeroes(int amountOfEach) {
		ArrayList<LegendsHero> heroes = new ArrayList<LegendsHero>();

		ArrayList<LegendsHeroClass> localCopyHeroClasses = LegendsHeroClass.POSSIBLE_CLASSES;

		for (int i = 0; i < amountOfEach; i++) {
			for (LegendsHeroClass classType : localCopyHeroClasses) {
				heroes.add(new LegendsHero(LegendsOfValor.ENTITY_IDS, Names.getName(classType),
						EntityGenerator.getHeroStats(classType), classType));
				LegendsOfValor.ENTITY_IDS++;
			}
		}

		return heroes;
	}

	/*
	 * Static Method used to create a custom hero, with a picked class, and custom
	 * name, while stats are still randomized, and inventory/armour/current
	 * weapon/known spells completely empty.
	 */
	public static LegendsHero generateCustomHero() {
		String name = EntityGenerator.promptForName();
		LegendsHeroClass heroClass = EntityGenerator.promptForClass();

		return new LegendsHero(LegendsOfValor.ENTITY_IDS, name, EntityGenerator.getHeroStats(heroClass), heroClass);
	}

	/*
	 * Generate the random stats for a hero of a specific class. The math here
	 * follows the basic logic given to us in the handout, dealing with everything
	 * in regards to level.
	 */
	private static LegendsHeroStats getHeroStats(LegendsHeroClass classType) {
		int level = Random.randomInt(1, 20);

		int maxHP = 100 * level;
		int maxMana = (int) Math.round(100 * (Math.pow(1.1, level)));
		int strength = (int) Math
				.round(Random.randomInt(50, 150) * (Math.pow(classType.getStrengthBoost() + 0.05, level)));
		int dexterity = (int) Math
				.round(Random.randomInt(100, 200) * (Math.pow(classType.getDexterityBoost() + 0.05, level)));
		int agility = (int) Math
				.round(Random.randomInt(10, 100) * (Math.pow(classType.getAgilityBoost() + 0.05, level)));

		return new LegendsHeroStats(level * 10, level, maxHP, maxMana, dexterity, strength, agility);
	}

	/*
	 * Get a Player chosen name.
	 */
	private static String promptForName() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String answer = "";
		String yN = "";
		boolean cont = true;
		boolean contTwo = true;

		while (cont) {
			contTwo = true;
			Printer.printMSG("Please enter the name for your Hero:");

			answer = input.nextLine();

			while (contTwo) {
				Printer.printMSG("Entered name: " + answer + "\n" + "Are you happy with this name? (Y/N)");

				yN = input.nextLine();

				switch (yN) {
				case "Y":
				case "y":
					cont = false;
					contTwo = false;
					break;
				case "N":
				case "n":
					Printer.printMSG("Prompting for name again...");
					contTwo = false;
					break;
				default:
					Printer.printSetMessage("invalidResponse");
					break;
				}
			}
		}

		return answer;
	}

	/*
	 * Get a player chosen Class
	 */
	private static LegendsHeroClass promptForClass() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String answer = "";

		while (true) {
			Printer.printMSG("Please enter the Class for your Hero: ('Paladin'/'Sorcerer'/'Warrior')");

			answer = input.nextLine();

			if (answer.equalsIgnoreCase("Paladin"))
				return new Paladin();
			else if (answer.equalsIgnoreCase("Sorcerer"))
				return new Sorcerer();
			else if (answer.equals("Warrior"))
				return new Warrior();
			else
				Printer.printSetMessage("invalidResponse");
		}
	}
}
