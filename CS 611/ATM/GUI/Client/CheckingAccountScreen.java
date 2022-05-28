package GUI.Client;

import Accounts.Checking;
import GUI.Abstraction.BankAction;
import GUI.Abstraction.BankFrame;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.ScrollPane;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JButton;

public class CheckingAccountScreen extends BankFrame {

	private static final long serialVersionUID = 1L;

	private Checking checkingAccount;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public CheckingAccountScreen(Checking checkingAccount) {
		super(100, 100, 450, 300);
		this.checkingAccount = checkingAccount;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method builds the actual Securities Account GUI Screen with all its
	 * requirements.
	 */
	@Override
	public void initialize(ArrayList<BankAction> actions, String errorMessage) {
		JLabel title = new JLabel("Checking Account #" + checkingAccount.getID());
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title.setBounds(10, 11, 414, 35);
		this.addComponent(title);

		JLabel currenciesTitle = new JLabel("Current Currencies");
		currenciesTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		currenciesTitle.setBounds(10, 57, 154, 14);
		this.addComponent(currenciesTitle);

		JList<String> currenciesList = new JList<String>();
		currenciesList.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = processCurrentBalance();

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
		currenciesList.setBounds(10, 82, 208, 168);

		ScrollPane currenciesContainer = new ScrollPane();
		currenciesContainer.add(currenciesList);
		currenciesContainer.setBounds(10, 82, 208, 168);
		this.addComponent(currenciesContainer);

		JButton transferBtn = new JButton("Transfer Money");
		transferBtn.setBounds(284, 103, 140, 23);
		transferBtn.setAction(actions.get(0));
		this.addComponent(transferBtn);

		JButton withdrawBtn = new JButton("Withdraw Money");
		withdrawBtn.setBounds(284, 171, 140, 23);
		withdrawBtn.setAction(actions.get(2));
		this.addComponent(withdrawBtn);

		JButton depositBtn = new JButton("Deposit Money");
		depositBtn.setBounds(284, 137, 140, 23);
		depositBtn.setAction(actions.get(3));
		this.addComponent(depositBtn);

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
