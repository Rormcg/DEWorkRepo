/**
 * Class for use in testing the Card class
 * To be used as a Comparator that sorts Card objects
 * @author Rory McGuire
 */

public class TestCards {

	public static void main(String[] args) {
		Deck a = new Deck();
		//System.out.println(a);
		
		//Create random deck
		Card[] testArray = new Card[52];
		int count = 0;
		for(int i = 0; i < 4; i ++) {
			for(int j = 0; j < 13; j ++) {
				if(Math.random() > 0.5) {
					testArray[count] = new Card(i, j);
					count ++;
				}
			}
		}
		Deck b = new Deck(testArray);
		System.out.println("Initialized:\n" + b);
		b.shuffle();
		System.out.println("Shuffled:\n" + b);
		b.selectionSort();
		System.out.println("Selection Sorted:\n" + b);
		b.shuffle();
		System.out.println("Shuffled:\n" + b);
		b.mergeSort();
		System.out.println("MergeSorted:\n" + b);
		
		

	}

}
