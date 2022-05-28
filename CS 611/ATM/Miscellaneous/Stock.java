package Miscellaneous;

import Persistent.Traceable;

import java.io.Serializable;

public class Stock implements Traceable, Serializable {

	private static final long serialVersionUID = 60L;

	private final long id;
	private String companyName;
	private int buyingPrice;
	private int sellingPrice;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Stock(long id, String companyName) {
		this.id = id;
		this.setCompanyName(companyName);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	@Override
	public long getId() {
		return id;
	}

	public int getBuyingPrice() {
		return buyingPrice;
	}

	public void setBuyingPrice(int buyingPrice) {
		this.buyingPrice = buyingPrice;
	}

	public int getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(int sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/* ============== */
	/* Helper Methods */
	/* ============== */

	public boolean equals(Stock otherStock) {
		return this.getCompanyName().equals(otherStock.getCompanyName());
	}

}
