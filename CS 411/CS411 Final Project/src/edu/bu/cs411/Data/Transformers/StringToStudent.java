package edu.bu.cs411.Data.Transformers;

import edu.bu.cs411.Config.DataConfig;
import edu.bu.cs411.Config.UsersConfig;
import edu.bu.cs411.Courses.CourseID;
import edu.bu.cs411.Users.Student;
import edu.bu.cs411.Users.Util.Credentials;
import edu.bu.cs411.Users.Util.UniqueID;
import edu.bu.cs411.Users.Util.UserDetails;

import java.util.ArrayList;

/**
 * Parser for Saving/Loading Students.
 * <p>
 * Admin CSV Format: "id,firstName,lastName,email,password,coursesRegisteredFor,numCredits".
 * Replacing the ',' with DataConfig.CSV_DELIMITER_ONE.
 *
 * @author evanlhsu@bu.edu.
 * @version 1.0.0.
 */
public class StringToStudent extends Reversible<String, Student> {

    /**
     * Transform a Student Object into a String.
     *
     * @param student Student Object to Transform.
     * @return String.
     */
    @Override
    public String reverse(Student student) {
        UserDetails userDetails = student.getDetails();

        String id = userDetails.getId().toString();
        String firstName = userDetails.getFirstName();
        String lastName = userDetails.getLastName();

        Credentials credentials = student.getCredentials();
        String email = credentials.getEmail();
        String password = String.valueOf(credentials.getPassword());

        StringBuilder coursesRegisteredStrBuilder = new StringBuilder();
        for (CourseID courseID : student.getCoursesRegisteredFor())
            coursesRegisteredStrBuilder.append(courseID.toString()).append(DataConfig.CSV_DELIMITER_TWO);
        if (coursesRegisteredStrBuilder.length() > 0)
            coursesRegisteredStrBuilder.deleteCharAt(coursesRegisteredStrBuilder.length() - 1);
        String coursesRegisteredForStr = coursesRegisteredStrBuilder.toString();
        String numCredits = String.valueOf(student.getNumCredits());

        return String.join(DataConfig.CSV_DELIMITER_ONE, id, firstName, lastName,
                email, password, coursesRegisteredForStr, numCredits);
    }

    /**
     * Transform a String into a Student Object.
     *
     * @param s String to Transform.
     * @return Student Object.
     */
    @Override
    public Student apply(String s) {
        String[] studentArr = s.split(DataConfig.CSV_DELIMITER_ONE);

        if (studentArr.length != 7)
            return null;

        UniqueID uniqueID;
        try {
            uniqueID = new UniqueID(studentArr[0]);
        } catch (InstantiationException e) {
            e.printStackTrace();
            uniqueID = UsersConfig.DEFAULT_ID;
        }
        Credentials credentials = new Credentials(uniqueID, studentArr[3], studentArr[4].toCharArray());
        UserDetails userDetails = new UserDetails(uniqueID, studentArr[1], studentArr[2]);
        ArrayList<CourseID> coursesRegisteredFor = new ArrayList<>();
        for (String courseID : studentArr[5].split(DataConfig.CSV_DELIMITER_TWO)) {
            try {
                coursesRegisteredFor.add(new CourseID(courseID));
            } catch (InstantiationException ignored) {
            }
        }
        int numCredits = Integer.parseInt(studentArr[6]);

        return new Student(credentials, userDetails, coursesRegisteredFor, numCredits);
    }

}
