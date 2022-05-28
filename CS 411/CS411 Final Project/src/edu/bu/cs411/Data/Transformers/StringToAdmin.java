package edu.bu.cs411.Data.Transformers;

import edu.bu.cs411.Config.DataConfig;
import edu.bu.cs411.Config.UsersConfig;
import edu.bu.cs411.Users.Admin;
import edu.bu.cs411.Users.Util.UserDetails;
import edu.bu.cs411.Users.Util.Credentials;
import edu.bu.cs411.Users.Util.UniqueID;
import edu.bu.cs411.Util.College;

/**
 * Parser for Saving/Loading Admins.
 *
 * Admin CSV Format: "id,firstName,lastName,email,password,college".
 * Replacing the ',' with DataConfig.CSV_DELIMITER_ONE.
 *
 * @author evanlhsu@bu.edu.
 * @version 1.0.0.
 */
public class StringToAdmin extends Reversible<String, Admin> {

    /**
     * Transform an Admin Object into a String.
     *
     * @param admin Admin Object to Transform.
     * @return String.
     */
    @Override
    public String reverse(Admin admin) {
        UserDetails userDetails = admin.getDetails();
        String id = userDetails.getId().toString();
        String firstName = userDetails.getFirstName();
        String lastName = userDetails.getLastName();

        Credentials credentials = admin.getCredentials();
        String email = credentials.getEmail();
        String password = String.valueOf(credentials.getPassword());

        String college = admin.getCollege().toString();

        return String.join(DataConfig.CSV_DELIMITER_ONE, id, firstName, lastName, email, password, college);
    }

    /**
     * Transform a String into an Admin Object.
     *
     * @param s String to Transform.
     * @return Admin Object.
     */
    @Override
    public Admin apply(String s) {
        String[] adminArr = s.split(DataConfig.CSV_DELIMITER_ONE);

        if (adminArr.length != 6)
            return null;

        UniqueID uniqueID;
        try {
            uniqueID = new UniqueID(adminArr[0]);
        } catch (InstantiationException e) {
            e.printStackTrace();
            uniqueID = UsersConfig.DEFAULT_ID;
        }
        Credentials credentials = new Credentials(uniqueID, adminArr[3], adminArr[4].toCharArray());
        UserDetails userDetails = new UserDetails(uniqueID, adminArr[1], adminArr[2]);
        College college = College.valueOf(adminArr[5]);

        return new Admin(credentials, userDetails, college);
    }

}
