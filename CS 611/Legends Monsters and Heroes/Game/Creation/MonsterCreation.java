/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Game.Creation;

import java.util.ArrayList;

import Classes.LegendsMonsterClass;
import Game.LegendsGame;
import Game.LegendsMonster;
import Game.Util.Creation.Names;
import Game.Util.Monster.LegendsMonsterGameStats;
import Game.Util.Monster.LegendsMonsterMonsterStats;
import Util.Random;

public class MonsterCreation {

	/*
	 * Static Method to generate a list of monsters of size
	 * (PossibleMonsterClasses.size()*amountOfEach). This list will then be used to
	 * randomly pick Monsters for encounters.
	 * 
	 * Each Monster will have a class, and randomly generated statistic relating to
	 * their class and level.
	 */
	public static ArrayList<LegendsMonster> generateMonster(int amountOfEach) {
		ArrayList<LegendsMonster> monsters = new ArrayList<LegendsMonster>();

		ArrayList<LegendsMonsterClass> localCopyMonsterClasses = LegendsMonsterClass.POSSIBLE_CLASSES;

		for (int i = 0; i < amountOfEach; i++) {
			for (LegendsMonsterClass classType : localCopyMonsterClasses) {
				monsters.add(new LegendsMonster(LegendsGame.ENTITY_IDS, Names.getName(classType),
						MonsterCreation.getMonsterStats(classType), new LegendsMonsterGameStats(), classType));
				LegendsGame.ENTITY_IDS++;
			}
		}

		return monsters;
	}

	/*
	 * Method to generate random Monster Stats based on the Monster Class, all based
	 * around the Monster's level, and following the basic math of the handout given
	 * to us.
	 */
	private static LegendsMonsterMonsterStats getMonsterStats(LegendsMonsterClass classType) {
		int level = Random.randomInt(1, 20);
		int maxHP = 100 * level;
		int strength = (int) Math
				.round(Random.randomInt(50, 150) * (Math.pow(classType.getDamageBoost() + 0.05, level)));
		int defense = (int) Math.round(Random.randomInt(0, 50) * (Math.pow(classType.getDefenseBoost() + 0.05, level)));
		double dodge = Math.max(Math
				.min(Random.randomDouble(0.0, 1.0) * (Math.pow(classType.getDodgeBoost() + 0.05, level)) - 1.0, 0.9),
				0.0001);

		return new LegendsMonsterMonsterStats(maxHP, defense, strength, dodge, level);
	}

}
