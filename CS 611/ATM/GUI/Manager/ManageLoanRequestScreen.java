package GUI.Manager;

import java.awt.Font;
import java.awt.ScrollPane;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.JList;

import GUI.Abstraction.BankAction;
import GUI.Abstraction.BankFrame;
import Miscellaneous.Loan;

import javax.swing.JTextField;
import javax.swing.JButton;

public class ManageLoanRequestScreen extends BankFrame {

	private static final long serialVersionUID = 1L;

	private JTextField interestRateField;
	private ArrayList<Loan> loans;
	private JList<String> pendingLoans;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public ManageLoanRequestScreen(ArrayList<Loan> loans) {
		super(100, 100, 536, 403);
		this.loans = loans;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method builds the actual Manage Loan Requests GUI Screen with all its
	 * requirements.
	 */
	@Override
	public void initialize(ArrayList<BankAction> actions, String errorMessage) {
		JLabel title = new JLabel("Loan Requests");
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title.setBounds(10, 11, 414, 35);
		this.addComponent(title);

		JLabel pendingLoansTitle = new JLabel("Pending Loan Requests");
		pendingLoansTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		pendingLoansTitle.setBounds(10, 57, 154, 14);
		this.addComponent(pendingLoansTitle);

		pendingLoans = new JList<String>();
		pendingLoans.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = processLoanRequests();

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
		pendingLoans.setBounds(10, 82, 500, 144);

		ScrollPane pendingLoansContainer = new ScrollPane();
		pendingLoansContainer.add(pendingLoans);
		pendingLoansContainer.setBounds(10, 82, 500, 144);
		this.addComponent(pendingLoansContainer);

		JLabel interestRateTitle = new JLabel("Re-Negotiated Interest Rate");
		interestRateTitle.setBounds(10, 245, 200, 14);
		this.addComponent(interestRateTitle);

		interestRateField = new JTextField();
		interestRateField.setBounds(10, 270, 200, 20);
		this.addComponent(interestRateField);
		interestRateField.setColumns(10);

		JButton acceptLoanBtn = new JButton("Accept Loan Request");
		acceptLoanBtn.setBounds(324, 269, 161, 23);
		acceptLoanBtn.setAction(actions.get(0));
		this.addComponent(acceptLoanBtn);

		JLabel errorMessageField = new JLabel("" + errorMessage);
		errorMessageField.setBounds(10, 306, 500, 14);
		this.addComponent(errorMessageField);

		JButton backBtn = new JButton("Back");
		backBtn.setBounds(398, 330, 112, 23);
		backBtn.setAction(actions.get(1));
		this.addComponent(backBtn);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public String getInterestRate() {
		return this.interestRateField.getText();
	}

	public String getSelectedLoan() {
		return this.pendingLoans.getSelectedValue();
	}

	/* ============== */
	/* Helper Methods */
	/* ============== */

	/*
	 * Returns a formatted String Array to display all Loan Requests the Bank has
	 * active.
	 */
	@SuppressWarnings("deprecation")
	public String[] processLoanRequests() {
		ArrayList<String> scrapValues = new ArrayList<String>();
		for (Loan l : loans) {
			int percentageRate = (int) Math.round(100 * l.getInterestRate());
			String s = "- Loan #" + l.getID() + " | Client: " + l.getClient().getUsername() + " | Currency: "
					+ l.getCurrency() + " | Amount: " + l.getValue() + " | Interest Rate: " + percentageRate + "%";
			if (!l.isAccepted())
				scrapValues.add(s);
		}

		String[] formattedValues = new String[scrapValues.size()];
		for (int i = 0; i < scrapValues.size(); i++)
			formattedValues[i] = scrapValues.get(i);

		return formattedValues;
	}

}
