import java.util.ArrayList;
import java.util.Scanner;

public abstract class Game {
	private final int ENTITIES_MAXMAX = 100;
	private final int ENTITIES_MINMIN = 2;
	private final int TEAM_MAXMAX = 100;
	private final int TEAM_MINMIN = 1;

	private String gameName;

	private String instructions;
	private String welcomeMSG;
	private String goodbyeMSG;

	private Board b;
	private int boardSize;

	private ArrayList<Entity> entities;
	private int maxEntities;
	private int maxPlayersPerTeam;

	private boolean teamsAllowed;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Game(String gameName) {
		this.setName(gameName);
		this.setTeamsAllowed(false);
		Board newBoard = new Board();
		this.boardSize = newBoard.getSize();
		this.setBoard(newBoard);
		this.setEntities(new ArrayList<Entity>());
	}
	
	public Game(String gameName, Board b) {
		this.setName(gameName);
		this.setTeamsAllowed(false);
		this.boardSize = b.getSize();
		this.setBoard(b);
		this.setEntities(new ArrayList<Entity>());
	}
	
	public Game(String gameName, boolean teamsAllowed) {
		this.setName(gameName);
		this.setTeamsAllowed(teamsAllowed);
		Board newBoard = new Board();
		this.boardSize = newBoard.getSize();
		this.setBoard(newBoard);
		this.setEntities(new ArrayList<Entity>());
	}
	
	public Game(String gameName, Board b, boolean teamsAllowed) {
		this.setName(gameName);
		this.setTeamsAllowed(teamsAllowed);
		this.boardSize = b.getSize();
		this.setBoard(b);
		this.setEntities(new ArrayList<Entity>());
	}

	/* ============ */
	/* Game Methods */
	/* ============ */

	protected abstract void entitiesSetup();

	protected abstract void play();

	protected void won(Entity t) {
		Entity winningEntity = null;

		for (Entity e : this.getEntities()) {
			if (e.equals(t)) {
				e.addWin();
				winningEntity = e;
			} else {
				e.addLoss();
			}
		}

		winningEntity.announceWin();

		this.printer("scores");
	}

