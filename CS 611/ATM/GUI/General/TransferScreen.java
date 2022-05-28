package GUI.General;

import Accounts.Account;
import GUI.Abstraction.BankAction;
import GUI.Abstraction.BankFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.ScrollPane;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.AbstractListModel;

public class TransferScreen extends BankFrame {

	private static final long serialVersionUID = 1L;

	private JTextField amountField;
	private JTextField targetField;
	private JList<String> currencies;
	private Account account;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public TransferScreen(Account account) {
		super(100, 100, 391, 348);
		this.account = account;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method builds the actual Transfer Money GUI Screen with all its
	 * requirements.
	 */
	@Override
	public void initialize(ArrayList<BankAction> actions, String errorMessage) {
		JLabel title = new JLabel("Transfer Money");
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title.setBounds(10, 11, 309, 35);
		this.addComponent(title);

		JLabel errorMessageField = new JLabel("" + errorMessage);
		errorMessageField.setBounds(10, 57, 309, 14);
		this.addComponent(errorMessageField);

		JLabel currenciesTitle = new JLabel("Currency To Transfer");
		currenciesTitle.setBounds(10, 82, 129, 14);
		this.addComponent(currenciesTitle);

		currencies = new JList<String>();
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
		currencies.setBounds(14, 106, 125, 155);

		ScrollPane currenciesContainer = new ScrollPane();
		currenciesContainer.add(currencies);
		currenciesContainer.setBounds(10, 102, 129, 159);
		this.addComponent(currenciesContainer);

		JLabel amountTitle = new JLabel("Amount To Transfer");
		amountTitle.setBounds(161, 82, 129, 14);
		this.addComponent(amountTitle);

		amountField = new JTextField();
		amountField.setColumns(10);
		amountField.setBounds(161, 102, 86, 20);
		this.addComponent(amountField);

		JLabel targetTitle = new JLabel("Target Account ID");
		targetTitle.setBounds(161, 133, 198, 14);
		this.addComponent(targetTitle);

		targetField = new JTextField();
		targetField.setColumns(10);
		targetField.setBounds(161, 158, 86, 20);
		this.addComponent(targetField);

		JButton trasnferBtn = new JButton("Transfer");
		trasnferBtn.setBounds(161, 189, 129, 23);
		trasnferBtn.setAction(actions.get(0));
		this.addComponent(trasnferBtn);

		JButton backBtn = new JButton("Back");
		backBtn.setBounds(271, 275, 94, 23);
		backBtn.setAction(actions.get(1));
		this.addComponent(backBtn);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public String getSelectedCurrency() {
		return this.currencies.getSelectedValue();
	}

	public String getAmount() {
		return this.amountField.getText();
	}

	public String getTarget() {
		return this.targetField.getText();
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
		for (String c : account.getWallet().getValues().keySet()) {
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
