package Persistent;

import Accounts.Account;
import Accounts.Checking;
import Accounts.Saving;
import Accounts.Securities;
import Miscellaneous.Loan;
import Miscellaneous.Stock;
import Miscellaneous.StockMarket;
import Transactions.*;
import Transactions.Types.B2C;
import Transactions.Types.B2N;
import Transactions.Types.C2B;
import Transactions.Types.C2C;
import Transactions.Types.C2N;
import Transactions.Types.N2B;
import Transactions.Types.N2C;
import Users.Client;
import Users.Manager;

import java.io.*;
import java.util.ArrayList;

/**
 * A phony database, designed in singleton mode.
 *
 * @see Diskable
 * @see RecordCreatable,RecordFindable,RecordRemovable
 */
public class Database implements Diskable, RecordCreatable, RecordFindable, RecordRemovable {

	private Table<Account> accountTable;
	private Table<Transaction> transactionTable;
	private Table<Client> clientTable;
	private Table<Manager> managerTable;
	private Table<Stock> stockTable;
	private Table<Loan> loanTable;
	private StockMarket stockMarket;

	/* ================= */
	/* Singleton Methods */
	/* ================= */

	/*
	 * The following 3 methods allow for the obtaining of a DB object to grab data
	 * from and save data to.
	 */
	private static class SingletonHolder {
		private static final Database INSTANCE = new Database();
	}

	private Database() {
		loadDataFromDisk();
	}

	public static Database getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/* ================ */
	/* Diskable Methods */
	/* ================ */

	/*
	 * The following 3 methods allow the DB to fetch all the data from Serialized
	 * Files, and load them into the DB tables to be used by the application.
	 * 
	 * The first method is the global one, called to load the data, and the
	 * following two methods allow us to de-serialize a specific piece of data or a
	 * Table of some sort, thus allowing for Data Load.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void loadDataFromDisk() {
		// accountTable
		Table<? extends Traceable> tempAccountTable = deserializeTable(
				DiskCommon.getPath(DiskCommon.MyData.ACCOUNT_TABLE));
		accountTable = tempAccountTable == null ? new Table<>() : (Table<Account>) tempAccountTable;

		// transactionTable
		Table<? extends Traceable> tempXactTable = deserializeTable(
				DiskCommon.getPath(DiskCommon.MyData.TRANSACTION_TABLE));
		transactionTable = tempXactTable == null ? new Table<>() : (Table<Transaction>) tempXactTable;

		// clientTable
		Table<? extends Traceable> tempClientTable = deserializeTable(
				DiskCommon.getPath(DiskCommon.MyData.CLIENT_TABLE));
		clientTable = tempClientTable == null ? new Table<>() : (Table<Client>) tempClientTable;

		// managerTable
		Table<? extends Traceable> tempManagerTable = deserializeTable(
				DiskCommon.getPath(DiskCommon.MyData.MANAGER_TABLE));
		managerTable = tempManagerTable == null ? new Table<>() : (Table<Manager>) tempManagerTable;

		// stockTable
		Table<? extends Traceable> tempStockTable = deserializeTable(DiskCommon.getPath(DiskCommon.MyData.STOCK_TABLE));
		stockTable = tempStockTable == null ? new Table<>() : (Table<Stock>) tempStockTable;

		// loanTable
		Table<? extends Traceable> tempLoanTable = deserializeTable(DiskCommon.getPath(DiskCommon.MyData.LOAN_TABLE));
		loanTable = tempLoanTable == null ? new Table<>() : (Table<Loan>) tempLoanTable;

		// stockMarket
		StockMarket tempStockMarket = deserializeStockMarket(DiskCommon.getPath(DiskCommon.MyData.STOCK_MARKET_OBJECT));
		stockMarket = tempStockMarket == null ? new StockMarket(0) : tempStockMarket;
	}

	@SuppressWarnings("unchecked")
	private Table<? extends Traceable> deserializeTable(String path) {
		if (!DiskCommon.isFileExist(path))
			return null;
		Table<? extends Traceable> table = null;
		try (FileInputStream fis = new FileInputStream(path); ObjectInputStream ois = new ObjectInputStream(fis)) {
			table = (Table<? extends Traceable>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return table;
	}

	private StockMarket deserializeStockMarket(String path) {
		if (!DiskCommon.isFileExist(path))
			return null;
		StockMarket market = null;
		try (FileInputStream fis = new FileInputStream(path); ObjectInputStream ois = new ObjectInputStream(fis)) {
			market = (StockMarket) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return market;
	}

	/*
	 * The following 2 methods allow us to flush all the data in the DB onto a
	 * serialized file for that specific Table or Object, thus allowing us to
	 * actually save the data.
	 */
	@Override
	public void flushDataOntoDisk() {
		serialize(accountTable, DiskCommon.getPath(DiskCommon.MyData.ACCOUNT_TABLE));
		serialize(transactionTable, DiskCommon.getPath(DiskCommon.MyData.TRANSACTION_TABLE));
		serialize(clientTable, DiskCommon.getPath(DiskCommon.MyData.CLIENT_TABLE));
		serialize(managerTable, DiskCommon.getPath(DiskCommon.MyData.MANAGER_TABLE));
		serialize(stockTable, DiskCommon.getPath(DiskCommon.MyData.STOCK_TABLE));
		serialize(loanTable, DiskCommon.getPath(DiskCommon.MyData.LOAN_TABLE));
		serialize(stockMarket, DiskCommon.getPath(DiskCommon.MyData.STOCK_MARKET_OBJECT));
	}

