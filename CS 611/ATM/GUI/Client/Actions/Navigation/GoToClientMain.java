package GUI.Client.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Client.ClientMainScreen;
import GUI.General.Actions.Navigation.GoToLogin;
import Users.Client;

public class GoToClientMain extends BankAction {

	private static final long serialVersionUID = 1L;

	private Client client;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToClientMain(BankGUI gui, Client client) {
		super("Back", gui);
		this.client = client;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the Client to the Client Main Screen.
	 */
	public void actionPerformed(ActionEvent e) {
		ClientMainScreen newScreen = new ClientMainScreen(client);
		newScreen.initialize(new ArrayList<BankAction>(Arrays.asList(new BankAction[] {
				new GoToEditInfo(this.getGUI(), client), new GoToLogin("Logout", this.getGUI()),
				new GoToAccounts("View Accounts", this.getGUI(), client),
				new GoToLoans("View Loans", this.getGUI(), client), new GoToTransactions(this.getGUI(), client) })),
				"");
		this.getGUI().setFrame(newScreen);
	}

}
