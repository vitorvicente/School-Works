package GUI.Abstraction;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public abstract class BankFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	/*
	 * This abstract constructor runs some basic operations that are shared by all
	 * GUI Frames.
	 */
	public BankFrame(int x, int y, int width, int height) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(x, y, width, height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}

	/* ================ */
	/* Abstract Methods */
	/* ================ */

	public abstract void initialize(ArrayList<BankAction> actions, String errorMessage);

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public JPanel getContentPane() {
		return this.contentPane;
	}

	public void addComponent(Component component) {
		this.contentPane.add(component);
	}

}
