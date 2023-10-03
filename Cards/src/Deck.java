/**
 * Represents a deck of cards
 * Contains an array of Card objects (cards) and an int value topCard to represent the topmost
 * non-null value
 * Implements Selection Sort and Merge Sort to properly sort the cards array
 * @author Rory McGuire
 */



public class Deck {
	public final int NUMCARDS = 52; //Size of a full deck (NOT the number of cards currently in the deck)
	private Card[] cards = new Card[NUMCARDS];
	private int topCard;
	
	
	/**
	 * Default Constructor: fills the cards array with a sorted set of Cards objects
	 */
	Deck() {
		for(int i = 0; i < 4; i ++) {
			for(int j = 0; j < 13; j ++) {
				cards[i * 13 + j] = new Card(i, j);
			}
		}
		
		topCard = NUMCARDS - 1;
	}
	
	/**
	 * Fills the cards array with the values passed through the parameters
	 * @param c the Card array to assign to cards
	 */
	Deck(Card[] c) {
		setCards(c);
	}
	
	/**
	 * Fills the cards array with a sorted array if true, but a shuffled one if false
	 * @param a if false, shuffle the cards array
	 */
	Deck(boolean a) {
		this();
		
		if(!a) shuffle();
	}
	
	/**
	 * Copy Constructor: creates a duplicate of the passed object d with the same fields
	 * @param d the Deck object to be replicated
	 */
	Deck(Deck d) {
		setCards(d.getCards());
	}
	
	/**
	 * Randomly shuffles the cards array, leaving empty indices at the back end of the array
	 */
	public void shuffle() {
		Card[] newDeck = new Card[cards.length];
		for(int i = topCard; i >= 0; i --) {
			//pick a random card from the remaining original deck
			int rand = (int)(Math.random() * (i + 1));
			//add that card to the new deck
			newDeck[i] = cards[rand];
			//then remove the card from the old deck
			this.remove(rand);
		}
		
		cards = newDeck;
	}
	
