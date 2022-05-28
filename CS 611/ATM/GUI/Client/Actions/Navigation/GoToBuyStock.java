package GUI.Client.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import Accounts.Securities;
import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Client.BuyStockScreen;
import GUI.Client.Actions.BuyStock;
import Users.Client;

public class GoToBuyStock extends BankAction {

	private static final long serialVersionUID = 1L;

	private Client client;
	private Securities account;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToBuyStock(BankGUI gui, Client client, Securities account) {
		super("Buy Stock", gui);
		this.client = client;
		this.account = account;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the Client to the Buy Stock Screen.
	 */
	public void actionPerformed(ActionEvent e) {
		BuyStockScreen newScreen = new BuyStockScreen(this.getGUI().getBank().getStockMarket());
		newScreen.initialize(new ArrayList<BankAction>(
				Arrays.asList(new BankAction[] { new BuyStock(this.getGUI(), client, account, newScreen),
						new GoToSecurities("Back", this.getGUI(), client) })),
				"");
		this.getGUI().setFrame(newScreen);
	}

}
