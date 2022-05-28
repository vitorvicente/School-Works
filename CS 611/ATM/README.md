# CS611 Final Project - A Bank!

 Compile Instructions: javac \*.java (in the main folder)
 Running Instructions: java Main.java (in the main folder)
 
This is a bank application with a GUI component. As a team, we first considered the Object-Oriented Design of the Bank application first, then considered how to integrate GUI into the design that we agreed on.

# Project Class Structure

- Main.java - Main access class to the Project	
- /Accounts/
	- Account.java - Account Abstract Class, sets up the basic interface shared by all accounts
	- Checking.java - Checking Account Class (extends Account Abstract Class), sets up and holds the Checking Account object with all its requirements
	- Saving.java - Savings Account Class (extends Account Abstract Class), sets up and holds the Savings Account object with all its requirements
	- Securities.java - Securities Account Class (extends Account Abstract Class), sets up and holds the Securities Account object with all its requirements
- /Bank/
	- Bank.java - Bank Object Class, holds and sets up all the requirements for the Bank
- /Currency/
	- Currency.java - Base Currency Class, sets up all Currency Objects and their requirements
	- /Types/
		- Dirham.java - Instance of Dirham Currency (extends Currency Class)
		- Dollar.java - Instance of Dollar Currency (extends Currency Class)
		- Euro.java - Instance of Euro Currency (extends Currency Class)
		- Franc.java - Instance of Franc Currency (extends Currency Class)
		- Lira.java - Instance of Lira Currency (extends Currency Class)
		- Peso.java - Instance of Peso Currency (extends Currency Class)
		- Pound.java - Instance of Pound Currency (extends Currency Class)
		- Ruble.java - Instance of Ruble Currency (extends Currency Class)
		- Won.java - Instance of Won Currency (extends Currency Class)
		- Yen.java - Instance of Yen Currency (extends Currency Class)
		- Yuan.java - Instance of Yuan Currency (extends Currency Class)
