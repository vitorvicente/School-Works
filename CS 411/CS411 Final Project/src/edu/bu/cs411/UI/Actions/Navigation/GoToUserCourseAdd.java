package edu.bu.cs411.UI.Actions.Navigation;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.Courses.Course;
import edu.bu.cs411.Courses.CourseID;
import edu.bu.cs411.UI.Actions.Actual.AddCourseToUser;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.PageIndex;
import edu.bu.cs411.UI.Screens.Admin.UserCourseAddScreen;
import edu.bu.cs411.UI.Screens.Admin.UserCourseListScreen;
import edu.bu.cs411.UI.Screens.GUIScreen;
import edu.bu.cs411.Users.Admin;
import edu.bu.cs411.Users.User;
import edu.bu.cs411.Users.Util.UniqueID;

import java.awt.event.ActionEvent;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * User Course Add Navigation Action Class.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class GoToUserCourseAdd extends GUIAction {

    /**
     * Action Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Base Constructor for the Course Add Navigation Action.
     * Initializes the data required.
     *
     * @param displayName Action Display Name.
     * @param pageIndex   Reference to the overall GUI.
     */
    public GoToUserCourseAdd(String displayName, PageIndex pageIndex) {
        super(displayName, pageIndex);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public GoToUserCourseAdd() throws InstantiationException {
        super();
    }

    /**
     * Static Factory Method to return the User Course Add Screen.
     *
     * @param extraMessage  Error/Success Message to Display on the Screen.
     * @param pageIndex     Reference to the PageIndex Software.
     * @param user          Logged-In User.
     * @param secondaryUser Reference to the User to Add a Course to.
     * @param backAction    Specific Back Navigation Action for the Screen.
     * @return Base User Course Add Screen.
     */
    public static GUIScreen getScreen(String extraMessage, PageIndex pageIndex, User user, UniqueID secondaryUser,
                                      GUIAction backAction) {
        UserCourseAddScreen newScreen = new UserCourseAddScreen();

        ArrayList<CourseID> courses = new ArrayList<>();
        for (Course course : pageIndex.getSoftware().getCourseListing().getCourses())
            courses.add(course.getId());

        newScreen.setUser(user);
        newScreen.setSecondaryUser(secondaryUser);
        newScreen.setCourses(courses);
        newScreen.initialize(new ArrayList<>(
                List.of(
                        new GoToCourseInfo(GUIConfig.VIEW_COURSE_INFO_ACTION_NAME, pageIndex),
                        new AddCourseToUser(GUIConfig.ADD_COURSE_ACTION_NAME, pageIndex),
                        backAction
                )
        ), extraMessage);

        return newScreen;
    }

    /**
     * Perform the User Course Add Navigation Action by creating a new Screen and opening it.
     * It includes logic to provide the Factory Method with the proper Back Action.
     * <p>
     * If the Logged-In User isn't an Admin, it fails and displays an error message on screen.
     * If action is performed outside of one of the UserCourseListScreen, it fails and displays
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

        // Deal with Actions coming from UserCourseListScreen
        if (oldScreen instanceof UserCourseListScreen castedScreen) {
            secondaryUser = castedScreen.getSecondaryUser();
            User fetchedUser = this.getPageIndex().getSoftware().getUserListing().fetchUser(secondaryUser);

            // Check Valid User
            if (fetchedUser == null) {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.INVALID_SELECTED_USER_MSG,
                        this.getPageIndex());
                this.getPageIndex().setFrame(newScreen);
                return;
            }

            newScreen = GoToUserCourseAdd.getScreen(GUIConfig.EMPTY_MSG, this.getPageIndex(), user,
                    secondaryUser, new GoToUserCourseList(GUIConfig.BACK_ACTION_NAME, this.getPageIndex()));

            // Deals with Invalid Actions
        } else {
            newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.UNABLE_TO_NAVIGATE_MSG, this.getPageIndex());
        }

        this.getPageIndex().setFrame(newScreen);
    }

}
