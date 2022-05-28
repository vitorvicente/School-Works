package Bank;

import Accounts.Account;
import Accounts.Checking;
import Currency.Currency;
import Currency.Types.*;
import GUI.BankGUI;
import Miscellaneous.Loan;
import Miscellaneous.StockMarket;
import Users.Client;
import Users.Manager;
import Persistent.Database;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Bank {

	/*
	 * Static list of all available currencies to be used at the bank.
	 */
	public static final ArrayList<Currency> AVAILABLE_CURRENCIES = new ArrayList<Currency>(
			Arrays.asList(new Currency[] { new Dollar(), new Yuan(), new Yen(), new Won(), new Ruble(), new Pound(),
					new Lira(), new Franc(), new Euro(), new Dirham(), new Peso() }));

	/*
	 * Configurable information regarding the functionality of the bank, the %% fees
	 * it takes, the minimum amount for a customer to get a Securities account, and
	 * the minimum amount they must have in their Savings to actually use their
	 * Securities account.
	 */
	public static final double TRANSACTION_FEE = 0.05;
	public static final int MINIMUM_TO_GET_SECURITIES = 5000;
	public static final int MINIMUM_IN_SAVINGS = 2500;

	/*
	 * Basic information for the Bank Manager, this will be true at the very start,
	 * and can be changed by the Manager in the GUI.
	 */
	private static final long MANAGER_ID = 0;
	private static final String MANAGER_USERNAME = "a";
	private static final String MANAGER_PASSWORD = "a";
	private static final String MANAGER_FULL_NAME = "Mr. Bank Manager";
	private static final long GLOBAL_ACCOUNT_ID = 100000;

	private Manager manager;
	private Checking globalAccount;

	private ArrayList<Account> accounts;
	private ArrayList<Client> clients;
	private ArrayList<Loan> loans;

	private StockMarket stockMarket;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Bank() {
		this.loadData();
	}

	/*
	 * This method will load everything from the DB object instance, thus making it
	 * so the data is not lost between uses of the GUI.
	 */
	public void loadData() {
		Database db = Database.getInstance();
		db.loadDataFromDisk();

		manager = db.getManager(MANAGER_ID);
		if (manager == null)
			manager = db.createManager(MANAGER_ID, MANAGER_USERNAME, MANAGER_PASSWORD, MANAGER_FULL_NAME);

		globalAccount = db.getAccountChecking(GLOBAL_ACCOUNT_ID);
		if (globalAccount == null)
			globalAccount = db.createAccountChecking(GLOBAL_ACCOUNT_ID);

		accounts = db.getAllAccounts();
		clients = db.getAllClients();
		loans = db.getAllLoans();

		stockMarket = db.getStockMarket();
	}

	/*
	 * This method is the entry method into the GUI.
	 */
	public void launchGUI() {
		Bank b = this;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BankGUI window = new BankGUI(b);
					window.toggleVisible();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}

	public ArrayList<Client> getClients() {
		return clients;
	}

	public void setClients(ArrayList<Client> clients) {
		this.clients = clients;
	}

	public StockMarket getStockMarket() {
		return this.stockMarket;
	}

	public void setStockMarket(StockMarket stockMarket) {
		this.stockMarket = stockMarket;
	}

	public Checking getGlobalAccount() {
		return globalAccount;
	}

	public void setGlobalAccount(Checking globalAccount) {
		this.globalAccount = globalAccount;
	}

	public ArrayList<Loan> getLoans() {
		return loans;
	}

	public void setLoans(ArrayList<Loan> loans) {
		this.loans = loans;
	}

	public Loan requestLoan(Client client, String cur, int value, String collat, double interestRate) {
		Loan newLoan = Database.getInstance().createLoan(client, cur, value, collat, interestRate);
		client.getLoans().add(newLoan.getId());
		this.loans.add(newLoan);
		return newLoan;
	}
}
