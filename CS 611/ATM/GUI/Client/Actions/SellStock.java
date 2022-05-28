package GUI.Client.Actions;

import Accounts.Account;
import Accounts.Securities;
import GUI.Abstraction.BankAction;
import GUI.BankGUI;
import GUI.Client.Actions.Navigation.GoToAccounts;
import GUI.Client.Actions.Navigation.GoToBuyStock;
import GUI.Client.Actions.Navigation.GoToSecurities;
import GUI.Client.Actions.Navigation.GoToSellStock;
import GUI.Client.SecuritiesAccountScreen;
import GUI.Client.SellStockScreen;
import GUI.General.Actions.Navigation.GoToTransfer;
import Miscellaneous.Stock;
import Transactions.Types.C2B;
import Transactions.Types.N2C;
import Users.Client;
import Persistent.Database;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class SellStock extends BankAction {

	private static final long serialVersionUID = 1L;

	private Client client;
	private Securities account;
	private SellStockScreen screen;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public SellStock(BankGUI gui, Client client, Securities account, SellStockScreen screen) {
		super("Sell Stock", gui);
		this.client = client;
		this.account = account;
		this.screen = screen;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method proceeds with the actual sale of a Stock by a specific Client,
	 * checking for valid inputs, and then processing the sale. Once the sale is
	 * complete it moves back to the Securities Account Screen.
	 */
	public void actionPerformed(ActionEvent e) {
		if (screen.getSelectedStock() == null || screen.getSelectedStock().isEmpty()) {
			SellStockScreen newScreen = new SellStockScreen(account, this.getGUI().getBank().getStockMarket());
			newScreen
					.initialize(
							new ArrayList<BankAction>(Arrays
									.asList(new BankAction[] { new SellStock(this.getGUI(), client, account, newScreen),
											new GoToSecurities("Back", this.getGUI(), client) })),
							"No Stock Selected!");
			this.getGUI().setFrame(newScreen);
			return;
		}

		String parsedCompany = screen.getSelectedStock().split(" ")[1];
		int price = this.getGUI().getBank().getStockMarket().checkPrice(parsedCompany);
		Stock stock = account.getStocks().getCurrentStocks().get(parsedCompany).get(0);

		int fees = (int) Math.round(price * Bank.Bank.TRANSACTION_FEE);

		account.depositFunds("USD", price - fees);
		account.addRealizedProfit(price - fees - stock.getBuyingPrice());

		Account bankAccount = this.getGUI().getBank().getGlobalAccount();
		bankAccount.depositFunds("USD", fees);

		account.getStocks().removeStock(stock);
		stock.setSellingPrice(price);

		if (!account.getStocks().getSoldStocks().containsKey(parsedCompany))
			account.getStocks().getSoldStocks().put(parsedCompany, new ArrayList<Stock>());
		account.getStocks().getSoldStocks().get(parsedCompany).add(stock);

		N2C transaction = Database.getInstance().createTransactionN2C(price, "USD", account);
		client.addTransaction(new Date(), transaction);
		C2B transactionFee = Database.getInstance().createTransactionC2B(fees, "USD", bankAccount, account);
		this.getGUI().getBank().getManager().addTransaction(new Date(), transactionFee);

		SecuritiesAccountScreen newScreen = new SecuritiesAccountScreen(account,
				this.getGUI().getBank().getStockMarket());
		newScreen.initialize(new ArrayList<BankAction>(Arrays.asList(new BankAction[] {
				new GoToBuyStock(this.getGUI(), client, account), new GoToSellStock(this.getGUI(), client, account),
				new GoToTransfer(this.getGUI(), client, account), new GoToAccounts("Back", this.getGUI(), client) })),
				"");
		this.getGUI().setFrame(newScreen);
	}

}
