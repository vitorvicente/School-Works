package edu.bu.cs411.UI.Actions.Navigation;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.UI.Actions.Actual.AddCourse;
import edu.bu.cs411.UI.Actions.GUIAction;
import edu.bu.cs411.UI.PageIndex;
import edu.bu.cs411.UI.Screens.Admin.CourseAddScreen;
import edu.bu.cs411.UI.Screens.GUIScreen;
import edu.bu.cs411.Users.Admin;
import edu.bu.cs411.Users.Professor;
import edu.bu.cs411.Users.User;
import edu.bu.cs411.Users.Util.UniqueID;

import java.awt.event.ActionEvent;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Course Add Navigation Action Class.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class GoToCourseAdd extends GUIAction {

    /**
     * Action Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Base Constructor for the Course Add Page Navigation Action.
     * Initializes the data required.
     *
     * @param displayName Action Display Name.
     * @param pageIndex   Reference to the overall GUI.
     */
    public GoToCourseAdd(String displayName, PageIndex pageIndex) {
        super(displayName, pageIndex);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public GoToCourseAdd() throws InstantiationException {
        super();
    }

    /**
     * Static Factory Method to return the Course Add Screen.
     *
     * @param extraMessage Error/Success Message to Display on the Screen.
     * @param pageIndex    Reference to the PageIndex Software.
     * @param user         Logged-In User.
     * @return Base Course Add Screen.
     */
    public static GUIScreen getScreen(String extraMessage, PageIndex pageIndex, User user) {
        CourseAddScreen newScreen = new CourseAddScreen();

        ArrayList<UniqueID> professors = new ArrayList<>();
        for (Professor professor : pageIndex.getSoftware().getUserListing().getProfessors())
            professors.add(professor.getID());

        newScreen.setUser(user);
        newScreen.setProfessors(professors);
        newScreen.initialize(new ArrayList<>(
                Arrays.asList(
                        new AddCourse(GUIConfig.ADD_COURSE_ACTION_NAME, pageIndex),
                        new GoToCourseList(GUIConfig.BACK_ACTION_NAME, pageIndex)
                )
        ), extraMessage);

        return newScreen;
    }

    /**
     * Perform the Course Add Navigation Action by creating a new Screen and opening it.
     * <p>
     * If the Logged-In User isn't an Admin, it fails and displays an error message on screen.
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

        newScreen = GoToCourseAdd.getScreen(GUIConfig.EMPTY_MSG, this.getPageIndex(), user);
        this.getPageIndex().setFrame(newScreen);
    }

}
