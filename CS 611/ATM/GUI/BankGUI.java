package GUI;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;

import Bank.Bank;
import GUI.Abstraction.BankAction;
import GUI.General.LoginScreen;
import GUI.General.Actions.Login;
import GUI.General.Actions.Navigation.GoToRegister;
import Persistent.Database;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BankGUI {

	private JFrame frame;
	private Bank bank;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public BankGUI(Bank bank) {
		this.setBank(bank);
		initialize();
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method initializes the entire GUI, loading the Login Screen (which is
	 * the first screen, and creating a listener to look out for the window closing,
	 * in which case it flushes the data onto the Disk to save everything.
	 */
	private void initialize() {
		LoginScreen loginScreen = new LoginScreen();
		loginScreen.initialize(
				new ArrayList<BankAction>(Arrays.asList(new Login(this, loginScreen), new GoToRegister(this))), "");

		frame = loginScreen;
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Database.getInstance().flushDataOntoDisk();
				System.exit(0);
			}
		});
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	/*
	 * This method will consistently be used to switch the visible window in the
	 * GUI, it also listens for closings, in which case it saves the data,
	 */
	public void setFrame(JFrame f) {
		this.toggleVisible();
		frame = f;
		Database.getInstance().flushDataOntoDisk();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Database.getInstance().flushDataOntoDisk();
				super.windowClosing(e);
			}
		});
		this.toggleVisible();
	}

	/*
	 * Toggles the visibility of the GUI.
	 */
	public void toggleVisible() {
		frame.setVisible(!frame.isVisible());
	}

}
