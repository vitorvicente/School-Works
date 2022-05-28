package edu.bu.cs411.UI.Actions.Navigation;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.Courses.Course;
import edu.bu.cs411.Courses.CourseID;
import edu.bu.cs411.UI.Actions.Actual.AddCourseToUser;
import edu.bu.cs411.UI.Actions.Actual.CloseCourse;
import edu.bu.cs411.UI.Actions.Actual.OpenCourse;
import edu.bu.cs411.UI.Actions.Actual.RemoveCourse;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.PageIndex;
import edu.bu.cs411.UI.Screens.Common.CourseListScreen;
import edu.bu.cs411.UI.Screens.GUIScreen;
import edu.bu.cs411.Users.Admin;
import edu.bu.cs411.Users.Professor;
import edu.bu.cs411.Users.Student;
import edu.bu.cs411.Users.User;

import java.awt.event.ActionEvent;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * Course List Navigation Action Class.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class GoToCourseList extends GUIAction {

    /**
     * Action Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Base Constructor for the Course List Navigation Action.
     * Initializes the data required.
     *
     * @param displayName Action Display Name.
     * @param pageIndex   Reference to the overall GUI.
     */
    public GoToCourseList(String displayName, PageIndex pageIndex) {
        super(displayName, pageIndex);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public GoToCourseList() throws InstantiationException {
        super();
    }

    /**
     * Static Factory Method to return the Course List Screen.
     *
     * @param extraMessage Error/Success Message to Display on the Screen.
     * @param pageIndex    Reference to the PageIndex Software.
     * @param user         Logged-In User.
     * @param backAction   Specific Back Navigation Action for the Screen.
     * @return Base Course List Screen.
     */
    public static GUIScreen getScreen(String extraMessage, PageIndex pageIndex, User user, GUIAction backAction) {
        CourseListScreen newScreen = new CourseListScreen();

        ArrayList<CourseID> courses = new ArrayList<>();
        for (Course course : pageIndex.getSoftware().getCourseListing().getCourses())
            courses.add(course.getId());

        newScreen.setUser(user);
        newScreen.setCourses(courses);
        newScreen.initialize(new ArrayList<>(
                List.of(
                        new GoToCourseInfo(GUIConfig.VIEW_COURSE_INFO_ACTION_NAME, pageIndex),
                        new AddCourseToUser(GUIConfig.REGISTER_FOR_CLASS_ACTION_NAME, pageIndex),
                        new OpenCourse(GUIConfig.OPEN_COURSE_ACTION_NAME, pageIndex),
                        new CloseCourse(GUIConfig.CLOSE_COURSE_ACTION_NAME, pageIndex),
                        new GoToCourseEdit(GUIConfig.VIEW_EDIT_COURSE_ACTION_NAME, pageIndex),
                        new RemoveCourse(GUIConfig.REMOVE_COURSE_ACTION_NAME, pageIndex),
                        new GoToCourseAdd(GUIConfig.VIEW_ADD_COURSE_ACTION_NAME, pageIndex),
                        backAction
                )
        ), extraMessage);

        return newScreen;
    }

    /**
     * Perform the Course List Navigation Action by creating a new Screen and opening it.
     * It includes logic to provide the Factory Method with the proper Back Action.
     * <p>
     * If action is performed by an invalid User Type, it fails, displaying a message on screen.
     *
     * @param e ActionEvent for the performed action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        GUIScreen oldScreen = (GUIScreen) this.getPageIndex().getFrame();
        User user = oldScreen.getUser();
        GUIScreen newScreen;

        // Deal with Actions from Students and Professors
        if (user instanceof Student || user instanceof Professor) {
            newScreen = GoToCourseList.getScreen(GUIConfig.EMPTY_MSG, this.getPageIndex(), user,
                    new GoToSchedule(GUIConfig.BACK_ACTION_NAME, this.getPageIndex()));

            // Deal with Actions from Admins
        } else if (user instanceof Admin) {
            newScreen = GoToCourseList.getScreen(GUIConfig.EMPTY_MSG, this.getPageIndex(), user,
                    new GoToAdminNav(GUIConfig.BACK_ACTION_NAME, this.getPageIndex()));

            // Deals with Invalid Actions
        } else {
            newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.UNABLE_TO_NAVIGATE_MSG, this.getPageIndex());
        }

        this.getPageIndex().setFrame(newScreen);
    }

}
