package GUI.Client.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;

import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Client.LoanScreen;
import Miscellaneous.Loan;
import Users.Client;

public class GoToLoans extends BankAction {

	private static final long serialVersionUID = 1L;

	private Client client;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToLoans(String name, BankGUI gui, Client client) {
		super(name, gui);
		this.client = client;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the Client to the Loans Screen. It also fetches all the loans the
	 * Client currently has so it can display them.
	 */
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		ArrayList<Loan> loans = new ArrayList<Loan>();
		@SuppressWarnings("unchecked")
		ArrayList<Long> cloneIDS = (ArrayList<Long>) client.getLoans().clone();

		for (Loan l : this.getGUI().getBank().getLoans()) {
			if (cloneIDS.contains(l.getID())) {
				loans.add(l);
				cloneIDS.remove(l.getID());
			}

			if (cloneIDS.isEmpty())
				break;
		}

		Date today = new Date();
		for (Loan l : loans) {
			if (today.getYear() - l.getLastInterestDate().getYear() >= 1) {
				l.setValue((int) Math.round(l.getValue() * (1 + l.getInterestRate())));
				l.setLastInterestDate(today);
			}
		}

		LoanScreen newScreen = new LoanScreen(loans);
		newScreen.initialize(
				new ArrayList<BankAction>(Arrays.asList(new BankAction[] { new GoToRequestLoan(this.getGUI(), client),
						new GoToPayLoan(this.getGUI(), client, loans), new GoToClientMain(this.getGUI(), client) })),
				"");
		this.getGUI().setFrame(newScreen);
	}

}