package Transactions.Types;

import Accounts.Account;
import Transactions.Transaction;
import Persistent.Database;

public class C2C extends Transaction {
	
	private static final long serialVersionUID = 13L;
	
	private long clientAccountOneId;
	private long clientAccountTwoId;
	
	/* =================== */
	/* Constructor Methods */
	/* =================== */
	
	public C2C(long id, int value, String currency, Account clientAccountOne, Account clientAccountTwo) {
		super(id, value, currency);
		this.setClientAccountOne(clientAccountOne);
		this.setClientAccountTwo(clientAccountTwo);
	}
	
	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public Account getClientAccountOne() {
		return Database.getInstance().getAccount(clientAccountOneId);
	}

	private void setClientAccountOne(Account clientAccountOne) {
		this.clientAccountOneId = clientAccountOne.getID();
	}

	public Account getClientAccountTwo() {
		return Database.getInstance().getAccount(clientAccountTwoId);
	}

	private void setClientAccountTwo(Account clientAccountTwo) {
		this.clientAccountTwoId = clientAccountTwo.getID();
	}
	
}
