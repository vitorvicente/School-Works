/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Game.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import Game.LegendsHero;
import Game.Creation.HeroCreation;
import Util.Random;

public class LegendsGamePlayerHeroes implements Iterable<LegendsHero> {

	// Theoretical limits to the amount of Heroes that any one Player can have
	public static final int ABSOLUTE_MAX_HEROES_PER_PLAYER = 3;
	public static final int ABSOLUTE_MIN_HEROES_PER_PLAYER = 1;

	private ArrayList<LegendsHero> heroes;

	/* =================== */
	/* Constructor Methods */
	/* =================== */
	
	public LegendsGamePlayerHeroes() {
		this.createHeroes();
	}
	
	/* ============ */
	/* Game Methods */
	/* ============ */

	private void createHeroes() {
		this.resetHeroes();

		int amountOfHeroes = this.getAmountOfHeroes();
		boolean createOwnHeroes = this.getCreateOwnHeroes();

		LegendsHero hero;
		for (int i = 0; i < amountOfHeroes; i++) {
			if (createOwnHeroes) {
				hero = HeroCreation.generateCustomHero();
			} else {
				System.out.println();
				System.out.println("Creating a random hero pool to pick from! Expect some lag!");
				ArrayList<LegendsHero> pickableHeroes = HeroCreation.generateHeroes(2);
				hero = pickableHeroes.get(Random.randomInt(0, pickableHeroes.size() - 1));

				System.out.println();
				System.out.println("Picked hero: " + hero);
				System.out.println("Hero Class: " + hero.getEntityClass());
				System.out.println("Hero Stats: \n" + hero.getHeroRelatedStats());
			}
			this.addHero(hero);
		}

	}

	private int getAmountOfHeroes() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		int input = 0;
		while (true) {
			System.out.println();
			System.out.println("Please enter the amount of heroes you'd like to play with?");

			input = scanner.nextInt();

			if (input < LegendsGamePlayerHeroes.ABSOLUTE_MIN_HEROES_PER_PLAYER)
				System.out.println("Value provided too small, trying again!");
			else if (input > LegendsGamePlayerHeroes.ABSOLUTE_MAX_HEROES_PER_PLAYER)
				System.out.println("Value provided too large, trying again!");
			else
				return input;

		}
	}

	private boolean getCreateOwnHeroes() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String input = "";

		while (true) {
			System.out.println();
			System.out.println("Would you like to create your own heroes? ('Y'/'N')");

			input = scanner.nextLine();

			switch (input) {
			case "Y":
			case "y":
				return true;
			case "N":
			case "n":
				return false;
			default:
				System.out.println("Invalid entry! Trying again!");
			}
		}
	}
	
	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public void addHero(LegendsHero h) {
		if (this.isHeroInGame(h)) {
			System.out.println("Provided Entity is already in game!");
			return;
		}

		if (this.getHeroes().size() == LegendsGamePlayerHeroes.ABSOLUTE_MAX_HEROES_PER_PLAYER) {
			System.out.println("The current Game has reached max capacity for entites!");
			return;
		}

		this.heroes.add(h);
	}

	public void removeHero(LegendsHero h) {
		if (!this.isHeroInGame(h)) {
			System.out.println("Provided Entity is not in game!");
			return;
		}
		if (this.getHeroes().size() == LegendsGamePlayerHeroes.ABSOLUTE_MIN_HEROES_PER_PLAYER) {
			System.out.println("The current Game has reached minimum capacity for entites!");
			return;
		}

		this.heroes.remove(h);
	}

	public void setHeroes(ArrayList<LegendsHero> heroes) {
		for (LegendsHero h : heroes) {
			this.addHero(h);
		}
	}

	public ArrayList<LegendsHero> getHeroes() {
		return this.heroes;
	}

	public void resetHeroes() {
		this.heroes = new ArrayList<LegendsHero>();
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	public boolean isHeroInGame(LegendsHero h) {
		if (this.getHeroes().indexOf(h) == -1)
			return false;
		else
			return true;
	}

	@Override
	public Iterator<LegendsHero> iterator() {
		return new Itr();
	}

	private class Itr implements Iterator<LegendsHero> {
		private int index;

		public Itr() {
			this.index = 0;
		}

		@Override
		public boolean hasNext() {
			return this.index < getHeroes().size();
		}

		@Override
		public LegendsHero next() {
			LegendsHero h = getHeroes().get(index);
			this.index++;

			return h;
		}

	}

}
