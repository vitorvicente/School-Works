package GUI.Client;

import java.awt.Font;
import java.awt.ScrollPane;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

import GUI.Abstraction.BankAction;
import GUI.Abstraction.BankFrame;
import Miscellaneous.Loan;

import javax.swing.JTextField;

public class PayLoanScreen extends BankFrame {

	private static final long serialVersionUID = 1L;

	private JTextField amountField;
	private JList<String> activeLoans;
	private ArrayList<Loan> loanList;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public PayLoanScreen(ArrayList<Loan> loanList) {
		super(100, 100, 536, 423);
		this.loanList = loanList;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method builds the actual Pay Loan GUI Screen with all its requirements.
	 */
	@Override
	public void initialize(ArrayList<BankAction> actions, String errorMessage) {
		JLabel title = new JLabel("Pay Loan");
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title.setBounds(10, 11, 414, 35);
		this.addComponent(title);

		JLabel amountTitle = new JLabel("Amount to Pay");
		amountTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		amountTitle.setBounds(10, 250, 154, 14);
		this.addComponent(amountTitle);

		amountField = new JTextField();
		amountField.setBounds(10, 275, 200, 20);
		this.addComponent(amountField);
		amountField.setColumns(10);

		JLabel activeLoansTitle = new JLabel("Active Loans");
		activeLoansTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		activeLoansTitle.setBounds(10, 57, 154, 14);
		this.addComponent(activeLoansTitle);

		activeLoans = new JList<String>();
		activeLoans.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = processActiveLoans();

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
		activeLoans.setBounds(10, 82, 479, 144);

		ScrollPane activeLoansContainer = new ScrollPane();
		activeLoansContainer.add(activeLoans);
		activeLoansContainer.setBounds(10, 82, 479, 144);
		this.addComponent(activeLoansContainer);

		JLabel errorMessageField = new JLabel("" + errorMessage);
		errorMessageField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		errorMessageField.setBounds(10, 317, 154, 14);
		this.addComponent(errorMessageField);

		JButton payLoanBtn = new JButton("Pay Loan");
		payLoanBtn.setBounds(10, 350, 172, 23);
		payLoanBtn.setAction(actions.get(0));
		this.addComponent(payLoanBtn);

		JButton backBtn = new JButton("Back");
		backBtn.setBounds(338, 350, 172, 23);
		backBtn.setAction(actions.get(1));
		this.addComponent(backBtn);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public String getSelectedLoan() {
		return this.activeLoans.getSelectedValue();
	}

	public String getAmount() {
		return this.amountField.getText();
	}

	/* ============== */
	/* Helper Methods */
	/* ============== */

	/*
	 * Returns a formatted String Array to display all the active Loans the client
	 * is involved with.
	 */
	@SuppressWarnings("deprecation")
	public String[] processActiveLoans() {
		ArrayList<String> scrapValuesActive = new ArrayList<String>();
		for (Loan l : loanList) {
			int percentageRate = (int) Math.round(100 * l.getInterestRate());
			String s = "- Loan #" + l.getID() + " | Currency: " + l.getCurrency() + " | Amount: " + l.getValue()
					+ " | Interest Rate: " + percentageRate + "%";
			if (l.isAccepted())
				scrapValuesActive.add(s);
		}

		String[] formattedValuesActive = new String[scrapValuesActive.size()];
		for (int i = 0; i < scrapValuesActive.size(); i++)
			formattedValuesActive[i] = scrapValuesActive.get(i);

		return formattedValuesActive;
	}

}
