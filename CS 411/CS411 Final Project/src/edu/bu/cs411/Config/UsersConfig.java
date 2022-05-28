package edu.bu.cs411.Config;

import edu.bu.cs411.Users.Admin;
import edu.bu.cs411.Users.Professor;
import edu.bu.cs411.Users.Student;
import edu.bu.cs411.Users.Util.Credentials;
import edu.bu.cs411.Users.Util.UniqueID;
import edu.bu.cs411.Users.Util.UserDetails;
import edu.bu.cs411.Util.College;
import edu.bu.cs411.Util.Department;

import java.util.ArrayList;
import java.util.List;

/**
 * Config Class for all details for Users.
 *
 * @author jycchoi@bu.edu.
 * @version 1.0.0.
 */
public class UsersConfig {

    /**
     * ToString Formatting Splitter for UniqueIDs
     */
    public static final String UNIQUE_ID_SPLITTER = " ";
    /**
     * ToString Formatting Splitter for Emails.
     */
    public static final String EMAIL_SPLITTER = "@";


    /**
     * Default Value for a UniqueID.
     */
    public static final UniqueID DEFAULT_ID = new UniqueID('U', 0);


    /**
     * Value for a UniqueID for Default User One.
     */
    public static final UniqueID DEFAULT_ID_ONE = new UniqueID('U', 1231235);
    /**
     * Value for a UniqueID for Default User Two.
     */
    public static final UniqueID DEFAULT_ID_TWO = new UniqueID('U', 3245345);
    /**
     * Value for a UniqueID for Default User Three.
     */
    public static final UniqueID DEFAULT_ID_THREE = new UniqueID('P', 3453453);
    /**
     * Value for a UniqueID for Default User Four.
     */
    public static final UniqueID DEFAULT_ID_FOUR = new UniqueID('P', 2342342);
    /**
     * Value for a UniqueID for Default User Five.
     */
    public static final UniqueID DEFAULT_ID_FIVE = new UniqueID('P', 4655456);
    /**
     * Value for a UniqueID for Default User Six.
     */
    public static final UniqueID DEFAULT_ID_SIX = new UniqueID('A', 4655906);


    /**
     * Value for the Credentials for Default User One.
     */
    public static final Credentials CREDENTIALS_ONE = new Credentials(DEFAULT_ID_ONE, "kevin@bu.edu",
            new char[]{'1', '2', '3', '4', '5'});
    /**
     * Value for the Credentials for Default User Two.
     */
    public static final Credentials CREDENTIALS_TWO = new Credentials(DEFAULT_ID_TWO, "paula@bu.edu",
            new char[]{'1', '2', '3', '4', '5'});
    /**
     * Value for the Credentials for Default User Three.
     */
    public static final Credentials CREDENTIALS_THREE = new Credentials(DEFAULT_ID_THREE, "chen@bu.edu",
            new char[]{'1', '2', '3', '4', '5'});
    /**
     * Value for the Credentials for Default User Four.
     */
    public static final Credentials CREDENTIALS_FOUR = new Credentials(DEFAULT_ID_FOUR, "luise@bu.edu",
            new char[]{'1', '2', '3', '4', '5'});
    /**
     * Value for the Credentials for Default User Five.
     */
    public static final Credentials CREDENTIALS_FIVE = new Credentials(DEFAULT_ID_FIVE, "linsy@bu.edu",
            new char[]{'1', '2', '3', '4', '5'});
    /**
     * Value for the Credentials for Default User Six.
     */
    public static final Credentials CREDENTIALS_SIX = new Credentials(DEFAULT_ID_SIX, "admin@bu.edu",
            new char[]{'1', '2', '3', '4', '5'});


    /**
     * Value for the UserDetails for Default User One.
     */
    public static final UserDetails USER_DETAILS_ONE = new UserDetails(DEFAULT_ID_ONE, "Kevin", "Smith");
    /**
     * Value for the UserDetails for Default User Two.
     */
    public static final UserDetails USER_DETAILS_TWO = new UserDetails(DEFAULT_ID_TWO, "Paula", "Rodgers");
    /**
     * Value for the UserDetails for Default User Three.
     */
    public static final UserDetails USER_DETAILS_THREE = new UserDetails(DEFAULT_ID_THREE, "Chen", "Young");
    /**
     * Value for the UserDetails for Default User Four.
     */
    public static final UserDetails USER_DETAILS_FOUR = new UserDetails(DEFAULT_ID_FOUR, "Luise", "Tames");
    /**
     * Value for the UserDetails for Default User Five.
     */
    public static final UserDetails USER_DETAILS_FIVE = new UserDetails(DEFAULT_ID_FIVE, "Linsy", "Wang");
    /**
     * Value for the UserDetails for Default User Six.
     */
    public static final UserDetails USER_DETAILS_SIX = new UserDetails(DEFAULT_ID_SIX, "Admin", "Admin");


    /**
     * Default User One.
     */
    public static final Student DEFAULT_STUDENT_ONE = new Student(CREDENTIALS_ONE, USER_DETAILS_ONE,
            new ArrayList<>(), 0);
    /**
     * Default User Two.
     */
    public static final Student DEFAULT_STUDENT_TWO = new Student(CREDENTIALS_TWO, USER_DETAILS_TWO,
            new ArrayList<>(), 0);
    /**
     * Default Student List.
     */
    public static final ArrayList<Student> DEFAULT_STUDENTS = new ArrayList<>(List.of(DEFAULT_STUDENT_ONE,
            DEFAULT_STUDENT_TWO));


    /**
     * Default User Three.
     */
    public static final Professor DEFAULT_PROFESSOR_ONE = new Professor(CREDENTIALS_THREE, USER_DETAILS_THREE,
            new ArrayList<>(), College.CAS, Department.CS);
    /**
     * Default User Four.
     */
    public static final Professor DEFAULT_PROFESSOR_TWO = new Professor(CREDENTIALS_FOUR, USER_DETAILS_FOUR,
            new ArrayList<>(), College.CAS, Department.CS);
    /**
     * Default User Five.
     */
    public static final Professor DEFAULT_PROFESSOR_THREE = new Professor(CREDENTIALS_FIVE, USER_DETAILS_FIVE,
            new ArrayList<>(), College.CAS, Department.CS);
    /**
     * Default Professor List.
     */
    public static final ArrayList<Professor> DEFAULT_PROFESSORS = new ArrayList<>(List.of(DEFAULT_PROFESSOR_ONE,
            DEFAULT_PROFESSOR_TWO, DEFAULT_PROFESSOR_THREE));


    /**
     * Default User Six.
     */
    public static final Admin DEFAULT_ADMIN = new Admin(CREDENTIALS_SIX, USER_DETAILS_SIX, College.CAS);
    /**
     * Default Admin List.
     */
    public static final ArrayList<Admin> DEFAULT_ADMINS = new ArrayList<>(List.of(DEFAULT_ADMIN));


    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public UsersConfig() throws InstantiationException {
        throw new InstantiationException("Illegal No Args Constructor");
    }

}
