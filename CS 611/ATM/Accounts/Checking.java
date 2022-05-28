package Accounts;

import Currency.Currency;
import Miscellaneous.CurrencyWallet;

public class Checking extends Account {

	private static final long serialVersionUID = 0L;

	private CurrencyWallet wallet;
	private long ID;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Checking(long id) {
		this.wallet = new CurrencyWallet(-1);
		this.setID(id);
	}

	/* =============== */
	/* Account Methods */
	/* =============== */

	/*
	 * Checks whether the currency provided is valid, if so it deposits that amount
	 * on the account.
	 */
	@Override
	public void depositFunds(String currency, int amount) {
		boolean validCurrency = false;

		for (Currency curr : Bank.Bank.AVAILABLE_CURRENCIES) {
			if (curr.getSymbol().equals(currency)) {
				validCurrency = true;
				break;
			}
		}

		if (validCurrency)
			this.wallet.depositValue(currency, amount);
	}

	@Override
	public void withdrawFunds(String currency, int amount) {
		if (this.wallet.hasCurrency(currency))
			this.wallet.withdrawValue(currency, amount);
	}

	@Override
	public boolean hasFunds(String currency, int amount) {
		return this.wallet.getAmountOf(currency) >= amount;
	}
	
	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */	

	@Override
	public CurrencyWallet getWallet() {
		return this.wallet;
	}

	@Override
	public long getID() {
		return ID;
	}

	@Override
	public void setID(long id) {
		this.ID = id;
	}

}