- /GUI/
	- BankGUI.java - Main Bank GUI Class, sets up the basis for the GUI, and initiates it on the Login Page
	- /Abstraction/
		- BankAction.java - Abstract BankAction Class, sets up the absolute basis of all AbstractActions (ie: GUI Actions) used by the GUI
		- BankFrame.java - Abstract BankFrame Class, sets up the absolute basis of all the JFrame's (ie: all the GUI Screen) used by the GUI
	- /Client/
		- AccountScreen.java - Client All Personal Accounts GUI Screen Class (extends BankFrame)
		- BuyStockScreen.java - Client Buy Stock GUI Screen Class (extends BankFrame)
		- CheckingAccountScreen.java - Client Checking Account GUI Screen Class (extends BankFrame)
		- ClientMainScreen.java - Client Main GUI Screen Class (extends BankFrame)
		- EditBasicInfoScreen.java - Client Edit Own Basic Info GUI Screen Class (extends BankFrame)
		- LoanScreen.java - Client Own Loan Management GUI Screen Class (extends BankFrame)
		- PayLoanScreen.java - Client Pay Loan GUI Screen Class (extends BankFrame)
		- RequestLoanScreen.java - Client Request Loan GUI Screen Class (extends BankFrame)
		- SavingsAccountScreen.java - Client Savings Account GUI Screen Class (extends BankFrame)
		- SecuritiesAccountScreen.java - Client Securities Account GUI Screen Class (extends BankFrame)
		- SellStockScreen.java - Client Sell Stock GUI Screen Class (extends BankFrame)
		- TransactionScreen.java - Client Transactions Overview GUI Screen Class (extends BankFrame)
		- /Actions/
			- BuyStock.java - Client Buy Stock GUI Action Class (extends BankAction)
			- PayLoan.java - Client Pay Loan GUI Action Class (extends BankAction)
			- RequestLoan.java - Client Request Loan GUI Action Class (extends BankAction)
			- SaveEditedInfo.java - Client Save Edited Info GUI Action Class (extends BankAction)
			- SellStock.java - Client Sell Stock GUI Action Class (extends BankAction)
			- /Navigation/
				- GoToAccounts.java - Move to AccountScreen GUI Action Class (extends BankAction)
				- GoToBuyStock.java - Move to BuyStockScreen GUI Action Class (extends BankAction)
				- GoToChecking.java - Move to CheckingAccountScreen GUI Action Class (extends BankAction)
				- GoToClientMain.java - Move to ClientMainScreen GUI Action Class (extends BankAction)
				- GoToEditInfo.java - Move to Client EditBasicInfoScreen GUI Action Class (extends BankAction)
				- GoToLoans.java - Move to LoanScreen GUI Action Class (extends BankAction)
				- GoToPayLoan.java - Move to PayLoanScreen GUI Action Class (extends BankAction)
				- GoToRequestLoan.java - Move to RequestLoanScreen GUI Action Class (extends BankAction)
				- GoToSavings.java - Move to SavingsAccountScreen GUI Action Class (extends BankAction)
				- GoToSecurities.java - Move to SecuritiesAccountScreen GUI Action Class (extends BankAction)
				- GoToSellStock.java - Move to SellStockScreen GUI Action Class (extends BankAction)
				- GoToTransactions.java - Move to Client TransactionScreen GUI Action Class (extends BankAction)
	- /General/
		- DepositScreen.java - General Money Deposit GUI Screen Class (extends BankFrame)
		- LoginScreen.java - General User Login GUI Screen Class (extends BankFrame)
		- RegisterScreen.java - General User Registration GUI Screen Class (extends BankFrame)
		- TransferScreen.java - General Money Transfer GUI Screen Class (extends BankFrame)
		- WithdrawScreen.java - General Money Withdraw GUI Screen Class (extends BankFrame)
		- /Actions/
			- DepositMoney.java - User Deposit Money GUI Action Class (extends BankAction)
			- Login.java - User Login GUI Action Class (extends BankAction)
			- RegisterAccount.java - User Registration GUI Action Class (extends BankAction)
			- TransferMoney.java - User Transfer Money GUI Action Class (extends BankAction)
			- WithdrawMoney.java - User Withdraw Money GUI Action Class (extends BankAction)
			- /Navigation/
				- GoToDeposit.java - Move to DepositScreen GUI Action Class (extends BankAction)
				- GoToLogin.java - Move to LoginScreen GUI Action Class (extends BankAction)
				- GoToRegister.java - Move to RegisterScreen GUI Action Class (extends BankAction)
				- GoToTransfer.java - Move to TransactionScreen GUI Action Class (extends BankAction)
				- GoToWithdraw.java - Move to WithdrawScreen GUI Action Class (extends BankAction)
	- /Manager/
		- AddStockScreen.java - Manager Add Stock GUI Screen Class (extends BankFrame)
		- ClientInfoScreen.java - Manager Specific Client Info GUI Screen Class (extends BankFrame)
		- EditBasicInfoScreen.java - Manager Edit Own Basic Info GUI Screen Class (extends BankFrame)
		- EditStockScreen.java - Manager Edit Stock Info GUI Screen Class (extends BankFrame)
		- ManageClientsScreen.java - Manager Manage All Clients GUI Screen Class (extends BankFrame)
		- ManageLoanRequestScreen.java - Manager Manage All Loan Requests GUI Screen Class (extends BankFrame)
		- ManageLoansScreen.java - Manager Manage All Loans GUI Screen Class (extends BankFrame)
		- ManagerMainScreen.java - Manager Main GUI Screen Class (extends BankFrame)
		- ManageStockScreen.java - Manager Manage All Stocks GUI Screen Class (extends BankFrame)
		- OlderTransactionsScreen.java - Manager View All Older Transactions GUI Screen Class (extends BankFrame)
		- RemoveStockScreen.java - Manager Remove Stock GUI Screen Class (extends BankFrame)
		- TransactionScreen.java - Manager View All New Transactions GUI Screen Class (extends BankFrame)
		- /Actions/
			- AcceptLoanRequest.java - Manager Accept Loan Request GUI Action Class (extends BankAction)
			- AddStock.java - Manager Add Stock GUI Action Class (extends BankAction)
			- CloseClientAccount.java - Manager Close Specific Client Account GUI Action Class (extends BankAction)
			- EditStock.java - Manager Edit Stock GUI Action Class (extends BankAction)
			- RemoveStock.java - Manager Remove Stock GUI Action Class (extends BankAction)
			- SaveEditedInfo.java - Manager Save Own Edited Info GUI Action Class (extends BankAction)
			- /Navigation/
				- GoToAddStock.java - Move to AddStockScreen GUI Action Class (extends BankAction)
				- GoToClientInfo.java - Move to ClientInfoScreen GUI Action Class (extends BankAction)
				- GoToEditInfo.java - Move to Manager EditBasicInfoScreen GUI Action Class (extends BankAction)
				- GoToEditStock.java - Move to EditStockScreen GUI Action Class (extends BankAction)
				- GoToLoanRequest.java - Move to ManageLoanRequestScreen GUI Action Class (extends BankAction)
				- GoToManageClients.java - Move to ManageClientsScreen GUI Action Class (extends BankAction)
				- GoToManageLoans.java - Move to ManageLoansScreen GUI Action Class (extends BankAction)
				- GoToManageStock.java - Move to ManageStockScreen GUI Action Class (extends BankAction)
				- GoToManagerMain.java - Move to ManagerMainScreen GUI Action Class (extends BankAction)
				- GoToOlderTransactions.java - Move to OlderTransactionsScreen GUI Action Class (extends BankAction)
				- GoToRemoveStock.java - Move to RemoveStockScreen GUI Action Class (extends BankAction)
				- GoToTransactions.java - Move to Manager TransactionScreen GUI Action Class (extends BankAction)
