/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Game.Util;

import Classes.LegendsEntityClass;

public abstract class LegendsEntity {
	
	private String name;
	private int ID;
	
	/* =================== */
	/* Constructor Methods */
	/* =================== */
	
	public LegendsEntity(int ID) {
		this.setID(ID);
		this.setName("Legends Entity #" + ID);
	}
	
	public LegendsEntity(int ID, String name) {
		this.setID(ID);
		this.setName(name);
	}
	
	/* ================ */
	/* Abstract Methods */
	/* ================ */
	
	public abstract LegendsEntityClass getEntityClass();
	public abstract void setEntityClass(LegendsEntityClass eClass);
	
	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */
	
	private void setID(int ID) {
		this.ID = ID;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public String getName() {
		return this.name;
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	public boolean equals(LegendsEntity e) {
		return this.getID() == e.getID();
	}

	public String toString() {
		return this.getName();
	}
}
