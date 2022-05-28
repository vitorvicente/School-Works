package edu.bu.cs411.UI.Screens.Common;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.Courses.CourseID;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.Screens.GUIScreen;
import edu.bu.cs411.Users.Student;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;

/**
 * Schedule Screen Class.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class ScheduleScreen extends GUIScreen {

    /**
     * Screen Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Actual Course List for the User.
     */
    private ArrayList<CourseID> courses;
    /**
     * Display String List for Courses.
     */
    private JList<String> displayCourseList;

    /**
     * Base Constructor for the Schedule GUI Screen.
     * Calls Super Constructor to do the heavy lifting.
     */
    public ScheduleScreen() {
        super(100, 100, 470, 310);
        this.courses = new ArrayList<>();
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
        JLabel title = new JLabel(GUIConfig.SCHEDULE_TITLE);
        title.setFont(new Font(GUIConfig.GUI_FONT, Font.PLAIN, GUIConfig.GUI_TEXT_SIZE_ONE));
        title.setBounds(10, 11, 165, 35);
        this.addComponent(title);

        JScrollPane displayCourseListScroll = new JScrollPane();
        displayCourseList = new JList<>();
        displayCourseList.setModel(new AbstractListModel<>() {
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
        displayCourseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        displayCourseListScroll.setBounds(20, 57, 171, 135);
        displayCourseListScroll.setViewportView(displayCourseList);
        displayCourseListScroll.setHorizontalScrollBar(null);
        this.addComponent(displayCourseListScroll);

        JButton classInfoBtn = new JButton(GUIConfig.VIEW_COURSE_INFO_ACTION_NAME);
        classInfoBtn.setBounds(238, 69, 165, 23);
        classInfoBtn.setAction(actions.get(0));
        this.addComponent(classInfoBtn);

        if (this.getUser() instanceof Student) {
            JButton registerForClassBtn = new JButton(GUIConfig.REGISTER_FOR_CLASS_ACTION_NAME);
            registerForClassBtn.setBounds(238, 117, 165, 23);
            registerForClassBtn.setAction(actions.get(1));
            this.addComponent(registerForClassBtn);

            JButton dropClassBtn = new JButton(GUIConfig.DROP_COURSE_ACTION_NAME);
            dropClassBtn.setBounds(238, 165, 165, 23);
            dropClassBtn.setAction(actions.get(2));
            this.addComponent(dropClassBtn);
        }

        JLabel extraMessageField = new JLabel(GUIConfig.EMPTY_MSG + extraMessage);
        extraMessageField.setBounds(38, 204, 365, 14);
        this.addComponent(extraMessageField);

        JButton backBtn = new JButton(GUIConfig.LOGOUT_ACTION_NAME);
        backBtn.setBounds(299, 227, 125, 23);
        backBtn.setAction(actions.get(3));
        this.addComponent(backBtn);

    }

    /**
     * Get the currently selected CourseID.
     *
     * @return Currently selected CourseID.
     */
    public CourseID getSelectedCourse() {
        if (this.displayCourseList.getSelectedIndex() == -1)
            return null;
        return this.courses.get(this.displayCourseList.getSelectedIndex());
    }

    /**
     * Sets the Course List to Display.
     *
     * @param courses Course List to Display.
     */
    public void setCourses(ArrayList<CourseID> courses) {
        this.courses = courses;
    }

    /**
     * Process the CourseID List into an Array of Strings to be displayed.
     *
     * @return Formatted CourseID String Array.
     */
    private String[] processValues() {
        String[] formattedValues = new String[this.courses.size()];

        for (int i = 0; i < this.courses.size(); i++)
            formattedValues[i] = this.courses.get(0).toString();

        return formattedValues;
    }

}
