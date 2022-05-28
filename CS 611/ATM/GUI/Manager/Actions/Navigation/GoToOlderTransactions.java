package GUI.Manager.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Manager.OlderTransactionsScreen;
import Users.Manager;

public class GoToOlderTransactions extends BankAction {

	private static final long serialVersionUID = 1L;

	private Manager manager;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToOlderTransactions(BankGUI gui, Manager manager) {
		super("View Older Transactions", gui);
		this.manager = manager;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the Manager to the Old Transactions Info Screen.
	 */
	public void actionPerformed(ActionEvent e) {
		OlderTransactionsScreen newScreen = new OlderTransactionsScreen(this.getGUI().getBank().getClients(), manager);
		newScreen.initialize(new ArrayList<BankAction>(
				Arrays.asList(new BankAction[] { new GoToTransactions("Back", this.getGUI(), manager) })), "");
		this.getGUI().setFrame(newScreen);
	}

}
