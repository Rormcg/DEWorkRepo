import java.util.LinkedList;


/**
 * Tester class for Knapsack
 * @author Rory McGuire
 */
public class TestKnapsack {
	public static final int
	TESTS = 10,
	MIN_OBJS = 3,
	MAX_OBJS = 10,
	MIN_OBJ_VAL = 1,
	MAX_OBJ_VAL = 40,
	MIN_SUM_VAL = 10,
	MAX_SUM_VAL = 200;
	
	//public static final boolean VERBOSE = false;
	
	/**
	 * Runs the Knapsack.knapsackSum() method several times on random values, and prints the input and output for each to CMD
	 * @param args program parameters (unused)
	 */
	public static void main(String[] args) {
		
			
		for(int i = 0; i < TESTS; i++) {
			int[] objs = new int[(int)(Math.random()*(MAX_OBJS-MIN_OBJS)) + MIN_OBJS];
			for(int j = 0; j < objs.length; j++) {
				objs[j] = (int)(Math.random()*(MAX_OBJ_VAL - MIN_OBJ_VAL)) + MIN_OBJ_VAL;
			}
			int limit = (int)(Math.random()*(MAX_SUM_VAL - MIN_SUM_VAL)) + MIN_SUM_VAL;
			
			System.out.println(i + ":\t\t" + testSum(objs, objs.length, limit) + "\n");
		}

	}
	
	private static String testSum(int[] w, int n, int limit) {
		LinkedList<Integer> picked = new LinkedList<Integer>();
		int sum = Knapsack.knapsackSum(w, w.length, limit, picked);
		
		String s = "Limit: " + limit + "\tInts: ";
		for(int i : w) {
			s += i + ", ";
		}
		s = s.substring(0, s.length() - 2) + "\nSolution:\tSum: " + sum + "  \tValues: ";
		for(Integer i : picked) {
			s += i + ", ";
		}
		
		return s.substring(0, s.length() - 2);
	}

}