	/**
	 * Compares this Deck object with another Object to see if they are equal
	 * PRECONDITION: both decks only have one of each card
	 * @param o the object to compare this Deck to
	 * @return boolean whether this equals o
	 * @throws IllegalArgumentException if o is not an instance of Deck
	 */
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Deck)) throw new IllegalArgumentException("Incompatible types");
		Deck a = (Deck) o;
		
		if(a.getCards().length != cards.length) return false;
		
		//check if they have all the same cards
		for(Card x : a.getCards()) {
			if(!this.hasCard(x)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Finds out whether cards contains the Card object c or a Card that is equivalent to it
	 * @param c Card to be checked whether it is contained in cards
	 * @return whether c is in the cards array
	 */
	public boolean hasCard(Card c) {
		for(Card x : cards) {
			if(c.equals(x)) return true;
		}
		return false;
	}
	
	/**
	 * Deals a select number of cards to a select number of hands, removing each card from the 
	 * cards array. Will return nothing if there are not enough Card objects in cards, otherwise
	 * will return each hand of cards as its own Deck in an array of Decks
	 * @param hands the number of hands to be dealt
	 * @param cardsPerHand the number of Cards to be dealt to each hand
	 * @return null if not enough cards to be dealt, otherwise an array of Decks, each Deck
	 * being a hand
	 */
	public Deck[] deal(int hands, int cardsPerHand) {
		//If there aren't enough cards to deal, return null
		if(hands * cardsPerHand > this.cards.length) return null;
		
		Card[][] a = new Card[hands][cardsPerHand];
		for(int i = 0; i < cardsPerHand; i ++) {
			for(int j = 0; j < hands; j ++) {
				
				a[j][i] = this.cards[this.cards.length - (i * hands + j)];
				this.remove(cards.length - (i * hands + j));
			}
		}
		
		Deck[] decks = new Deck[hands];
		for(int i  = 0; i < a[hands].length; i ++) {
			decks[i] = new Deck(a[i]);
		}
		
		return decks;
	}
	
	/**
	 * Picks a random card from cards, removes it from the deck, and returns the Card
	 * @return the random Card to be picked from cards
	 */
	public Card pick() {
		int randIndex = (int)(Math.random() * (topCard + 1));
		Card c = cards[randIndex];
		remove(randIndex);
		
		return c;
	}
	
	/**
	 * Remove the Card at a given index, sliding the other Cards to the left to fill its place
	 * @param x the index in cards of the Card to be removed
	 */
	public void remove(int x) {
		for(int i = x; i < cards.length - 1; i ++) {
			cards[i] = cards[i + 1];
		}
		cards[cards.length - 1] = null;
	}
	
	/**
	 * Sorts the cards array via selection sort with O(n^2) efficiency
	 */
	public void selectionSort() {
		for(int i = topCard; i > 0; i --) {
			int biggest = 0;
			//find the largest card
			for(int j = 0; j <= i; j ++) {
				if(cards[j].compareTo(cards[biggest]) > 0) {
					biggest = j;
				}
			}
			//move the biggest card to the end, slide the cards to the left to fill the gap left by the biggest card
			Card temp = cards[biggest];
			for(int x = biggest; x < i; x++) {
				cards[x] = cards[x+1];
			}
			cards[i] = temp;
		}
	}
	
	/**
	 * Sorts the cards array via mergesort with O(n*log(n)) efficiency
	 * Passes the cards array to helper methods in order to sort the array recursively
	 */
	public void mergeSort() {
		//In order for mergeSort to have no parameters and still work recursively, the cards array
		//must be sent as a parameter to a helper method, which does the actual sorting
		
		//remove the ending null values
		Card[] cutCards = new Card[topCard + 1];
		for(int i = 0; i < cutCards.length; i++) {
			cutCards[i] = cards[i];
		}
		
		//mergesort the array via helper methods
		cutCards = mergeSortArray(cutCards);
		
		//pass the sorted array to cards (now with the rightmost null values again)
		cards = new Card[NUMCARDS];
		for(int i = 0; i < cutCards.length; i ++) {
			cards[i] = cutCards[i];
		}
	}
	
	private Card[] mergeSortArray(Card[] a) {
		if(a.length == 1) return a; //Base Case
		//Split the array a into two halves
		Card[] x = new Card[(int)(a.length * 0.5)];
		Card[] y = new Card[(int)Math.ceil(a.length * 0.5)];
		for(int i = 0; i < a.length; i ++) {
			if(i < x.length) {
				x[i] = a[i];
			} else {
				y[i - x.length] = a[i];
			}
		}
		//put each half array back into this method, then merge the results
		return merge(mergeSortArray(x), mergeSortArray(y));
	}
	
	private Card[] merge(Card[] a, Card[] b) {
		Card[] newArray = new Card[a.length + b.length];
		int index1 = 0, index2 = 0;
		for(int i = 0; i < a.length + b.length; i++) {
			//add the lower value between the two arrays to the newArray
			if(index1 >= a.length || (index2 < b.length && b[index2].compareTo(a[index1]) < 0)) {
				newArray[i] = b[index2];
				index2 ++;
			} else {
				newArray[i] = a[index1];
				index1 ++;
			}
		}
		
		return newArray;
	}
	
	
	/**
	 * Returns a String representation of the cards array
	 * If the cards array is full with 52 Cards, will return the Cards in four columns, one for each rank
	 * Otherwise, will return a numbered list of each Card in cards, with each Card on its own line
	 * @return the String representation of cards
	 */
	@Override
	public String toString() {
		String s = "";
		if(topCard == NUMCARDS - 1) {
			for(int r = 0; r < 13; r ++) {
				for(int c = 0; c < 4; c ++) {
					s += cards[0].RANKS[r] + " of " + cards[0].SUITS[c];
					if(c <= 3) s += "\t";
				}
				s += "\n";
			}
			
			/*for(int r = 0; r < 13; r ++) {
				for(int c = 0; c < 4; c ++) {
					s += cards[r*c + c].toString() + " ";
				}
				s += "\n";
			}*/
		} else {
			for(int i  = 0; i <= topCard; i ++) {
				s += (i + 1) + ". " + cards[i] + "\n";
			}
		}
		s.trim();
		return s;
	}
	
	/**
	 * Returns the cards field
	 * @return returns the Card array cards
	 */
	public Card[] getCards() {
		return cards;
	}
	
	/**
	 * Sets the value of cards according to the value of c - will instantiate new values for each
	 * Card rather than using the same memory addresses
	 * @param c the Card array to be copied into cards
	 */
	public void setCards(Card[] c) {
		for(int i = 0; i < NUMCARDS - 1; i ++) {
			if(i >= c.length || c[i] == null) {
				topCard = i - 1;
				break;
			}
			this.cards[i] = new Card(c[i]);
			if(i >= NUMCARDS - 2) topCard = NUMCARDS - 1;
		}
	}
	
}
