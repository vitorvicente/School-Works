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

import Entities.Util.Player.LegendsPlayerHeroTeam;
import Util.Abstraction.Player;

public class LegendsPlayer extends Player {
	private LegendsPlayerHeroTeam team;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsPlayer(int ID) {
		super(ID);
		team = new LegendsPlayerHeroTeam();
	}

	public LegendsPlayer(int ID, String name) {
		super(ID, name);
		team = new LegendsPlayerHeroTeam();
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public void addHero(LegendsHero hero) {
		this.team.addHero(hero);
	}

	public LegendsPlayerHeroTeam getTeam() {
		return team;
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	public void printTeam() {
		System.out.format("+------------------------+%n");
		System.out.format("|        Your Team       |%n");
		System.out.format("+------------------------+%n");
		int i = 0;
		for (LegendsHero h : team) {
			System.out.format("%3d: " + h + "%n", i);
			i++;
		}
	}

	public void printTeamNames() {
		System.out.format("+------------------------+%n");
		System.out.format("|        Your Team       |%n");
		System.out.format("+------------------------+%n");
		int i = 0;
		for (LegendsHero h : team) {
			System.out.format("%3d: " + h.getName() + "%n", i);
			i++;
		}
	}
}
