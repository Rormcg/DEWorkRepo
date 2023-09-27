/**
 * Represents a deck of cards
 * Contains an Array of Card objects
 * @author Rory McGuire
 */



public class Deck {
	public final int NUMCARDS = 54; //Size of a full deck (NOT the number of cards currently in the deck)
	private Card[] cards = new Card[NUMCARDS];
	private int topCard;
	
	
	Deck() {
		for(int i = 1; i <= 4; i ++) {
			for(int j = 1; j <= 13; j ++) {
				cards[i + j] = new Card(i, j);
			}
		}
		
		topCard = NUMCARDS - 1;
	}
	
	Deck(boolean a) {
		this();
		
		if(!a) shuffle();
	}
	
	Deck(Deck d) {
		setCards(d.getCards());
	}
	
	public void shuffle() {
		
	}
	
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Deck)) throw new IllegalArgumentException("Incompatible types");
		Deck a = (Deck) o;
		
		if(a.getCards().length != cards.length) return false;
		
		//check if they have all the same cards
		
		return true;
	}
	
	public boolean hasCard(Card c) {
		for(Card x : cards) {
			if(c.equals(x)) return true;
		}
		return false;
	}
	
	public Card[] getCards() {
		return cards;
	}
	
	public void setCards(Card[] c) {
		for(int i = 0; i < NUMCARDS - 1; i ++) {
			if(i >= c.length) break;
			this.cards[i] = new Card(c[i]);
		}
	}
	
}
