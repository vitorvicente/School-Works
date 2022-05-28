import java.util.ArrayList;
import java.util.Arrays;

public class CardHand {
	private ArrayList<Card> hand;

	/* =================== */
	/* Constructor Methods */
	/* =================== */
	
	public CardHand() {
		this.setHand(new ArrayList<Card>());
	}

	public CardHand(ArrayList<Card> hand) {
		this.setHand(hand);
	}

	public CardHand(Card[] hand) {
		this.setHand(new ArrayList<Card>(Arrays.asList(hand)));
	}

	/* ===================== */
	/* Getter/Setter Methods */
	/* ===================== */ 
	
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public void addCard(Card c) {
		this.hand.add(c);
	}

	public void removeCard(Card c) {
		this.hand.remove(c);
	}

	public void clearHand() {
		this.hand = new ArrayList<Card>();
	}

	public ArrayList<Card> getHand() {
		return this.hand;
	}
	
	/* =========== */
	/* Aux Methods */
	/* =========== */
	
	public String toString() {
		String res = "";
		
		for (Card c : this.getHand()) {
			if (res.equals(""))
				res = c + "";
			else 
				res = res + ", " + c;
		}
		
		return res;
	}

}
