package GUI.Manager.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import Accounts.Account;
import Accounts.Checking;
import Accounts.Saving;
import Accounts.Securities;
import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Manager.ClientInfoScreen;
import GUI.Manager.ManageClientsScreen;
import GUI.Manager.Actions.CloseClientAccount;
import Users.Client;
import Users.Manager;

public class GoToClientInfo extends BankAction {

	private static final long serialVersionUID = 1L;

	private Manager manager;
	private ManageClientsScreen screen;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToClientInfo(BankGUI gui, Manager manager, ManageClientsScreen screen) {
		super("Client Info", gui);
		this.manager = manager;
		this.screen = screen;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the Manager to the Client Info Screen. Making sure to fetch the correct
	 * client before doing so.
	 */
	public void actionPerformed(ActionEvent e) {
		Client finalClient = null;
		Account savings = null;
		Account checking = null;
		Account securities = null;

		if (screen.getSelectedClient() == null || screen.getSelectedClient().isEmpty()) {
			ManageClientsScreen newScreen = new ManageClientsScreen(this.getGUI().getBank().getClients());
			newScreen.initialize(new ArrayList<BankAction>(
					Arrays.asList(new BankAction[] { new GoToClientInfo(this.getGUI(), manager, newScreen),
							new GoToManagerMain(this.getGUI(), manager) })),
					"No Client Selected!");
			this.getGUI().setFrame(newScreen);
			return;
		}

		String username = screen.getSelectedClient().split(" ")[3];

		for (Client c : this.getGUI().getBank().getClients()) {
			if (c.getUsername().equals(username)) {
				finalClient = c;
				break;
			}
		}

		if (finalClient == null) {
			ManageClientsScreen newScreen = new ManageClientsScreen(this.getGUI().getBank().getClients());
			newScreen.initialize(
					new ArrayList<BankAction>(
							Arrays.asList(new BankAction[] { new GoToClientInfo(this.getGUI(), manager, newScreen),
									new GoToManagerMain(this.getGUI(), manager) })),
					"CRITICAL ERROR: CLIENT NOT FOUND");
			this.getGUI().setFrame(newScreen);
			return;
		}

		for (Account account : this.getGUI().getBank().getAccounts()) {
			if (account.getID() == finalClient.getSavingsAccount() && (account instanceof Saving))
				savings = account;
			else if (account.getID() == finalClient.getCheckingAccount() && (account instanceof Checking))
				checking = account;
			else if (account.getID() == finalClient.getSecuritiesAccount() && (account instanceof Securities))
				securities = account;

			if (savings != null && checking != null && securities != null)
				break;
		}

		if (savings == null || checking == null || securities == null) {
			ManageClientsScreen newScreen = new ManageClientsScreen(this.getGUI().getBank().getClients());
			newScreen.initialize(
					new ArrayList<BankAction>(
							Arrays.asList(new BankAction[] { new GoToClientInfo(this.getGUI(), manager, newScreen),
									new GoToManagerMain(this.getGUI(), manager) })),
					"CRITICAL ERROR: ACCOUNTS NOT FOUND");
			this.getGUI().setFrame(newScreen);
			return;
		}

		Saving castedSavings = (Saving) savings;
		Checking castedChecking = (Checking) checking;
		Securities castedSecurities = (Securities) securities;

		ClientInfoScreen newScreen = new ClientInfoScreen(finalClient, castedSavings, castedChecking, castedSecurities,
				this.getGUI().getBank().getStockMarket());
		newScreen
				.initialize(
						new ArrayList<BankAction>(Arrays.asList(new BankAction[] {
								new CloseClientAccount(this.getGUI(), manager, finalClient, castedSavings,
										castedChecking, castedSecurities),
								new GoToManageClients("Back", this.getGUI(), manager) })),
						"");
		this.getGUI().setFrame(newScreen);
	}

}
