/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Game.Util.Monster;

public class LegendsMonsterGameStats {

	private int kills;
	private int deaths;
	
	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsMonsterGameStats() {
		this.resetStats();
	}


	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */
	
	private void resetStats() {
		this.kills = 0;
		this.deaths = 0;
	}

	public void addKill() {
		this.kills++;
	}

	public void addDeaths() {
		this.deaths++;
	}

	public int getKills() {
		return this.kills;
	}

	public int getDeaths() {
		return this.deaths;
	}
}
