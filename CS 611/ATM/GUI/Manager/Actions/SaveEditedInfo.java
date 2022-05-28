package GUI.Manager.Actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import Accounts.Checking;
import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Manager.EditBasicInfoScreen;
import GUI.Manager.ManagerMainScreen;
import GUI.Manager.Actions.Navigation.GoToEditInfo;
import GUI.Manager.Actions.Navigation.GoToManageClients;
import GUI.Manager.Actions.Navigation.GoToManageLoans;
import GUI.Manager.Actions.Navigation.GoToManageStock;
import GUI.Manager.Actions.Navigation.GoToTransactions;
import GUI.General.Actions.Navigation.GoToDeposit;
import GUI.General.Actions.Navigation.GoToLogin;
import GUI.General.Actions.Navigation.GoToTransfer;
import GUI.General.Actions.Navigation.GoToWithdraw;
import Users.Manager;

public class SaveEditedInfo extends BankAction {

	private static final long serialVersionUID = 1L;

	private Manager manager;
	private EditBasicInfoScreen screen;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public SaveEditedInfo(BankGUI gui, Manager manager, EditBasicInfoScreen screen) {
		super("Save", gui);
		this.manager = manager;
		this.screen = screen;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method edits whatever Manager information was actively changed by the
	 * Manager, switching the information in the Manager object.
	 */
	public void actionPerformed(ActionEvent e) {
		if (screen.getName() != null && !screen.getName().isEmpty())
			manager.setFullName(screen.getName());
		if (screen.getUsername() != null && !screen.getUsername().isEmpty())
			manager.setUsername(screen.getUsername());
		if (screen.getPassword() != null && !screen.getPassword().isEmpty())
			manager.setPassword(screen.getPassword());

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
