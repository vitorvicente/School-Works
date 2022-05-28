package edu.bu.cs411.UI.Actions.Navigation;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.Courses.Course;
import edu.bu.cs411.UI.Actions.Actual.EditCourse;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.PageIndex;
import edu.bu.cs411.UI.Screens.Admin.CourseEditScreen;
import edu.bu.cs411.UI.Screens.Common.CourseListScreen;
import edu.bu.cs411.UI.Screens.GUIScreen;
import edu.bu.cs411.Users.Admin;
import edu.bu.cs411.Users.Professor;
import edu.bu.cs411.Users.User;
import edu.bu.cs411.Users.Util.UniqueID;

import java.awt.event.ActionEvent;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * Course Edit Navigation Action Class.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class GoToCourseEdit extends GUIAction {

    /**
     * Action Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Base Constructor for the Course Edit Navigation Action.
     * Initializes the data required.
     *
     * @param displayName Action Display Name.
     * @param pageIndex   Reference to the overall GUI.
     */
    public GoToCourseEdit(String displayName, PageIndex pageIndex) {
        super(displayName, pageIndex);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public GoToCourseEdit() throws InstantiationException {
        super();
    }

    /**
     * Static Factory Method to return the Course Edit Screen.
     *
     * @param extraMessage Error/Success Message to Display on the Screen.
     * @param pageIndex    Reference to the PageIndex Software.
     * @param user         Logged-In User.
     * @param course       Course to Edit.
     * @param backAction   Specific Back Navigation Action for the Screen.
     * @return Base Course Info Screen.
     */
    public static GUIScreen getScreen(String extraMessage, User user, PageIndex pageIndex, Course course,
                                      GUIAction backAction) {
        CourseEditScreen newScreen = new CourseEditScreen();

        ArrayList<UniqueID> professors = new ArrayList<>();
        for (Professor professor : pageIndex.getSoftware().getUserListing().getProfessors())
            professors.add(professor.getID());

        newScreen.setUser(user);
        newScreen.setCourse(course);
        newScreen.setProfessors(professors);
        newScreen.initialize(new ArrayList<>(
                List.of(
                        new EditCourse(GUIConfig.SAVE_COURSE_ACTION_NAME, pageIndex),
                        backAction
                )
        ), extraMessage);

        return newScreen;
    }

    /**
     * Perform the Course Edit Navigation Action by creating a new Screen and opening it.
     * Includes logic to deal with errors inherent in fetching the Course from the Course List. It also includes
     * logic to provide the Factory Method with the proper Back Action.
     * <p>
     * If the Logged-In User isn't an Admin, it fails and displays an error message on screen.
     * <p>
     * If action is performed outside of one of the Screens that includes a selected course, it fails and displays
     * an error message on screen.
     * If action is performed and no legitimate course is able to be fetched, it fails and displays an error message
     * on screen.
     *
     * @param e ActionEvent for the performed action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        GUIScreen oldScreen = (GUIScreen) this.getPageIndex().getFrame();
        User user = oldScreen.getUser();
        Course course;
        GUIScreen newScreen;

        // Checks permissions
        if (!(user instanceof Admin)) {
            newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.NO_PERMISSIONS_MSG, this.getPageIndex());
            this.getPageIndex().setFrame(newScreen);
            return;
        }

        // Deal with Actions coming from CourseListScreen
        if (oldScreen instanceof CourseListScreen castedScreen) {
            course = this.getPageIndex().getSoftware().getCourseListing()
                    .fetchCourse(castedScreen.getSelectedCourse());

            // Invalid Course Fetched
            if (course == null) {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.UNSUCCESSFUL_LOAD_COURSE_INFO_MSG,
                        this.getPageIndex());
                this.getPageIndex().setFrame(newScreen);
                return;
            }

            newScreen = GoToCourseEdit.getScreen(GUIConfig.EMPTY_MSG, user, this.getPageIndex(), course,
                    new GoToCourseList(GUIConfig.BACK_ACTION_NAME, this.getPageIndex()));

            // Deals with Invalid Actions
        } else {
            newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.UNSUCCESSFUL_LOAD_COURSE_INFO_MSG,
                    this.getPageIndex());
        }

        this.getPageIndex().setFrame(newScreen);
    }

}
