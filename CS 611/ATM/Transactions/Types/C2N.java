package Transactions.Types;

import Accounts.Account;
import Transactions.Transaction;
import Persistent.Database;

public class C2N extends Transaction {
	
	private static final long serialVersionUID = 14L;
	
	private long clientAccountId;
	
	/* =================== */
	/* Constructor Methods */
	/* =================== */
	
	public C2N(long id, int value, String currency,Account clientAccount) {
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
