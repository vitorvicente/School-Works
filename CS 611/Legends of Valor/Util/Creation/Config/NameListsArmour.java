/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Util.Creation.Config;

import java.util.ArrayList;
import java.util.Arrays;

public class NameListsArmour {
	
	/*
	 * Static Lists of Random names that are used on Armour Item Creation
	 */
	
	public static final ArrayList<String> LOW_TIER_HEAD_PIECE_NAMES = new ArrayList<String>(
			Arrays.asList(new String[] { "Leather Cap", "Bearskin Cap", "Wooden Bucket", "Blue Hat", "Grey Hood" }));
	public static final ArrayList<String> MEDIUM_TIER_HEAD_PIECE_NAMES = new ArrayList<String>(Arrays.asList(
			new String[] { "Bronze Helmet", "Iron Half-Helm", "Viking Helm", "Boar-Tusk Helmet", "Black-Iron Mask" }));
	public static final ArrayList<String> HIGH_TIER_HEAD_PIECE_NAMES = new ArrayList<String>(
			Arrays.asList(new String[] { "Iron Close-Helmet", "Black-Iron Helm", "Steel Helmet", "Gold-Encrusted Helm",
					"Valyrian-Steel Helm" }));

	public static final ArrayList<String> LOW_TIER_CHEST_PIECE_NAMES = new ArrayList<String>(
			Arrays.asList(new String[] { "Leather Chestplate", "Leather Tunic", "Grey Cloak", "Blue Leather-Shirt",
					"Bronze Shoulder-Plate" }));
	public static final ArrayList<String> MEDIUM_TIER_CHEST_PIECE_NAMES = new ArrayList<String>(
			Arrays.asList(new String[] { "Bronze Scale-Armour Chest Piece", "Iron Chestplate",
					"Black-Iron Mail Chest Piece", "Steel Chestplate", "Metal Laminar Chest Piece" }));
	public static final ArrayList<String> HIGH_TIER_CHEST_PIECE_NAMES = new ArrayList<String>(
			Arrays.asList(new String[] { "Iron-Enclosed Chestplate", "Gold-Encrusted Chestplate",
					"Valyrian-Steel Chestplace", "Ruby-Ornamented Tunic", "Black-Steel Chestplace" }));

	public static final ArrayList<String> LOW_TIER_LEG_PIECE_NAMES = new ArrayList<String>(Arrays.asList(
			new String[] { "Leather Pants", "Cloth Trousers", "Padded Leather-Jerkin", "Cloth Shorts", "Cloth Kilt" }));
	public static final ArrayList<String> MEDIUM_TIER_LEG_PIECE_NAMES = new ArrayList<String>(
			Arrays.asList(new String[] { "Iron Leggings", "Bronze Leggings", "Chain-Mail Leggings",
					"Metal Laminated Kilt", "Black-Iron Padded Battleskirt" }));
	public static final ArrayList<String> HIGH_TIER_LEG_PIECE_NAMES = new ArrayList<String>(
			Arrays.asList(new String[] { "Valyrian-Steel Leggings", "Black-Steel Leggings", "Gold-Encrusted Leggings",
					"Ruby-Ornamented Tunic Trousers", "Heavily-Plated Trousers" }));

	public static final ArrayList<String> LOW_TIER_FEET_PIECE_NAMES = new ArrayList<String>(Arrays.asList(
			new String[] { "Leather Boots", "Leather Slippers", "Wooden Slippers", "Leather Roman Sandals", "Crocs" }));
	public static final ArrayList<String> MEDIUM_TIER_FEET_PIECE_NAMES = new ArrayList<String>(
			Arrays.asList(new String[] { "Iron Plated Boots", "Chain-Mail Plated Boots", "Black-Iron Plated Boots",
					"Bronze Plated Boots", "Metal Laminar Plated Boots" }));
	public static final ArrayList<String> HIGH_TIER_FEET_PIECE_NAMES = new ArrayList<String>(
			Arrays.asList(new String[] { "Valeryian-Steel Plated Boots", "Gold-Encrusted Plated Boots",
					"Ruby-Encrusted Slippers", "Black-Steel Boots", "Velvet Slippers" }));
}
