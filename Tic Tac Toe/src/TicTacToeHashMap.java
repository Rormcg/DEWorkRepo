

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author unknown/anonymous
 * @author Rory McGuire
 * Breaks down a HashMap in order to determine its efficacy at hashing and storing data,
 * to then compare the results with another method
 */
public class TicTacToeHashMap {

	private HashMap<String, Boolean> winners;
	
	/**
	 * Instantiates a TicTacToeHashMap instance.
	 * Fills the winners HashMap with values from winners.txt
	 */
	TicTacToeHashMap() {
		// load values
		
		int numWins = 1400; //the number of winning boards in winners.txt
		winners = new HashMap<String, Boolean> (numWins);
		
		Scanner sc = null;
		File f = new File("winners.txt");
		
		try {
			sc = new Scanner(f);
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		/*Fill with false values?
		for(int i = 0; i < winners.size(); i++) {
			put( false;
		}*/
		while(sc.hasNextLine()) {
			winners.put(sc.nextLine(), true);
		}
	}

	/**
	 * Finds and returns the capacity (available space) of the winners HashMap
	 * @return the capacity of the HashMap
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	private int capacity() throws NoSuchFieldException, IllegalAccessException {
		Field tableField = HashMap.class.getDeclaredField("table");
		tableField.setAccessible(true);
		Object[] table = (Object[]) tableField.get(winners);
		return table == null ? 0 : table.length;   
	}
	
	/**
	 * Finds and returns the distribution of values in the winners HashMap array of entries.
	 * Returns in the form of an int array of length 4, so that each index has the number of key-value pairs in
	 * the corresponding quarter of the array 
	 * @return the distribution of entries in the winners array in the form of a 4-part int[]
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	private int[] entryDistribution() throws NoSuchFieldException, IllegalAccessException{
		int[] quarters = new int[4];
		for(int i = 0; i < quarters.length; i++) {
			quarters[i] = 0;
		}
		
		Field tableField = HashMap.class.getDeclaredField("table");
		tableField.setAccessible(true);
		Object[] table = (Object[]) tableField.get(winners);
		for(int i = 0; i < table.length; i++) {
			if(table[i] != null) {
				
				Field element = table[i].getClass().getDeclaredField("next");
				element.setAccessible(true);
				Object nextNode = table[i];
				int currentLength = 0;
				while(nextNode != null) {
					currentLength ++;
					
					nextNode = (Object) element.get(nextNode);
				}
				
				//depending on where this chain falls in the array, add its number of entries to quarters
				for(int j = 0; j < quarters.length; j++) {
					if(i < table.length * ((j+1)/(quarters.length*1.0))) {
						quarters[j] += currentLength;
						break;
					}
				}
			}
		}
		
		return quarters;
	}
	
	/**
	 * Finds and returns the distribution of collisions in the winners HashMap array of entries.
	 * Returns in the form of an int array of length 10, so that each index has the number of collisions 
	 * (defined as every key after the first in each chain) in the corresponding tenth of the array 
	 * @return the distribution of collisions in the winners array in the form of a 10-part int[]
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	private int[] collisionDistribution() throws NoSuchFieldException, IllegalAccessException{
		int[] tenths = new int[10];
		for(int i = 0; i < tenths.length; i++) {
			tenths[i] = 0;
		}
		
		Field tableField = HashMap.class.getDeclaredField("table");
		tableField.setAccessible(true);
		Object[] table = (Object[]) tableField.get(winners);
		for(int i = 0; i < table.length; i++) {
			if(table[i] != null) {
				
				Field element = table[i].getClass().getDeclaredField("next");
				element.setAccessible(true);
				Object nextNode = table[i];
				int currentLength = 0;
				while(nextNode != null) {
					currentLength ++;
					
					nextNode = (Object) element.get(nextNode);
				}
				
				//depending on where this chain falls in the array, add its number of collisions (#entries - 1) to tenths
				for(int j = 0; j < tenths.length; j++) {
					if(i < table.length * ((j+1)/(tenths.length*1.0)) && currentLength > 1) {
						tenths[j] += currentLength - 1;
						break;
					}
				}
			}
		}
		
		return tenths;
	}
	
	/**
	 * Finds and returns the length of the longest chain in the winners HashMap array of chains
	 * @return the longest chain length in the HashMap
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	private int maxChainLength() throws NoSuchFieldException, IllegalAccessException{
		int max = 0;
		
		Field tableField = HashMap.class.getDeclaredField("table");
		tableField.setAccessible(true);
		Object[] table = (Object[]) tableField.get(winners);
		for(int i = 0; i < table.length; i++) {
			if(table[i] != null) {
				
				Field element = table[i].getClass().getDeclaredField("next");
				element.setAccessible(true);
				Object nextNode = table[i];
				int currentLength = 0;
				while(nextNode != null) {
					currentLength ++;
					if(max < currentLength) {
						max = currentLength;
					}
					nextNode = (Object) element.get(nextNode);
				}
			}
		}
		return max;
	}
	
	/**
	 * Finds and returns the  average length all chains with length > 1 in the winners HashMap array of chains
	 * @return the average chain length in the HashMap
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	private double averageChainLength() throws NoSuchFieldException, IllegalAccessException{
		//Counting chains of length > 1 only
		int total = 0;
		int numChains = 0;
		
		Field tableField = HashMap.class.getDeclaredField("table");
		tableField.setAccessible(true);
		Object[] table = (Object[]) tableField.get(winners);
		for(int i = 0; i < table.length; i++) {
			if(table[i] != null) {
				
				Field element = table[i].getClass().getDeclaredField("next");
				element.setAccessible(true);
				Object nextNode = table[i];
				int currentLength = 0;
				while(nextNode != null) {
					currentLength ++;
					nextNode = (Object) element.get(nextNode);
				}
				if(currentLength > 1) {
					total += currentLength;
					numChains ++;
				}
			}
		}
		return (double)total / numChains;
	}
	
	/**
	 * Finds and returns the loadFactor of the winners HashMap
	 * @return the loadFactor of the HashMap
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	private float loadFactor() throws NoSuchFieldException, IllegalAccessException{
		Field tableLoadFactor = HashMap.class.getDeclaredField("loadFactor");
		tableLoadFactor.setAccessible(true);
		float loadFactor = (float) tableLoadFactor.get(winners);
		return loadFactor; 
	}
	
	/**
	 * Runs each of the methods of this class to analyze the winners HashMap and prints out the results
	 * @param args not used in this implementation
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

		TicTacToeHashMap m = new TicTacToeHashMap();


		System.out.println("Capacity: " + m.capacity());
		System.out.println("Load Factor: " + m.loadFactor());
		System.out.println("Max Chain Length: " + m.maxChainLength());
		System.out.println("Average Chain Length (Ignoring chains of length < 1): " + m.averageChainLength());
		
		int[] dist = m.entryDistribution();
		System.out.println("Entry Distribution By Quarters of the Array:");
		for(int i = 0; i < dist.length; i++) {
			System.out.println((i+1) + ": " + dist[i]);
		}
		
		int[] dist2 = m.collisionDistribution();
		System.out.println("Collision Distribution By Tenths of the Array:");
		for(int i = 0; i < dist2.length; i++) {
			System.out.println((i+1) + ": " + dist2[i]);
		}
		
		//System.out.println("Capacity: " + m.capacity());

	}

}