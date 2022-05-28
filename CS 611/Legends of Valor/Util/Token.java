/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Util;

public class Token {

	String design;

	/* =================== */
	/* Constructor Methods */
	/* =================== */
	
	public Token(String design) {
		this.design = design;
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */
	
	public void setDesign(String d) {
		design = d;
	}

	public String getDesign() {
		return design;
	}

	public void printDesign() {
		System.out.print(design);
	}

}
