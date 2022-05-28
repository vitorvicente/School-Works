package edu.bu.cs411.Data;

import edu.bu.cs411.Config.CoursesConfig;
import edu.bu.cs411.Config.RegistrarConfig;
import edu.bu.cs411.Config.UsersConfig;
import edu.bu.cs411.Courses.Course;
import edu.bu.cs411.Courses.CourseListing;
import edu.bu.cs411.Data.Transformers.*;
import edu.bu.cs411.Registrar.RegistrationDetails;
import edu.bu.cs411.Users.Admin;
import edu.bu.cs411.Users.Professor;
import edu.bu.cs411.Users.Student;
import edu.bu.cs411.Users.UserListing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main Entry Class for the Data of the Program.
 * Hub for Saving and Loading all Data.
 *
 * @author evanlhsu@bu.edu.
 * @version 1.0.0.
 */
public class DataManager {

    /**
     * Path to the Students Save File.
     */
    public static final String pathToStudents = "/src/edu/bu/cs411/Data/SaveFiles/Students.csv";
    /**
     * Path to the Professors Save File.
     */
    public static final String pathToProfessors = "/src/edu/bu/cs411/Data/SaveFiles/Professors.csv";
    /**
     * Path to the Admins Save File.
     */
    public static final String pathToAdmins = "/src/edu/bu/cs411/Data/SaveFiles/Admins.csv";

    /**
     * Path to the Courses Save File.
     */
    public static final String pathToCourses = "/src/edu/bu/cs411/Data/SaveFiles/Courses.csv";

    /**
     * Path to the RegDetails Save File.
     */
    public static final String pathToRegDetails = "/src/edu/bu/cs411/Data/SaveFiles/RegDetails.csv";

