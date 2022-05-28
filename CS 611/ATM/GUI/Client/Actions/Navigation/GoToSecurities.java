package GUI.Client.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import Accounts.Account;
import Accounts.Saving;
import Accounts.Securities;
import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Client.AccountsScreen;
import GUI.Client.SecuritiesAccountScreen;
import GUI.General.Actions.Navigation.GoToTransfer;
import Users.Client;

public class GoToSecurities extends BankAction {

	private static final long serialVersionUID = 1L;

	private Client client;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToSecurities(String name, BankGUI gui, Client client) {
		super(name, gui);
		this.client = client;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method is a bit more complex than the other move methods, as it actually
	 * performs some checks to make sure the Client can go to their Securities
	 * Account.
	 * 
	 * Firstly, it checks whether the Securities Account is locked, if so it checks
	 * if the Client has passed their required threshold, and if so it unlocks it.
	 * 
	 * Secondly, it checks whether the Client has at least the required minimum in
	 * their Savings Account to enter trading.
	 */
	public void actionPerformed(ActionEvent e) {
		Account clientAccount = null;
		Account savingsAccount = null;

		for (Account account : this.getGUI().getBank().getAccounts()) {
			if (account.getID() == client.getSecuritiesAccount())
				clientAccount = account;
			else if (account.getID() == client.getSavingsAccount())
				savingsAccount = account;

			if (clientAccount != null && savingsAccount != null)
				break;
		}

		if (clientAccount == null || !(clientAccount instanceof Securities) || savingsAccount == null
				|| !(savingsAccount instanceof Saving)) {
			AccountsScreen newScreen = new AccountsScreen(client);
			newScreen.initialize(
					new ArrayList<BankAction>(
							Arrays.asList(new BankAction[] { new GoToSavings("Access account", this.getGUI(), client),
									new GoToChecking("Access Account", this.getGUI(), client),
									new GoToSecurities("Access Account", this.getGUI(), client),
									new GoToClientMain(this.getGUI(), client) })),
					"Critical Error: Accounts Not Found!");
			this.getGUI().setFrame(newScreen);
			return;
		}

		Securities castedAccount = (Securities) clientAccount;

		if (castedAccount.isBlocked()
				&& savingsAccount.getWallet().getAmountOf("USD") >= Bank.Bank.MINIMUM_TO_GET_SECURITIES)
			castedAccount.setBlocked(false);

		if (savingsAccount.getWallet().getAmountOf("USD") < Bank.Bank.MINIMUM_IN_SAVINGS || castedAccount.isBlocked()) {
			AccountsScreen newScreen = new AccountsScreen(client);
			newScreen.initialize(
					new ArrayList<BankAction>(
							Arrays.asList(new BankAction[] { new GoToSavings("Access account", this.getGUI(), client),
									new GoToChecking("Access Account", this.getGUI(), client),
									new GoToSecurities("Access Account", this.getGUI(), client),
									new GoToClientMain(this.getGUI(), client) })),
					"You do not gave the Required Solvency to Enter Trading!");
			this.getGUI().setFrame(newScreen);
			return;
		}

		SecuritiesAccountScreen newScreen = new SecuritiesAccountScreen(castedAccount,
				this.getGUI().getBank().getStockMarket());
		newScreen.initialize(new ArrayList<BankAction>(
				Arrays.asList(new BankAction[] { new GoToBuyStock(this.getGUI(), client, castedAccount),
						new GoToSellStock(this.getGUI(), client, castedAccount),
						new GoToTransfer(this.getGUI(), client, castedAccount),
						new GoToAccounts("Back", this.getGUI(), client) })),
				"");
		this.getGUI().setFrame(newScreen);
	}

}
