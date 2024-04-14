

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * @author Rory McGuire
 * Mirrors the tests of TicTacToeHashMap, this time analyzing the hashCode method created for this project
 */
public class TicTacToeMyHashMap {
	public static final int MAX_HASH = 666; //upper bound for the hash - the possible hash values go from 0 -> MAX_HASH
	public static final int TOTAL_WINS = 1400; //number of win configurations in winners.txt
	
	ArrayList<TreeSet<String>> winners; //Array of containers for each hash to resolve collisions
	
	/**
	 * Constructs an instance of the TicTacToeMyHashCode class.
	 * Fills the winners ArrayList with binary trees containing each winning configuration attributed to the hash
	 */
	TicTacToeMyHashMap() {
		//Use chaining to resolve hash collisions
		winners = new ArrayList<TreeSet<String>> ();
		for(int i = 0; i <= MAX_HASH; i++) {
			winners.add(new TreeSet<String>());
		}
		
		Scanner sc = null;
		File f = new File("winners.txt");
		
		try {
			sc = new Scanner(f);
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		while(sc.hasNextLine()) {
			String line = sc.nextLine()+"";
			winners.get(tTTHashCode(line)).add(line);
		}
	}
	
	/**
	 * Converts a given board configuration to a hash value (an integer between 0-666)
	 * @param position the board configuration to be converted to a hash
	 * @return the hash value for the given board configuration
	 */
	public static int tTTHashCode(String position) {
		if(position.length() != 9) return 0;
		
		position = Board.convertToBoardString(position);
		//add the first third to the middle third and finally to the last third of the String
		//Should give a number between 0-666
		int hash = Integer.parseInt(position.substring(0, 3)) + Integer.parseInt(position.substring(3, 6)) + Integer.parseInt(position.substring(6, 9));
		
		return hash;
	}

	/**
	 * Finds and returns the capacity (available space) of the winners HashMap
	 * @return the capacity of the HashMap
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	private int capacity() throws NoSuchFieldException, IllegalAccessException {
		Field arrayField = ArrayList.class.getDeclaredField("elementData");
		arrayField.setAccessible(true);
		Object[] array = (Object[]) arrayField.get(winners);
		return array == null ? 0 : array.length;
	}

	/**
	 * Finds and returns the distribution of values in the winners HashMap array of
	 * entries. Returns in the form of an int array of length 4, so that each index
	 * has the number of key-value pairs in the corresponding quarter of the array
	 * 
	 * @return the distribution of entries in the winners array in the form of a
	 *         4-part int[]
	 */
	private int[] entryDistribution() {
		int[] quarters = new int[4];
		for (int i = 0; i < quarters.length; i++) {
			quarters[i] = 0;
		}

		for (int i = 0; i < winners.size(); i++) {

			// depending on where this chain falls in the array, add its number of entries
			// to quarters
			for (int j = 0; j < quarters.length; j++) {
				if (i < winners.size() * ((j + 1) / (quarters.length * 1.0))) {
					quarters[j] += winners.get(i).size();
					break;
				}
			}
		}

		return quarters;
	}

	/**
	 * Finds and returns the distribution of collisions in the winners HashMap array
	 * of entries. Returns in the form of an int array of length 10, so that each
	 * index has the number of collisions (defined as every key after the first in
	 * each chain) in the corresponding tenth of the array
	 * 
	 * @return the distribution of collisions in the winners array in the form of a
	 *         10-part int[]
	 */
	private int[] collisionDistribution() {
		int[] tenths = new int[10];
		for (int i = 0; i < tenths.length; i++) {
			tenths[i] = 0;
		}

		for (int i = 0; i < winners.size(); i++) {

			// depending on where this chain falls in the array, add its number of collisions
			// to tenths
			for (int j = 0; j < tenths.length; j++) {
				if (i < winners.size() * ((j + 1) / (tenths.length * 1.0)) && winners.get(i).size() > 1) {
					tenths[j] += winners.get(i).size() - 1;
					break;
				}
			}
		}

		return tenths;
	}

	/**
	 * Finds and returns the length of the longest chain in the winners HashMap
	 * array of chains
	 * 
	 * @return the longest chain length in the HashMap
	 */
	private int maxChainLength() {
		int max = 0;

		for (int i = 0; i < winners.size(); i++) {
			if(winners.get(i).size() > max) {
				max = winners.get(i).size();
			}
		}
		return max;
	}

	/**
	 * Finds and returns the average length all chains with length > 1 in the
	 * winners HashMap array of chains
	 * 
	 * @return the average chain length in the HashMap
	 */
	private double averageChainLength() {
		// Counting chains of length > 1 only
		int total = 0;
		int numChains = 0;

		for (int i = 0; i < winners.size(); i++) {
			total += winners.get(i).size();
			numChains ++;
		}
		
		return (double) total / numChains;
	}

	/**
	 * Finds and returns the loadFactor of the winners HashMap
	 * 
	 * @return the loadFactor of the HashMap
	 */
	private double loadFactor() {
		double loadFactor = (MAX_HASH + 1) / (double)TOTAL_WINS;
		
		return loadFactor;
	}

	/**
	 * Runs each of the methods of this class to analyze the winners HashMap and
	 * prints out the results
	 * 
	 * @param args not used in this implementation
	 * @throws java.io.FileNotFoundException
	 */
	public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

		TicTacToeMyHashMap m = new TicTacToeMyHashMap();

		System.out.println("Capacity: " + m.capacity());
		System.out.println("Load Factor: " + m.loadFactor());
		System.out.println("Max Chain Length: " + m.maxChainLength());
		System.out.println("Average Chain Length (Ignoring chains of length < 1): " + m.averageChainLength());

		int[] dist = m.entryDistribution();
		System.out.println("Entry Distribution By Quarters of the Array:");
		for (int i = 0; i < dist.length; i++) {
			System.out.println((i + 1) + ": " + dist[i]);
		}

		int[] dist2 = m.collisionDistribution();
		System.out.println("Collision Distribution By Tenths of the Array:");
		for (int i = 0; i < dist2.length; i++) {
			System.out.println((i + 1) + ": " + dist2[i]);
		}

		// System.out.println("Capacity: " + m.capacity());

	}

}