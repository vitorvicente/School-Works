/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Util.Abstraction;

import java.util.*;

import Items.LegendsArmour;
import Items.LegendsItem;
import Items.LegendsPotion;
import Items.LegendsSpell;
import Items.LegendsWeapon;
import Util.Creation.ItemGenerator;

public class Inventory {

	private ArrayList<LegendsItem> inventory;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Inventory() {
		inventory = new ArrayList<LegendsItem>();
	}

	public Inventory(int numToGenerate) {
		inventory = ItemGenerator.generateItems(numToGenerate);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */
	
	public ArrayList<LegendsItem> getRawInventory() {
		return inventory;
	}

	public boolean isEmpty() {
		return inventory.size() <= 0;
	}

	public LegendsItem remove(int index) {
		return this.inventory.remove(index);
	}

	public void remove(LegendsItem item) {
		this.inventory.remove(item);
	}

	public void add(LegendsItem item) {
		this.inventory.add(item);
	}

	public int size() {
		return this.inventory.size();
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	/*
	 * The methods below print the Inventory in a fancy way
	 */
	public void printInventory() {
		System.out.println();

		String weaponsFormat = "| [%-2d] | %-50s | %-4d | %-3d | %-10s | %-6s  |  %-4d  |  %-5b  | %-10s |   %-6s   | %-4s | %n";
		String armourFormat = "| [%-2d] | %-50s | %-4d | %-3d | %-10s | %.4f  |  %-4s  |  %-5s  | %-10s |   %-6s   | %-4s | %n";
		String spellFormat = "| [%-2d] | %-50s | %-4d | %-3d | %-10s | %-6s  |  %-4d  |  %-5s  | %-10s |   %2.4f   | %-4d | %n";
		String potionFormat = "| [%-2d] | %-50s | %-4d | %-3d | %-10s | %-6s  |  %-4s  |  %-5s  | %-10s |   %2.4f   | %-4s | %n";
		String headerFormat = "| %-4s | %-50s | %-4s | %-3s | %-10s | %-6s | %-4s | %-5s | %-10s | %-6s | %-4s | %n";

		int numSpaces = 147;
		System.out.println("+" + concatLine(numSpaces, "-") + "+");
		System.out.println("|" + concatLine(numSpaces, " ") + "|");
		System.out.println(
				"|" + concatLine((numSpaces / 2) - 4, " ") + "INVENTORY " + concatLine((numSpaces / 2) - 5, " ") + "|");
		System.out.println("|" + concatLine(numSpaces, " ") + "|");
		printHelperLine();
		System.out.printf(headerFormat, "##", concatLine(23, " ") + "NAME" + concatLine(23, " "), "COST", "LVL",
				"   CLASS  ", "DEFENSE", "DAMAGE", "2 HAND?", "  ABILITY ", "MULTIPLIER", "MANA");
		printHelperLine();

		for (int i = 0; i < inventory.size(); i++) {
			LegendsItem currItem = inventory.get(i);
			if (currItem instanceof LegendsWeapon) {
				System.out.printf(weaponsFormat, i, currItem.getName(), currItem.getCost(), currItem.getMinLevel(),
						"WEAPON", "", ((LegendsWeapon) currItem).getDamage(),
						((LegendsWeapon) currItem).getDualHanded(), "", "", "");
			} else if (currItem instanceof LegendsArmour) {
				System.out.printf(armourFormat, i, currItem.getName(), currItem.getCost(), currItem.getMinLevel(),
						"ARMOR", ((LegendsArmour) currItem).getDefenseMultiplier(), "", "", "", "", "");
			} else if (currItem instanceof LegendsSpell) {
				System.out.printf(spellFormat, i, currItem.getName(), currItem.getCost(), currItem.getMinLevel(),
						"SPELL", "", ((LegendsSpell) currItem).getMaxDamage(), "",
						((LegendsSpell) currItem).getTargetAbility(), ((LegendsSpell) currItem).getMultiplier(),
						((LegendsSpell) currItem).getManaRequired());
			} else if (currItem instanceof LegendsPotion) {
				System.out.printf(potionFormat, i, currItem.getName(), currItem.getCost(), currItem.getMinLevel(),
						"POTION", "", "", "", ((LegendsPotion) currItem).getTargetAbility(),
						((LegendsPotion) currItem).getMultiplier(), "");
			}
			printHelperLine();
		}

		System.out.println();
	}

	private String concatLine(int numSpaces, String toPrint) {
		String ret = "";
		for (int i = 0; i < numSpaces; i++) {
			ret += toPrint;
		}
		return ret;
	}

	private void printHelperLine() {
		System.out.println("+" + concatLine(6, "-") + "+" + concatLine(52, "-") + "+" + concatLine(6, "-") + "+"
				+ concatLine(5, "-") + "+" + concatLine(12, "-") + "+" + concatLine(9, "-") + "+" + concatLine(8, "-")
				+ "+" + concatLine(9, "-") + "+" + concatLine(12, "-") + "+" + concatLine(12, "-") + "+"
				+ concatLine(6, "-") + "+");

	}

}
