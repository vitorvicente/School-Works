package Users;

import Transactions.Transaction;
import Persistent.Database;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Manager extends User {

	private static final long serialVersionUID = 21L;

	private HashMap<Date, ArrayList<Long>> transactions;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Manager(long uid, String username, String password, String fullName) {
		super(uid, username, password, fullName);
		this.setTransactions(new HashMap<>());
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

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

}
