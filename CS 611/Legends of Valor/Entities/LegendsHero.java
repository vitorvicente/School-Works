/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Guinta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Entities;

import java.util.Scanner;

import Entities.Classes.LegendsEntityClass;
import Entities.Classes.LegendsHeroClass;
import Entities.Util.LegendsEntityStats;
import Entities.Util.Hero.LegendsHeroInventory;
import Entities.Util.Hero.LegendsHeroStats;
import Game.LegendsOfValor;
import Map.Places.Place;
import Map.Places.Plains.Plains;
import Map.Tracks.Lane;
import Util.Printer;

public class LegendsHero extends LegendsEntity {

	private LegendsHeroInventory inventory;
	private LegendsHeroStats stats;
	private LegendsHeroClass heroClass;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsHero(int ID, String name, LegendsHeroStats stats, LegendsHeroClass heroClass) {
		super(ID, name);
		inventory = new LegendsHeroInventory();
		this.stats = stats;
		this.heroClass = heroClass;
	}

	/* ============ */
	/* Game Methods */
	/* ============ */

	@Override
	public void levelUp() {
		this.stats.levelUp(this.heroClass);

		this.inventory.gainMoney(100);

		Printer.printMSG(this.getName() + " has leveled up to level " + this.stats.getLevel() + "!");
	}

	/*
	 * This method is used to update the position of the Hero, it checks whether it
	 * is moving into a row that is accessible, and isn't occupied by any Monster.
	 * The complication of the method arises from checking the cell to the side of
	 * the cell the Hero is moving to, to make sure it's a legal move. If by chance
	 * the cell to the side of the cell where the Hero is moving to has a Monster,
	 * he is teleported there to combat the Monster.
	 */
	@Override
	public void updatePosition(int x, int y, LegendsOfValor game) {
		int z = 0;
		if (y == 0)
			z = 1;

		Place toMoveTo = this.getCurrPlace().getCurrTrack().getPlace(x, y);
		Place sideCell = this.getCurrPlace().getCurrTrack().getPlace(x, z);

		if (toMoveTo != null && toMoveTo.isAccessible()) {
			if ((toMoveTo.getMonstersOnCell().size() > 0) && (toMoveTo instanceof Plains)) {
				this.getCurrPlace().removeHeroOnCell(this);
				this.setCurrPlace(toMoveTo);
				this.getCurrPlace().addHeroOnCell(this);
				this.getCurrPlace().activatePlace(this, game);
			} else if ((sideCell.getMonstersOnCell().size() > 0) && (sideCell instanceof Plains)) {
				this.getCurrPlace().removeHeroOnCell(this);
				this.setCurrPlace(sideCell);
				this.getCurrPlace().addHeroOnCell(this);
				this.getCurrPlace().activatePlace(this, game);
			} else if ((toMoveTo.getMonstersOnCell().size() == 0) && (sideCell.getMonstersOnCell().size() == 0)) {
				this.getCurrPlace().removeHeroOnCell(this);
				this.setCurrPlace(toMoveTo);
				this.getCurrPlace().addHeroOnCell(this);
				this.getCurrPlace().activatePlace(this, game);
			} else {
				Printer.printSetMessage("invalidMove");
			}
		} else {
			Printer.printSetMessage("invalidMove");
		}
	}

	/*
	 * Used to reset the Hero position back to their Spawning Spot. In case the hero
	 * is still alive, it removes them from the current cell (back command), if they
	 * are dead it doesn't since they don't have a current cell (respawn).
	 */
	@Override
	public void resetPosition() {
		if (this.getEntityStats().getCurrHP() > 0)
			this.getCurrPlace().removeHeroOnCell(this);

		this.setCurrPlace(this.getSpawnPlace());
		this.getCurrPlace().addHeroOnCell(this);
	}

	@Override
	public void respawn() {
		this.resetPosition();

		this.stats.regenHealth(1);
		this.stats.regenMana(1);
	}

