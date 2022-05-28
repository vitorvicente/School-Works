package edu.bu.cs411.Config;

/**
 * Config Class for all details for the DataManager.
 *
 * @author evanlhsu@bu.edu.
 * @version 1.0.0.
 */
public class DataConfig {

    /**
     * Main CSV Object Delimiter.
     */
    public static final String CSV_DELIMITER_ONE = ",";
    /**
     * Secondary CSV Object Delimiter.
     */
    public static final String CSV_DELIMITER_TWO = "|";

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public DataConfig() throws InstantiationException {
        throw new InstantiationException("Illegal No Args Constructor");
    }

}
