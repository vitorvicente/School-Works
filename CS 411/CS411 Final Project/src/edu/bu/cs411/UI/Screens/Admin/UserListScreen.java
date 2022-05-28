package edu.bu.cs411.UI.Screens.Admin;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.Screens.GUIScreen;
import edu.bu.cs411.Users.Util.UniqueID;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;

/**
 * User List Screen Class.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class UserListScreen extends GUIScreen {

    /**
     * Screen Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;
    /**
     * Actual List of Users.
     */
    private ArrayList<UniqueID> users;
    /**
     * Whether the Screen is Displaying a List of Students or Professors.
     */
    private boolean studentsOrProfessors;
    /**
     * Display String List for Users.
     */
    private JList<String> userList;

    /**
     * Base Constructor for the User List GUI Screen.
     * Calls Super Constructor to do the heavy lifting.
     */
    public UserListScreen() {
        super(100, 100, 500, 433);
        this.users = new ArrayList<>();
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
        String titleText = this.getStudentsOrProfessors() ? "Student" : "Professor";
        JLabel title = new JLabel(titleText + GUIConfig.USER_LIST_TITLE);
        title.setFont(new Font(GUIConfig.GUI_FONT, Font.PLAIN, GUIConfig.GUI_TEXT_SIZE_ONE));
        title.setBounds(10, 11, 464, 35);
        this.addComponent(title);


        JScrollPane userListScroll = new JScrollPane();
        userList = new JList<>();
        userList.setModel(new AbstractListModel<>() {
            @Serial
            private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;
            final String[] values = processValues();

            public int getSize() {
                return values.length;
            }

            public String getElementAt(int index) {
                return values[index];
            }
        });
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userListScroll.setBounds(20, 56, 439, 219);
        userListScroll.setViewportView(userList);
        userListScroll.setHorizontalScrollBar(null);
        this.addComponent(userListScroll);

        JButton btnUserInfo = new JButton(GUIConfig.VIEW_USER_COURSE_LIST_ACTION_NAME);
        btnUserInfo.setBounds(32, 296, 150, 23);
        btnUserInfo.setAction(actions.get(0));
        this.addComponent(btnUserInfo);

        JLabel errorMessageField = new JLabel("" + extraMessage);
        errorMessageField.setBounds(10, 335, 365, 14);
        this.addComponent(errorMessageField);

        JButton backBtn = new JButton(GUIConfig.BACK_ACTION_NAME);
        backBtn.setBounds(349, 360, 125, 23);
        backBtn.setAction(actions.get(1));
        this.addComponent(backBtn);
    }

    /**
     * Gets currently selected UniqueID.
     *
     * @return Currently selected UniqueID.
     */
    public UniqueID getSelectedUser() {
        if (this.userList.getSelectedIndex() == -1)
            return null;
        return this.users.get(this.userList.getSelectedIndex());
    }

    /**
     * Gets whether the Screen is Displaying a List of Students or Professors.
     *
     * @return Whether the Screen is Displaying a List of Students or Professors.
     */
    public boolean getStudentsOrProfessors() {
        return this.studentsOrProfessors;
    }

    /**
     * Gets whether the Screen is Displaying a List of Students or Professors.
     *
     * @param getStudentsOrProfessors Whether the Screen is Displaying a List of Students or Professors.
     */
    public void setStudentsOrProfessors(boolean getStudentsOrProfessors) {
        this.studentsOrProfessors = getStudentsOrProfessors;
    }

    /**
     * Sets the List of Users to Display.
     *
     * @param users List of Users to Display.
     */
    public void setUsers(ArrayList<UniqueID> users) {
        this.users = users;
    }

    /**
     * Process the UniqueID List into an Array of Strings to be displayed.
     *
     * @return Formatted UniqueID String Array.
     */
    public String[] processValues() {
        String[] formattedValues = new String[this.users.size()];

        for (int i = 0; i < this.users.size(); i++)
            formattedValues[i] = this.users.get(i).toString();

        return formattedValues;
    }

}
