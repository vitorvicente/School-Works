package edu.bu.cs411.Config;

/**
 * Config Class for all shared details.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class GeneralConfig {

    /**
     * Error Message: Illegal No Args Constructors.
     */
    public static final String ILLEGAL_EMPTY_CONSTRUCTOR_ERROR = "Illegal No Args Constructor.";
    /**
     * Error Message: Illegal Arguments Provided.
     */
    public static final String ILLEGAL_ARGUMENTS_ERROR = "Illegal Arguments Provided.";

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public GeneralConfig() throws InstantiationException {
        throw new InstantiationException(GeneralConfig.ILLEGAL_EMPTY_CONSTRUCTOR_ERROR);
    }

}
