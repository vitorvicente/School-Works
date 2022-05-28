package GUI.Client.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import Accounts.Securities;
import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Client.SellStockScreen;
import GUI.Client.Actions.SellStock;
import Users.Client;

public class GoToSellStock extends BankAction {

	private static final long serialVersionUID = 1L;

	private Client client;
	private Securities account;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToSellStock(BankGUI gui, Client client, Securities account) {
		super("Sell Stock", gui);
		this.client = client;
		this.account = account;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the Client to the Sell Stock Screen.
	 */
	public void actionPerformed(ActionEvent e) {
		SellStockScreen newScreen = new SellStockScreen(account, this.getGUI().getBank().getStockMarket());
		newScreen.initialize(new ArrayList<BankAction>(
				Arrays.asList(new BankAction[] { new SellStock(this.getGUI(), client, account, newScreen),
						new GoToSecurities("Back", this.getGUI(), client) })),
				"");
		this.getGUI().setFrame(newScreen);
	}

}
