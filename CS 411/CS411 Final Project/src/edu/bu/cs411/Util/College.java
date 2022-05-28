package edu.bu.cs411.Util;

import edu.bu.cs411.Config.GeneralConfig;

/**
 * ENUM Representing all the possible College Types.
 * Includes Helper Methods to deal with managing Colleges.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public enum College {
    /**
     * Possible Values
     */
    BUA, CAS, CFA, CGS, COM, ENG, EGS, EOP, GMS, GRS,
    HUB, KHC, LAW, MED, MET, OTP, PDP, QST, SAR, SED,
    SDM, SHA, SPH, SSW, STH, SUM, UNI, XRG;

    /**
     * Static Helper Method to get a College from its String Value.
     *
     * @param college Value to get the College for.
     * @return College Value obtained from String.
     */
    public static College getCollege(String college) {
        for (College col : College.values()) {
            if (col.toString().equalsIgnoreCase(college))
                return col;
        }
        throw new IllegalArgumentException(GeneralConfig.ILLEGAL_ARGUMENTS_ERROR);
    }

    /**
     * Retrieve the String Value for the College.
     *
     * @return String Value for the College.
     */
    @Override
    public String toString() {
        return this.name();
    }

}
