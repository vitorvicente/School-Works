package edu.bu.cs411.UI.Actions.Actual;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.Actions.Navigation.GoToAdminNav;
import edu.bu.cs411.UI.PageIndex;
import edu.bu.cs411.UI.Screens.Admin.AdminNavScreen;
import edu.bu.cs411.UI.Screens.GUIScreen;
import edu.bu.cs411.Users.Admin;
import edu.bu.cs411.Users.User;

import java.awt.event.ActionEvent;
import java.io.Serial;

/**
 * Toggle Open and Close Registration Action Class.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class ToggleOpenCloseReg extends GUIAction {

    /**
     * Action Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Base constructor for the Toggle Open and Close Registration Action.
     * Initializes the data required.
     *
     * @param displayName Action Display Name.
     * @param pageIndex   Reference to the overall GUI.
     */
    public ToggleOpenCloseReg(String displayName, PageIndex pageIndex) {
        super(displayName, pageIndex);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public ToggleOpenCloseReg() throws InstantiationException {
        super();
    }

    /**
     * Perform the Toggle Open and Close Registration Action by calling the Backend functionality. It includes logic to
     * prevent errors and assure correct permissions level.
     * <p>
     * If the Action is called from a screen other than the AdminNavScreen, it fails and displays an error message on
     * screen.
     * If the User performing the Action isn't an Admin, it fails and displays an error message on screen.
     * <p>
     * If it succeeds, it toggles the overall status of Open/Close for Registration, displaying a success message.
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

        // Deal with Actions coming from AdminNavScreen
        if (oldScreen instanceof AdminNavScreen) {
            boolean toggle = !this.getPageIndex().getSoftware().getRegistrationDetails().isOverAllOpen();
            this.getPageIndex().getSoftware().getRegistrationDetails().setOverAllOpen(toggle);

            String toggleOpenCloseActionName = toggle ? GUIConfig.TOGGLE_CLOSE_ACTION_NAME :
                    GUIConfig.TOGGLE_OPEN_ACTION_NAME;
            newScreen = GoToAdminNav.getScreen(GUIConfig.SUCCESSFULLY_OPEN_OVERALL_MSG, this.getPageIndex(), user,
                    toggleOpenCloseActionName);

            // Deal with Invalid Actions
        } else {
            newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.UNABLE_TO_TOGGLE_REGISTRATION_MSG,
                    this.getPageIndex());
        }

        this.getPageIndex().setFrame(newScreen);
    }

}
