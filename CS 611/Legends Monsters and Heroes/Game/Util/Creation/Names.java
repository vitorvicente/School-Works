/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Game.Util.Creation;

import Classes.LegendsEntityClass;
import Classes.Heroes.Paladin;
import Classes.Heroes.Sorcerer;
import Classes.Heroes.Warrior;
import Classes.Monster.Dragon;
import Classes.Monster.Exoskeleton;
import Classes.Monster.Spirit;
import Util.Random;

public class Names {

	/*
	 * Static Method to get a random name for an entity based on its class.
	 */
	public static String getName(LegendsEntityClass eClass) {
		if (eClass instanceof Warrior)
			return NameListsEntities.WARRIOR_NAMES.get(Random.randomInt(0, NameListsEntities.WARRIOR_NAMES.size() - 1))
					+ ", " + NameListsEntities.WARRIOR_TITLES
							.get(Random.randomInt(0, NameListsEntities.WARRIOR_TITLES.size() - 1));
		else if (eClass instanceof Paladin)
			return NameListsEntities.PALADIN_NAMES.get(Random.randomInt(0, NameListsEntities.PALADIN_NAMES.size() - 1))
					+ ", " + NameListsEntities.PALADIN_TITLES
							.get(Random.randomInt(0, NameListsEntities.PALADIN_TITLES.size() - 1));
		else if (eClass instanceof Sorcerer)
			return NameListsEntities.SORCERER_NAMES
					.get(Random.randomInt(0, NameListsEntities.SORCERER_NAMES.size() - 1)) + ", "
					+ NameListsEntities.SORCERER_TITLES
							.get(Random.randomInt(0, NameListsEntities.SORCERER_TITLES.size() - 1));
		else if (eClass instanceof Dragon)
			return NameListsEntities.DRAGON_NAMES.get(Random.randomInt(0, NameListsEntities.DRAGON_NAMES.size() - 1))
					+ ", " + NameListsEntities.DRAGON_TITLES
							.get(Random.randomInt(0, NameListsEntities.DRAGON_TITLES.size() - 1));
		else if (eClass instanceof Spirit)
			return NameListsEntities.SPIRIT_NAMES.get(Random.randomInt(0, NameListsEntities.SPIRIT_NAMES.size() - 1));
		else if (eClass instanceof Exoskeleton)
			return NameListsEntities.EXOSKELETON_NAMES
					.get(Random.randomInt(0, NameListsEntities.EXOSKELETON_NAMES.size() - 1));
		else
			return NameListsEntities.HUMAN_NAMES.get(Random.randomInt(0, NameListsEntities.HUMAN_NAMES.size() - 1));
	}