    /**
     * Helper Method to load the UserListing Object from Saved Files.
     *
     * @return UserListing Object from Saved Files.
     */
    public UserListing loadUsers() {
        File studentFile = new File(System.getProperty("user.dir") + pathToStudents);
        Scanner studentSC;
        try {
            studentSC = new Scanner(studentFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        File professorFile = new File(System.getProperty("user.dir") + pathToProfessors);
        Scanner professorSC;
        try {
            professorSC = new Scanner(professorFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        File adminFile = new File(System.getProperty("user.dir") + pathToAdmins);
        Scanner adminSC;
        try {
            adminSC = new Scanner(adminFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        StringToStudent studentTransformer = new StringToStudent();
        StringToProfessor professorTransformer = new StringToProfessor();
        StringToAdmin adminTransformer = new StringToAdmin();

        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Professor> professors = new ArrayList<>();
        ArrayList<Admin> admins = new ArrayList<>();

        while (studentSC.hasNextLine())
            students.add(studentTransformer.apply(studentSC.nextLine()));

        while (professorSC.hasNextLine())
            professors.add(professorTransformer.apply(professorSC.nextLine()));

        while (adminSC.hasNextLine())
            admins.add(adminTransformer.apply(adminSC.nextLine()));

        if (students.isEmpty() || professors.isEmpty() || admins.isEmpty())
            return this.loadDefaultUsers();

        return new UserListing(professors, students, admins);
    }

    /**
     * Helper method to load the CourseListing Object from Saved Files.
     *
     * @return CourseListing Object from Saved Files.
     */
    public CourseListing loadCourses() {
        File file = new File(System.getProperty("user.dir") + pathToCourses);
        Scanner courseSC;
        try {
            courseSC = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        StringToCourse courseTransformer = new StringToCourse();
        ArrayList<Course> courses = new ArrayList<>();

        while (courseSC.hasNextLine())
            courses.add(courseTransformer.apply(courseSC.nextLine()));

        if (courses.isEmpty())
            return this.loadDefaultCourses();

        return new CourseListing(courses);
    }

    /**
     * Helper method to load the RegistrationDetails Object from Saved Files.
     *
     * @return RegistrationDetails Object from Saved Files.
     */
    public RegistrationDetails loadRegDetails() {
        File file = new File(System.getProperty("user.dir") + pathToRegDetails);
        Scanner sc;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        StringToRegDetails regDetailsTransformer = new StringToRegDetails();

        if (!sc.hasNextLine())
            return this.loadDefaultRegDetails();

        return regDetailsTransformer.apply(sc.nextLine());
    }

    /**
     * Helper Method to load the Default UserListing Object.
     *
     * @return Default UserListing Object.
     */
    public UserListing loadDefaultUsers() {
        return new UserListing(UsersConfig.DEFAULT_PROFESSORS, UsersConfig.DEFAULT_STUDENTS, UsersConfig.DEFAULT_ADMINS);
    }

    /**
     * Helper Method to load the Default CourseListing Object.
     *
     * @return Default CourseListing Object.
     */
    public CourseListing loadDefaultCourses() {
        return new CourseListing(CoursesConfig.DEFAULT_COURSES);
    }

    /**
     * Helper Method to load the Default RegistrationDetails Object.
     *
     * @return Default RegistrationDetails Object.
     */
    public RegistrationDetails loadDefaultRegDetails() {
        return new RegistrationDetails(RegistrarConfig.DEFAULT_ALL_OPEN, RegistrarConfig.DEFAULT_SEMESTER);
    }

    /**
     * Helper Method to save the UserListing Object to Saved Files.
     *
     * @param userListing UserListing Object to save.
     * @return UserListing Object Saved.
     */
    public UserListing saveUsers(UserListing userListing) {
        StringBuilder studentsCSV = new StringBuilder();
        StringBuilder professorsCSV = new StringBuilder();
        StringBuilder adminsCSV = new StringBuilder();

        ArrayList<Student> students = userListing.getStudents();
        ArrayList<Professor> professors = userListing.getProfessors();
        ArrayList<Admin> admins = userListing.getAdmins();

        StringToStudent studentTransformer = new StringToStudent();
        StringToProfessor professorTransformer = new StringToProfessor();
        StringToAdmin adminTransformer = new StringToAdmin();

        Path studentsFileName = Path.of(System.getProperty("user.dir") + pathToStudents);
        Path professorsFileName = Path.of(System.getProperty("user.dir") + pathToProfessors);
        Path adminsFileName = Path.of(System.getProperty("user.dir") + pathToAdmins);

        for (Student student : students)
            studentsCSV.append(studentTransformer.reverse(student)).append("\n");

        for (Professor professor : professors)
            professorsCSV.append(professorTransformer.reverse(professor)).append("\n");

        for (Admin admin : admins)
            adminsCSV.append(adminTransformer.reverse(admin)).append("\n");

        try {
            Files.writeString(studentsFileName, studentsCSV.toString());
            Files.writeString(professorsFileName, professorsCSV.toString());
            Files.writeString(adminsFileName, adminsCSV.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return userListing;
    }

    /**
     * Helper Method to save the CourseListing Object to Saved Files.
     *
     * @param courseListing CourseListing Object to save.
     * @return CourseListing Object Saved.
     */
    public CourseListing saveCourses(CourseListing courseListing) {
        StringBuilder coursesCSV = new StringBuilder();
        ArrayList<Course> courses = courseListing.getCourses();
        StringToCourse courseTransformer = new StringToCourse();
        Path coursesFileName = Path.of(System.getProperty("user.dir") + pathToCourses);

        for (Course course : courses)
            coursesCSV.append(courseTransformer.reverse(course)).append("\n");

        try {
            Files.writeString(coursesFileName, coursesCSV.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return courseListing;
    }

    /**
     * Helper Method to save the RegistrationDetails Object to Saved Files.
     *
     * @param registrationDetails RegistrationDetails Object to save.
     * @return RegistrationDetails Object Saved.
     */
    public RegistrationDetails saveRegDetails(RegistrationDetails registrationDetails) {
        String regDetailsCSV = new StringToRegDetails().reverse(registrationDetails);
        Path regDetailsFileName = Path.of(System.getProperty("user.dir") + pathToRegDetails);

        try {
            Files.writeString(regDetailsFileName, regDetailsCSV);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return registrationDetails;
    }

}
