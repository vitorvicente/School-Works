package edu.bu.cs411.UI.Actions.Actual;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.Config.UsersConfig;
import edu.bu.cs411.Courses.Course;
import edu.bu.cs411.Courses.CourseID;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.Actions.Navigation.GoToSchedule;
import edu.bu.cs411.UI.Actions.Navigation.GoToUserCourseList;
import edu.bu.cs411.UI.Actions.Navigation.GoToUserList;
import edu.bu.cs411.UI.PageIndex;
import edu.bu.cs411.UI.Screens.Admin.UserCourseListScreen;
import edu.bu.cs411.UI.Screens.Common.ScheduleScreen;
import edu.bu.cs411.UI.Screens.GUIScreen;
import edu.bu.cs411.Users.Professor;
import edu.bu.cs411.Users.Student;
import edu.bu.cs411.Users.User;

import java.awt.event.ActionEvent;
import java.io.Serial;

/**
 * Remove Course From User Action Class.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class RemoveCourseFromUser extends GUIAction {

    /**
     * Action Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Base constructor for the Remove Course From User Action.
     * Initializes the data required.
     *
     * @param displayName Action Display Name.
     * @param pageIndex   Reference to the overall GUI.
     */
    public RemoveCourseFromUser(String displayName, PageIndex pageIndex) {
        super(displayName, pageIndex);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public RemoveCourseFromUser() throws InstantiationException {
        super();
    }

    /**
     * Perform the Remove Course From User Action by checking who is removing (ie: Admin add or other add), checking the
     * validity of the request data, and working accordingly.
     * It includes logic to deal with errors inherent in the removal of a Course from a user. It also includes logic
     * to provide the Factory Method with the proper Back Action.
     * <p>
     * If the Action is called from a Screen other than CourseListScreen or UserCourseListScreen, it fails and
     * displays an error message on screen.
     * If the User performing the Action for another User isn't an Admin, it fails and displays an error message on
     * screen.
     * If either the User or Course details are invalid, it fails and displays an error message on the screen.
     * <p>
     * If it succeeds, it removes the Course to the User, and returns to the previous Screen.
     *
     * @param e ActionEvent for the performed action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        GUIScreen oldScreen = (GUIScreen) this.getPageIndex().getFrame();
        User user = oldScreen.getUser();
        User userToRemove;
        CourseID courseToRemove;
        Course fetchedCourseToRemove;
        GUIScreen newScreen;

        // Deal with Actions coming from UserCourseListScreen
        if (oldScreen instanceof UserCourseListScreen castedScreen) {
            courseToRemove = castedScreen.getSelectedCourse();
            fetchedCourseToRemove = this.getPageIndex().getSoftware().getCourseListing().fetchCourse(courseToRemove);
            userToRemove = this.getPageIndex().getSoftware().getUserListing().fetchUser(castedScreen.getSecondaryUser());

            // Check Valid User
            if (userToRemove == null) {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.INVALID_SELECTED_USER_MSG,
                        this.getPageIndex());
                this.getPageIndex().setFrame(newScreen);
                return;
            }
            //Check Valid Course
            if (fetchedCourseToRemove == null) {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.INVALID_SELECTED_COURSE_MSG,
                        this.getPageIndex());
                this.getPageIndex().setFrame(newScreen);
                return;
            }

            // TODO Update Course Variables

            // Deal with removing Course to Students
            if (userToRemove instanceof Student castedUser) {
                // Check if the Student is Registered for the Course
                if (!castedUser.isRegisteredFor(courseToRemove)) {
                    newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.INVALID_SELECTED_COURSE_MSG,
                            this.getPageIndex());
                    this.getPageIndex().setFrame(newScreen);
                    return;
                }

                fetchedCourseToRemove.removeStudent(castedUser.getID());
                castedUser.dropCourse(courseToRemove);
                newScreen = GoToUserCourseList.getScreen(GUIConfig.SUCCESSFULLY_REMOVED_COURSE_MSG,
                        this.getPageIndex(), oldScreen.getUser(),
                        castedScreen.getSecondaryUser(), new GoToUserList(GUIConfig.BACK_ACTION_NAME,
                                this.getPageIndex(), GUIConfig.SHOW_STUDENTS));

                // Deal with removing Course to Professors
            } else if (userToRemove instanceof Professor castedUser) {
                // Check if the Professor is Registered for the Course
                if (!castedUser.isTeaching(courseToRemove)) {
                    newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.INVALID_SELECTED_COURSE_MSG,
                            this.getPageIndex());
                    this.getPageIndex().setFrame(newScreen);
                    return;
                }

                fetchedCourseToRemove.setProfessor(UsersConfig.DEFAULT_ID);
                castedUser.removeCourseTeaching(courseToRemove);
                newScreen = GoToUserCourseList.getScreen(GUIConfig.SUCCESSFULLY_REMOVED_COURSE_MSG,
                        this.getPageIndex(), oldScreen.getUser(),
                        castedScreen.getSecondaryUser(), new GoToUserList(GUIConfig.BACK_ACTION_NAME,
                                this.getPageIndex(), GUIConfig.SHOW_PROFESSORS));

                // Deal with removing Course to Others
            } else {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.INVALID_SELECTED_USER_MSG,
                        this.getPageIndex());
            }

            // Deal with Actions coming from ScheduleScreen
        } else if (oldScreen instanceof ScheduleScreen castedScreen) {
            courseToRemove = castedScreen.getSelectedCourse();
            fetchedCourseToRemove = this.getPageIndex().getSoftware().getCourseListing().fetchCourse(courseToRemove);

            // Check Valid Course
            if (fetchedCourseToRemove == null) {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.INVALID_SELECTED_COURSE_MSG,
                        this.getPageIndex());
                this.getPageIndex().setFrame(newScreen);
                return;
            }

            // Deal with removing Course to Students
            if (user instanceof Student castedUser) {
                if (!castedUser.isRegisteredFor(courseToRemove)) {
                    newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.INVALID_SELECTED_COURSE_MSG,
                            this.getPageIndex());
                    this.getPageIndex().setFrame(newScreen);
                    return;
                }

                fetchedCourseToRemove.removeStudent(castedUser.getID());
                castedUser.dropCourse(courseToRemove);
                newScreen = GoToSchedule.getScreen(GUIConfig.SUCCESSFULLY_REMOVED_COURSE_MSG, this.getPageIndex(),
                        oldScreen.getUser());

                // Deal with removing Course to Others
            } else {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.INVALID_SELECTED_USER_MSG,
                        this.getPageIndex());
            }

            // Deals with Invalid Actions
        } else {
            newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.UNABLE_TO_REMOVE_COURSE_MSG, this.getPageIndex());
        }

        this.getPageIndex().setFrame(newScreen);
    }

}