	/*
	 * Overall Teleport method. It starts by getting the Teleport Coordinates, and
	 * then proceeds to make several checks. Firstly, Heroes are only allowed to TP
	 * to "Plains" cells inside "Lane" tracks. This means a Hero can't teleport to
	 * an inaccessible cell, nor to a Nexus.
	 * 
	 * Then it runs a check to see what is the first row that has a monster, and
	 * makes sure the Hero isn't teleporting behind them. If they teleport to the
	 * cell, or to the cell besides it, they'll be put into the cell with the
	 * Monster, and engage.
	 */
	public void teleport(LegendsOfValor game) {

		int[] coords = this.tpCoord(game);
		if (coords == null)
			return;

		Place toTPTo = game.getMap().getPlace(coords[0], coords[1], coords[2]);

		if (toTPTo.getCurrTrack() instanceof Lane && toTPTo instanceof Plains) {
			Place[][] rawPlaces = ((Lane) toTPTo.getCurrTrack()).getRawPlaces();
			int minRow = -1;
			for (int i = rawPlaces.length - 1; i > -1; i--) {
				if (rawPlaces[i][0].getMonstersOnCell().size() > 0 || rawPlaces[i][1].getMonstersOnCell().size() > 0) {
					minRow = i;
					break;
				}
			}
			System.out.println(minRow);
			if (minRow > coords[1]) {
				Printer.printSetMessage("invalidMove");
			} else if (minRow == coords[1]) {
				if ((rawPlaces[minRow][0].getMonstersOnCell().size() > 0)) {
					this.getCurrPlace().removeHeroOnCell(this);
					this.setCurrPlace(rawPlaces[minRow][0]);
					this.getCurrPlace().addHeroOnCell(this);
					this.getCurrPlace().activatePlace(this, game);
				} else if ((rawPlaces[minRow][1].getMonstersOnCell().size() > 0)) {
					this.getCurrPlace().removeHeroOnCell(this);
					this.setCurrPlace(rawPlaces[minRow][1]);
					this.getCurrPlace().addHeroOnCell(this);
					this.getCurrPlace().activatePlace(this, game);
				} else {
					Printer.printSetMessage("invalidMove");
				}
			} else {
				this.getCurrPlace().removeHeroOnCell(this);
				this.setCurrPlace(toTPTo);
				this.getCurrPlace().addHeroOnCell(this);
				this.getCurrPlace().activatePlace(this, game);
			}
		} else {
			Printer.printSetMessage("invalidMove");
		}

	}

	/*
	 * This method prompts for user input for lane/row/col to TP to. It then passes
	 * this information as an array of integers back to the main Teleport Method.
	 * 
	 * It also allows the user to exit the teleport at any moment.
	 */
	private int[] tpCoord(LegendsOfValor game) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		boolean leave = false;

		String laneResponse = "";
		int laneRow = 0;
		int laneCol = 0;

		boolean contOne = true;
		while (contOne) {
			Printer.printMSG("Please enter the Lane you'd like to teleport to? ['Top'/'Mid'/'Bot'/'Leave']");
			laneResponse = in.nextLine();

			switch (laneResponse) {
			case "Top":
			case "Mid":
			case "Bot":
				contOne = false;
				break;
			case "Leave":
				contOne = false;
				leave = true;
				break;
			default:
				Printer.printSetMessage("invalidResponse");
			}
		}
		int numLane = 0;
		if (laneResponse.equals("Mid"))
			numLane = 2;
		else if (laneResponse.equals("Bot"))
			numLane = 4;

		if (leave)
			return null;

		boolean contTwo = true;
		while (contTwo) {
			Printer.printMSG("Please enter the Row # you'd like to teleport to? [Or -1 to leave]");

			laneRow = in.nextInt();

			if (laneRow == -1) {
				leave = true;
				contTwo = false;
			} else if (laneRow >= 0 && laneRow < game.getMap().getMapDimen()) {
				contTwo = false;
			} else
				Printer.printSetMessage("invalidResponse");
		}

		if (leave)
			return null;

		boolean contThree = true;
		while (contThree) {
			Printer.printMSG("Please enter the Col # you'd like to teleport to? [Or -1 to leave]");

			laneCol = in.nextInt();

			if (laneCol == -1) {
				leave = true;
				contThree = false;
			} else if ((game.getMap().getMap()[numLane] instanceof Lane) && (laneCol >= 0 && laneCol < 2)) {
				contThree = false;
			} else if ((game.getMap().getMap()[numLane] instanceof Lane) && (laneCol >= 0 && laneCol < 1)) {
				contThree = false;
			} else
				Printer.printSetMessage("invalidResponse");
		}

		if (leave)
			return null;

		return new int[] { numLane, laneRow, laneCol };
	}
	
	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	@Override
	public LegendsEntityClass getEntityClass() {
		return this.heroClass;
	}

	@Override
	public void setEntityClass(LegendsEntityClass eClass) {
		if (!(eClass instanceof LegendsHeroClass)) {
			Printer.printSetMessage("invalidClass");
			return;
		}

		this.heroClass = (LegendsHeroClass) eClass;
	}

	@Override
	public LegendsEntityStats getEntityStats() {
		return this.stats;
	}

	@Override
	public void setLegendsEntityStats(LegendsEntityStats eStats) {
		if (!(eStats instanceof LegendsHeroStats)) {
			Printer.printSetMessage("invalidStats");
			return;
		}

		this.stats = (LegendsHeroStats) eStats;
	}

	public LegendsHeroInventory getInventory() {
		return this.inventory;
	}

	public void setInventory(LegendsHeroInventory inventory) {
		this.inventory = inventory;
	}
	
	/* =========== */
	/* Aux Methods */
	/* =========== */

	public String toString() {
		return String.format("| %-50s | %-5d | %-5d | %-7d | %-7d | %-5d | %-7d | %-7d | %-7d  | %n", getName(),
				stats.getLevel(), stats.getCurrHP(), stats.getCurrMana(), inventory.getBalance(), stats.getEXP(),
				stats.getDexterity(), stats.getAgility(), stats.getStrength());
	}

}