- /Miscellaneous/
	- CurrencyWallet.java - CurrencyWallet Object Class, holds and maintains a wallet with the amount of difference currencies that a certain account has
	- Loan.java - Loan Object Class, holds and maintains all the requirements to keep up a Loan between a Client and the Bank
	- Stock.java - Stock Object Class, holds and maintains all the requirements to keep a Stock Object for trading
	- StockMarket.java - StockMarket Object Class, holds and maintains all the currently available Stocks and their Prices
	- StockWallet.java - StockWallet Object Class, holds and maintains a wallet with the amount of different stocks that a certain Client has
- /Persistent/
	- Database.java - Main Database Object Class, holds and maintains all the required methods and data to allow persistent behaviour of data (implements Diskable, RecordCreatable, RecordFindable, RecordRemovable)
	- DiskCommon.java - Main DiskCommon Object Class, holds and maintains all the static requirements and methods to deal with file storage
	- Diskable.java - Diskable Interface, setups the basis of all "Diskable" classes (ie: all classes that save/load from the Disk)
	- RecordCreatable.java - RecordCreatable Interface, sets up the basis of all "RecordCreatable" classes (ie: all classes that can create Objects from specific records)
	- RecordFindable.java - RecordFindable Interface, sets up the basis of all "RecordFindable" classes (ie: all classes that are able to find a specifc Object somewhere)
	- RecordRemovable.java - RecordRemovable Interface, sets up the basis of all "RecordRemovable" classes (ie: all classes that can remove certain Objects from specifc records)
	- Table.java - Table Object Class, holds and maintains the basic requirements of a generalized Table for a type of Object (used in DB to set up the different storage Tables)
	- Traceable.java - Traceable Interface, sets up the basic requirements for all objects that are Traceable (ie: all objects that have an ID to be used)
- /Transactions/
	- Transaction.java - Transaction Abstract Class, holds and maintains all the basics shared by all kinds of Transactions
	- /Types/
		- B2C.java - Bank to Client Transaction Object (extends Transaction Abstract Class), holds and maintains all the required information for Bank to Client Transactions
		- B2N.java - Bank to Nothing Transaction Object (extends Transaction Abstract Class), holds and maintains all the required information for Bank to a Third Party Transactions
		- C2B.java - Client to Bank Transaction Object (extends Transaction Abstract Class), holds and maintains all the required information for Client to Bank Transactions
		- C2C.java - Client to Client Transaction Object (extends Transaction Abstract Class), holds and maintains all the required information for Client to Client Transactions
		- C2N.java - Client to Nothing Transaction Object (extends Transaction Abstract Class), holds and maintains all the required information for Client to Third Party Transactions
		- N2B.java - Nothing to Bank Transaction Object (extends Transaction Abstract Class), holds and maintains all the required information for Third Party to Bank Transactions
		- N2C.java - Nothing to Client Transaction Object (extends Transaction Abstract Class), holds and maintains all the required information for Third Party to Bank Transactions
- /Users/
	- Client.java - Client Object Class (extends User Abstract Class), holds and maintains the requirements for every Client of the Bank
	- Manager.java - Manager Object Class (extends User Abstract Class), holds and maintains the requirements for every Manager of the Bank
	- User.java - User Abstract Class, holds and maintains all the basics shared by all kinds of Users of the Bank

## Users

To implement the active human entities, we first established the **User** object, which is an abstract class that the Client and Manager should extend, together these three classes are the backbone of all Entities that exist in our Banking system.

## Wallets 
We have two types of wallets, a **CurrencyWallet** and a **StockWallet**. Because each wallet serves entirely different purposes and have different states/methods, there was no place for class inheritance/interface implementation. Although both wallets do end up serving as a "storage" Object for whatever it is they are storing, thus making our life easier to deal with trades/transfers/etc...

The **CurrencyWallet** has a HashMap that maps a String (symbol of currency) to an Integer (amount of currency the wallet has), with appropriate methods to manipulate the state of the currencies.

