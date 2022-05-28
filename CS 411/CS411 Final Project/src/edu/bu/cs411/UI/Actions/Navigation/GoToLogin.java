package edu.bu.cs411.UI.Actions.Navigation;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.UI.Actions.Actual.Login;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.PageIndex;
import edu.bu.cs411.UI.Screens.Common.LoginScreen;
import edu.bu.cs411.UI.Screens.GUIScreen;

import java.awt.event.ActionEvent;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * Login Navigation Action Class.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class GoToLogin extends GUIAction {

    /**
     * Action Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Base Constructor for the Login Navigation Action.
     * Initializes the data required.
     *
     * @param displayName Action Display Name.
     * @param pageIndex   Reference to the overall GUI.
     */
    public GoToLogin(String displayName, PageIndex pageIndex) {
        super(displayName, pageIndex);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public GoToLogin() throws InstantiationException {
        super();
    }

    /**
     * Static Factory Method to return the Login Screen.
     *
     * @param extraMessage Error/Success Message to Display on the Screen.
     * @param pageIndex    Reference to the PageIndex Software.
     * @return Base Login Screen.
     */
    public static GUIScreen getScreen(String extraMessage, PageIndex pageIndex) {
        LoginScreen newScreen = new LoginScreen();
        newScreen.setUser(null);
        newScreen.initialize(new ArrayList<>(
                List.of(new Login(GUIConfig.LOGIN_ACTION_NAME, pageIndex))
        ), extraMessage);

        return newScreen;
    }

    /**
     * Perform the Login Navigation Action by creating a new Screen and opening it.
     *
     * @param e ActionEvent for the performed action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        GUIScreen newScreen = GoToLogin.getScreen(GUIConfig.EMPTY_MSG, this.getPageIndex());
        this.getPageIndex().setFrame(newScreen);
    }

}
