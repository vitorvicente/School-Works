package GUI.Client.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import Accounts.Account;
import Accounts.Saving;
import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Client.SavingsAccountScreen;
import GUI.General.Actions.Navigation.GoToTransfer;
import Users.Client;

public class GoToSavings extends BankAction {

	private static final long serialVersionUID = 2L;

	private Client client;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToSavings(String name, BankGUI gui, Client client) {
		super(name, gui);
		this.client = client;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the Client to the Savings Account Screen. It also fetches the Client
	 * Saving Account before doing so.
	 */
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		Account clientAccount = null;
		for (Account account : this.getGUI().getBank().getAccounts()) {
			if (account.getID() == client.getSavingsAccount()) {
				clientAccount = account;
				break;
			}
		}

		if (clientAccount == null || !(clientAccount instanceof Saving))
			return;

		Saving castedAccount = (Saving) clientAccount;

		Date today = new Date();
		if (today.getYear() - castedAccount.getLastPayDate().getYear() >= 1) {
			for (String c : castedAccount.getWallet().getValues().keySet()) {
				castedAccount.depositFunds(c,
						(int) Math.round(castedAccount.getWallet().getAmountOf(c) * castedAccount.getInterestRate()));
			}
		}

		SavingsAccountScreen newScreen = new SavingsAccountScreen(castedAccount);
		newScreen.initialize(new ArrayList<BankAction>(
				Arrays.asList(new BankAction[] { new GoToTransfer(this.getGUI(), client, castedAccount),
						new GoToAccounts("Back", this.getGUI(), client) })),
				"");
		this.getGUI().setFrame(newScreen);
	}

}
