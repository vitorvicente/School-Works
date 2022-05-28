package edu.bu.cs411.Config;

import edu.bu.cs411.Registrar.Semester;

import java.util.Date;

/**
 * Config Class for all details for the Registrar.
 *
 * @author dsullo@bu.edu.
 * @version 1.0.0.
 */
public class RegistrarConfig {

    /**
     * Overall Maximum Number of Credits a Student can register for.
     */
    public static final int MAX_CREDITS = 20;
    /**
     * Starting Number of Credits a Student is registered for..
     */
    public static final int START_CREDITS = 0;


    /**
     * String Value for the Spring Term.
     */
    public static final String SPRING_TERM_STRING_VALUE = "Spring";
    /**
     * String Value for the Summer Term.
     */
    public static final String SUMMER_TERM_STRING_VALUE = "Summer";
    /**
     * String Value for the Fall Term.
     */
    public static final String FALL_TERM_STRING_VALUE = "Fall";


    /**
     * Splitter Character for the Semester String Value.
     */
    public static final String SEMESTER_STRING_SPLITTER = " ";
    /**
     * Size of the Split Semester String Array.
     */
    public static final int SEMESTER_SPLIT_SIZE = 2;
    /**
     * Index of the Term Value in the Split Semester String Array.
     */
    public static final int SEMESTER_SPLIT_TERM_INDEX = 0;
    /**
     * Index of the Year Value in the Split Semester String Array.
     */
    public static final int SEMESTER_SPLIT_YEAR_INDEX = 1;
    /**
     * Year Jump Size when moving Semesters.
     */
    public static final int SEMESTER_YEAR_JUMP = 1;
    /**
     * Final Month Value caught by the Spring Term.
     */
    public static final int SEMESTER_SPRING_TERM_END = 4;
    /**
     * Final Month Value caught by the Summer Term.
     */
    public static final int SEMESTER_SUMMER_TERM_END = 7;


    /**
     * Default Value for the Overall Open Boolean.
     */
    public static final boolean DEFAULT_ALL_OPEN = false;
    /**
     * Default Semester.
     */
    public static final Semester DEFAULT_SEMESTER = new Semester(new Date());


    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public RegistrarConfig() throws InstantiationException {
        throw new InstantiationException(GeneralConfig.ILLEGAL_EMPTY_CONSTRUCTOR_ERROR);
    }

}
