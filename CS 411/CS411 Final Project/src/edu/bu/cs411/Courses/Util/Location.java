package edu.bu.cs411.Courses.Util;

import edu.bu.cs411.Config.CoursesConfig;
import edu.bu.cs411.Config.GeneralConfig;

/**
 * Course Location Object Class.
 *
 * @author ejeon@bu.edu.
 * @version 1.0.0.
 */
public class Location {

    /**
     * Current BuildingCode for this Location.
     */
    private BuildingCode buildingCode;
    /**
     * Current Room for this Location.
     */
    private int room;

    /**
     * Base Constructor for the Location Object.
     *
     * @param buildingCode Current BuildingCode for this Location.
     * @param room         Current Room for this Location.
     */
    public Location(BuildingCode buildingCode, int room) {
        this.setBuildingCode(buildingCode);
        this.setRoom(room);
    }

    /**
     * String Constructor for the Location Object.
     *
     * @param location String Value for the Location.
     * @throws InstantiationException Illegal Initiation Parameters.
     */
    public Location(String location) throws InstantiationException {
        String[] splitLocation = location.split(CoursesConfig.LOCATION_SPLITTER);
        if (splitLocation.length != 2)
            throw new InstantiationException(GeneralConfig.ILLEGAL_ARGUMENTS_ERROR);

        this.setBuildingCode(BuildingCode.getBuildingCode(splitLocation[0]));
        this.setRoom(Integer.parseInt(splitLocation[1]));
    }

    /**
     * No Args Constructor.
     */
    public Location() {
    }

    /**
     * Get the Current BuildingCode for this Location.
     *
     * @return Current BuildingCode for this Location.
     */
    public BuildingCode getBuildingCode() {
        return this.buildingCode;
    }

    /**
     * Set the Current BuildingCode for this Location.
     *
     * @param buildingCode Current BuildingCode for this Location.
     */
    public void setBuildingCode(BuildingCode buildingCode) {
        this.buildingCode = buildingCode;
    }

    /**
     * Get the Current Room for this Location.
     *
     * @return Current Room for this Location.
     */
    public int getRoom() {
        return this.room;
    }

    /**
     * Set the Current Room for this Location.
     *
     * @param room Current Room for this Location.
     */
    public void setRoom(int room) {
        this.room = room;
    }

    /**
     * Helper Method to check whether this Location is equal to another Location.
     *
     * @param o Object to compare to.
     * @return Whether this Location is equal to another Location.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Location castedObject)
            return this.getBuildingCode().equals(castedObject.getBuildingCode())
                    && this.getRoom() == castedObject.getRoom();

        return false;
    }

    /**
     * Retrieve the String Value for the Location.
     *
     * @return String Value for the Location.
     */
    @Override
    public String toString() {
        return this.getBuildingCode().toString() + CoursesConfig.LOCATION_SPLITTER + this.getRoom();
    }

}
