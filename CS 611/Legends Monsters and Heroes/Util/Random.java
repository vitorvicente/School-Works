/*=====================================================*/
/* Project Title: Legends: Heroes and Monster          */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Author: Victor Vicente                      */
/*=====================================================*/

package Util;

public class Random {

	/*
	 * Static method to get a random int within limits, this is used all over the
	 * place.
	 */
	public static int randomInt(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}

	/*
	 * Same as above, but for doubles
	 */
	public static double randomDouble(double min, double max) {
		return min + (Math.random() * (max - min));
	}
}
