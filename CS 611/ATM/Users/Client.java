package Users;

import Transactions.Transaction;
import Persistent.Database;

import java.util.*;
import java.util.stream.Collectors;

public class Client extends User {

	private static final long serialVersionUID = 20L;

	private long ssn;
	private String phoneNumber;
	private String address;
	private long checkingAccount;
	private long savingsAccount;
	private long securitiesAccount;

	private HashMap<Date, ArrayList<Long>> transactions;
	private ArrayList<Long> loans;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Client(long uid, String username, String password, String fullName, long ssn, String phoneNumber,
			String address) {
		super(uid, username, password, fullName);
		this.ssn = ssn;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.setLoans(new ArrayList<>());
		this.setTransactions(new HashMap<>());
	}

	public Client(long uid, String username, String password, String fullName, long ssn, String phoneNumber,
			String address, long checkingAccount, long savingsAccount, long securitiesAccount) {
		this(uid, username, password, fullName, ssn, phoneNumber, address);
		this.securitiesAccount = securitiesAccount;
		this.savingsAccount = savingsAccount;
		this.checkingAccount = checkingAccount;
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public List<Transaction> listTransactions(Date d) {
		return getTransactions().get(d);
	}

	public long getSsn() {
		return this.ssn;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public String getAddress() {
		return this.address;
	}

	public Client setSsn(long ssn) {
		this.ssn = ssn;
		return this;
	}

	public Client setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public Client setAddress(String address) {
		this.address = address;
		return this;
	}

	public HashMap<Date, ArrayList<Transaction>> getTransactions() {
		HashMap<Date, ArrayList<Transaction>> map = new HashMap<>();
		for (Map.Entry<Date, ArrayList<Long>> entry : transactions.entrySet()) {
			ArrayList<Transaction> list = entry.getValue().stream()
					.map(tid -> Database.getInstance().getTransaction(tid))
					.collect(Collectors.toCollection(ArrayList::new));
			map.put(entry.getKey(), list);
		}
		return map;
	}

	public void setTransactions(HashMap<Date, ArrayList<Transaction>> input) {
		if (this.transactions == null) {
			this.transactions = new HashMap<>();
		} else {
			this.transactions.clear();
		}
		for (Map.Entry<Date, ArrayList<Transaction>> entry : input.entrySet()) {
			ArrayList<Long> list = entry.getValue().stream().map(Transaction::getId)
					.collect(Collectors.toCollection(ArrayList::new));
			this.transactions.put(entry.getKey(), list);
		}
	}

	public void addTransaction(Date date, Transaction transaction) {
		ArrayList<Long> ts = this.transactions.getOrDefault(date, new ArrayList<>());
		ts.add(transaction.getId());
		this.transactions.put(date, ts);
	}

	public ArrayList<Long> getLoans() {
		return loans;
	}

	public void setLoans(ArrayList<Long> loans) {
		this.loans = loans;
	}

	public long getCheckingAccount() {
		return checkingAccount;
	}

	public void setCheckingAccount(long checkingAccount) {
		this.checkingAccount = checkingAccount;
	}

	public long getSavingsAccount() {
		return savingsAccount;
	}

	public void setSavingsAccount(long savingsAccount) {
		this.savingsAccount = savingsAccount;
	}

	public long getSecuritiesAccount() {
		return securitiesAccount;
	}

	public void setSecuritiesAccount(long securitiesAccount) {
		this.securitiesAccount = securitiesAccount;
	}
}
