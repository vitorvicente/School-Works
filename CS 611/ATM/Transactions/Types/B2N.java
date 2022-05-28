package Transactions.Types;

import Accounts.Account;
import Transactions.Transaction;
import Persistent.Database;

public class B2N extends Transaction {
	
	private static final long serialVersionUID = 11L;
	
	private long bankAccountId;

	/* =================== */
	/* Constructor Methods */
	/* =================== */
	
	public B2N(long id, int value, String currency, Account bankAccount) {
		super(id, value, currency);
		this.setBankAccount(bankAccount);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */
	
	public Account getBankAccount() {
		return Database.getInstance().getAccount(bankAccountId);
	}

	private void setBankAccount(Account clientAccount) {
		this.bankAccountId = clientAccount.getId();
	}
}
