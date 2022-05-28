package GUI.Manager.Actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import Accounts.Checking;
import Accounts.Saving;
import Accounts.Securities;
import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Manager.ManageClientsScreen;
import GUI.Manager.Actions.Navigation.GoToClientInfo;
import GUI.Manager.Actions.Navigation.GoToManagerMain;
import Users.Client;
import Users.Manager;

public class CloseClientAccount extends BankAction {

	private static final long serialVersionUID = 1L;

	private Manager manager;

	private Saving savingsAccount;
	private Checking checkingAccount;
	private Securities securitiesAccount;
	private Client client;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public CloseClientAccount(BankGUI gui, Manager manager, Client client, Saving savingsAccount,
			Checking checkingAccount, Securities securitiesAccount) {
		super("Close Client Account", gui);
		this.manager = manager;
		this.client = client;
		this.savingsAccount = savingsAccount;
		this.checkingAccount = checkingAccount;
		this.securitiesAccount = securitiesAccount;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method proceeds to remove a Client Account from the records, effectively
	 * closing it.
	 */
	public void actionPerformed(ActionEvent e) {
		this.getGUI().getBank().getClients().remove(client);
		this.getGUI().getBank().getAccounts().remove(checkingAccount);
		this.getGUI().getBank().getAccounts().remove(securitiesAccount);
		this.getGUI().getBank().getAccounts().remove(savingsAccount);

		ManageClientsScreen newScreen = new ManageClientsScreen(this.getGUI().getBank().getClients());
		newScreen.initialize(new ArrayList<BankAction>(Arrays.asList(new BankAction[] {
				new GoToClientInfo(this.getGUI(), manager, newScreen), new GoToManagerMain(this.getGUI(), manager) })),
				"");
		this.getGUI().setFrame(newScreen);
	}

}
