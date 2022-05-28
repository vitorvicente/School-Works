/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Game;

import Classes.LegendsEntityClass;
import Classes.LegendsMonsterClass;
import Game.Util.LegendsEntity;
import Game.Util.Monster.LegendsMonsterGameStats;
import Game.Util.Monster.LegendsMonsterMonsterStats;

public class LegendsMonster extends LegendsEntity {

	private LegendsMonsterClass monsterClass;
	private LegendsMonsterMonsterStats monsterRelatedStats;
	private LegendsMonsterGameStats gameRelatedStats;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsMonster(int ID, String name, LegendsMonsterMonsterStats monsterRelatedStats,
			LegendsMonsterGameStats gameRelatedStats, LegendsEntityClass eClass) {
		super(ID, name);
		this.setMonsterRelatedStats(monsterRelatedStats);
		this.setGameRelatedStats(gameRelatedStats);
		this.setEntityClass(eClass);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public LegendsMonsterMonsterStats getMonsterRelatedStats() {
		return monsterRelatedStats;
	}

	private void setMonsterRelatedStats(LegendsMonsterMonsterStats monsterRelatedStats) {
		this.monsterRelatedStats = monsterRelatedStats;
	}

	public LegendsMonsterGameStats getGameRelatedStats() {
		return gameRelatedStats;
	}

	private void setGameRelatedStats(LegendsMonsterGameStats gameRelatedStats) {
		this.gameRelatedStats = gameRelatedStats;
	}

	@Override
	public LegendsEntityClass getEntityClass() {
		return this.monsterClass;
	}

	@Override
	public void setEntityClass(LegendsEntityClass eClass) {
		if (!(eClass instanceof LegendsMonsterClass)) {
			System.out.println("Invalid Entity Class Type!");
			return;
		}

		this.monsterClass = (LegendsMonsterClass) eClass;
	}
}
