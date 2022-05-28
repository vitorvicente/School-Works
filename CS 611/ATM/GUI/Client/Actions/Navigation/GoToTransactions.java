package GUI.Client.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Client.TransactionScreen;
import Users.Client;

public class GoToTransactions extends BankAction {

	private static final long serialVersionUID = 1L;

	private Client client;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToTransactions(BankGUI gui, Client client) {
		super("Check Transactions", gui);
		this.client = client;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the Client to the Transactions Info Screen.
	 */
	public void actionPerformed(ActionEvent e) {
		TransactionScreen newScreen = new TransactionScreen(client);
		newScreen.initialize(new ArrayList<BankAction>(
				Arrays.asList(new BankAction[] { new GoToClientMain(this.getGUI(), client) })), "");
		this.getGUI().setFrame(newScreen);
	}

}
