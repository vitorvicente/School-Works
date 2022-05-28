package edu.bu.cs411.Users.Util;

import edu.bu.cs411.Config.GeneralConfig;
import edu.bu.cs411.Config.UsersConfig;

/**
 * User UniqueID Object Class.
 *
 * @author jycchoi@bu.edu.
 * @version 1.0.0.
 */
public class UniqueID {
    /**
     * Unique ID Letter Code.
     */
    private char idLetter;
    /**
     * Unique ID Number Code.
     */
    private long idNumber;

    /**
     * Base Constructor for the UniqueID Object.
     *
     * @param idLetter Unique ID Letter Code.
     * @param idNumber Unique ID Number Code.
     */
    public UniqueID(char idLetter, long idNumber) {
        this.setIdLetter(idLetter);
        this.setIdNumber(idNumber);
    }

    /**
     * String Constructor for the UniqueID Object.
     *
     * @param uniqueID String Value for the UniqueID.
     * @throws InstantiationException Illegal Initiation Parameters.
     */
    public UniqueID(String uniqueID) throws InstantiationException {
        String[] splitID = uniqueID.split(UsersConfig.UNIQUE_ID_SPLITTER);
        if (splitID.length != 2)
            throw new InstantiationException(GeneralConfig.ILLEGAL_ARGUMENTS_ERROR);

        this.setIdLetter(splitID[0].charAt(0));
        this.setIdNumber(Long.parseLong(splitID[1]));
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Instantiation Type.
     */
    public UniqueID() throws InstantiationException {
        throw new InstantiationException(GeneralConfig.ILLEGAL_EMPTY_CONSTRUCTOR_ERROR);
    }

    /**
     * Get the Unique ID Letter Code.
     *
     * @return Unique ID Letter Code.
     */
    public char getIdLetter() {
        return this.idLetter;
    }

    /**
     * Set the Unique ID Letter Code.
     *
     * @param idLetter Unique ID Letter Code.
     */
    public void setIdLetter(char idLetter) {
        this.idLetter = idLetter;
    }

    /**
     * Get the Unique ID Number Code.
     *
     * @return Unique ID Number Code.
     */
    public long getIdNumber() {
        return this.idNumber;
    }

    /**
     * Set the Unique ID Number Code.
     *
     * @param idNumber Unique ID Number Code.
     */
    public void setIdNumber(long idNumber) {
        this.idNumber = idNumber;
    }

    /**
     * Helper Method to check whether this UniqueID is equal to another UniqueID.
     *
     * @param o Object to compare to.
     * @return Whether this UniqueID is equal to another UniqueID.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof UniqueID castedObject)
            return (this.getIdLetter() == castedObject.getIdLetter())
                    && (this.getIdNumber() == castedObject.getIdNumber());

        return false;
    }

    /**
     * Retrieve the String Value for the UniqueID.
     *
     * @return String Value for the UniqueID.
     */
    @Override
    public String toString() {
        return this.getIdLetter() + UsersConfig.UNIQUE_ID_SPLITTER + this.getIdNumber();
    }

}
