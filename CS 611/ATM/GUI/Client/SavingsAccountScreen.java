package GUI.Client;

import Accounts.Saving;
import GUI.Abstraction.BankAction;
import GUI.Abstraction.BankFrame;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.ScrollPane;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.AbstractListModel;

public class SavingsAccountScreen extends BankFrame {

	private static final long serialVersionUID = 1L;

	private Saving savingsAccount;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public SavingsAccountScreen(Saving savingsAccount) {
		super(100, 100, 450, 300);
		this.savingsAccount = savingsAccount;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method builds the actual Savings Account GUI Screen with all its
	 * requirements.
	 */
	@Override
	public void initialize(ArrayList<BankAction> actions, String errorMessage) {
		JLabel title = new JLabel("Savings Account #" + savingsAccount.getID());
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title.setBounds(10, 11, 414, 35);
		this.addComponent(title);

		int percentage = (int) Math.round(savingsAccount.getInterestRate() * 100);

		JLabel interestRate = new JLabel("Interest Rate: " + percentage + "%");
		interestRate.setBounds(10, 57, 98, 14);
		this.addComponent(interestRate);

		JLabel currenciesTitle = new JLabel("Current Currencies");
		currenciesTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		currenciesTitle.setBounds(10, 82, 154, 14);
		this.addComponent(currenciesTitle);

		JList<String> currencies = new JList<String>();
		currencies.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 3L;
			String[] values = processCurrentBalance();

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
		currencies.setBounds(10, 107, 208, 149);

		ScrollPane currenciesContainer = new ScrollPane();
		currenciesContainer.add(currencies);
		currenciesContainer.setBounds(10, 107, 208, 149);
		this.addComponent(currenciesContainer);

		JButton transferBtn = new JButton("Transfer Money");
		transferBtn.setBounds(284, 162, 140, 23);
		transferBtn.setAction(actions.get(0));
		this.addComponent(transferBtn);

		JButton backBtn = new JButton("Back");
		backBtn.setBounds(335, 227, 89, 23);
		backBtn.setAction(actions.get(1));
		this.addComponent(backBtn);
	}

	/* ============== */
	/* Helper Methods */
	/* ============== */

	/*
	 * Returns a formatted String Array to display all the currencies the Client
	 * currently has in this account.
	 */
	public String[] processCurrentBalance() {
		ArrayList<String> scrapValues = new ArrayList<String>();
		for (String c : savingsAccount.getWallet().getValues().keySet()) {
			String s = "- " + c + ": " + savingsAccount.getWallet().getAmountOf(c);
			scrapValues.add(s);
		}

		String[] formattedValues = new String[scrapValues.size()];
		for (int i = 0; i < scrapValues.size(); i++)
			formattedValues[i] = scrapValues.get(i);

		return formattedValues;
	}

}
