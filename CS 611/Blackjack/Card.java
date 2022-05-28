public class Card {
	/*
	 * Static final list of possible suits and faces
	 */
	public static final String[] AVAILABLE_SUITS = new String[] { "clubs", "diamonds", "hearts", "spades" };
	public static final String[] AVAILABLE_FACES = new String[] { "ace", "king", "queen", "jack" };

	private String suit;
	private int pip;
	private boolean isFace;
	private String face;

	/* =================== */
	/* Constructor Methods */
	/* =================== */
	
	public Card(String suit, int pip) {
		this.setSuit(suit);
		this.setPip(pip);
		this.setIsFace(false);
	}

	public Card(String suit, String face) {
		this.setSuit(suit);
		this.setFace(face);
		this.setIsFace(true);
	}

	public Card() {
		this.getRandomSuit();
		this.getRandomPip();
	}
	
	/* ================== */
	/* Randomizer Methods */
	/* ================== */

	private void getRandomSuit() {
		int suit = 1 + (int) (Math.random() * ((4 - 1) + 1));

		switch (suit) {
		case 1:
			this.setSuit("clubs");
			break;
		case 2:
			this.setSuit("diamonds");
			break;
		case 3:
			this.setSuit("hearts");
			break;
		case 4:
			this.setSuit("spades");
			break;
		default:
			break;
		}
	}

	private void getRandomPip() {
		int pip = 1 + (int) (Math.random() * ((13 - 1) + 1));

		switch (pip) {
		case 1:
			this.setFace("ace");
			this.setIsFace(true);
			break;
		case 11:
			this.setFace("jack");
			this.setIsFace(true);
			break;
		case 12:
			this.setFace("queen");
			this.setIsFace(true);
			break;
		case 13:
			this.setFace("king");
			this.setIsFace(true);
			break;
		default:
			this.setPip(pip);
			this.setIsFace(false);
			break;
		}
	}
	
	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */

	public void setPip(int pip) {
		if (pip < 2 || pip > 10) {
			System.out.println("Invalid Pip value provided! Values only allowed between 2 and 10!");
		}

		this.pip = pip;
	}

	public void setIsFace(boolean isFace) {
		this.isFace = isFace;
	}

	public void setFace(String face) {
		boolean valid = false;

		for (String f : Card.AVAILABLE_FACES) {
			if (f.equalsIgnoreCase(face))
				valid = true;
		}

		if (!valid) {
			System.out.println("Invalid Face value provided! Only values allowed are: 'ace', 'king', 'queen', 'jack'");
			return;
		}

		this.face = face;
	}

	public void setSuit(String suit) {
		boolean valid = false;

		for (String s : Card.AVAILABLE_SUITS) {
			if (s.equalsIgnoreCase(suit))
				valid = true;
		}

		if (!valid) {
			System.out.println(
					"Invalid Suit value provided! Only values allowed are: 'clubs', 'diamonds', 'hearts', 'spades'");
			return;
		}

		this.suit = suit;
	}

	public String getSuit() {
		return this.suit;
	}

	public String getFace() {
		return this.face;
	}

	public boolean getIsFace() {
		return this.isFace;
	}

	public int getPip() {
		return this.pip;
	}
	
	/* =========== */
	/* Aux Methods */
	/* =========== */
	
	public boolean equals(Card c) {
		if (this.getIsFace() != c.getIsFace()) {
			return false;
		} else if (this.getIsFace()) {
			return this.getFace().equalsIgnoreCase(c.getFace()) && this.getSuit().equalsIgnoreCase(c.getSuit());
		} else {
			return this.getSuit().equalsIgnoreCase(c.getSuit()) && this.getPip() == c.getPip();
		}
	}
	
	public String toString() {
		if (this.getIsFace())
			return this.getFace() + " of " + this.getSuit();
		else
			return String.valueOf(this.getPip()) + " of " + this.getSuit();
	}
}
