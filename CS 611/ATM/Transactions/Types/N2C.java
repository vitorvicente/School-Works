package Transactions.Types;

import Accounts.Account;
import Transactions.Transaction;
import Persistent.Database;

public class N2C extends Transaction {
	
	private static final long serialVersionUID = 16L;
	
	private long clientAccountId;
	
	/* =================== */
	/* Constructor Methods */
	/* =================== */
	
	public N2C(long id, int value, String currency, Account clientAccount) {
		super(id, value, currency);
		this.setClientAccount(clientAccount);
	}
	
	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public Account getClientAccount() {
		return Database.getInstance().getAccount(clientAccountId);
	}

	private void setClientAccount(Account clientAccount) {
		this.clientAccountId = clientAccount.getId();
	}

}
