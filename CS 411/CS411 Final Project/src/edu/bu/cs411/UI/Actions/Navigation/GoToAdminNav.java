package edu.bu.cs411.UI.Actions.Navigation;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.UI.Actions.Actual.MoveToNextSemester;
import edu.bu.cs411.UI.Actions.Actual.ToggleOpenCloseReg;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.PageIndex;
import edu.bu.cs411.UI.Screens.Admin.AdminNavScreen;
import edu.bu.cs411.UI.Screens.GUIScreen;
import edu.bu.cs411.Users.Admin;
import edu.bu.cs411.Users.User;

import java.awt.event.ActionEvent;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * AdminNav Navigation Action Class.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class GoToAdminNav extends GUIAction {

    /**
     * Action Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Base Constructor for the AdminNav Navigation Action.
     * Initializes the data required.
     *
     * @param displayName Action Display Name.
     * @param pageIndex   Reference to the overall GUI.
     */
    public GoToAdminNav(String displayName, PageIndex pageIndex) {
        super(displayName, pageIndex);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public GoToAdminNav() throws InstantiationException {
        super();
    }

    /**
     * Static Factory Method to return the AdminNav Screen.
     *
     * @param extraMessage              Error/Success Message to Display on the Screen.
     * @param pageIndex                 Reference to the PageIndex Software.
     * @param user                      Logged-In User.
     * @param toggleOpenCloseActionName Whether the Toggle Open/Close Action Button is to Open or Close.
     * @return Base AdminNav Screen.
     */
    public static GUIScreen getScreen(String extraMessage, PageIndex pageIndex, User user,
                                      String toggleOpenCloseActionName) {
        AdminNavScreen newScreen = new AdminNavScreen();
        newScreen.setUser(user);
        newScreen.initialize(new ArrayList<>(
                Arrays.asList(
                        new GoToCourseList(GUIConfig.VIEW_COURSE_LIST_ACTION_NAME, pageIndex),
                        new GoToUserList(GUIConfig.VIEW_STUDENT_LIST_ACTION_NAME, pageIndex,
                                GUIConfig.SHOW_STUDENTS),
                        new GoToUserList(GUIConfig.VIEW_PROFESSOR_LIST_ACTION_NAME, pageIndex,
                                GUIConfig.SHOW_PROFESSORS),
                        new ToggleOpenCloseReg(toggleOpenCloseActionName, pageIndex),
                        new MoveToNextSemester(GUIConfig.MOVE_TO_NEXT_SEMESTER_ACTION_NAME, pageIndex),
                        new GoToLogin(GUIConfig.LOGOUT_ACTION_NAME, pageIndex)
                )
        ), extraMessage);

        return newScreen;
    }

    /**
     * Perform the AdminNav Navigation Action by creating a new Screen and opening it.
     * <p>
     * If the Logged-In User isn't an Admin, it fails and displays an error message on screen.
     *
     * @param e ActionEvent for the performed action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        GUIScreen oldScreen = (GUIScreen) this.getPageIndex().getFrame();
        User user = oldScreen.getUser();
        String toggleOpenCloseActionName = this.getPageIndex().getSoftware().getRegistrationDetails().isOverAllOpen() ?
                GUIConfig.TOGGLE_CLOSE_ACTION_NAME : GUIConfig.TOGGLE_OPEN_ACTION_NAME;
        GUIScreen newScreen;

        // Checks permissions
        if (!(user instanceof Admin)) {
            newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.NO_PERMISSIONS_MSG, this.getPageIndex());
            this.getPageIndex().setFrame(newScreen);
            return;
        }

        newScreen = GoToAdminNav.getScreen(GUIConfig.EMPTY_MSG, this.getPageIndex(), user,
                toggleOpenCloseActionName);
        this.getPageIndex().setFrame(newScreen);
    }

}