The **StockWallet** has 2 HashMaps. Both maps a String (name of stock) to an ArrayList of stocks (tracks # of stocks). The first, currentStocks, tracks the current stocks in the wallet, while the second, soldStocks, tracks the stocks sold and no longer in the wallet. This class also has the appropriate setters/getters, as well as a method to calculate the unrealized profits for a specific Stock.

## Bank Accounts
Initially, we had it that there was an **Account** interface, that the different account types would implement, however due to an issue with the Serializable class, we ended up having to switch the Interface for an Abstract class, which does the same job, of establishing the basic methods all Account types share, we then proceeded to create an object for each type: Savings, Checking, Securities. Each has a CurrencyWallet, and the Securities account also has a StockWallet. They each also implement the methods of the Abstract Class, and have their own getters/setters.

## Stock Market

We chose to have a class to represent a **Stock** object. In it, it has a selling price, buying price, and the name of the stock, as well as appropriate getters/setters. It should be noted that both the Buying and Selling price are only set when a Client buys or sells the Stock, the actual current value of the Stock is stored elsewhere.

With that established, we moved forward to create an object to represent the **StockMarket**. In this class, we have a HashMap that maps a Stock to its current cost. It has methods to check the price of a certain stock, add/remove stocks, change the price of said stock, and appropriate setter/getters. This class effectively regulates all the trading and is therefore quite centra/

## Loan
For the **Loan** object, we have several members: **long ID, String currency, int value, long clientId, String collateral, double interestRate, boolean accepted, Client client, and Date lastInterestDate**. With these fields, we allow for a Client to request a loan, which will fill all of them in except lastInterestDate, and will set accepted to false. This will notify the Manager, which can then change the interestRate and accept the loan, thus initializing the lastInterestDate, and switching accepted to true.

This system allows for us to keep track of all the required information about a specific Loan, as well as add a little bit of back and forward so that a Loan isn't automatically accepted, and rather must be negotiated.

## Bank

Finally, to put it altogether, we have the bank object! This is the main object behind the entire project. 

It keeps track of a few static configurations: **AVAILABLE_CURRENCIES, TRANSACTION_FEE, MINIMUM_TO_GET_SECURITIES, MINIMUM_IN_SAVINGS** and **MANAGER_ID, MANAGER_USERNAME, MANAGER_PASSWORD, MANAGER_FULL_NAME, GLOBAL_ACCOUNT_ID**. The first few of these allow us to configure some behavioural aspects of the bank, from what Currencies Clients can use, to the fees charged on any service, to the requirements to allow a Client to Trade. The second set is a bit more complicated, but it basically sets up the defaults for the Manager Account in case none exists, this allows for the program to be loaded without any prior data without it messing up its functionality.

The Bank Object itself keeps track of both its Manager, and the so called **globalAccount**, which is just the Checking Account attributed to the Manager, so they might know how much money they have made. After this, the Bank keeps track of all Clients, Accounts, and Loans that exist, keeping them here instead under other Objects allows for easier access, and DB relation. Finally the Bank also keeps track of the global Stock Market that all Users share.

The Bank Class itself includes a few getters and setters, a method to load all Data from the DB, and a method to initiate the Bank GUI.

## Graphical User Interface
We discovered pretty early on that Eclipse has a designer for Java GUIs, so we used that to the best of our advantage. Our GUI is broken down into 2 main abstractions: an action and a frame. Every BankFrame object holds nothing but the static requirements for that GUI Screen, thus there are _no_ dynamic methods to preform actions, rather the objects simply display the information required in a GUI Screen, and take in an ArrayList of BankActions to apply to the buttons/components in the GUI.

BankActions are split into two kinds: Actual Actions, and Navigation Actions. Navigation Actions are the easier to explain, all they do is load any necessary data, and navigate to a specific Screen. Actual Actions are the more complicated, as these are responsible for all dynamic actions in the GUI, such as Account Creation, Money Transfering, etc... They each use the required AsbtractAction methods from Java Swift, and have error checking built in. Without these Actions the GUI would be purely Static.

There are a lot of screens and actions that are written, so it will take forever to go through every single one. Luckily, the name of the file tells you what it does, or you might look at the class detailing above for a short description.

With all the screens and actions, we have the initial **BankGUI** class initialize the log in screen to initialize the GUI.

## Persistent
As was required, our application has Data Presistence, which means that whenever it is closed and opened again, it will maintain all the Data regarding, well, everything. We do this by creating a Database Object that works in Singleton mode, allowing us to grab the DB instance, and all the data it comes with.

The DB object itself implements quite a few different Interfaces, which allow it to create the Objects, find these Objects in its tables, and remove the Objects from the record. The DB object also includes methods to read from the serialized files, and de-serialize the objects there, so they might be used by the application. Furthermore, it includes methods to do the reverse, serialize every Object and save it in a file. When in use, all information is stored in Table objects, which keep track of every piece of data.

This implementation allows us to fetch and store data at the start and at the end of the application, and use the loaded data from an Object in the mean time. Although slightly different from the standard of constantly fetching data from the DB, it does make this application much cleaner and easier to deal with.