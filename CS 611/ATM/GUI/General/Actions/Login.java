package GUI.General.Actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import Accounts.Checking;
import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Client.ClientMainScreen;
import GUI.Client.Actions.Navigation.GoToAccounts;
import GUI.Client.Actions.Navigation.GoToLoans;
import GUI.General.LoginScreen;
import GUI.General.Actions.Navigation.GoToDeposit;
import GUI.General.Actions.Navigation.GoToLogin;
import GUI.General.Actions.Navigation.GoToRegister;
import GUI.General.Actions.Navigation.GoToTransfer;
import GUI.General.Actions.Navigation.GoToWithdraw;
import GUI.Manager.ManagerMainScreen;
import GUI.Manager.Actions.Navigation.GoToManageClients;
import GUI.Manager.Actions.Navigation.GoToManageLoans;
import GUI.Manager.Actions.Navigation.GoToManageStock;
import Users.Client;
import Users.Manager;

public class Login extends BankAction {

	private static final long serialVersionUID = 1L;

	private LoginScreen screen;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Login(BankGUI gui, LoginScreen screen) {
		super("Login", gui);
		this.screen = screen;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method effectively loads the Client/Manager into the GUI, checking for
	 * valid input, and then checking if this input matches any of the existing
	 * Clients or the Manager.
	 */
	public void actionPerformed(ActionEvent e) {
		if (screen.getUsername() == null || screen.getUsername().isEmpty() || screen.getPassword() == null
				|| String.valueOf(screen.getPassword()).isEmpty()) {
			LoginScreen newScreen = new LoginScreen();
			newScreen.initialize(
					new ArrayList<BankAction>(
							Arrays.asList(new Login(this.getGUI(), newScreen), new GoToRegister(this.getGUI()))),
					"Login Failed! Invalid Credentials!");
			this.getGUI().setFrame(newScreen);
			return;
		}

		String password = String.valueOf(screen.getPassword());

		if (this.getGUI().getBank().getManager().getUsername().equals(screen.getUsername())
				&& this.getGUI().getBank().getManager().getPassword().equals(password)) {

			Manager manager = this.getGUI().getBank().getManager();

			Checking globalAccount = this.getGUI().getBank().getGlobalAccount();

			ManagerMainScreen newScreen = new ManagerMainScreen(globalAccount);
			newScreen.initialize(new ArrayList<BankAction>(Arrays
					.asList(new BankAction[] { new GUI.Manager.Actions.Navigation.GoToEditInfo(this.getGUI(), manager),
							new GoToManageClients("Manage Clients", this.getGUI(), manager),
							new GUI.Manager.Actions.Navigation.GoToTransactions("View Transactions", this.getGUI(),
									manager),
							new GoToManageLoans("Manage Loans", this.getGUI(), manager),
							new GoToManageStock("Manage Stock", this.getGUI(), manager),
							new GoToLogin("Logout", this.getGUI()),
							new GoToTransfer(this.getGUI(), manager, globalAccount),
							new GoToDeposit(this.getGUI(), manager, globalAccount),
							new GoToWithdraw(this.getGUI(), manager, globalAccount) })),
					"");
			this.getGUI().setFrame(newScreen);
		} else {
			Client finalClient = null;
			for (Client client : this.getGUI().getBank().getClients()) {
				if (client.getUsername().equals(screen.getUsername()) && client.getPassword().equals(password)) {
					finalClient = client;
					break;
				}
			}

			if (finalClient == null) {
				LoginScreen newScreen = new LoginScreen();
				newScreen.initialize(
						new ArrayList<BankAction>(
								Arrays.asList(new Login(this.getGUI(), newScreen), new GoToRegister(this.getGUI()))),
						"Login Failed! No Matching Credentials Found!");
				this.getGUI().setFrame(newScreen);
				return;
			}

			ClientMainScreen newScreen = new ClientMainScreen(finalClient);
			newScreen
					.initialize(
							new ArrayList<BankAction>(Arrays.asList(new BankAction[] {
									new GUI.Client.Actions.Navigation.GoToEditInfo(this.getGUI(), finalClient),
									new GoToLogin("Logout", this.getGUI()),
									new GoToAccounts("View Accounts", this.getGUI(), finalClient),
									new GoToLoans("View Loans", this.getGUI(), finalClient),
									new GUI.Client.Actions.Navigation.GoToTransactions(this.getGUI(), finalClient) })),
							"");
			this.getGUI().setFrame(newScreen);
		}
	}

}