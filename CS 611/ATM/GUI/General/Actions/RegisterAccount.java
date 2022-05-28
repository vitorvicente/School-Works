package GUI.General.Actions;

import Accounts.Checking;
import Accounts.Saving;
import Accounts.Securities;
import GUI.Abstraction.BankAction;
import GUI.BankGUI;
import GUI.General.Actions.Navigation.GoToLogin;
import GUI.General.Actions.Navigation.GoToRegister;
import GUI.General.LoginScreen;
import GUI.General.RegisterScreen;
import Users.Client;
import Persistent.Database;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class RegisterAccount extends BankAction {

	private static final long serialVersionUID = 1L;

	private RegisterScreen screen;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public RegisterAccount(BankGUI gui, RegisterScreen screen) {
		super("Create", gui);
		this.screen = screen;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This proceeds to actually register new Clients onto the Bank. It checks for
	 * valid inputs, and proceeds to create the Client Accounts and the Client
	 * itself.
	 */
	public void actionPerformed(ActionEvent e) {
		if (this.checkValid()) {
			RegisterScreen newScreen = new RegisterScreen();
			newScreen.initialize(
					new ArrayList<BankAction>(Arrays.asList(new BankAction[] {
							new RegisterAccount(this.getGUI(), newScreen), new GoToLogin("Back", this.getGUI()) })),
					"Invalid Registration Fields!");
			this.getGUI().setFrame(newScreen);
			return;
		}

		long ssn = Long.valueOf(screen.getSsn());
		Checking accountChecking = Database.getInstance().createAccountChecking();
		Saving accountSaving = Database.getInstance().createAccountSaving();
		Securities accountSecurities = Database.getInstance().createAccountSecurities();

		this.getGUI().getBank().getAccounts().add(accountChecking);
		this.getGUI().getBank().getAccounts().add(accountSaving);
		this.getGUI().getBank().getAccounts().add(accountSecurities);

		Client newClient = Database.getInstance().createClient(screen.getUsername(), screen.getPassword(),
				screen.getName(), ssn, screen.getPhone(), screen.getAddress(), accountChecking.getId(),
				accountSaving.getId(), accountSecurities.getId());

		this.getGUI().getBank().getClients().add(newClient);

		LoginScreen newScreen = new LoginScreen();
		newScreen.initialize(new ArrayList<BankAction>(
				Arrays.asList(new Login(this.getGUI(), newScreen), new GoToRegister(this.getGUI()))), "");
		this.getGUI().setFrame(newScreen);
	}

	/* ============== */
	/* Helper Methods */
	/* ============== */

	/*
	 * This method simply checks for valid inputs.
	 */
	private boolean checkValid() {
		return screen.getName() == null || screen.getUsername() == null || screen.getPassword() == null
				|| screen.getSsn() == null || screen.getPhone() == null || screen.getAddress() == null
				|| screen.getName().isEmpty() || screen.getUsername().isEmpty() || screen.getPassword().isEmpty()
				|| screen.getSsn().isEmpty() || screen.getPhone().isEmpty() || screen.getAddress().isEmpty();
	}

}
