/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Tiles;

import java.util.ArrayList;
import java.util.Scanner;

import Game.LegendsHero;
import Game.LegendsItem;
import Game.LegendsPlayer;
import Game.Creation.ItemCreation;
import Game.Util.LegendsGameEntities;
import Game.Util.LegendsMapTile;
import Items.Armour;
import Items.Spell;
import Items.Weapon;
import Util.Entity;

public class Marketplace extends LegendsMapTile {

	private ArrayList<LegendsItem> itemsForSale;
	private LegendsGameEntities et;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Marketplace(int ID, int xCoord, int yCoord) {
		super(ID, xCoord, yCoord);
		this.generateItemsForSale();
	}

	/* ============ */
	/* Game Methods */
	/* ============ */

	/*
	 * The onEntry method deals with the gameplay mechanics that occur when a group
	 * of Heroes enters the tile.
	 * 
	 * In this case, it goes through all Heroes, asks them what they want to do
	 * (Buy, Sell, Inventory, Leave, Nothing, Information, Quit), and behaves
	 * accordingly.
	 */
	@Override
	public void onEntry(LegendsGameEntities entities) {
		this.setEt(entities);
		this.setPlayerQuit(false);
		// At Any point if user inputs for information it should be displayed

		this.printer("Welcome to the Market!");

		// For Each Entity
		for (Entity e : entities.getEntities()) {
			LegendsPlayer player = (LegendsPlayer) e;

			// For each hero
			for (LegendsHero h : player.getHeroes().getHeroes()) {

				this.printer("Current Hero " + h);

				String nextMove = "";
				while (!nextMove.equals("Leave")) {
					nextMove = this.getNextMove(e, h);
					this.preformMove(player, h, nextMove);

					if (this.getPlayerQuit())
						break;
				}

				if (this.getPlayerQuit())
					break;
			}

			if (this.getPlayerQuit())
				break;
		}

		this.onExit(entities);
	}

	/*
	 * The onExit method deals with exiting the method, clearing temporary variable
	 * if need be, and returning to the Game play method.
	 */
	@Override
	public void onExit(LegendsGameEntities entities) {
		if (this.getPlayerQuit())
			return;

		this.printer("Thank you for visiting the market!");
		this.setEt(null);
		this.setPlayerQuit(false);
	}

