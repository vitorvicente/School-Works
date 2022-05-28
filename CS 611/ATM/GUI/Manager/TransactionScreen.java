package GUI.Manager;

import java.awt.Font;
import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

import GUI.Abstraction.BankAction;
import GUI.Abstraction.BankFrame;
import Transactions.Transaction;
import Transactions.Types.B2C;
import Transactions.Types.B2N;
import Transactions.Types.C2B;
import Transactions.Types.C2C;
import Transactions.Types.C2N;
import Transactions.Types.N2B;
import Transactions.Types.N2C;
import Users.Client;
import Users.Manager;

public class TransactionScreen extends BankFrame {

	private static final long serialVersionUID = 1L;

	private ArrayList<Client> clients;
	private Manager manager;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public TransactionScreen(ArrayList<Client> clients, Manager manager) {
		super(100, 100, 536, 334);
		this.clients = clients;
		this.manager = manager;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method builds the actual Transactions Info GUI Screen with all its
	 * requirements.
	 */
	@Override
	public void initialize(ArrayList<BankAction> actions, String errorMessage) {
		JLabel title = new JLabel("All Bank Transactions");
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title.setBounds(10, 11, 414, 35);
		this.addComponent(title);

		JLabel transactionsTitle = new JLabel("Transactions");
		transactionsTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		transactionsTitle.setBounds(10, 57, 154, 14);
		this.addComponent(transactionsTitle);

		JList<String> transactions = new JList<String>();
		transactions.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = processTransactions();

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

		JButton olderTransactionsButton = new JButton("View Older Transactions");
		olderTransactionsButton.setBounds(10, 232, 174, 23);
		olderTransactionsButton.setAction(actions.get(0));
		this.addComponent(olderTransactionsButton);

		JButton backBtn = new JButton("Back");
		backBtn.setBounds(421, 261, 89, 23);
		backBtn.setAction(actions.get(1));
		this.addComponent(backBtn);
	}

	/* ============== */
	/* Helper Methods */
	/* ============== */

	/*
	 * Returns a formatted String Array to display all the Transactions that have
	 * ever occurred in the Bank that have yet to be seen by the Manager.
	 */
	public String[] processTransactions() {
		ArrayList<String> scrapValues = new ArrayList<String>();

		for (Client client : clients) {
			for (Date d : client.getTransactions().keySet()) {
				for (Transaction t : client.getTransactions().get(d)) {
					if (t.isSeen())
						continue;

					t.setSeen(true);

					String type = "";
					String source = "";
					String destination = "";
					if (t instanceof N2C) {
						type = "Third Party To Client";
						source = "NA";
						destination = "#" + ((N2C) t).getClientAccount().getID();
					} else if (t instanceof C2N) {
						type = "Client to Third Party";
						source = "#" + ((C2N) t).getClientAccount().getID();
						destination = "NA";
					} else if (t instanceof B2C) {
						type = "Bank to Client";
						source = "#" + ((B2C) t).getBankAccount().getID();
						destination = "#" + ((B2C) t).getClientAccount().getID();
					} else if (t instanceof C2B) {
						type = "Client to Bank";
						source = "#" + ((C2B) t).getClientAccount().getID();
						destination = "#" + ((C2B) t).getBankAccount().getID();
					} else if (t instanceof N2B) {
						type = "Third Party to Bank";
						source = "NA";
						destination = "#" + ((N2B) t).getBankAccount().getID();
					} else if (t instanceof B2N) {
						type = "Bank to Third Party";
						source = "#" + ((B2N) t).getBankAccount().getID();
						destination = "NA";
					} else {
						type = "Client to Client";
						source = "#" + ((C2C) t).getClientAccountOne().getID();
						destination = "#" + ((C2C) t).getClientAccountTwo().getID();
					}

					String s = "- Transaction #" + t.getId() + " | Type: " + type + " | Source Account: " + source
							+ " | Destination Account: " + destination + " | Currency: " + t.getCurrency()
							+ " | Amount: " + t.getValue();
					scrapValues.add(s);
				}
			}
		}

		for (Date d : manager.getTransactions().keySet()) {
			for (Transaction t : manager.getTransactions().get(d)) {
				if (t.isSeen())
					continue;

				t.setSeen(true);

				String type = "";
				String source = "";
				String destination = "";
				if (t instanceof B2C) {
					type = "Bank to Client";
					source = "#" + ((B2C) t).getBankAccount().getID();
					destination = "#" + ((B2C) t).getClientAccount().getID();
				} else if (t instanceof C2B) {
					type = "Client to Bank";
					source = "#" + ((C2B) t).getClientAccount().getID();
					destination = "#" + ((C2B) t).getBankAccount().getID();
				} else if (t instanceof N2B) {
					type = "Third Party to Bank";
					source = "NA";
					destination = "#" + ((N2B) t).getBankAccount().getID();
				} else if (t instanceof B2N) {
					type = "Bank to Third Party";
					source = "#" + ((B2N) t).getBankAccount().getID();
					destination = "NA";
				}

				String s = "- Transaction #" + t.getId() + " | Type: " + type + " | Source Account: " + source
						+ " | Destination Account: " + destination + " | Currency: " + t.getCurrency() + " | Amount: "
						+ t.getValue();
				scrapValues.add(s);
			}
		}

		String[] formattedValues = new String[scrapValues.size()];
		for (int i = 0; i < scrapValues.size(); i++)
			formattedValues[i] = scrapValues.get(i);

		return formattedValues;
	}

}
