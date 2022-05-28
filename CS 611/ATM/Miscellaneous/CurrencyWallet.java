package Miscellaneous;

import Persistent.Traceable;

import java.io.Serializable;
import java.util.HashMap;

public class CurrencyWallet implements Traceable, Serializable {

	private static final long serialVersionUID = 30L;

	private HashMap<String, Integer> values;
	private final long id;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public CurrencyWallet(long id) {
		this.setValues(new HashMap<String, Integer>());
		this.id = id;
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	@Override
	public long getId() {
		return id;
	}

	public int getAmountOf(String currency) {
		return this.getValues().containsKey(currency) ? this.getValues().get(currency) : 0;
	}

	public boolean hasCurrency(String currency) {
		return this.getValues().containsKey(currency);
	}

	public HashMap<String, Integer> getValues() {
		return values;
	}

	public void setValues(HashMap<String, Integer> values) {
		this.values = values;
	}

	public void depositValue(String currency, int value) {
		this.values.put(currency, value + this.getAmountOf(currency));
	}

	public void withdrawValue(String currency, int value) {
		if (this.getAmountOf(currency) < value) {
			System.out.println("You do not have enough money!");
			return;
		}
		this.values.put(currency, this.values.get(currency) - value);
	}
	
}
