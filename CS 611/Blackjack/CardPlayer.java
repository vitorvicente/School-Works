public class CardPlayer extends Player {
	private CardHand hand;
	private CardHand handTwo;
	private int balance;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public CardPlayer(int ID) {
		super(ID);
		this.setHand(new CardHand());
		this.setHandTwo(new CardHand());
		this.setBalance(0);
	}

	public CardPlayer(int ID, String name) {
		super(ID, name);
		this.setHand(new CardHand());
		this.setHandTwo(new CardHand());
		this.setBalance(0);
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public void setHand(CardHand hand) {
		this.hand = hand;
	}

	public CardHand getHand() {
		return this.hand;
	}

	public void setHandTwo(CardHand hand) {
		this.handTwo = hand;
	}

	public CardHand getHandTwo() {
		return this.handTwo;
	}

	public void resetBalance() {
		this.balance = 0;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		if (balance != -1 && (balance < 0 || balance > 100000)) {
			System.out.println("Invalid balance value!");
			return;
		}

		this.balance = balance;
	}

	public boolean removeBalance(int remove) {
		if (this.getBalance() - remove <= 0) {
			System.out.println("Not enough balance! Balance remaining: " + this.getBalance());
			return false;
		}

		this.balance -= remove;

		return true;
	}

	public void addBalance(int add) {
		this.balance += add;
	}

	/* =========== */
	/* Aux Methods */
	/* =========== */

	public void resetHands() {
		this.setHand(new CardHand());
		this.setHandTwo(new CardHand());
	}

	public void printHand(boolean hideSome) {

		System.out.println();
		System.out.println(this.getName() + "'s hand:");

		if (hideSome) {
			System.out.println(this.getHand().getHand().get(0) + ", [HIDDEN]");
		} else {
			if (this.getHandTwo().getHand().size() == 0) {
				System.out.println(this.getHand().getHand());
			} else {
				System.out.println("Hand #1: " + this.getHand().getHand());
				System.out.println("Hand #1: " + this.getHandTwo().getHand());
			}
		}
		System.out.println();
	}
}
