/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Game;

import Game.Util.LegendsGamePlayerHeroes;
import Game.Util.LegendsPlayerGameStats;
import Util.Player;

public class LegendsPlayer extends Player {

	private LegendsPlayerGameStats gameRelatedStats;
	private LegendsGamePlayerHeroes heroes;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsPlayer(int ID) {
		super(ID);
		this.setHeroes(new LegendsGamePlayerHeroes());
		this.setGameRelatedStats(new LegendsPlayerGameStats());

		for (LegendsHero hero : this.heroes.getHeroes())
			this.gameRelatedStats.addHeroUsed(hero);

		this.gameRelatedStats.addGamesPlayed();
	}

	public LegendsPlayer(int ID, String name) {
		super(ID, name);
		this.setHeroes(new LegendsGamePlayerHeroes());
		this.setGameRelatedStats(new LegendsPlayerGameStats());

		for (LegendsHero hero : this.heroes.getHeroes())
			this.gameRelatedStats.addHeroUsed(hero);

		this.gameRelatedStats.addGamesPlayed();
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public LegendsPlayerGameStats getGameRelatedStats() {
		return gameRelatedStats;
	}

	public void setGameRelatedStats(LegendsPlayerGameStats gameRelatedStats) {
		this.gameRelatedStats = gameRelatedStats;
	}

	public LegendsGamePlayerHeroes getHeroes() {
		return heroes;
	}

	public void setHeroes(LegendsGamePlayerHeroes heroes) {
		this.heroes = heroes;
	}

}
