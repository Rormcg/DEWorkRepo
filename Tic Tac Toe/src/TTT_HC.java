import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Extends Board, and by extension JFrame.
 * Completes the abstract Board class, filling in the missing methods
 * for converting to a hashcode and checking for a win
 * @author anonymous
 * @author Rory McGuire
 */
public class TTT_HC extends Board {

	private static final long serialVersionUID = 1L;
	boolean [] winners;  // True if the hash string that maps to this index is a winner, false otherwise
	
	/**
	 * Constructs an instance of the TicTacToeHashCode class with a given title.
	 * Fills the winners array with boolean values: true for every configuration contained in winners.txt, false for others
	 * @param s the title of this JFrame instance
	 */
	TTT_HC(String s) {
		super(s);
		winners = new boolean[(int)Math.pow(3, 9) % 1000 + 1000];
		
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
			winners[tTTHashCode(sc.nextLine())] = true;
		}
	}
	
	
	@Override
	public int myHashCode() {return 0;}
	
	/**
	 * Converts this board configuration to a hash value
	 * @return the hash value for this's board configuration
	 */
	//@Override
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
	 * Converts a given board configuration to a hash value
	 * @param position the board configuration to be converted to a hash
	 * @return the hash value for the given board configuration
	 */
	public static int tTTHashCode(String position) {
		
		
		
		if(position.length() != 9) return 0;
		
		position = convertToBoardString(position);
		String front = position.substring(0, 4);
		int hash = 0;
		while(position.length() > 0) {
			hash += Integer.parseInt(position.substring(0,1)) * Math.pow(3, position.length() - 1);
			position = position.substring(1); 
		}
		//Reduces the maximum value of hash to 1683, thus reducing the size issue of the previously huge range (0-19683)
		return (hash % 1000) + (hash > 10000 ? 1000 : 0);
		
		/*vvTOO LARGE HASHvv*/
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
		
		return winners[tTTHashCode(s)];
	}
	
	/**
	 * 
	 */
	public static void main(String[] args) throws InterruptedException {
		System.out.println((int)Math.pow(3, 9) % 1000 + 1000);
	}
}