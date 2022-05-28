/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Game.Util.Hero;

import java.util.ArrayList;
import java.util.Arrays;

import Items.Armour;

public class LegendsHeroArmour {

	private Armour headPiece;
	private Armour chestPiece;
	private Armour legPiece;
	private Armour feetPiece;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsHeroArmour() {
		this.resetSlots();
	}

	/* ============ */
	/* Game Methods */
	/* ============ */

	/*
	 * This take damage method is special, since the equipped armour takes damage as
	 * a while, but the damage is split between each in regards to a specific
	 * percentage, it also deals with pieces breaking
	 */
	public void takeDamage(int damage) {
		int damageToHead = (int) Math.round(damage * 0.2);
		int damageToChest = (int) Math.round(damage * 0.4);
		int damageToLegs = (int) Math.round(damage * 0.3);
		int damageToFeet = (int) Math.round(damage * 0.1);

		if (this.getHeadPiece() != null) {
			boolean brokeHeadPiece = this.getHeadPiece().loseDurability(damageToHead);
			if (brokeHeadPiece) {
				System.out.println("You just broke your " + this.getHeadPiece());
				this.removeHeadPiece();
			}
		}

		if (this.getChestPiece() != null) {
			boolean brokeChestPiece = this.getChestPiece().loseDurability(damageToChest);
			if (brokeChestPiece) {
				System.out.println("You just broke your " + this.getHeadPiece());
				this.removeChestPiece();
			}
		}

		if (this.getLegPiece() != null) {
			boolean brokeLegPiece = this.getLegPiece().loseDurability(damageToLegs);
			if (brokeLegPiece) {
				System.out.println("You just broke your " + this.getHeadPiece());
				this.removeLegPiece();
			}
		}

		if (this.getFeetPiece() != null) {
			boolean brokeFeetPiece = this.getFeetPiece().loseDurability(damageToFeet);
			if (brokeFeetPiece) {
				System.out.println("You just broke your " + this.getHeadPiece());
				this.removeFeetPiece();
			}
		}

	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public void resetSlots() {
		this.headPiece = null;
		this.chestPiece = null;
		this.legPiece = null;
		this.feetPiece = null;
	}

	public void removeHeadPiece() {
		this.headPiece = null;
	}

	public void removeChestPiece() {
		this.chestPiece = null;
	}

	public void removeLegPiece() {
		this.legPiece = null;
	}

	public void removeFeetPiece() {
		this.feetPiece = null;
	}

	public Armour getHeadPiece() {
		return this.headPiece;
	}

	public Armour getChestPiece() {
		return this.chestPiece;
	}

	public Armour getLegPiece() {
		return this.legPiece;
	}

	public Armour getFeetPiece() {
		return this.feetPiece;
	}

	public ArrayList<Armour> getArmour() {
		return new ArrayList<Armour>(
				Arrays.asList(new Armour[] { this.headPiece, this.chestPiece, this.legPiece, this.feetPiece }));
	}

	public void setHeadPiece(Armour newPiece) {
		if (!newPiece.getSlot().equals("Head")) {
			System.out.println("Invalid Armour Type Provided!");
			return;
		}

		this.headPiece = newPiece;
	}

	public void setChestPiece(Armour newPiece) {
		if (!newPiece.getSlot().equals("Chest")) {
			System.out.println("Invalid Armour Type Provided!");
			return;
		}

		this.chestPiece = newPiece;
	}

	public void setLegPiece(Armour newPiece) {
		if (!newPiece.getSlot().equals("Legs")) {
			System.out.println("Invalid Armour Type Provided!");
			return;
		}

		this.legPiece = newPiece;
	}

	public void setFeetPiece(Armour newPiece) {
		if (!newPiece.getSlot().equals("Feet")) {
			System.out.println("Invalid Armour Type Provided!");
			return;
		}

		this.feetPiece = newPiece;
	}

}
