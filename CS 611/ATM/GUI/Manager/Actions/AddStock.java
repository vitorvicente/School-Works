package GUI.Manager.Actions;

import GUI.Abstraction.BankAction;
import GUI.BankGUI;
import GUI.Manager.Actions.Navigation.*;
import Miscellaneous.Stock;
import GUI.Manager.AddStockScreen;
import GUI.Manager.ManageStockScreen;
import Users.Manager;
import Persistent.Database;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class AddStock extends BankAction {

	private static final long serialVersionUID = 1L;

	private Manager manager;
	private AddStockScreen screen;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public AddStock(BankGUI gui, Manager manager, AddStockScreen screen) {
		super("Save", gui);
		this.manager = manager;
		this.screen = screen;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method creates a new Stock, checking for valid input, and then creating
	 * the new Stock Object, and adding it to the Stock Market.
	 */
	public void actionPerformed(ActionEvent e) {
		if (screen.getCompanyName() == null || screen.getCompanyName().isEmpty() || screen.getPrice() == null
				|| screen.getPrice().isEmpty()) {
			AddStockScreen newScreen = new AddStockScreen();
			newScreen
					.initialize(
							new ArrayList<BankAction>(
									Arrays.asList(new BankAction[] { new AddStock(this.getGUI(), manager, newScreen),
											new GoToManageStock("Back", this.getGUI(), manager) })),
							"Invalid Fields Entered!");
			this.getGUI().setFrame(newScreen);
			return;
		}

		Stock newStock = Database.getInstance().createStock(screen.getCompanyName());
		this.getGUI().getBank().getStockMarket().addStock(newStock, Integer.valueOf(screen.getPrice()));

		ManageStockScreen newScreen = new ManageStockScreen(this.getGUI().getBank().getStockMarket());
		newScreen.initialize(new ArrayList<BankAction>(Arrays.asList(new BankAction[] {
				new GoToEditStock(this.getGUI(), manager), new GoToRemoveStock(this.getGUI(), manager),
				new GoToAddStock(this.getGUI(), manager), new GoToManagerMain(this.getGUI(), manager) })), "");
		this.getGUI().setFrame(newScreen);
	}

}
