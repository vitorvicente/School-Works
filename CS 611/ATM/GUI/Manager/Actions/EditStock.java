package GUI.Manager.Actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Manager.EditStockScreen;
import GUI.Manager.ManageStockScreen;
import GUI.Manager.Actions.Navigation.GoToAddStock;
import GUI.Manager.Actions.Navigation.GoToEditStock;
import GUI.Manager.Actions.Navigation.GoToManageStock;
import GUI.Manager.Actions.Navigation.GoToManagerMain;
import GUI.Manager.Actions.Navigation.GoToRemoveStock;
import Miscellaneous.Stock;
import Users.Manager;

public class EditStock extends BankAction {

	private static final long serialVersionUID = 1L;

	private Manager manager;
	private EditStockScreen screen;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public EditStock(BankGUI gui, Manager manager, EditStockScreen screen) {
		super("Save", gui);
		this.manager = manager;
		this.screen = screen;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method proceeds to edit the selected Stock by the Manager, checking for
	 * valid inputs, and the proceeding to alter the Stock in question.
	 */
	public void actionPerformed(ActionEvent e) {
		if (screen.getSelectedStock() == null || screen.getSelectedStock().isEmpty()) {
			EditStockScreen newScreen = new EditStockScreen(this.getGUI().getBank().getStockMarket());
			newScreen
					.initialize(
							new ArrayList<BankAction>(
									Arrays.asList(new BankAction[] { new EditStock(this.getGUI(), manager, newScreen),
											new GoToManageStock("Back", this.getGUI(), manager) })),
							"Invalid Fields Entered!");
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
			EditStockScreen newScreen = new EditStockScreen(this.getGUI().getBank().getStockMarket());
			newScreen.initialize(
					new ArrayList<BankAction>(
							Arrays.asList(new BankAction[] { new EditStock(this.getGUI(), manager, newScreen),
									new GoToManageStock("Back", this.getGUI(), manager) })),
					"CRITICAL ERROR: STOCK NOT FOUND!");
			this.getGUI().setFrame(newScreen);
			return;
		}

		if (screen.getPrice() != null && !screen.getPrice().isEmpty())
			this.getGUI().getBank().getStockMarket().updatePrice(finalStock, Integer.valueOf(screen.getPrice()));

		ManageStockScreen newScreen = new ManageStockScreen(this.getGUI().getBank().getStockMarket());
		newScreen.initialize(new ArrayList<BankAction>(Arrays.asList(new BankAction[] {
				new GoToEditStock(this.getGUI(), manager), new GoToRemoveStock(this.getGUI(), manager),
				new GoToAddStock(this.getGUI(), manager), new GoToManagerMain(this.getGUI(), manager) })), "");
		this.getGUI().setFrame(newScreen);
	}

}
