import java.util.ArrayList;
import java.util.Arrays;

public class Deck {
	/*
	 * This variable might sound random, but it will actually be of great help on
	 * the creation of decks/checking whether decks are valid.
	 */
	public static final ArrayList<Card> COMPLETE_DECK = new ArrayList<Card>(Arrays.asList(new Card[] {
			new Card("clubs", "ace"), new Card("clubs", "king"), new Card("clubs", "queen"), new Card("clubs", "jack"),
			new Card("clubs", 2), new Card("clubs", 3), new Card("clubs", 4), new Card("clubs", 5),
			new Card("clubs", 6), new Card("clubs", 7), new Card("clubs", 8), new Card("clubs", 9),
			new Card("clubs", 10), new Card("diamonds", "ace"), new Card("diamonds", "king"),
			new Card("diamonds", "queen"), new Card("diamonds", "jack"), new Card("diamonds", 2),
			new Card("diamonds", 3), new Card("diamonds", 4), new Card("diamonds", 5), new Card("diamonds", 6),
			new Card("diamonds", 7), new Card("diamonds", 8), new Card("diamonds", 9), new Card("diamonds", 10),
			new Card("spades", "ace"), new Card("spades", "king"), new Card("spades", "queen"),
			new Card("spades", "jack"), new Card("spades", 2), new Card("spades", 3), new Card("spades", 4),
			new Card("spades", 5), new Card("spades", 6), new Card("spades", 7), new Card("spades", 8),
			new Card("spades", 9), new Card("spades", 10), new Card("hearts", "ace"), new Card("hearts", "king"),
			new Card("hearts", "queen"), new Card("hearts", "jack"), new Card("hearts", 2), new Card("hearts", 3),
			new Card("hearts", 4), new Card("hearts", 5), new Card("hearts", 6), new Card("hearts", 7),
			new Card("hearts", 8), new Card("hearts", 9), new Card("hearts", 10) }));

	/*
	 * These two classes help to keep track of every card in the game
	 */
	private ArrayList<Card> availableCards;
	private ArrayList<Card> deltCards;

	/* =================== */
	/* Constructor Methods */
	/* =================== */

	public Deck() {
		this.createDeck(1, null);
	}

	public Deck(int amountOfRepetitions) {
		this.createDeck(amountOfRepetitions, null);
	}

	public Deck(ArrayList<Card> cardsToExclude) {
		this.createDeck(1, cardsToExclude);
	}

	public Deck(Card[] cardsToExclude) {
		this.createDeck(1, new ArrayList<Card>(Arrays.asList(cardsToExclude)));
	}

	public Deck(int amountOfRepetitions, ArrayList<Card> cardsToExclude) {
		this.createDeck(amountOfRepetitions, cardsToExclude);
	}

	public Deck(int amountOfRepetitions, Card[] cardsToExclude) {
		this.createDeck(amountOfRepetitions, new ArrayList<Card>(Arrays.asList(cardsToExclude)));
	}

	/* ================================ */
	/* Generalized Deck Creation Method */
	/* ================================ */

	private void createDeck(int amountOfRepetitions, ArrayList<Card> cardsToExclude) {
		this.availableCards = new ArrayList<Card>();
		this.deltCards = new ArrayList<Card>();
		ArrayList<Card> validCards = Deck.COMPLETE_DECK;

		if (cardsToExclude != null) {
			for (Card c : cardsToExclude) {
				validCards.remove(c);
			}
		}

		for (int i = 0; i < amountOfRepetitions; i++) {
			this.availableCards.addAll(validCards);
		}
	}

	/* ============== */
	/* Setter Methods */
	/* ============== */

	/*
	 * This method makes use of the Math.random() class to get a random card each
	 * time, thus achieving some sort of shuffling without the hassle that it would
	 * be to actually shuffle the deck
	 */
	public Card dealCard() {
		int random = 0 + (int) (Math.random() * (((this.availableCards.size() - 1) - 0) + 1));

		Card c = this.availableCards.get(random);

		this.availableCards.remove(random);
		this.deltCards.add(c);

		return c;
	}

	public void returnToDeck(Card c) {
		this.deltCards.remove(c);
		this.availableCards.add(c);
	}

	/* ============== */
	/* Getter Methods */
	/* ============== */

	public ArrayList<Card> getAvailableCards() {
		return this.availableCards;
	}

	public ArrayList<Card> getDeltCards() {
		return this.deltCards;
	}
}
