package Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Team extends Entity implements Iterable<Player> {
	public final static int ABSOLUTE_MAX_ENTITIES_PER_TEAM = 100;
	public final static int ABSOLUTE_MIN_ENTITIES_PER_TEAM = 1;
	private int maxPlayersPerTeam;
	private ArrayList<Player> players;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	/*
	 * There are _a lot_ of constructors here, and although I'll likely only use 1
	 * or 2, the reason why I added all of them is because I think it'd be a
	 * perfectly reasonable assumption that this class could be "constructed" using
	 * any individual one of these, further, the fact that there are _a lot_ of
	 * variables, means that we have exponentially as many constructors.
	 */

	public Team(int ID) {
		super(ID, "Team #" + ID);
		this.setMaxPlayersPerTeam(Team.ABSOLUTE_MAX_ENTITIES_PER_TEAM);
		this.createPlayers();
	}

	public Team(int ID, String name) {
		super(ID, name);
		this.setMaxPlayersPerTeam(Team.ABSOLUTE_MAX_ENTITIES_PER_TEAM);
		this.createPlayers();
	}

	public Team(int ID, int maxPlayersPerTeam) {
		super(ID, "Team #" + ID);
		this.setMaxPlayersPerTeam(maxPlayersPerTeam);
		this.createPlayers();
	}

	public Team(int ID, String name, int maxPlayersPerTeam) {
		super(ID, name);
		this.setMaxPlayersPerTeam(maxPlayersPerTeam);
		this.createPlayers();
	}


	/* ===================== */
	/* Functionality Methods */
	/* ===================== */

	private void createPlayers() {
		this.resetPlayers();
		
		System.out.println();
		System.out.println("Team creation began for " + this.getName());
		System.out.println();

		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String answer = "";
		int counter = 0;
		boolean cont = true;

		while (cont) {
			if (counter == this.getMaxPlayersPerTeam()) {
				System.out.println();
				System.out.println("Maximum amount of players created! Exiting creation method!");
				System.out.println();
				cont = false;
				break;
			}

			System.out.println();
			System.out.println("Would you like to add another player or exit? ('new'/'exit')");
			System.out.println();
			answer = input.nextLine();

			switch (answer) {
			case "new":
			case "New":
			case "N":
			case "n":
				Player p = new Player(counter+1);
				this.addPlayer(p);
				counter++;
				break;
			case "exit":
			case "Exit":
			case "E":
			case "e":
				cont = false;
				break;
			default:
				System.out.println("Invalid Input, only accepted inputs are: 'Exit', 'exit', 'New', 'new'");
				break;
			}
		}

		System.out.println();
		System.out.println("Team creation over for " + this.getName());
		System.out.println();
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	// This method has 1 little detail that's important, and it stops them from
	// inputting "bad" values using a theoretical limit up and down
	public void setMaxPlayersPerTeam(int maxPlayersPerTeam) {
		if (maxPlayersPerTeam > Team.ABSOLUTE_MAX_ENTITIES_PER_TEAM) {
			System.out.println("Max Players Per Team Value provided is too large, maximum value allowed is "
					+ Team.ABSOLUTE_MAX_ENTITIES_PER_TEAM);
			return;
		}
		if (maxPlayersPerTeam < Team.ABSOLUTE_MIN_ENTITIES_PER_TEAM) {
			System.out.println("Max Players Per Team Value provided is too small, minimum value allowed is "
					+ Team.ABSOLUTE_MIN_ENTITIES_PER_TEAM);
			return;
		}
		this.maxPlayersPerTeam = maxPlayersPerTeam;
	}

	public int getMaxPlayersPerTeam() {
		return this.maxPlayersPerTeam;
	}
	

	public void setPlayers(ArrayList<Player> players) {
		if (players.size() > this.getMaxPlayersPerTeam()) {
			System.out.println(
					"Player List provided is too large, maximum allowed size is: " + this.getMaxPlayersPerTeam());
			return;
		}

		if (players.size() < Team.ABSOLUTE_MIN_ENTITIES_PER_TEAM) {
			System.out.println("Player List provided is too small, minimum allowed size is: "
					+ Team.ABSOLUTE_MIN_ENTITIES_PER_TEAM);
			return;
		}

		this.players = players;
	}

	// Besides assuring that there is space, this method also checks to see if the
	// player is already on the team
	public void addPlayer(Player p) {
		if (this.getPlayers().size() == this.getMaxPlayersPerTeam()) {
			System.out.println("Cannot add any more players to the team, maximum value (" + this.getMaxPlayersPerTeam()
					+ ") reached!!");
			return;
		}

		if (this.isPlayerInTeam(p)) {
			System.out.println("Player is already on the team!!");
			return;
		}

		this.players.add(p);
	}

	public void removePlayer(Player p) {
		if (this.getPlayers().size() == 0) {
			System.out.println("No players on the team!!");
			return;
		}

		if (this.getPlayers().size() == Team.ABSOLUTE_MIN_ENTITIES_PER_TEAM) {
			System.out.println("Cannot remove any more players to the team, minimum value ("
					+ Team.ABSOLUTE_MIN_ENTITIES_PER_TEAM + ") reached!!");
			return;
		}

		if (!this.isPlayerInTeam(p)) {
			System.out.println("Player not on the team!");
			return;
		}

		this.players.remove(p);
	}

	public ArrayList<Player> getPlayers() {
		return this.players;
	}
	
	public Player getPlayerAtIndex(int index) {
		return this.getPlayers().get(index);
	}

	public void resetPlayers() {
		this.players = new ArrayList<Player>();
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	/*
	 * Both of these methods are to assist with adding/removing players, they also
	 * implement some custom implementation of methods that technically already
	 * exist, but could fail in-use for these cases, so I just coded my own.
	 */
	public boolean isPlayerInTeam(Player p) {
		if (this.indexOfPlayer(p) == -1)
			return false;
		else
			return true;
	}

	public int indexOfPlayer(Player p) {
		int index = 0;

		for (Player player : this.getPlayers()) {
			if (player.equals(p)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	@Override
	public Iterator<Player> iterator() {
		return new PlayerItr();
	}
	
	private class PlayerItr implements Iterator<Player> {
		private int curIndex;

		public PlayerItr() {
			this.curIndex = 0;
		}
		
		@Override
		public boolean hasNext() {
			return this.curIndex < getPlayers().size();
		}

		@Override
		public Player next() {
			Player p = getPlayerAtIndex(this.curIndex);
			this.curIndex++;
			return p;
		}
	}
}