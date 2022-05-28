/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Util.Creation;

import java.util.ArrayList;

import Game.LegendsOfValor;
import Items.LegendsArmour;
import Items.LegendsItem;
import Items.LegendsPotion;
import Items.LegendsSpell;
import Items.LegendsWeapon;
import Util.Random;

public class ItemGenerator {
	// Absolute maximum values for certain details
	public static final int MAX_PRICE = 2500;
	public static final int MIN_PRICE = 10;
	public static final int LEVEL_TO_UNLOCK_EVERYTHING = 100;
	
	/*
	 * Static method to generate a list of items of size
	 * (typeOfItem.size()*amountOfEach). Each item is generated with completely
	 * random stats, all based around the base multiplier, which determines how good
	 * an item is.
	 * 
	 * The item name, and remaining stats are chosen in regards to the multiplier,
	 * while item subtypes (like armour slots, weapon's dual handedness, and
	 * potion/spells target abilities) are picked randomly.
	 */
	public static ArrayList<LegendsItem> generateItems(int amountOfEach) {
		ArrayList<LegendsItem> items = new ArrayList<LegendsItem>();

		for (int i = 0; i < amountOfEach; i++) {
			items.add(generateArmour(LegendsOfValor.ITEM_IDS));
			LegendsOfValor.ITEM_IDS++;

			items.add(generateWeapon(LegendsOfValor.ITEM_IDS));
			LegendsOfValor.ITEM_IDS++;

			items.add(generateSpell(LegendsOfValor.ITEM_IDS));
			LegendsOfValor.ITEM_IDS++;

			items.add(generatePotion(LegendsOfValor.ITEM_IDS));
			LegendsOfValor.ITEM_IDS++;
		}

		return items;
	}
	
	// Generate random Armour Piece as seen above
	private static LegendsArmour generateArmour(int ID) {
		double multiplier = Random.randomDouble(0, 1);
		int slot = Random.randomInt(1, 4);

		int degree = 3;
		if (multiplier < 0.333)
			degree = 1;
		else if (multiplier < 0.666)
			degree = 2;

		String name = Names.getArmourName(slot, degree);

		String slotName = "Head";
		if (slot == 2)
			slotName = "Chest";
		else if (slot == 3)
			slotName = "Legs";
		else if (slot == 4)
			slotName = "Feet";
		
		int cost = ((int) Math.round(ItemGenerator.MAX_PRICE * multiplier));
		int minLevel = ((int) Math.round(ItemGenerator.LEVEL_TO_UNLOCK_EVERYTHING * multiplier));
		double defenseMultiplier = multiplier * LegendsArmour.ABSOLUTE_MAX_DEFENSE_MULTIPLIER;
		int durability = ((int) Math.round(multiplier * LegendsArmour.ABSOLUTE_MAX_DURABILITY));

		return new LegendsArmour(ID, name, cost, minLevel, defenseMultiplier, durability, slotName);
	}
	
	// Generate random Weapon Item as seen above
	private static LegendsWeapon generateWeapon(int ID) {
		double multiplier = Random.randomDouble(0, 1);
		int dualHanded = Random.randomInt(1, 2);

		int degree = 3;
		if (multiplier < 0.333)
			degree = 1;
		else if (multiplier < 0.666)
			degree = 2;

		String name = Names.getWeaponName(dualHanded, degree);
		int cost = ((int) Math.round(ItemGenerator.MAX_PRICE * multiplier));
		int minLevel = ((int) Math.round(ItemGenerator.LEVEL_TO_UNLOCK_EVERYTHING * multiplier));
		int damage = ((int) Math.round(multiplier * LegendsWeapon.ABSOLUTE_MAX_DAMAGE));
		int maxDurability = ((int) Math.round(multiplier * LegendsWeapon.ABSOLUTE_MAX_DURABILITY));

		return new LegendsWeapon(ID, name, cost, minLevel, damage, (dualHanded == 2), maxDurability);
	}
	
	// Generate random Spell Item as seen above
	private static LegendsSpell generateSpell(int ID) {
		double multiplier = Random.randomDouble(0, 1);
		int targetAbility = Random.randomInt(1, 6);

		int degree = 3;
		if (multiplier < 0.333)
			degree = 1;
		else if (multiplier < 0.666)
			degree = 2;

		String name = Names.getSpellName(targetAbility, degree);

		String targetAbilityName = "Strength";
		if (targetAbility == 1)
			targetAbilityName = "Dexterity";
		else if (targetAbility == 3)
			targetAbilityName = "Agility";
		else if (targetAbility == 4)
			targetAbilityName = "Defense";
		if (targetAbility == 6)
			targetAbilityName = "Dodge";
		

		int cost = ((int) Math.round(ItemGenerator.MAX_PRICE * multiplier));
		int minLevel = ((int) Math.round(ItemGenerator.LEVEL_TO_UNLOCK_EVERYTHING * multiplier));
		
		int manaRequired = ((int) Math.round(LegendsSpell.ABSOLUTE_MAX_MANA_COST * multiplier));
		int maxDamage = (targetAbility > 3) ? (int) Math.round(LegendsSpell.ABSOLUTE_MAX_DAMAGE * multiplier) : 0;
		
		boolean isBuff = (targetAbility <= 3);
		
		double damageMultiplier = (multiplier * LegendsSpell.ABSOLUTE_MAX_MULTIPLIER);

		return new LegendsSpell(ID, name, cost, minLevel, manaRequired, maxDamage, targetAbilityName, isBuff, damageMultiplier);
	}
	
	// Generate random Potion Item as seen above
	private static LegendsPotion generatePotion(int ID) {
		double multiplier = Random.randomDouble(0, 1);
		int targetAbility = Random.randomInt(1, 6);

		int degree = 3;
		if (multiplier < 0.333)
			degree = 1;
		else if (multiplier < 0.666)
			degree = 2;

		String name = Names.getPotionName(targetAbility, degree);

		String targetAbilityName = "Strength";
		if (targetAbility == 1)
			targetAbilityName = "Dexterity";
		else if (targetAbility == 3)
			targetAbilityName = "Agility";
		else if (targetAbility == 4)
			targetAbilityName = "Defense";
		if (targetAbility == 6)
			targetAbilityName = "Dodge";

		int cost = ((int) Math.round(ItemGenerator.MAX_PRICE * multiplier));
		int minLevel = ((int) Math.round(ItemGenerator.LEVEL_TO_UNLOCK_EVERYTHING * multiplier));
		
		boolean isBuff = (targetAbility <= 3);
		
		double damageMultiplier = (multiplier * LegendsPotion.ABSOLUTE_MAX_MULTIPLIER);

		return new LegendsPotion(ID, name, cost, minLevel, isBuff, targetAbilityName, damageMultiplier);
	}
}
