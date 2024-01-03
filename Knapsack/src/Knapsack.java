import java.util.List;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;

/**
 * 
 * @author Rory McGuire
 */

public class Knapsack {
	
	public static void main(String[] args) {
		String filename = "";
		File f = new File(filename);
		try {
			Scanner s = new Scanner(f);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		//int[] numbers = new int[];
		
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
		
		return 0;
	}
}
