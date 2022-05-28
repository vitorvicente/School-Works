package GUI.Client.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Client.EditBasicInfoScreen;
import GUI.Client.Actions.SaveEditedInfo;
import Users.Client;

public class GoToEditInfo extends BankAction {

	private static final long serialVersionUID = 1L;

	private Client client;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToEditInfo(BankGUI gui, Client client) {
		super("Edit Basic Info", gui);
		this.client = client;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the Client to the Edit Info Screen.
	 */
	public void actionPerformed(ActionEvent e) {
		EditBasicInfoScreen newScreen = new EditBasicInfoScreen();
		newScreen.initialize(new ArrayList<BankAction>(Arrays.asList(new BankAction[] {
				new SaveEditedInfo(this.getGUI(), client, newScreen), new GoToClientMain(this.getGUI(), client) })),
				"");
		this.getGUI().setFrame(newScreen);
	}

}
