/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Game.Creation;

import java.util.ArrayList;

import Game.LegendsGame;
import Game.LegendsItem;
import Game.Util.Creation.Names;
import Items.Armour;
import Items.Potion;
import Items.Spell;
import Items.Weapon;
import Items.Util.ArmourType;
import Items.Util.PotionType;
import Items.Util.SpellType;
import Items.Util.WeaponType;
import Util.Random;

public class ItemCreation {

	// Absolute maximum values for certain details
	public static final int MAX_PRICE = 4990;
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
			items.add(generateArmour(LegendsGame.ITEM_IDS));
			LegendsGame.ITEM_IDS++;

			items.add(generateWeapon(LegendsGame.ITEM_IDS));
			LegendsGame.ITEM_IDS++;

			items.add(generateSpell(LegendsGame.ITEM_IDS));
			LegendsGame.ITEM_IDS++;

			items.add(generatePotion(LegendsGame.ITEM_IDS));
			LegendsGame.ITEM_IDS++;
		}

		return items;
	}

	// Generate random Armour Piece as seen above
	private static Armour generateArmour(int ID) {
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

		return new Armour(ID, name, (((int) Math.round(ItemCreation.MAX_PRICE * multiplier)) + ItemCreation.MIN_PRICE),
				((int) Math.round(ItemCreation.LEVEL_TO_UNLOCK_EVERYTHING * multiplier)), slotName,
				new ArmourType(multiplier * ArmourType.ABSOLUTE_MAX_DEFENSE_MULTIPLIER,
						(int) Math.round(multiplier * ArmourType.ABSOLUTE_MAX_DURABILITY)));
	}

	// Generate random Weapon Item as seen above
	private static Weapon generateWeapon(int ID) {
		double multiplier = Random.randomDouble(0, 1);
		int dualHanded = Random.randomInt(1, 2);

		int degree = 3;
		if (multiplier < 0.333)
			degree = 1;
		else if (multiplier < 0.666)
			degree = 2;

		String name = Names.getWeaponName(dualHanded, degree);

		return new Weapon(ID, name, (((int) Math.round(ItemCreation.MAX_PRICE * multiplier)) + ItemCreation.MIN_PRICE),
				((int) Math.round(ItemCreation.LEVEL_TO_UNLOCK_EVERYTHING * multiplier)),
				new WeaponType((int) Math.round(multiplier * WeaponType.ABSOLUTE_MAX_DAMAGE), (dualHanded == 2),
						(int) Math.round(multiplier * WeaponType.ABSOLUTE_MAX_DURABILITY)));
	}

	// Generate random Spell Item as seen above
	private static Spell generateSpell(int ID) {
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

		return new Spell(ID, name, (((int) Math.round(ItemCreation.MAX_PRICE * multiplier)) + ItemCreation.MIN_PRICE),
				((int) Math.round(ItemCreation.LEVEL_TO_UNLOCK_EVERYTHING * multiplier)),
				new SpellType((targetAbility > 3) ? (int) Math.round(SpellType.ABSOLUTE_MIN_DAMAGE * multiplier) : 0,
						(targetAbility > 3) ? (int) Math.round(SpellType.ABSOLUTE_MAX_DAMAGE * multiplier) : 0,
						(int) Math.round(SpellType.ABSOLUTE_MAX_MANA_COST * multiplier), (targetAbility > 3),
						targetAbilityName, (multiplier * SpellType.ABSOLUTE_MAX_MULTIPLIER)));
	}

	// Generate random Potion Item as seen above
	private static Potion generatePotion(int ID) {
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

		return new Potion(ID, name, (((int) Math.round(ItemCreation.MAX_PRICE * multiplier)) + ItemCreation.MIN_PRICE),
				((int) Math.round(ItemCreation.LEVEL_TO_UNLOCK_EVERYTHING * multiplier)), new PotionType(
						!(targetAbility > 3), targetAbilityName, (multiplier * PotionType.ABSOLUTE_MAX_MULTIPLIER)));
	}
}
