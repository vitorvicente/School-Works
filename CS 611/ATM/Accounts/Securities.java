package Accounts;

import Miscellaneous.CurrencyWallet;
import Miscellaneous.Stock;
import Miscellaneous.StockWallet;

public class Securities extends Account {

	private static final long serialVersionUID = 2L;

	private CurrencyWallet wallet;
	private StockWallet stock;
	private long ID;
	private int realizedProfit;
	private boolean blocked;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Securities(long id) {
		this.wallet = new CurrencyWallet(-1);
		this.stock = new StockWallet(-1);
		this.setID(id);
		this.setBlocked(true);
	}

	/* =============== */
	/* Account Methods */
	/* =============== */

	/*
	 * In the case of Securities accounts, the only available currency is the US
	 * Dollar, hence no other currencies are accepted.
	 */
	@Override
	public void depositFunds(String currency, int amount) {
		if (currency.equals("USD"))
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

	public CurrencyWallet getWallet() {
		return this.wallet;
	}

	public StockWallet getStocks() {
		return this.stock;
	}

	public void addStock(Stock s) {
		stock.addStock(s);
	}

	public void removeStock(Stock s) {
		stock.removeStock(s);
	}

	@Override
	public long getID() {
		return ID;
	}

	@Override
	public void setID(long id) {
		this.ID = id;
	}

	public int getRealizedProfit() {
		return realizedProfit;
	}

	public void setRealizedProfit(int realizedProfit) {
		this.realizedProfit = realizedProfit;
	}

	public void addRealizedProfit(int toAdd) {
		this.realizedProfit += toAdd;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

}
