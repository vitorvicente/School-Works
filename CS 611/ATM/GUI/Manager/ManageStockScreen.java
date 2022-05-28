package GUI.Manager;

import java.awt.Font;
import java.awt.ScrollPane;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.JList;

import GUI.Abstraction.BankAction;
import GUI.Abstraction.BankFrame;
import Miscellaneous.Stock;
import Miscellaneous.StockMarket;

import javax.swing.JButton;

public class ManageStockScreen extends BankFrame {

	private static final long serialVersionUID = 1L;

	private JList<String> availableStocks;
	private StockMarket stockMarket;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public ManageStockScreen(StockMarket stockMarket) {
		super(100, 100, 536, 345);
		this.stockMarket = stockMarket;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method builds the actual Manage Stocks GUI Screen with all its
	 * requirements.
	 */
	@Override
	public void initialize(ArrayList<BankAction> actions, String errorMessage) {
		JLabel title = new JLabel("Manage Stocks");
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title.setBounds(10, 11, 414, 35);
		this.addComponent(title);

		JLabel availableStocksTitle = new JLabel("Currently Available Stocks");
		availableStocksTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		availableStocksTitle.setBounds(10, 57, 154, 14);
		this.addComponent(availableStocksTitle);

		availableStocks = new JList<String>();
		availableStocks.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = processAvailableStocks();

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
		availableStocks.setBounds(10, 82, 500, 144);

		ScrollPane availableStocksContainer = new ScrollPane();
		availableStocksContainer.add(availableStocks);
		availableStocksContainer.setBounds(10, 82, 500, 144);
		this.addComponent(availableStocksContainer);

		JButton editStockBtn = new JButton("Edit Stock Prices");
		editStockBtn.setBounds(10, 232, 154, 23);
		editStockBtn.setAction(actions.get(0));
		this.addComponent(editStockBtn);

		JButton removeStockBtn = new JButton("Remove Stock");
		removeStockBtn.setBounds(356, 232, 154, 23);
		removeStockBtn.setAction(actions.get(1));
		this.addComponent(removeStockBtn);

		JButton addStockBtn = new JButton("Add Stock");
		addStockBtn.setBounds(185, 232, 154, 23);
		addStockBtn.setAction(actions.get(2));
		this.addComponent(addStockBtn);

		JButton backBtn = new JButton("Back");
		backBtn.setBounds(386, 272, 124, 23);
		backBtn.setAction(actions.get(3));
		this.addComponent(backBtn);
	}

	/* ============== */
	/* Helper Methods */
	/* ============== */

	/*
	 * Returns a formatted String Array to display all the available stocks.
	 */
	public String[] processAvailableStocks() {
		ArrayList<String> scrapValues = new ArrayList<String>();
		for (Stock st : stockMarket.getRawAvailableStocks().keySet()) {
			String s = "- " + st.getCompanyName() + " | Price: " + stockMarket.checkPrice(st);
			scrapValues.add(s);
		}

		String[] formattedValues = new String[scrapValues.size()];
		for (int i = 0; i < scrapValues.size(); i++)
			formattedValues[i] = scrapValues.get(i);

		return formattedValues;
	}

}
