package Persistent;

public interface Diskable {

	/* ================= */
	/* Interface Methods */
	/* ================= */

	void loadDataFromDisk();

	void flushDataOntoDisk();
	
}
