package GUI.Manager.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import Accounts.Checking;
import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.General.Actions.Navigation.GoToDeposit;
import GUI.General.Actions.Navigation.GoToLogin;
import GUI.General.Actions.Navigation.GoToTransfer;
import GUI.General.Actions.Navigation.GoToWithdraw;
import GUI.Manager.ManagerMainScreen;
import Users.Manager;

public class GoToManagerMain extends BankAction {

	private static final long serialVersionUID = 1L;

	private Manager manager;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToManagerMain(BankGUI gui, Manager manager) {
		super("Back", gui);
		this.manager = manager;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the Manager to the Main Manager Screen.
	 */
	public void actionPerformed(ActionEvent e) {
		Checking globalAccount = this.getGUI().getBank().getGlobalAccount();

		ManagerMainScreen newScreen = new ManagerMainScreen(globalAccount);
		newScreen.initialize(
				new ArrayList<BankAction>(Arrays.asList(new BankAction[] { new GoToEditInfo(this.getGUI(), manager),
						new GoToManageClients("Manage Clients", this.getGUI(), manager),
						new GoToTransactions("View Transactions", this.getGUI(), manager),
						new GoToManageLoans("Manage Loans", this.getGUI(), manager),
						new GoToManageStock("Manage Stock", this.getGUI(), manager),
						new GoToLogin("Logout", this.getGUI()), new GoToTransfer(this.getGUI(), manager, globalAccount),
						new GoToDeposit(this.getGUI(), manager, globalAccount),
						new GoToWithdraw(this.getGUI(), manager, globalAccount) })),
				"");
		this.getGUI().setFrame(newScreen);
	}

}
