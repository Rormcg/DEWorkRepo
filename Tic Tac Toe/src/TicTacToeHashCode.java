import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TicTacToeHashCode extends Board {

	boolean [] winners;  // True if the hash string that maps to this index is a winner, false otherwise

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

	// TODO - write the myHashCode function.  It must create a unique hashcode for all of the 
	//   possible values the game board (3 ^ 9) and it MUST use the super.charAt(row, col) function
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
	
	public boolean isWin() {
		String s = "";
		for(int r = 0; r < 3; r++) {
			for(int c = 0; c < 3; c ++) {
				s += charAt(r, c);
			}
		}
		return isWin(s);
	}

	public boolean isWin(String s) {
		if(s.length() != 9) return false;
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) != '*' &&
				s.charAt(i) != 'o' &&
				s.charAt(i) != 'x') {
				return false;
			}
		}
		return winners[hashCode(s)];
	}
	
	

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
		while(sc.hasNext()) {
			//s = kb.nextLine();
			s = sc.next();
			board.setBoardString(convertToBoardString(s));
			board.setHashCodeLabel(board.myHashCode());
			board.setWinner(board.isWin());
			board.show(board.getBoardString());
			System.out.println(s);
			Thread.sleep(2000);
			
			
			/*
			//  String currentBoard = board.boardValues[(int)(Math.random()* board.boardValues.length)];
			//  board.show(currentBoard);
			board.setHashCodeLabel(board.myHashCode());
			// TODO Update this line to call your isWin method.
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