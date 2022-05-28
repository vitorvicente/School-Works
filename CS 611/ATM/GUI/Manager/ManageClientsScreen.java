package GUI.Manager;

import java.awt.Font;
import java.awt.ScrollPane;
import java.util.ArrayList;

import javax.swing.JLabel;

import GUI.Abstraction.BankAction;
import GUI.Abstraction.BankFrame;
import Users.Client;

import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.AbstractListModel;

public class ManageClientsScreen extends BankFrame {

	private static final long serialVersionUID = 1L;

	private JList<String> clients;
	private ArrayList<Client> clientList;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public ManageClientsScreen(ArrayList<Client> clientList) {
		super(100, 100, 450, 418);
		this.clientList = clientList;
	}

	/* =========== */
	/* GUI Methods */
	/* =========== */

	/*
	 * This method builds the actual Manage Clients GUI Screen with all its
	 * requirements.
	 */
	@Override
	public void initialize(ArrayList<BankAction> actions, String errorMessage) {
		JLabel title = new JLabel("Client Control Panel");
		title.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title.setBounds(10, 11, 414, 35);
		this.addComponent(title);

		JLabel clientsTitle = new JLabel("Clients");
		clientsTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		clientsTitle.setBounds(10, 57, 123, 14);
		this.addComponent(clientsTitle);

		clients = new JList<String>();
		clients.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = processClients();

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
		clients.setBounds(10, 82, 414, 168);

		ScrollPane clientsContainer = new ScrollPane();
		clientsContainer.add(clients);
		clientsContainer.setBounds(10, 82, 414, 168);
		this.addComponent(clientsContainer);

		JLabel errorMessageField = new JLabel("" + errorMessage);
		errorMessageField.setFont(new Font("Tahoma", Font.BOLD, 12));
		errorMessageField.setBounds(10, 265, 123, 14);
		this.addComponent(errorMessageField);

		JButton clientInfoBtn = new JButton("Client Info");
		clientInfoBtn.setBounds(10, 298, 128, 23);
		clientInfoBtn.setAction(actions.get(0));
		this.addComponent(clientInfoBtn);

		JButton backBtn = new JButton("Back");
		backBtn.setBounds(321, 345, 103, 23);
		backBtn.setAction(actions.get(1));
		this.addComponent(backBtn);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public String getSelectedClient() {
		return this.clients.getSelectedValue();
	}

	/* ============== */
	/* Helper Methods */
	/* ============== */

	/*
	 * Returns a formatted String Array to display all the Clients the Bank has.
	 */
	public String[] processClients() {
		ArrayList<String> scrapValues = new ArrayList<String>();

		for (Client client : clientList) {
			String s = "- Client Username: " + client.getUsername() + " | Name: " + client.getFullName();
			scrapValues.add(s);
		}

		String[] formattedValues = new String[scrapValues.size()];
		for (int i = 0; i < scrapValues.size(); i++)
			formattedValues[i] = scrapValues.get(i);

		return formattedValues;
	}

}
