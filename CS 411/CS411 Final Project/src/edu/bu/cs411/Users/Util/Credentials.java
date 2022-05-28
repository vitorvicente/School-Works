package edu.bu.cs411.Users.Util;

import edu.bu.cs411.Config.GeneralConfig;
import edu.bu.cs411.Config.UsersConfig;

import java.util.Arrays;

/**
 * User Credentials Object Class.
 *
 * @author jycchoi@bu.edu.
 * @version 1.0.0.
 */
public class Credentials {

    /**
     * UniqueID for this User.
     */
    private UniqueID id;
    /**
     * Email for this User.
     */
    private String email;
    /**
     * Password for this User.
     */
    private char[] password;

    /**
     * Base Constructor for the User Credentials Object.
     *
     * @param id       UniqueID for this User.
     * @param email    Email for this User.
     * @param password Password for this User.
     */
    public Credentials(UniqueID id, String email, char[] password) {
        this.setId(id);
        this.setEmail(email);
        this.setPassword(password);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Instantiation Type.
     */
    public Credentials() throws InstantiationException {
        throw new InstantiationException(GeneralConfig.ILLEGAL_EMPTY_CONSTRUCTOR_ERROR);
    }

    /**
     * Get the UniqueID for this User.
     *
     * @return UniqueID for this User.
     */
    public UniqueID getId() {
        return this.id;
    }

    /**
     * Set the UniqueID for this User.
     *
     * @param id UniqueID for this User.
     */
    public void setId(UniqueID id) {
        this.id = id;
    }

    /**
     * Get the Email for this User.
     *
     * @return Email for this User.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Set the Email for this User.
     *
     * @param email Email for this User.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the Password for this User.
     *
     * @return Password for this User.
     */
    public char[] getPassword() {
        return this.password;
    }

    /**
     * Set the Password for this User.
     *
     * @param password Password for this User.
     */
    public void setPassword(char[] password) {
        this.password = password;
    }

    /**
     * Helper Method to check whether these Credentials are equal to another set of Credentials.
     *
     * @param o Object to compare to.
     * @return Whether these Credentials are equal to another set of Credentials.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Credentials castedObject)
            return (this.getEmail().split(UsersConfig.EMAIL_SPLITTER)[0]
                    .equalsIgnoreCase(castedObject.getEmail().split(UsersConfig.EMAIL_SPLITTER)[0])
                    || this.getEmail().equalsIgnoreCase(castedObject.getEmail()))
                    && Arrays.equals(this.getPassword(), castedObject.getPassword());
        return false;
    }

}