	/*
	 * Gets the Individual Hero's next move (or if Q it quits for all)
	 */
	@Override
	public String getNextMove(Entity e, LegendsHero hero) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String input = "";
		while (true) {
			this.printer(e.getName() + " please enter the action for " + hero.getName()
					+ " (Possible action: 'Buy'/'Sell'/'Leave'/'Inventory'/'I'/'Q')");
			input = scanner.nextLine();

			if (input.equalsIgnoreCase("Buy"))
				return "Buy";
			else if (input.equals("Sell"))
				return "Sell";
			else if (input.equalsIgnoreCase("Nothing"))
				return "Inventory";
			else if (input.equalsIgnoreCase("Leave"))
				return "Leave";
			else if (input.equalsIgnoreCase("I"))
				return "I";
			else if (input.equalsIgnoreCase("Q"))
				return "Q";
			else
				this.printer("Invalid input, trying again!");
		}
	}

	/*
	 * Performs the Hero's next move as given by the parent method
	 */
	@Override
	public void preformMove(Entity e, LegendsHero hero, String move) {
		switch (move) {
		case "Leave":
			return;
		case "Buy":
			this.preformBuy(e, hero);
			return;
		case "Sell":
			this.preformSell(e, hero);
			return;
		case "Inventory":
			this.accessInventory(e, hero);
			return;
		case "I":
			this.displayInfo();
			return;
		case "Q":
			this.quit();
			return;
		}

	}

	/*
	 * Deals with a Player accessing their inventory, selecting an item (which is
	 * done in another method), and uses the item if possible
	 */
	public void accessInventory(Entity e, LegendsHero h) {
		LegendsItem itemToUse = this.pickItem(e, h);

		if (itemToUse instanceof Armour) {
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
		} else {
			this.printer("This item cannot be used here!");
		}

	}

	// Lets Heroes pick an item in their inventory to use.
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

	// Sets up the Player's quitting of the game
	@Override
	public void quit() {
		this.setPlayerQuit(true);
	}

	// Displays information regarding all Heroes at play
	@Override
	public void displayInfo() {
		this.printer("Printing Information");

		System.out.println("Hero Information");
		for (Entity e : this.getEt()) {
			for (LegendsHero h : ((LegendsPlayer) e).getHeroes().getHeroes()) {
				this.printer(h + "'s stats: \n" + h.getHeroRelatedStats());

			}
		}
	}

	// Performs the overall Buying of items for Heroes, checking for money and level
	// requirements, and then selling the Hero the actual item
	private void preformBuy(Entity e, LegendsHero h) {
		LegendsItem item = this.pickItemBuy(e, h);

		if (item.getCost() > h.getHeroRelatedStats().getCoins()) {
			this.printer(h + " does not have enough money to cover the cost!");
			return;
		}
		if (item.getMinLevel() > h.getHeroRelatedStats().getLevel()) {
			this.printer(h + "'s level is not enough to buy this item!");
			return;
		}
		this.sellItem(e, h, item);
	}

	// Performs the overall Selling of items for Heroes, and buying the item from
	// the Hero in the end
	private void preformSell(Entity e, LegendsHero h) {
		LegendsItem item = this.pickItemSell(e, h);

		this.buyItem(e, h, item);
	}

	// Lets Heroes pick which of the Marketplace's items to buy
	private LegendsItem pickItemBuy(Entity e, LegendsHero h) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String input = "";

		this.printer(
				e.getName() + " please pick which item " + h + " will buy (or enter 'l' to leave the transaction)");

		int i = 0;
		for (LegendsItem item : this.getItemsForSale()) {
			System.out.println("[" + i + "] " + item + " $" + item.getCost() + " Minimum Level:" + item.getMinLevel());
			i++;
		}

		input = scanner.nextLine();

		if (input.equalsIgnoreCase("l"))
			return null;
		else {
			int itemIndex = Integer.valueOf(input);
			return this.getItemsForSale().get(itemIndex);
		}
	}

	// Lets Heroes pick which of their inventory items to sell
	private LegendsItem pickItemSell(Entity e, LegendsHero h) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String input = "";

		this.printer(
				e.getName() + " please pick which item " + h + " will sell (or enter 'l' to leave the transaction)");

		int i = 0;
		for (LegendsItem item : h.getHeroInventory().getInventory()) {
			System.out.println("[" + i + "] " + item + " $" + ((int) Math.round(item.getCost() * 0.5)));
			i++;
		}

		input = scanner.nextLine();

		if (input.equals("l"))
			return null;
		else {
			int itemIndex = Integer.valueOf(input);
			return h.getHeroInventory().getInventory().get(itemIndex);
		}
	}

	// Sells an item to the Hero
	private void sellItem(Entity e, LegendsHero h, LegendsItem i) {
		this.itemsForSale.remove(i);

		if (i instanceof Spell)
			h.getKnownSpells().add((Spell) i);
		else
			h.getHeroInventory().addItem(i);

		h.getHeroRelatedStats().spendMoney(i.getCost());
		h.getGameRelatedStats().addItemsBought();
		((LegendsPlayer) e).getGameRelatedStats().addItemsBought();
	}

	// Buys the item from a Hero
	private void buyItem(Entity e, LegendsHero h, LegendsItem i) {
		if (i instanceof Armour)
			((Armour) i).regenDurability(1);
		else if (i instanceof Weapon)
			((Weapon) i).regenDurability(1);

		this.itemsForSale.add(i);
		h.getHeroRelatedStats().addMoney((int) Math.round(i.getCost() * 0.5));
		h.getGameRelatedStats().addItemsSold();
		((LegendsPlayer) e).getGameRelatedStats().addItemsSold();
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	/*
	 * The reason for the entities is for the info Display
	 */
	private LegendsGameEntities getEt() {
		return et;
	}

	private void setEt(LegendsGameEntities et) {
		this.et = et;
	}

	private void generateItemsForSale() {
		this.itemsForSale = ItemCreation.generateItems(5);
	}

	public ArrayList<LegendsItem> getItemsForSale() {
		if (this.itemsForSale.size() == 0)
			this.generateItemsForSale();

		return itemsForSale;
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	public String toString() {
		return "M";
	}
}
