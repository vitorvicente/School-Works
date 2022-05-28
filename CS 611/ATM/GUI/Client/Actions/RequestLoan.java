package GUI.Client.Actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Client.LoanScreen;
import GUI.Client.RequestLoanScreen;
import GUI.Client.Actions.Navigation.GoToClientMain;
import GUI.Client.Actions.Navigation.GoToLoans;
import GUI.Client.Actions.Navigation.GoToPayLoan;
import GUI.Client.Actions.Navigation.GoToRequestLoan;
import Miscellaneous.Loan;
import Users.Client;

public class RequestLoan extends BankAction {

	private static final long serialVersionUID = 1L;

	private Client client;
	private RequestLoanScreen screen;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public RequestLoan(BankGUI gui, Client client, RequestLoanScreen screen) {
		super("Request", gui);
		this.client = client;
		this.screen = screen;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method proceeds with the request of a Loan by a specific Client, it
	 * checks for valid inputs, and then proceeds to create a Loan, marking it as a
	 * request, which means the Manager will need to approve for the payment to
	 * occur, once this is done, it moves the Client back to the Loans Screen.
	 */
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		if (this.checkValid()) {
			RequestLoanScreen newScreen = new RequestLoanScreen(Bank.Bank.AVAILABLE_CURRENCIES);
			newScreen
					.initialize(
							new ArrayList<BankAction>(
									Arrays.asList(new BankAction[] { new RequestLoan(this.getGUI(), client, newScreen),
											new GoToLoans("Back", this.getGUI(), client) })),
							"Invalid Fields Entered!");
			this.getGUI().setFrame(newScreen);
			return;
		}

		int amount = Integer.valueOf(screen.getAmount());
		double rate = Integer.valueOf(screen.getInterestRate()) / 100.0;
		String parsedCurrency = screen.getSelectedCurrency().split(" ")[1];

		this.getGUI().getBank().requestLoan(client, parsedCurrency, amount, screen.getCollateral(), rate);

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

		LoanScreen newScreen = new LoanScreen(loans);
		newScreen.initialize(
				new ArrayList<BankAction>(Arrays.asList(new BankAction[] { new GoToRequestLoan(this.getGUI(), client),
						new GoToPayLoan(this.getGUI(), client, loans), new GoToClientMain(this.getGUI(), client) })),
				"");
		this.getGUI().setFrame(newScreen);
	}

	/* ============== */
	/* Helper Methods */
	/* ============== */

	/*
	 * This method simply checks if all the inputs given are valid.
	 */
	private boolean checkValid() {
		return screen.getAmount() == null || screen.getInterestRate() == null || screen.getSelectedCurrency() == null
				|| screen.getCollateral() == null || screen.getAmount().isEmpty() || screen.getInterestRate().isEmpty()
				|| screen.getSelectedCurrency().isEmpty() || screen.getCollateral().isEmpty();
	}

}
