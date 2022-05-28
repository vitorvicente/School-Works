package GUI.Client.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Client.AccountsScreen;
import Users.Client;

public class GoToAccounts extends BankAction {

	private static final long serialVersionUID = 1L;

	private Client client;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToAccounts(String name, BankGUI gui, Client client) {
		super(name, gui);
		this.client = client;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the Client to the Accounts Screen.
	 */
	public void actionPerformed(ActionEvent e) {
		AccountsScreen newScreen = new AccountsScreen(client);
		newScreen.initialize(new ArrayList<BankAction>(
				Arrays.asList(new BankAction[] { new GoToSavings("Access account", this.getGUI(), client),
						new GoToChecking("Access Account", this.getGUI(), client),
						new GoToSecurities("Access Account", this.getGUI(), client),
						new GoToClientMain(this.getGUI(), client) })),
				"");
		this.getGUI().setFrame(newScreen);
	}

}
