package edu.bu.cs411.UI.Screens.Admin;

import edu.bu.cs411.Config.CoursesConfig;
import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.Courses.Course;
import edu.bu.cs411.Courses.CourseID;
import edu.bu.cs411.Courses.Util.BuildingCode;
import edu.bu.cs411.Courses.Util.CourseStatus;
import edu.bu.cs411.Courses.Util.Location;
import edu.bu.cs411.Courses.Util.Schedule;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.Screens.GUIScreen;
import edu.bu.cs411.Users.Util.UniqueID;
import edu.bu.cs411.Util.College;
import edu.bu.cs411.Util.Department;

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
public class CourseAddScreen extends GUIScreen {

    /**
     * Screen Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Form Course Number Field.
     */
    private JTextField courseNumber;
    /**
     * Form Max Capacity Field.
     */
    private JTextField maxCapacity;
    /**
     * Form Room Number Field.
     */
    private JTextField roomNumber;
    /**
     * Form College List.
     */
    private JList<String> collegeList;
    /**
     * Form Department List.
     */
    private JList<String> departmentList;
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
     * Actual List of Colleges.
     */
    private ArrayList<College> colleges;
    /**
     * Actual List of Departments.
     */
    private ArrayList<Department> departments;
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
    public CourseAddScreen() {
        super(100, 100, 500, 841);
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
        JLabel title = new JLabel(GUIConfig.NEW_COURSE_TITLE);
        title.setFont(new Font(GUIConfig.GUI_FONT, Font.PLAIN, GUIConfig.GUI_TEXT_SIZE_ONE));
        title.setBounds(10, 11, 464, 35);
        this.addComponent(title);

        JLabel collegeTitle = new JLabel(GUIConfig.COLLEGE_FIELD_TITLE);
        collegeTitle.setBounds(20, 58, 46, 14);
        this.addComponent(collegeTitle);

        JScrollPane collegeListScroll = new JScrollPane();
        collegeList = new JList<>();
        collegeList.setModel(new AbstractListModel<>() {
            @Serial
            private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;
            final String[] values = processValuesColleges();

            public int getSize() {
                return values.length;
            }

            public String getElementAt(int index) {
                return values[index];
            }
        });
        collegeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        collegeListScroll.setBounds(30, 83, 86, 110);
        collegeListScroll.setViewportView(collegeList);
        collegeListScroll.setHorizontalScrollBar(null);
        this.addComponent(collegeListScroll);

        JLabel departmentTitle = new JLabel(GUIConfig.DEPARTMENT_FIELD_TITLE);
        departmentTitle.setBounds(178, 58, 86, 14);
        this.addComponent(departmentTitle);

        JScrollPane departmentListScroll = new JScrollPane();
        departmentList = new JList<>();
        departmentList.setModel(new AbstractListModel<>() {
            @Serial
            private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;
            final String[] values = processValuesDepartments();

            public int getSize() {
                return values.length;
            }

            public String getElementAt(int index) {
                return values[index];
            }
        });
        departmentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        departmentListScroll.setBounds(188, 85, 86, 110);
        departmentListScroll.setViewportView(departmentList);
        departmentListScroll.setHorizontalScrollBar(null);
        this.addComponent(departmentListScroll);

        JLabel courseNumberTitle = new JLabel(GUIConfig.COURSE_NUMBER_FIELD_TITLE);
        courseNumberTitle.setBounds(329, 58, 108, 14);
        this.addComponent(courseNumberTitle);

        courseNumber = new JTextField();
        courseNumber.setColumns(10);
        courseNumber.setBounds(339, 83, 86, 20);
        this.addComponent(courseNumber);

        JLabel professorListTitle = new JLabel(GUIConfig.PROFESSORS_FIELD_TITLE);
        professorListTitle.setBounds(20, 237, 65, 14);
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
        professorListScroll.setBounds(30, 262, 244, 110);
        professorListScroll.setViewportView(professorList);
        professorListScroll.setHorizontalScrollBar(null);
        this.addComponent(professorListScroll);

        JLabel maxCapacityTitle = new JLabel(GUIConfig.MAX_CAPACITY_FIELD_TITLE);
        maxCapacityTitle.setBounds(329, 237, 108, 14);
        this.addComponent(maxCapacityTitle);

        maxCapacity = new JTextField();
        maxCapacity.setColumns(10);
        maxCapacity.setBounds(339, 260, 86, 20);
        this.addComponent(maxCapacity);

        JLabel daysOfWeekTitle = new JLabel(GUIConfig.DAYS_FIELD_TITLE);
        daysOfWeekTitle.setBounds(20, 398, 108, 14);
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
        dayListScroll.setBounds(30, 425, 86, 110);
        dayListScroll.setViewportView(dayList);
        dayListScroll.setHorizontalScrollBar(null);
        this.addComponent(dayListScroll);

        JLabel startTimeTitle = new JLabel(GUIConfig.START_TIME_FIELD_TITLE);
        startTimeTitle.setBounds(178, 398, 86, 14);
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
        startTimeListScroll.setBounds(188, 425, 86, 110);
        startTimeListScroll.setViewportView(startTimeList);
        startTimeListScroll.setHorizontalScrollBar(null);
        this.addComponent(startTimeListScroll);

        JLabel endTimeTitle = new JLabel(GUIConfig.END_TIME_FIELD_TITLE);
        endTimeTitle.setBounds(329, 398, 86, 14);
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
        endTimeListScroll.setBounds(339, 425, 86, 110);
        endTimeListScroll.setViewportView(endTimeList);
        endTimeListScroll.setHorizontalScrollBar(null);
        this.addComponent(endTimeListScroll);

        JLabel buildingCodeTitle = new JLabel(GUIConfig.BUILDING_FIELD_TITLE);
        buildingCodeTitle.setBounds(20, 563, 65, 14);
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
        buildingListScroll.setBounds(30, 588, 244, 110);
        buildingListScroll.setViewportView(buildingList);
        buildingListScroll.setHorizontalScrollBar(null);
        this.addComponent(buildingListScroll);

        JLabel roomNumberTitle = new JLabel(GUIConfig.ROOM_NUMBER_FIELD_TITLE);
        roomNumberTitle.setBounds(329, 563, 108, 14);
        this.addComponent(roomNumberTitle);

        roomNumber = new JTextField();
        roomNumber.setColumns(10);
        roomNumber.setBounds(339, 586, 86, 20);
        this.addComponent(roomNumber);

        JLabel extraMessageField = new JLabel("" + extraMessage);
        extraMessageField.setBounds(20, 728, 365, 14);
        this.addComponent(extraMessageField);

        JButton saveBtn = new JButton(GUIConfig.SAVE_COURSE_ACTION_NAME);
        saveBtn.setBounds(10, 768, 125, 23);
        saveBtn.setAction(actions.get(0));
        this.addComponent(saveBtn);

        JButton backBtn = new JButton(GUIConfig.BACK_ACTION_NAME);
        backBtn.setBounds(349, 768, 125, 23);
        backBtn.setAction(actions.get(1));
        this.addComponent(backBtn);
    }

