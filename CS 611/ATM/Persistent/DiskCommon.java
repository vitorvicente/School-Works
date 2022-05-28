package Persistent;

import java.io.File;
import java.util.HashMap;

public class DiskCommon {

	/*
	 * All possible Tables in the DB.
	 */
	public enum MyData {
		ACCOUNT_TABLE, TRANSACTION_TABLE, CLIENT_TABLE, MANAGER_TABLE, STOCK_TABLE, LOAN_TABLE, STOCK_MARKET_OBJECT,
	}

	/*
	 * Static configurations for DB Files.
	 */
	private static final String DIR_DATA = "data/";
	private static final String EXTENSION = ".ser";
	private static final HashMap<MyData, String> DATA_PATH_MAP = new HashMap<>();

	static {
		for (MyData myData : MyData.values()) {
			DATA_PATH_MAP.put(myData, DIR_DATA + myData.name() + EXTENSION);
		}
	}

	/*
	 * Get the path for a specific DB Table.
	 */
	public static String getPath(MyData myData) {
		return DATA_PATH_MAP.get(myData);
	}

	/*
	 * Checks if a file exists.
	 */
	public static boolean isFileExist(String path) {
		return new File(path).exists();
	}
}
