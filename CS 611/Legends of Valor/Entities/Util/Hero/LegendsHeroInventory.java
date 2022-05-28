/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Entities.Util.Hero;

import java.util.ArrayList;
import java.util.Scanner;

import Items.LegendsArmour;
import Items.LegendsItem;
import Items.LegendsSpell;
import Items.LegendsWeapon;
import Util.Printer;
import Util.Abstraction.Inventory;

public class LegendsHeroInventory extends Inventory {

	private LegendsWeapon equippedWeapon;
	private LegendsHeroArmour equippedArmour;

	private ArrayList<LegendsSpell> knownSpells;

	private int balance;

	private ArrayList<LegendsItem> items;

	/* =================== */
	/* Constructor Methods */
	/* =================== */
	
	public LegendsHeroInventory() {
		this.equippedArmour = null;
		this.equippedArmour = new LegendsHeroArmour();

		this.knownSpells = new ArrayList<LegendsSpell>();

		this.balance = 250;

	}
	
	/* ============ */
	/* Game Methods */
	/* ============ */
	
	public LegendsItem accessInventory() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		int choice = 0;
		
		while (true) {
			Printer.printMSG("What would you like to do?" + "\n" + "[1] View Inventory" + "\n" + "[2] Use Item" + "\n" + "[3] Exit");
			
			choice = scanner.nextInt();
			
			System.out.println();
			
			switch(choice) {
			case 1:
				printHeroInventory();
				break;
			case 2:
				return this.useItem();
			case 3:
				return null;
			default:
				Printer.printSetMessage("invalidResponse");
			}
		}
		
	}
	
	private LegendsItem useItem() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		int choice = 0;
		
		while (true) {
			Printer.printMSG("Please pick one of the following items:");
			
			this.printGeneralItems();
			choice = scanner.nextInt();
			
			if (choice >= 0 && choice < this.size()) {
				return this.remove(choice);
			}
			
			Printer.printSetMessage("invalidResponse");
		}
	}
	
	public LegendsSpell pickSpell() {
		if (this.knownSpells.size() == 0) {
			Printer.printMSG("You have not yet gained knowledge of any spells!");
			return null;
		}
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		int choice = 0;
		
		while (true) {
			Printer.printMSG("Please pick one of the following spells:");
			
			this.printKnownSpells();;
			choice = scanner.nextInt();
			
			if (choice >= 0 && choice < this.knownSpells.size()) {
				return this.knownSpells.get(choice);
			}

			Printer.printSetMessage("invalidResponse");
		}
	}
	
	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */
	
	public void learnSpell(LegendsSpell spell) {
		this.knownSpells.add(spell);
	}
	
	public void spendMoney(int toSpend) {
		this.balance -= toSpend;
	}
	
	public void gainMoney(int toGain) {
		this.balance += toGain;
	}
	
	public int getBalance() {
		return this.balance;
	}

	public LegendsWeapon getEquippedWeapon() {
		return this.equippedWeapon;
	}

	public LegendsHeroArmour getEquippedArmour() {
		return this.equippedArmour;
	}

	public ArrayList<LegendsSpell> getKnownSpells() {
		return this.knownSpells;
	}
	
	public void replaceEquippedWeapon(LegendsWeapon weapon) {
		if (equippedWeapon != null && equippedWeapon.getCurrentDurability() > 0)
			items.add(equippedWeapon);
		
		equippedWeapon = weapon;
	}
	
	public void switchArmour(LegendsArmour armour) {
		if (armour.getSlot().equals("Head")) {
			LegendsArmour toSwitch = this.getEquippedArmour().getHeadPiece();
			if (toSwitch != null)
				this.items.add(toSwitch);
			
			this.equippedArmour.setHeadPiece(armour);
		} else if (armour.getSlot().equals("Chest")) {
			LegendsArmour toSwitch = this.getEquippedArmour().getChestPiece();
			if (toSwitch != null)
				this.items.add(toSwitch);
			
			this.equippedArmour.setChestPiece(armour);
		} else if (armour.getSlot().equals("Legs")) {
			LegendsArmour toSwitch = this.getEquippedArmour().getLegPiece();
			if (toSwitch != null)
				this.items.add(toSwitch);
			
			this.equippedArmour.setLegPiece(armour);
		} else if (armour.getSlot().equals("Feet")) {
			LegendsArmour toSwitch = this.getEquippedArmour().getFeetPiece();
			if (toSwitch != null)
				this.items.add(toSwitch);
			
			this.equippedArmour.setFeetPiece(armour);
		} else {
			Printer.printSetMessage("invalidArmourType");;
		}
		
	}
	
	/* =========== */
	/* Aux Methods */
	/* =========== */

	public void printHeroInventory() {
		System.out.format("+------------------------+%n");
		System.out.format("|        INVENTORY       |%n");
		System.out.format("+------------------------+%n");
		printBalance();
		printEquipped();
		printKnownSpells();
		printGeneralItems();
	}

	private void printBalance() {
		System.out.format("Current Balance: " + this.balance + "%n");
	}


	private void printEquipped() {
		System.out.format("+-------------------------------+%n");
		System.out.format("|          Equipped Gear        |%n");
		System.out.format("+-------------------------------+%n");
		System.out.format("Equipped Armour:%n");
		System.out.format(this.equippedArmour + "%n");
		System.out.format("%n");
		System.out.format("Equipped Weapon:%n");
		if(this.equippedWeapon != null){
		   System.out.format(this.equippedWeapon + "%n");
		}
		else{
		   System.out.format("No weapon equipped.%n");
		}
	}
	private void printKnownSpells() {
		System.out.format("+------------------------+%n");
		System.out.format("|          SPELLS        |%n");
		System.out.format("+------------------------+%n");
		printItems(this.knownSpells);
	}

	private void printGeneralItems() {
		System.out.format("+-------------------------------+%n");
		System.out.format("|          General Items        |%n");
		System.out.format("+-------------------------------+%n");
		printItems(this.getRawInventory());
	}

	private void printItems(ArrayList<?> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.print(i + ": " + list.get(i));
		}
	}

}
