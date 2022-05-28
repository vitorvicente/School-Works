package edu.bu.cs411.Users;

import edu.bu.cs411.Config.GeneralConfig;
import edu.bu.cs411.Users.Util.Credentials;
import edu.bu.cs411.Users.Util.UniqueID;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Main Entry Class for the Users of the Program.
 * Maintains all the User details for the Registration Software.
 *
 * @author jycchoi@bu.edu.
 * @version 1.0.0.
 */
public class UserListing {

    /**
     * List of Current Professors.
     */
    private ArrayList<Professor> professors;
    /**
     * List of Current Students.
     */
    private ArrayList<Student> students;
    /**
     * List of Current Admins.
     */
    private ArrayList<Admin> admins;

    /**
     * Base Constructor for the UserListing Object.
     *
     * @param professors List of Current Professors.
     * @param students   List of Current Students.
     * @param admins     List of Current Admins.
     */
    public UserListing(ArrayList<Professor> professors, ArrayList<Student> students, ArrayList<Admin> admins) {
        this.setProfessors(professors);
        this.setStudents(students);
        this.setAdmins(admins);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Instantiation Type.
     */
    public UserListing() throws InstantiationException {
        throw new InstantiationException(GeneralConfig.ILLEGAL_EMPTY_CONSTRUCTOR_ERROR);
    }

    /**
     * Helper Method to attempt to Log In a User.
     *
     * @param credentials Credentials of the Login Attempt.
     * @return Logged In User.
     */
    public User login(Credentials credentials) {
        for (User p : this.getProfessors()) {
            if (p.getCredentials().equals(credentials))
                return p;
        }
        for (User s : this.getStudents()) {
            if (s.getCredentials().equals(credentials))
                return s;
        }
        for (User a : this.getAdmins()) {
            if (a.getCredentials().equals(credentials))
                return a;
        }

        return null;
    }

    /**
     * Helper Method to attempt to Fetch a User.
     *
     * @param id UniqueID of the User to Fetch.
     * @return Fetched User.
     */
    public User fetchUser(UniqueID id) {
        for (User p : this.getProfessors()) {
            if (p.getID().equals(id))
                return p;
        }
        for (User s : this.getStudents()) {
            if (s.getID().equals(id))
                return s;
        }
        for (User a : this.getAdmins()) {
            if (a.getID().equals(id))
                return a;
        }

        return null;
    }

    /**
     * Get the List of Current Professors.
     *
     * @return List of Current Professors.
     */
    public ArrayList<Professor> getProfessors() {
        return this.professors;
    }

    /**
     * Set the List of Current Professors.
     *
     * @param professors List of Current Professors.
     */
    public void setProfessors(ArrayList<Professor> professors) {
        this.professors = professors;
    }

    /**
     * Get the List of Current Students.
     *
     * @return List of Current Students.
     */
    public ArrayList<Student> getStudents() {
        return this.students;
    }

    /**
     * Set the List of Current Students.
     *
     * @param students List of Current Students.
     */
    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    /**
     * Get the List of Current Admins.
     *
     * @return List of Current Admins.
     */
    public ArrayList<Admin> getAdmins() {
        return this.admins;
    }

    /**
     * Set the List of Current Admins.
     *
     * @param admins List of Current Admins.
     */
    public void setAdmins(ArrayList<Admin> admins) {
        this.admins = admins;
    }

}
