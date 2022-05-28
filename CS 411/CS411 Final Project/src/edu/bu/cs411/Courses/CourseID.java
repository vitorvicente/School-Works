package edu.bu.cs411.Courses;

import edu.bu.cs411.Config.CoursesConfig;
import edu.bu.cs411.Config.GeneralConfig;
import edu.bu.cs411.Util.College;
import edu.bu.cs411.Util.Department;

/**
 * Course CourseID Object Class.
 *
 * @author ejeon@bu.edu.
 * @version 1.0.0.
 */
public class CourseID {

    /**
     * College of this Course.
     */
    private College college;
    /**
     * Department of this Course.
     */
    private Department department;
    /**
     * Number of this Course.
     */
    private int number;

    /**
     * Base Constructor for the CourseID Object.
     *
     * @param college    College of this Course.
     * @param department Department of this Course.
     * @param number     Number of this Course.
     */
    public CourseID(College college, Department department, int number) {
        this.setCollege(college);
        this.setDepartment(department);
        this.setNumber(number);
    }

    /**
     * String Constructor for the CourseID Object.
     *
     * @param courseID String Value for the CourseID.
     * @throws InstantiationException Illegal Initiation Parameters.
     */
    public CourseID(String courseID) throws InstantiationException {
        String[] splitID = courseID.split(CoursesConfig.COURSE_ID_SPLITTER);
        if (splitID.length != 3)
            throw new InstantiationException(GeneralConfig.ILLEGAL_ARGUMENTS_ERROR);

        this.setCollege(College.getCollege(splitID[0]));
        this.setDepartment(Department.getDepartment(splitID[1]));
        this.setNumber(Integer.parseInt(splitID[2]));
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Instantiation Type.
     */
    public CourseID() throws InstantiationException {
        throw new InstantiationException(GeneralConfig.ILLEGAL_EMPTY_CONSTRUCTOR_ERROR);
    }

    /**
     * Get the College of this Course.
     *
     * @return College of this Course.
     */
    public College getCollege() {
        return college;
    }

    /**
     * Set the College of this Course.
     *
     * @param college College of this Course.
     */
    public void setCollege(College college) {
        this.college = college;
    }

    /**
     * Get the Department of this Course.
     *
     * @return Department of this Course.
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Set the Department of this Course.
     *
     * @param department Department of this Course.
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * Get the Number of this Course.
     *
     * @return Number of this Course.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Set the Number of this Course.
     *
     * @param number Number of this Course.
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Helper Method to check whether this CourseID is equal to another CourseID.
     *
     * @param o Object to compare to.
     * @return Whether this CourseID is equal to another CourseID.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof CourseID castedObject)
            return this.getCollege().equals(castedObject.getCollege())
                    && this.getDepartment().equals(castedObject.getDepartment())
                    && this.getNumber() == castedObject.getNumber();

        return false;
    }

    /**
     * Retrieve the String Value for the CourseID.
     *
     * @return String Value for the CourseID.
     */
    @Override
    public String toString() {
        return this.getCollege().toString() + CoursesConfig.COURSE_ID_SPLITTER + this.getDepartment().toString()
                + CoursesConfig.COURSE_ID_SPLITTER + this.getNumber();
    }

}
