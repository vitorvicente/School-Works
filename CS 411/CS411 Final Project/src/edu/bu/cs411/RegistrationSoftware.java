package edu.bu.cs411;

import edu.bu.cs411.Courses.CourseListing;
import edu.bu.cs411.Data.DataManager;
import edu.bu.cs411.Registrar.RegistrationDetails;
import edu.bu.cs411.UI.PageIndex;
import edu.bu.cs411.Users.UserListing;

/**
 * Main Entry Class for the overall Software.
 *
 * @author dsullo@bu.edu.
 * @version 1.0.0.
 */
public class RegistrationSoftware {

    /**
     * Reference to the UserListing Object.
     */
    private UserListing userListing;
    /**
     * Reference to the CourseListing Object.
     */
    private CourseListing courseListing;
    /**
     * Reference to the RegistrationDetails Object.
     */
    private RegistrationDetails registrationDetails;
    /**
     * Reference to the overall GUI Object.
     */
    private PageIndex pageIndex;
    /**
     * Reference to the DataManager Object.
     */
    private DataManager dataManager;

    /**
     * Base Constructor for the Registration Software.
     *
     * @param userListing         Reference for the UserListing Object
     * @param courseListing       Reference for the CourseListing Object.
     * @param registrationDetails Reference for the RegistrationDetails Object.
     */
    public RegistrationSoftware(UserListing userListing, CourseListing courseListing,
                                RegistrationDetails registrationDetails) {
        this.setUserListing(userListing);
        this.setCourseListing(courseListing);
        this.setRegistrationDetails(registrationDetails);
        this.setPageIndex(new PageIndex(this));
        this.setDataManager(new DataManager());
    }

    /**
     * No Args Constructor for the Registration Software.
     */
    public RegistrationSoftware() {
        this.setPageIndex(new PageIndex(this));
        this.setDataManager(new DataManager());
    }

    /**
     * Overall Start Program Method.
     * Loads all Data and initializes the GUI.
     */
    public void startProgram() {
        this.setCourseListing(this.getDataManager().loadCourses());
        this.setUserListing(this.getDataManager().loadUsers());
        this.setRegistrationDetails(this.getDataManager().loadRegDetails());

        this.getPageIndex().initialize();
    }

    /**
     * Overall End Program Method.
     * Saves all Data before it closes.
     */
    public void endProgram() {
        this.getDataManager().saveCourses(this.getCourseListing());
        this.getDataManager().saveUsers(this.getUserListing());
        this.getDataManager().saveRegDetails(this.getRegistrationDetails());
    }

    /**
     * Gets the Reference to the UserListing Object.
     *
     * @return Reference to the UserListing Object.
     */
    public UserListing getUserListing() {
        return userListing;
    }

    /**
     * Sets the Reference to the UserListing Object.
     *
     * @param userListing Reference to the UserListing Object.
     */
    public void setUserListing(UserListing userListing) {
        this.userListing = userListing;
    }

    /**
     * Gets the Reference to the CourseListing Object.
     *
     * @return Reference to the CourseListing Object.
     */
    public CourseListing getCourseListing() {
        return courseListing;
    }

    /**
     * Sets the Reference to the CourseListing Object.
     *
     * @param courseListing Reference to the CourseListing Object.
     */
    public void setCourseListing(CourseListing courseListing) {
        this.courseListing = courseListing;
    }

    /**
     * Gets the Reference to the RegistrationDetails Object.
     *
     * @return Reference to the RegistrationDetails Object.
     */
    public RegistrationDetails getRegistrationDetails() {
        return registrationDetails;
    }

    /**
     * Sets the Reference to the RegistrationDetails Object.
     *
     * @param registrationDetails Reference to the RegistrationDetails Object.
     */
    public void setRegistrationDetails(RegistrationDetails registrationDetails) {
        this.registrationDetails = registrationDetails;
    }

    /**
     * Gets the Reference to the overall GUI Object.
     *
     * @return Reference to the overall GUI Object.
     */
    public PageIndex getPageIndex() {
        return pageIndex;
    }

    /**
     * Sets the Reference to the overall GUI Object.
     *
     * @param pageIndex Reference to the overall GUI Object.
     */
    public void setPageIndex(PageIndex pageIndex) {
        this.pageIndex = pageIndex;
    }

    /**
     * Gets the Reference to the DataManager Object.
     *
     * @return Reference to the DataManager Object.
     */
    public DataManager getDataManager() {
        return dataManager;
    }

    /**
     * Sets the Reference to the DataManager Object.
     *
     * @param dataManager Reference to the DataManager Object.
     */
    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

}
