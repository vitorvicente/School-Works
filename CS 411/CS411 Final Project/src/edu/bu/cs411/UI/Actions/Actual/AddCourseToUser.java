package edu.bu.cs411.UI.Actions.Actual;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.Courses.Course;
import edu.bu.cs411.Courses.CourseID;
import edu.bu.cs411.Courses.Util.CourseStatus;
import edu.bu.cs411.Registrar.RegistrationDetails;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.Actions.Navigation.GoToSchedule;
import edu.bu.cs411.UI.Actions.Navigation.GoToUserCourseList;
import edu.bu.cs411.UI.Actions.Navigation.GoToUserList;
import edu.bu.cs411.UI.PageIndex;
import edu.bu.cs411.UI.Screens.Admin.UserCourseAddScreen;
import edu.bu.cs411.UI.Screens.Admin.UserCourseListScreen;
import edu.bu.cs411.UI.Screens.Common.CourseListScreen;
import edu.bu.cs411.UI.Screens.GUIScreen;
import edu.bu.cs411.Users.Professor;
import edu.bu.cs411.Users.Student;
import edu.bu.cs411.Users.User;

import java.awt.event.ActionEvent;
import java.io.Serial;

/**
 * Add Course To User Action Class.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class AddCourseToUser extends GUIAction {

    /**
     * Action Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Base Constructor for the Add Course To User Action.
     * Initializes the data required.
     *
     * @param displayName Action Display Name.
     * @param pageIndex   Reference to the overall GUI.
     */
    public AddCourseToUser(String displayName, PageIndex pageIndex) {
        super(displayName, pageIndex);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public AddCourseToUser() throws InstantiationException {
        super();
    }

    /**
     * Perform the Add Course to User Action by checking who is adding (ie: Admin add or other add), checking the
     * validity of the request data, and working accordingly.
     * It includes logic to deal with errors inherent in the addition of a Course to a user. It also includes logic
     * to provide the Factory Method with the proper Back Action.
     * <p>
     * If the Action is called from a Screen other than CourseListScreen or UserCourseListScreen, it fails and
     * displays an error message on screen.
     * If the User performing the Action for another User isn't an Admin, it fails and displays an error message on
     * screen.
     * If either the User or Course details are invalid, it fails and displays an error message on the screen.
     * <p>
     * If it succeeds, it adds the Course to the User, and returns to the previous Screen, displaying a success message.
     *
     * @param e ActionEvent for the performed action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        GUIScreen oldScreen = (GUIScreen) this.getPageIndex().getFrame();
        User user = oldScreen.getUser();
        User userToAdd;
        CourseID courseToAdd;
        Course fetchedCourseToAdd;
        GUIScreen newScreen;

        // Deal with Actions coming from UserCourseListScreen
        if (oldScreen instanceof UserCourseAddScreen castedScreen) {
            courseToAdd = castedScreen.getSelectedCourse();
            fetchedCourseToAdd = this.getPageIndex().getSoftware().getCourseListing().fetchCourse(courseToAdd);
            userToAdd = this.getPageIndex().getSoftware().getUserListing().fetchUser(castedScreen.getSecondaryUser());

            // Check Valid User
            if (userToAdd == null) {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.INVALID_SELECTED_USER_MSG,
                        this.getPageIndex());
                this.getPageIndex().setFrame(newScreen);
                return;
            }
            //Check Valid Course
            if (fetchedCourseToAdd == null) {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.INVALID_SELECTED_COURSE_MSG,
                        this.getPageIndex());
                this.getPageIndex().setFrame(newScreen);
                return;
            }


            // Deal with adding Course to Students
            if (userToAdd instanceof Student castedUser) {
                fetchedCourseToAdd.addStudent(castedUser.getID());
                boolean temp = castedUser.registerForCourse(courseToAdd);
                System.out.println(temp);
                newScreen = GoToUserCourseList.getScreen(GUIConfig.SUCCESSFULLY_ADDED_COURSE_MSG, this.getPageIndex(),
                        oldScreen.getUser(),
                        castedScreen.getSecondaryUser(), new GoToUserList(GUIConfig.BACK_ACTION_NAME,
                                this.getPageIndex(), GUIConfig.SHOW_STUDENTS));

                // Deal with adding Course to Professors
            } else if (userToAdd instanceof Professor castedUser) {
                fetchedCourseToAdd.setProfessor(castedUser.getID());

                castedUser.addCourseTeaching(courseToAdd);
                newScreen = GoToUserCourseList.getScreen(GUIConfig.SUCCESSFULLY_ADDED_COURSE_MSG, this.getPageIndex(),
                        oldScreen.getUser(),
                        castedScreen.getSecondaryUser(), new GoToUserList(GUIConfig.BACK_ACTION_NAME,
                                this.getPageIndex(), GUIConfig.SHOW_PROFESSORS));
                // Deal with adding Course to Others
            } else {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.INVALID_SELECTED_USER_MSG,
                        this.getPageIndex());
            }

            // Deal with Actions coming from UserCourseListScreen
        } else if (oldScreen instanceof CourseListScreen castedScreen) {
            courseToAdd = castedScreen.getSelectedCourse();
            fetchedCourseToAdd = this.getPageIndex().getSoftware().getCourseListing().fetchCourse(courseToAdd);

            // Check Valid Course
            if (fetchedCourseToAdd == null) {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.INVALID_SELECTED_COURSE_MSG,
                        this.getPageIndex());
                this.getPageIndex().setFrame(newScreen);
                return;
            }

            // Check if Registration is Allowed
            if ((!this.getPageIndex().getSoftware().getRegistrationDetails().isOverAllOpen()
                    && !fetchedCourseToAdd.getStatus().equals(CourseStatus.OPEN))
                    || fetchedCourseToAdd.getMaxCapacity() <= fetchedCourseToAdd.getStudents().size()) {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.UNABLE_TO_ADD_COURSE_MSG,
                        this.getPageIndex());
                this.getPageIndex().setFrame(newScreen);
                return;
            }

            // Deal with adding Course to Students
            if (user instanceof Student castedUser) {
                // Check if User is Fully Registered
                if (castedUser.getNumCredits() >= RegistrationDetails.MAX_CREDITS) {
                    newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.UNABLE_TO_ADD_COURSE_MSG,
                            this.getPageIndex());
                    this.getPageIndex().setFrame(newScreen);
                    return;
                }

                fetchedCourseToAdd.addStudent(castedUser.getID());
                castedUser.registerForCourse(courseToAdd);
                newScreen = GoToSchedule.getScreen(GUIConfig.SUCCESSFULLY_ADDED_COURSE_MSG, this.getPageIndex(),
                        oldScreen.getUser());

                // Deal with adding Course to Others
            } else {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.INVALID_SELECTED_USER_MSG,
                        this.getPageIndex());
            }

            // Deals with Invalid Actions
        } else {
            newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.UNABLE_TO_ADD_COURSE_MSG, this.getPageIndex());
            System.out.println("Weird Screen");
        }

        this.getPageIndex().setFrame(newScreen);
    }

}
