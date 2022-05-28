/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Map.Places.Market;

import java.util.Scanner;

import Entities.LegendsEntity;
import Entities.LegendsHero;
import Game.LegendsOfValor;
import Items.LegendsArmour;
import Items.LegendsItem;
import Items.LegendsSpell;
import Items.LegendsWeapon;
import Map.Places.Place;
import Map.Tracks.Track;
import Util.Printer;
import Util.Token;
import Util.Abstraction.Inventory;

public class Market extends Place {

	private Inventory inventory;
	private Scanner in;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Market(Track track, int row, int col, Token marketToken) {
		super(track, row, col, true, marketToken);
		inventory = new Inventory(10);
		in = new Scanner(System.in);
	}

	/* ================= */
	/* Main Game Methods */
	/* ================= */

	/*
	 * This is the entry method, it prompts the player if they really want to
	 * interact, and behaves accordingly
	 */
	public void activatePlace(LegendsEntity e, LegendsOfValor game) {
		if (e instanceof LegendsHero) {
			Printer.printMSG("Your hero " + e.getName() + " stumbles past the town market!" + "\n"
					+ "Would you like to enter? [Y/N]");
			while (true) {
				try {
					String input = in.nextLine();

					if (input.equalsIgnoreCase("Y")) {
						marketSequence(((LegendsHero) e), game);
						break;
					} else if (input.equalsIgnoreCase("N"))
						break;
					else
						Printer.printSetMessage("invalidResponse");

				} catch (Exception ex) {
					Printer.printSetMessage("invalidResponse");
				}
			}
		}
	}

	/*
	 * Main market sequence method, runs the entire functionality of the cell,
	 * getting the proper input, and behaving accordingly
	 */
	public void marketSequence(LegendsHero h, LegendsOfValor game) {
		if (inventory.isEmpty())
			inventory = new Inventory(10);

		Printer.printMSG("Welcome to the shop!");

		while (true) {
			Printer.printMSG("Please choose one of the following actions:");
			printActions();

			try {
				String input = in.nextLine();

				if (input.equalsIgnoreCase("1")) {
					buySequence(h);
					in.nextLine();
				} else if (input.equalsIgnoreCase("2")) {
					sellSequence(h);
					in.nextLine();
				} else if (input.equalsIgnoreCase("i"))
					showInfo();
				else if (input.equalsIgnoreCase("c"))
					checkInventory(h);
				else if (input.equalsIgnoreCase("q")) {
					game.quit();
					break;
				} else if (input.equalsIgnoreCase("0"))
					break;
				else
					Printer.printSetMessage("Invalid Response");

			} catch (Exception e) {
				Printer.printSetMessage("Invalid Response");
			}
		}

	}

	/*
	 * Prints legal actions in the cell
	 */
	public void printActions() {
		System.out.println();

		String leftAlignFormat = "| %-20s | %-4s |%n";

		System.out.format("+-----------------------------+%n");
		System.out.format("|            ACTIONS          |%n");
		System.out.format("+----------------------+------+%n");
		System.out.format(leftAlignFormat, "SHOP", "1");
		System.out.format(leftAlignFormat, "SELL", "2");
		System.out.format(leftAlignFormat, "LEAVE", "0");
		System.out.format(leftAlignFormat, "SHOW INFO", "i");
		System.out.format(leftAlignFormat, "ACCESS INVENTORY", "c");
		System.out.format(leftAlignFormat, "QUIT GAME", "q");
		System.out.format("+----------------------+------+%n");

		System.out.println();
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

	/* ===================== */
	/* Sell Sequence Methods */
	/* ===================== */

	private void sellSequence(LegendsHero h) {
		while (true) {
			Printer.printMSG("Please pick what you'd like to sell! [Or enter -1 to leave]");

			h.getInventory().printInventory();

			int itemChoice = in.nextInt();

			if (itemChoice == -1)
				break;
			else if (itemChoice >= 0 && itemChoice < h.getInventory().size()) {
				attemptSellItem(h.getInventory().remove(itemChoice), h);
				break;
			} else
				Printer.printSetMessage("invalidResponse");

		}

		Printer.printMSG("Thanks for shopping!");
	}

	private void attemptSellItem(LegendsItem item, LegendsHero h) {
		if (item instanceof LegendsArmour)
			((LegendsArmour) item).regenDurability(1);
		else if (item instanceof LegendsWeapon)
			((LegendsWeapon) item).regenDurability(1);

		Printer.printMSG("You have sold " + item.getName());

		h.getInventory().remove(item);
	}

	/* ===================== */
	/* Buy Sequence Methods */
	/* ===================== */

	public void buySequence(LegendsHero h) {
		while (true) {
			Printer.printMSG("Please pick what you'd like to buy! [Or enter -1 to leave]");

			inventory.printInventory();

			int choice = in.nextInt();

			if (choice == -1)
				break;
			else if (choice >= 0 && choice < this.inventory.size()) {
				attemptPurchaseItem(this.inventory.remove(choice), h);
				break;
			} else
				Printer.printSetMessage("invalidResponse");
		}

		Printer.printMSG("Thanks for shopping!");
	}

	private void attemptPurchaseItem(LegendsItem item, LegendsHero h) {
		if (h.getInventory().getBalance() < item.getCost()) {
			Printer.printMSG("You do not have enough funds!");
			this.inventory.add(item);
			return;
		}

		if (h.getEntityStats().getLevel() < item.getMinLevel()) {
			Printer.printMSG("You are not high enough of a level!");
			this.inventory.add(item);
			return;
		}

		Printer.printMSG("You have purchased " + item.getName());

		if (item instanceof LegendsSpell)
			h.getInventory().learnSpell((LegendsSpell) item);
		else
			h.getInventory().add(item);
	}
}