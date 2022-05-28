package Miscellaneous;

import Persistent.Database;
import Persistent.Traceable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class StockWallet implements Traceable, Serializable {
	private static final long serialVersionUID = 31L;

	private final long id;

	private HashMap<String, ArrayList<Long>> currentStocks;
	private HashMap<String, ArrayList<Long>> soldStocks;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public StockWallet(long id) {
		this.id = id;
		this.setCurrentStocks(new HashMap<>());
		this.setSoldStocks(new HashMap<>());
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	@Override
	public long getId() {
		return id;
	}

	public int getAmount(Stock s) {
		return (this.getCurrentStocks().containsKey(s.getCompanyName()))
				? this.getCurrentStocks().get(s.getCompanyName()).size()
				: 0;
	}

	public int getAmount(String s) {
		return (this.getCurrentStocks().containsKey(s)) ? this.getCurrentStocks().get(s).size() : 0;
	}

	public int getSold(Stock s) {
		return this.getSoldStocks().get(s.getCompanyName()).size();
	}

	public boolean hasStock(Stock s) {
		return this.getCurrentStocks().containsKey(s.getCompanyName());
	}

	public void addStock(Stock s) {
		if (!currentStocks.containsKey(s.getCompanyName())) {
			currentStocks.put(s.getCompanyName(), new ArrayList<>());
		}
		this.currentStocks.get(s.getCompanyName()).add(s.getId());
	}

	public void removeStock(Stock s) {
		if (this.getAmount(s) <= 0)
			return;
		else
			this.currentStocks.get(s.getCompanyName()).remove(s.getId());

		if (this.currentStocks.get(s.getCompanyName()).size() == 0)
			this.currentStocks.remove(s.getCompanyName());
	}

	public HashMap<String, ArrayList<Stock>> getCurrentStocks() {
		HashMap<String, ArrayList<Stock>> map = new HashMap<>();
		for (Map.Entry<String, ArrayList<Long>> entry : currentStocks.entrySet()) {
			ArrayList<Stock> list = entry.getValue().stream().map(sid -> Database.getInstance().getStock(sid))
					.collect(Collectors.toCollection(ArrayList::new));
			map.put(entry.getKey(), list);
		}
		return map;
	}

	public void setCurrentStocks(HashMap<String, ArrayList<Stock>> input) {
		if (this.currentStocks == null) {
			this.currentStocks = new HashMap<>();
		} else {
			this.currentStocks.clear();
		}
		for (Map.Entry<String, ArrayList<Stock>> entry : input.entrySet()) {
			ArrayList<Long> list = entry.getValue().stream().map(Stock::getId)
					.collect(Collectors.toCollection(ArrayList::new));
			this.currentStocks.put(entry.getKey(), list);
		}
	}

	public HashMap<String, ArrayList<Stock>> getSoldStocks() {
		HashMap<String, ArrayList<Stock>> map = new HashMap<>();
		for (Map.Entry<String, ArrayList<Long>> entry : soldStocks.entrySet()) {
			ArrayList<Stock> list = entry.getValue().stream().map(sid -> Database.getInstance().getStock(sid))
					.collect(Collectors.toCollection(ArrayList::new));
			map.put(entry.getKey(), list);
		}
		return map;
	}

	public void setSoldStocks(HashMap<String, ArrayList<Stock>> input) {
		if (this.soldStocks == null) {
			this.soldStocks = new HashMap<>();
		} else {
			this.soldStocks.clear();
		}
		for (Map.Entry<String, ArrayList<Stock>> entry : input.entrySet()) {
			ArrayList<Long> list = entry.getValue().stream().map(Stock::getId)
					.collect(Collectors.toCollection(ArrayList::new));
			this.soldStocks.put(entry.getKey(), list);
		}
	}

	public int getUnrealizedProfit(String companyName, StockMarket sm) {
		int price = sm.checkPrice(companyName);

		int profit = 0;
		if (currentStocks.containsKey(companyName)) {
			for (Long sid : currentStocks.get(companyName)) {
				Stock s = Database.getInstance().getStock(sid);
				profit += price - s.getBuyingPrice();
			}
		}

		return profit;
	}
	
}
