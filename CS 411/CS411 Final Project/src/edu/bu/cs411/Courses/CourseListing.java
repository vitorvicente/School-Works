package edu.bu.cs411.Courses;

import edu.bu.cs411.Config.CoursesConfig;
import edu.bu.cs411.Config.GeneralConfig;

import java.util.ArrayList;

/**
 * Main Entry Class for the Courses of the Program.
 * Maintains all the Course details for the Registration Software.
 *
 * @author ejeon@bu.edu.
 * @version 1.0.0.
 */
public class CourseListing {

    /**
     * List of all Current Courses.
     */
    private ArrayList<Course> courses;

    /**
     * Base Constructor for the CourseListing Object.
     *
     * @param courses List of all Current Courses.
     */
    public CourseListing(ArrayList<Course> courses) {
        this.setCourses(courses);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Instantiation Type.
     */
    public CourseListing() throws InstantiationException {
        throw new InstantiationException(GeneralConfig.ILLEGAL_EMPTY_CONSTRUCTOR_ERROR);
    }

    /**
     * Add a New Course to the Current List.
     *
     * @param course Course to Add.
     * @return Whether the Addition was Successful.
     */
    public boolean addCourse(Course course) {
        if (!this.getCourses().contains(course))
            return this.getCourses().add(course);

        return false;
    }

    /**
     * Remove a Course from the Current List.
     *
     * @param id CourseID to Remove.
     * @return Whether the Removal was Successful.
     */
    public boolean removeCourse(CourseID id) {
        for (Course course : this.getCourses()) {
            if (course.getId().equals(id))
                return this.getCourses().remove(course);
        }

        return false;
    }

    /**
     * Remove a Course in the Current List.
     * If the Course is not available, add it.
     *
     * @param course Course to Update.
     * @return Whether the Update was Successful.
     */
    public boolean updateCourse(Course course) {
        for (Course c : this.getCourses()) {
            if (c.getId().equals(course.getId())) {
                boolean result = this.getCourses().remove(course);
                result &= this.getCourses().add(course);
                return result;
            }
        }

        return this.getCourses().add(course);
    }

    /**
     * Helper Method to attempt to Fetch a Course.
     *
     * @param id CourseID of the Course to Fetch.
     * @return Fetched Course.
     */
    public Course fetchCourse(CourseID id) {
        for (Course course : this.getCourses()) {
            if (course.getId().equals(id))
                return course;
        }

        return null;
    }

    /**
     * Helper Method to check if _every_ aspect of a Course is Valid.
     *
     * @param course Course to Check.
     * @return Whether the Course is Valid.
     */
    public boolean checkValid(Course course) {
        if (course == null)
            return false;

        System.out.println(course.getId() == null || course.getId().getCollege() == null || course.getId().getDepartment() == null
                || course.getId().getNumber() > CoursesConfig.MAX_COURSE_NUMBER
                || course.getId().getNumber() < CoursesConfig.MIN_COURSE_NUMBER);
        System.out.println(course.getStudents() == null);
        System.out.println(course.getProfessor() == null);
        System.out.println(course.getStatus() == null);
        System.out.println(course.getMaxCapacity() < CoursesConfig.MIN_COURSE_CAPACITY
                || course.getMaxCapacity() > CoursesConfig.MAX_COURSE_CAPACITY);
        System.out.println(course.getSchedule() == null || course.getSchedule().getDays() == null
                || course.getSchedule().getDays().isEmpty() || course.getSchedule().getStartTime() == null
                || course.getSchedule().getEndTime() == null);
        System.out.println(course.getLocation() != null && course.getLocation().getBuildingCode() != null
                && course.getLocation().getRoom() <= CoursesConfig.MAX_ROOM_NUMBER
                && course.getLocation().getRoom() >= CoursesConfig.MIN_ROOM_NUMBER);

        if (course.getId() == null || course.getId().getCollege() == null || course.getId().getDepartment() == null
                || course.getId().getNumber() > CoursesConfig.MAX_COURSE_NUMBER
                || course.getId().getNumber() < CoursesConfig.MIN_COURSE_NUMBER)
            return false;
        if (course.getStudents() == null)
            return false;
        if (course.getProfessor() == null)
            return false;
        if (course.getStatus() == null)
            return false;
        if (course.getMaxCapacity() < CoursesConfig.MIN_COURSE_CAPACITY
                || course.getMaxCapacity() > CoursesConfig.MAX_COURSE_CAPACITY)
            return false;
        if (course.getSchedule() == null || course.getSchedule().getDays() == null
                || course.getSchedule().getDays().isEmpty() || course.getSchedule().getStartTime() == null
                || course.getSchedule().getEndTime() == null)
            return false;
        return course.getLocation() != null && course.getLocation().getBuildingCode() != null
                && course.getLocation().getRoom() <= CoursesConfig.MAX_ROOM_NUMBER
                && course.getLocation().getRoom() >= CoursesConfig.MIN_ROOM_NUMBER;
    }

    /**
     * Helper Method to check if the Location of a Course is Available.
     *
     * @param course Course to Check.
     * @return Whether the Location is Available.
     */
    public boolean checkLocation(Course course) {
        for (Course c : this.getCourses()) {
            if (c.getLocation().equals(course.getLocation()) && c.getSchedule().equals(course.getSchedule()))
                return false;
        }

        return true;
    }

    /**
     * Helper Method to check if the ID of a Course is Unique.
     *
     * @param course Course to check.
     * @return Whether the ID is Unique.
     */
    public boolean checkUnique(Course course) {
        for (Course c : this.getCourses()) {
            if (c.getId().equals(course.getId()))
                return false;
        }

        return true;
    }

    /**
     * Get the List of all Current Courses.
     *
     * @return List of all Current Courses.
     */
    public ArrayList<Course> getCourses() {
        return this.courses;
    }

    /**
     * Set the List of all Current Courses.
     *
     * @param courses List of all Current Courses.
     */
    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

}
