/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Game;

public abstract class LegendsItem {

	private String name;
	private int ID;

	private int cost;
	private int minLevel;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsItem(int ID, String name, int cost, int minLevel) {
		this.setID(ID);
		this.setName(name);
		this.setCost(cost);
		this.setMinLevel(minLevel);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public int getID() {
		return ID;
	}

	private void setID(int ID) {
		this.ID = ID;
	}

	public int getCost() {
		return cost;
	}

	private void setCost(int cost) {
		this.cost = cost;
	}

	public int getMinLevel() {
		return minLevel;
	}

	private void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	public String toString() {
		return this.getName();
	}

	public boolean isEqual(LegendsItem toCompare) {
		return this.getID() == toCompare.getID();
	}

}
