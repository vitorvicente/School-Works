package Transactions.Types;

import Accounts.Account;
import Transactions.Transaction;
import Persistent.Database;

public class N2B extends Transaction {
	
	private static final long serialVersionUID = 15L;
	
	private long bankAccountId;
	
	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public N2B(long id, int value, String currency, Account bankAccount) {
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
