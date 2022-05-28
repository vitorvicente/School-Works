package GUI.General.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import Accounts.Checking;
import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Client.Actions.Navigation.GoToChecking;
import GUI.General.WithdrawScreen;
import GUI.General.Actions.WithdrawMoney;
import GUI.Manager.Actions.Navigation.GoToManagerMain;
import Users.Client;
import Users.Manager;
import Users.User;

public class GoToWithdraw extends BankAction {

	private static final long serialVersionUID = 1L;

	private User user;
	private Checking account;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToWithdraw(BankGUI gui, User user, Checking account) {
		super("Withdraw Money", gui);
		this.user = user;
		this.account = account;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the User to the Withdraw Screen.
	 */
	public void actionPerformed(ActionEvent e) {
		WithdrawScreen newScreen = new WithdrawScreen(account);

		if (user instanceof Manager) {
			Manager castedUser = (Manager) user;
			newScreen.initialize(new ArrayList<BankAction>(
					Arrays.asList(new BankAction[] { new WithdrawMoney(this.getGUI(), castedUser, account, newScreen),
							new GoToManagerMain(this.getGUI(), castedUser) })),
					"");
		} else {
			Client castedUser = (Client) user;
			newScreen.initialize(new ArrayList<BankAction>(
					Arrays.asList(new BankAction[] { new WithdrawMoney(this.getGUI(), castedUser, account, newScreen),
							new GoToChecking("Back", this.getGUI(), castedUser) })),
					"");
		}

		this.getGUI().setFrame(newScreen);
	}

}
