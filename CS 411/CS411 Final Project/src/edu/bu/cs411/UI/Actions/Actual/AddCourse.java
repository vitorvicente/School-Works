package edu.bu.cs411.UI.Actions.Actual;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.Courses.Course;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.Actions.Navigation.GoToAdminNav;
import edu.bu.cs411.UI.Actions.Navigation.GoToCourseList;
import edu.bu.cs411.UI.PageIndex;
import edu.bu.cs411.UI.Screens.Admin.CourseAddScreen;
import edu.bu.cs411.UI.Screens.GUIScreen;
import edu.bu.cs411.Users.Admin;
import edu.bu.cs411.Users.User;

import java.awt.event.ActionEvent;
import java.io.Serial;

/**
 * Add Course Action Class.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class AddCourse extends GUIAction {

    /**
     * Action Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Base Constructor for the Add Course Action.
     * Initializes the data required.
     *
     * @param displayName Action Display Name.
     * @param pageIndex   Reference to the overall GUI.
     */
    public AddCourse(String displayName, PageIndex pageIndex) {
        super(displayName, pageIndex);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public AddCourse() throws InstantiationException {
        super();
    }

    /**
     * Perform the Add Course Action by verifying the Adding Course details, and working accordingly.
     * Includes logic to deal with errors inherent in creating a Course.
     * <p>
     * If the Action is called from a screen other than the CourseAddScreen, it fails and displays an error message
     * on screen.
     * If the User performing the Action isn't an Admin, it fails and displays an error message on screen.
     * If at any point, some data of the given Course is invalid, the ID is not Unique, or the chosen Room is busy at
     * that time, it fails and displays an error message on screen.
     * <p>
     * If it succeeds, it will return to the Course List screen, displaying a success message.
     *
     * @param e ActionEvent for the performed action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        GUIScreen oldScreen = (GUIScreen) this.getPageIndex().getFrame();
        User user = oldScreen.getUser();
        GUIScreen newScreen;

        // Checks permissions
        if (!(user instanceof Admin)) {
            newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.NO_PERMISSIONS_MSG, this.getPageIndex());
            this.getPageIndex().setFrame(newScreen);
            return;
        }

        // Deal with Actions coming from CourseAddScreen
        if (oldScreen instanceof CourseAddScreen) {
            Course course = ((CourseAddScreen) oldScreen).getCourse();

            // Check Valid Course Details
            if (!this.getPageIndex().getSoftware().getCourseListing().checkValid(course)) {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.INVALID_COURSE_TO_ADD_MSG,
                        this.getPageIndex());
                this.getPageIndex().setFrame(newScreen);
                return;
            }
            // Check Unique CourseID
            if (!this.getPageIndex().getSoftware().getCourseListing().checkUnique(course)) {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.COURSE_ADD_NO_UNIQUE_ID_MSG,
                        this.getPageIndex());
                this.getPageIndex().setFrame(newScreen);
                return;
            }
            // Check Unique Location + Schedule
            if (!this.getPageIndex().getSoftware().getCourseListing().checkLocation(course)) {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.COURSE_ADD_BUSY_LOC_MSG, this.getPageIndex());
                this.getPageIndex().setFrame(newScreen);
                return;
            }
            this.getPageIndex().getSoftware().getCourseListing().addCourse(course);

            newScreen = GoToCourseList.getScreen(GUIConfig.SUCCESSFULLY_ADDED_COURSE_MSG,
                    this.getPageIndex(), user,
                    new GoToAdminNav(GUIConfig.BACK_ACTION_NAME, this.getPageIndex()));

            // Deals with Invalid Actions
        } else {
            newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.UNABLE_TO_ADD_COURSE_MSG, this.getPageIndex());
        }

        this.getPageIndex().setFrame(newScreen);
    }

}
