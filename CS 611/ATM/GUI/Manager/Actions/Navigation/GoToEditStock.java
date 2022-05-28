package GUI.Manager.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Manager.EditStockScreen;
import GUI.Manager.Actions.EditStock;
import Users.Manager;

public class GoToEditStock extends BankAction {

	private static final long serialVersionUID = 1L;

	private Manager manager;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToEditStock(BankGUI gui, Manager manager) {
		super("Edit Stock Prices", gui);
		this.manager = manager;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the Manager to the Edit Stock Screen.
	 */
	public void actionPerformed(ActionEvent e) {
		EditStockScreen newScreen = new EditStockScreen(this.getGUI().getBank().getStockMarket());
		newScreen.initialize(new ArrayList<BankAction>(
				Arrays.asList(new BankAction[] { new EditStock(this.getGUI(), manager, newScreen),
						new GoToManageStock("Back", this.getGUI(), manager) })),
				"");
		this.getGUI().setFrame(newScreen);
	}

}
