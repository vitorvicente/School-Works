package edu.bu.cs411.Data.Transformers;

import edu.bu.cs411.Config.DataConfig;
import edu.bu.cs411.Config.RegistrarConfig;
import edu.bu.cs411.Registrar.RegistrationDetails;
import edu.bu.cs411.Registrar.Semester;

/**
 * Parser for Saving/Loading RegDetails.
 * <p>
 * Admin CSV Format: "overAllOpen,currentSemester".
 * Replacing the ',' with DataConfig.CSV_DELIMITER_ONE.
 *
 * @author evanlhsu@bu.edu.
 * @version 1.0.0.
 */
public class StringToRegDetails extends Reversible<String, RegistrationDetails> {

    /**
     * Transform the RegistrationDetails Object into a String.
     *
     * @param registrationDetails RegistrationDetails Object to Transform.
     * @return String.
     */
    @Override
    public String reverse(RegistrationDetails registrationDetails) {
        String overAllOpen = String.valueOf(registrationDetails.isOverAllOpen());
        String currentSemester = registrationDetails.getCurrentSemester().toString();

        return String.join(DataConfig.CSV_DELIMITER_ONE, overAllOpen, currentSemester);
    }

    /**
     * Transform the RegistrationDetails into a Professor Object.
     *
     * @param s String to Transform.
     * @return RegistrationDetails Object.
     */
    @Override
    public RegistrationDetails apply(String s) {
        String[] regDetailsArr = s.split(DataConfig.CSV_DELIMITER_ONE);

        if (regDetailsArr.length != 2)
            return null;

        boolean overAllOpen = Boolean.parseBoolean(regDetailsArr[0]);
        Semester currentSemester;
        try {
            currentSemester = new Semester(regDetailsArr[1]);
        } catch (InstantiationException e) {
            e.printStackTrace();
            currentSemester = RegistrarConfig.DEFAULT_SEMESTER;
        }

        return new RegistrationDetails(overAllOpen, currentSemester);
    }

}
