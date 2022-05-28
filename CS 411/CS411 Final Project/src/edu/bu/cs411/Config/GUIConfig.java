package edu.bu.cs411.Config;

/**
 * Config Class for all details for the GUI.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class GUIConfig {

    /**
     * Serial UID for Components.
     */
    public static final long SERIAL_VERSION_UID = 1L;

    /**
     * Top Border Padding.
     */
    public static final int BORDER_TOP = 5;
    /**
     * Left Border Padding.
     */
    public static final int BORDER_LEFT = 5;
    /**
     * Bottom Border Padding.
     */
    public static final int BORDER_BOTTOM = 5;
    /**
     * Right Border Padding.
     */
    public static final int BORDER_RIGHT = 5;


    /**
     * Boolean Value used by the User List Screen to display a List of Students.
     */
    public static final boolean SHOW_STUDENTS = true;
    /**
     * Boolean Value used by the User List Screen to display a List of Professors.
     */
    public static final boolean SHOW_PROFESSORS = false;


    /**
     * Error Message: Illegal User Type.
     */
    public static final String ILLEGAL_USER_TYPE_MSG = "Illegal User!";
    /**
     * Error Message: Invalid Course Details Provided.
     */
    public static final String INVALID_COURSE_TO_ADD_MSG = "Course details are invalid/incomplete!";
    /**
     * Error Message: Duplicate Course ID.
     */
    public static final String COURSE_ADD_NO_UNIQUE_ID_MSG = "Course ID given already exists!";
    /**
     * Error Message: Selected Location is Occupied.
     */
    public static final String COURSE_ADD_BUSY_LOC_MSG = "Location chosen for this Course is busy at the selected " +
            "time!";
    /**
     * Error Message: No Permissions.
     */
    public static final String NO_PERMISSIONS_MSG = "No Permissions!";
    /**
     * Error Message: Unable to Navigate to Target.
     */
    public static final String UNABLE_TO_NAVIGATE_MSG = "Unable to perform navigation task!";
    /**
     * Error Message: Selected User is Invalid.
     */
    public static final String INVALID_SELECTED_USER_MSG = "Selected User is Invalid!";
    /**
     * Error Message: Selected Course is Invalid.
     */
    public static final String INVALID_SELECTED_COURSE_MSG = "Selected Course is Invalid!";
    /**
     * Error Message: Unable to Close Course.
     */
    public static final String UNABLE_TO_CLOSE_COURSE_MSG = "Unable to Close Course!";
    /**
     * Error Message: Unable to Edit Course.
     */
    public static final String UNABLE_TO_EDIT_COURSE_MSG = "Unable to Edit Course!";
    /**
     * Error Message: Unable to Open Course.
     */
    public static final String UNABLE_TO_OPEN_COURSE_MSG = "Unable to Open Course!";
    /**
     * Error Message: Unable to Remove Course.
     */
    public static final String UNABLE_TO_REMOVE_COURSE_MSG = "Unable to Remove Course!";
    /**
     * Error Message: Unable to Load Course Info.
     */
    public static final String UNSUCCESSFUL_LOAD_COURSE_INFO_MSG = "Unable to load Course Info Screen!";
    /**
     * Error Message: Unable to Add Course.
     */
    public static final String UNABLE_TO_ADD_COURSE_MSG = "Unable to add Course!";
    /**
     * Error Message: Unable to Login.
     */
    public static final String UNABLE_TO_LOG_USER_IN_MSG = "Unable to Log In!";
    /**
     * Error Message: Unsuccessful Login.
     */
    public static final String UNSUCCESSFUL_LOGIN_MSG = "Unsuccessful Login Attempt!";
    /**
     * Error Message: Unable to Toggle Registration.
     */
    public static final String UNABLE_TO_TOGGLE_REGISTRATION_MSG = "Unable to Toggle Registration!";
    /**
     * Error Message: Unable to Move to Next Semester.
     */
    public static final String UNABLE_TO_MOVE_TO_NEXT_SEM_MSG = "Unable to Move to Next Semester!";


    /**
     * Success Message: Added new Course.
     */
    public static final String SUCCESSFULLY_ADDED_COURSE_MSG = "Successfully Added the Course!";
    /**
     * Success Message: Removed Course.
     */
    public static final String SUCCESSFULLY_REMOVED_COURSE_MSG = "Successfully Removed the Course!";
    /**
     * Success Message: Opened Course.
     */
    public static final String SUCCESSFULLY_OPEN_COURSE_MSG = "Successfully Opened the Course!";
    /**
     * Success Message: Closed Course.
     */
    public static final String SUCCESSFULLY_CLOSED_COURSE_MSG = "Successfully Closed the Course!";
    /**
     * Success Message: Edited Course.
     */
    public static final String SUCCESSFULLY_EDIT_COURSE_MSG = "Successfully Edited the Course!";
    /**
     * Success Message: Toggled Overall Registration.
     */
    public static final String SUCCESSFULLY_OPEN_OVERALL_MSG = "Successfully Toggle Registration!";
    /**
     * Success Message: Moved to Next Semester
     */
    public static final String SUCCESSFULLY_MOVED_TO_NEXT_SEM_MSG = "Successfully Moved to Next Semester!";

    /**
     * Success & Error Message: Empty Message.
     */
    public static final String EMPTY_MSG = "";


    /**
     * Navigation Action Name: View Course List.
     */
    public static final String VIEW_COURSE_LIST_ACTION_NAME = "View Course List";
    /**
     * Navigation Action Name: View Student List.
     */
    public static final String VIEW_STUDENT_LIST_ACTION_NAME = "View Student List";
    /**
     * Navigation Action Name: View Professor List.
     */
    public static final String VIEW_PROFESSOR_LIST_ACTION_NAME = "View Professor List";
    /**
     * Navigation Action Name: View Course Info.
     */
    public static final String VIEW_COURSE_INFO_ACTION_NAME = "View Course Info";
    /**
     * Navigation Action Name: Register for Classes.
     */
    public static final String VIEW_CLASSES_TO_REGISTER_ACTION_NAME = "Register for Classes";
    /**
     * Navigation Action Name: Edit Course.
     */
    public static final String VIEW_EDIT_COURSE_ACTION_NAME = "Edit Course";
    /**
     * Navigation Action Name: Add New Course.
     */
    public static final String VIEW_ADD_COURSE_ACTION_NAME = "Add New Course";
    /**
     * Navigation Action Name: View User Details
     */
    public static final String VIEW_USER_COURSE_LIST_ACTION_NAME = "View User Details";
    /**
     * Navigation Action Name: Move Back
     */
    public static final String BACK_ACTION_NAME = "Back";


    /**
     * Action Name: Login.
     */
    public static final String LOGIN_ACTION_NAME = "Login";
    /**
     * Action Name: Logout.
     */
    public static final String LOGOUT_ACTION_NAME = "Logout";
    /**
     * Action Name: Save Course.
     */
    public static final String SAVE_COURSE_ACTION_NAME = "Save Course";
    /**
     * Action Name: Add Course.
     */
    public static final String ADD_COURSE_ACTION_NAME = "Add Course";
    /**
     * Action Name: Remove Course.
     */
    public static final String REMOVE_COURSE_ACTION_NAME = "Remove Course";
    /**
     * Action Name: Register for Course.
     */
    public static final String REGISTER_FOR_CLASS_ACTION_NAME = "Register for Course";
    /**
     * Action Name: Drop Course.
     */
    public static final String DROP_COURSE_ACTION_NAME = "Drop Course";
    /**
     * Action Name: Add Course to User.
     */
    public static final String VIEW_ADD_COURSE_TO_USER_ACTION_NAME = "Add Course";
    /**
     * Action Name: Remove Course from User.
     */
    public static final String REMOVE_COURSE_FROM_USER_ACTION_NAME = "Remove Course";
    /**
     * Action Name: Open Registration for Course.
     */
    public static final String OPEN_COURSE_ACTION_NAME = "Open Registration";
    /**
     * Action Name: Close Registration for Course.
     */
    public static final String CLOSE_COURSE_ACTION_NAME = "Close Registration";
    /**
     * Action Name: Open Registration.
     */
    public static final String TOGGLE_OPEN_ACTION_NAME = "Open Registration";
    /**
     * Action Name: Close Registration.
     */
    public static final String TOGGLE_CLOSE_ACTION_NAME = "Close Registration";
    /**
     * Action Name: Move to Next Semester.
     */
    public static final String MOVE_TO_NEXT_SEMESTER_ACTION_NAME = "Move Semester";

    /**
     * GUI Title: Main Menu.
     */
    public static final String GUI_TITLE = "Course Registration";
    /**
     * GUI Title: Username Field.
     */
    public static final String USERNAME_FIELD_TITLE = "Username";
    /**
     * GUI Title: Password Field.
     */
    public static final String PASSWORD_FIELD_TITLE = "Password";
    /**
     * GUI Title: Schedule Screen.
     */
    public static final String SCHEDULE_TITLE = "Schedule";
    /**
     * GUI Title: Course Info Screen.
     */
    public static final String COURSE_INFO_TITLE = "Course Info: #";
    /**
     * GUI Title: Admin Navbar Screen.
     */
    public static final String ADMIN_NAV_TITLE = "Admin Navbar";
    /**
     * GUI Title: Course List Screen.
     */
    public static final String COURSE_LIST_TITLE = "Course List";
    /**
     * GUI Title: New Course Screen.
     */
    public static final String NEW_COURSE_TITLE = "New Course";
    /**
     * GUI Title: Edit Course Screen.
     */
    public static final String EDIT_COURSE_TITLE = "Edit Course";
    /**
     * GUI Title: User List Screen.
     */
    public static final String USER_LIST_TITLE = " List";

    /**
     * GUI Title: Course Status Field.
     */
    public static final String COURSE_STATUS_TITLE = "Status:";
    /**
     * GUI Title: Course Professor Field.
     */
    public static final String COURSE_PROFESSOR_TITLE = "Professor:";
    /**
     * GUI Title: Course Location Field.
     */
    public static final String COURSE_LOCATION_TITLE = "Location:";
    /**
     * GUI Title: Course Schedule Field.
     */
    public static final String COURSE_SCHEDULE_TITLE = "Schedule:";
    /**
     * GUI Title: Student List Field.
     */
    public static final String STUDENT_LIST_TITLE = "Student List";
    /**
     * GUI Title: College Field.
     */
    public static final String COLLEGE_FIELD_TITLE = "College";
    /**
     * GUI Title: Department Field.
     */
    public static final String DEPARTMENT_FIELD_TITLE = "Department";
    /**
     * GUI Title: Course Number Field.
     */
    public static final String COURSE_NUMBER_FIELD_TITLE = "Course Number";
    /**
     * GUI Title: Professors Field.
     */
    public static final String PROFESSORS_FIELD_TITLE = "Professors";
    /**
     * GUI Title: Max Capacity Field.
     */
    public static final String MAX_CAPACITY_FIELD_TITLE = "Max Capacity";
    /**
     * GUI Title: Schedule Days Field.
     */
    public static final String DAYS_FIELD_TITLE = "Days Of The Week";
    /**
     * GUI Title: Start Time Field.
     */
    public static final String START_TIME_FIELD_TITLE = "Start Time";
    /**
     * GUI Title: End Time Field.
     */
    public static final String END_TIME_FIELD_TITLE = "End Time";
    /**
     * GUI Title: Building Code Field.
     */
    public static final String BUILDING_FIELD_TITLE = "Building";
    /**
     * GUI Title: Room Number Field.
     */
    public static final String ROOM_NUMBER_FIELD_TITLE = "Room Number";


    /**
     * GUI Formatting: Font Type
     */
    public static final String GUI_FONT = "Tahoma";
    /**
     * GUI Formatting: First Text Size.
     */
    public static final int GUI_TEXT_SIZE_ONE = 20;
    /**
     * GUI Formatting: Second Text Size.
     */
    public static final int GUI_TEXT_SIZE_TWO = 14;
    /**
     * GUI Formatting: Third Text Size.
     */
    public static final int GUI_TEXT_SIZE_THREE = 12;


    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public GUIConfig() throws InstantiationException {
        throw new InstantiationException("Illegal No Args Constructor");
    }

}
