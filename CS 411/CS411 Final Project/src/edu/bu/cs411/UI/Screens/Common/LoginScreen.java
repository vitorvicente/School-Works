package edu.bu.cs411.UI.Screens.Common;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.Screens.GUIScreen;
import edu.bu.cs411.Users.Util.Credentials;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;

/**
 * Login Screen Class.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class LoginScreen extends GUIScreen {

    /**
     * Screen Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Login Form Username Field.
     */
    private JTextField usernameField;
    /**
     * Login Form Password Field.
     */
    private JPasswordField passwordField;

    /**
     * Base Constructor for the Login GUI Screen.
     * Calls Super Constructor to do the heavy lifting.
     */
    public LoginScreen() {
        super(100, 100, 464, 300);
    }

    /**
     * Initializer Method.
     * Meant to build the Content Pane appropriately.
     *
     * @param actions      ArrayList with all the Actions this GUI Screen will have available.
     * @param extraMessage Error/Success Message to display.
     */
    @Override
    public void initialize(ArrayList<GUIAction> actions, String extraMessage) {
        JLabel title = new JLabel(GUIConfig.GUI_TITLE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font(GUIConfig.GUI_FONT, Font.BOLD, GUIConfig.GUI_TEXT_SIZE_ONE));
        title.setBounds(10, 11, 414, 39);
        this.addComponent(title);

        JLabel usernameTitle = new JLabel(GUIConfig.USERNAME_FIELD_TITLE);
        usernameTitle.setFont(new Font(GUIConfig.GUI_FONT, Font.BOLD, GUIConfig.GUI_TEXT_SIZE_TWO));
        usernameTitle.setBounds(47, 70, 114, 14);
        this.addComponent(usernameTitle);

        usernameField = new JTextField();
        usernameField.setBounds(47, 95, 354, 20);
        this.addComponent(usernameField);
        usernameField.setColumns(10);

        JLabel passwordTitle = new JLabel(GUIConfig.PASSWORD_FIELD_TITLE);
        passwordTitle.setFont(new Font(GUIConfig.GUI_FONT, Font.BOLD, GUIConfig.GUI_TEXT_SIZE_TWO));
        passwordTitle.setBounds(47, 128, 114, 14);
        this.addComponent(passwordTitle);

        passwordField = new JPasswordField();
        passwordField.setBounds(47, 153, 354, 20);
        this.addComponent(passwordField);

        JLabel extraMessageField = new JLabel(GUIConfig.EMPTY_MSG + extraMessage);
        extraMessageField.setFont(new Font(GUIConfig.GUI_FONT, Font.PLAIN, GUIConfig.GUI_TEXT_SIZE_THREE));
        extraMessageField.setBounds(47, 184, 354, 14);
        this.addComponent(extraMessageField);

        JButton loginBtn = new JButton(GUIConfig.LOGIN_ACTION_NAME);
        loginBtn.setAction(actions.get(0));
        loginBtn.setBounds(47, 214, 89, 23);
        this.addComponent(loginBtn);
    }

    /**
     * Get a set of Credentials obtained from the Login Screen Form.
     *
     * @return Credentials Object filled with the information from the Login Screen Form.
     */
    public Credentials getCredentials() {
        return new Credentials(null, usernameField.getText(), passwordField.getPassword());
    }

}
