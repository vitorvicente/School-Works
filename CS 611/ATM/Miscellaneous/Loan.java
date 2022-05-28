package Miscellaneous;

import Users.Client;
import Persistent.Database;
import Persistent.Traceable;

import java.io.Serializable;
import java.util.Date;

public class Loan implements Traceable, Serializable {

	private static final long serialVersionUID = 40L;

	private final long id;

	private String currency;
	private int value;
	private String collateral;

	private double interestRate;
	private Date lastInterestDate;

	private boolean accepted;
	private long clientId;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Loan(long id, Client client, String currency, int value, String collateral, double interestRate) {
		this.id = id;
		this.setClient(client);
		this.setCurrency(currency);
		this.setValue(value);
		this.setCollateral(collateral);
		this.setInterestRate(interestRate);
		this.setAccepted(false);
		this.setLastInterestDate(new Date());
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public void payLoan(int value) {
		this.value -= value;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getCollateral() {
		return collateral;
	}

	public void setCollateral(String collateral) {
		this.collateral = collateral;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	@Override
	public long getId() {
		return id;
	}

	@Deprecated
	public long getID() {
		return id;
	}

	public Client getClient() {
		return Database.getInstance().getClient(clientId);
	}

	public void setClient(Client client) {
		this.clientId = client.getId();
	}

	public Date getLastInterestDate() {
		return lastInterestDate;
	}

	public void setLastInterestDate(Date lastInterestDate) {
		this.lastInterestDate = lastInterestDate;
	}

}
