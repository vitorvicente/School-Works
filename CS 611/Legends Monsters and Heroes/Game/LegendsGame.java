/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Game;

import java.util.Scanner;

import Game.Util.LegendsGameEntities;
import Game.Util.LegendsMapTile;
import Tiles.Common;
import Tiles.Marketplace;
import Util.Entity;
import Util.Game;
import Util.GameEntities;

public class LegendsGame extends Game {
	
	public static final int DEFAULT_MAP_SIZE = 8;
	public static int ENTITY_IDS = 0;
	public static int ITEM_IDS = 0;
	public static int TILE_IDS = 0;

	private LegendsMap world;
	private LegendsGameEntities players;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsGame() {
		super("Legends: Heroes and Monsters");

		this.setMessages();

		this.printer("welcome");

		this.printer("WARNING \n Map is now being generated, this might take a while...");
		this.setWorld(new LegendsMap(LegendsGame.DEFAULT_MAP_SIZE));

		this.printer("instructions");

		this.setupGameEntities();

		this.play();
	}

	public LegendsGame(int mapSize) {
		super("Legends: Heroes and Monsters");

		this.setMessages();

		this.printer("welcome");

		this.printer("WARNING \n Map is now being generated, this might take a while...");
		this.setWorld(new LegendsMap(mapSize));

		this.printer("instructions");

		this.setupGameEntities();

		this.play();
	}

	public LegendsGame(LegendsMap map) {
		super("Legends: Heroes and Monsters");

		this.setWorld(map);

		this.setupGameEntities();

		this.play();
	}

	public LegendsGame(GameEntities entities) {
		super("Legends: Heroes and Monsters");

		this.setMessages();

		this.printer("welcome");

		this.printer("WARNING \n Map is now being generated, this might take a while...");
		this.setWorld(new LegendsMap(LegendsGame.DEFAULT_MAP_SIZE));

		this.printer("instructions");

		this.setGameEntities(entities);

		this.play();
	}

	public LegendsGame(int mapSize, GameEntities entities) {
		super("Legends: Heroes and Monsters");

		this.setMessages();

		this.printer("welcome");

		this.printer("WARNING \n Map is now being generated, this might take a while...");
		this.setWorld(new LegendsMap(mapSize));

		this.printer("instructions");

		this.setGameEntities(entities);

		this.play();
	}

	public LegendsGame(LegendsMap map, GameEntities entities) {
		super("Legends: Heroes and Monsters");

		this.setMessages();

		this.printer("welcome");

		this.setWorld(map);

		this.printer("instructions");

		this.setGameEntities(entities);

		this.play();
	}

	/* ============ */
	/* Game Methods */
	/* ============ */

	/*
	 * Actual play method, this method deals with moving between tiles, and dealing
	 * with secondary events such as player deaths and quits, but leaves most of the
	 * work to the actual tiles, with the help of the map itself
	 */
	@Override
	public void play() {
		boolean playersQuit = false;
		boolean playersAlive = true;

		while (!playersQuit && playersAlive) {
			this.printer(this.getWorld() + "");

			String move = "";
			boolean canMove = false;

			while (!canMove) {
				move = this.getNextMove(this.getGameEntities().getEntities().get(0));
				if (move.equals("Q"))
					break;
				canMove = this.getWorld().move(move);

				((LegendsPlayer) this.getGameEntities().getEntities().get(0)).getGameRelatedStats().addTilesMoved();
				for (LegendsHero hero : ((LegendsPlayer) this.getGameEntities().getEntities().get(0)).getHeroes()
						.getHeroes())
					hero.getGameRelatedStats().addTilesMoved();
			}
			if (move.equals("Q")) {
				playersQuit = true;
				break;
			}

			this.printer(this.getWorld() + "");

			LegendsMapTile currentCell = this.getWorld().getCurrentCell();
			if (currentCell instanceof Marketplace) {
				Marketplace castedCell = (Marketplace) currentCell;

				castedCell.onEntry(this.getGameEntities());

				playersQuit = castedCell.getPlayerQuit();
			} else if (currentCell instanceof Common) {
				Common castedCell = (Common) currentCell;

				castedCell.onEntry(this.getGameEntities());

				playersQuit = castedCell.getPlayerQuit();

				if (castedCell.getEncounterOccured())
					playersAlive = castedCell.getPlayersWon();
			}
		}

		if (playersQuit)
			this.playersQuit();
		else if (!playersAlive)
			this.playersDied();
	}

	// Sets up the Entities at Play (currently only one Player)
	@Override
	public void setupGameEntities() {
		this.resetGameEntities();

		LegendsPlayer player = new LegendsPlayer(1);

		this.getGameEntities().addEntity(player);
	}

	// Gets a Player's next move
	private String getNextMove(Entity e) {
		String move = "";
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		while (true) {
			this.printer(e + " please select the next move for your team of heroes! ('W'/'A'/'S'/'D'/'I'/'Q')");
			move = scanner.nextLine();

			switch (move) {
			case "W":
			case "w":
				return "W";
			case "A":
			case "a":
				return "A";
			case "S":
			case "s":
				return "S";
			case "D":
			case "d":
				return "D";
			case "Q":
			case "q":
				return "Q";
			case "I":
			case "i":
				this.printInfo(e);
			}
		}
	}

	// Print Hero information
	private void printInfo(Entity e) {
		LegendsPlayer castedEntity = (LegendsPlayer) e;

		this.printer("Hero Stats:");
		for (LegendsHero hero : castedEntity.getHeroes().getHeroes()) {
			this.printer(hero + "'s class:" + hero.getEntityClass() + "\n" + hero + "'s stats: \n"
					+ hero.getHeroRelatedStats());
		}
	}

