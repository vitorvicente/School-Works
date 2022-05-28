package GUI.General;

import java.awt.Font;
import java.awt.ScrollPane;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

import Accounts.Checking;
import GUI.Abstraction.BankAction;
import GUI.Abstraction.BankFrame;

public class WithdrawScreen extends BankFrame {

	private static final long serialVersionUID = 1L;

	private JTextField amountField;
	private JList<String> currencies;

	private Checking checkingAccount;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public WithdrawScreen(Checking checkingAccount) {
		super(100, 100, 341, 366);
		this.checkingAccount = checkingAccount;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method builds the actual Withdraw Money GUI Screen with all its
	 * requirements.
	 */
	@Override
	public void initialize(ArrayList<BankAction> actions, String errorMessage) {
		JLabel title = new JLabel("Withdraw Money");
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title.setBounds(10, 11, 309, 35);
		this.addComponent(title);

		JLabel amountTitle = new JLabel("Amount To Withdraw");
		amountTitle.setBounds(170, 57, 129, 14);
		this.addComponent(amountTitle);

		amountField = new JTextField();
		amountField.setColumns(10);
		amountField.setBounds(170, 80, 86, 20);
		this.addComponent(amountField);

		JLabel currenciesTitle = new JLabel("Currency To Withdraw");
		currenciesTitle.setBounds(10, 57, 129, 14);
		this.addComponent(currenciesTitle);

		currencies = new JList<String>();
		currencies.setValueIsAdjusting(true);
		currencies.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = processOwnCurrencies();

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
		currencies.setBounds(10, 82, 129, 160);

		ScrollPane currenciesContainer = new ScrollPane();
		currenciesContainer.add(currencies);
		currenciesContainer.setBounds(10, 82, 129, 160);
		this.addComponent(currenciesContainer);

		JButton depositBtn = new JButton("Withdraw");
		depositBtn.setBounds(170, 219, 129, 23);
		depositBtn.setAction(actions.get(0));
		this.addComponent(depositBtn);

		JLabel errorMessageField = new JLabel("" + errorMessage);
		errorMessageField.setBounds(10, 262, 305, 14);
		this.addComponent(errorMessageField);

		JButton backBtn = new JButton("Back");
		backBtn.setBounds(221, 293, 94, 23);
		backBtn.setAction(actions.get(1));
		this.addComponent(backBtn);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public String getAmount() {
		return this.amountField.getText();
	}

	public String getSelectedCurrency() {
		return this.currencies.getSelectedValue();
	}

	/* ============== */
	/* Helper Methods */
	/* ============== */

	/*
	 * Returns a formatted String Array to display all the currencies the Client
	 * has.
	 */
	public String[] processOwnCurrencies() {
		ArrayList<String> scrapValues = new ArrayList<String>();
		for (String c : checkingAccount.getWallet().getValues().keySet()) {
			String s = "- " + c;
			scrapValues.add(s);
		}

		String[] formattedValues = new String[scrapValues.size()];
		for (int i = 0; i < scrapValues.size(); i++) {
			formattedValues[i] = scrapValues.get(i);
		}

		return formattedValues;
	}

}
