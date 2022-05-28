package edu.bu.cs411.Registrar;

import edu.bu.cs411.Config.GeneralConfig;
import edu.bu.cs411.Config.RegistrarConfig;

/**
 * ENUM Representing all the possible Term Types.
 * Includes Helper Methods to deal with managing Terms.
 *
 * @author dsullo@bu.edu.
 * @version 1.0.0.
 */
public enum Term {
    /**
     * Possible Values
     */
    SPRING, SUMMER, FALL;

    /**
     * Static Helper Method to get a Term from its String Value.
     *
     * @param term String Value to get the Term for.
     * @return Term Value obtained from String.
     */
    public static Term getTerm(String term) {
        for (Term t : Term.values()) {
            if (t.toString().equalsIgnoreCase(term))
                return t;
        }
        throw new IllegalArgumentException(GeneralConfig.ILLEGAL_ARGUMENTS_ERROR);
    }

    /**
     * Helper Method to get the next Term in the cycle.
     *
     * @return Next Term in the cycle.
     */
    public Term next() {
        return switch (this) {
            case SPRING -> SUMMER;
            case SUMMER -> FALL;
            case FALL -> SPRING;
        };
    }

    /**
     * Retrieve the String Value for the Term.
     *
     * @return String Value for the Term.
     */
    @Override
    public String toString() {
        return switch (this) {
            case SPRING -> RegistrarConfig.SPRING_TERM_STRING_VALUE;
            case SUMMER -> RegistrarConfig.SUMMER_TERM_STRING_VALUE;
            case FALL -> RegistrarConfig.FALL_TERM_STRING_VALUE;
        };
    }

}