	private void serialize(Object obj, String path) {
		try (FileOutputStream fos = new FileOutputStream(path); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* ======================= */
	/* RecordCreatable Methods */
	/* ======================= */

	/*
	 * The following methods fall under the RecordCreatable Interface, and allow us
	 * to create all the different kinds of Objects that exist in the DB and the
	 * application, from Users to Transactions, all with the needed data.
	 */
	@Override
	public Checking createAccountChecking() {
		long id = accountTable.getNextId();
		return createAccountChecking(id);
	}

	public Checking createAccountChecking(long id) {
		Checking account = new Checking(id);
		accountTable.add(account);
		return account;
	}

	@Override
	public Saving createAccountSaving() {
		long id = accountTable.getNextId();
		Saving account = new Saving(id);
		accountTable.add(account);
		return account;
	}

	@Override
	public Securities createAccountSecurities() {
		long id = accountTable.getNextId();
		Securities account = new Securities(id);
		accountTable.add(account);
		return account;
	}

	@Override
	public B2C createTransactionB2C(int value, String currency, Account bankAccount, Account clientAccount) {
		long id = transactionTable.getNextId();
		B2C xact = new B2C(id, value, currency, bankAccount, clientAccount);
		transactionTable.add(xact);
		return xact;
	}

	@Override
	public B2N createTransactionB2N(int value, String currency, Account bankAccount) {
		long id = transactionTable.getNextId();
		B2N xact = new B2N(id, value, currency, bankAccount);
		transactionTable.add(xact);
		return xact;
	}

	@Override
	public C2B createTransactionC2B(int value, String currency, Account bankAccount, Account clientAccount) {
		long id = transactionTable.getNextId();
		C2B xact = new C2B(id, value, currency, bankAccount, clientAccount);
		transactionTable.add(xact);
		return xact;
	}

	@Override
	public C2C createTransactionC2C(int value, String currency, Account clientAccountOne, Account clientAccountTwo) {
		long id = transactionTable.getNextId();
		C2C xact = new C2C(id, value, currency, clientAccountOne, clientAccountTwo);
		transactionTable.add(xact);
		return xact;
	}

	@Override
	public C2N createTransactionC2N(int value, String currency, Account clientAccount) {
		long id = transactionTable.getNextId();
		C2N xact = new C2N(id, value, currency, clientAccount);
		transactionTable.add(xact);
		return xact;
	}

	@Override
	public N2B createTransactionN2B(int value, String currency, Account bankAccount) {
		long id = transactionTable.getNextId();
		N2B xact = new N2B(id, value, currency, bankAccount);
		transactionTable.add(xact);
		return xact;
	}

	@Override
	public N2C createTransactionN2C(int value, String currency, Account clientAccount) {
		long id = transactionTable.getNextId();
		N2C xact = new N2C(id, value, currency, clientAccount);
		transactionTable.add(xact);
		return xact;
	}

	@Override
	public Client createClient(String username, String password, String fullName, long ssn, String phoneNumber,
			String address) {
		long id = clientTable.getNextId();
		Client client = new Client(id, username, password, fullName, ssn, phoneNumber, address);
		clientTable.add(client);
		return client;
	}

	@Override
	public Client createClient(String username, String password, String fullName, long ssn, String phoneNumber,
			String address, long checkingAccount, long savingsAccount, long securitiesAccount) {
		long id = clientTable.getNextId();
		Client client = new Client(id, username, password, fullName, ssn, phoneNumber, address, checkingAccount,
				savingsAccount, securitiesAccount);
		clientTable.add(client);
		return client;
	}

	@Override
	public Manager createManager(String username, String password, String fullName) {
		long id = managerTable.getNextId();
		return createManager(id, username, password, fullName);
	}

	public Manager createManager(long id, String username, String password, String fullName) {
		Manager manager = new Manager(id, username, password, fullName);
		managerTable.add(manager);
		return manager;
	}

	@Override
	public Stock createStock(String companyName) {
		long id = stockTable.getNextId();
		Stock stock = new Stock(id, companyName);
		stockTable.add(stock);
		return stock;
	}

	@Override
	public Loan createLoan(Client client, String currency, int value, String collateral, double interestRate) {
		long id = loanTable.getNextId();
		Loan loan = new Loan(id, client, currency, value, collateral, interestRate);
		loanTable.add(loan);
		return loan;
	}

	/* ====================== */
	/* RecordFindable Methods */
	/* ====================== */

	/*
	 * All the following methods allow us to search the DB for a specific Object
	 * based on its ID, thus allowing us to access that specific object in the
	 * application.
	 */
	public Account getAccount(long id) {
		return accountTable.get(id);
	}

	@Override
	public Checking getAccountChecking(long id) {
		Account account = accountTable.get(id);
		if (account == null)
			return null;
		if (!(account instanceof Checking))
			return null;
		return (Checking) account;
	}

	@Override
	public Saving getAccountSaving(long id) {
		Account account = accountTable.get(id);
		if (account == null)
			return null;
		if (!(account instanceof Saving))
			return null;
		return (Saving) account;
	}

	@Override
	public Securities getAccountSecurities(long id) {
		Account account = accountTable.get(id);
		if (account == null)
			return null;
		if (!(account instanceof Securities))
			return null;
		return (Securities) account;
	}

	public Transaction getTransaction(long id) {
		return transactionTable.get(id);
	}

	@Override
	public B2C getTransactionB2C(long id) {
		Transaction xact = transactionTable.get(id);
		if (xact == null)
			return null;
		if (!(xact instanceof B2C))
			return null;
		return (B2C) xact;
	}

	@Override
	public B2N getTransactionB2N(long id) {
		Transaction xact = transactionTable.get(id);
		if (xact == null)
			return null;
		if (!(xact instanceof B2N))
			return null;
		return (B2N) xact;
	}

	@Override
	public C2B getTransactionC2B(long id) {
		Transaction xact = transactionTable.get(id);
		if (xact == null)
			return null;
		if (!(xact instanceof C2B))
			return null;
		return (C2B) xact;
	}

	@Override
	public C2C getTransactionC2C(long id) {
		Transaction xact = transactionTable.get(id);
		if (xact == null)
			return null;
		if (!(xact instanceof C2C))
			return null;
		return (C2C) xact;
	}

	@Override
	public C2N getTransactionC2N(long id) {
		Transaction xact = transactionTable.get(id);
		if (xact == null)
			return null;
		if (!(xact instanceof C2N))
			return null;
		return (C2N) xact;
	}

	@Override
	public N2B getTransactionN2B(long id) {
		Transaction xact = transactionTable.get(id);
		if (xact == null)
			return null;
		if (!(xact instanceof N2B))
			return null;
		return (N2B) xact;
	}

	@Override
	public N2C getTransactionN2C(long id) {
		Transaction xact = transactionTable.get(id);
		if (xact == null)
			return null;
		if (!(xact instanceof N2C))
			return null;
		return (N2C) xact;
	}

	@Override
	public Client getClient(long id) {
		return clientTable.get(id);
	}

	@Override
	public Manager getManager(long id) {
		return managerTable.get(id);
	}

	@Override
	public Stock getStock(long id) {
		return stockTable.get(id);
	}

	@Override
	public Loan getLoan(long id) {
		return loanTable.get(id);
	}

	public StockMarket getStockMarket() {
		return stockMarket;
	}

	public ArrayList<Account> getAllAccounts() {
		return accountTable.getAll();
	}

	public ArrayList<Client> getAllClients() {
		return clientTable.getAll();
	}

	public ArrayList<Loan> getAllLoans() {
		return loanTable.getAll();
	}

	/* ======================= */
	/* RecordRemovable Methods */
	/* ======================= */

	/*
	 * All the following methods allow us to remove a specific Object from the DB
	 * based on its ID.
	 */
	public boolean removeAccount(long id) {
		return accountTable.remove(id);
	}

	@Override
	public boolean removeAccountChecking(long id) {
		return removeAccount(id);
	}

	@Override
	public boolean removeAccountSaving(long id) {
		return removeAccount(id);
	}

	@Override
	public boolean removeAccountSecurities(long id) {
		return removeAccount(id);
	}

	public boolean removeTransaction(long id) {
		return transactionTable.remove(id);
	}

	@Override
	public boolean removeTransactionB2C(long id) {
		return removeTransaction(id);
	}

	@Override
	public boolean removeTransactionB2N(long id) {
		return removeTransaction(id);
	}

	@Override
	public boolean removeTransactionC2B(long id) {
		return removeTransaction(id);
	}

	@Override
	public boolean removeTransactionC2C(long id) {
		return removeTransaction(id);
	}

	@Override
	public boolean removeTransactionC2N(long id) {
		return removeTransaction(id);
	}

	@Override
	public boolean removeTransactionN2B(long id) {
		return removeTransaction(id);
	}

	@Override
	public boolean removeTransactionN2C(long id) {
		return removeTransaction(id);
	}

	@Override
	public boolean removeClient(long id) {
		return clientTable.remove(id);
	}

	@Override
	public boolean removeManager(long id) {
		return managerTable.remove(id);
	}

	@Override
	public boolean removeStock(long id) {
		return stockTable.remove(id);
	}

	@Override
	public boolean removeLoan(long id) {
		return loanTable.remove(id);
	}
	
}
