package GUI.General.Actions;

import Accounts.Account;
import Accounts.Checking;
import Accounts.Saving;
import Accounts.Securities;
import GUI.Abstraction.BankAction;
import GUI.BankGUI;
import GUI.Client.Actions.Navigation.*;
import GUI.Client.CheckingAccountScreen;
import GUI.Client.SavingsAccountScreen;
import GUI.Client.SecuritiesAccountScreen;
import GUI.General.Actions.Navigation.GoToDeposit;
import GUI.General.Actions.Navigation.GoToLogin;
import GUI.General.Actions.Navigation.GoToTransfer;
import GUI.General.Actions.Navigation.GoToWithdraw;
import GUI.General.TransferScreen;
import GUI.Manager.Actions.Navigation.GoToEditInfo;
import GUI.Manager.Actions.Navigation.GoToTransactions;
import Transactions.Types.B2C;
import Transactions.Types.C2B;
import Transactions.Types.C2C;
import GUI.Manager.Actions.Navigation.*;
import GUI.Manager.ManagerMainScreen;
import Users.Client;
import Users.Manager;
import Users.User;
import Persistent.Database;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class TransferMoney extends BankAction {

	private static final long serialVersionUID = 1L;

	private User user;
	private Account account;
	private TransferScreen screen;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public TransferMoney(BankGUI gui, User user, Account account, TransferScreen screen) {
		super("Transfer", gui);
		this.user = user;
		this.account = account;
		this.screen = screen;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method is a tiny bit complicated as it needs to allow for errors to
	 * occur regardless of what Account type, and even User type it is dealing with.
	 * 
	 * In the broadest terms, it allows for the Transfer of money between accounts,
	 * checking for valid inputs, if the User has the funds to proceed with the
	 * Transfer, and then actually completing the Transfer.
	 */
	public void actionPerformed(ActionEvent e) {
		if (this.checkValid()) {
			TransferScreen newScreen = new TransferScreen(account);

			if (user instanceof Manager) {
				Manager castedUser = (Manager) user;
				newScreen
						.initialize(
								new ArrayList<BankAction>(Arrays.asList(
										new BankAction[] { new TransferMoney(this.getGUI(), user, account, newScreen),
												new GoToManagerMain(this.getGUI(), castedUser) })),
								"Invalid Transfer Fields!");
			} else {
				Client castedUser = (Client) user;
				if (account instanceof Checking)
					newScreen.initialize(
							new ArrayList<BankAction>(Arrays.asList(
									new BankAction[] { new TransferMoney(this.getGUI(), user, account, newScreen),
											new GoToChecking("Back", this.getGUI(), castedUser) })),
							"Invalid Transfer Fields!");
				else if (account instanceof Saving)
					newScreen.initialize(
							new ArrayList<BankAction>(Arrays.asList(
									new BankAction[] { new TransferMoney(this.getGUI(), user, account, newScreen),
											new GoToSavings("Back", this.getGUI(), castedUser) })),
							"Invalid Transfer Fields!");
				else
					newScreen.initialize(
							new ArrayList<BankAction>(Arrays.asList(
									new BankAction[] { new TransferMoney(this.getGUI(), user, account, newScreen),
											new GoToSecurities("Back", this.getGUI(), castedUser) })),
							"Invalid Transfer Fields!");
			}

			this.getGUI().setFrame(newScreen);
			return;
		}

		Account otherAccount = null;

		for (Account tempAccount : this.getGUI().getBank().getAccounts()) {
			if (tempAccount.getID() == Long.valueOf(screen.getTarget())) {
				otherAccount = tempAccount;
				break;
			}
		}

		if (otherAccount == null) {
			TransferScreen newScreen = new TransferScreen(account);

			if (user instanceof Manager) {
				Manager castedUser = (Manager) user;
				newScreen.initialize(
						new ArrayList<BankAction>(Arrays
								.asList(new BankAction[] { new TransferMoney(this.getGUI(), user, account, newScreen),
										new GoToManagerMain(this.getGUI(), castedUser) })),
						"Target Account Not Found!");
			} else {
				Client castedUser = (Client) user;
				if (account instanceof Checking)
					newScreen.initialize(
							new ArrayList<BankAction>(Arrays.asList(
									new BankAction[] { new TransferMoney(this.getGUI(), user, account, newScreen),
											new GoToChecking("Back", this.getGUI(), castedUser) })),
							"Target Account Not Found!");
				else if (account instanceof Saving)
					newScreen.initialize(
							new ArrayList<BankAction>(Arrays.asList(
									new BankAction[] { new TransferMoney(this.getGUI(), user, account, newScreen),
											new GoToSavings("Back", this.getGUI(), castedUser) })),
							"Target Account Not Found!");
				else
					newScreen.initialize(
							new ArrayList<BankAction>(Arrays.asList(
									new BankAction[] { new TransferMoney(this.getGUI(), user, account, newScreen),
											new GoToSecurities("Back", this.getGUI(), castedUser) })),
							"Target Account Not Found!");
			}

			this.getGUI().setFrame(newScreen);
			return;
		}

		String parsedCurrency = screen.getSelectedCurrency().split(" ")[1];
		int amount = Integer.valueOf(screen.getAmount());
		int fees = (int) Math.round(amount * Bank.Bank.TRANSACTION_FEE);

		if (account.getWallet().getAmountOf(parsedCurrency) < amount + fees) {
			TransferScreen newScreen = new TransferScreen(account);

			if (user instanceof Manager) {
				Manager castedUser = (Manager) user;
				newScreen
						.initialize(
								new ArrayList<BankAction>(Arrays.asList(
										new BankAction[] { new TransferMoney(this.getGUI(), user, account, newScreen),
												new GoToManagerMain(this.getGUI(), castedUser) })),
								"Not Enough Funds!");
			} else {
				Client castedUser = (Client) user;
				if (account instanceof Checking)
					newScreen.initialize(
							new ArrayList<BankAction>(Arrays.asList(
									new BankAction[] { new TransferMoney(this.getGUI(), user, account, newScreen),
											new GoToChecking("Back", this.getGUI(), castedUser) })),
							"Not Enough Funds!");
				else if (account instanceof Saving)
					newScreen.initialize(
							new ArrayList<BankAction>(Arrays.asList(
									new BankAction[] { new TransferMoney(this.getGUI(), user, account, newScreen),
											new GoToSavings("Back", this.getGUI(), castedUser) })),
							"Not Enough Funds!");
				else
					newScreen.initialize(
							new ArrayList<BankAction>(Arrays.asList(
									new BankAction[] { new TransferMoney(this.getGUI(), user, account, newScreen),
											new GoToSecurities("Back", this.getGUI(), castedUser) })),
							"Not Enough Funds!");
			}

			this.getGUI().setFrame(newScreen);
			return;
		}

		account.withdrawFunds(parsedCurrency, amount + fees);
		otherAccount.depositFunds(parsedCurrency, amount);

		Account bankAccount = this.getGUI().getBank().getGlobalAccount();
		bankAccount.depositFunds(parsedCurrency, fees);

		if (user instanceof Manager) {
			Manager castedUser = (Manager) user;

			B2C transaction = Database.getInstance().createTransactionB2C(amount, parsedCurrency, account,
					otherAccount);
			castedUser.addTransaction(new Date(), transaction);

			Checking castedAccount = (Checking) account;
			ManagerMainScreen newScreen = new ManagerMainScreen(castedAccount);
			newScreen.initialize(new ArrayList<BankAction>(Arrays.asList(new BankAction[] {
					new GoToEditInfo(this.getGUI(), castedUser),
					new GoToManageClients("Manage Clients", this.getGUI(), castedUser),
					new GoToTransactions("View Transactions", this.getGUI(), castedUser),
					new GoToManageLoans("Manage Loans", this.getGUI(), castedUser),
					new GoToManageStock("Manage Stock", this.getGUI(), castedUser),
					new GoToLogin("Logout", this.getGUI()), new GoToTransfer(this.getGUI(), castedUser, castedAccount),
					new GoToDeposit(this.getGUI(), castedUser, castedAccount),
					new GoToWithdraw(this.getGUI(), castedUser, castedAccount) })), "");
			this.getGUI().setFrame(newScreen);
		} else {
			Client castedUser = (Client) user;

			C2C transaction = Database.getInstance().createTransactionC2C(amount, parsedCurrency, account,
					otherAccount);
			castedUser.addTransaction(new Date(), transaction);
			C2B transactionFee = Database.getInstance().createTransactionC2B(fees, parsedCurrency, bankAccount,
					account);
			this.getGUI().getBank().getManager().addTransaction(new Date(), transactionFee);

			if (account instanceof Saving) {
				Saving castedAccount = (Saving) account;
				SavingsAccountScreen newScreen = new SavingsAccountScreen(castedAccount);
				newScreen.initialize(new ArrayList<BankAction>(
						Arrays.asList(new BankAction[] { new GoToTransfer(this.getGUI(), castedUser, castedAccount),
								new GoToAccounts("Back", this.getGUI(), castedUser) })),
						"");
				this.getGUI().setFrame(newScreen);

			} else if (account instanceof Checking) {
				Checking castedAccount = (Checking) account;
				CheckingAccountScreen newScreen = new CheckingAccountScreen(castedAccount);
				newScreen.initialize(new ArrayList<BankAction>(
						Arrays.asList(new BankAction[] { new GoToTransfer(this.getGUI(), castedUser, account),
								new GoToAccounts("Back", this.getGUI(), castedUser),
								new GoToWithdraw(this.getGUI(), castedUser, castedAccount),
								new GoToDeposit(this.getGUI(), castedUser, castedAccount) })),
						"");
				this.getGUI().setFrame(newScreen);
			} else {
				Securities castedAccount = (Securities) account;
				SecuritiesAccountScreen newScreen = new SecuritiesAccountScreen(castedAccount,
						this.getGUI().getBank().getStockMarket());
				newScreen.initialize(new ArrayList<BankAction>(
						Arrays.asList(new BankAction[] { new GoToBuyStock(this.getGUI(), castedUser, castedAccount),
								new GoToSellStock(this.getGUI(), castedUser, castedAccount),
								new GoToTransfer(this.getGUI(), castedUser, castedAccount),
								new GoToAccounts("Back", this.getGUI(), castedUser) })),
						"");
				this.getGUI().setFrame(newScreen);
			}
		}
	}

	/* ============== */
	/* Helper Methods */
	/* ============== */

	/*
	 * This method simply checks for valid inputs.
	 */
	private boolean checkValid() {
		return screen.getTarget() == null || screen.getAmount() == null || screen.getSelectedCurrency() == null
				|| screen.getTarget().isEmpty() || screen.getAmount().isEmpty()
				|| screen.getSelectedCurrency().isEmpty();
	}

}
