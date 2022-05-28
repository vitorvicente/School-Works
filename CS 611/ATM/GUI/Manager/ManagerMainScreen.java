package GUI.Manager;

import java.awt.Font;
import java.awt.ScrollPane;
import java.util.ArrayList;

import javax.swing.JLabel;

import Accounts.Checking;
import GUI.Abstraction.BankAction;
import GUI.Abstraction.BankFrame;

import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.AbstractListModel;

public class ManagerMainScreen extends BankFrame {

	private static final long serialVersionUID = 1L;

	private Checking checkingAccount;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public ManagerMainScreen(Checking account) {
		super(100, 100, 450, 461);
		this.checkingAccount = account;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method builds the actual Main Manager GUI Screen with all its
	 * requirements.
	 */
	@Override
	public void initialize(ArrayList<BankAction> actions, String errorMessage) {
		JLabel title = new JLabel("Manager Control Panel");
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title.setBounds(10, 11, 414, 35);
		this.addComponent(title);

		JLabel currentBalanceTitle = new JLabel("Current Balances");
		currentBalanceTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		currentBalanceTitle.setBounds(10, 57, 115, 14);
		this.addComponent(currentBalanceTitle);

		JList<String> currentBalance = new JList<String>();
		currentBalance.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = processCurrentBalance();

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
		currentBalance.setBounds(10, 82, 149, 329);

		ScrollPane currentBalanceContainer = new ScrollPane();
		currentBalanceContainer.add(currentBalance);
		currentBalanceContainer.setBounds(10, 82, 149, 329);
		this.addComponent(currentBalanceContainer);

		JButton editInfoBtn = new JButton("Edit Personal Information");
		editInfoBtn.setBounds(239, 79, 153, 23);
		editInfoBtn.setAction(actions.get(0));
		this.addComponent(editInfoBtn);

		JButton manageClientsBtn = new JButton("Manage Clients");
		manageClientsBtn.setBounds(239, 112, 153, 23);
		manageClientsBtn.setAction(actions.get(1));
		this.addComponent(manageClientsBtn);

		JButton viewTransactionsBtn = new JButton("View Transactions");
		viewTransactionsBtn.setBounds(239, 146, 153, 23);
		viewTransactionsBtn.setAction(actions.get(2));
		this.addComponent(viewTransactionsBtn);

		JButton manageLoansBtn = new JButton("Manage Loans");
		manageLoansBtn.setBounds(239, 180, 153, 23);
		manageLoansBtn.setAction(actions.get(3));
		this.addComponent(manageLoansBtn);

		JButton manageStockBtn = new JButton("Manage Stock");
		manageStockBtn.setBounds(239, 214, 153, 23);
		manageStockBtn.setAction(actions.get(4));
		this.addComponent(manageStockBtn);

		JButton btnTransferMoney = new JButton("Transfer Money");
		btnTransferMoney.setBounds(239, 248, 153, 23);
		btnTransferMoney.setAction(actions.get(6));
		this.addComponent(btnTransferMoney);

		JButton btnDepositMoney = new JButton("Deposit Money");
		btnDepositMoney.setBounds(239, 282, 153, 23);
		btnDepositMoney.setAction(actions.get(7));
		this.addComponent(btnDepositMoney);

		JButton btnWithdrawMoney = new JButton("Withdraw Money");
		btnWithdrawMoney.setBounds(239, 316, 153, 23);
		btnWithdrawMoney.setAction(actions.get(8));
		this.addComponent(btnWithdrawMoney);

		JButton logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(318, 388, 106, 23);
		logoutBtn.setAction(actions.get(5));
		this.addComponent(logoutBtn);
	}

	/* ============== */
	/* Helper Methods */
	/* ============== */

	/*
	 * Returns a formatted String Array to display all the currencies the Manager
	 * has in their Global Account.
	 */
	public String[] processCurrentBalance() {
		ArrayList<String> scrapValues = new ArrayList<String>();
		for (String c : checkingAccount.getWallet().getValues().keySet()) {
			String s = "- " + c + ": " + checkingAccount.getWallet().getAmountOf(c);
			scrapValues.add(s);
		}

		String[] formattedValues = new String[scrapValues.size()];
		for (int i = 0; i < scrapValues.size(); i++) {
			formattedValues[i] = scrapValues.get(i);
		}

		return formattedValues;
	}

}
