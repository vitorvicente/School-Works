package edu.bu.cs411.UI.Actions.Navigation;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.PageIndex;
import edu.bu.cs411.UI.Screens.Admin.UserListScreen;
import edu.bu.cs411.UI.Screens.GUIScreen;
import edu.bu.cs411.Users.Admin;
import edu.bu.cs411.Users.Professor;
import edu.bu.cs411.Users.Student;
import edu.bu.cs411.Users.User;
import edu.bu.cs411.Users.Util.UniqueID;

import java.awt.event.ActionEvent;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * User List Navigation Action Class.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class GoToUserList extends GUIAction {

    /**
     * Action Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Checks if the creation required of it is for a List of Students, or a List of Professors.
     */
    private boolean studentsOrProfessors;

    /**
     * Base Constructor for the User List Navigation Action.
     * Initializes the data required.
     *
     * @param displayName          Action Display Name.
     * @param pageIndex            Reference to the overall GUI.
     * @param studentsOrProfessors Whether the creation required is for a List of Students or Professors.
     */
    public GoToUserList(String displayName, PageIndex pageIndex, boolean studentsOrProfessors) {
        super(displayName, pageIndex);
        this.setStudentsOrProfessors(studentsOrProfessors);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public GoToUserList() throws InstantiationException {
        super();
    }

    /**
     * Static Factory Method to return the User List Screen.
     *
     * @param extraMessage         Error/Success Message to Display on the Screen.
     * @param pageIndex            Reference to the PageIndex Software.
     * @param user                 Logged-In User.
     * @param studentsOrProfessors Whether the creation required is for a List of Students or Professors.
     * @return Base Schedule Screen.
     */
    public static GUIScreen getScreen(String extraMessage, PageIndex pageIndex, User user,
                                      boolean studentsOrProfessors) {
        UserListScreen newScreen = new UserListScreen();

        ArrayList<UniqueID> users = new ArrayList<>();
        if (studentsOrProfessors) {
            for (Student student : pageIndex.getSoftware().getUserListing().getStudents())
                users.add(student.getID());
        } else {
            for (Professor professor : pageIndex.getSoftware().getUserListing().getProfessors())
                users.add(professor.getID());
        }

        newScreen.setUser(user);
        newScreen.setStudentsOrProfessors(studentsOrProfessors);
        newScreen.setUsers(users);
        newScreen.initialize(new ArrayList<>(
                List.of(
                        new GoToUserCourseList(GUIConfig.VIEW_USER_COURSE_LIST_ACTION_NAME, pageIndex),
                        new GoToAdminNav(GUIConfig.BACK_ACTION_NAME, pageIndex)
                )
        ), extraMessage);

        return newScreen;
    }

    /**
     * Perform the User List Navigation Action by creating a new Screen and opening it.
     * <p>
     * If the Logged-In User isn't an admin, it fails and displays an error message on the screen.
     *
     * @param e ActionEvent for the performed action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        GUIScreen oldScreen = (GUIScreen) this.getPageIndex().getFrame();
        User user = oldScreen.getUser();
        GUIScreen newScreen;

        // Checks permissions
        if (!(user instanceof Admin)) {
            newScreen = GUIScreen.goToOldScreen(oldScreen, GUIConfig.NO_PERMISSIONS_MSG, this.getPageIndex());
            this.getPageIndex().setFrame(newScreen);
            return;
        }

        newScreen = GoToUserList.getScreen(GUIConfig.EMPTY_MSG, this.getPageIndex(), user,
                this.getStudentsOrProfessors());
        this.getPageIndex().setFrame(newScreen);
    }

    /**
     * Returns whether the creation required is for a List of Students, or a List of Professors.
     *
     * @return Whether the creation required is for a List of Students or Professors.
     */
    public boolean getStudentsOrProfessors() {
        return this.studentsOrProfessors;
    }

    /**
     * Sets whether the creation required is for a List of Students, or a List of Professors.
     *
     * @param studentsOrProfessors Whether the creation required is for a List of Students or Professors.
     */
    public void setStudentsOrProfessors(boolean studentsOrProfessors) {
        this.studentsOrProfessors = studentsOrProfessors;
    }

}
