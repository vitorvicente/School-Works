package GUI.Client.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import Accounts.Account;
import Accounts.Checking;
import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Client.CheckingAccountScreen;
import GUI.General.Actions.Navigation.GoToDeposit;
import GUI.General.Actions.Navigation.GoToTransfer;
import GUI.General.Actions.Navigation.GoToWithdraw;
import Users.Client;

public class GoToChecking extends BankAction {

	private static final long serialVersionUID = 1L;

	private Client client;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToChecking(String name, BankGUI gui, Client client) {
		super(name, gui);
		this.client = client;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the Client to the Checking Account Screen. It also fetches the Client
	 * Checking Account before doing so.
	 */
	public void actionPerformed(ActionEvent e) {
		Account clientAccount = null;
		for (Account account : this.getGUI().getBank().getAccounts()) {
			if (account.getID() == client.getCheckingAccount()) {
				clientAccount = account;
				break;
			}
		}

		if (clientAccount == null || !(clientAccount instanceof Checking))
			return;

		Checking castedAccount = (Checking) clientAccount;

		CheckingAccountScreen newScreen = new CheckingAccountScreen(castedAccount);
		newScreen.initialize(new ArrayList<BankAction>(Arrays.asList(new BankAction[] {
				new GoToTransfer(this.getGUI(), client, castedAccount), new GoToAccounts("Back", this.getGUI(), client),
				new GoToWithdraw(this.getGUI(), client, castedAccount),
				new GoToDeposit(this.getGUI(), client, castedAccount) })), "");
		this.getGUI().setFrame(newScreen);
	}

}
