package GUI.Client.Actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Client.ClientMainScreen;
import GUI.Client.EditBasicInfoScreen;
import GUI.Client.Actions.Navigation.GoToAccounts;
import GUI.Client.Actions.Navigation.GoToEditInfo;
import GUI.Client.Actions.Navigation.GoToLoans;
import GUI.Client.Actions.Navigation.GoToTransactions;
import GUI.General.Actions.Navigation.GoToLogin;
import Users.Client;

public class SaveEditedInfo extends BankAction {

	private static final long serialVersionUID = 1L;

	private Client client;
	private EditBasicInfoScreen screen;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public SaveEditedInfo(BankGUI gui, Client client, EditBasicInfoScreen screen) {
		super("Save", gui);
		this.client = client;
		this.screen = screen;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method saves all the edited info about the Client, only changing that
	 * which was edited, and then moving back to the Main Client Screen.
	 */
	public void actionPerformed(ActionEvent e) {
		if (screen.getName() != null && !screen.getName().isEmpty())
			client.setFullName(screen.getName());
		if (screen.getUsername() != null && !screen.getUsername().isEmpty())
			client.setUsername(screen.getUsername());
		if (screen.getPassword() != null && !screen.getPassword().isEmpty())
			client.setPassword(screen.getPassword());
		if (screen.getSsn() != null && !screen.getSsn().isEmpty())
			client.setSsn(Long.valueOf(screen.getSsn()));
		if (screen.getPhone() != null && !screen.getPhone().isEmpty())
			client.setPhoneNumber(screen.getPhone());
		if (screen.getAddress() != null && !screen.getAddress().isEmpty())
			client.setAddress(screen.getAddress());

		ClientMainScreen newScreen = new ClientMainScreen(client);
		newScreen.initialize(new ArrayList<BankAction>(Arrays.asList(new BankAction[] {
				new GoToEditInfo(this.getGUI(), client), new GoToLogin("Logout", this.getGUI()),
				new GoToAccounts("View Accounts", this.getGUI(), client),
				new GoToLoans("View Loans", this.getGUI(), client), new GoToTransactions(this.getGUI(), client) })),
				"");
		this.getGUI().setFrame(newScreen);
	}

}
