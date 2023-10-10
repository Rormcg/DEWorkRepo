import java.util.Arrays;

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
		Deck compareMe = null;
		//compare using Arrays.sort();
		
		System.out.println("Initialized:\n" + b);
		b.shuffle();
		System.out.println("Shuffled:\n" + b);
		
		compareMe = new Deck(b);
		Arrays.sort(compareMe.getEffectiveCards());
		b.selectionSort();
		System.out.println("Selection Sorted: " + (compareMe.equals(b) ? "Passed" : "Failed") + "\n" + b);
		
		b.shuffle();
		System.out.println("Shuffled:\n" + b);
		
		compareMe = new Deck(b);
		Arrays.sort(compareMe.getEffectiveCards());
		b.mergeSort();
		System.out.println("MergeSorted: " + (compareMe.equals(b) ? "Passed" : "Failed") + "\n" + b);
		
		int tests = 100;
		int mergePassed = 0;
		int selectPassed = 0;
		for(int i = 0; i < tests; i++) {
			b.shuffle();
			compareMe = new Deck(b);
			Arrays.sort(compareMe.getEffectiveCards());
			b.selectionSort();
			
			selectPassed += compareMe.equals(b) ? 1 : 0;
			
			b.shuffle();
			compareMe = new Deck(b);
			Arrays.sort(compareMe.getEffectiveCards());
			b.mergeSort();
			
			mergePassed += compareMe.equals(b) ? 1 : 0;
		}
		System.out.println("Tests Run: " + tests + "\n" +
							"Selection Sort~ Passed: " + selectPassed + " Failed: " + (tests - selectPassed) + "\n" +
							"Merge Sort~ Passed: " + mergePassed + " Failed: " + (tests - mergePassed));

	}

}
