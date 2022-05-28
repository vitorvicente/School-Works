package edu.bu.cs411.UI.Actions.Actual;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.Courses.Course;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.PageIndex;
import edu.bu.cs411.UI.Screens.Common.CourseListScreen;
import edu.bu.cs411.UI.Screens.GUIScreen;
import edu.bu.cs411.Users.Admin;
import edu.bu.cs411.Users.User;

import java.awt.event.ActionEvent;
import java.io.Serial;

/**
 * Remove Course Action Class.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class RemoveCourse extends GUIAction {

    /**
     * Action Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Base constructor for the Remove Course Action.
     * Initializes the data required.
     *
     * @param displayName Action Display Name.
     * @param pageIndex   Reference to the overall GUI.
     */
    public RemoveCourse(String displayName, PageIndex pageIndex) {
        super(displayName, pageIndex);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public RemoveCourse() throws InstantiationException {
        super();
    }

    /**
     * Perform the Remove Course Action by checking the validity of the Remove Request, and working accordingly.
     * It includes logic to deal with errors inherent in the removing of a Course.
     * <p>
     * If the Action is called from a screen other than the CourseListScreen, it fails and displays an error message
     * on screen.
     * If the User performing the Action isn't an Admin, it fails and displays an error message on screen.
     * If at any point, the selected Course is Invalid, it fails and displays an error message on screen.
     * <p>
     * If it succeeds, it Opens the Course, and stays in the CourseListScreen, displaying a success message.
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

        // Deal with Actions coming from CourseListScreen
        if (oldScreen instanceof CourseListScreen castedScreen) {
            Course selectedCourse =
                    this.getPageIndex().getSoftware().getCourseListing().fetchCourse(castedScreen.getSelectedCourse());

            // Checks Valid Course
            if (selectedCourse == null) {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.INVALID_SELECTED_COURSE_MSG,
                        this.getPageIndex());
                this.getPageIndex().setFrame(newScreen);
                return;
            }

            this.getPageIndex().getSoftware().getCourseListing().removeCourse(castedScreen.getSelectedCourse());
            newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.SUCCESSFULLY_REMOVED_COURSE_MSG,
                    this.getPageIndex());

            // Deals with Invalid Actions
        } else {
            newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.UNABLE_TO_REMOVE_COURSE_MSG, this.getPageIndex());
        }

        this.getPageIndex().setFrame(newScreen);
    }

}
