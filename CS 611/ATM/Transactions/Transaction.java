package Transactions;

import Persistent.Traceable;

import java.io.Serializable;

public abstract class Transaction implements Traceable, Serializable {

	private static final long serialVersionUID = 1L;

	private final long id;
	private int value;
	private String currency;
	private boolean seen;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Transaction(long id, int value, String currency) {
		this.id = id;
		this.setValue(value);
		this.setCurrency(currency);
		this.seen = false;
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public long getId() {
		return id;
	}

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}

}
