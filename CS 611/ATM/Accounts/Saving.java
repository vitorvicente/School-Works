package Accounts;

import Currency.Currency;
import Miscellaneous.CurrencyWallet;

import java.util.Date;

public class Saving extends Account {

	private static final long serialVersionUID = 1L;

	private CurrencyWallet wallet;
	private long ID;
	private double interestRate;
	private Date lastPayDate;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Saving(long id) {
		this.wallet = new CurrencyWallet(-1);
		this.setID(id);
		this.setInterestRate(0.05);
		this.setLastPayDate(new Date());
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

	/*
	 * Pays interest in every currency the customer has.
	 */
	public void payInterest() {
		this.setLastPayDate(new Date());

		for (String currency : this.getWallet().getValues().keySet()) {
			int amountToDeposit = (int) Math.round(this.getWallet().getAmountOf(currency) * this.getInterestRate());
			this.getWallet().depositValue(currency, amountToDeposit);
		}
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

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

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public Date getLastPayDate() {
		return lastPayDate;
	}

	public void setLastPayDate(Date lastPayDate) {
		this.lastPayDate = lastPayDate;
	}

}