	// Deals with Players quitting the game
	private void playersQuit() {
		this.printer("goodbye");
	}

	// Deals with a situation where all Players' Heroes are dead.
	private void playersDied() {
		this.printer("All players have died! Game Over");
		this.printer("goodbye");
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	@Override
	public LegendsGameEntities getGameEntities() {
		return this.players;
	}

	@Override
	public void setGameEntities(GameEntities g) {
		if (g instanceof LegendsGameEntities) {
			this.players = (LegendsGameEntities) g;
		} else {
			System.out.println(
					"Only instanced of LegendsGameEntities are allowed to be used for the Game Entities Object for the Legends: Heros and Monsters game!");
		}
	}

	public void resetGameEntities() {
		this.players = new LegendsGameEntities();
	}

	public LegendsMap getWorld() {
		return world;
	}

	private void setWorld(LegendsMap world) {
		this.world = world;
	}

	private void setMessages() {
		String welcome = "===================== \n" + "Welcome to Legends: Heroes and Monsters! \n"
				+ "A game by Vitor Vicente \n" + "=====================";

		this.setWelcomeMSG(welcome);

		String instructions = "===================== \n"
				+ "This is quite a complex game, so we'll split it into different sections to explain: Setup, Market, Heroes, Monsters, Items, Encounters, Controls \n \n"
				+ "Setup \n"
				+ "During the setup of the game, a randomly generated board of the specified size will be created. "
				+ "This board will have 3 different types of tiles, Common Areas, which are represented by emtpy spaces; Marketplaces, which are represented with an 'M'; and Inaccessible Areas, represented with an I. \n \n"
				+ "Common Areas are tiles where encounters with monsters occur. Marketplaces are used to buy and sell items. Inaccessible areas are like walls. \n \n"
				+ "After the creation of the map, you will be prompted for a Player name, this is different from the name of your heroes!"
				+ "After you created your player, you will be asked how many Heroes you'd like to play, currently you can play with between 1 and 3 heroes, each with individual stats etc... \n"
				+ "You may create your own Heroes, or get randomly generated ones. \n" + "Setup is now done!"
				+ "Heroes \n"
				+ "Heroes are the characters played by the players, they have a class, of which there are 3, Sorcerer, Warrior, Paladin. Each class boosts two of the heroes 3 abilities, Dexterity, Agility, and Strength. \n"
				+ "A Hero's Dexterity increased the amount of damage dealt with spells. A Hero's Agility increases the chance of dodging an attack. A Hero's Strength increases the amount of damage outputed while using weapons. \n"
				+ "A Hero will also have a certain amount of Health (HP), and Mana, which is used to cast spells. Further, each Hero has an amount of Exp in their current Level; a current Level, which delimits item use; and a certain amount of money. \n \n"
				+ "Monsters \n"
				+ "Monsters are the other Entity in the game, just like the Heroes they have a level, a class (Dragon/Spirit/Exoskeleton), and 3 abilities Strength, Defense, Dodge. Each monster also has a certain amount of Health. \n \n"
				+ "Items \n" + "There are 4 different types of items: Armour, Weapons, Potions, Spells. \n"
				+ "Weapons are used to attack Monsters, and they each have a price, a required level, a durability (which goes down with use), and an amount of damage they inflict. "
				+ "A hero can only have 1 equipped weapon at a time, but may keep others in their inventory, which they can access and switch with the main weapon. \n"
				+ "Armour is used for defense, each piece has a slot it goes on (Head, Chest, Legs, Feet), a level, a price, a durability, and a defense multiplier (ie: how much they protect). \n"
				+ "Potions and Spells can be used either to buff a Hero's abilities, or debuff an opponents. Spells also damage oponents, potions do not. "
				+ "Potions and Spells both have a level, price, buff/debuff multiplier; and Spells have a certain amount of damage they inflict. Potions are one time use items.\n \n"
				+ "Encounters \n"
				+ "Encounters occur in Common Areas, and if they occur, each Hero will fight a SINGLE monster at a time, if one dies, then it will wait for another opponent to become available, but never will two Heroes attack one Monster or vice versa. \n"
				+ "Encounters have rounds, where each Hero will get to pick an action, and each monster will attack the hero they are agaisnt. At the end of each round Heroes that are alive get an HP and Mana boost of 10%, at the end of an encounter they are reset to 100%. \n"
				+ "\n" + "Controls \n"
				+ "When moving, a player can use the W,A,S,D keys to move Up, Left, Down, Right respectively. A player may also always use I to display current information, as well as Q to quit game. \n"
				+ "During an encounter, a Hero can choose to Attack, Spell, Inventory, Revive. Attacking attacks with the current weapon. Spell brings up a list of currently known spells, and lets the Hero use one to attack."
				+ "Inventory allows a user to access their inventory to switch armour/weapon or use a potion. Revive allows a Hero to revive another Hero! \n"
				+ "When in the Marketplace, a Hero may use Sell, Buy, Nothing, Leave. If Sell or Buy is selected, a list will be brought up of items that the Hero can sell or buy. If Nothing or Leave are selected, a Hero will not interact with the market. \n"
				+ "Both during battle and in the market a Hero can use Q and I. \n"
				+ "Sometimes you will have to enter an integer to pick something, or l to leave. \n"
				+ "===================== \n";

		this.setInstructions(instructions);

		String goodbye = "===================== \n" + "Thank you for playing Legends: Heroes and Monsters \n"
				+ "A game by Vitor Vicente, for CS611 Spring '21 \n" + "===================== \n";

		this.setGoodbyeMSG(goodbye);
	}
}
