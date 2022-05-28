package edu.bu.cs411.UI.Actions.Navigation;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.UI.Actions.Actual.RemoveCourseFromUser;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.PageIndex;
import edu.bu.cs411.UI.Screens.Admin.UserCourseAddScreen;
import edu.bu.cs411.UI.Screens.Admin.UserCourseListScreen;
import edu.bu.cs411.UI.Screens.Admin.UserListScreen;
import edu.bu.cs411.UI.Screens.GUIScreen;
import edu.bu.cs411.Users.Admin;
import edu.bu.cs411.Users.Professor;
import edu.bu.cs411.Users.Student;
import edu.bu.cs411.Users.User;
import edu.bu.cs411.Users.Util.UniqueID;

import java.awt.event.ActionEvent;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * User Course List Navigation Action Class.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class GoToUserCourseList extends GUIAction {

    /**
     * Action Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Base Constructor for the User Course List Navigation Action.
     * Initializes the data required.
     *
     * @param displayName Action Display Name.
     * @param pageIndex   Reference to the overall GUI.
     */
    public GoToUserCourseList(String displayName, PageIndex pageIndex) {
        super(displayName, pageIndex);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public GoToUserCourseList() throws InstantiationException {
        super();
    }

    /**
     * Static Factory Method to return the User Course List Screen.
     *
     * @param extraMessage  Error/Success Message to Display on the Screen.
     * @param pageIndex     Reference to the PageIndex Software.
     * @param user          Logged-In User.
     * @param secondaryUser Reference to the User to View the Courses of.
     * @param backAction    Specific Back Navigation Action for the Screen.
     * @return Base User Course List Screen.
     */
    public static GUIScreen getScreen(String extraMessage, PageIndex pageIndex, User user, UniqueID secondaryUser,
                                      GUIAction backAction) {
        UserCourseListScreen newScreen = new UserCourseListScreen();
        newScreen.setUser(user);
        newScreen.setSecondaryUser(secondaryUser);

        if (pageIndex.getSoftware().getUserListing().fetchUser(secondaryUser) instanceof Student castedUser)
            newScreen.setCourses(castedUser.getCoursesRegisteredFor());
        else if (pageIndex.getSoftware().getUserListing().fetchUser(secondaryUser) instanceof Professor castedUser)
            newScreen.setCourses(castedUser.getCoursesTeaching());

        newScreen.initialize(new ArrayList<>(
                List.of(
                        new GoToCourseInfo(GUIConfig.VIEW_COURSE_INFO_ACTION_NAME, pageIndex),
                        new RemoveCourseFromUser(GUIConfig.REMOVE_COURSE_FROM_USER_ACTION_NAME, pageIndex),
                        new GoToUserCourseAdd(GUIConfig.VIEW_ADD_COURSE_TO_USER_ACTION_NAME, pageIndex),
                        backAction
                )
        ), extraMessage);

        return newScreen;
    }

    /**
     * Perform the User Course List Navigation Action by creating a new Screen and opening it.
     * It includes logic to provide the Factory Method with the proper Back Action.
     * <p>
     * If the Logged-In User isn't an Admin, it fails and displays an error message on screen.
     * If action is performed outside of one of the UserListScreen or UserCourseAddScreen, it fails and displays
     * an error message on screen.
     *
     * @param e ActionEvent for the performed action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        GUIScreen oldScreen = (GUIScreen) this.getPageIndex().getFrame();
        User user = oldScreen.getUser();
        UniqueID secondaryUser;
        GUIScreen newScreen;

        // Checks permissions
        if (!(user instanceof Admin)) {
            newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.NO_PERMISSIONS_MSG, this.getPageIndex());
            this.getPageIndex().setFrame(newScreen);
            return;
        }

        // Deals with Actions coming from UserListScreen
        if (oldScreen instanceof UserListScreen castedScreen) {
            secondaryUser = castedScreen.getSelectedUser();
            User fetchedUser = this.getPageIndex().getSoftware().getUserListing().fetchUser(secondaryUser);

            // Check Valid User
            if (fetchedUser == null) {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.INVALID_SELECTED_USER_MSG,
                        this.getPageIndex());
                this.getPageIndex().setFrame(newScreen);
                return;
            }

            newScreen = GoToUserCourseList.getScreen(GUIConfig.EMPTY_MSG, this.getPageIndex(), user, secondaryUser,
                    new GoToUserList(GUIConfig.BACK_ACTION_NAME, this.getPageIndex(),
                            castedScreen.getStudentsOrProfessors()));


            // Deals with Actions coming from the UserCourseAddScreen
        } else if (oldScreen instanceof UserCourseAddScreen castedScreen) {
            secondaryUser = castedScreen.getSecondaryUser();
            User fetchedUser = this.getPageIndex().getSoftware().getUserListing().fetchUser(secondaryUser);

            // Check Valid User
            if (fetchedUser == null) {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.INVALID_SELECTED_USER_MSG,
                        this.getPageIndex());
                this.getPageIndex().setFrame(newScreen);
                return;
            }

            // Deal with Students
            if (fetchedUser instanceof Student) {
                newScreen = GoToUserCourseList.getScreen(GUIConfig.EMPTY_MSG, this.getPageIndex(), user, secondaryUser,
                        new GoToUserList(GUIConfig.BACK_ACTION_NAME, this.getPageIndex(), GUIConfig.SHOW_STUDENTS));

                // Deal with Professors
            } else if (fetchedUser instanceof Professor) {
                newScreen = GoToUserCourseList.getScreen(GUIConfig.EMPTY_MSG, this.getPageIndex(), user, secondaryUser,
                        new GoToUserList(GUIConfig.BACK_ACTION_NAME, this.getPageIndex(), GUIConfig.SHOW_PROFESSORS));

                // Deal with Invalid User Types
            } else {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.ILLEGAL_USER_TYPE_MSG, this.getPageIndex());
            }

            // Deals with Invalid Actions
        } else {
            newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.UNABLE_TO_NAVIGATE_MSG, this.getPageIndex());
        }

        this.getPageIndex().setFrame(newScreen);
    }

}
