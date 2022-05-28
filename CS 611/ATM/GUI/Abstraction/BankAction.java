package GUI.Abstraction;

import javax.swing.AbstractAction;

import GUI.BankGUI;

public abstract class BankAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private BankGUI gui;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	/*
	 * This abstract constructor runs some basic functions that every action coming
	 * from the BankGUI share.
	 */
	public BankAction(String displayName, BankGUI gui) {
		this.setGUI(gui);
		putValue(NAME, displayName);
		putValue(SHORT_DESCRIPTION, displayName);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public BankGUI getGUI() {
		return gui;
	}

	public void setGUI(BankGUI gui) {
		this.gui = gui;
	}

}
