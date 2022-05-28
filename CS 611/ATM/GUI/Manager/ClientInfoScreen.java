package GUI.Manager;

import java.awt.Font;
import java.awt.ScrollPane;
import java.util.ArrayList;

import javax.swing.JLabel;

import Accounts.Checking;
import Accounts.Saving;
import Accounts.Securities;
import GUI.Abstraction.BankAction;
import GUI.Abstraction.BankFrame;
import Miscellaneous.StockMarket;
import Users.Client;

import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.AbstractListModel;

public class ClientInfoScreen extends BankFrame {

	private static final long serialVersionUID = 1L;

	private Client client;
	private Saving savingsAccount;
	private Checking checkingAccount;
	private Securities securitiesAccount;
	private StockMarket stockMarket;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public ClientInfoScreen(Client client, Saving savingsAccount, Checking checkingAccount,
			Securities securitiesAccount, StockMarket stockMarket) {
		super(100, 100, 790, 586);

		this.client = client;
		this.savingsAccount = savingsAccount;
		this.checkingAccount = checkingAccount;
		this.securitiesAccount = securitiesAccount;
		this.stockMarket = stockMarket;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method builds the actual Client Info GUI Screen with all its
	 * requirements.
	 */
	@Override
	public void initialize(ArrayList<BankAction> actions, String errorMessage) {
		JLabel title = new JLabel("Client Info");
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title.setBounds(10, 11, 414, 35);
		this.addComponent(title);

		JLabel name = new JLabel("Name: " + client.getFullName());
		name.setFont(new Font("Tahoma", Font.PLAIN, 11));
		name.setBounds(10, 57, 198, 14);
		this.addComponent(name);

		JLabel username = new JLabel("Username: " + client.getUsername());
		username.setFont(new Font("Tahoma", Font.PLAIN, 11));
		username.setBounds(218, 57, 206, 14);
		this.addComponent(username);

		JLabel address = new JLabel("Address " + client.getAddress());
		address.setFont(new Font("Tahoma", Font.PLAIN, 11));
		address.setBounds(10, 85, 198, 14);
		this.addComponent(address);

		JLabel phone = new JLabel("Phone: " + client.getPhoneNumber());
		phone.setFont(new Font("Tahoma", Font.PLAIN, 11));
		phone.setBounds(218, 85, 206, 14);
		this.addComponent(phone);

		JLabel savingsAccountTitle = new JLabel("Savings Account");
		savingsAccountTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		savingsAccountTitle.setBounds(10, 138, 198, 14);
		this.addComponent(savingsAccountTitle);

		JLabel savingsAccountID = new JLabel("Account #" + savingsAccount.getID());
		savingsAccountID.setFont(new Font("Tahoma", Font.PLAIN, 11));
		savingsAccountID.setBounds(10, 163, 206, 14);
		this.addComponent(savingsAccountID);

		JList<String> savingsAccountBalances = new JList<String>();
		savingsAccountBalances.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = processCurrentSavingsBalance();

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
		savingsAccountBalances.setBounds(10, 188, 198, 249);

		ScrollPane savingsAccountContainer = new ScrollPane();
		savingsAccountContainer.add(savingsAccountBalances);
		savingsAccountContainer.setBounds(10, 188, 198, 249);
		this.addComponent(savingsAccountContainer);

		JLabel checkingAccountTitle = new JLabel("Checking Account");
		checkingAccountTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		checkingAccountTitle.setBounds(218, 138, 198, 14);
		this.addComponent(checkingAccountTitle);

		JLabel checkingAccountID = new JLabel("Account #" + checkingAccount.getID());
		checkingAccountID.setFont(new Font("Tahoma", Font.PLAIN, 11));
		checkingAccountID.setBounds(218, 163, 206, 14);
		this.addComponent(checkingAccountID);

		JList<String> checkingAccountBalances = new JList<String>();
		checkingAccountBalances.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = processCurrentCheckingBalance();

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
		checkingAccountBalances.setBounds(218, 188, 198, 249);

		ScrollPane checkingAccountContainer = new ScrollPane();
		checkingAccountContainer.add(checkingAccountBalances);
		checkingAccountContainer.setBounds(218, 188, 198, 249);
		this.addComponent(checkingAccountContainer);

		JLabel securitiesAccountTitle = new JLabel("Securities Account");
		securitiesAccountTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		securitiesAccountTitle.setBounds(426, 138, 198, 14);
		this.addComponent(securitiesAccountTitle);

		JLabel securitiesAccountID = new JLabel("Account #" + securitiesAccount.getID());
		securitiesAccountID.setFont(new Font("Tahoma", Font.PLAIN, 11));
		securitiesAccountID.setBounds(426, 163, 206, 14);
		this.addComponent(securitiesAccountID);

		JLabel securitiesAccountBalance = new JLabel(
				"USD Balance: " + securitiesAccount.getWallet().getAmountOf("USD"));
		securitiesAccountBalance.setFont(new Font("Tahoma", Font.PLAIN, 11));
		securitiesAccountBalance.setBounds(426, 189, 206, 14);
		this.addComponent(securitiesAccountBalance);

		JLabel securitiesAccountProfit = new JLabel("Realized Profit: " + securitiesAccount.getRealizedProfit());
		securitiesAccountProfit.setFont(new Font("Tahoma", Font.PLAIN, 11));
		securitiesAccountProfit.setBounds(605, 189, 206, 14);
		this.addComponent(securitiesAccountProfit);

		JLabel securitiesAccountStocksTitle = new JLabel("Stocks");
		securitiesAccountStocksTitle.setFont(new Font("Tahoma", Font.PLAIN, 11));
		securitiesAccountStocksTitle.setBounds(426, 214, 206, 14);
		this.addComponent(securitiesAccountStocksTitle);

		JList<String> securitiesAccountStocks = new JList<String>();
		securitiesAccountStocks.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = processOwnedStocks();

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
		securitiesAccountStocks.setBounds(426, 239, 338, 198);

		ScrollPane securitiesAccountContainer = new ScrollPane();
		securitiesAccountContainer.add(securitiesAccountStocks);
		securitiesAccountContainer.setBounds(426, 239, 338, 198);
		this.addComponent(securitiesAccountContainer);

		JButton closeAccountBtn = new JButton("Close Client Account");
		closeAccountBtn.setBounds(10, 479, 200, 23);
		closeAccountBtn.setAction(actions.get(0));
		this.addComponent(closeAccountBtn);

		JButton backBtn = new JButton("Back");
		backBtn.setBounds(661, 513, 103, 23);
		backBtn.setAction(actions.get(1));
		this.addComponent(backBtn);
	}

	/* ============== */
	/* Helper Methods */
	/* ============== */

	/*
	 * Returns a formatted String Array to display all the currencies the Client has
	 * in their Checking Account.
	 */
	public String[] processCurrentCheckingBalance() {
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

	/*
	 * Returns a formatted String Array to display all the currencies the Client has
	 * in their Savings Account.
	 */
	public String[] processCurrentSavingsBalance() {
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

	/*
	 * Returns a formatted String Array to display all the Stocks the Client owns.
	 */
	public String[] processOwnedStocks() {
		ArrayList<String> scrapValues = new ArrayList<String>();
		for (String st : securitiesAccount.getStocks().getCurrentStocks().keySet()) {
			String s = "- " + st + " | Amount: " + securitiesAccount.getStocks().getAmount(st) + " | Current Value: "
					+ stockMarket.checkPrice(st) + " | Unrealized Profit: "
					+ securitiesAccount.getStocks().getUnrealizedProfit(st, stockMarket);
			scrapValues.add(s);
		}

		String[] formattedValues = new String[scrapValues.size()];
		for (int i = 0; i < scrapValues.size(); i++)
			formattedValues[i] = scrapValues.get(i);

		return formattedValues;
	}

}
