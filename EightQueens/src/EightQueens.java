import java.util.ArrayList;

/**
 * Main class for the EightQueens Project; finds, stores, and displays the solutions to the eight queens problem
 */
public class EightQueens {
	
	private static ArrayList<String> solutions;
	
	/**
	 * Finds every solution to the eight queens problem, then displays all of these solutions in sequence on a ChessBoard instance
	 * @param args Not used in this implementation
	 */
	public static void main(String[] args) {
		solutions = new ArrayList<String> ();
		
		//ChessBoard b = new ChessBoard("35716024");
		ChessBoard b = new ChessBoard();
		
		findAllSolutions();
		
		displaySolutions(b);
	}
	
	/**
	 * Displays every solution this class has stored in sequence on the given ChessBoard
	 * @param b the ChessBoard on which to display the solutions
	 */
	public static void displaySolutions(ChessBoard b) {
		System.out.println(solutions.size() + " Solutions Found");
		for(String s: solutions) {
			b.createBoard(s);
			System.out.println(s);
			try {
				Thread.sleep(1500);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void findAllSolutions() {
		for(int r = 0; r < ChessBoard.L; r++) {
			placeQueens("", r);
		}
	}
	
	private static void placeQueens(String s, int row) {
		if(canPlace(s, row)) {
			s += row;
		} else return;
		
		if(s.length() == ChessBoard.W) {
			if(!solutions.contains(s)) {
				solutions.add(s);
			} else return;
		}
		
		for(int r = 0; r < ChessBoard.L; r++) {
			placeQueens(s, r);
		}
	}
	
	private static boolean canPlace(String s, int row) {
		//check no queens are in that row
		for(int i = 0; i < s.length(); i++) {
			if(Integer.parseInt(s.charAt(i)+"") == row) {
				return false;
			}
		}
		
		//check the left-up and -down diagonals
		for(int col = s.length() - 1; col >= 0; col --) {
			int r = Integer.parseInt(s.charAt(col)+"");
			if(r == row - (s.length() - col) || r == row + (s.length() - col)) {
				return false;
			}
		}
		
		//no need to check for right diagonals, since no right queens have been added
		return true;
	}
}
