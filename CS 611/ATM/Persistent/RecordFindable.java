package Persistent;

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

public interface RecordFindable {

	/* ================= */
	/* Interface Methods */
	/* ================= */

	Checking getAccountChecking(long id);

	Saving getAccountSaving(long id);

	Securities getAccountSecurities(long id);

	B2C getTransactionB2C(long id);

	B2N getTransactionB2N(long id);

	C2B getTransactionC2B(long id);

	C2C getTransactionC2C(long id);

	C2N getTransactionC2N(long id);

	N2B getTransactionN2B(long id);

	N2C getTransactionN2C(long id);

	Client getClient(long id);

	Manager getManager(long id);

	Stock getStock(long id);

	Loan getLoan(long id);

}
