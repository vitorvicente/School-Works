package GUI.Manager.Actions.Navigation;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import GUI.BankGUI;
import GUI.Abstraction.BankAction;
import GUI.Manager.ManageLoanRequestScreen;
import GUI.Manager.Actions.AcceptLoanRequest;
import Users.Manager;

public class GoToLoanRequests extends BankAction {

	private static final long serialVersionUID = 1L;

	private Manager manager;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public GoToLoanRequests(BankGUI gui, Manager manager) {
		super("Manage Loans Requests", gui);
		this.manager = manager;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * Moves the Manager to the Loan Request Screen.
	 */
	public void actionPerformed(ActionEvent e) {
		ManageLoanRequestScreen newScreen = new ManageLoanRequestScreen(this.getGUI().getBank().getLoans());
		newScreen.initialize(new ArrayList<BankAction>(
				Arrays.asList(new BankAction[] { new AcceptLoanRequest(this.getGUI(), manager, newScreen),
						new GoToManageLoans("Back", this.getGUI(), manager) })),
				"");
		this.getGUI().setFrame(newScreen);
	}

}
