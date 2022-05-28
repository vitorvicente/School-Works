/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Game.Util;

import java.util.ArrayList;

import Game.LegendsPlayer;
import Util.Entity;
import Util.GameEntities;

public class LegendsGameEntities extends GameEntities {

	private ArrayList<Entity> entities;

	/* =================== */
	/* Constructor Methods */
	/* =================== */
	
	public LegendsGameEntities() {
		super(1, 1);
		super.setTeamsAllowed(false);
		this.resetEntities();
	}
	
	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	@Override
	public void addEntity(Entity e) {
		if (!(e instanceof LegendsPlayer)) {
			System.out.println("Only instances of LegendsPlayer Entities are allowed to be added to the Game!");
			return;
		}

		if (super.isEntityInGame(e)) {
			System.out.println("Provided Entity is already in game!");
			return;
		}

		if (this.getEntities().size() == super.getMaxEntitiesInGame()) {
			System.out.println("The current Game has reached max capacity for entites!");
			return;
		}

		this.entities.add(e);
	}

	@Override
	public void removeEntity(Entity e) {
		if (!super.isEntityInGame(e)) {
			System.out.println("Provided Entity is not in game!");
			return;
		}
		if (this.getEntities().size() == super.getMinEntitiesInGame()) {
			System.out.println("The current Game has reached minimum capacity for entites!");
			return;
		}

		this.entities.remove(e);

	}

	@Override
	public void setEntities(ArrayList<Entity> entities) {
		for (Entity e : entities) {
			this.addEntity(e);
		}
	}

	@Override
	public ArrayList<Entity> getEntities() {
		return entities;
	}

	@Override
	public void resetEntities() {
		this.entities = new ArrayList<Entity>();
	}

}
