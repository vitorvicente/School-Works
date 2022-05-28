package GUI.Manager.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Manager.EditBasicInfoScreen;
import GUI.Manager.Actions.SaveEditedInfo;
import Users.Manager;

public class GoToEditInfo extends BankAction {

	private static final long serialVersionUID = 1L;

	private Manager manager;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToEditInfo(BankGUI gui, Manager manager) {
		super("Edit Personal Info", gui);
		this.manager = manager;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the Manager to the Edit Info Screen.
	 */
	public void actionPerformed(ActionEvent e) {
		EditBasicInfoScreen newScreen = new EditBasicInfoScreen(manager);
		newScreen.initialize(new ArrayList<BankAction>(Arrays.asList(new BankAction[] {
				new SaveEditedInfo(this.getGUI(), manager, newScreen), new GoToManagerMain(this.getGUI(), manager) })),
				"");
		this.getGUI().setFrame(newScreen);
	}

}