	/*
	 * Static Method to get a random name for a Spell based on the spell's target
	 * ability, and "degree", which is basically how high the multiplier is (ie: how
	 * good the Spell is)
	 */
	public static String getSpellName(int targetAbility, int degree) {
		switch (targetAbility) {
		case 1:
			if (degree == 1)
				return NameListsSpells.LOW_TIER_DEXTERITY_SPELL_NAMES
						.get(Random.randomInt(0, NameListsSpells.LOW_TIER_DEXTERITY_SPELL_NAMES.size() - 1));
			else if (degree == 2)
				return NameListsSpells.MEDIUM_TIER_DEXTERITY_SPELL_NAMES
						.get(Random.randomInt(0, NameListsSpells.MEDIUM_TIER_DEXTERITY_SPELL_NAMES.size() - 1));
			else
				return NameListsSpells.HIGH_TIER_DEXTERITY_SPELL_NAMES
						.get(Random.randomInt(0, NameListsSpells.HIGH_TIER_DEXTERITY_SPELL_NAMES.size() - 1));
		case 2:
			if (degree == 1)
				return NameListsSpells.LOW_TIER_STRENGTH_BUFF_SPELL_NAMES
						.get(Random.randomInt(0, NameListsSpells.LOW_TIER_STRENGTH_BUFF_SPELL_NAMES.size() - 1));
			else if (degree == 2)
				return NameListsSpells.MEDIUM_TIER_STRENGTH_BUFF_SPELL_NAMES
						.get(Random.randomInt(0, NameListsSpells.MEDIUM_TIER_STRENGTH_BUFF_SPELL_NAMES.size() - 1));
			else
				return NameListsSpells.HIGH_TIER_STRENGTH_BUFF_SPELL_NAMES
						.get(Random.randomInt(0, NameListsSpells.HIGH_TIER_STRENGTH_BUFF_SPELL_NAMES.size() - 1));
		case 3:
			if (degree == 1)
				return NameListsSpells.LOW_TIER_AGILITY_SPELL_NAMES
						.get(Random.randomInt(0, NameListsSpells.LOW_TIER_AGILITY_SPELL_NAMES.size() - 1));
			else if (degree == 2)
				return NameListsSpells.MEDIUM_TIER_AGILITY_SPELL_NAMES
						.get(Random.randomInt(0, NameListsSpells.MEDIUM_TIER_AGILITY_SPELL_NAMES.size() - 1));
			else
				return NameListsSpells.HIGH_TIER_AGILITY_SPELL_NAMES
						.get(Random.randomInt(0, NameListsSpells.HIGH_TIER_AGILITY_SPELL_NAMES.size() - 1));
		case 4:
			if (degree == 1)
				return NameListsSpells.LOW_TIER_DEFENSE_SPELL_NAMES
						.get(Random.randomInt(0, NameListsSpells.LOW_TIER_DEFENSE_SPELL_NAMES.size() - 1));
			else if (degree == 2)
				return NameListsSpells.MEDIUM_TIER_DEFENSE_SPELL_NAMES
						.get(Random.randomInt(0, NameListsSpells.MEDIUM_TIER_DEFENSE_SPELL_NAMES.size() - 1));
			else
				return NameListsSpells.HIGH_TIER_DEFENSE_SPELL_NAMES
						.get(Random.randomInt(0, NameListsSpells.HIGH_TIER_DEFENSE_SPELL_NAMES.size() - 1));
		case 5:
			if (degree == 1)
				return NameListsSpells.LOW_TIER_STRENGTH_DEBUFF_SPELL_NAMES
						.get(Random.randomInt(0, NameListsSpells.LOW_TIER_STRENGTH_DEBUFF_SPELL_NAMES.size() - 1));
			else if (degree == 2)
				return NameListsSpells.MEDIUM_TIER_STRENGTH_DEBUFF_SPELL_NAMES
						.get(Random.randomInt(0, NameListsSpells.MEDIUM_TIER_STRENGTH_DEBUFF_SPELL_NAMES.size() - 1));
			else
				return NameListsSpells.HIGH_TIER_STRENGTH_DEBUFF_SPELL_NAMES
						.get(Random.randomInt(0, NameListsSpells.HIGH_TIER_STRENGTH_DEBUFF_SPELL_NAMES.size() - 1));
		default:
			if (degree == 1)
				return NameListsSpells.LOW_TIER_DODGE_SPELL_NAMES
						.get(Random.randomInt(0, NameListsSpells.LOW_TIER_DODGE_SPELL_NAMES.size() - 1));
			else if (degree == 2)
				return NameListsSpells.MEDIUM_TIER_DODGE_SPELL_NAMES
						.get(Random.randomInt(0, NameListsSpells.MEDIUM_TIER_DODGE_SPELL_NAMES.size() - 1));
			else
				return NameListsSpells.HIGH_TIER_DODGE_SPELL_NAMES
						.get(Random.randomInt(0, NameListsSpells.HIGH_TIER_DODGE_SPELL_NAMES.size() - 1));
		}
	}

