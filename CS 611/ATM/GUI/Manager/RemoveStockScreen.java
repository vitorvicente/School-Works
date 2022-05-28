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

public class RemoveStockScreen extends BankFrame {

	private static final long serialVersionUID = 1L;

	private JList<String> availableStocks;
	private StockMarket stockMarket;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public RemoveStockScreen(StockMarket stockMarket) {
		super(100, 100, 536, 356);
		this.stockMarket = stockMarket;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method builds the actual Remove Stocks GUI Screen with all its
	 * requirements.
	 */
	@Override
	public void initialize(ArrayList<BankAction> actions, String errorMessage) {
		JLabel title = new JLabel("Remove Stock");
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

		JLabel errorMessageField = new JLabel("" + errorMessage);
		errorMessageField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		errorMessageField.setBounds(10, 232, 500, 14);
		this.addComponent(errorMessageField);

		JButton removeStockBtn = new JButton("Remove Stock");
		removeStockBtn.setBounds(10, 259, 168, 23);
		removeStockBtn.setAction(actions.get(0));
		this.addComponent(removeStockBtn);

		JButton backBtn = new JButton("Back");
		backBtn.setBounds(399, 283, 111, 23);
		backBtn.setAction(actions.get(1));
		this.addComponent(backBtn);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public String getSelectedStock() {
		return this.availableStocks.getSelectedValue();
	}

	/* ============== */
	/* Helper Methods */
	/* ============== */

	/*
	 * Returns a formatted String Array to display all the available stock.
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
