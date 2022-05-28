package edu.bu.cs411.Registrar;

import edu.bu.cs411.Config.CoursesConfig;
import edu.bu.cs411.Config.GeneralConfig;
import edu.bu.cs411.Config.RegistrarConfig;
import edu.bu.cs411.Config.UsersConfig;
import edu.bu.cs411.Courses.Course;
import edu.bu.cs411.Courses.CourseListing;
import edu.bu.cs411.Courses.Util.CourseStatus;
import edu.bu.cs411.Users.Professor;
import edu.bu.cs411.Users.Student;
import edu.bu.cs411.Users.UserListing;

/**
 * Main Entry Class for the RegistrationDetails of the Program.
 * Maintains all the global details for the Registration Software.
 *
 * @author dsullo@bu.edu.
 * @version 1.0.0.
 */
public class RegistrationDetails {

    /**
     * Overall Maximum Number of Credits a Student can register for.
     */
    public static final int MAX_CREDITS = RegistrarConfig.MAX_CREDITS;

    /**
     * Whether the overall Registration for this Semester is open.
     */
    private boolean overAllOpen;
    /**
     * Current Active Semester.
     */
    private Semester currentSemester;

    /**
     * Base Constructor for the RegistrationDetails Object.
     *
     * @param overAllOpen     Whether the overall Registration for this Semester is open.
     * @param currentSemester Current Active Semester.
     */
    public RegistrationDetails(boolean overAllOpen, Semester currentSemester) {
        this.setOverAllOpen(overAllOpen);
        this.setCurrentSemester(currentSemester);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Instantiation Type.
     */
    public RegistrationDetails() throws InstantiationException {
        throw new InstantiationException(GeneralConfig.ILLEGAL_EMPTY_CONSTRUCTOR_ERROR);
    }

    /**
     * Perform the move to the next Semester.
     * Also clears all Semester specific Data.
     *
     * @param userListing   Reference to the overall UserListing Object.
     * @param courseListing Reference to the overall CourseListing Object.
     */
    public void moveToNextSemester(UserListing userListing, CourseListing courseListing) {
        this.setCurrentSemester(this.getCurrentSemester().next());

        for (Student student : userListing.getStudents()) {
            student.getCoursesRegisteredFor().clear();
            student.setNumCredits(RegistrarConfig.START_CREDITS);
        }
        for (Professor professor : userListing.getProfessors()) {
            professor.getCoursesTeaching().clear();
        }
        for (Course course : courseListing.getCourses()) {
            course.getStudents().clear();
            course.setProfessor(UsersConfig.DEFAULT_ID);
            course.setLocation(CoursesConfig.DEFAULT_LOCATION);
            course.setSchedule(CoursesConfig.DEFAULT_SCHEDULE);
            course.setStatus(CourseStatus.CLOSED);
        }
    }

    /**
     * Check whether the overall Registration for this Semester is open.
     *
     * @return Whether the overall Registration for this Semester is open.
     */
    public boolean isOverAllOpen() {
        return this.overAllOpen;
    }

    /**
     * Set whether the overall Registration for this Semester is open.
     *
     * @param overAllOpen Whether the overall Registration for this Semester is open.
     */
    public void setOverAllOpen(boolean overAllOpen) {
        this.overAllOpen = overAllOpen;
    }

    /**
     * Retrieve the current Active Semester.
     *
     * @return Current Active Semester.
     */
    public Semester getCurrentSemester() {
        return this.currentSemester;
    }

    /**
     * Set the current Active Semester.
     *
     * @param currentSemester Current Active Semester.
     */
    public void setCurrentSemester(Semester currentSemester) {
        this.currentSemester = currentSemester;
    }

}
