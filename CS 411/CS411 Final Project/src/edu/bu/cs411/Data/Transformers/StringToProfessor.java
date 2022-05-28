package edu.bu.cs411.Data.Transformers;

import edu.bu.cs411.Config.DataConfig;
import edu.bu.cs411.Config.UsersConfig;
import edu.bu.cs411.Courses.CourseID;
import edu.bu.cs411.Users.Professor;
import edu.bu.cs411.Users.Util.Credentials;
import edu.bu.cs411.Users.Util.UniqueID;
import edu.bu.cs411.Users.Util.UserDetails;
import edu.bu.cs411.Util.College;
import edu.bu.cs411.Util.Department;

import java.util.ArrayList;

/**
 * Parser for Saving/Loading Professors.
 * <p>
 * Admin CSV Format: "id,firstName,lastName,email,password,coursesTeaching,college,department".
 * Replacing the ',' with DataConfig.CSV_DELIMITER_ONE.
 *
 * @author evanlhsu@bu.edu.
 * @version 1.0.0.
 */
public class StringToProfessor extends Reversible<String, Professor> {

    /**
     * Transform a Professor Object into a String.
     *
     * @param professor Professor Object to Transform.
     * @return String.
     */
    @Override
    public String reverse(Professor professor) {
        UserDetails userDetails = professor.getDetails();

        String id = userDetails.getId().toString();
        String firstName = userDetails.getFirstName();
        String lastName = userDetails.getLastName();

        Credentials credentials = professor.getCredentials();
        String email = credentials.getEmail();
        String password = String.valueOf(credentials.getPassword());

        ArrayList<CourseID> coursesTeaching = professor.getCoursesTeaching();
        StringBuilder coursesTeachingStrBuilder = new StringBuilder();
        for (CourseID courseID : coursesTeaching)
            coursesTeachingStrBuilder.append(courseID.toString()).append(DataConfig.CSV_DELIMITER_TWO);
        if (coursesTeachingStrBuilder.length() > 0)
            coursesTeachingStrBuilder.deleteCharAt(coursesTeachingStrBuilder.length() - 1);
        String coursesTeachingStr = coursesTeachingStrBuilder.toString();
        String college = professor.getCollege().toString();
        String department = professor.getDepartment().toString();

        return String.join(DataConfig.CSV_DELIMITER_ONE, id, firstName, lastName, email, password,
                coursesTeachingStr, college, department);
    }

    /**
     * Transform a String into a Professor Object.
     *
     * @param s String to Transform.
     * @return Professor Object.
     */
    @Override
    public Professor apply(String s) {
        String[] professorArr = s.split(DataConfig.CSV_DELIMITER_ONE);

        if (professorArr.length != 8)
            return null;

        UniqueID uniqueID;
        try {
            uniqueID = new UniqueID(professorArr[0]);
        } catch (InstantiationException e) {
            e.printStackTrace();
            uniqueID = UsersConfig.DEFAULT_ID;
        }
        Credentials credentials = new Credentials(uniqueID, professorArr[3], professorArr[4].toCharArray());
        UserDetails userDetails = new UserDetails(uniqueID, professorArr[1], professorArr[2]);
        ArrayList<CourseID> coursesTeaching = new ArrayList<>();
        for (String courseID : professorArr[5].split(DataConfig.CSV_DELIMITER_TWO)) {
            try {
                coursesTeaching.add(new CourseID(courseID));
            } catch (InstantiationException ignored) {
            }
        }
        College college = College.valueOf(professorArr[6]);
        Department department = Department.valueOf(professorArr[7]);

        return new Professor(credentials, userDetails, coursesTeaching, college, department);
    }

}
