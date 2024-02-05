import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Static class designed to solve the knapsack problem:<br>
 * Take a set of values, determine which combination of those values is the greatest without going over a given limit
 * @author Rory McGuire
 */
public class Knapsack {
	
	/**
	 * Point from which the Knapsack program runs.
	 * Will read in values from a series of given files and print out the results.
	 * @param args the filenames for input files
	 */
	public static void main(String[] args) {
		int[] limits = new int[args.length];
		ArrayList<ArrayList<Integer>> weight = new ArrayList<ArrayList<Integer>>();
		int[] arrLengths = new int[args.length];
		
		//read in files
		File f = null;
		Scanner s = null;
		for(int i = 0; i < args.length; i++) {
			weight.add(new ArrayList<Integer>());
			
			f = new File(args[i]);
			try {
				s = new Scanner(f);
			} catch(FileNotFoundException e) {
				e.printStackTrace();
			}
			
			if(s.hasNext()) {
				limits[i] = s.nextInt();
			}
			
			while(s.hasNext()) {
				arrLengths[i] ++;
				weight.get(i).add(s.nextInt());
			}
		}
		
		int maxLength = 0; //find the longest array in order to set the upper array length limit
		for(int i = 0; i < arrLengths.length; i++) {
			if(arrLengths[i] > maxLength) maxLength = arrLengths[i];
		}
		
		//convert ArrayLists into arrays
		int[][] weights = new int[args.length][maxLength];
		for(int i = 0; i < weight.size(); i++) {
			for(int j = 0; j < weight.get(i).size(); j++) {
				weights[i][j] = weight.get(i).get(j);
			}
		}
		
		for(int j = 0; j < weights.length; j++) {
			System.out.println(knapsackOutput(args[j], weights[j], arrLengths[j], limits[j]) + "\n");
			//System.out.println(knapsackOutputNoList(args[j], weights[j], arrLengths[j], limits[j]) + "\n");
		}
	}
	
	/**
	 * 
	 * @param filename
	 * @param w
	 * @param n
	 * @param limit
	 * @return
	 */
	public static String knapsackOutput(String filename, int[] w, int n, int limit) {
		String s = filename + " " + limit + "\t";
		for(int i = 0; i < n; i++) {
			s += w[i] + ", ";
		}
		s = s.substring(0, s.length() - 2) + "\n";
		
		LinkedList<Integer> melons = new LinkedList<Integer>();
		int sum = knapsackSum(w, n, limit, melons);
		for(Integer m: melons) {
			s += m + " pound watermelon\n";
		}
		
		return s + "Total: " + sum + (sum == 1 ? " pound" : " pounds");
	}
	
	/**
	 * 
	 * @param filename
	 * @param w
	 * @param n
	 * @param limit
	 * @return
	 */
	public static String knapsackOutputNoList(String filename, int[] w, int n, int limit) {
		String s = filename + " " + limit + "\t";
		for(int i = 0; i < n; i++) {
			s += w[i] + ", ";
		}
		s = s.substring(0, s.length() - 2) + "\n";
		
		int sum = knapsackSum(w, n, limit);
		return s + "Total: " + sum + (sum == 1 ? " pound" : " pounds");
	}
	
	/**
	 * 
	 * @param w
	 * @param n
	 * @param limit
	 * @return
	 */
	public static int knapsackSum(int[] w, int n, int limit) {
		if(limit < 0) return 0;
		if(n == 1) {
			return w[n-1] < limit ? w[n-1] : 0;
		}
		
		int valueWith = knapsackSum(w, n - 1, limit - w[n - 1]);
		int valueWithout = knapsackSum(w, n - 1, limit);
		
		if(valueWith > valueWithout) {
			return valueWith + w[n-1];
		} else {
			return valueWithout;
		}
	}
	
	/**
	 * 
	 * @param w the array of weights
	 * @param n the number of elements in w to be acknowledged in this method
	 * @param limit the largest allowed total weight of objects in the "knapsack"
	 * @param list the list to contain the greatest total weight of objects without exceeding limit
	 * @return the greatest sum of ints in w[] achievable without exceeding limit
	 */
	public static int knapsackSum(int[] w, int n, int limit, List<Integer> list) {
		//System.out.println(n);
		
		if(limit <= 0 || n < 1) {
			if(!list.isEmpty()) {
				list.remove(0);
			}
			return 0;
		}
		if(n == 1) {
			if(w[n-1] < limit) {
				//list.add(w[n-1]);
				return w[n-1];
			}
			return 0;
		}
		
		ArrayList<Integer> list1 = new ArrayList<Integer>(list);
		list1.add(w[n-1]);
		ArrayList<Integer> list2 = new ArrayList<Integer>(list);
		
		int valueWith = 0;
		int valueWithout = 0;
		
		//if(w[n-1] <= limit) {
			valueWith = knapsackSum(w, n - 1, limit - w[n - 1], list1);
			list1.add(w[n-1]);
			valueWithout = knapsackSum(w, n - 1, limit, list2);
			
		//}
		
		if(valueWith > 0 && valueWith > valueWithout) {
			
			list.addAll(list1);
			return valueWith + w[n-1];
			
		} else {
			list.addAll(list2);
			return valueWithout;
		}
	}
}
