package GUI.General.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.General.LoginScreen;
import GUI.General.Actions.Login;

public class GoToLogin extends BankAction {

	private static final long serialVersionUID = 1L;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToLogin(String name, BankGUI gui) {
		super(name, gui);
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the User to the Login Screen.
	 */
	public void actionPerformed(ActionEvent e) {
		LoginScreen newScreen = new LoginScreen();
		newScreen.initialize(new ArrayList<BankAction>(
				Arrays.asList(new Login(this.getGUI(), newScreen), new GoToRegister(this.getGUI()))), "");
		this.getGUI().setFrame(newScreen);
	}

}
