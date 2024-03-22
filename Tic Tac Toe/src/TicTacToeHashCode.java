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
public class TicTacToeHashCode extends Board {

	private static final long serialVersionUID = 1L;
	boolean [] winners;  // True if the hash string that maps to this index is a winner, false otherwise
	
	/**
	 * Constructs an instance of the TicTacToeHashCode class with a given title.
	 * Fills the winners array with boolean values: true for every configuration contained in winners.txt, false for others
	 * @param s the title of this JFrame instance
	 */
	TicTacToeHashCode(String s) {
		super(s);
		winners = new boolean[(int)Math.pow(3, 9)];
		
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
			winners[hashCode(sc.nextLine())] = true;
		}
	}

	/**
	 * Converts this board configuration to a hash value
	 * @return the hash value for this's board configuration
	 */
	@Override
	public int myHashCode() {
		String s = "";
		for(int r = 0; r < 3; r ++) {
			for(int c = 0; c < 3; c++) {
				s += charAt(r, c);
			}
		}
		
		return hashCode(s);
	}
	
	/**
	 * Converts a given board configuration to a hash value
	 * @param position the board configuration to be converted to a hash
	 * @return the hash value for the given board configuration
	 */
	public static int hashCode(String position) {
		if(position.length() != 9) return 0;
		
		position = convertToBoardString(position);
		
		int hash = 0;
		while(position.length() > 0) {
			hash += Integer.parseInt(position.substring(0,1)) * Math.pow(3, position.length() - 1);
			position = position.substring(1); 
		}
		return hash;
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
		/*for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) != '*' &&
				s.charAt(i) != 'o' &&
				s.charAt(i) != 'x') {
				return false;
			}
		}*/
		return winners[hashCode(s)];
	}
	
	/**
	 * Scans the input file TTT_tests.txt and displays each board configuration on a TicTacToeHashCode instance
	 * @param args the command line input arguments for this method. Ignored in this implementation.
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		TicTacToeHashCode board = new TicTacToeHashCode ("Tic Tac Toe");
		
		//ArrayList<String> tests = new ArrayList<String> ();
		Scanner sc = null;
		File f = new File("TTT_Tests.txt");
		
		try {
			sc = new Scanner(f);
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		/*while(sc.hasNext()) {
			tests.add(sc.next());
		}
		sc.close();*/
		
		String s = "";
		//Scanner kb = new Scanner(System.in);
		
		//while (true) {
		while(sc.hasNextLine()) {
			//s = kb.nextLine();
			s = sc.nextLine();
			//System.out.println(board.winners[134]);
			board.setBoardString(convertToBoardString(s));
			board.setHashCodeLabel(board.myHashCode());
			board.setWinner(board.isWin());
			board.show(board.getBoardString());
			//System.out.println(s);
			Thread.sleep(2000);
			
			
			/*
			//  String currentBoard = board.boardValues[(int)(Math.random()* board.boardValues.length)];
			//  board.show(currentBoard);
			board.setHashCodeLabel(board.myHashCode());
			//  board.setWinner(TicTacToe.isWin(currentBoard));
			//  TicTacToe ttt = new TicTacToe();
			//  System.out.println(ttt.isWin(s));
			board.show(s);
			//     Thread.sleep(4000); 
			//       board.displayRandomString();
			 */


		}
	}
}