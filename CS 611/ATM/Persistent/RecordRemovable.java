package Persistent;

public interface RecordRemovable {

	/* ================= */
	/* Interface Methods */
	/* ================= */

	boolean removeAccountChecking(long id);

	boolean removeAccountSaving(long id);

	boolean removeAccountSecurities(long id);

	boolean removeTransactionB2C(long id);

	boolean removeTransactionB2N(long id);

	boolean removeTransactionC2B(long id);

	boolean removeTransactionC2C(long id);

	boolean removeTransactionC2N(long id);

	boolean removeTransactionN2B(long id);

	boolean removeTransactionN2C(long id);

	boolean removeClient(long id);

	boolean removeManager(long id);

	boolean removeStock(long id);

	boolean removeLoan(long id);

}
