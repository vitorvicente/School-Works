/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Game.Util.Creation;

import java.util.ArrayList;
import java.util.Arrays;

public class NameListsWeapons {

	/*
	 * Static Lists of Random names that are used on Weapon Item Creation
	 */

	public static final ArrayList<String> LOW_TIER_SINGLE_HANDED_WEAPON_NAMES = new ArrayList<String>(
			Arrays.asList(new String[] { "Steel Dagger", "Bronze Dagger", "Stone Mace" }));
	public static final ArrayList<String> MEDIUM_TIER_SINGLE_HANDED_WEAPON_NAMES = new ArrayList<String>(
			Arrays.asList(new String[] { "Steel Sword", "Iron Smallaxe", "Steel Arak" }));
	public static final ArrayList<String> HIGH_TIER_SINGLE_HANDED_WEAPON_NAMES = new ArrayList<String>(
			Arrays.asList(new String[] { "Black Steel Sword", "Valyrian Steel Sword", "Gold-Encrusted Arak" }));

	public static final ArrayList<String> LOW_TIER_DUAL_HANDED_WEAPON_NAMES = new ArrayList<String>(
			Arrays.asList(new String[] { "Stone Heavy-Hammer", "Oaken Bow", "Bronze Pike" }));
	public static final ArrayList<String> MEDIUM_TIER_DUAL_HANDED_WEAPON_NAMES = new ArrayList<String>(
			Arrays.asList(new String[] { "Steel Heavy-Axe", "Iron Broadsword", "Steel Bastard-Sword" }));
	public static final ArrayList<String> HIGH_TIER_DUAL_HANDED_WEAPON_NAMES = new ArrayList<String>(
			Arrays.asList(new String[] { "Valyrian Steel Broadsword", "Black-Steel Bastard-Sword", "Elven Longbow" }));

}
