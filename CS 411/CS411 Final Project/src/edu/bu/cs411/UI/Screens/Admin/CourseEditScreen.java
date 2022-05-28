package edu.bu.cs411.UI.Screens.Admin;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.Courses.Course;
import edu.bu.cs411.Courses.Util.BuildingCode;
import edu.bu.cs411.Courses.Util.Location;
import edu.bu.cs411.Courses.Util.Schedule;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.Screens.GUIScreen;
import edu.bu.cs411.Users.Util.UniqueID;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;

/**
 * Course Add Screen Class.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class CourseEditScreen extends GUIScreen {

    /**
     * Screen Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Course Being Edited.
     */
    private Course course;

    /**
     * Form Max Capacity Field.
     */
    private JTextField maxCapacity;
    /**
     * Form Room Number Field.
     */
    private JTextField roomNumber;
    /**
     * Form Professor List.
     */
    private JList<String> professorList;
    /**
     * Form Day List.
     */
    private JList<String> dayList;
    /**
     * Form Start Time List.
     */
    private JList<String> startTimeList;
    /**
     * Form End Time List.
     */
    private JList<String> endTimeList;
    /**
     * Form Building Code List.
     */
    private JList<String> buildingList;

    /**
     * Actual List of Professors.
     */
    private ArrayList<UniqueID> professors;
    /**
     * Actual List of Days.
     */
    private ArrayList<DayOfWeek> days;
    /**
     * Actual List of Times.
     */
    private ArrayList<Time> times;
    /**
     * Actual List of Building Codes.
     */
    private ArrayList<BuildingCode> buildingCodes;

    /**
     * Base Constructor for the Login GUI Screen.
     * Calls Super Constructor to do the heavy lifting.
     */
    public CourseEditScreen() {
        super(100, 100, 500, 630);
        this.initVariables();
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
        JLabel title = new JLabel(GUIConfig.EDIT_COURSE_TITLE);
        title.setFont(new Font(GUIConfig.GUI_FONT, Font.PLAIN, GUIConfig.GUI_TEXT_SIZE_ONE));
        title.setBounds(10, 11, 464, 35);
        this.addComponent(title);

        JLabel professorListTitle = new JLabel(GUIConfig.PROFESSORS_FIELD_TITLE);
        professorListTitle.setBounds(20, 57, 65, 14);
        this.addComponent(professorListTitle);

        JScrollPane professorListScroll = new JScrollPane();
        professorList = new JList<>();
        professorList.setModel(new AbstractListModel<>() {
            @Serial
            private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;
            final String[] values = processValuesProfessors();

            public int getSize() {
                return values.length;
            }

            public String getElementAt(int index) {
                return values[index];
            }
        });
        professorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        professorListScroll.setBounds(30, 82, 244, 110);
        professorListScroll.setViewportView(professorList);
        professorListScroll.setHorizontalScrollBar(null);
        this.addComponent(professorListScroll);

        JLabel maxCapacityTitle = new JLabel(GUIConfig.MAX_CAPACITY_FIELD_TITLE);
        maxCapacityTitle.setBounds(317, 57, 108, 14);
        this.addComponent(maxCapacityTitle);

        maxCapacity = new JTextField();
        maxCapacity.setColumns(10);
        maxCapacity.setBounds(327, 80, 86, 20);
        this.addComponent(maxCapacity);

        JLabel daysOfWeekTitle = new JLabel(GUIConfig.DAYS_FIELD_TITLE);
        daysOfWeekTitle.setBounds(20, 163, 120, 110);
        this.addComponent(daysOfWeekTitle);

        JScrollPane dayListScroll = new JScrollPane();
        dayList = new JList<>();
        dayList.setModel(new AbstractListModel<>() {
            @Serial
            private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;
            final String[] values = processValuesDays();

            public int getSize() {
                return values.length;
            }

            public String getElementAt(int index) {
                return values[index];
            }
        });
        dayListScroll.setBounds(30, 236, 86, 110);
        dayListScroll.setViewportView(dayList);
        dayListScroll.setHorizontalScrollBar(null);
        this.addComponent(dayListScroll);

        JLabel startTimeTitle = new JLabel(GUIConfig.START_TIME_FIELD_TITLE);
        startTimeTitle.setBounds(177, 211, 86, 14);
        this.addComponent(startTimeTitle);

        JScrollPane startTimeListScroll = new JScrollPane();
        startTimeList = new JList<>();
        startTimeList.setModel(new AbstractListModel<>() {
            @Serial
            private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;
            final String[] values = processValuesTimes();

            public int getSize() {
                return values.length;
            }

            public String getElementAt(int index) {
                return values[index];
            }
        });
        startTimeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        startTimeListScroll.setBounds(188, 236, 86, 110);
        startTimeListScroll.setViewportView(startTimeList);
        startTimeListScroll.setHorizontalScrollBar(null);
        this.addComponent(startTimeListScroll);

        JLabel endTimeTitle = new JLabel(GUIConfig.END_TIME_FIELD_TITLE);
        endTimeTitle.setBounds(317, 211, 86, 14);
        this.addComponent(endTimeTitle);

        JScrollPane endTimeListScroll = new JScrollPane();
        endTimeList = new JList<>();
        endTimeList.setModel(new AbstractListModel<>() {
            @Serial
            private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;
            final String[] values = processValuesTimes();

            public int getSize() {
                return values.length;
            }

            public String getElementAt(int index) {
                return values[index];
            }
        });
        endTimeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        endTimeListScroll.setBounds(327, 236, 86, 110);
        endTimeListScroll.setViewportView(endTimeList);
        endTimeListScroll.setHorizontalScrollBar(null);
        this.addComponent(endTimeListScroll);

        JLabel buildingCodeTitle = new JLabel(GUIConfig.BUILDING_FIELD_TITLE);
        buildingCodeTitle.setBounds(20, 372, 65, 14);
        this.addComponent(buildingCodeTitle);

        JScrollPane buildingListScroll = new JScrollPane();
        buildingList = new JList<>();
        buildingList.setModel(new AbstractListModel<>() {
            @Serial
            private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;
            final String[] values = processValuesBuildings();

            public int getSize() {
                return values.length;
            }

            public String getElementAt(int index) {
                return values[index];
            }
        });
        buildingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        buildingListScroll.setBounds(30, 397, 244, 110);
        buildingListScroll.setViewportView(buildingList);
        buildingListScroll.setHorizontalScrollBar(null);
        this.addComponent(buildingListScroll);

        JLabel roomNumberTitle = new JLabel(GUIConfig.ROOM_NUMBER_FIELD_TITLE);
        roomNumberTitle.setBounds(317, 372, 108, 14);
        this.addComponent(roomNumberTitle);

        roomNumber = new JTextField();
        roomNumber.setColumns(10);
        roomNumber.setBounds(327, 395, 86, 20);
        this.addComponent(roomNumber);

        JLabel extraMessageField = new JLabel("" + extraMessage);
        extraMessageField.setBounds(20, 532, 365, 14);
        this.addComponent(extraMessageField);

        JButton saveBtn = new JButton(GUIConfig.SAVE_COURSE_ACTION_NAME);
        saveBtn.setBounds(10, 557, 125, 23);
        saveBtn.setAction(actions.get(0));
        this.addComponent(saveBtn);

        JButton backBtn = new JButton(GUIConfig.BACK_ACTION_NAME);
        backBtn.setBounds(349, 557, 125, 23);
        backBtn.setAction(actions.get(1));
        this.addComponent(backBtn);
    }

    /**
     * Gets the formatted Course obtained from the Submission Form.
     *
     * @return Formatted Course from the Submission Form.
     */
    public Course getCourse() {
        Course course = this.course;

        if (this.professorList.getSelectedIndex() != -1)
            course.setProfessor(this.professors.get(this.professorList.getSelectedIndex()));

        if (this.isNumeric(this.maxCapacity.getText()))
            course.setMaxCapacity(Integer.getInteger(this.maxCapacity.getText()));

        Schedule schedule = course.getSchedule();
        ArrayList<DayOfWeek> days = new ArrayList<>();
        if (this.dayList.getSelectedIndex() != -1) {
            for (Integer i : this.dayList.getSelectedIndices())
                days.add(this.days.get(i));
            schedule.setDays(days);
        }
        if (this.startTimeList.getSelectedIndex() != -1)
            schedule.setStartTime(this.times.get(this.startTimeList.getSelectedIndex()));
        if (this.endTimeList.getSelectedIndex() != -1)
            schedule.setEndTime(this.times.get(this.endTimeList.getSelectedIndex()));
        if (schedule.getStartTime().after(schedule.getEndTime()))
            schedule.setEndTime(new Time(schedule.getStartTime().getHours() + 1, schedule.getStartTime().getMinutes(),
                    0));
        course.setSchedule(schedule);

        Location location = course.getLocation();
        if (this.buildingList.getSelectedIndex() != -1)
            location.setBuildingCode(this.buildingCodes.get(this.buildingList.getSelectedIndex()));
        if (this.isNumeric(this.roomNumber.getText()))
            location.setRoom(Integer.parseInt(this.roomNumber.getText()));
        course.setLocation(location);

        return course;
    }

    /**
     * Sets the Course currently being Edited.
     *
     * @param course Course to be Edited.
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Sets the List of Available Professors.
     *
     * @param professors List of Available Professors.
     */
    public void setProfessors(ArrayList<UniqueID> professors) {
        this.professors = professors;
    }

    /**
     * Initializes all Class Variables.
     */
    private void initVariables() {
        this.buildingCodes = new ArrayList<>(java.util.List.of(BuildingCode.values()));
        this.days = new ArrayList<>(java.util.List.of(DayOfWeek.values()));
        this.times = new ArrayList<>();
        this.professors = new ArrayList<>();

        boolean breakable = true;
        Time pastTime = new Time(8, 0, 0);
        while (breakable) {
            times.add((Time) pastTime.clone());

            if (pastTime.getMinutes() == 45) {
                pastTime.setMinutes(0);
                pastTime.setHours(pastTime.getHours() + 1);
            } else
                pastTime.setMinutes(pastTime.getMinutes() + 15);

            if (pastTime.getHours() == 20)
                breakable = false;

        }
    }

    /**
     * Process the Professor List into an Array of Strings to be displayed.
     *
     * @return Formatted Professor String Array.
     */
    public String[] processValuesProfessors() {
        String[] formattedValues = new String[this.professors.size()];

        for (int i = 0; i < this.professors.size(); i++)
            formattedValues[i] = this.professors.get(i).toString();

        return formattedValues;
    }

    /**
     * Process the Day List into an Array of Strings to be displayed.
     *
     * @return Formatted Day String Array.
     */
    public String[] processValuesDays() {
        String[] formattedValues = new String[this.days.size()];

        for (int i = 0; i < this.days.size(); i++)
            formattedValues[i] = this.days.get(i).toString();

        return formattedValues;
    }

    /**
     * Process the Time List into an Array of Strings to be displayed.
     *
     * @return Formatted Time String Array.
     */
    public String[] processValuesTimes() {
        String[] formattedValues = new String[this.times.size()];

        for (int i = 0; i < this.times.size(); i++)
            formattedValues[i] = this.times.get(i).toString();

        return formattedValues;
    }

    /**
     * Process the Building Code List into an Array of Strings to be displayed.
     *
     * @return Formatted Building Code String Array.
     */
    public String[] processValuesBuildings() {
        String[] formattedValues = new String[this.buildingCodes.size()];

        for (int i = 0; i < this.buildingCodes.size(); i++)
            formattedValues[i] = this.buildingCodes.get(i).toString();

        return formattedValues;
    }

    /**
     * Helper Method to check if a String has a numerical value.
     *
     * @param toConvert String to check.
     * @return Whether the given Parameter has a numerical value.
     */
    public boolean isNumeric(String toConvert) {
        try {
            int testInt = Integer.parseInt(toConvert);
            return testInt > 0;
        } catch (Exception e) {
            return false;
        }
    }

}
