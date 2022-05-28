package edu.bu.cs411.UI.Actions.Actual;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.Actions.Navigation.GoToAdminNav;
import edu.bu.cs411.UI.Actions.Navigation.GoToSchedule;
import edu.bu.cs411.UI.PageIndex;
import edu.bu.cs411.UI.Screens.Common.LoginScreen;
import edu.bu.cs411.UI.Screens.GUIScreen;
import edu.bu.cs411.Users.Admin;
import edu.bu.cs411.Users.Professor;
import edu.bu.cs411.Users.Student;
import edu.bu.cs411.Users.User;
import edu.bu.cs411.Users.Util.Credentials;

import java.awt.event.ActionEvent;
import java.io.Serial;

/**
 * Login Action Class.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class Login extends GUIAction {

    /**
     * Action Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Base constructor for the Login Action.
     * Initializes the data required.
     *
     * @param displayName Action Display Name.
     * @param pageIndex   Reference to the overall GUI.
     */
    public Login(String displayName, PageIndex pageIndex) {
        super(displayName, pageIndex);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public Login() throws InstantiationException {
        super();
    }

    /**
     * Perform the Login Action by calling the UserListing Backend Object, and working accordingly.
     * Includes logic to deal with the login of different types of Users.
     * <p>
     * If the Action is called from a screen other than the LoginScreen, it fails and displays an error message on
     * screen.
     * If an invalid or null Login is able to be retrieved, it fails and displays an error message on screen.
     * <p>
     * If it succeeds moves User to proper screen:
     * - Professor || Student -> Schedule.
     * - Admin -> Admin Nav.
     *
     * @param e ActionEvent for the performed action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        GUIScreen oldScreen = (GUIScreen) this.getPageIndex().getFrame();
        GUIScreen newScreen;

        // Deal with Actions coming from LoginScreen
        if (oldScreen instanceof LoginScreen castedScreen) {
            Credentials cred = castedScreen.getCredentials();
            User user = this.getPageIndex().getSoftware().getUserListing().login(cred);

            // Deal with Invalid User
            if (user == null) {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.UNSUCCESSFUL_LOGIN_MSG, this.getPageIndex());
                this.getPageIndex().setFrame(newScreen);
                return;
            }

            // Deal with Student or Professor Login
            if (user instanceof Student || user instanceof Professor) {
                newScreen = GoToSchedule.getScreen(GUIConfig.EMPTY_MSG, this.getPageIndex(), user);

                // Deal with Admin Login
            } else if (user instanceof Admin) {
                String toggleOpenCloseActionName = this.getPageIndex().getSoftware().getRegistrationDetails().isOverAllOpen() ?
                        GUIConfig.TOGGLE_CLOSE_ACTION_NAME : GUIConfig.TOGGLE_OPEN_ACTION_NAME;

                newScreen = GoToAdminNav.getScreen(GUIConfig.EMPTY_MSG, this.getPageIndex(), user,
                        toggleOpenCloseActionName);

                // Deal with Illegal User Type
            } else {
                newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.ILLEGAL_USER_TYPE_MSG, this.getPageIndex());
            }

            // Deals with Invalid Actions
        } else {
            newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.UNABLE_TO_LOG_USER_IN_MSG, this.getPageIndex());
        }

        this.getPageIndex().setFrame(newScreen);
    }

}
