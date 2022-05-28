package edu.bu.cs411.UI.Screens.Common;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.Courses.CourseID;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.Screens.GUIScreen;
import edu.bu.cs411.Users.Admin;
import edu.bu.cs411.Users.Student;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;

/**
 * Course List Screen Class.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class CourseListScreen extends GUIScreen {

    /**
     * Screen Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * List of all Available Courses.
     */
    private ArrayList<CourseID> courses;
    /**
     * Display String List for Courses.
     */
    private JList<String> courseList;

    /**
     * Base Constructor for the Course List GUI Screen.
     * Calls Super Constructor to do the heavy lifting.
     */
    public CourseListScreen() {
        super(100, 100, 500, 558);
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
        JLabel title = new JLabel(GUIConfig.COURSE_LIST_TITLE);
        title.setFont(new Font(GUIConfig.GUI_FONT, Font.PLAIN, GUIConfig.GUI_TEXT_SIZE_ONE));
        title.setBounds(10, 11, 464, 35);
        this.addComponent(title);

        JScrollPane courseListScroll = new JScrollPane();
        courseList = new JList<>();
        courseList.setModel(new AbstractListModel<>() {
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
        courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        courseListScroll.setBounds(20, 56, 439, 219);
        courseListScroll.setViewportView(courseList);
        courseListScroll.setHorizontalScrollBar(null);
        this.addComponent(courseListScroll);

        JButton courseInfoBtn = new JButton(GUIConfig.VIEW_COURSE_INFO_ACTION_NAME);
        courseInfoBtn.setBounds(20, 301, 149, 23);
        courseInfoBtn.setAction(actions.get(0));
        this.addComponent(courseInfoBtn);

        if (this.getUser() instanceof Student) {
            JButton registerForCourseBtn = new JButton(GUIConfig.REGISTER_FOR_CLASS_ACTION_NAME);
            registerForCourseBtn.setBounds(310, 301, 149, 23);
            registerForCourseBtn.setAction(actions.get(1));
            this.addComponent(registerForCourseBtn);
        } else if (this.getUser() instanceof Admin) {
            JButton openCourseBtn = new JButton(GUIConfig.OPEN_COURSE_ACTION_NAME);
            openCourseBtn.setBounds(20, 335, 149, 23);
            openCourseBtn.setAction(actions.get(2));
            this.addComponent(openCourseBtn);

            JButton closeCourseBtn = new JButton(GUIConfig.CLOSE_COURSE_ACTION_NAME);
            closeCourseBtn.setBounds(310, 335, 149, 23);
            closeCourseBtn.setAction(actions.get(3));
            this.addComponent(closeCourseBtn);

            JButton editCourseBtn = new JButton(GUIConfig.VIEW_EDIT_COURSE_ACTION_NAME);
            editCourseBtn.setBounds(20, 369, 149, 23);
            editCourseBtn.setAction(actions.get(4));
            this.addComponent(editCourseBtn);

            JButton removeCourseBtn = new JButton(GUIConfig.REMOVE_COURSE_ACTION_NAME);
            removeCourseBtn.setBounds(310, 369, 149, 23);
            removeCourseBtn.setAction(actions.get(5));
            this.addComponent(removeCourseBtn);

            JButton addNewCourseBtn = new JButton(GUIConfig.ADD_COURSE_ACTION_NAME);
            addNewCourseBtn.setBounds(164, 412, 149, 23);
            addNewCourseBtn.setAction(actions.get(6));
            this.addComponent(addNewCourseBtn);
        }

        JLabel extraMessageField = new JLabel(GUIConfig.EMPTY_MSG + extraMessage);
        extraMessageField.setBounds(20, 460, 365, 14);
        this.addComponent(extraMessageField);

        JButton backBtn = new JButton(GUIConfig.BACK_ACTION_NAME);
        backBtn.setBounds(349, 485, 125, 23);
        backBtn.setAction(actions.get(7));
        this.addComponent(backBtn);

    }

    /**
     * Sets the List of Available Courses.
     *
     * @param courses List of Available Courses.
     */
    public void setCourses(ArrayList<CourseID> courses) {
        this.courses = courses;
    }

    /**
     * Gets the currently selected CourseID.
     *
     * @return Currently selected CourseID.
     */
    public CourseID getSelectedCourse() {
        if (this.courseList.getSelectedIndex() == -1)
            return null;
        return this.courses.get(this.courseList.getSelectedIndex());
    }

    /**
     * Process the CourseID List into an Array of Strings to be displayed.
     *
     * @return Formatted CourseID String Array.
     */
    private String[] processValues() {
        String[] formattedValues = new String[this.courses.size()];

        for (int i = 0; i < this.courses.size(); i++)
            formattedValues[i] = this.courses.get(i).toString();

        return formattedValues;
    }

}
