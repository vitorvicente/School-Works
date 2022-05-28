package edu.bu.cs411.UI.Actions.Navigation;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.Courses.Course;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.PageIndex;
import edu.bu.cs411.UI.Screens.Admin.UserCourseListScreen;
import edu.bu.cs411.UI.Screens.Common.CourseInfoScreen;
import edu.bu.cs411.UI.Screens.Common.CourseListScreen;
import edu.bu.cs411.UI.Screens.Common.ScheduleScreen;
import edu.bu.cs411.UI.Screens.GUIScreen;
import edu.bu.cs411.Users.User;

import java.awt.event.ActionEvent;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * Course Info Navigation Action Class.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class GoToCourseInfo extends GUIAction {

    /**
     * Action Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Base Constructor for the Course Info Navigation Action.
     * Initializes the data required.
     *
     * @param displayName Action Display Name.
     * @param pageIndex   Reference to the overall GUI.
     */
    public GoToCourseInfo(String displayName, PageIndex pageIndex) {
        super(displayName, pageIndex);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public GoToCourseInfo() throws InstantiationException {
        super();
    }

    /**
     * Static Factory Method to return the Course Info Screen.
     *
     * @param extraMessage Error/Success Message to Display on the Screen.
     * @param user         Logged-In User.
     * @param course       Course to view Info for.
     * @param backAction   Specific Back Navigation Action for the Screen.
     * @return Base Course Info Screen.
     */
    public static GUIScreen getScreen(String extraMessage, User user, Course course, GUIAction backAction) {
        CourseInfoScreen newScreen = new CourseInfoScreen();
        newScreen.setUser(user);
        newScreen.setCourse(course);
        newScreen.initialize(new ArrayList<>(
                List.of(
                        backAction
                )
        ), extraMessage);

        return newScreen;
    }

    /**
     * Perform the Course Info Navigation Action by creating a new Screen and opening it.
     * Includes logic to deal with errors inherent in fetching the Course from the Course List. It also includes
     * logic to provide the Factory Method with the proper Back Action.
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

        // Deal with Actions coming from ScheduleScreen
        if (oldScreen instanceof ScheduleScreen castedScreen) {
            course = this.getPageIndex().getSoftware().getCourseListing()
                    .fetchCourse(castedScreen.getSelectedCourse());

            // Invalid Course Fetched
            if (course == null) {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.UNSUCCESSFUL_LOAD_COURSE_INFO_MSG,
                        this.getPageIndex());
                this.getPageIndex().setFrame(newScreen);
                return;
            }

            newScreen = GoToCourseInfo.getScreen(GUIConfig.EMPTY_MSG, user, course,
                    new GoToSchedule(GUIConfig.BACK_ACTION_NAME, this.getPageIndex()));

            // Deal with Actions coming from CourseListScreen
        } else if (oldScreen instanceof CourseListScreen castedScreen) {
            course = this.getPageIndex().getSoftware().getCourseListing()
                    .fetchCourse(castedScreen.getSelectedCourse());

            // Invalid Course Fetched
            if (course == null) {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.UNSUCCESSFUL_LOAD_COURSE_INFO_MSG,
                        this.getPageIndex());
                this.getPageIndex().setFrame(newScreen);
                return;
            }

            newScreen = GoToCourseInfo.getScreen(GUIConfig.EMPTY_MSG, user, course,
                    new GoToCourseList(GUIConfig.BACK_ACTION_NAME, this.getPageIndex()));

            // Deal with Actions coming from UserCourseListScreen
        } else if (oldScreen instanceof UserCourseListScreen castedScreen) {
            course = this.getPageIndex().getSoftware().getCourseListing()
                    .fetchCourse(castedScreen.getSelectedCourse());

            // Invalid Course Fetched
            if (course == null) {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.UNSUCCESSFUL_LOAD_COURSE_INFO_MSG,
                        this.getPageIndex());
                this.getPageIndex().setFrame(newScreen);
                return;
            }

            newScreen = GoToCourseInfo.getScreen(GUIConfig.EMPTY_MSG, user, course,
                    new GoToCourseList(GUIConfig.BACK_ACTION_NAME, this.getPageIndex()));

            // Deals with Invalid Actions
        } else {
            newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.UNSUCCESSFUL_LOAD_COURSE_INFO_MSG,
                    this.getPageIndex());
        }

        this.getPageIndex().setFrame(newScreen);
    }

}
