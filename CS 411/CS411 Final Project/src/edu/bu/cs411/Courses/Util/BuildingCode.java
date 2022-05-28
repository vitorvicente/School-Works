package edu.bu.cs411.Courses.Util;

import edu.bu.cs411.Config.GeneralConfig;

/**
 * ENUM Representing all the possible BuildingCode Types.
 * Includes Helper Methods to deal with managing BuildingCode.
 *
 * @author ejeon@bu.edu.
 * @version 1.0.0.
 */
public enum BuildingCode {
    /**
     * Possible Values
     */
    AAA, BAB, BRB, BSC, CAD, CAS, CFA, CGS, CLN, COM, CRW, CSE, EGL, EID, EIL, EMA, EMB, ENG, EOP, ERA, EPC, ERB, FAB,
    FCB, FCC, FLR, FOB, FRC, GRS, GSU, HAR, HAW, HIS, IEC, IRB, IRC, JSC, KCB, KHC, LAW, LNG, LSE, MAR, MCS, MED,
    MET, MOR, MUG, PHO, PLS, PRB, PSY, PTH, REL, SAC, SAL, SAR, SCI, SHA, SLB, SOC, SSW, STH, STO, TTC, THA, WEA, WED,
    YAW, B, CT, E, G, K, L, R, S, T, X, NA;

    /**
     * Static Helper Method to get a BuildingCode from its String Value.
     *
     * @param buildingCode Value to get the BuildingCode for.
     * @return BuildingCode Value obtained from String.
     */
    public static BuildingCode getBuildingCode(String buildingCode) {
        for (BuildingCode bc : BuildingCode.values()) {
            if (bc.toString().equalsIgnoreCase(buildingCode))
                return bc;
        }
        throw new IllegalArgumentException(GeneralConfig.ILLEGAL_ARGUMENTS_ERROR);
    }

    /**
     * Retrieve the String Value for the BuildingCode.
     *
     * @return String Value for the BuildingCode.
     */
    @Override
    public String toString() {
        return this.name();
    }

}
