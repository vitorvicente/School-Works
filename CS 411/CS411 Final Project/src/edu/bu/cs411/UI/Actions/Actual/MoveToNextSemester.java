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
 * Move To Next Semester Action Class.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class MoveToNextSemester extends GUIAction {

    /**
     * Action Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Base constructor for the Move To Next Semester Action.
     * Initializes the data required.
     *
     * @param displayName Action Display Name.
     * @param pageIndex   Reference to the overall GUI.
     */
    public MoveToNextSemester(String displayName, PageIndex pageIndex) {
        super(displayName, pageIndex);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public MoveToNextSemester() throws InstantiationException {
        super();
    }

    /**
     * Perform the Move To Next Semester Action by calling the Backend functionality. It includes logic to prevent
     * errors and assure correct permissions level.
     * <p>
     * If the Action is called from a screen other than the AdminNavScreen, it fails and displays an error message on
     * screen.
     * If the User performing the Action isn't an Admin, it fails and displays an error message on screen.
     * <p>
     * If it succeeds, it moves the entire Registration to the next semester, and clears all class registration data,
     * displaying a success message.
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
            this.getPageIndex().getSoftware().getRegistrationDetails().moveToNextSemester(
                    this.getPageIndex().getSoftware().getUserListing(),
                    this.getPageIndex().getSoftware().getCourseListing());

            String toggleOpenCloseActionName = this.getPageIndex().getSoftware().getRegistrationDetails().isOverAllOpen()
                    ? GUIConfig.TOGGLE_CLOSE_ACTION_NAME : GUIConfig.TOGGLE_OPEN_ACTION_NAME;
            newScreen = GoToAdminNav.getScreen(GUIConfig.SUCCESSFULLY_MOVED_TO_NEXT_SEM_MSG, this.getPageIndex(), user,
                    toggleOpenCloseActionName);

            // Deal with Invalid Actions
        } else {
            newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.UNABLE_TO_MOVE_TO_NEXT_SEM_MSG,
                    this.getPageIndex());
        }

        this.getPageIndex().setFrame(newScreen);
    }

}
