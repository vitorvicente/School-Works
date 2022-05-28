package GUI.Manager.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Manager.TransactionScreen;
import Users.Manager;

public class GoToTransactions extends BankAction {

	private static final long serialVersionUID = 1L;

	private Manager manager;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToTransactions(String name, BankGUI gui, Manager manager) {
		super(name, gui);
		this.manager = manager;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the Manager to the Transactions Info Screen.
	 */
	public void actionPerformed(ActionEvent e) {
		TransactionScreen newScreen = new TransactionScreen(this.getGUI().getBank().getClients(), manager);
		newScreen.initialize(new ArrayList<BankAction>(Arrays.asList(new BankAction[] {
				new GoToOlderTransactions(this.getGUI(), manager), new GoToManagerMain(this.getGUI(), manager) })), "");
		this.getGUI().setFrame(newScreen);
	}

}
