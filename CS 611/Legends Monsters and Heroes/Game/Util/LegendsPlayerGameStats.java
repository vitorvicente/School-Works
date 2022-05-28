/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Game.Util;

import java.util.ArrayList;

import Game.LegendsHero;

public class LegendsPlayerGameStats {

	private int kills;
	private int deaths;

	private int tilesMoved;

	private int itemsBought;
	private int itemsSold;

	private int friendliesRevived;
	private int timesRevived;

	private int gamesPlayed;

	private int heroesUsed;
	private ArrayList<LegendsHero> heroesList;
	
	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsPlayerGameStats() {
		this.resetStats();
	}
	
	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	private void resetStats() {
		this.kills = 0;
		this.deaths = 0;
		this.tilesMoved = 0;
		this.itemsBought = 0;
		this.itemsSold = 0;
		this.friendliesRevived = 0;
		this.timesRevived = 0;
		this.gamesPlayed = 0;
		this.heroesUsed = 0;
		this.heroesList = new ArrayList<LegendsHero>();
	}

	public void addKill() {
		this.kills++;
	}

	public void addDeaths() {
		this.deaths++;
	}

	public void addTilesMoved() {
		this.tilesMoved++;
	}

	public void addItemsBought() {
		this.itemsBought++;
	}

	public void addItemsSold() {
		this.itemsSold++;
	}

	public void addFriendliesRevived() {
		this.friendliesRevived++;
	}

	public void addTimesRevived() {
		this.timesRevived++;
	}

	public void addGamesPlayed() {
		this.gamesPlayed++;
	}

	public void addHeroUsed(LegendsHero hero) {
		this.heroesUsed++;
		this.heroesList.add(hero);
	}

	public int getKills() {
		return this.kills;
	}

	public int getDeaths() {
		return this.deaths;
	}

	public int getTilesMoved() {
		return this.tilesMoved;
	}

	public int getItemsBought() {
		return this.itemsBought;
	}

	public int getItemsSold() {
		return this.itemsSold;
	}

	public int getFriendliesRevived() {
		return this.friendliesRevived;
	}

	public int getTimesRevived() {
		return this.timesRevived;
	}

	public int getGamesPlayed() {
		return this.gamesPlayed;
	}

	public int getHeroesUsed() {
		return this.heroesUsed;
	}

	public ArrayList<LegendsHero> getHeroesList() {
		return this.heroesList;
	}
}