	protected void staleMate() {
		System.out.println();
		System.out.println("A Stale Mate has been reached in the game.");
		System.out.println();

		for (Entity e : this.getEntities()) {
			e.addDraw();
		}

		this.printer("scores");
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public ArrayList<Entity> getEntities() {
		return this.entities;
	}

	/*
	 * Below are all the different ways of adding Entities, whether via an ArrayList
	 * (which will encompases ArrayLists of type Entity, Team, and Player; or via
	 * regular Arrays, in which case there is a method for each of the types.
	 */
	public void setEntities(ArrayList<Entity> entities) {
		if (entities.size() > this.getMaxEntities()) {
			System.out
					.println("Given list of Entities is too large, maximum allowed size is: " + this.getMaxEntities());
			return;
		}

		this.entities = entities;
	}

	public void setEntities(Entity[] entities) {
		if (entities.length > this.getMaxEntities()) {
			System.out
					.println("Given list of Entities is too large, maximum allowed size is: " + this.getMaxEntities());
			return;
		}

		for (Entity e : entities) {
			this.addEntity(e);
		}
	}

	public void setEntities(Team[] entities) {
		if (entities.length > this.getMaxEntities()) {
			System.out
					.println("Given list of Entities is too large, maximum allowed size is: " + this.getMaxEntities());
			return;
		}
		if (!this.getTeamsAllowed()) {
			System.out.println("Teams are now allowed for this game! Rejecting Entity List.");
			return;
		}

		for (Entity e : entities) {
			this.addEntity(e);
		}
	}

	public void setEntities(Player[] entities) {
		if (entities.length > this.getMaxEntities()) {
			System.out
					.println("Given list of Entities is too large, maximum allowed size is: " + this.getMaxEntities());
			return;
		}

		for (Entity e : entities) {
			this.addEntity(e);
		}
	}

	/*
	 * This method is crucial in the addition of entities, as it checks if they are
	 * either of the type Team, or Player. This might seem trivial, but there could
	 * be other potential entities that messed stuff up, so that's barrier #1.
	 * Barrier #2 comes when the entity is a Team, there the method will check if
	 * teams are allowed, and if so if this team is of an allowed size.
	 * 
	 * It'll also check whether the maximum allowed number of entities has been
	 * reached, in which case no further entities can be added.
	 */
	public void addEntity(Entity e) {
		
		if (this.getEntities().size() == this.getMaxEntities()) {
			System.out.println("Maximum Entities already in-game, no other Entities allowed to be added!");
			return;
		}

		if (this.isEntityInGame(e)) {
			System.out.println("This Entity is already in-game, thus it cannot be added twice!");
			return;
		}

		if (e instanceof Team) {
			if (!this.getTeamsAllowed()) {
				System.out.println("Teams are now allowed for this game! Rejecting Entity " + e.getID());
				return;
			}

			if (((Team) e).getPlayers().size() > this.getMaxPlayersPerTeam()) {
				System.out.println(
						"Team size is too large (" + ((Team) e).getPlayers().size() + "), maximum value allowed is "
								+ this.getMaxPlayersPerTeam() + ". Rejecting Entity " + e.getID());
				return;
			}

			this.entities.add(e);
		} else if (e instanceof Player) {
			this.entities.add(e);
		} else {
			if (this.getTeamsAllowed())
				System.out.println("Invalid Entity Type entered, only entities allowed are: 'Team' & 'Player'");
			else
				System.out.println("Invalid Entity Type entered, only entities allowed are: 'Player'");
		}
	}

	// Checks if it can remove without breaking the minimum required, and checks if
	// the entity is in the game before removing
	public void removeEntity(Entity e) {
		if (this.getEntities().size() == this.getAbsoluteMinEntities()) {
			System.out.println("Minimum Number of Entities reached, cannot remove any further Entities!");
			return;
		}

		if (!this.isEntityInGame(e)) {
			System.out.println("Entity not in game!");
			return;
		}

		int index = this.indexOfEntity(e);

		this.getEntities().remove(index);
	}

	// Returns a specific entity via ID, useful to alter player/team properties
	public Entity getEntity(int entityID) {
		Entity response = null;

		for (Entity e : this.getEntities()) {
			if (e.getID() == entityID) {
				response = e;
				break;
			}
		}

		return response;
	}

	public int getMaxPlayersPerTeam() {
		return this.maxPlayersPerTeam;
	}

	// Checks if the value is within the allowed limits
	public void setMaxPlayersPerTeam(int maxPlayersPerTeam) {
		if (maxPlayersPerTeam > this.getAbsoluteMaxPlayersPerTeam()) {
			System.out.println("Max Players Per Team Value provided is too large, maximum value allowed is "
					+ this.getAbsoluteMaxPlayersPerTeam());
			return;
		}
		if (maxPlayersPerTeam < this.getAbsoluteMinPlayersPerTeam()) {
			System.out.println("Max Players Per Team Value provided is too small, minimum value allowed is "
					+ this.getAbsoluteMinPlayersPerTeam());
			return;
		}
		this.maxPlayersPerTeam = maxPlayersPerTeam;
	}

	// Again these methods are more of a good-to-have than anything else
	public int getAbsoluteMaxPlayersPerTeam() {
		return this.TEAM_MAXMAX;
	}

	public int getAbsoluteMinPlayersPerTeam() {
		return this.TEAM_MINMIN;
	}

	public boolean getTeamsAllowed() {
		return this.teamsAllowed;
	}

	public void setTeamsAllowed(boolean teamsAllowed) {
		this.teamsAllowed = teamsAllowed;
	}

	public int getMaxEntities() {
		return this.maxEntities;
	}

	// Checks if the value is within the allowed limits
	public void setMaxEntities(int maxEntities) {
		if (maxEntities > this.getAbsoluteMaxEntities()) {
			System.out.println("Max Entities Value provided is too large, maximum value allowed is "
					+ this.getAbsoluteMaxEntities());
			return;
		}
		if (maxEntities < this.getAbsoluteMinEntities()) {
			System.out.println("Max Entities Value provided is too small, minimum value allowed is "
					+ this.getAbsoluteMinEntities());
			return;
		}
		this.maxEntities = maxEntities;
	}

	// Again these methods are more of a good-to-have than anything else
	public int getAbsoluteMaxEntities() {
		return this.ENTITIES_MAXMAX;
	}

	public int getAbsoluteMinEntities() {
		return this.ENTITIES_MINMIN;
	}

	public String getName() {
		return this.gameName;
	}

	public void setName(String gameName) {
		this.gameName = gameName;
	}

	public int getBoardSize() {
		return this.boardSize;
	}

	public Board getBoard() {
		return this.b;
	}

	public void setBoard(Board b) {
		if (this.getBoard() != null && b.getSize() != this.getBoardSize()) {
			System.out.println("Invalid Board Size! Cannot change the board size mid-game!");
			return;
		}

		this.b = b;
	}

	public String getWelcomeMSG() {
		return this.welcomeMSG;
	}

	public void setWelcomeMSG(String welcomeMSG) {
		this.welcomeMSG = welcomeMSG;
	}

	public String getInstructions() {
		return this.instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getGoodbyeMSG() {
		return this.goodbyeMSG;
	}

	public void setGoodbyeMSG(String goodbyeMSG) {
		this.goodbyeMSG = goodbyeMSG;
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	protected boolean getYesNo(String message) throws Exception {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String input = "";
		int timeoutCounter = 0;

		while (true) {
			if (timeoutCounter > 10000)
				throw new Exception("Request timed out!");
			timeoutCounter += 1;
			
			System.out.println();
			System.out.println(message + " (Y/N)");
			input = scanner.nextLine();

			switch (input) {
			case "Y":
			case "y":
				return true;
			case "N":
			case "n":
				return false;
			default:
				System.out.println("Invalid Input! Only 'Y', 'N', 'y', 'n' are allowed!");
			}
		}
	}

	protected void printer(String printGoal) {
		System.out.println();

		switch (printGoal) {
		case "welcome":
			System.out.println(this.getWelcomeMSG());
			break;
		case "goodbye":
			System.out.println(this.getGoodbyeMSG());
			break;
		case "instructions":
			System.out.println(this.getInstructions());
			break;
		case "scores":
			System.out.println("Current Scores:");
			for (Entity e : this.getEntities())
				System.out.println(e.getScores());
			break;
		default:
			break;
		}

		System.out.println();
	}

	/*
	 * Both of these methods are to assist with adding/removing Entities, they also
	 * implement some custom implementation of methods that technically already
	 * exist, but could fail in-use for these cases, so I just coded my own.
	 */
	private boolean isEntityInGame(Entity e) {
		if (this.indexOfEntity(e) == -1)
			return false;
		else
			return true;
	}

	private int indexOfEntity(Entity e) {
		int index = 0;

		for (Entity entity : this.getEntities()) {
			if (entity.equals(e)) {
				return index;
			}
			index++;
		}
		return -1;
	}
}
