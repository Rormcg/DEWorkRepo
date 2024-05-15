import java.util.ArrayList;

public class EightQueens {
	
	private static ArrayList<String> solutions;
	
	public static void main(String[] args) {
		solutions = new ArrayList<String> ();
		
		//ChessBoard b = new ChessBoard("35716024");
		ChessBoard b = new ChessBoard();
		
		findAllSolutions();
		
		displaySolutions(b);
	}
	
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
	
	public static boolean canPlace(String s, int row) {
		//check no queens are in that row
		for(int i = 0; i < s.length(); i++) {
			if(Integer.parseInt(s.charAt(i)+"") == row) {
				return false;
			}
		}
		
		//check up-left diagonal
		for(int r = row - 1; r >= 0; r --) {
			int col = s.length() - (row - r);
			if(col < 0) break;
			if(Integer.parseInt(s.charAt(col)+"") == r) {
				return false;
			}
		}

		//check down-left diagonal
		for(int r = row + 1; r < ChessBoard.L; r ++) {
			int col = s.length() - (r - row);
			if(col < 0) break;
			if(Integer.parseInt(s.charAt(col)+"") == r) {
				return false;
			}
		}
		
		//no need to check for right diagonals, since no right queens have been added
		return true;
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
}
