package edu.bu.cs411.Courses.Util;

import edu.bu.cs411.Config.CoursesConfig;
import edu.bu.cs411.Config.GeneralConfig;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;

/**
 * Course Schedule Object Class.
 *
 * @author ejeon@bu.edu.
 * @version 1.0.0.
 */
public class Schedule {

    /**
     * Current Scheduled Days for this Schedule.
     */
    private ArrayList<DayOfWeek> days;
    /**
     * Current Start Time for this Schedule.
     */
    private Time startTime;
    /**
     * Current End Time for this Schedule.
     */
    private Time endTime;

    /**
     * Base Constructor for the Schedule Object.
     *
     * @param days      Current Scheduled Days for this Schedule.
     * @param startTime Current Start Time for this Schedule.
     * @param endTime   Current End Time for this Schedule.
     */
    public Schedule(ArrayList<DayOfWeek> days, Time startTime, Time endTime) {
        this.setDays(days);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
    }

    /**
     * String Constructor for the Schedule Object.
     *
     * @param schedule String Value for the Schedule.
     * @throws InstantiationException Illegal Initiation Parameters.
     */
    public Schedule(String schedule) throws InstantiationException {
        String[] splitSchedule = schedule.split(CoursesConfig.SCHEDULE_MAIN_SPLITTER);

        if (splitSchedule.length < 2)
            throw new InstantiationException(GeneralConfig.ILLEGAL_ARGUMENTS_ERROR);

        ArrayList<DayOfWeek> days = new ArrayList<>();
        for (int i = 0; i < splitSchedule.length-1; i++)
            days.add(DayOfWeek.valueOf(splitSchedule[i]));

        String[] splitTimes = splitSchedule[splitSchedule.length-1].split(CoursesConfig.SCHEDULE_TO_SPLITTER);
        String[] startTime = splitTimes[0].split(CoursesConfig.SCHEDULE_TIME_SPLITTER);
        String[] endTime = splitTimes[1].split(CoursesConfig.SCHEDULE_TIME_SPLITTER);

        if (startTime.length != 2 || endTime.length != 2)
            throw new InstantiationException(GeneralConfig.ILLEGAL_ARGUMENTS_ERROR);

        this.setDays(days);
        this.setStartTime(new Time(Integer.parseInt(startTime[0]), Integer.parseInt(startTime[1]), 0));
        this.setEndTime(new Time(Integer.parseInt(endTime[0]), Integer.parseInt(endTime[1]), 0));
    }

    /**
     * No Args Constructor.
     */
    public Schedule() {
    }

    /**
     * Get the Current Scheduled Days for this Schedule.
     *
     * @return Current Scheduled Days for this Schedule.
     */
    public ArrayList<DayOfWeek> getDays() {
        return this.days;
    }

    /**
     * Set the Current Scheduled Days for this Schedule.
     *
     * @param days Current Scheduled Days for this Schedule.
     */
    public void setDays(ArrayList<DayOfWeek> days) {
        this.days = days;
    }

    /**
     * Get the Current Start Time for this Schedule.
     *
     * @return Current Start Time for this Schedule.
     */
    public Time getStartTime() {
        return this.startTime;
    }

    /**
     * Set the Current Start Time for this Schedule.
     *
     * @param startTime Current Start Time for this Schedule.
     */
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    /**
     * Get the Current End Time for this Schedule.
     *
     * @return Current End Time for this Schedule.
     */
    public Time getEndTime() {
        return this.endTime;
    }

    /**
     * Set the Current End Time for this Schedule.
     *
     * @param endTime Current End Time for this Schedule.
     */
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    /**
     * Helper Method to check whether this Schedule is equal to another Schedule.
     *
     * @param o Object to compare to.
     * @return Whether this Schedule is equal to another Schedule.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Schedule castedObject) {
            for (DayOfWeek day : this.getDays()) {
                if (castedObject.getDays().contains(day) && this.getEndTime().before(castedObject.getStartTime())) {
                    return true;
                }
            }

            return false;
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (DayOfWeek day : this.getDays())
            stringBuilder.append(day.toString()).append(CoursesConfig.SCHEDULE_MAIN_SPLITTER);

        stringBuilder.append(this.getStartTime().getHours()).append(CoursesConfig.SCHEDULE_TIME_SPLITTER)
                .append(this.getStartTime().getMinutes()).append(CoursesConfig.SCHEDULE_TO_SPLITTER)
                .append(this.getEndTime().getHours()).append(CoursesConfig.SCHEDULE_TIME_SPLITTER)
                .append(this.getEndTime().getMinutes());

        return stringBuilder.toString();
    }

}
