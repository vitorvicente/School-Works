package edu.bu.cs411.UI.Screens.Common;

import edu.bu.cs411.Config.CoursesConfig;
import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.Courses.Course;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.Screens.GUIScreen;
import edu.bu.cs411.Users.Student;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;

/**
 * Course Info Screen Class.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class CourseInfoScreen extends GUIScreen {

    /**
     * Screen Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Course to Display Info of.
     */
    private Course course;

    /**
     * Base Constructor for the Login GUI Screen.
     * Calls Super Constructor to do the heavy lifting.
     */
    public CourseInfoScreen() {
        super(100, 100, 450, 296);
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
        JLabel title = new JLabel(GUIConfig.COURSE_INFO_TITLE + GUIConfig.EMPTY_MSG
                + this.getCourse().getId().toString());
        title.setFont(new Font(GUIConfig.GUI_FONT, Font.PLAIN, GUIConfig.GUI_TEXT_SIZE_ONE));
        title.setBounds(10, 11, 414, 35);
        this.addComponent(title);

        JLabel classStatusTitle = new JLabel(GUIConfig.COURSE_STATUS_TITLE);
        classStatusTitle.setFont(new Font(GUIConfig.GUI_FONT, Font.BOLD, GUIConfig.GUI_TEXT_SIZE_TWO));
        classStatusTitle.setBounds(20, 57, 70, 14);
        this.addComponent(classStatusTitle);

        JLabel classStatusField = new JLabel(GUIConfig.EMPTY_MSG + this.getCourse().getStatus().toString());
        classStatusField.setBounds(80, 57, 60, 14);
        this.addComponent(classStatusField);

        JLabel professorTitle = new JLabel(GUIConfig.COURSE_PROFESSOR_TITLE);
        professorTitle.setFont(new Font(GUIConfig.GUI_FONT, Font.BOLD, GUIConfig.GUI_TEXT_SIZE_TWO));
        professorTitle.setBounds(20, 82, 80, 14);
        this.addComponent(professorTitle);

        JLabel professorField = new JLabel(GUIConfig.EMPTY_MSG + this.getCourse().getProfessor().toString());
        professorField.setBounds(110, 82, 98, 14);
        this.addComponent(professorField);

        JLabel locationTitle = new JLabel(GUIConfig.COURSE_LOCATION_TITLE);
        locationTitle.setFont(new Font(GUIConfig.GUI_FONT, Font.BOLD, GUIConfig.GUI_TEXT_SIZE_TWO));
        locationTitle.setBounds(20, 107, 80, 14);
        this.addComponent(locationTitle);

        JLabel locationField = new JLabel(GUIConfig.EMPTY_MSG + this.getCourse().getLocation().toString());
        locationField.setBounds(110, 107, 98, 14);
        this.addComponent(locationField);

        JLabel scheduleTitle = new JLabel(GUIConfig.COURSE_SCHEDULE_TITLE);
        scheduleTitle.setFont(new Font(GUIConfig.GUI_FONT, Font.BOLD, GUIConfig.GUI_TEXT_SIZE_TWO));
        scheduleTitle.setBounds(20, 132, 80, 14);
        this.addComponent(scheduleTitle);

        JScrollPane scheduleFieldScroll = new JScrollPane();
        JList<String> scheduleField = new JList<>();
        scheduleField.setModel(new AbstractListModel<>() {
            @Serial
            private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;
            final String[] values = getCourse().getSchedule().toString().split(CoursesConfig.SCHEDULE_MAIN_SPLITTER);

            public int getSize() {
                return values.length;
            }

            public String getElementAt(int index) {
                return values[index];
            }
        });
        scheduleField.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scheduleFieldScroll.setBounds(30, 157, 154, 70);
        scheduleFieldScroll.setViewportView(scheduleField);
        scheduleFieldScroll.setHorizontalScrollBar(null);
        this.addComponent(scheduleFieldScroll);

        if (!(this.getUser() instanceof Student)) {
            JLabel studentListTitle = new JLabel(GUIConfig.STUDENT_LIST_TITLE);
            studentListTitle.setFont(new Font(GUIConfig.GUI_FONT, Font.BOLD, GUIConfig.GUI_TEXT_SIZE_TWO));
            studentListTitle.setBounds(263, 57, 98, 14);
            this.addComponent(studentListTitle);

            JScrollPane studentListScroll = new JScrollPane();
            JList<String> studentList = new JList<>();
            studentList.setModel(new AbstractListModel<>() {
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
            studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            studentListScroll.setBounds(263, 82, 140, 110);
            studentListScroll.setViewportView(studentList);
            studentListScroll.setHorizontalScrollBar(null);
            this.addComponent(studentListScroll);
        }

        JLabel extraMessageField = new JLabel(GUIConfig.EMPTY_MSG + extraMessage);
        extraMessageField.setBounds(10, 205, 365, 14);
        this.addComponent(extraMessageField);

        JButton backBtn = new JButton(GUIConfig.BACK_ACTION_NAME);
        backBtn.setBounds(299, 223, 125, 23);
        backBtn.setAction(actions.get(0));
        this.addComponent(backBtn);

    }

    /**
     * Get the current Course on Display.
     *
     * @return Current Course on Display
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Sets the current Course on Display.
     *
     * @param course Course to Display.
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Process the Student List into an Array of Strings to be displayed.
     *
     * @return Formatted Student String Array.
     */
    public String[] processValues() {
        String[] formattedValues = new String[this.getCourse().getStudents().size()];

        for (int i = 0; i < this.getCourse().getStudents().size(); i++)
            formattedValues[i] = this.getCourse().getStudents().get(i).toString();

        return formattedValues;
    }

}
