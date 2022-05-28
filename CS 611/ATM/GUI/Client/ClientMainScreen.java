package GUI.Client;

import GUI.Abstraction.BankAction;
import GUI.Abstraction.BankFrame;
import Users.Client;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;

public class ClientMainScreen extends BankFrame {

	private static final long serialVersionUID = 1L;

	private Client client;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public ClientMainScreen(Client client) {
		super(100, 100, 450, 300);
		this.client = client;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method builds the actual Main Client GUI Screen with all its requirements.
	 */
	@Override
	public void initialize(ArrayList<BankAction> actions, String errorMessage) {
		JLabel title = new JLabel("Client Info");
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title.setBounds(10, 11, 101, 35);
		this.addComponent(title);

		JLabel name = new JLabel("Name: " + client.getFullName());
		name.setBounds(10, 57, 201, 14);
		this.addComponent(name);

		JLabel username = new JLabel("Username: " + client.getUsername());
		username.setBounds(10, 82, 201, 14);
		this.addComponent(username);

		JLabel password = new JLabel("Password: " + client.getPassword());
		password.setBounds(10, 107, 201, 14);
		this.addComponent(password);

		JLabel ssn = new JLabel("SSN: " + client.getSsn());
		ssn.setBounds(10, 132, 201, 14);
		this.addComponent(ssn);

		JLabel phoneNumber = new JLabel("Phone Number: " + client.getPhoneNumber());
		phoneNumber.setBounds(10, 157, 201, 14);
		this.addComponent(phoneNumber);

		JLabel address = new JLabel("Address: " + client.getAddress());
		address.setBounds(10, 182, 201, 14);
		this.addComponent(address);

		JButton editInfoBtn = new JButton("Edit Basic Info");
		editInfoBtn.setBounds(10, 211, 141, 23);
		editInfoBtn.setAction(actions.get(0));
		this.addComponent(editInfoBtn);

		JButton viewAccountsBtn = new JButton("View Accounts");
		viewAccountsBtn.setBounds(260, 78, 164, 23);
		viewAccountsBtn.setAction(actions.get(2));
		this.addComponent(viewAccountsBtn);

		JButton viewLoansBtn = new JButton("View Loans");
		viewLoansBtn.setBounds(260, 103, 164, 23);
		viewLoansBtn.setAction(actions.get(3));
		this.addComponent(viewLoansBtn);

		JButton checkTransactionsBtn = new JButton("Check Transactions");
		checkTransactionsBtn.setBounds(260, 128, 164, 23);
		checkTransactionsBtn.setAction(actions.get(4));
		this.addComponent(checkTransactionsBtn);

		JButton logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(283, 227, 141, 23);
		logoutBtn.setAction(actions.get(1));
		this.addComponent(logoutBtn);
	}

}
