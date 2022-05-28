package edu.bu.cs411.UI.Screens.Admin;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.Screens.GUIScreen;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;

/**
 * Admin Navbar Screen Class.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class AdminNavScreen extends GUIScreen {

    /**
     * Screen Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Base Constructor for the Login GUI Screen.
     * Calls Super Constructor to do the heavy lifting.
     */
    public AdminNavScreen() {
        super(100, 100, 530, 290);
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
        JLabel title = new JLabel(GUIConfig.ADMIN_NAV_TITLE);
        title.setFont(new Font(GUIConfig.GUI_FONT, Font.PLAIN, GUIConfig.GUI_TEXT_SIZE_ONE));
        title.setBounds(10, 11, 464, 35);
        this.addComponent(title);

        JButton courseListBtn = new JButton(GUIConfig.VIEW_COURSE_LIST_ACTION_NAME);
        courseListBtn.setBounds(20, 81, 150, 23);
        courseListBtn.setAction(actions.get(0));
        this.addComponent(courseListBtn);

        JButton studentListBtn = new JButton(GUIConfig.VIEW_STUDENT_LIST_ACTION_NAME);
        studentListBtn.setBounds(182, 81, 150, 23);
        studentListBtn.setAction(actions.get(1));
        this.addComponent(studentListBtn);

        JButton professorListBtn = new JButton(GUIConfig.VIEW_PROFESSOR_LIST_ACTION_NAME);
        professorListBtn.setBounds(344, 81, 150, 23);
        professorListBtn.setAction(actions.get(2));
        this.addComponent(professorListBtn);

        JButton toggleRegistrationBtn = new JButton(GUIConfig.TOGGLE_CLOSE_ACTION_NAME);
        toggleRegistrationBtn.setBounds(20, 148, 150, 23);
        toggleRegistrationBtn.setAction(actions.get(3));
        this.addComponent(toggleRegistrationBtn);

        JButton changeSemesterBtn = new JButton(GUIConfig.MOVE_TO_NEXT_SEMESTER_ACTION_NAME);
        changeSemesterBtn.setBounds(182, 148, 150, 23);
        changeSemesterBtn.setAction(actions.get(4));
        this.addComponent(changeSemesterBtn);

        JLabel errorMessageField = new JLabel(GUIConfig.EMPTY_MSG + extraMessage);
        errorMessageField.setBounds(20, 192, 365, 14);
        this.addComponent(errorMessageField);

        JButton backBtn = new JButton(GUIConfig.LOGOUT_ACTION_NAME);
        backBtn.setBounds(349, 210, 125, 23);
        backBtn.setAction(actions.get(5));
        this.addComponent(backBtn);
    }

}
