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
				cards[(i - 1) * 13 + j - 1] = new Card(i, j);
			}
		}
		
		topCard = NUMCARDS - 1;
	}
	
	Deck(Card[] c) {
		for(int i = 0; i < cards.length || i < c.length; i ++) {
			cards[i] = c[i];
			if(i < cards.length || i < c.length) topCard = i;
		}
	}
	
	Deck(boolean a) {
		this();
		
		if(!a) shuffle();
	}
	
	Deck(Deck d) {
		setCards(d.getCards());
	}
	
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
	 * PRECONDITION: both decks only have one of each card
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
	
	public boolean hasCard(Card c) {
		for(Card x : cards) {
			if(c.equals(x)) return true;
		}
		return false;
	}
	
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
	
	public Card pick() {
		int randIndex = (int)(Math.random() * (topCard + 1));
		Card c = cards[randIndex];
		remove(randIndex);
		
		return c;
	}
	
	//remove the card at index x
	public void remove(int x) {
		for(int i = x; i < cards.length - 1; i ++) {
			cards[i] = cards[i + 1];
		}
		cards[cards.length - 1] = null;
	}
	
	public void selectionSort() {
		for(int i = topCard; i > 0; i --) {
			int biggest = 0;
			//find the largest card
			for(int j = 0; j < i; j ++) {
				if(cards[j].compareTo(biggest) > 0) {
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
	
	public void mergeSort() {
		//In order for mergeSort to have no parameters and still work recursively, the cards array
		//must be sent as a parameter to a helper method, which does the actual sorting
		cards = mergeSortArray(cards);
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
	
	
	@Override
	public String toString() {
		String s = "";
		if(topCard == 51) {
			for(int r = 0; r < 13; r ++) {
				for(int c = 0; c < 4; c ++) {
					s += cards[1].RANKS[r] + " of " + cards[1].SUITS[c];
					if(c < 3) s += "\t";
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
			for(int i  = 0; i < cards.length; i ++) {
				s += i + ". " + cards[i].toString() + "\t";
			}
		}
		s.trim();
		return s;
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
