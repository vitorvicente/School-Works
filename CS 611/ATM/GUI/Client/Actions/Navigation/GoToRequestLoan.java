package GUI.Client.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Client.RequestLoanScreen;
import GUI.Client.Actions.RequestLoan;
import Users.Client;

public class GoToRequestLoan extends BankAction {

	private static final long serialVersionUID = 1L;

	private Client client;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToRequestLoan(BankGUI gui, Client client) {
		super("Request Loan", gui);
		this.client = client;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the Client to the Request Loan Screen.
	 */
	public void actionPerformed(ActionEvent e) {
		RequestLoanScreen newScreen = new RequestLoanScreen(Bank.Bank.AVAILABLE_CURRENCIES);
		newScreen.initialize(new ArrayList<BankAction>(Arrays.asList(new BankAction[] {
				new RequestLoan(this.getGUI(), client, newScreen), new GoToLoans("Back", this.getGUI(), client) })),
				"");
		this.getGUI().setFrame(newScreen);
	}

}
