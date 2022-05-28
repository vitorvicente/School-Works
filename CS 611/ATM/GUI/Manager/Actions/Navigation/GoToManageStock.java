package GUI.Manager.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Manager.ManageStockScreen;
import Users.Manager;

public class GoToManageStock extends BankAction {

	private static final long serialVersionUID = 1L;

	private Manager manager;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToManageStock(String name, BankGUI gui, Manager manager) {
		super(name, gui);
		this.manager = manager;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the Manager to the Manage Stock Screen.
	 */
	public void actionPerformed(ActionEvent e) {
		ManageStockScreen newScreen = new ManageStockScreen(this.getGUI().getBank().getStockMarket());
		newScreen.initialize(new ArrayList<BankAction>(Arrays.asList(new BankAction[] {
				new GoToEditStock(this.getGUI(), manager), new GoToRemoveStock(this.getGUI(), manager),
				new GoToAddStock(this.getGUI(), manager), new GoToManagerMain(this.getGUI(), manager) })), "");
		this.getGUI().setFrame(newScreen);
	}

}
