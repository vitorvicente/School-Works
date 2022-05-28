/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Util;

public abstract class Entity implements Comparable<Entity> {
	
	private int ID;
	private String name;

	private int wins;
	private int losses;
	private int draws;

	/*
	 * Although all other attributes are pretty obvious, this one in specific will
	 * be a list of available pieces, in the future this could be expanded to its
	 * own class, but no need for now
	 */

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	/*
	 * There are a lot of constructors here mostly because I feel like it'd be
	 * possible to use all of these individual constructors, yet all of them do the
	 * same thing, setID, setName, resetStates (which basically sets all
	 * of them to 0) no constructor can lack ID, however if it lacks any of the
	 * others it still initiates them to something unamed Entities are called
	 * "Entity #ID", all Entities begin with zeroed out stats.
	 */
	public Entity(int ID) {
		this.setID(ID);
		this.setName("Entity #" + this.getID());
		this.resetStats();
	}

	public Entity(int ID, String name) {
		this.setID(ID);
		this.setName(name);
		this.resetStats();
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public int getID() {
		return this.ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWins() {
		return this.wins;
	}

	public int getLosses() {
		return this.losses;
	}

	public int getDraws() {
		return this.draws;
	}

	// These variables can only ever go up, or be resetted, so the setter methods
	// reflect that
	public void addWin() {
		this.wins++;
	}

	public void addLoss() {
		this.losses++;
	}

	public void addDraw() {
		this.draws++;
	}

	public void resetWins() {
		this.wins = 0;
	}

	public void resetLosses() {
		this.losses = 0;
	}

	public void resetDraws() {
		this.draws = 0;
	}

	// Global Reset
	public void resetStats() {
		this.resetWins();
		this.resetLosses();
		this.resetDraws();
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	// Announce that the entity won
	public void announceWin() {
		System.out.println();
		System.out.println(this.getName() + " has won the game!!");
		System.out.println();
	}

	// Return a formatted string with the scores
	public String getScores() {
		return this.getName() + ": " + this.getWins() + " Wins | " + this.getLosses() + " Losses | " + this.getDraws()
				+ " Draws";
	}

	// Return raw scores (just in case anyone needs them)
	public int[] getRawScores() {
		return new int[] { this.getWins(), this.getLosses(), this.getDraws() };
	}

	// Overriding the default equals method
	public boolean equals(Entity t) {
		return this.getID() == t.getID();
	}

	/*
	 * This is the compareTo method required for the Comparable interface. First it
	 * does a check to see if the Entities are the same (ie: have the same ID, in
	 * which case it returns that they are equal in "size". It is structured to first
	 * check which team has more wins, and thus return that the current Entity is
	 * larger if it has more wins than the other, and vice-versa. In the case they
	 * have the same amount of wins, it performs the same check on draws and returns
	 * the correct value. In the case that both of these values are equal, it
	 * performs the inverted check on the amount of losses each Entity has,
	 * returning that the current Entity is larger if it has fewer losses. If even
	 * this value is the same, then it will return 0, which means they are, quite
	 * literally, tied on all accounts.
	 */
	public int compareTo(Entity t) {
		if (this.equals(t))
			return 0;

		if (this.getWins() > t.getWins())
			return 1;
		else if (this.getWins() < t.getWins())
			return -1;

		if (this.getDraws() > t.getDraws())
			return 1;
		else if (this.getDraws() < t.getDraws())
			return -1;

		if (this.getLosses() < t.getLosses())
			return 1;
		else if (this.getLosses() > t.getLosses())
			return -1;

		return 0;
	}
	
	public String toString() {
		return this.getName();
	} 
}
