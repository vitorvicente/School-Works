package edu.bu.cs411.UI.Actions;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.Config.GeneralConfig;
import edu.bu.cs411.UI.PageIndex;

import javax.swing.*;
import java.io.Serial;

/**
 * Abstract Class to represent all GUI Actions.
 * Includes the reference to the overall GUI.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public abstract class GUIAction extends AbstractAction {

    /**
     * Screen Serial UID.
     */
    @Serial
    private static final long serialVersionUID = GUIConfig.SERIAL_VERSION_UID;

    /**
     * Reference to the overall GUI.
     */
    private PageIndex pageIndex;

    /**
     * Base Constructor for the Abstract GUI Action.
     *
     * @param displayName Action Display Name.
     * @param pageIndex   Reference to the overall GUI.
     */
    public GUIAction(String displayName, PageIndex pageIndex) {
        this.setPageIndex(pageIndex);
        putValue(NAME, displayName);
        putValue(SHORT_DESCRIPTION, displayName);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public GUIAction() throws InstantiationException {
        throw new InstantiationException(GeneralConfig.ILLEGAL_EMPTY_CONSTRUCTOR_ERROR);
    }

    /**
     * Gets the Reference to the overall GUI.
     *
     * @return Reference to the overall GUI.
     */
    public PageIndex getPageIndex() {
        return pageIndex;
    }

    /**
     * Sets the Reference to the overall GUI.
     *
     * @param pageIndex Reference to the overall GUI.
     */
    public void setPageIndex(PageIndex pageIndex) {
        this.pageIndex = pageIndex;
    }

}
