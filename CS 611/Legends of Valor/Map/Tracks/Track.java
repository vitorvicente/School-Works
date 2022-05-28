/*=====================================================*/
/* Project Title: Legends Of Valor                     */
/* Course Name: GRS CS611                              */
/* Semester: Spring '21                                */
/* Project Authors:                                    */
/*    - Jack Giunta                                    */
/*    - Victoria-Rose Burke                            */
/*    - Victor Vicente                                 */
/*=====================================================*/

package Map.Tracks;

import Map.Places.Place;

public abstract class Track {
	
	private int trackID;

	/* =================== */
	/* Constructor Methods */
	/* =================== */
	
	public Track(int trackID) {
		this.trackID = trackID;
	}
	
	/* ================ */
	/* Abstract Methods */
	/* ================ */
	
	public abstract Place getPlace(int row, int col);

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */
	
	public int getTrackID() {
		return trackID;
	}

	public void setTrackID(int newID) {
		trackID = newID;
	}

}