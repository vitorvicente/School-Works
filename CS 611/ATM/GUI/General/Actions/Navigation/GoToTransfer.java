package GUI.General.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import Accounts.Account;
import Accounts.Checking;
import Accounts.Saving;
import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Client.Actions.Navigation.GoToChecking;
import GUI.Client.Actions.Navigation.GoToSavings;
import GUI.Client.Actions.Navigation.GoToSecurities;
import GUI.General.TransferScreen;
import GUI.General.Actions.TransferMoney;
import GUI.Manager.Actions.Navigation.GoToManagerMain;
import Users.Client;
import Users.Manager;
import Users.User;

public class GoToTransfer extends BankAction {

	private static final long serialVersionUID = 1L;

	private User user;
	private Account account;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToTransfer(BankGUI gui, User user, Account account) {
		super("Transfer Money", gui);
		this.user = user;
		this.account = account;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the User to the Transfer Screen.
	 */
	public void actionPerformed(ActionEvent e) {
		TransferScreen newScreen = new TransferScreen(account);

		if (user instanceof Manager) {
			Manager castedUser = (Manager) user;
			newScreen.initialize(new ArrayList<BankAction>(
					Arrays.asList(new BankAction[] { new TransferMoney(this.getGUI(), user, account, newScreen),
							new GoToManagerMain(this.getGUI(), castedUser) })),
					"");
		} else {
			Client castedUser = (Client) user;
			if (account instanceof Checking)
				newScreen.initialize(new ArrayList<BankAction>(
						Arrays.asList(new BankAction[] { new TransferMoney(this.getGUI(), user, account, newScreen),
								new GoToChecking("Back", this.getGUI(), castedUser) })),
						"");
			else if (account instanceof Saving)
				newScreen.initialize(new ArrayList<BankAction>(
						Arrays.asList(new BankAction[] { new TransferMoney(this.getGUI(), user, account, newScreen),
								new GoToSavings("Back", this.getGUI(), castedUser) })),
						"");
			else
				newScreen.initialize(new ArrayList<BankAction>(
						Arrays.asList(new BankAction[] { new TransferMoney(this.getGUI(), user, account, newScreen),
								new GoToSecurities("Back", this.getGUI(), castedUser) })),
						"");
		}

		this.getGUI().setFrame(newScreen);
	}

}
