/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Entities;

import Entities.Classes.LegendsEntityClass;
import Entities.Util.LegendsEntityStats;
import Game.LegendsOfValor;
import Map.Places.Place;
import Util.Token;

public abstract class LegendsEntity {
	
	private String name;
	private int ID;
	
	private Place currPlace;
	private Place spawnPlace;
	
	private Token token;
	
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
	
	public abstract LegendsEntityStats getEntityStats();
	public abstract void setLegendsEntityStats(LegendsEntityStats eStats);
	
	public abstract void respawn();
	
	public abstract void updatePosition(int x, int y, LegendsOfValor game);
	
	public abstract void resetPosition();
	
	public abstract void levelUp();
	
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
	
	public Place getCurrPlace() {
		return currPlace;
	}
	
	public void setCurrPlace(Place currPlace) {
		this.currPlace = currPlace;
	}
	
	public Place getSpawnPlace() {
		return spawnPlace;
	}
	
	public void setSpawnPlace(Place spawnPlace) {
		this.spawnPlace = spawnPlace;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
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
