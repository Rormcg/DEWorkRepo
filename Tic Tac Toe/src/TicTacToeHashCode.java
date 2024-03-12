//TODO Make sure you remove all of the TODO comments from this file before turning itin

import java.io.File;
import java.io.IOException;
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
		while(sc.hasNext()) {
			winners[hashCode(sc.next())] = true;
		}
	}

	// TODO - write the myHashCode function.  It must create a unique hashcode for all of the 
	//   possible values the game board (3 ^ 9) and it MUST use the super.charAt(row, col) function
	@Override
	public int myHashCode() {
		int hash = 0;
		for(int r = 0; r < 3; r ++) {
			for(int c = 0; c < 3; c++) {
				hash += Integer.parseInt(charAt(r, c)+"") * Math.pow(3,  r * 3 + c);
			}
		}
		
		return hash;
	}
	
	public static int hashCode(String position) {
		int hash = 0;
		while(position.length() > 0) {
			hash += Integer.parseInt(position.substring(0,1)) * Math.pow(3, position.length() - 1);
			position = position.substring(1);
		}
		return hash;
	}
	public boolean isWin() {
		return false;
	}

	public boolean isWin(String s) {
		// return the value in the winner array for the hash chode of the board string sent in.
		return true;
	}

	public static void main(String[] args) throws InterruptedException {
		TicTacToeHashCode board = new TicTacToeHashCode ("Tic Tac Toe");
		String s = "";
		java.util.Scanner kb = new java.util.Scanner(System.in);

		while (true) {
			s = kb.nextLine();
			//    String currentBoard = board.boardValues[(int)(Math.random()* board.boardValues.length)];
			//    board.show(currentBoard);
			//    board.setHashCode(board.myHashCode());
			// TODO Update this line to call your isWin method.
			//    board.setWinner(TicTacToe.isWin(currentBoard));
			TicTacToe ttt = new TicTacToe();
			System.out.println(ttt.isWin(s));
			board.show(s);
			//     Thread.sleep(4000); 
			//       board.displayRandomString();      


		}
	}
}