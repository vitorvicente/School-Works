package edu.bu.cs411.Users;

import edu.bu.cs411.Config.CoursesConfig;
import edu.bu.cs411.Courses.CourseID;
import edu.bu.cs411.Users.Util.Credentials;
import edu.bu.cs411.Users.Util.UserDetails;

import java.util.ArrayList;

/**
 * Base Class for Student User Objects.
 *
 * @author jycchoi@bu.edu.
 * @version 1.0.0.
 */
public class Student extends User {

    /**
     * List of all Courses the Student is Registered For.
     */
    private ArrayList<CourseID> coursesRegisteredFor;
    /**
     * Current Number of Credits the Student is Registered For.
     */
    private int numCredits;

    /**
     * Base Constructor for the Student Object.
     *
     * @param credentials          Credentials for this User.
     * @param details              General Details for this User.
     * @param coursesRegisteredFor List of all Courses the Student is Registered For.
     * @param numCredits           Current Number of Credits the Student is Registered For.
     */
    public Student(Credentials credentials, UserDetails details,
                   ArrayList<CourseID> coursesRegisteredFor, int numCredits) {
        super(credentials, details);
        this.setCoursesRegisteredFor(coursesRegisteredFor);
        this.setNumCredits(numCredits);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Instantiation Type.
     */
    public Student() throws InstantiationException {
        super();
    }

    /**
     * Register Student for Course.
     *
     * @param id Course to Register for.
     * @return Whether the Registration was Successful.
     */
    public boolean registerForCourse(CourseID id) {
        if (!this.isRegisteredFor(id)) {
            this.setNumCredits(this.getNumCredits() + CoursesConfig.CREDITS_PER_COURSE);
            return this.getCoursesRegisteredFor().add(id);
        }

        return false;
    }

    /**
     * Drop Course for Student
     *
     * @param id Course to Drop.
     * @return Whether the Drop was Successful.
     */
    public boolean dropCourse(CourseID id) {
        if (this.isRegisteredFor(id)) {
            this.setNumCredits(this.getNumCredits() - CoursesConfig.CREDITS_PER_COURSE);
            return this.getCoursesRegisteredFor().remove(id);
        }

        return false;
    }

    /**
     * Check whether the Student is Registered for a Course.
     *
     * @param id Course to Check.
     * @return Whether the Student is Registered for a Course.
     */
    public boolean isRegisteredFor(CourseID id) {
        return this.getCoursesRegisteredFor().contains(id);
    }

    /**
     * Get the List of all Courses the Student is Registered For.
     *
     * @return List of all Courses the Student is Registered For.
     */
    public ArrayList<CourseID> getCoursesRegisteredFor() {
        return this.coursesRegisteredFor;
    }

    /**
     * Set the List of all Courses the Student is Registered For.
     *
     * @param coursesRegisteredFor List of all Courses the Student is Registered For.
     */
    public void setCoursesRegisteredFor(ArrayList<CourseID> coursesRegisteredFor) {
        this.coursesRegisteredFor = coursesRegisteredFor;
    }

    /**
     * Get the Current Number of Credits the Student is Registered For.
     *
     * @return Current Number of Credits the Student is Registered For.
     */
    public int getNumCredits() {
        return this.numCredits;
    }

    /**
     * Set the Current Number of Credits the Student is Registered For.
     *
     * @param numCredits Current Number of Credits the Student is Registered For.
     */
    public void setNumCredits(int numCredits) {
        this.numCredits = numCredits;
    }

}
