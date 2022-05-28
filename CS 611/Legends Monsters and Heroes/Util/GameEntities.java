/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Util;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class GameEntities implements Iterable<Entity> {
	
	// Absolute maximums for Entities at play
	public static final int ABSOLUTE_MAX_ENTITIES_IN_GAME = 100;
	public static final int ABSOLUTE_MIN_ENTITIES_IN_GAME = 1;

	private boolean teamsAllowed;
	private int maxPlayersPerTeam;
	private int minPlayersPerTeam;

	private int maxEntitiesInGame;
	private int minEntitiesInGame;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GameEntities() {
		this.setMaxEntitiesInGame(GameEntities.ABSOLUTE_MAX_ENTITIES_IN_GAME);
		this.setMinEntitiesInGame(GameEntities.ABSOLUTE_MIN_ENTITIES_IN_GAME);
	}

	public GameEntities(int max) {
		this.setMaxEntitiesInGame(max);
		this.setMinEntitiesInGame(GameEntities.ABSOLUTE_MIN_ENTITIES_IN_GAME);
	}

	public GameEntities(int max, int min) {
		this.setMaxEntitiesInGame(max);
		this.setMinEntitiesInGame(min);
	}

	/* ================ */
	/* Abstract Methods */
	/* ================ */

	public abstract void addEntity(Entity e);

	public abstract void removeEntity(Entity e);

	public abstract void setEntities(ArrayList<Entity> entities);

	public abstract ArrayList<Entity> getEntities();

	public abstract void resetEntities();

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public int getMaxEntitiesInGame() {
		return this.maxEntitiesInGame;
	}

	public int getMinEntitiesInGame() {
		return this.minEntitiesInGame;
	}

	public void setMaxEntitiesInGame(int max) {
		if (max > GameEntities.ABSOLUTE_MAX_ENTITIES_IN_GAME) {
			System.out.println("Max Entities Value provided is too large, maximum value allowed is "
					+ GameEntities.ABSOLUTE_MAX_ENTITIES_IN_GAME);
			return;
		}
		if (max < GameEntities.ABSOLUTE_MIN_ENTITIES_IN_GAME) {
			System.out.println("Max Entities Value provided is too small, minimum value allowed is "
					+ GameEntities.ABSOLUTE_MIN_ENTITIES_IN_GAME);
			return;
		}

		if (max < this.getMinEntitiesInGame()) {
			System.out.println(
					"Max Entities Value provided is smaller than the current set minimum value of Entities allowed of "
							+ this.getMinEntitiesInGame());
			return;
		}

		this.maxEntitiesInGame = max;
	}

	public void setMinEntitiesInGame(int min) {
		if (min > GameEntities.ABSOLUTE_MAX_ENTITIES_IN_GAME) {
			System.out.println("Min Entities Value provided is too large, maximum value allowed is "
					+ GameEntities.ABSOLUTE_MAX_ENTITIES_IN_GAME);
			return;
		}
		if (min < GameEntities.ABSOLUTE_MIN_ENTITIES_IN_GAME) {
			System.out.println("Min Entities Value provided is too small, minimum value allowed is "
					+ GameEntities.ABSOLUTE_MIN_ENTITIES_IN_GAME);
			return;
		}

		if (min < this.getMaxEntitiesInGame()) {
			System.out.println(
					"Min Entities Value provided is larger than the current set maximum value of Entities allowed of "
							+ this.getMaxEntitiesInGame());
			return;
		}

		this.minEntitiesInGame = min;
	}

	public void setEntityLimitsInGame(int max, int min) {
		if (max > GameEntities.ABSOLUTE_MAX_ENTITIES_IN_GAME) {
			System.out.println("Max Entities Value provided is too large, maximum value allowed is "
					+ GameEntities.ABSOLUTE_MAX_ENTITIES_IN_GAME);
			return;
		}
		if (max < GameEntities.ABSOLUTE_MIN_ENTITIES_IN_GAME) {
			System.out.println("Max Entities Value provided is too small, minimum value allowed is "
					+ GameEntities.ABSOLUTE_MIN_ENTITIES_IN_GAME);
			return;
		}

		if (min > GameEntities.ABSOLUTE_MAX_ENTITIES_IN_GAME) {
			System.out.println("Min Entities Value provided is too large, maximum value allowed is "
					+ GameEntities.ABSOLUTE_MAX_ENTITIES_IN_GAME);
			return;
		}
		if (min < GameEntities.ABSOLUTE_MIN_ENTITIES_IN_GAME) {
			System.out.println("Min Entities Value provided is too small, minimum value allowed is "
					+ GameEntities.ABSOLUTE_MIN_ENTITIES_IN_GAME);
			return;
		}

		if (min > max) {
			System.out.println("Min Entities Value provided is larger than the Max Entities Value provided!");
			return;
		}

		this.minEntitiesInGame = min;
		this.maxEntitiesInGame = max;
	}

	public int getMaxPlayersPerTeam() {
		return this.maxPlayersPerTeam;
	}

	public int getMinPlayersPerTeam() {
		return this.minPlayersPerTeam;
	}

	// Checks if the value is within the allowed limits
	public void setMaxPlayersPerTeam(int max) {
		if (max > Team.ABSOLUTE_MAX_ENTITIES_PER_TEAM) {
			System.out.println("Max Players Per Team Value provided is too large, maximum value allowed is "
					+ Team.ABSOLUTE_MAX_ENTITIES_PER_TEAM);
			return;
		}
		if (max < Team.ABSOLUTE_MIN_ENTITIES_PER_TEAM) {
			System.out.println("Max Players Per Team Value provided is too small, minimum value allowed is "
					+ Team.ABSOLUTE_MIN_ENTITIES_PER_TEAM);
			return;
		}

		if (max < this.getMinPlayersPerTeam()) {
			System.out.println(
					"Max Players Per Team Value provided is smaller than the current set minimum value of Players Per Team allowed of "
							+ this.getMinPlayersPerTeam());
			return;
		}

		this.maxPlayersPerTeam = max;
	}

	// Checks if the value is within the allowed limits
	public void setMinPlayersPerTeam(int min) {
		if (min > Team.ABSOLUTE_MAX_ENTITIES_PER_TEAM) {
			System.out.println("Max Players Per Team Value provided is too large, maximum value allowed is "
					+ Team.ABSOLUTE_MAX_ENTITIES_PER_TEAM);
			return;
		}
		if (min < Team.ABSOLUTE_MIN_ENTITIES_PER_TEAM) {
			System.out.println("Max Players Per Team Value provided is too small, minimum value allowed is "
					+ Team.ABSOLUTE_MIN_ENTITIES_PER_TEAM);
			return;
		}

		if (min < this.getMaxPlayersPerTeam()) {
			System.out.println(
					"Min Players Per Team Value provided is larger than the current set maximum value of Players Per Team allowed of "
							+ this.getMaxPlayersPerTeam());
			return;
		}

		this.minPlayersPerTeam = min;
	}

	public void setTeamLimitsInGame(int max, int min) {
		if (max > Team.ABSOLUTE_MAX_ENTITIES_PER_TEAM) {
			System.out.println("Max Players Per Team Value provided is too large, maximum value allowed is "
					+ Team.ABSOLUTE_MAX_ENTITIES_PER_TEAM);
			return;
		}
		if (max < Team.ABSOLUTE_MIN_ENTITIES_PER_TEAM) {
			System.out.println("Max Players Per Team Value provided is too small, minimum value allowed is "
					+ Team.ABSOLUTE_MIN_ENTITIES_PER_TEAM);
			return;
		}

		if (min > Team.ABSOLUTE_MAX_ENTITIES_PER_TEAM) {
			System.out.println("Max Players Per Team Value provided is too large, maximum value allowed is "
					+ Team.ABSOLUTE_MAX_ENTITIES_PER_TEAM);
			return;
		}
		if (min < Team.ABSOLUTE_MIN_ENTITIES_PER_TEAM) {
			System.out.println("Max Players Per Team Value provided is too small, minimum value allowed is "
					+ Team.ABSOLUTE_MIN_ENTITIES_PER_TEAM);
			return;
		}

		if (min > max) {
			System.out.println(
					"Min Players Per Team Value provided is larger than the Max Players Per Team Value provided!");
			return;
		}

		this.maxPlayersPerTeam = min;
		this.minPlayersPerTeam = max;
	}

	public boolean getTeamsAllowed() {
		return this.teamsAllowed;
	}

	public void setTeamsAllowed(boolean teamsAllowed) {
		this.teamsAllowed = teamsAllowed;
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	public boolean isEntityInGame(Entity e) {
		if (this.getEntities().indexOf(e) == -1)
			return false;
		else
			return true;
	}

	@Override
	public Iterator<Entity> iterator() {
		return new Itr();
	}

	private class Itr implements Iterator<Entity> {
		private int index;

		public Itr() {
			this.index = 0;
		}

		@Override
		public boolean hasNext() {
			return this.index < getEntities().size();
		}

		@Override
		public Entity next() {
			Entity e = getEntities().get(index);
			this.index++;

			return e;
		}

	}

}
