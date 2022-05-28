package GUI.Client;

import java.awt.Font;
import java.awt.ScrollPane;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

import GUI.Abstraction.BankAction;
import GUI.Abstraction.BankFrame;
import Transactions.Transaction;
import Transactions.Types.B2C;
import Transactions.Types.C2B;
import Transactions.Types.C2N;
import Transactions.Types.N2C;
import Users.Client;

import java.util.ArrayList;
import java.util.Date;

public class TransactionScreen extends BankFrame {

	private static final long serialVersionUID = 1L;

	private Client client;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public TransactionScreen(Client client) {
		super(100, 100, 536, 334);
		this.client = client;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method builds the actual Transaction Info GUI Screen with all its
	 * requirements.
	 */
	@Override
	public void initialize(ArrayList<BankAction> actions, String errorMessage) {

		JLabel title = new JLabel("Client Transactions");
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title.setBounds(10, 11, 414, 35);
		this.addComponent(title);

		JLabel transactionsTitle = new JLabel("Past Transactions");
		transactionsTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		transactionsTitle.setBounds(10, 57, 154, 14);
		this.addComponent(transactionsTitle);

		JList<String> transactions = new JList<String>();
		transactions.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = processOwnTransactions();

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
		transactions.setBounds(10, 82, 500, 144);

		ScrollPane transactionsContainer = new ScrollPane();
		transactionsContainer.add(transactions);
		transactionsContainer.setBounds(10, 82, 500, 144);
		this.addComponent(transactionsContainer);

		JButton backBtn = new JButton("Back");
		backBtn.setBounds(421, 261, 89, 23);
		backBtn.setAction(actions.get(0));
		this.addComponent(backBtn);
	}

	/* ============== */
	/* Helper Methods */
	/* ============== */

	/*
	 * Returns a formatted String Array to display all Client Transactions.
	 */
	public String[] processOwnTransactions() {
		ArrayList<String> scrapValues = new ArrayList<String>();
		for (Date d : client.getTransactions().keySet()) {
			for (Transaction t : client.getTransactions().get(d)) {
				String type = "Client to Client";
				if (t instanceof N2C)
					type = "Third Party To Client";
				else if (t instanceof C2N)
					type = "Client to Third Party";
				else if (t instanceof B2C)
					type = "Bank to Client";
				else if (t instanceof C2B)
					type = "Client to Bank";

				String s = "- Transaction #" + t.getId() + " | Type: " + type + " | Currency: " + t.getCurrency()
						+ " | Amount: " + t.getValue();
				scrapValues.add(s);
			}
		}

		String[] formattedValues = new String[scrapValues.size()];
		for (int i = 0; i < scrapValues.size(); i++)
			formattedValues[i] = scrapValues.get(i);

		return formattedValues;
	}

}
