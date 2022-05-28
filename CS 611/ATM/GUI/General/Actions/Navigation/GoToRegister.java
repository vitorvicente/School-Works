package GUI.General.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.General.RegisterScreen;
import GUI.General.Actions.RegisterAccount;

public class GoToRegister extends BankAction {

	private static final long serialVersionUID = 1L;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToRegister(BankGUI gui) {
		super("Register", gui);
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the User to the Registration Screen.
	 */
	public void actionPerformed(ActionEvent e) {
		RegisterScreen newScreen = new RegisterScreen();
		newScreen
				.initialize(
						new ArrayList<BankAction>(Arrays.asList(new BankAction[] {
								new RegisterAccount(this.getGUI(), newScreen), new GoToLogin("Back", this.getGUI()) })),
						"");
		this.getGUI().setFrame(newScreen);
	}

}