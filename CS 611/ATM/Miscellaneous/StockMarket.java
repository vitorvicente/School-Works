package Miscellaneous;

import Persistent.Database;
import Persistent.Traceable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class StockMarket implements Traceable, Serializable {

	private static final long serialVersionUID = 50L;

	private final long id;
	private HashMap<Long, Integer> availableStocks;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public StockMarket(long id) {
		this(id, new HashMap<>());
	}

	public StockMarket(long id, HashMap<Stock, Integer> stocks) {
		this.id = id;
		this.setRawAvailableStocks(stocks);
	}
	
	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public int checkPrice(Stock s) {
		return this.availableStocks.getOrDefault(s.getId(), 0);
	}

	public int checkPrice(String companyName) {
		for (Map.Entry<Stock, Integer> entry : getRawAvailableStocks().entrySet()) {
			if (companyName.equals(entry.getKey().getCompanyName())) {
				return entry.getValue();
			}
		}
		return 0;
	}

	public void updatePrice(Stock s, int price) {
		if (!this.availableStocks.containsKey(s.getId())) {
			System.out.println("Stock not on the market!");
			return;
		}
		this.availableStocks.put(s.getId(), price);
	}

	public void addStock(Stock s, int price) {
		this.availableStocks.put(s.getId(), price);
	}

	public void removeStock(Stock s) {
		if (!this.availableStocks.containsKey(s.getId())) {
			System.out.println("Stock not on the market!");
			return;
		}
		this.availableStocks.remove(s.getId());
	}

	public HashMap<Stock, Integer> getRawAvailableStocks() {
		HashMap<Stock, Integer> map = new HashMap<>();
		Database database = Database.getInstance();
		for (Map.Entry<Long, Integer> entry : availableStocks.entrySet()) {
			Stock stock = database.getStock(entry.getKey());
			if (stock == null)
				continue;
			map.put(stock, entry.getValue());
		}
		return map;
	}

	public void setRawAvailableStocks(HashMap<Stock, Integer> availableStocks) {
		this.availableStocks = new HashMap<>();
		for (Map.Entry<Stock, Integer> entry : availableStocks.entrySet()) {
			this.availableStocks.put(entry.getKey().getId(), entry.getValue());
		}
	}

	@Override
	public long getId() {
		return id;
	}
	
}
