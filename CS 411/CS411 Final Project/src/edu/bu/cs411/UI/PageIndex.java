package edu.bu.cs411.UI;

import edu.bu.cs411.Config.GUIConfig;
import edu.bu.cs411.Config.GeneralConfig;
import edu.bu.cs411.RegistrationSoftware;
import edu.bu.cs411.UI.Actions.Navigation.GoToLogin;
import edu.bu.cs411.UI.Screens.GUIScreen;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Main entry class for the program GUI, registers the software and initializes the first frame.
 *
 * @author vitor@bu.edu.
 * @version 1.0.0.
 */
public class PageIndex {

    /**
     * Quick overall access to the Backend by the GUI.
     */
    private RegistrationSoftware software;
    /**
     * Display Screen.
     */
    private JFrame frame;

    /**
     * Base Constructor for the GUI.
     * Saves the Backend reference and initiates the GUI.
     *
     * @param software Reference to the Backend.
     */
    public PageIndex(RegistrationSoftware software) {
        this.setSoftware(software);
    }

    /**
     * No Args Constructor.
     *
     * @throws InstantiationException Illegal Initiation Type.
     */
    public PageIndex() throws InstantiationException {
        throw new InstantiationException(GeneralConfig.ILLEGAL_ARGUMENTS_ERROR);
    }

    /**
     * GUI Initialization Method.
     * Loads up the Login Screen and opens it.
     */
    public void initialize() {
        this.frame = GoToLogin.getScreen(GUIConfig.EMPTY_MSG, this);
        this.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                getSoftware().endProgram();
            }
        });
        this.toggleVisible();
    }

    /**
     * Get the Reference to the Backend Software.
     *
     * @return Backend Object Reference.
     */
    public RegistrationSoftware getSoftware() {
        return software;
    }

    /**
     * Sets the Reference to the Backend Software.
     *
     * @param software Backend Object Reference.
     */
    public void setSoftware(RegistrationSoftware software) {
        this.software = software;
    }

    /**
     * Get the current Display Screen.
     *
     * @return Current Display Screen.
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Sets the current Display Screen, and opens it.
     * Adds the proper closing method, to make sure data is always saved on Close.
     *
     * @param frame Screen to Display.
     */
    public void setFrame(JFrame frame) {
        this.toggleVisible();
        this.frame = frame;

        this.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                getSoftware().endProgram();
            }
        });

        this.toggleVisible();
    }

    /**
     * Toggle the visibility of the GUI.
     */
    public void toggleVisible() {
        this.getFrame().setVisible(!this.getFrame().isVisible());
    }

}