	/*
	 * Static Method to get a random name for a Spell based on the spell's target
	 * ability, and "degree", same logic as Spells
	 */
	public static String getPotionName(int targetAbility, int degree) {
		switch (targetAbility) {
		case 1:
			if (degree == 1)
				return NameListsPotions.LOW_TIER_DEXTERITY_POTION_NAMES
						.get(Random.randomInt(0, NameListsPotions.LOW_TIER_DEXTERITY_POTION_NAMES.size() - 1));
			else if (degree == 2)
				return NameListsPotions.MEDIUM_TIER_DEXTERITY_POTION_NAMES
						.get(Random.randomInt(0, NameListsPotions.MEDIUM_TIER_DEXTERITY_POTION_NAMES.size() - 1));
			else
				return NameListsPotions.HIGH_TIER_DEXTERITY_POTION_NAMES
						.get(Random.randomInt(0, NameListsPotions.HIGH_TIER_DEXTERITY_POTION_NAMES.size() - 1));
		case 2:
			if (degree == 1)
				return NameListsPotions.LOW_TIER_STRENGTH_BUFF_POTION_NAMES
						.get(Random.randomInt(0, NameListsPotions.LOW_TIER_STRENGTH_BUFF_POTION_NAMES.size() - 1));
			else if (degree == 2)
				return NameListsPotions.MEDIUM_TIER_STRENGTH_BUFF_POTION_NAMES
						.get(Random.randomInt(0, NameListsPotions.MEDIUM_TIER_STRENGTH_BUFF_POTION_NAMES.size() - 1));
			else
				return NameListsPotions.HIGH_TIER_STRENGTH_BUFF_POTION_NAMES
						.get(Random.randomInt(0, NameListsPotions.HIGH_TIER_STRENGTH_BUFF_POTION_NAMES.size() - 1));
		case 3:
			if (degree == 1)
				return NameListsPotions.LOW_TIER_AGILITY_POTION_NAMES
						.get(Random.randomInt(0, NameListsPotions.LOW_TIER_AGILITY_POTION_NAMES.size() - 1));
			else if (degree == 2)
				return NameListsPotions.MEDIUM_TIER_AGILITY_POTION_NAMES
						.get(Random.randomInt(0, NameListsPotions.MEDIUM_TIER_AGILITY_POTION_NAMES.size() - 1));
			else
				return NameListsPotions.HIGH_TIER_AGILITY_POTION_NAMES
						.get(Random.randomInt(0, NameListsPotions.HIGH_TIER_AGILITY_POTION_NAMES.size() - 1));
		case 4:
			if (degree == 1)
				return NameListsPotions.LOW_TIER_DEFENSE_POTION_NAMES
						.get(Random.randomInt(0, NameListsPotions.LOW_TIER_DEFENSE_POTION_NAMES.size() - 1));
			else if (degree == 2)
				return NameListsPotions.MEDIUM_TIER_DEFENSE_POTION_NAMES
						.get(Random.randomInt(0, NameListsPotions.MEDIUM_TIER_DEFENSE_POTION_NAMES.size() - 1));
			else
				return NameListsPotions.HIGH_TIER_DEFENSE_POTION_NAMES
						.get(Random.randomInt(0, NameListsPotions.HIGH_TIER_DEFENSE_POTION_NAMES.size() - 1));
		case 5:
			if (degree == 1)
				return NameListsPotions.LOW_TIER_STRENGTH_DEBUFF_POTION_NAMES
						.get(Random.randomInt(0, NameListsPotions.LOW_TIER_STRENGTH_DEBUFF_POTION_NAMES.size() - 1));
			else if (degree == 2)
				return NameListsPotions.MEDIUM_TIER_STRENGTH_DEBUFF_POTION_NAMES
						.get(Random.randomInt(0, NameListsPotions.MEDIUM_TIER_STRENGTH_DEBUFF_POTION_NAMES.size() - 1));
			else
				return NameListsPotions.HIGH_TIER_STRENGTH_DEBUFF_POTION_NAMES
						.get(Random.randomInt(0, NameListsPotions.HIGH_TIER_STRENGTH_DEBUFF_POTION_NAMES.size() - 1));
		default:
			if (degree == 1)
				return NameListsPotions.LOW_TIER_DODGE_POTION_NAMES
						.get(Random.randomInt(0, NameListsPotions.LOW_TIER_DODGE_POTION_NAMES.size() - 1));
			else if (degree == 2)
				return NameListsPotions.MEDIUM_TIER_DODGE_POTION_NAMES
						.get(Random.randomInt(0, NameListsPotions.MEDIUM_TIER_DODGE_POTION_NAMES.size() - 1));
			else
				return NameListsPotions.HIGH_TIER_DODGE_POTION_NAMES
						.get(Random.randomInt(0, NameListsPotions.HIGH_TIER_DODGE_POTION_NAMES.size() - 1));
		}
	}

