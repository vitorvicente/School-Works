/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Entities.Classes;

public class LegendsEntityClass {

	private String className;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LegendsEntityClass(String className) {
		this.setClassName(className);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public String getClassName() {
		return this.className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	public String toString() {
		return this.getClassName();
	}
}
