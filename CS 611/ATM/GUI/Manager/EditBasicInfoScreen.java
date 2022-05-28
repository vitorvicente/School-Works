package GUI.Manager;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import GUI.Abstraction.BankAction;
import GUI.Abstraction.BankFrame;
import Users.Manager;

public class EditBasicInfoScreen extends BankFrame {

	private static final long serialVersionUID = 1L;

	private JTextField nameField;
	private JTextField usernameField;
	private JTextField passwordField;
	private Manager manager;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public EditBasicInfoScreen(Manager manager) {
		super(100, 100, 433, 333);
		this.manager = manager;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method builds the actual Edit Manager Info GUI Screen with all its
	 * requirements.
	 */
	@Override
	public void initialize(ArrayList<BankAction> actions, String errorMessage) {
		JLabel title = new JLabel("Edit Basic Info");
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title.setBounds(10, 11, 164, 35);
		this.addComponent(title);

		JLabel nameTitle = new JLabel("Name");
		nameTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		nameTitle.setBounds(10, 57, 123, 14);
		this.addComponent(nameTitle);

		JLabel currentName = new JLabel("Current Name: " + manager.getFullName());
		currentName.setBounds(163, 85, 244, 14);
		this.addComponent(currentName);

		nameField = new JTextField();
		nameField.setColumns(10);
		nameField.setBounds(10, 82, 123, 20);
		this.addComponent(nameField);

		JLabel usernameTitle = new JLabel("Username");
		usernameTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		usernameTitle.setBounds(10, 113, 123, 14);
		this.addComponent(usernameTitle);

		JLabel currentUsername = new JLabel("Current Username: " + manager.getUsername());
		currentUsername.setBounds(163, 141, 244, 14);
		this.addComponent(currentUsername);

		usernameField = new JTextField();
		usernameField.setColumns(10);
		usernameField.setBounds(10, 138, 123, 20);
		this.addComponent(usernameField);

		JLabel passwordTitle = new JLabel("Password");
		passwordTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		passwordTitle.setBounds(10, 169, 123, 14);
		this.addComponent(passwordTitle);

		JLabel currentPassword = new JLabel("Current Password: " + manager.getPassword());
		currentPassword.setBounds(163, 197, 244, 14);
		this.addComponent(currentPassword);

		passwordField = new JTextField();
		passwordField.setColumns(10);
		passwordField.setBounds(10, 194, 123, 20);
		this.addComponent(passwordField);

		JButton saveBtn = new JButton("Save");
		saveBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		saveBtn.setBounds(10, 254, 123, 29);
		saveBtn.setAction(actions.get(0));
		this.addComponent(saveBtn);

		JButton backBtn = new JButton("Back");
		backBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		backBtn.setBounds(284, 254, 123, 29);
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

}
