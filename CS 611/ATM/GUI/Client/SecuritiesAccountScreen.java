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

public class SecuritiesAccountScreen extends BankFrame {

	private static final long serialVersionUID = 1L;

	private Securities securitiesAccount;
	private StockMarket stockMarket;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public SecuritiesAccountScreen(Securities securitiesAccount, StockMarket stockMarket) {
		super(100, 100, 536, 417);
		this.securitiesAccount = securitiesAccount;
		this.stockMarket = stockMarket;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method builds the actual Securities Account GUI Screen with all its
	 * requirements.
	 */
	@Override
	public void initialize(ArrayList<BankAction> actions, String errorMessage) {
		JLabel title = new JLabel("Securities Account #" + securitiesAccount.getID());
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title.setBounds(10, 11, 414, 35);
		this.addComponent(title);

		JLabel currentBalance = new JLabel("Current USD Balance: " + securitiesAccount.getWallet().getAmountOf("USD"));
		currentBalance.setBounds(10, 57, 199, 14);
		this.addComponent(currentBalance);

		JLabel realizedProfit = new JLabel("Realized Profit: " + securitiesAccount.getRealizedProfit() + " USD");
		realizedProfit.setBounds(219, 57, 199, 14);
		this.addComponent(realizedProfit);

		JLabel currentStocksTitle = new JLabel("Current Stocks");
		currentStocksTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		currentStocksTitle.setBounds(10, 82, 154, 14);
		this.addComponent(currentStocksTitle);

		JList<String> currentStocks = new JList<String>();
		currentStocks.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = processOwnedStocks();

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
		currentStocks.setBounds(10, 107, 500, 144);

		ScrollPane currentStocksContainer = new ScrollPane();
		currentStocksContainer.add(currentStocks);
		currentStocksContainer.setBounds(10, 107, 500, 144);
		this.addComponent(currentStocksContainer);

		JButton buyStockBtn = new JButton("Buy Stock");
		buyStockBtn.setBounds(10, 280, 117, 23);
		buyStockBtn.setAction(actions.get(0));
		this.addComponent(buyStockBtn);

		JButton sellStockBtn = new JButton("Sell Stock");
		sellStockBtn.setBounds(10, 314, 117, 23);
		sellStockBtn.setAction(actions.get(1));
		this.addComponent(sellStockBtn);

		JButton transferBtn = new JButton("Transfer Money");
		transferBtn.setBounds(338, 280, 172, 23);
		transferBtn.setAction(actions.get(2));
		this.addComponent(transferBtn);

		JButton backBtn = new JButton("Back");
		backBtn.setBounds(421, 344, 89, 23);
		backBtn.setAction(actions.get(3));
		this.addComponent(backBtn);
	}

	/* ============== */
	/* Helper Methods */
	/* ============== */

	/*
	 * Returns a formatted String Array to display all the Stocks the Client owns.
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
