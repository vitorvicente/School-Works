package edu.bu.cs411.Users;

import edu.bu.cs411.Users.Util.Credentials;
import edu.bu.cs411.Users.Util.UserDetails;
import edu.bu.cs411.Util.College;

/**
 * Base Class for Admin User Objects.
 *
 * @author jycchoi@bu.edu.
 * @version 1.0.0.
 */
public class Admin extends User {

    /**
     * College the Admin Works For.
     */
    private College college;

    /**
     * Base Constructor for the Admin Object.
     *
     * @param credentials Credentials for this User.
     * @param details     General Details for this User.
     * @param college     College the Admin Works For.
     */
    public Admin(Credentials credentials, UserDetails details, College college) {
        super(credentials, details);
        this.setCollege(college);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Instantiation Type.
     */
    public Admin() throws InstantiationException {
        super();
    }

    /**
     * Get the College the Admin Works For.
     *
     * @return College the Admin Works For.
     */
    public College getCollege() {
        return this.college;
    }

    /**
     * Set the College the Admin Works For.
     *
     * @param college College the Admin Works For.
     */
    public void setCollege(College college) {
        this.college = college;
    }

}
