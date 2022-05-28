package edu.bu.cs411.Config;

import edu.bu.cs411.Courses.Course;
import edu.bu.cs411.Courses.CourseID;
import edu.bu.cs411.Courses.Util.BuildingCode;
import edu.bu.cs411.Courses.Util.CourseStatus;
import edu.bu.cs411.Courses.Util.Location;
import edu.bu.cs411.Courses.Util.Schedule;
import edu.bu.cs411.Util.College;
import edu.bu.cs411.Util.Department;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

/**
 * Config Class for all details for Courses.
 *
 * @author ejeon@bu.edu.
 * @version 1.0.0.
 */
public class CoursesConfig {

    /**
     * Number of Credits each Course is worth.
     */
    public static final int CREDITS_PER_COURSE = 4;


    /**
     * Minimum Course Number Allowed.
     */
    public static final int MIN_COURSE_NUMBER = 1;
    /**
     * Maximum Course Number Allowed.
     */
    public static final int MAX_COURSE_NUMBER = 999;
    /**
     * Minimum Course Capacity Allowed.
     */
    public static final int MIN_COURSE_CAPACITY = 1;
    /**
     * Maximum Course Capacity Allowed.
     */
    public static final int MAX_COURSE_CAPACITY = 1000;
    /**
     * Minimum Room Number Allowed.
     */
    public static final int MIN_ROOM_NUMBER = 1;
    /**
     * Maximum Room Number Allowed.
     */
    public static final int MAX_ROOM_NUMBER = 5000;


    /**
     * ToString Formatting Splitter for CourseIDs
     */
    public static final String COURSE_ID_SPLITTER = " ";


    /**
     * ToString Formatting Splitter for Locations
     */
    public static final String LOCATION_SPLITTER = " ";

    /**
     * ToString Formatting Main Splitter for Schedules
     */
    public static final String SCHEDULE_MAIN_SPLITTER = " ";
    /**
     * ToString Formatting Time Splitter for Schedules
     */
    public static final String SCHEDULE_TIME_SPLITTER = ":";
    /**
     * ToString Formatting To-From Splitter for Schedules
     */
    public static final String SCHEDULE_TO_SPLITTER = "-";


    /**
     * Default Value for a CourseID.
     */
    public static final CourseID DEFAULT_COURSE_ID = new CourseID(College.CAS, Department.AA, 0);
    /**
     * Default Value for a Course Start Time.
     */
    public static final Time DEFAULT_START_TIME = new Time(8, 0, 0);
    /**
     * Default Value for a Course End Time.
     */
    public static final Time DEFAULT_END_TIME = new Time(9, 0, 0);
    /**
     * Default Value for a Schedule.
     */
    public static final Schedule DEFAULT_SCHEDULE = new Schedule(new ArrayList<>(List.of(DayOfWeek.MONDAY)),
            DEFAULT_START_TIME, DEFAULT_END_TIME);
    /**
     * Default Value for a Course BuildingCode.
     */
    public static final BuildingCode DEFAULT_BUILDING_CODE = BuildingCode.CAS;
    /**
     * Default Value for a Location.
     */
    public static final Location DEFAULT_LOCATION = new Location(DEFAULT_BUILDING_CODE, 1);


    /**
     * Value for a CourseID for Default Course One.
     */
    public static final CourseID DEFAULT_COURSE_ID_ONE = new CourseID(College.CAS, Department.CS, 111);
    /**
     * Value for a CourseID for Default Course Two.
     */
    public static final CourseID DEFAULT_COURSE_ID_TWO = new CourseID(College.CAS, Department.CS, 411);
    /**
     * Value for a CourseID for Default Course Three.
     */
    public static final CourseID DEFAULT_COURSE_ID_THREE = new CourseID(College.CAS, Department.CS, 611);


    /**
     * Value for a Schedule for Default Course One.
     */
    public static final Schedule DEFAULT_SCHEDULE_ONE = new Schedule(new ArrayList<>(List.of(DayOfWeek.MONDAY)),
            new Time(8, 0, 0), new Time(8, 0, 0));
    /**
     * Value for a Schedule for Default Course Two.
     */
    public static final Schedule DEFAULT_SCHEDULE_TWO = new Schedule(new ArrayList<>(List.of(DayOfWeek.TUESDAY)),
            new Time(8, 0, 0), new Time(8, 0, 0));
    /**
     * Value for a Schedule for Default Course Three.
     */
    public static final Schedule DEFAULT_SCHEDULE_THREE = new Schedule(new ArrayList<>(List.of(DayOfWeek.WEDNESDAY)),
            new Time(8, 0, 0), new Time(8, 0, 0));


    /**
     * Default Course One.
     */
    public static final Course DEFAULT_COURSE_ONE = new Course(DEFAULT_COURSE_ID_ONE, CourseStatus.CLOSED,
            UsersConfig.DEFAULT_PROFESSOR_ONE.getID(), new ArrayList<>(), 200, DEFAULT_SCHEDULE_ONE,
            DEFAULT_LOCATION);
    /**
     * Default Course Two.
     */
    public static final Course DEFAULT_COURSE_TWO = new Course(DEFAULT_COURSE_ID_TWO, CourseStatus.CLOSED,
            UsersConfig.DEFAULT_PROFESSOR_TWO.getID(), new ArrayList<>(), 200, DEFAULT_SCHEDULE_TWO,
            DEFAULT_LOCATION);
    /**
     * Default Course Three.
     */
    public static final Course DEFAULT_COURSE_THREE = new Course(DEFAULT_COURSE_ID_THREE, CourseStatus.CLOSED,
            UsersConfig.DEFAULT_PROFESSOR_THREE.getID(), new ArrayList<>(), 200, DEFAULT_SCHEDULE_THREE,
            DEFAULT_LOCATION);


    /**
     * Default Course List.
     */
    public static final ArrayList<Course> DEFAULT_COURSES = new ArrayList<>(List.of(DEFAULT_COURSE_ONE,
            DEFAULT_COURSE_TWO, DEFAULT_COURSE_THREE));

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public CoursesConfig() throws InstantiationException {
        throw new InstantiationException("Illegal No Args Constructor");
    }

}
