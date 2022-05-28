package edu.bu.cs411.Courses.Util;

import edu.bu.cs411.Config.GeneralConfig;

/**
 * ENUM Representing all the possible CourseStatus Types.
 * Includes Helper Methods to deal with managing CourseStatus.
 *
 * @author ejeon@bu.edu.
 * @version 1.0.0.
 */
public enum CourseStatus {
    /**
     * Possible Values
     */
    OPEN, CLOSED, FULL, REMOVED;

    /**
     * Static Helper Method to get a CourseStatus from its String Value.
     *
     * @param courseStatus Value to get the CourseStatus for.
     * @return CourseStatus Value obtained from String.
     */
    public static CourseStatus getCourseStatus(String courseStatus) {
        for (CourseStatus cs : CourseStatus.values()) {
            if (cs.toString().equalsIgnoreCase(courseStatus))
                return cs;
        }
        throw new IllegalArgumentException(GeneralConfig.ILLEGAL_ARGUMENTS_ERROR);
    }

    /**
     * Retrieve the String Value for the CourseStatus.
     *
     * @return String Value for the CourseStatus.
     */
    @Override
    public String toString() {
        return this.name();
    }

}
