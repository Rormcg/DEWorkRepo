import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * 
 * @author Rory McGuire
 */

public class Knapsack {
	
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
		int i = 0;
		while(weight.size() != 0) {
			int j = 0;
			while(weight.get(0).size() != 0) {
				weights[i][j] = weight.get(0).remove(0);
				j++;
			}
			weight.remove(0);
			i++;
		}
		
		for(int j = 0; j < weights.length; j++) {
			System.out.println(knapsackOutput(args[j], weights[j], arrLengths[j], limits[j]));
		}
	}
	
	private static String knapsackOutput(String filename, int[] w, int n, int limit) {
		String s = filename + " " + limit + "\t";
		for(int i = 0; i < w.length; i++) {
			s += w[i] + ", ";
		}
		s = s.substring(0, s.length() - 2) + "\n\n";
		
		LinkedList<Integer> melons = new LinkedList<Integer>();
		int sum = knapsackSum(w, n, limit, melons);
		for(Integer m: melons) {
			s += m + " pound watermelon\n";
		}
		
		return s.trim();
	}
	
	public static int knapsackSum(int[] w, int n, int limit) {
		if(limit < 0) return 0;
		if(n == 1) {
			return w[n-1] < limit ? w[n-1] : 0;
		}
		
		int valueWith = knapsackSum(w, n - 1, limit - w[n - 1]);
		int valueWithout = knapsackSum(w, n - 1, limit);
		
		if(valueWith > valueWithout) {
			//list.add(w[n-1]);
			return valueWith + w[n-1];
		} else {
			return valueWithout;
		}
	}
	
	public static int knapsackSum(int[] w, int n, int limit, List<Integer> list) {
		if(limit < 0) return 0;
		if(n == 1) {
			return w[n-1] < limit ? w[n-1] : 0;
		}
		
		ArrayList<Integer> list1 = new ArrayList<Integer>(list);
		list1.add(w[n-1]);
		ArrayList<Integer> list2 = new ArrayList<Integer>(list);
		
		int valueWith = knapsackSum(w, n - 1, limit - w[n - 1], list1);
		int valueWithout = knapsackSum(w, n - 1, limit, list2);
		
		if(valueWith > valueWithout) {
			//list.add(w[n-1]);
			list.addAll(list1);
			return valueWith + w[n-1];
			
		} else {
			list.addAll(list2);
			return valueWithout;
		}
	}
}
