package GUI.Client;

import java.awt.Font;
import java.awt.ScrollPane;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Currency.Currency;
import GUI.Abstraction.BankAction;
import GUI.Abstraction.BankFrame;

import javax.swing.JList;
import javax.swing.AbstractListModel;

public class RequestLoanScreen extends BankFrame {

	private static final long serialVersionUID = 1L;

	private JTextField amountField;
	private JTextField collateralField;
	private JTextField interestRateField;
	private JList<String> currencies;
	private ArrayList<Currency> availableCurrencies;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public RequestLoanScreen(ArrayList<Currency> availableCurrencies) {
		super(100, 100, 356, 406);
		this.availableCurrencies = availableCurrencies;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method builds the actual Request Loan GUI Screen with all its
	 * requirements.
	 */
	@Override
	public void initialize(ArrayList<BankAction> actions, String errorMessage) {
		JLabel title = new JLabel("Request Loan");
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title.setBounds(10, 11, 164, 35);
		this.addComponent(title);

		JLabel amountTitle = new JLabel("Amount");
		amountTitle.setBounds(10, 57, 123, 14);
		this.addComponent(amountTitle);

		amountField = new JTextField();
		amountField.setColumns(10);
		amountField.setBounds(10, 82, 123, 20);
		this.addComponent(amountField);

		JLabel collateralTitle = new JLabel("Collateral");
		collateralTitle.setBounds(198, 57, 123, 14);
		this.addComponent(collateralTitle);

		collateralField = new JTextField();
		collateralField.setColumns(10);
		collateralField.setBounds(198, 82, 123, 20);
		this.addComponent(collateralField);

		JLabel interestRateTitle = new JLabel("Proposed Interest Rate");
		interestRateTitle.setBounds(198, 113, 123, 14);
		this.addComponent(interestRateTitle);

		interestRateField = new JTextField();
		interestRateField.setColumns(10);
		interestRateField.setBounds(198, 140, 123, 20);
		this.addComponent(interestRateField);

		JLabel currenciesTitle = new JLabel("Available Currencies");
		currenciesTitle.setBounds(10, 113, 123, 14);
		this.addComponent(currenciesTitle);

		currencies = new JList<String>();
		currencies.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = processAvailableCurrencies();

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
		currencies.setBounds(10, 142, 123, 137);

		ScrollPane currenciesContainer = new ScrollPane();
		currenciesContainer.add(currencies);
		currenciesContainer.setBounds(10, 142, 123, 137);
		this.addComponent(currenciesContainer);

		JLabel errorMessageField = new JLabel("" + errorMessage);
		errorMessageField.setBounds(10, 285, 311, 14);
		this.addComponent(errorMessageField);

		JButton requestBtn = new JButton("Request");
		requestBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		requestBtn.setBounds(10, 305, 123, 29);
		requestBtn.setAction(actions.get(0));
		this.addComponent(requestBtn);

		JButton backBtn = new JButton("Back");
		backBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		backBtn.setBounds(207, 305, 123, 29);
		backBtn.setAction(actions.get(1));
		this.addComponent(backBtn);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public String getAmount() {
		return this.amountField.getText();
	}

	public String getCollateral() {
		return this.collateralField.getText();
	}

	public String getInterestRate() {
		return this.interestRateField.getText();
	}

	public String getSelectedCurrency() {
		return this.currencies.getSelectedValue();
	}

	/* ============== */
	/* Helper Methods */
	/* ============== */

	/*
	 * Returns a formatted String Array to display all the available currencies.
	 */
	public String[] processAvailableCurrencies() {
		String[] formattedValues = new String[availableCurrencies.size()];
		for (int i = 0; i < availableCurrencies.size(); i++)
			formattedValues[i] = "- " + availableCurrencies.get(i).getSymbol();

		return formattedValues;
	}

}
