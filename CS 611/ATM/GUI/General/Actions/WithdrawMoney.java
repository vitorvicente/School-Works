package GUI.General.Actions;

import Accounts.Account;
import Accounts.Checking;
import GUI.Abstraction.BankAction;
import GUI.BankGUI;
import GUI.Client.Actions.Navigation.GoToAccounts;
import GUI.Client.Actions.Navigation.GoToChecking;
import GUI.Client.CheckingAccountScreen;
import GUI.General.Actions.Navigation.GoToDeposit;
import GUI.General.Actions.Navigation.GoToLogin;
import GUI.General.Actions.Navigation.GoToTransfer;
import GUI.General.Actions.Navigation.GoToWithdraw;
import GUI.General.WithdrawScreen;
import GUI.Manager.Actions.Navigation.*;
import Transactions.Types.B2N;
import Transactions.Types.C2B;
import Transactions.Types.C2N;
import GUI.Manager.ManagerMainScreen;
import Users.Client;
import Users.Manager;
import Users.User;
import Persistent.Database;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class WithdrawMoney extends BankAction {

	private static final long serialVersionUID = 1L;

	private User user;
	private Checking account;
	private WithdrawScreen screen;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public WithdrawMoney(BankGUI gui, User user, Checking account, WithdrawScreen screen) {
		super("Withdraw", gui);
		this.user = user;
		this.account = account;
		this.screen = screen;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method proceeds with the actual Withdraw of money from a User account.
	 * It checks for valid inputs, it checks if the User has enough money, and
	 * proceeds to actually withdraw the amount.
	 */
	public void actionPerformed(ActionEvent e) {
		if (screen.getSelectedCurrency() == null || screen.getAmount() == null || screen.getSelectedCurrency().isEmpty()
				|| screen.getAmount().isEmpty()) {
			WithdrawScreen newScreen = new WithdrawScreen(account);

			if (user instanceof Manager) {
				Manager castedUser = (Manager) user;
				newScreen.initialize(new ArrayList<BankAction>(Arrays
						.asList(new BankAction[] { new WithdrawMoney(this.getGUI(), castedUser, account, newScreen),
								new GoToManagerMain(this.getGUI(), castedUser) })),
						"Invalid Fields Entered!");
			} else {
				Client castedUser = (Client) user;
				newScreen.initialize(
						new ArrayList<BankAction>(Arrays.asList(
								new BankAction[] { new WithdrawMoney(this.getGUI(), castedUser, account, newScreen),
										new GoToChecking("Back", this.getGUI(), castedUser) })),
						"Invalid Fields Entered!");
			}

			this.getGUI().setFrame(newScreen);
			return;
		}

		String parsedCurrency = screen.getSelectedCurrency().split(" ")[1];
		int amount = Integer.valueOf(screen.getAmount());
		int fees = (int) Math.round(amount * Bank.Bank.TRANSACTION_FEE);

		if (account.getWallet().getAmountOf(parsedCurrency) < amount + fees) {
			WithdrawScreen newScreen = new WithdrawScreen(account);

			if (user instanceof Manager) {
				Manager castedUser = (Manager) user;
				newScreen.initialize(new ArrayList<BankAction>(Arrays
						.asList(new BankAction[] { new WithdrawMoney(this.getGUI(), castedUser, account, newScreen),
								new GoToManagerMain(this.getGUI(), castedUser) })),
						"Not Enough Balance!");
			} else {
				Client castedUser = (Client) user;
				newScreen.initialize(new ArrayList<BankAction>(Arrays
						.asList(new BankAction[] { new WithdrawMoney(this.getGUI(), castedUser, account, newScreen),
								new GoToChecking("Back", this.getGUI(), castedUser) })),
						"Not Enough Balance!");
			}

			this.getGUI().setFrame(newScreen);
			return;
		}

		account.withdrawFunds(parsedCurrency, amount + fees);

		Account bankAccount = this.getGUI().getBank().getGlobalAccount();
		bankAccount.depositFunds(parsedCurrency, fees);

		if (user instanceof Manager) {
			Manager castedUser = (Manager) user;

			B2N transaction = Database.getInstance().createTransactionB2N(amount, parsedCurrency, account);
			castedUser.addTransaction(new Date(), transaction);

			ManagerMainScreen newScreen = new ManagerMainScreen(account);
			newScreen.initialize(new ArrayList<BankAction>(Arrays.asList(new BankAction[] {
					new GoToEditInfo(this.getGUI(), castedUser),
					new GoToManageClients("Manage Clients", this.getGUI(), castedUser),
					new GoToTransactions("View Transactions", this.getGUI(), castedUser),
					new GoToManageLoans("Manage Loans", this.getGUI(), castedUser),
					new GoToManageStock("Manage Stock", this.getGUI(), castedUser),
					new GoToLogin("Logout", this.getGUI()), new GoToTransfer(this.getGUI(), castedUser, account),
					new GoToDeposit(this.getGUI(), castedUser, account),
					new GoToWithdraw(this.getGUI(), castedUser, account) })), "");
			this.getGUI().setFrame(newScreen);
		} else {
			Client castedUser = (Client) user;

			C2N transaction = Database.getInstance().createTransactionC2N(amount, parsedCurrency, account);
			castedUser.addTransaction(new Date(), transaction);
			C2B transactionFee = Database.getInstance().createTransactionC2B(fees, parsedCurrency, bankAccount,
					account);
			this.getGUI().getBank().getManager().addTransaction(new Date(), transactionFee);

			CheckingAccountScreen newScreen = new CheckingAccountScreen(account);
			newScreen.initialize(new ArrayList<BankAction>(
					Arrays.asList(new BankAction[] { new GoToTransfer(this.getGUI(), castedUser, account),
							new GoToAccounts("Back", this.getGUI(), castedUser),
							new GoToWithdraw(this.getGUI(), castedUser, account),
							new GoToDeposit(this.getGUI(), castedUser, account) })),
					"");
			this.getGUI().setFrame(newScreen);
		}
	}

}