	/*
	 * Static Method to get a random name for a Weapon based on its degree (same
	 * thing as before), and whether the Weapon is double handed or not
	 */
	public static String getWeaponName(int doubleHanded, int degree) {
		switch (doubleHanded) {
		case 1:
			if (degree == 1)
				return NameListsWeapons.LOW_TIER_SINGLE_HANDED_WEAPON_NAMES
						.get(Random.randomInt(0, NameListsWeapons.LOW_TIER_SINGLE_HANDED_WEAPON_NAMES.size() - 1));
			else if (degree == 2)
				return NameListsWeapons.MEDIUM_TIER_SINGLE_HANDED_WEAPON_NAMES
						.get(Random.randomInt(0, NameListsWeapons.MEDIUM_TIER_SINGLE_HANDED_WEAPON_NAMES.size() - 1));
			else
				return NameListsWeapons.HIGH_TIER_SINGLE_HANDED_WEAPON_NAMES
						.get(Random.randomInt(0, NameListsWeapons.HIGH_TIER_SINGLE_HANDED_WEAPON_NAMES.size() - 1));
		default:
			if (degree == 1)
				return NameListsWeapons.LOW_TIER_DUAL_HANDED_WEAPON_NAMES
						.get(Random.randomInt(0, NameListsWeapons.LOW_TIER_DUAL_HANDED_WEAPON_NAMES.size() - 1));
			else if (degree == 2)
				return NameListsWeapons.MEDIUM_TIER_DUAL_HANDED_WEAPON_NAMES
						.get(Random.randomInt(0, NameListsWeapons.MEDIUM_TIER_DUAL_HANDED_WEAPON_NAMES.size() - 1));
			else
				return NameListsWeapons.HIGH_TIER_DUAL_HANDED_WEAPON_NAMES
						.get(Random.randomInt(0, NameListsWeapons.HIGH_TIER_DUAL_HANDED_WEAPON_NAMES.size() - 1));
		}
	}

	/*
	 * Static Method to get a random name for a Armour based on its degree (same
	 * thing as before), and the piece's slot (Head, Chest, Legs, Boots)
	 */
	public static String getArmourName(int armourSlot, int degree) {
		switch (armourSlot) {
		case 1:
			if (degree == 1)
				return NameListsArmour.LOW_TIER_HEAD_PIECE_NAMES
						.get(Random.randomInt(0, NameListsArmour.LOW_TIER_HEAD_PIECE_NAMES.size() - 1));
			else if (degree == 2)
				return NameListsArmour.MEDIUM_TIER_HEAD_PIECE_NAMES
						.get(Random.randomInt(0, NameListsArmour.MEDIUM_TIER_HEAD_PIECE_NAMES.size() - 1));
			else
				return NameListsArmour.HIGH_TIER_HEAD_PIECE_NAMES
						.get(Random.randomInt(0, NameListsArmour.HIGH_TIER_HEAD_PIECE_NAMES.size() - 1));
		case 2:
			if (degree == 1)
				return NameListsArmour.LOW_TIER_CHEST_PIECE_NAMES
						.get(Random.randomInt(0, NameListsArmour.LOW_TIER_CHEST_PIECE_NAMES.size() - 1));
			else if (degree == 2)
				return NameListsArmour.MEDIUM_TIER_CHEST_PIECE_NAMES
						.get(Random.randomInt(0, NameListsArmour.MEDIUM_TIER_CHEST_PIECE_NAMES.size() - 1));
			else
				return NameListsArmour.HIGH_TIER_CHEST_PIECE_NAMES
						.get(Random.randomInt(0, NameListsArmour.HIGH_TIER_CHEST_PIECE_NAMES.size() - 1));
		case 3:
			if (degree == 1)
				return NameListsArmour.LOW_TIER_LEG_PIECE_NAMES
						.get(Random.randomInt(0, NameListsArmour.LOW_TIER_LEG_PIECE_NAMES.size() - 1));
			else if (degree == 2)
				return NameListsArmour.MEDIUM_TIER_LEG_PIECE_NAMES
						.get(Random.randomInt(0, NameListsArmour.MEDIUM_TIER_LEG_PIECE_NAMES.size() - 1));
			else
				return NameListsArmour.HIGH_TIER_LEG_PIECE_NAMES
						.get(Random.randomInt(0, NameListsArmour.HIGH_TIER_LEG_PIECE_NAMES.size() - 1));
		default:
			if (degree == 1)
				return NameListsArmour.LOW_TIER_FEET_PIECE_NAMES
						.get(Random.randomInt(0, NameListsArmour.LOW_TIER_FEET_PIECE_NAMES.size() - 1));
			else if (degree == 2)
				return NameListsArmour.MEDIUM_TIER_FEET_PIECE_NAMES
						.get(Random.randomInt(0, NameListsArmour.MEDIUM_TIER_FEET_PIECE_NAMES.size() - 1));
			else
				return NameListsArmour.HIGH_TIER_FEET_PIECE_NAMES
						.get(Random.randomInt(0, NameListsArmour.HIGH_TIER_FEET_PIECE_NAMES.size() - 1));
		}
	}
}
