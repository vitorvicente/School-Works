package edu.bu.cs411.UI.Screens;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.Config.GeneralConfig;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.Actions.Navigation.*;
import edu.bu.cs411.UI.PageIndex;
import edu.bu.cs411.UI.Screens.Admin.*;
import edu.bu.cs411.UI.Screens.Common.CourseInfoScreen;
import edu.bu.cs411.UI.Screens.Common.CourseListScreen;
import edu.bu.cs411.UI.Screens.Common.LoginScreen;
import edu.bu.cs411.UI.Screens.Common.ScheduleScreen;
import edu.bu.cs411.Users.Admin;
import edu.bu.cs411.Users.Student;
import edu.bu.cs411.Users.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;

/**
 * Abstract Class to represent all GUI Screens.
 * Includes basic functionality shared by all Screen, as well as the base shared data.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public abstract class GUIScreen extends JFrame {

    /**
     * Screen Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Logged-In User.
     */
    private User user;

    /**
     * Base Constructor for the Abstract GUI Screen.
     * Initializes the Screen with a Blank Content Pane with the appropriate dimensions.
     *
     * @param x      Coordinate from the top-left of the Frame.
     * @param y      Coordinate from the top-left of the Frame.
     * @param width  Width of the Frame.
     * @param height Height of the Frame.
     */
    public GUIScreen(int x, int y, int width, int height) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(x, y, width, height);
        JPanel jP = new JPanel();
        jP.setBorder(new EmptyBorder(GUIConfig.BORDER_TOP, GUIConfig.BORDER_LEFT, GUIConfig.BORDER_BOTTOM,
                GUIConfig.BORDER_RIGHT));
        jP.setLayout(null);
        this.setContentPane(jP);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public GUIScreen() throws InstantiationException {
        throw new InstantiationException(GeneralConfig.ILLEGAL_EMPTY_CONSTRUCTOR_ERROR);
    }

    /**
     * Static Helper Method to go generate the old Screen again, with a new Message.
     *
     * @param screen    Instance of the Old Screen.
     * @param message   New Message to display.
     * @param pageIndex Reference to the PageIndex software.
     * @return New Instance of the Old Screen with a new Message.
     */
    public static GUIScreen goToOldScreen(GUIScreen screen, String message, PageIndex pageIndex) {
        if (screen instanceof CourseInfoScreen castedScreen) {
            if (castedScreen.getUser() instanceof Admin)
                return GoToCourseInfo.getScreen(message, castedScreen.getUser(), castedScreen.getCourse(),
                        new GoToCourseList(GUIConfig.BACK_ACTION_NAME, pageIndex));
            else
                return GoToCourseInfo.getScreen(message, castedScreen.getUser(), castedScreen.getCourse(),
                        new GoToSchedule(GUIConfig.BACK_ACTION_NAME, pageIndex));
        } else if (screen instanceof CourseListScreen castedScreen) {
            if (castedScreen.getUser() instanceof Admin)
                return GoToCourseList.getScreen(message, pageIndex, castedScreen.getUser(),
                        new GoToAdminNav(GUIConfig.BACK_ACTION_NAME, pageIndex));
            else
                return GoToCourseList.getScreen(message, pageIndex, castedScreen.getUser(),
                        new GoToSchedule(GUIConfig.BACK_ACTION_NAME, pageIndex));
        } else if (screen instanceof ScheduleScreen castedScreen) {
            return GoToSchedule.getScreen(message, pageIndex, castedScreen.getUser());
        } else if (screen instanceof AdminNavScreen castedScreen) {
            String toggleOpenCloseActionName = pageIndex.getSoftware().getRegistrationDetails().isOverAllOpen() ?
                    GUIConfig.TOGGLE_CLOSE_ACTION_NAME : GUIConfig.TOGGLE_OPEN_ACTION_NAME;
            return GoToAdminNav.getScreen(message, pageIndex, castedScreen.getUser(), toggleOpenCloseActionName);
        } else if (screen instanceof CourseAddScreen castedScreen) {
            return GoToCourseAdd.getScreen(message, pageIndex, castedScreen.getUser());
        } else if (screen instanceof CourseEditScreen castedScreen) {
            return GoToCourseEdit.getScreen(message, castedScreen.getUser(), pageIndex, castedScreen.getCourse(),
                    new GoToCourseList(GUIConfig.BACK_ACTION_NAME, pageIndex));
        } else if (screen instanceof UserCourseAddScreen castedScreen) {
            return GoToUserCourseAdd.getScreen(message, pageIndex, castedScreen.getUser(),
                    castedScreen.getSecondaryUser(), new GoToUserCourseList(GUIConfig.BACK_ACTION_NAME, pageIndex));
        } else if (screen instanceof UserCourseListScreen castedScreen) {
            boolean studentsOrProfessors =
                    pageIndex.getSoftware().getUserListing()
                            .fetchUser(castedScreen.getSecondaryUser()) instanceof Student;
            return GoToUserCourseList.getScreen(message, pageIndex, castedScreen.getUser(),
                    castedScreen.getSecondaryUser(),
                    new GoToUserList(GUIConfig.BACK_ACTION_NAME, pageIndex, studentsOrProfessors));
        } else if (screen instanceof UserListScreen castedScreen) {
            return GoToUserList.getScreen(message, pageIndex, castedScreen.getUser(),
                    castedScreen.getStudentsOrProfessors());
        } else if (screen instanceof LoginScreen) {
            return GoToLogin.getScreen(message, pageIndex);
        }

        return GoToLogin.getScreen(GUIConfig.UNABLE_TO_NAVIGATE_MSG, pageIndex);
    }

    /**
     * Abstract Initializer Method.
     * Meant to build the Content Pane appropriately.
     *
     * @param actions      ArrayList with all the Actions this GUI Screen will have available.
     * @param extraMessage Error/Success Message to display.
     */
    public abstract void initialize(ArrayList<GUIAction> actions, String extraMessage);

    /**
     * Helper Method to add AWT Components to the Content Pane.
     *
     * @param component AWT Component to add.
     */
    public void addComponent(Component component) {
        this.getContentPane().add(component);
    }

    /**
     * Get the Reference to the currently Logged-In User.
     *
     * @return Reference to the currently Logged-In User.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the Reference to the currently Logged-In User.
     *
     * @param user Reference to the currently Logged-In User.
     */
    public void setUser(User user) {
        this.user = user;
    }

}
