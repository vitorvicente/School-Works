package GUI.Manager.Actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Manager.ManageStockScreen;
import GUI.Manager.RemoveStockScreen;
import GUI.Manager.Actions.Navigation.GoToAddStock;
import GUI.Manager.Actions.Navigation.GoToEditStock;
import GUI.Manager.Actions.Navigation.GoToManageStock;
import GUI.Manager.Actions.Navigation.GoToManagerMain;
import GUI.Manager.Actions.Navigation.GoToRemoveStock;
import Miscellaneous.Stock;
import Users.Manager;

public class RemoveStock extends BankAction {

	private static final long serialVersionUID = 1L;

	private Manager manager;
	private RemoveStockScreen screen;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public RemoveStock(BankGUI gui, Manager manager, RemoveStockScreen screen) {
		super("Remove Stock", gui);
		this.manager = manager;
		this.screen = screen;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method removes a selected Stock from circulation. It first checks for
	 * valid input, and if it finds it, it removes the stock from the Stock Market,
	 * meaning it cannot be purchased anymore, and if it is sold, it will be sold
	 * for 0 USD.
	 */
	public void actionPerformed(ActionEvent e) {
		if (screen.getSelectedStock() == null || screen.getSelectedStock().isEmpty()) {
			RemoveStockScreen newScreen = new RemoveStockScreen(this.getGUI().getBank().getStockMarket());
			newScreen
					.initialize(
							new ArrayList<BankAction>(
									Arrays.asList(new BankAction[] { new RemoveStock(this.getGUI(), manager, newScreen),
											new GoToManageStock("Back", this.getGUI(), manager) })),
							"No Stock Selected!");
			this.getGUI().setFrame(newScreen);
			return;
		}

		String parsedCompany = screen.getSelectedStock().split(" ")[1];

		Stock finalStock = null;
		for (Stock stock : this.getGUI().getBank().getStockMarket().getRawAvailableStocks().keySet()) {
			if (stock.getCompanyName().equals(parsedCompany)) {
				finalStock = stock;
				break;
			}
		}

		if (finalStock == null) {
			RemoveStockScreen newScreen = new RemoveStockScreen(this.getGUI().getBank().getStockMarket());
			newScreen.initialize(
					new ArrayList<BankAction>(
							Arrays.asList(new BankAction[] { new RemoveStock(this.getGUI(), manager, newScreen),
									new GoToManageStock("Back", this.getGUI(), manager) })),
					"CRITICAL ERROR: STOCK NOT FOUND!");
			this.getGUI().setFrame(newScreen);
			return;
		}

		this.getGUI().getBank().getStockMarket().removeStock(finalStock);

		ManageStockScreen newScreen = new ManageStockScreen(this.getGUI().getBank().getStockMarket());
		newScreen.initialize(new ArrayList<BankAction>(Arrays.asList(new BankAction[] {
				new GoToEditStock(this.getGUI(), manager), new GoToRemoveStock(this.getGUI(), manager),
				new GoToAddStock(this.getGUI(), manager), new GoToManagerMain(this.getGUI(), manager) })), "");
		this.getGUI().setFrame(newScreen);
	}

}
