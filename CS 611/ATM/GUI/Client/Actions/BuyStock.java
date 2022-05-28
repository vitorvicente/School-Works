package GUI.Client.Actions;

import Accounts.Account;
import Accounts.Securities;
import GUI.Abstraction.BankAction;
import GUI.BankGUI;
import GUI.Client.Actions.Navigation.GoToAccounts;
import GUI.Client.Actions.Navigation.GoToBuyStock;
import GUI.Client.Actions.Navigation.GoToSecurities;
import GUI.Client.Actions.Navigation.GoToSellStock;
import GUI.Client.BuyStockScreen;
import GUI.Client.SecuritiesAccountScreen;
import GUI.General.Actions.Navigation.GoToTransfer;
import Miscellaneous.Stock;
import Transactions.Types.C2B;
import Transactions.Types.C2N;
import Users.Client;
import Persistent.Database;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class BuyStock extends BankAction {

	private static final long serialVersionUID = 1L;

	private Client client;
	private Securities account;
	private BuyStockScreen screen;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public BuyStock(BankGUI gui, Client client, Securities account, BuyStockScreen screen) {
		super("Buy Stock", gui);
		this.client = client;
		this.account = account;
		this.screen = screen;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * The following method proceeds with the purchase of a specific Stock by a
	 * specific Client. It starts by checking if there is a valid input, then
	 * calculates the price, and makes sure the Client can pay it, then finally it
	 * completes the purchase and moves back to the Securities Account screen.
	 */
	public void actionPerformed(ActionEvent e) {
		if (screen.getSelectedStock() == null || screen.getSelectedStock().isEmpty()) {
			BuyStockScreen newScreen = new BuyStockScreen(this.getGUI().getBank().getStockMarket());
			newScreen.initialize(new ArrayList<BankAction>(
					Arrays.asList(new BankAction[] { new BuyStock(this.getGUI(), client, account, newScreen),
							new GoToSecurities("Back", this.getGUI(), client) })),
					"No Stock Selected");
			this.getGUI().setFrame(newScreen);
			return;
		}

		String parsedCompany = screen.getSelectedStock().split(" ")[1];

		int price = this.getGUI().getBank().getStockMarket().checkPrice(parsedCompany);

		int priceWithFees = (int) Math.round(price * (1 + Bank.Bank.TRANSACTION_FEE));
		if (account.getWallet().getAmountOf("USD") < priceWithFees) {
			BuyStockScreen newScreen = new BuyStockScreen(this.getGUI().getBank().getStockMarket());
			newScreen.initialize(new ArrayList<BankAction>(
					Arrays.asList(new BankAction[] { new BuyStock(this.getGUI(), client, account, newScreen),
							new GoToSecurities("Back", this.getGUI(), client) })),
					"Not Enough Funds!");
			this.getGUI().setFrame(newScreen);
			return;
		}

		account.withdrawFunds("USD", priceWithFees);

		Account bankAccount = this.getGUI().getBank().getGlobalAccount();
		bankAccount.depositFunds("USD", (priceWithFees - price));

		Stock stock = Database.getInstance().createStock(parsedCompany);
		stock.setBuyingPrice(price);
		account.addStock(stock);

		C2N transaction = Database.getInstance().createTransactionC2N(price, "USD", account);
		client.addTransaction(new Date(), transaction);
		C2B transactionFee = Database.getInstance().createTransactionC2B((priceWithFees - price), "USD", bankAccount,
				account);
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
