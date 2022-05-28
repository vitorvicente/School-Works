package edu.bu.cs411.Users;

import edu.bu.cs411.Config.GeneralConfig;
import edu.bu.cs411.Users.Util.Credentials;
import edu.bu.cs411.Users.Util.UniqueID;
import edu.bu.cs411.Users.Util.UserDetails;

/**
 * Abstract overall User Class.
 * Maintains all the shared Data by all Users.
 *
 * @author jycchoi@bu.edu.
 * @version 1.0.0.
 */
public abstract class User {

    /**
     * Credentials for this User.
     */
    private Credentials credentials;
    /**
     * General Details for this User.
     */
    private UserDetails details;

    /**
     * Base Constructor for the User Object.
     *
     * @param credentials Credentials for this User.
     * @param details     General Details for this User.
     */
    public User(Credentials credentials, UserDetails details) {
        this.setCredentials(credentials);
        this.setDetails(details);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Instantiation Type.
     */
    public User() throws InstantiationException {
        throw new InstantiationException(GeneralConfig.ILLEGAL_EMPTY_CONSTRUCTOR_ERROR);
    }

    /**
     * Get the UniqueID for this User.
     *
     * @return UniqueID for this User.
     */
    public UniqueID getID() {
        return this.getDetails().getId();
    }

    /**
     * Get the Credentials for this User.
     *
     * @return Credentials for this User.
     */
    public Credentials getCredentials() {
        return this.credentials;
    }

    /**
     * Set the Credentials for this User.
     *
     * @param credentials Credentials for this User.
     */
    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    /**
     * Get the General Details for this User.
     *
     * @return General Details for this User.
     */
    public UserDetails getDetails() {
        return this.details;
    }

    /**
     * Set the General Details for this User.
     *
     * @param details General Details for this User.
     */
    public void setDetails(UserDetails details) {
        this.details = details;
    }

    /**
     * Helper Method to check whether this User is equal to another User.
     *
     * @param o Object to compare to.
     * @return Whether this User is equal to another User.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof User castedObject)
            return this.getID().equals(castedObject.getID());

        return false;
    }

    /**
     * Retrieve the String Value for the User.
     *
     * @return String Value for the User.
     */
    @Override
    public String toString() {
        return this.getID().toString();
    }

}
