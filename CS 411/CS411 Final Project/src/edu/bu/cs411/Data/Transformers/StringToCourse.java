package edu.bu.cs411.Data.Transformers;

import edu.bu.cs411.Config.CoursesConfig;
import edu.bu.cs411.Config.DataConfig;
import edu.bu.cs411.Courses.Course;
import edu.bu.cs411.Courses.CourseID;
import edu.bu.cs411.Courses.Util.CourseStatus;
import edu.bu.cs411.Courses.Util.Location;
import edu.bu.cs411.Courses.Util.Schedule;
import edu.bu.cs411.Users.Util.UniqueID;

import java.util.ArrayList;

/**
 * Parser for Saving/Loading Courses.
 * <p>
 * Admin CSV Format: "id,status,professor,students,maxCapacity,schedule,location".
 * Replacing the ',' with DataConfig.CSV_DELIMITER_ONE.
 *
 * @author evanlhsu@bu.edu.
 * @version 1.0.0.
 */
public class StringToCourse extends Reversible<String, Course> {

    /**
     * Transform a Course Object into a String.
     *
     * @param course Course Object to Transform.
     * @return String.
     */
    @Override
    public String reverse(Course course) {
        String id = course.getId().toString();
        String status = course.getStatus().toString();
        String professor = course.getProfessor().toString();

        ArrayList<UniqueID> students = course.getStudents();
        StringBuilder studentsStrBuilder = new StringBuilder();
        for (UniqueID uniqueID : students)
            studentsStrBuilder.append(uniqueID.toString()).append(DataConfig.CSV_DELIMITER_TWO);
        if (studentsStrBuilder.length() > 0)
            studentsStrBuilder.deleteCharAt(studentsStrBuilder.length() - 1);
        String studentsStr = studentsStrBuilder.toString();

        String maxCapacity = String.valueOf(course.getMaxCapacity());
        String schedule = course.getSchedule().toString();
        String location = course.getLocation().toString();

        return String.join(DataConfig.CSV_DELIMITER_ONE, id, status, professor,
                studentsStr, maxCapacity, schedule, location);
    }

    /**
     * Transform a String into a Course Object.
     *
     * @param s String to Transform.
     * @return Course Object.
     */
    @Override
    public Course apply(String s) {
        String[] courseArr = s.split(DataConfig.CSV_DELIMITER_ONE);

        if (courseArr.length != 7)
            return null;

        CourseID id;
        try {
            id = new CourseID(courseArr[0]);
        } catch (InstantiationException e) {
            e.printStackTrace();
            id = CoursesConfig.DEFAULT_COURSE_ID;
        }
        CourseStatus status = CourseStatus.valueOf(courseArr[1]);
        UniqueID professor = null;
        try {
            professor = new UniqueID(courseArr[2]);
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        ArrayList<UniqueID> students = new ArrayList<>();
        for (String uniqueID : courseArr[3].split(DataConfig.CSV_DELIMITER_TWO)) {
            try {
                students.add(new UniqueID(uniqueID));
            } catch (InstantiationException ignored) {
            }
        }
        int maxCapacity = Integer.parseInt(courseArr[4]);
        Schedule schedule;
        try {
            schedule = new Schedule(courseArr[5]);
        } catch (InstantiationException e) {
            e.printStackTrace();
            schedule = CoursesConfig.DEFAULT_SCHEDULE;
        }
        Location location;
        try {
            location = new Location(courseArr[6]);
        } catch (InstantiationException e) {
            e.printStackTrace();
            location = CoursesConfig.DEFAULT_LOCATION;
        }

        return new Course(id, status, professor, students, maxCapacity, schedule, location);
    }

}
