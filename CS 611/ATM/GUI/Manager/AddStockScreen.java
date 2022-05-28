package GUI.Manager;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextField;

import GUI.Abstraction.BankAction;
import GUI.Abstraction.BankFrame;

import javax.swing.JButton;

public class AddStockScreen extends BankFrame {

	private static final long serialVersionUID = 1L;

	private JTextField nameField;
	private JTextField priceField;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public AddStockScreen() {
		super(100, 100, 252, 274);
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method builds the actual Add Stock GUI Screen with all its requirements.
	 */
	@Override
	public void initialize(ArrayList<BankAction> actions, String errorMessage) {
		JLabel title = new JLabel("Add Stock");
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title.setBounds(10, 11, 211, 35);
		this.addComponent(title);

		JLabel nameTitle = new JLabel("Company Trading Code");
		nameTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		nameTitle.setBounds(10, 57, 211, 14);
		this.addComponent(nameTitle);

		nameField = new JTextField();
		nameField.setBounds(10, 82, 216, 20);
		this.addComponent(nameField);
		nameField.setColumns(10);

		JLabel priceTitle = new JLabel("Stock Price");
		priceTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		priceTitle.setBounds(10, 113, 211, 14);
		this.addComponent(priceTitle);

		priceField = new JTextField();
		priceField.setColumns(10);
		priceField.setBounds(10, 138, 216, 20);
		this.addComponent(priceField);

		JLabel errorMessageField = new JLabel("" + errorMessage);
		errorMessageField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		errorMessageField.setBounds(10, 169, 211, 14);
		this.addComponent(errorMessageField);

		JButton saveBtn = new JButton("Save");
		saveBtn.setBounds(10, 190, 89, 23);
		saveBtn.setAction(actions.get(0));
		this.addComponent(saveBtn);

		JButton backBtn = new JButton("Back");
		backBtn.setBounds(132, 190, 89, 23);
		backBtn.setAction(actions.get(1));
		this.addComponent(backBtn);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public String getPrice() {
		return this.priceField.getText();
	}

	public String getCompanyName() {
		return this.nameField.getText();
	}

}
