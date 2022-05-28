/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Classes;

public abstract class LegendsEntityClass {

	private String className;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsEntityClass(String className) {
		this.setClassName(className);
	}

	public void setClassName(String className) {
		this.className = className;
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public String getClassName() {
		return this.className;
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	public String toString() {
		return this.getClassName();
	}
}
