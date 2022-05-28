package Accounts;

import Persistent.Traceable;

import java.io.Serializable;

import Miscellaneous.CurrencyWallet;

public abstract class Account implements Traceable, Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Translating the Traceable method to the respective method in the Accounts
	 * objects.
	 */
	@Override
	public long getId() {
		return getID();
	}

	/* ================ */
	/* Abstract Methods */
	/* ================ */

	public abstract long getID();

	public abstract void setID(long id);

	public abstract void depositFunds(String currency, int amount);

	public abstract void withdrawFunds(String currency, int amount);

	public abstract boolean hasFunds(String currency, int amount);

	public abstract CurrencyWallet getWallet();

}
