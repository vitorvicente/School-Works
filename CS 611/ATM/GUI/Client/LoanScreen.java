package GUI.Client;

import java.awt.Font;
import java.awt.ScrollPane;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

import GUI.Abstraction.BankAction;
import GUI.Abstraction.BankFrame;
import Miscellaneous.Loan;

import java.util.ArrayList;
import java.util.Arrays;

public class LoanScreen extends BankFrame {

	private static final long serialVersionUID = 1L;

	private ArrayList<Loan> loanList;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public LoanScreen(ArrayList<Loan> loanList) {
		super(100, 100, 536, 600);
		this.loanList = loanList;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method builds the actual Client Loan GUI Screen with all its
	 * requirements.
	 */
	@Override
	public void initialize(ArrayList<BankAction> actions, String errorMessage) {
		ArrayList<String[]> formattedValues = processClientLoans();

		JLabel title = new JLabel("Client Loans");
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title.setBounds(10, 11, 414, 35);
		this.addComponent(title);

		JLabel pendingLoansTitle = new JLabel("Pending Loan Requests");
		pendingLoansTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		pendingLoansTitle.setBounds(10, 57, 154, 14);
		this.addComponent(pendingLoansTitle);

		JList<String> pendingLoans = new JList<String>();
		pendingLoans.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = formattedValues.get(1);

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

		JLabel activeLoansTitle = new JLabel("Active Loans");
		activeLoansTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		activeLoansTitle.setBounds(10, 250, 154, 14);
		this.addComponent(activeLoansTitle);

		JList<String> activeLoans = new JList<String>();
		activeLoans.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = formattedValues.get(0);

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
		activeLoans.setBounds(10, 275, 500, 144);

		ScrollPane activeLoansContainer = new ScrollPane();
		activeLoansContainer.add(activeLoans);
		activeLoansContainer.setBounds(10, 275, 500, 144);
		this.addComponent(activeLoansContainer);

		JLabel errorMessageField = new JLabel("" + errorMessage);
		errorMessageField.setFont(new Font("Tahoma", Font.BOLD, 11));
		errorMessageField.setBounds(10, 440, 479, 14);
		this.addComponent(errorMessageField);

		JButton requestLoanBtn = new JButton("Request Loan");
		requestLoanBtn.setAction(actions.get(0));
		requestLoanBtn.setBounds(338, 477, 172, 23);
		this.addComponent(requestLoanBtn);

		JButton payLoanBtn = new JButton("Pay Loan");
		payLoanBtn.setBounds(10, 477, 172, 23);
		payLoanBtn.setAction(actions.get(1));
		this.addComponent(payLoanBtn);

		JButton backBtn = new JButton("Back");
		backBtn.setBounds(421, 527, 89, 23);
		backBtn.setAction(actions.get(2));
		this.addComponent(backBtn);
	}

	/* ============== */
	/* Helper Methods */
	/* ============== */

	/*
	 * Returns a set of formatted String Arrays to display all the loans the Client
	 * is currently involved with.
	 */
	@SuppressWarnings("deprecation")
	public ArrayList<String[]> processClientLoans() {
		ArrayList<String> scrapValuesActive = new ArrayList<String>();
		ArrayList<String> scrapValuesPending = new ArrayList<String>();
		for (Loan l : loanList) {
			int percentageRate = (int) Math.round(100 * l.getInterestRate());
			String s = "- Loan #" + l.getID() + " | Currency: " + l.getCurrency() + " | Amount: " + l.getValue()
					+ " | Interest Rate: " + percentageRate + "%";
			if (l.isAccepted())
				scrapValuesActive.add(s);
			else
				scrapValuesPending.add(s);
		}

		String[] formattedValuesActive = new String[scrapValuesActive.size()];
		for (int i = 0; i < scrapValuesActive.size(); i++)
			formattedValuesActive[i] = scrapValuesActive.get(i);

		String[] formattedValuesPending = new String[scrapValuesPending.size()];
		for (int i = 0; i < scrapValuesPending.size(); i++)
			formattedValuesPending[i] = scrapValuesPending.get(i);

		return new ArrayList<String[]>(Arrays.asList(formattedValuesActive, formattedValuesPending));
	}

}
