package edu.bu.cs411.UI.Actions.Navigation;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.UI.Actions.Actual.RemoveCourseFromUser;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.PageIndex;
import edu.bu.cs411.UI.Screens.Common.ScheduleScreen;
import edu.bu.cs411.UI.Screens.GUIScreen;
import edu.bu.cs411.Users.Professor;
import edu.bu.cs411.Users.Student;
import edu.bu.cs411.Users.User;

import java.awt.event.ActionEvent;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * Schedule Navigation Action Class.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class GoToSchedule extends GUIAction {

    /**
     * Action Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Base Constructor for the Schedule Navigation Action.
     * Initializes the data required.
     *
     * @param displayName Action Display Name.
     * @param pageIndex   Reference to the overall GUI.
     */
    public GoToSchedule(String displayName, PageIndex pageIndex) {
        super(displayName, pageIndex);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public GoToSchedule() throws InstantiationException {
        super();
    }

    /**
     * Static Factory Method to return the Schedule Screen.
     *
     * @param extraMessage Error/Success Message to Display on the Screen.
     * @param pageIndex    Reference to the PageIndex Software.
     * @param user         Logged-In User.
     * @return Base Schedule Screen.
     */
    public static GUIScreen getScreen(String extraMessage, PageIndex pageIndex, User user) {
        ScheduleScreen newScreen = new ScheduleScreen();
        newScreen.setUser(user);

        if (user instanceof Student castedUser)
            newScreen.setCourses(castedUser.getCoursesRegisteredFor());
        else if (user instanceof Professor castedUser)
            newScreen.setCourses(castedUser.getCoursesTeaching());

        newScreen.initialize(new ArrayList<>(
                List.of(
                        new GoToCourseInfo(GUIConfig.VIEW_COURSE_INFO_ACTION_NAME, pageIndex),
                        new RemoveCourseFromUser(GUIConfig.DROP_COURSE_ACTION_NAME, pageIndex),
                        new GoToCourseList(GUIConfig.VIEW_CLASSES_TO_REGISTER_ACTION_NAME, pageIndex),
                        new GoToLogin(GUIConfig.LOGOUT_ACTION_NAME, pageIndex)
                )
        ), extraMessage);

        return newScreen;
    }

    /**
     * Perform the Schedule Navigation Action by creating a new Screen and opening it.
     *
     * @param e ActionEvent for the performed action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        User user = ((GUIScreen) this.getPageIndex().getFrame()).getUser();
        GUIScreen newScreen = GoToSchedule.getScreen(GUIConfig.EMPTY_MSG, this.getPageIndex(), user);
        this.getPageIndex().setFrame(newScreen);
    }

}
