package Currency;

import java.util.HashMap;

public class Currency {

	private HashMap<String, Double> conversions;
	private String name;
	private String symbol;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Currency(String name, double usdConversion, String symbol) {
		this.setName(name);
		this.setDefaultConversations(usdConversion);
		this.setSymbol(symbol);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public HashMap<String, Double> getConversions() {
		return conversions;
	}

	public void setConversions(HashMap<String, Double> conversions) {
		this.conversions = conversions;
	}

	public void addConversion(String symbol, double multiplier) {
		this.conversions.put(symbol, multiplier);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/* ============== */
	/* Helper Methods */
	/* ============== */

	/*
	 * This method helps us define the currency conversions for the default
	 * currencies based on their value against the USD.
	 */
	public void setDefaultConversations(double usdConversion) {
		double[] rates = new double[] { usdConversion * 3.67, usdConversion * 1.00, usdConversion * 0.83,
				usdConversion * 0.91, usdConversion * 8.29, usdConversion * 19.86, usdConversion * 0.72,
				usdConversion * 74.94, usdConversion * 1111.66, usdConversion * 108.14, usdConversion * 6.49 };

		this.conversions = new HashMap<String, Double>();

		this.addConversion("AED", rates[0]);
		this.addConversion("USD", rates[1]);
		this.addConversion("EUR", rates[2]);
		this.addConversion("CHF", rates[3]);
		this.addConversion("TRY", rates[4]);
		this.addConversion("MXN", rates[5]);
		this.addConversion("GPB", rates[6]);
		this.addConversion("RUB", rates[7]);
		this.addConversion("KRW", rates[8]);
		this.addConversion("JPY", rates[9]);
		this.addConversion("CNY", rates[10]);
	}

	public boolean equals(Currency c) {
		return this.getSymbol().equals(c.getSymbol());
	}

}