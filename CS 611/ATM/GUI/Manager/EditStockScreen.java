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

import javax.swing.JTextField;
import javax.swing.JButton;

public class EditStockScreen extends BankFrame {

	private static final long serialVersionUID = 1L;

	private JList<String> availableStocks;
	private JTextField priceField;
	private StockMarket stockMarket;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public EditStockScreen(StockMarket stockMarket) {
		super(100, 100, 536, 409);
		this.stockMarket = stockMarket;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method builds the actual Edit Stock GUI Screen with all its
	 * requirements.
	 */
	@Override
	public void initialize(ArrayList<BankAction> actions, String errorMessage) {
		JLabel title = new JLabel("Edit Stock");
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

		JLabel priceTitle = new JLabel("New Price");
		priceTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		priceTitle.setBounds(10, 243, 180, 14);
		this.addComponent(priceTitle);

		priceField = new JTextField();
		priceField.setBounds(10, 268, 180, 20);
		this.addComponent(priceField);
		priceField.setColumns(10);

		JLabel errorFieldName = new JLabel("" + errorMessage);
		errorFieldName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		errorFieldName.setBounds(10, 298, 500, 14);
		this.addComponent(errorFieldName);

		JButton saveBtn = new JButton("Save");
		saveBtn.setBounds(10, 323, 109, 23);
		saveBtn.setAction(actions.get(0));
		this.addComponent(saveBtn);

		JButton backBtn = new JButton("Back");
		backBtn.setBounds(420, 336, 90, 23);
		backBtn.setAction(actions.get(1));
		this.addComponent(backBtn);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public String getSelectedStock() {
		return this.availableStocks.getSelectedValue();
	}

	public String getPrice() {
		return this.priceField.getText();
	}

	/* ============== */
	/* Helper Methods */
	/* ============== */

	/*
	 * Returns a formatted String Array to display all available Stocks.
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
