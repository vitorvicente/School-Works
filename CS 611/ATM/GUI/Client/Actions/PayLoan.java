package GUI.Client.Actions;

import Accounts.Account;
import Accounts.Checking;
import GUI.Abstraction.BankAction;
import GUI.BankGUI;
import GUI.Client.Actions.Navigation.GoToClientMain;
import GUI.Client.Actions.Navigation.GoToLoans;
import GUI.Client.Actions.Navigation.GoToPayLoan;
import GUI.Client.Actions.Navigation.GoToRequestLoan;
import Miscellaneous.Loan;
import Transactions.Types.C2B;
import GUI.Client.LoanScreen;
import GUI.Client.PayLoanScreen;
import Users.Client;
import Persistent.Database;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class PayLoan extends BankAction {

	private static final long serialVersionUID = 1L;

	private Client client;
	private PayLoanScreen screen;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public PayLoan(BankGUI gui, Client client, PayLoanScreen screen) {
		super("Pay", gui);
		this.client = client;
		this.screen = screen;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method proceeds with the full or partial payment of a specific Loan by a
	 * specific Client. It checks for valid inputs, checks if the Client has enough
	 * balance to make the payment and finally proceeds to make the payment, and
	 * move back to the Loans Screen.
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

		if (screen.getAmount() == null || screen.getSelectedLoan() == null || screen.getAmount().isEmpty()
				|| screen.getSelectedLoan().isEmpty()) {
			PayLoanScreen newScreen = new PayLoanScreen(loans);
			newScreen
					.initialize(
							new ArrayList<BankAction>(
									Arrays.asList(new BankAction[] { new PayLoan(this.getGUI(), client, newScreen),
											new GoToLoans("Back", this.getGUI(), client) })),
							"Invalid Fields Entered!");
			this.getGUI().setFrame(newScreen);
			return;
		}

		String stringID = screen.getSelectedLoan().split(" ")[2].substring(1);
		long longID = Long.valueOf(stringID);
		int amount = Integer.valueOf(screen.getAmount());

		Loan finalLoan = null;
		for (Loan loan : this.getGUI().getBank().getLoans()) {
			if (loan.getID() == longID) {
				finalLoan = loan;
				break;
			}
		}

		Checking finalAccount = null;
		for (Account account : this.getGUI().getBank().getAccounts()) {
			if (account.getID() == client.getCheckingAccount() && account instanceof Checking) {
				finalAccount = (Checking) account;
				break;
			}
		}

		if (finalAccount == null || finalLoan == null) {
			PayLoanScreen newScreen = new PayLoanScreen(loans);
			newScreen.initialize(
					new ArrayList<BankAction>(
							Arrays.asList(new BankAction[] { new PayLoan(this.getGUI(), client, newScreen),
									new GoToLoans("Back", this.getGUI(), client) })),
					"CRITICAL ERROR: ACCOUNT OR LOAN NOT FOUND!");
			this.getGUI().setFrame(newScreen);
			return;
		}

		int priceWithFees = (int) Math.round(amount * (1 + Bank.Bank.TRANSACTION_FEE));
		if (finalAccount.getWallet().getAmountOf(finalLoan.getCurrency()) < priceWithFees
				|| finalLoan.getValue() < amount) {
			PayLoanScreen newScreen = new PayLoanScreen(loans);
			newScreen.initialize(new ArrayList<BankAction>(Arrays.asList(new BankAction[] {
					new PayLoan(this.getGUI(), client, newScreen), new GoToLoans("Back", this.getGUI(), client) })),
					"Not Enough Balance!");
			this.getGUI().setFrame(newScreen);
			return;
		}

		finalAccount.withdrawFunds(finalLoan.getCurrency(), priceWithFees);
		this.getGUI().getBank().getGlobalAccount().depositFunds(finalLoan.getCurrency(), amount);

		Account bankAccount = this.getGUI().getBank().getGlobalAccount();
		bankAccount.depositFunds(finalLoan.getCurrency(), (priceWithFees - amount));

		C2B transaction = Database.getInstance().createTransactionC2B(amount, finalLoan.getCurrency(),
				this.getGUI().getBank().getGlobalAccount(), finalAccount);
		client.addTransaction(new Date(), transaction);
		C2B transactionFee = Database.getInstance().createTransactionC2B((priceWithFees - amount),
				finalLoan.getCurrency(), bankAccount, finalAccount);
		this.getGUI().getBank().getManager().addTransaction(new Date(), transactionFee);

		finalLoan.setValue(finalLoan.getValue() - amount);
		if (finalLoan.getValue() == 0) {
			client.getLoans().remove(finalLoan.getID());
			this.getGUI().getBank().getLoans().remove(finalLoan);
		}

		LoanScreen newScreen = new LoanScreen(loans);
		newScreen.initialize(
				new ArrayList<BankAction>(Arrays.asList(new BankAction[] { new GoToRequestLoan(this.getGUI(), client),
						new GoToPayLoan(this.getGUI(), client, loans), new GoToClientMain(this.getGUI(), client) })),
				"");
		this.getGUI().setFrame(newScreen);
	}

}
