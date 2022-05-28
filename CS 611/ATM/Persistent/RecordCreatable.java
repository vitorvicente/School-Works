package Persistent;

import Accounts.Account;
import Accounts.Checking;
import Accounts.Saving;
import Accounts.Securities;
import Miscellaneous.Loan;
import Miscellaneous.Stock;
import Transactions.Types.B2C;
import Transactions.Types.B2N;
import Transactions.Types.C2B;
import Transactions.Types.C2C;
import Transactions.Types.C2N;
import Transactions.Types.N2B;
import Transactions.Types.N2C;
import Users.Client;
import Users.Manager;

public interface RecordCreatable {

	/* ================= */
	/* Interface Methods */
	/* ================= */

	Checking createAccountChecking();

	Saving createAccountSaving();

	Securities createAccountSecurities();

	B2C createTransactionB2C(int value, String currency, Account bankAccount, Account clientAccount);

	B2N createTransactionB2N(int value, String currency, Account bankAccount);

	C2B createTransactionC2B(int value, String currency, Account bankAccount, Account clientAccount);

	C2C createTransactionC2C(int value, String currency, Account clientAccountOne, Account clientAccountTwo);

	C2N createTransactionC2N(int value, String currency, Account clientAccount);

	N2B createTransactionN2B(int value, String currency, Account bankAccount);

	N2C createTransactionN2C(int value, String currency, Account clientAccount);

	Client createClient(String username, String password, String fullName, long ssn, String phoneNumber,
			String address);

	Client createClient(String username, String password, String fullName, long ssn, String phoneNumber, String address,
			long checkingAccount, long savingsAccount, long securitiesAccount);

	Manager createManager(String username, String password, String fullName);

	Stock createStock(String companyName);

	Loan createLoan(Client client, String currency, int value, String collateral, double interestRate);

}
