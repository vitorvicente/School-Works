package GUI.Client;

import Accounts.Securities;
import GUI.Abstraction.BankAction;
import GUI.Abstraction.BankFrame;
import Miscellaneous.StockMarket;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.ScrollPane;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.AbstractListModel;

public class SellStockScreen extends BankFrame {

	private static final long serialVersionUID = 1L;

	private JList<String> stocks;
	private Securities securitiesAccount;
	private StockMarket stockMarket;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public SellStockScreen(Securities securitiesAccount, StockMarket stockMarket) {
		super(100, 100, 536, 361);
		this.securitiesAccount = securitiesAccount;
		this.stockMarket = stockMarket;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method builds the actual Sell Stock GUI Screen with all its requirements.
	 */
	@Override
	public void initialize(ArrayList<BankAction> actions, String errorMessage) {
		JLabel title = new JLabel("Sell Stocks");
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title.setBounds(10, 11, 414, 35);
		this.addComponent(title);

		JLabel stocksTitle = new JLabel("Current Stocks");
		stocksTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		stocksTitle.setBounds(10, 57, 154, 14);
		this.addComponent(stocksTitle);

		stocks = new JList<String>();
		stocks.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = processOwnedStocks();

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
		stocks.setBounds(10, 82, 500, 144);

		ScrollPane stocksContainer = new ScrollPane();
		stocksContainer.add(stocks);
		stocksContainer.setBounds(10, 82, 500, 144);
		this.addComponent(stocksContainer);

		JLabel errorMessageField = new JLabel("" + errorMessage);
		errorMessageField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		errorMessageField.setBounds(10, 245, 500, 14);
		this.addComponent(errorMessageField);

		JButton buyStockBtn = new JButton("Sell Stock");
		buyStockBtn.setBounds(10, 279, 117, 23);
		buyStockBtn.setAction(actions.get(0));
		this.addComponent(buyStockBtn);

		JButton backBtn = new JButton("Back");
		backBtn.setBounds(421, 288, 89, 23);
		backBtn.setAction(actions.get(1));
		this.addComponent(backBtn);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public String getSelectedStock() {
		return this.stocks.getSelectedValue();
	}

	/* ============== */
	/* Helper Methods */
	/* ============== */

	/*
	 * Returns a formatted String Array to display all the Stocks the Client
	 * currently owns.
	 */
	public String[] processOwnedStocks() {
		ArrayList<String> scrapValues = new ArrayList<String>();
		for (String st : securitiesAccount.getStocks().getCurrentStocks().keySet()) {
			String s = "- " + st + " | Amount: " + securitiesAccount.getStocks().getAmount(st) + " | Current Value: "
					+ stockMarket.checkPrice(st) + " | Unrealized Profit: "
					+ securitiesAccount.getStocks().getUnrealizedProfit(st, stockMarket);
			scrapValues.add(s);
		}

		String[] formattedValues = new String[scrapValues.size()];
		for (int i = 0; i < scrapValues.size(); i++)
			formattedValues[i] = scrapValues.get(i);

		return formattedValues;
	}

}
