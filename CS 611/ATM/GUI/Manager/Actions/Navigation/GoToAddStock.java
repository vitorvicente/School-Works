package GUI.Manager.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Manager.AddStockScreen;
import GUI.Manager.Actions.AddStock;
import Users.Manager;

public class GoToAddStock extends BankAction {

	private static final long serialVersionUID = 1L;

	private Manager manager;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToAddStock(BankGUI gui, Manager manager) {
		super("Add Stock", gui);
		this.manager = manager;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the Manager to the Add Stock Screen.
	 */
	public void actionPerformed(ActionEvent e) {
		AddStockScreen newScreen = new AddStockScreen();
		newScreen.initialize(new ArrayList<BankAction>(
				Arrays.asList(new BankAction[] { new AddStock(this.getGUI(), manager, newScreen),
						new GoToManageStock("Back", this.getGUI(), manager) })),
				"");
		this.getGUI().setFrame(newScreen);
	}

}
