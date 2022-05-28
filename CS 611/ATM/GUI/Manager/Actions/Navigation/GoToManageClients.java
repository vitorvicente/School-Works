package GUI.Manager.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Manager.ManageClientsScreen;
import Users.Manager;

public class GoToManageClients extends BankAction {

	private static final long serialVersionUID = 1L;

	private Manager manager;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToManageClients(String name, BankGUI gui, Manager manager) {
		super(name, gui);
		this.manager = manager;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the Manager to the Manage Clients Screen.
	 */
	public void actionPerformed(ActionEvent e) {
		ManageClientsScreen newScreen = new ManageClientsScreen(this.getGUI().getBank().getClients());
		newScreen.initialize(new ArrayList<BankAction>(Arrays.asList(new BankAction[] {
				new GoToClientInfo(this.getGUI(), manager, newScreen), new GoToManagerMain(this.getGUI(), manager) })),
				"");
		this.getGUI().setFrame(newScreen);
	}

}
