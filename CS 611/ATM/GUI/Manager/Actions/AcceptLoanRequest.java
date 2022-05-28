package GUI.Manager.Actions;

import Accounts.Account;
import GUI.Abstraction.BankAction;
import GUI.BankGUI;
import GUI.Manager.Actions.Navigation.GoToLoanRequests;
import GUI.Manager.Actions.Navigation.GoToManageLoans;
import GUI.Manager.Actions.Navigation.GoToManagerMain;
import Miscellaneous.Loan;
import Transactions.Types.B2C;
import GUI.Manager.ManageLoanRequestScreen;
import GUI.Manager.ManageLoansScreen;
import Users.Manager;
import Persistent.Database;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AcceptLoanRequest extends BankAction {

	private static final long serialVersionUID = 1L;

	private Manager manager;
	private ManageLoanRequestScreen screen;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public AcceptLoanRequest(BankGUI gui, Manager manager, ManageLoanRequestScreen screen) {
		super("Accept Loan Request", gui);
		this.manager = manager;
		this.screen = screen;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method proceeds to actively accept the selected Loan Request by the
	 * Manager. It begins by checking for valid input, and if it finds them, it then
	 * accepts the request, turning the Loan objective into an active one, and
	 * paying out the amount to the Client.
	 */
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		if (screen.getSelectedLoan() == null || screen.getSelectedLoan().isEmpty() || screen.getInterestRate() == null
				|| screen.getInterestRate().isEmpty()) {
			ManageLoanRequestScreen newScreen = new ManageLoanRequestScreen(this.getGUI().getBank().getLoans());
			newScreen
					.initialize(
							new ArrayList<BankAction>(Arrays
									.asList(new BankAction[] { new AcceptLoanRequest(this.getGUI(), manager, newScreen),
											new GoToManageLoans("Back", this.getGUI(), manager) })),
							"Invalid Fields Entered!");
			this.getGUI().setFrame(newScreen);
			return;
		}

		String stringID = screen.getSelectedLoan().split(" ")[2].substring(1);
		long longID = Long.valueOf(stringID);

		int interestRateInt = Integer.valueOf(screen.getInterestRate());
		double interestRate = interestRateInt / 100.0;

		Loan finalLoan = null;
		for (Loan loan : this.getGUI().getBank().getLoans()) {
			if (loan.getID() == longID) {
				finalLoan = loan;
				break;
			}
		}

		if (finalLoan == null) {
			ManageLoanRequestScreen newScreen = new ManageLoanRequestScreen(this.getGUI().getBank().getLoans());
			newScreen.initialize(
					new ArrayList<BankAction>(
							Arrays.asList(new BankAction[] { new AcceptLoanRequest(this.getGUI(), manager, newScreen),
									new GoToManageLoans("Back", this.getGUI(), manager) })),
					"CRITICAL ERROR: LOAN NOT FOUND");
			this.getGUI().setFrame(newScreen);
			return;
		}

		finalLoan.setInterestRate(interestRate);
		finalLoan.setAccepted(true);
		finalLoan.setLastInterestDate(new Date());

		long checkingID = finalLoan.getClient().getCheckingAccount();
		Account finalAccount = null;
		for (Account account : this.getGUI().getBank().getAccounts()) {
			if (account.getID() == checkingID) {
				finalAccount = account;
				break;
			}
		}

		if (finalAccount == null) {
			ManageLoanRequestScreen newScreen = new ManageLoanRequestScreen(this.getGUI().getBank().getLoans());
			newScreen.initialize(
					new ArrayList<BankAction>(
							Arrays.asList(new BankAction[] { new AcceptLoanRequest(this.getGUI(), manager, newScreen),
									new GoToManageLoans("Back", this.getGUI(), manager) })),
					"CRITICAL ERROR: CLIENT ACCOUNT NOT FOUND");
			this.getGUI().setFrame(newScreen);
			return;
		}

		finalAccount.depositFunds(finalLoan.getCurrency(), finalLoan.getValue());

		B2C transaction = Database.getInstance().createTransactionB2C(finalLoan.getValue(), finalLoan.getCurrency(),
				this.getGUI().getBank().getGlobalAccount(), finalAccount);
		finalLoan.getClient().addTransaction(new Date(), transaction);

		ManageLoansScreen newScreen = new ManageLoansScreen(this.getGUI().getBank().getLoans());
		newScreen.initialize(new ArrayList<BankAction>(Arrays.asList(new BankAction[] {
				new GoToLoanRequests(this.getGUI(), manager), new GoToManagerMain(this.getGUI(), manager) })), "");
		this.getGUI().setFrame(newScreen);
	}

}
