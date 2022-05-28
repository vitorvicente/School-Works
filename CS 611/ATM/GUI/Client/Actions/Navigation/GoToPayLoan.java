package GUI.Client.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Client.PayLoanScreen;
import GUI.Client.Actions.PayLoan;
import Miscellaneous.Loan;
import Users.Client;

public class GoToPayLoan extends BankAction {

	private static final long serialVersionUID = 1L;

	private Client client;

	private ArrayList<Loan> loans;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToPayLoan(BankGUI gui, Client client, ArrayList<Loan> loans) {
		super("Pay Loan", gui);
		this.client = client;
		this.loans = loans;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the Client to the Pay Loan Screen.
	 */
	public void actionPerformed(ActionEvent e) {
		PayLoanScreen newScreen = new PayLoanScreen(loans);
		newScreen.initialize(
				new ArrayList<BankAction>(Arrays.asList(new BankAction[] {
						new PayLoan(this.getGUI(), client, newScreen), new GoToLoans("Back", this.getGUI(), client) })),
				"");
		this.getGUI().setFrame(newScreen);
	}

}
