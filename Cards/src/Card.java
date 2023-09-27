/**
 * Represents a playing card
 * @author Rory McGuire
 */

public class Card implements Comparable {
	private String suit;
	private int rank;
	
	public final String[] SUITS = {"Spades", "Hearts", "Diamonds", "Clubs"};
	public final String[] RANKS = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven",
									"Eight", "Nine", "Ten", "Jack", "Queen", "King"};
	
	Card() {
		suit = SUITS[1];
		rank = 1;
	}
	
	Card(String s, int r) {
		setSuit(s);
		setRank(r);
	}
	
	Card(int s, int r) {
		setSuit(s);
		setRank(r);
	}
	
	Card(String s, String r) {
		setSuit(s);
		setRank(r);
	}
	
	Card(int s, String r) {
		setSuit(s);
		setRank(r);
	}
	
	
	Card(Card c) {
		this.rank = c.rank;
		this.suit = c.suit;
	}
	
	@Override
	public int compareTo(Object o) {
		if(!(o instanceof Card)) throw new IllegalArgumentException("Incompatible types");
		Card a = (Card) o;
		
		if(a.suit.equalsIgnoreCase(this.suit)) {
			return this.rank - a.rank;
		}
		
		return this.getSuitInt() - a.getSuitInt();
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Card)) throw(new IllegalArgumentException("Incompatible types"));
		Card a = (Card) o;
		
		return a.suit.equalsIgnoreCase(this.suit) && a.rank == this.rank;
	}
	
	public String toString() {
		return RANKS[rank] + " of " + suit;
	}
	
	public void setRank(String r) {
		for(int i = 0; i < RANKS.length; i++) {
			if(r.equalsIgnoreCase(RANKS[i])) {
				rank = i;
				break;
			}
			if(i >= RANKS.length - 1) {
				rank = 1;
				System.out.println("Invalid Rank Entered; Default Value (1) Given");
			}
		}
	}
	
	public void setRank(int r) {
		if(r > 13) {
			rank = 1;
			System.out.println("Invalid Rank Entered; Default Value (1) Given");
		} else rank = r;
	}
	
	public void setSuit(String s) {
		for(int i = 0; i < SUITS.length; i ++) {
			if(SUITS[i].equalsIgnoreCase(s)) {
				suit = SUITS[i];
			}
			if(i >= SUITS.length - 1) {
				suit = SUITS[1];
				System.out.println("Invalid Suit Entered; Default Value (" + SUITS[1] + ") Given");
			}
		}
		
	}
	
	public void setSuit(int s) {
		if(s > 0 && s < 5) {
			suit = SUITS[s];
		} else {
			suit = SUITS[1];
			System.out.println("Invalid Suit Entered; Default Value (" + SUITS[1] + ") Given");
		}
	}
	
	public int getRank() {
		return rank;
	}
	
	public String getRankStr() {
		return RANKS[rank];
	}
	
	public String getSuit() {
		return suit;
	}
	
	public int getSuitInt() {
		for(int i = 0; i < SUITS.length; i ++) {
			if(SUITS[i].equalsIgnoreCase(suit)) {
				return i;
			}
		}
		return -1;
	}
	
}
