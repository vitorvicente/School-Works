package edu.bu.cs411.Users.Util;

import edu.bu.cs411.Config.GeneralConfig;

/**
 * UserDetails Object Class.
 *
 * @author jycchoi@bu.edu.
 * @version 1.0.0.
 */
public class UserDetails {

    /**
     * UniqueID for this User.
     */
    private UniqueID id;
    /**
     * First Name of this User.
     */
    private String firstName;
    /**
     * Last Name of this User.
     */
    private String lastName;

    /**
     * Base Constructor for the UserDetails Object.
     *
     * @param id        UniqueID for this User.
     * @param firstName First Name of this User.
     * @param lastName  Last Name of this User.
     */
    public UserDetails(UniqueID id, String firstName, String lastName) {
        this.setId(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Instantiation Type.
     */
    public UserDetails() throws InstantiationException {
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
     * Get the First Name of this User.
     *
     * @return First Name of this User.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Set the First Name of this User.
     *
     * @param firstName First Name of this User.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the Last Name of this User.
     *
     * @return Last Name of this User.
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Set the Last Name of this User.
     *
     * @param lastName Last Name of this User.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Helper Method to check whether these UserDetails are equal to other UserDetails.
     *
     * @param o Object to compare to.
     * @return Whether these UserDetails are equal to other UserDetails.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof UserDetails castedObject)
            return this.getId().equals(castedObject.getId());

        return false;
    }

    /**
     * Retrieve the String Value for the UserDetails.
     *
     * @return String Value for the UserDetails.
     */
    @Override
    public String toString() {
        return this.getId().toString();
    }

}
