package GUI.Manager.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Manager.RemoveStockScreen;
import GUI.Manager.Actions.RemoveStock;
import Users.Manager;

public class GoToRemoveStock extends BankAction {

	private static final long serialVersionUID = 1L;

	private Manager manager;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToRemoveStock(BankGUI gui, Manager manager) {
		super("Remove Stock", gui);
		this.manager = manager;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the Manager to the Remove Stock Screen.
	 */
	public void actionPerformed(ActionEvent e) {
		RemoveStockScreen newScreen = new RemoveStockScreen(this.getGUI().getBank().getStockMarket());
		newScreen.initialize(new ArrayList<BankAction>(
				Arrays.asList(new BankAction[] { new RemoveStock(this.getGUI(), manager, newScreen),
						new GoToManageStock("Back", this.getGUI(), manager) })),
				"");
		this.getGUI().setFrame(newScreen);
	}

}
