package edu.bu.cs411.Util;

import edu.bu.cs411.Config.GeneralConfig;

/**
 * ENUM Representing all the possible Department Types.
 * Includes Helper Methods to deal with managing Department.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public enum Department {
    AA, AH, AI, AL, AM, AN, AR, AS, BB, BF, BI, CC, CG, CH, CI, CL, CS, EC, EE, EI, EN, ES, FR, FY, GE, HI, HU, ID,
    IN, IR, IT, JS, LA, LC, LD, LE, LF, LG, LH, LI, LJ, LK, LL, LM, LN, LO, LP, LR, LS, LT, LU, LW, LX, LY, LZ, MA,
    ME, MR, MS, MU, NE, NG, NS, PH, PO, PS, PY, QU, RN, RO, SO, SP, SS, SY, WR, WS, XL, TH, FA, ML, RH, CM, CO, EM,
    FT, JO, BE, EK, SE, BC, BN, BY, FS, GC, IM, MH, MI, MM, NU, OB, OH, PA, PM, BK, JD, XB, TX, AD, AT, BT, CJ, HC,
    IS, MG, UA, AQ, DA, ER, GS, HE, MB, NT, OE, PE, SK, WF, AC, DS, FE, FI, HM, MF, MK, OM, PL, QM, SI, SM, HP, HS,
    OT, PT, RS, SH, GD, MD, OD, OP, OR, OS, PD, PR, AP, CE, CT, DE, ED, HR, IE, SC, TL, YJ, BS, EH, EP, IH, MC, SB,
    CP, HB, MP, SR, WP, TA, TC, TE, TF, TJ, TM, TN, TO, TR, TS, TT, TY, TZ, BD, HD, SJ;

    /**
     * Static Helper Method to get a Department from its String Value.
     *
     * @param department Value to get the Department for.
     * @return Department Value obtained from String.
     */
    public static Department getDepartment(String department) {
        for (Department dep : Department.values()) {
            if (dep.toString().equalsIgnoreCase(department))
                return dep;
        }
        throw new IllegalArgumentException(GeneralConfig.ILLEGAL_ARGUMENTS_ERROR);
    }

    /**
     * Retrieve the String Value for the Department.
     *
     * @return String Value for the Department.
     */
    @Override
    public String toString() {
        return this.name();
    }

}
