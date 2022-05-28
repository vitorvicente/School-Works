package GUI.General;

import javax.swing.JButton;
import javax.swing.JPasswordField;

import GUI.Abstraction.BankAction;
import GUI.Abstraction.BankFrame;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class LoginScreen extends BankFrame {

	private static final long serialVersionUID = 1L;

	private JTextField usernameField;
	private JPasswordField passwordField;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LoginScreen() {
		super(100, 100, 450, 300);
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method builds the actual Login GUI Screen with all its requirements.
	 */
	@Override
	public void initialize(ArrayList<BankAction> actions, String errorMessage) {
		JLabel title = new JLabel("Welcome to the CS611 Bank");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.BOLD, 20));
		title.setBounds(10, 11, 414, 39);
		this.addComponent(title);

		JLabel usernameTitle = new JLabel("Username");
		usernameTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		usernameTitle.setBounds(47, 70, 114, 14);
		this.addComponent(usernameTitle);

		usernameField = new JTextField();
		usernameField.setBounds(47, 95, 354, 20);
		this.addComponent(usernameField);
		usernameField.setColumns(10);

		JLabel passwordTitle = new JLabel("Password");
		passwordTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		passwordTitle.setBounds(47, 128, 114, 14);
		this.addComponent(passwordTitle);

		passwordField = new JPasswordField();
		passwordField.setBounds(47, 153, 354, 20);
		this.addComponent(passwordField);

		JLabel errorMessageField = new JLabel(errorMessage + "");
		errorMessageField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		errorMessageField.setBounds(47, 184, 354, 14);
		this.addComponent(errorMessageField);

		JButton loginBtn = new JButton("Login");
		loginBtn.setAction(actions.get(0));
		loginBtn.setBounds(47, 214, 89, 23);
		this.addComponent(loginBtn);

		JButton registerBtn = new JButton("Register");
		registerBtn.setAction(actions.get(1));
		registerBtn.setBounds(312, 214, 89, 23);
		this.addComponent(registerBtn);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public char[] getPassword() {
		return this.passwordField.getPassword();
	}

	public String getUsername() {
		return this.usernameField.getText();
	}

}
