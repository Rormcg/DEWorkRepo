/**
 * Represents a playing card
 * @author Rory McGuire
 */

public class Card implements Comparable {
	private String suit;
	private int rank;
	
	public final String[] SUITS = {"Clubs", "Diamonds", "Hearts", "Spades"};
	public final String[] RANKS = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven",
									"Eight", "Nine", "Ten", "Jack", "Queen", "King"};
	
	/**
	 * Default Constructor: assigns the lowest suit/rank values to each field
	 */
	Card() {
		suit = SUITS[0];
		rank = 0;
	}
	
	/**
	 * @param s the String value to be assigned to suit
	 * @param r the int value to be assigned to rank
	 */
	Card(String s, int r) {
		setSuit(s);
		setRank(r);
	}
	
	/**
	 * @param s the int value to be assigned to suit after being converted to a corresponding string
	 * @param r the int value to be assigned to rank
	 */
	Card(int s, int r) {
		setSuit(s);
		setRank(r);
	}
	
	/**
	 * @param s the String value to be assigned to suit
	 * @param r the String value to be assigned to rank after being converted to a corresponding int
	 */
	Card(String s, String r) {
		setSuit(s);
		setRank(r);
	}
	
	/**
	 * @param s the int value to be assigned to suit after being converted to a corresponding string
	 * @param r the String value to be assigned to rank after being converted to a corresponding int
	 */
	Card(int s, String r) {
		setSuit(s);
		setRank(r);
	}
	
	
	/**
	 * Copy Constructor - Will create a copy of the parameter with the same values for its fields
	 * @param c the Card object to be copied
	 */
	Card(Card c) {
		this.rank = c.rank;
		this.suit = c.suit;
	}
	
	/**
	 * Method to compare another object to this Card object
	 * If the return value = 0, then the objects are equal
	 * If the return value is negative, then this object is less than the other
	 * If positive, then this object is greater than the other
	 * @param o Object to be compared to this Card Object
	 * @return integer representing "this object minus other object o"
	 * @throws IllegalArgumentException if o is not an instance of the Card class
	 */
	@Override
	public int compareTo(Object o) {
		if(!(o instanceof Card)) throw new IllegalArgumentException("Incompatible types");
		Card a = (Card) o;
		
		if(a.suit.equalsIgnoreCase(this.suit)) {
			return this.rank - a.rank;
		}
		
		return this.getSuitInt() - a.getSuitInt();
	}
	
	/**
	 * Method to check if another Object o is equal to this Card object
	 * Returns true if both the suit and rank fields are equivalent to the other object's
	 * @param o Object to be compared to this Card Object
	 * @return boolean representing whether the objects are equal
	 * @throws IllegalArgumentException if o is not an instance of the Card class
	 */
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Card)) throw(new IllegalArgumentException("Incompatible types"));
		Card a = (Card) o;
		
		return a.suit.equalsIgnoreCase(this.suit) && a.rank == this.rank;
	}
	
	/**
	 * Creates and returns a String representation of this Card object
	 * i.e. "Ace of Spades"
	 * @return String representation of this Card object
	 */
	public String toString() {
		return RANKS[rank] + " of " + suit;
	}
	
	/**
	 * Sets the rank field to an integer equivalent to r
	 * If r is not one of the accepted rank values, rank is assigned the value 0
	 * @param r the value to be assigned to rank
	 */
	public void setRank(String r) {
		for(int i = 0; i < RANKS.length; i++) {
			if(r.equalsIgnoreCase(RANKS[i])) {
				rank = i;
				break;
			}
			if(i >= RANKS.length - 1) {
				rank = 0;
				System.out.println("Invalid Rank Entered; Default Value (0) Given");
			}
		}
	}
	
	/**
	 * Sets the rank field to the value of r
	 * @param r the int value to be assigned to rank
	 */
	public void setRank(int r) {
		if(r >= 13) {
			rank = 0;
			System.out.println("Invalid Rank Entered; Default Value (0) Given");
		} else rank = r;
	}
	
	/**
	 * Sets the suit field to the value of s
	 * If s is not an accepted value, the lowest ranking suit value is assigned to suit instead
	 * @param s the String value to be assigned to suit
	 */
	public void setSuit(String s) {
		for(int i = 0; i < SUITS.length; i ++) {
			if(SUITS[i].equalsIgnoreCase(s)) {
				suit = SUITS[i];
			}
			if(i >= SUITS.length - 1) {
				suit = SUITS[0];
				System.out.println("Invalid Suit Entered; Default Value (" + SUITS[0] + ") Given");
			}
		}
		
	}
	
	/**
	 * Sets the suit to the String value associated with the int value s
	 * @param s int value that the corresponding String value will be assigned to suit
	 */
	public void setSuit(int s) {
		if(s >= 0 && s <= 3) {
			suit = SUITS[s];
		} else {
			suit = SUITS[0];
			System.out.println("Invalid Suit Entered; Default Value (" + SUITS[0] + ") Given");
		}
	}
	
	/**
	 * Returns the int value of the rank field
	 * @return rank
	 */
	public int getRank() {
		return rank;
	}
	
	/**
	 * Returns the String representation of the rank field
	 * @return rank's String equivalent
	 */
	public String getRankStr() {
		return RANKS[rank];
	}
	
	/**
	 * Returnd the String value of the suit field
	 * @return suit
	 */
	public String getSuit() {
		return suit;
	}
	
	/**
	 * Returns the integer equivalent of the suit field
	 * @return the integer equivalent of suit
	 */
	public int getSuitInt() {
		for(int i = 0; i < SUITS.length; i ++) {
			if(SUITS[i].equalsIgnoreCase(suit)) {
				return i;
			}
		}
		return -1;
	}
	
}
