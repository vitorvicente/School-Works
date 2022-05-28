package GUI.Client;

import GUI.Abstraction.BankAction;
import GUI.Abstraction.BankFrame;
import Users.Client;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;

public class AccountsScreen extends BankFrame {

	private static final long serialVersionUID = 1L;

	private Client client;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public AccountsScreen(Client client) {
		super(100, 100, 450, 300);
		this.client = client;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method builds the actual Accounts GUI Screen with all its requirements.
	 */
	@Override
	public void initialize(ArrayList<BankAction> actions, String errorMessage) {
		JLabel title = new JLabel("Client Accounts");
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title.setBounds(10, 11, 165, 35);
		this.addComponent(title);

		JLabel savingsTitle = new JLabel("Savings Account: " + client.getSavingsAccount());
		savingsTitle.setBounds(38, 73, 190, 14);
		this.addComponent(savingsTitle);

		JButton savingsBtn = new JButton("Access Account");
		savingsBtn.setBounds(238, 69, 165, 23);
		savingsBtn.setAction(actions.get(0));
		this.addComponent(savingsBtn);

		JLabel checkingTitle = new JLabel("Checking Account: " + client.getCheckingAccount());
		checkingTitle.setBounds(38, 121, 190, 14);
		this.addComponent(checkingTitle);

		JButton checkingBtn = new JButton("Access Account");
		checkingBtn.setBounds(238, 117, 165, 23);
		checkingBtn.setAction(actions.get(1));
		this.addComponent(checkingBtn);

		JLabel securitiesTitle = new JLabel("Securities Account: " + client.getSecuritiesAccount());
		securitiesTitle.setBounds(38, 169, 190, 14);
		this.addComponent(securitiesTitle);

		JButton securitiesBtn = new JButton("Access Account");
		securitiesBtn.setBounds(238, 165, 165, 23);
		securitiesBtn.setAction(actions.get(2));
		this.addComponent(securitiesBtn);

		JLabel errorMessageField = new JLabel("" + errorMessage);
		errorMessageField.setBounds(38, 204, 365, 14);
		this.addComponent(errorMessageField);

		JButton backBtn = new JButton("Back");
		backBtn.setBounds(299, 227, 125, 23);
		backBtn.setAction(actions.get(3));
		this.addComponent(backBtn);
	}

}
