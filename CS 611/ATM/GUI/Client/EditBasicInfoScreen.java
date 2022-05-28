package GUI.Client;

import GUI.Abstraction.BankAction;
import GUI.Abstraction.BankFrame;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JButton;

public class EditBasicInfoScreen extends BankFrame {

	private static final long serialVersionUID = 1L;

	private JTextField nameField;
	private JTextField usernameField;
	private JTextField passwordField;
	private JTextField ssnField;
	private JTextField phoneField;
	private JTextField addressField;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public EditBasicInfoScreen() {
		super(100, 100, 356, 362);
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method builds the actual Edit Basic Info GUI Screen with all its requirements.
	 */	
	@Override
	public void initialize(ArrayList<BankAction> actions, String errorMessage) {
		JLabel title = new JLabel("Edit Basic Info");
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title.setBounds(10, 11, 164, 35);
		this.addComponent(title);

		JLabel nameTitle = new JLabel("Name");
		nameTitle.setBounds(10, 57, 123, 14);
		this.addComponent(nameTitle);

		nameField = new JTextField();
		nameField.setColumns(10);
		nameField.setBounds(10, 82, 123, 20);
		this.addComponent(nameField);

		JLabel usernameTitle = new JLabel("Username");
		usernameTitle.setBounds(10, 113, 123, 14);
		this.addComponent(usernameTitle);

		usernameField = new JTextField();
		usernameField.setColumns(10);
		usernameField.setBounds(10, 138, 123, 20);
		this.addComponent(usernameField);

		JLabel passwordTitle = new JLabel("Password");
		passwordTitle.setBounds(10, 169, 123, 14);
		this.addComponent(passwordTitle);

		passwordField = new JTextField();
		passwordField.setColumns(10);
		passwordField.setBounds(10, 194, 123, 20);
		this.addComponent(passwordField);

		JLabel ssnTitle = new JLabel("SSN");
		ssnTitle.setBounds(198, 57, 123, 14);
		this.addComponent(ssnTitle);

		ssnField = new JTextField();
		ssnField.setColumns(10);
		ssnField.setBounds(198, 82, 123, 20);
		this.addComponent(ssnField);

		JLabel phoneTitle = new JLabel("Phone Number");
		phoneTitle.setBounds(198, 113, 123, 14);
		this.addComponent(phoneTitle);

		phoneField = new JTextField();
		phoneField.setColumns(10);
		phoneField.setBounds(198, 138, 123, 20);
		this.addComponent(phoneField);

		JLabel addressTitle = new JLabel("Address");
		addressTitle.setBounds(198, 169, 123, 14);
		this.addComponent(addressTitle);

		addressField = new JTextField();
		addressField.setColumns(10);
		addressField.setBounds(198, 194, 123, 20);
		this.addComponent(addressField);

		JButton saveBtn = new JButton("Save");
		saveBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		saveBtn.setBounds(10, 250, 123, 29);
		saveBtn.setAction(actions.get(0));
		this.addComponent(saveBtn);

		JButton backBtn = new JButton("Back");
		backBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		backBtn.setBounds(198, 250, 123, 29);
		backBtn.setAction(actions.get(1));
		this.addComponent(backBtn);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public String getName() {
		return this.nameField.getText();
	}

	public String getUsername() {
		return this.usernameField.getText();
	}

	public String getPassword() {
		return this.passwordField.getText();
	}

	public String getSsn() {
		return this.ssnField.getText();
	}

	public String getPhone() {
		return this.phoneField.getText();
	}

	public String getAddress() {
		return this.addressField.getText();
	}

}
