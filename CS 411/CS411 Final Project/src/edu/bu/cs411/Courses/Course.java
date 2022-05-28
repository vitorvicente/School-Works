package edu.bu.cs411.Courses;

import edu.bu.cs411.Courses.Util.CourseStatus;
import edu.bu.cs411.Courses.Util.Location;
import edu.bu.cs411.Courses.Util.Schedule;
import edu.bu.cs411.Users.Util.UniqueID;

import java.util.ArrayList;

/**
 * Base Class for Course Objects.
 *
 * @author ejeon@bu.edu.
 * @version 1.0.0.
 */
public class Course {

    /**
     * UniqueID for this Course.
     */
    private CourseID id;
    /**
     * Current RegistrationStatus for this Course.
     */
    private CourseStatus status;

    /**
     * Current Professor for this Course.
     */
    private UniqueID professor;
    /**
     * Current List of Students for this Course.
     */
    private ArrayList<UniqueID> students;
    /**
     * Absolute Maximum Capacity for this Course.
     */
    private int maxCapacity;

    /**
     * Current Schedule for this Course.
     */
    private Schedule schedule;
    /**
     * Current Location for this Course.
     */
    private Location location;

    /**
     * Base Constructor for the Course Object.
     *
     * @param id          UniqueID for this Course.
     * @param status      Current RegistrationStatus for this Course.
     * @param professor   Current Professor for this Course.
     * @param students    Current List of Students for this Course.
     * @param maxCapacity Absolute Maximum Capacity for this Course.
     * @param schedule    Current Schedule for this Course.
     * @param location    Current Location for this Course.
     */
    public Course(CourseID id, CourseStatus status, UniqueID professor, ArrayList<UniqueID> students, int maxCapacity,
                  Schedule schedule, Location location) {
        this.setId(id);
        this.setStatus(status);
        this.setProfessor(professor);
        this.setStudents(students);
        this.setMaxCapacity(maxCapacity);
        this.setSchedule(schedule);
        this.setLocation(location);
    }

    /**
     * No Args Constructor.
     */
    public Course() {
    }

    /**
     * Get the UniqueID for this Course.
     *
     * @return UniqueID for this Course.
     */
    public CourseID getId() {
        return this.id;
    }

    /**
     * Set the UniqueID for this Course.
     *
     * @param id UniqueID for this Course.
     */
    public void setId(CourseID id) {
        this.id = id;
    }

    /**
     * Get the Current RegistrationStatus for this Course.
     *
     * @return Current RegistrationStatus for this Course.
     */
    public CourseStatus getStatus() {
        return this.status;
    }

    /**
     * Set the Current RegistrationStatus for this Course.
     *
     * @param status Current RegistrationStatus for this Course.
     */
    public void setStatus(CourseStatus status) {
        this.status = status;
    }

    /**
     * Get the Current Professor for this Course.
     *
     * @return Current Professor for this Course.
     */
    public UniqueID getProfessor() {
        return this.professor;
    }

    /**
     * Set the Current Professor for this Course.
     *
     * @param professor Current Professor for this Course.
     */
    public void setProfessor(UniqueID professor) {
        this.professor = professor;
    }

    /**
     * Get the Current List of Students for this Course.
     *
     * @return Current List of Students for this Course.
     */
    public ArrayList<UniqueID> getStudents() {
        return this.students;
    }

    /**
     * Set the Current List of Students for this Course.
     *
     * @param students Current List of Students for this Course.
     */
    public void setStudents(ArrayList<UniqueID> students) {
        this.students = students;
    }

    /**
     * Add a Student to the Current Student List.
     *
     * @param id Student to Add.
     * @return Whether the Addition was Successful.
     */
    public boolean addStudent(UniqueID id) {
        if (!students.contains(id)) {
            return students.add(id);
        }

        return false;
    }

    /**
     * Remove a Student to the Current Student List.
     *
     * @param id Student to Remove.
     * @return Whether the Removal was Successful.
     */
    public boolean removeStudent(UniqueID id) {
        if (students.contains(id))
            return students.remove(id);

        return false;
    }

    /**
     * Get the Absolute Maximum Capacity for this Course.
     *
     * @return Absolute Maximum Capacity for this Course.
     */
    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    /**
     * Set the Absolute Maximum Capacity for this Course.
     *
     * @param maxCapacity Absolute Maximum Capacity for this Course.
     */
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    /**
     * Get the Current Schedule for this Course.
     *
     * @return Current Schedule for this Course.
     */
    public Schedule getSchedule() {
        return this.schedule;
    }

    /**
     * Set the Current Schedule for this Course.
     *
     * @param schedule Current Schedule for this Course.
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    /**
     * Get the Current Location for this Course.
     *
     * @return Current Location for this Course.
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * Set the Current Location for this Course.
     *
     * @param location Current Location for this Course.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Helper Method to check whether this Course is equal to another Course.
     *
     * @param o Object to compare to.
     * @return Whether this Course is equal to another Course.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Course castedObject)
            return this.getId().equals(castedObject.getId());

        return false;
    }

    /**
     * Retrieve the String Value for the Course.
     *
     * @return String Value for the Course.
     */
    @Override
    public String toString() {
        return this.getId().toString();
    }

}