    /**
     * Gets the formatted Course obtained from the Submission Form.
     *
     * @return Formatted Course from the Submission Form.
     */
    public Course getCourse() {
        Course course = new Course();

        CourseID id = CoursesConfig.DEFAULT_COURSE_ID;
        if (this.collegeList.getSelectedIndex() != -1 && this.departmentList.getSelectedIndex() != -1
                && this.isNumeric(this.courseNumber.getText()))
            id = new CourseID(this.colleges.get(this.collegeList.getSelectedIndex()),
                    this.departments.get(this.departmentList.getSelectedIndex()),
                    Integer.parseInt(this.courseNumber.getText()));
        course.setId(id);

        if (this.professorList.getSelectedIndex() != -1)
            course.setProfessor(this.professors.get(this.professorList.getSelectedIndex()));
        else
            course.setProfessor(null);

        if (this.isNumeric(this.maxCapacity.getText()))
            course.setMaxCapacity(Integer.parseInt(this.maxCapacity.getText()));
        else
            course.setMaxCapacity(CoursesConfig.MAX_COURSE_CAPACITY);

        Schedule schedule = new Schedule();
        ArrayList<DayOfWeek> days = new ArrayList<>(java.util.List.of(DayOfWeek.MONDAY));
        if (this.dayList.getSelectedIndex() != -1) {
            for (Integer i : this.dayList.getSelectedIndices())
                days.add(this.days.get(i));
        }
        schedule.setDays(days);

        if (this.startTimeList.getSelectedIndex() != -1)
            schedule.setStartTime(this.times.get(this.startTimeList.getSelectedIndex()));
        else
            schedule.setStartTime(CoursesConfig.DEFAULT_START_TIME);
        if (this.endTimeList.getSelectedIndex() != -1)
            schedule.setEndTime(this.times.get(this.endTimeList.getSelectedIndex()));
        else
            schedule.setEndTime(CoursesConfig.DEFAULT_END_TIME);
        if (schedule.getStartTime().after(schedule.getEndTime()))
            schedule.setEndTime(new Time(schedule.getStartTime().getHours() + 1, schedule.getStartTime().getMinutes(),
                    0));
        course.setSchedule(schedule);

        Location location = new Location();
        if (this.buildingList.getSelectedIndex() != -1)
            location.setBuildingCode(this.buildingCodes.get(this.buildingList.getSelectedIndex()));
        else
            location.setBuildingCode(CoursesConfig.DEFAULT_BUILDING_CODE);
        if (this.isNumeric(this.roomNumber.getText()))
            location.setRoom(Integer.parseInt(this.roomNumber.getText()));
        else
            location.setRoom(CoursesConfig.MIN_ROOM_NUMBER);
        course.setLocation(location);

        course.setStudents(new ArrayList<>());
        course.setStatus(CourseStatus.CLOSED);

        return course;
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
        this.colleges = new ArrayList<>(java.util.List.of(College.values()));
        this.buildingCodes = new ArrayList<>(java.util.List.of(BuildingCode.values()));
        this.departments = new ArrayList<>(java.util.List.of(Department.values()));
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
     * Process the College List into an Array of Strings to be displayed.
     *
     * @return Formatted College String Array.
     */
    public String[] processValuesColleges() {
        String[] formattedValues = new String[this.colleges.size()];

        for (int i = 0; i < this.colleges.size(); i++)
            formattedValues[i] = this.colleges.get(i).toString();

        return formattedValues;
    }

    /**
     * Process the Department List into an Array of Strings to be displayed.
     *
     * @return Formatted Department String Array.
     */
    public String[] processValuesDepartments() {
        String[] formattedValues = new String[this.departments.size()];

        for (int i = 0; i < this.departments.size(); i++)
            formattedValues[i] = this.departments.get(i).toString();

        return formattedValues;
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
