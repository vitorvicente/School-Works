package edu.bu.cs411.Users;

import edu.bu.cs411.Courses.CourseID;
import edu.bu.cs411.Users.Util.Credentials;
import edu.bu.cs411.Users.Util.UserDetails;
import edu.bu.cs411.Util.College;
import edu.bu.cs411.Util.Department;

import java.util.ArrayList;

/**
 * Base Class for Professor User Objects.
 *
 * @author jycchoi@bu.edu.
 * @version 1.0.0.
 */
public class Professor extends User {

    /**
     * List of all Courses the Professor is Teaching.
     */
    private ArrayList<CourseID> coursesTeaching;
    /**
     * College the Professor Works For.
     */
    private College college;
    /**
     * Department the Professor Works For.
     */
    private Department department;

    /**
     * Base Constructor for the Professor Object.
     *
     * @param credentials     Credentials for this User.
     * @param details         General Details for this User.
     * @param coursesTeaching List of all Courses the Professor is Teaching.
     * @param college         College the Professor Works For.
     * @param department      Department the Professor Works For.
     */
    public Professor(Credentials credentials, UserDetails details, ArrayList<CourseID> coursesTeaching,
                     College college, Department department) {
        super(credentials, details);
        this.setCoursesTeaching(coursesTeaching);
        this.setCollege(college);
        this.setDepartment(department);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Instantiation Type.
     */
    public Professor() throws InstantiationException {
        super();
    }

    /**
     * Register Professor for Course.
     *
     * @param id Course to Register for.
     * @return Whether the Registration was Successful.
     */
    public boolean addCourseTeaching(CourseID id) {
        if (!this.isTeaching(id))
            return this.getCoursesTeaching().add(id);

        return false;
    }

    /**
     * Drop Course for Professor.
     *
     * @param id Course to Drop.
     * @return Whether the Drop was Successful.
     */
    public boolean removeCourseTeaching(CourseID id) {
        if (this.isTeaching(id))
            return this.getCoursesTeaching().remove(id);

        return false;
    }

    /**
     * Check whether the Student is Teaching a Course.
     *
     * @param id Course to Check.
     * @return Whether the Student is Teaching a Course.
     */
    public boolean isTeaching(CourseID id) {
        return this.getCoursesTeaching().contains(id);
    }

    /**
     * Get the List of all Courses the Professor is Teaching.
     *
     * @return List of all Courses the Professor is Teaching.
     */
    public ArrayList<CourseID> getCoursesTeaching() {
        return this.coursesTeaching;
    }

    /**
     * Set the List of all Courses the Professor is Teaching.
     *
     * @param coursesTeaching List of all Courses the Professor is Teaching.
     */
    public void setCoursesTeaching(ArrayList<CourseID> coursesTeaching) {
        this.coursesTeaching = coursesTeaching;
    }

    /**
     * Get the College the Professor Works For.
     *
     * @return College the Professor Works For.
     */
    public College getCollege() {
        return this.college;
    }

    /**
     * Set the College the Professor Works For.
     *
     * @param college College the Professor Works For.
     */
    public void setCollege(College college) {
        this.college = college;
    }

    /**
     * Get the Department the Professor Works For.
     *
     * @return Department the Professor Works For.
     */
    public Department getDepartment() {
        return this.department;
    }

    /**
     * Set the Department the Professor Works For.
     *
     * @param department Department the Professor Works For.
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

}
