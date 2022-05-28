package Transactions.Types;

import Accounts.Account;
import Transactions.Transaction;
import Persistent.Database;

public class B2C extends Transaction {
	
	private static final long serialVersionUID = 10L;
	
	private long bankAccountId;
	private long clientAccountId;

	/* =================== */
	/* Constructor Methods */
	/* =================== */
	
	public B2C(long id, int value, String currency, Account bankAccount, Account clientAccount) {
		super(id, value, currency);
		this.setBankAccount(bankAccount);
		this.setClientAccount(clientAccount);
	}
	
	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public Account getBankAccount() {
		return Database.getInstance().getAccount(bankAccountId);
	}

	private void setBankAccount(Account bankAccount) {
		this.bankAccountId = bankAccount.getId();
	}

	public Account getClientAccount() {
		return Database.getInstance().getAccount(clientAccountId);
	}

	private void setClientAccount(Account clientAccount) {
		this.clientAccountId = clientAccount.getId();
	}

}
