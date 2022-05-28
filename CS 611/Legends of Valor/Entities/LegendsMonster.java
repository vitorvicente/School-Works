/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Entities;

import Entities.Classes.LegendsEntityClass;
import Entities.Classes.LegendsMonsterClass;
import Entities.Util.LegendsEntityStats;
import Entities.Util.Monster.LegendsMonsterStats;
import Game.LegendsOfValor;
import Map.Places.Place;
import Map.Places.Plains.Plains;
import Util.Printer;

public class LegendsMonster extends LegendsEntity {

	private LegendsMonsterStats stats;
	private LegendsMonsterClass monsterClass;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsMonster(int ID, String name, LegendsMonsterStats stats, LegendsMonsterClass monsterClass) {
		super(ID, name);
		this.stats = stats;
		this.monsterClass = monsterClass;
	}

	/* ============ */
	/* Game Methods */
	/* ============ */

	@Override
	public void levelUp() {
		this.stats.levelUp(this.monsterClass);

		Printer.printMSG(this.getName() + " has leveled up to level " + this.stats.getLevel() + "!");
	}

	/*
	 * This method is used to update the Position of the Monster. It runs the same
	 * checks as the Hero updatePosition, except it looks for Heroes instead of
	 * Monsters. It checks to make sure the cell is accessible, and that it is clear
	 * of enemies. If it isn't it checks if there can be combat, and TPs the monster
	 * to the set cell, initializing combat.
	 */
	@Override
	public void updatePosition(int x, int y, LegendsOfValor game) {
		int z = 0;
		if (y == 0)
			z = 1;

		Place toMoveTo = this.getCurrPlace().getCurrTrack().getPlace(x, y);
		Place sideCell = this.getCurrPlace().getCurrTrack().getPlace(x, z);

		if (toMoveTo != null && toMoveTo.isAccessible()) {
			if ((toMoveTo.getHeroesOnCell().size() > 0) && (toMoveTo instanceof Plains)) {
				this.getCurrPlace().removeMonsterOnCell(this);
				this.setCurrPlace(toMoveTo);
				this.getCurrPlace().addMonsterOnCell(this);
				this.getCurrPlace().activatePlace(this, game);
			} else if ((sideCell.getHeroesOnCell().size() > 0) && (sideCell instanceof Plains)) {
				this.getCurrPlace().removeMonsterOnCell(this);
				this.setCurrPlace(sideCell);
				this.getCurrPlace().addMonsterOnCell(this);
				this.getCurrPlace().activatePlace(this, game);
			} else if ((toMoveTo.getHeroesOnCell().size() == 0) && (sideCell.getHeroesOnCell().size() == 0)) {
				this.getCurrPlace().removeMonsterOnCell(this);
				this.setCurrPlace(toMoveTo);
				this.getCurrPlace().addMonsterOnCell(this);
				this.getCurrPlace().activatePlace(this, game);
			} else {
				Printer.printSetMessage("invalidMove");
			}
		} else {
			Printer.printSetMessage("invalidMove");
		}
	}

	/*
	 * Resets the position of the Monster to its Nexus, also checks if it is still
	 * alive, and if so it removes it from its current Cell.
	 */
	@Override
	public void resetPosition() {
		if (this.getEntityStats().getCurrHP() > 0)
			this.getCurrPlace().removeMonsterOnCell(this);

		this.setCurrPlace(this.getSpawnPlace());
		this.getCurrPlace().addMonsterOnCell(this);
	}

	@Override
	public void respawn() {
		this.resetPosition();

		this.stats.regenHealth(1);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	@Override
	public LegendsEntityClass getEntityClass() {
		return monsterClass;
	}

	@Override
	public void setEntityClass(LegendsEntityClass eClass) {
		if (!(eClass instanceof LegendsMonsterClass)) {
			Printer.printSetMessage("invalidClass");
			return;
		}

		this.monsterClass = (LegendsMonsterClass) eClass;
	}

	@Override
	public LegendsEntityStats getEntityStats() {
		return this.stats;
	}

	@Override
	public void setLegendsEntityStats(LegendsEntityStats eStats) {
		if (!(eStats instanceof LegendsMonsterStats)) {
			Printer.printSetMessage("invalidStats");
			return;
		}
		this.stats = (LegendsMonsterStats) eStats;
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	public String toString() {
		return String.format("| %-50s | %-5d | %-5d | %-5d | %.3f | %-7d | %-7d  |%n", getName(), stats.getLevel(),
				stats.getCurrHP(), stats.getEXP(), stats.getDodge(), stats.getDefense(), stats.getStrength());
	}

}
