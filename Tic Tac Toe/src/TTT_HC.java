import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Extends Board, and by extension JFrame.
 * Completes the abstract Board class, filling in the missing methods
 * for converting to a hashcode and checking for a win.
 * Similare to TicTacToeHashCode, but instead of a 1-to-1 hash, this hash is much smaller with collisions
 * @author anonymous
 * @author Rory McGuire
 */
public class TTT_HC extends Board {
	public static final int MAX_HASH = 666; //upper bound for the hash - the possible hash values go from 0 -> MAX_HASH
	
	private static final long serialVersionUID = 1L;
	boolean [] winners;  // True if the hash string that maps to this index is a winner, false otherwise
	ArrayList<TreeSet<String>> winnersChained; //Array of containers for each hash to resolve collisions
	
	/**
	 * Constructs an instance of the TTT_HC class with a given title.
	 * Fills the winners array with boolean values: true for every configuration contained in winners.txt, false for others
	 * fills the winnersChained ArrayList with binary trees containing each winning configuration attributed to the hash
	 * @param s the title of this JFrame instance
	 */
	TTT_HC(String s) {
		super(s);
		//Total items (boards): 19683
		//Size of array: 667
		//Load Factor: 29.5
		winners = new boolean[MAX_HASH + 1];
		
		//Use chaining to resolve hash collisions
		winnersChained = new ArrayList<TreeSet<String>> ();
		for(int i = 0; i <= MAX_HASH; i++) {
			winnersChained.add(new TreeSet<String>());
		}
		
		Scanner sc = null;
		File f = new File("winners.txt");
		
		try {
			sc = new Scanner(f);
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		for(int i = 0; i < winners.length; i++) {
			winners[i] = false;
		}
		while(sc.hasNextLine()) {
			String line = sc.nextLine()+"";
			winners[tTTHashCode(line)] = true;
			winnersChained.get(tTTHashCode(line)).add(line);
		}
	}
	
	/**
	 * Inherited from Board; in this implementation is simply a different way to call tttHashCode()
	 * @return 0
	 */
	@Override
	public int myHashCode() {
		return tttHashCode();
	}
	
	/**
	 * Converts this board configuration to a hash value (an integer between 0-666)
	 * @return the hash value for this object's board configuration
	 */
	public int tttHashCode() {
		String s = "";
		for(int r = 0; r < 3; r ++) {
			for(int c = 0; c < 3; c++) {
				s += charAt(r, c);
			}
		}
		return tTTHashCode(s);
	}
	
	/**
	 * Converts a given board configuration to a hash value (an integer between 0-666)
	 * @param position the board configuration to be converted to a hash
	 * @return the hash value for the given board configuration
	 */
	public static int tTTHashCode(String position) {
		
		
		
		if(position.length() != 9) return 0;
		
		position = convertToBoardString(position);
		String front = position.substring(0, 4);
		//add the first third to the middle third and finally to the last third of the String
		//Should give a number between 0-666
		int hash = Integer.parseInt(position.substring(0, 3)) + Integer.parseInt(position.substring(3, 6)) + Integer.parseInt(position.substring(6, 9));
		
		return hash;
		/*vv~TOO LARGE HASH~vv*/
		/*if(position.length() != 9) return 0;
		
		position = convertToBoardString(position);
		
		//Three segments of the full String
		String[] segs = {position.substring(0, 3),
						position.substring(3, 6),
						position.substring(6, 9)};
		
		int hash = 1;
		//multiply the decimal value (+1 to avoid mult by zero) of each of the three segs together to get the hash value
		for(String seg : segs) {
			//convert each seg from Base-3 to Decimal
			int curSeg = 0;
			while(seg.length() > 0) {
				curSeg += Integer.parseInt(seg.substring(0,1)) * Math.pow(3, seg.length() - 1);
				seg = seg.substring(1); 
			}
			//Then multiply hash by that decimal value 
			hash *= curSeg + 1;
		}
		//should be between 0 and 19683  <--TOO LARGE
		return hash;
		*/
	}
	
	/**
	 * Checks whether this board is a winner found within the winners array
	 * @return whether this board is found within the winners array (true if it is a valid win)
	 */
	public boolean isWin() {
		String s = "";
		for(int r = 0; r < 3; r++) {
			for(int c = 0; c < 3; c ++) {
				s += charAt(r, c);
			}
		}
		//System.out.println(s);
		return isWin(s);
	}
	
	/**
	 * Checks whether a given board is a winner found within the winners array
	 * @param s the board configuration to be analyzed
	 * @return whether a given board is found within the winners array (true if it is a valid win)
	 */
	public boolean isWin(String s) {
		if(s.length() != 9) return false;
		
		return winners[tTTHashCode(s)] && winnersChained.get(tTTHashCode(s)).contains(s);
	}
	
	/**
	 * Scans the input file TTT_tests.txt and displays each board configuration on a TicTacToeHashCode instance
	 * @param args the command line input arguments for this method. Ignored in this implementation.
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		TTT_HC board = new TTT_HC("Tic Tac Toe");

		Scanner sc = null;
		File f = new File("TTT_Tests.txt");
		
		try {
			sc = new Scanner(f);
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		String s = "";
		
		while(sc.hasNextLine()) {
			s = sc.nextLine();
			board.setBoardString(convertToBoardString(s));
			board.setHashCodeLabel(board.myHashCode());
			board.setWinner(board.isWin());
			board.show(board.getBoardString());
			Thread.sleep(2000);
		}
	}
}