/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Game.Creation;

import java.util.ArrayList;
import java.util.Scanner;

import Classes.LegendsHeroClass;
import Classes.Heroes.Paladin;
import Classes.Heroes.Sorcerer;
import Classes.Heroes.Warrior;
import Game.LegendsGame;
import Game.LegendsHero;
import Game.Util.Creation.Names;
import Game.Util.Hero.LegendsHeroArmour;
import Game.Util.Hero.LegendsHeroGameStats;
import Game.Util.Hero.LegendsHeroHeroStats;
import Game.Util.Hero.LegendsHeroInventory;
import Items.Spell;
import Util.Random;

public class HeroCreation {

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
				heroes.add(new LegendsHero(LegendsGame.ENTITY_IDS, Names.getName(classType), new LegendsHeroGameStats(),
						classType, HeroCreation.getHeroStats(classType), new LegendsHeroInventory(),
						new LegendsHeroArmour(), null, new ArrayList<Spell>()));
				LegendsGame.ENTITY_IDS++;
			}
		}

		return heroes;
	}

	/*
	 * Generate the random stats for a hero of a specific class. The math here
	 * follows the basic logic given to us in the handout, dealing with everything
	 * in regards to level.
	 */
	private static LegendsHeroHeroStats getHeroStats(LegendsHeroClass classType) {
		int level = Random.randomInt(1, 20);

		int maxHP = 100 * level;
		int maxMana = (int) Math.round(100 * (Math.pow(1.1, level)));
		int coins = 100 + (Random.randomInt(100, 500) * level);
		int strength = (int) Math
				.round(Random.randomInt(50, 150) * (Math.pow(classType.getStrengthBoost() + 0.05, level)));
		int dexterity = (int) Math
				.round(Random.randomInt(100, 200) * (Math.pow(classType.getDexterityBoost() + 0.05, level)));
		int agility = (int) Math
				.round(Random.randomInt(10, 100) * (Math.pow(classType.getAgilityBoost() + 0.05, level)));

		return new LegendsHeroHeroStats(maxHP, maxMana, level, coins, dexterity, strength, agility);
	}

	/*
	 * Static Method used to create a custom hero, with a picked class, and custom
	 * name, while stats are still randomized, and inventory/armour/current
	 * weapon/known spells completely empty.
	 */
	public static LegendsHero generateCustomHero() {
		String name = HeroCreation.promptForName();
		LegendsHeroClass heroClass = HeroCreation.promptForClass();

		return new LegendsHero(LegendsGame.ENTITY_IDS, name, new LegendsHeroGameStats(), heroClass,
				HeroCreation.getHeroStats(heroClass), new LegendsHeroInventory(), new LegendsHeroArmour(), null,
				new ArrayList<Spell>());
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
			System.out.println();
			System.out.println("Please enter the name for your Hero:");

			answer = input.nextLine();

			while (contTwo) {
				System.out.println();
				System.out.println("Entered name: " + answer);
				System.out.println("Are you happy with this name? (Y/N)");

				yN = input.nextLine();

				switch (yN) {
				case "Y":
				case "y":
					cont = false;
					contTwo = false;
					break;
				case "N":
				case "n":
					System.out.println("Prompting for name again...");
					contTwo = false;
					break;
				default:
					System.out.println("Invalid Input!! Only inputs allowed are: 'Y'/'y'/'N'/'n'");
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
			System.out.println();
			System.out.println("Please enter the Class for your Hero: ('Paladin'/'Sorcerer'/'Warrior')");

			answer = input.nextLine();

			if (answer.equalsIgnoreCase("Paladin"))
				return new Paladin();
			else if (answer.equalsIgnoreCase("Sorcerer"))
				return new Sorcerer();
			else if (answer.equals("Warrior"))
				return new Warrior();
			else
				System.out.println("Invalid input, trying again!");

		}
	}

}
