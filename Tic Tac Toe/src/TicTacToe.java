


/**
 * Class containing series of static methods to be used with manipulating Tic Tac Toe board configurations.
 * Includes methods for converting configurations and checking if they are valid or winning positions.
 */
public class TicTacToe {
	public final static int ROWS = 3;
	public final static int COLS = 3;
	public final static int POSSIBILITIES = (int) Math.pow(3,9);
	public final static int CHAR_POSSIBILITIES = 3; // x, o or space

	/**
	 * returns the number of a given character contained within a given board configuration
	 * @param b the board configuration to be analyzed
	 * @param ch the character to be counted
	 * @return the number of times ch appears in b
	 */
	private static int numChars(char[][] b, char ch) {
		int total = 0;
		for (int r = 0; r < ROWS; r++)
			for (int c = 0; c < COLS; c++)
				if (ch == b[r][c]) 
					total++;
		return total;
	}
	
	/**
	 * Checks whether a given board configuration is a valid TTT position that has the potential to be a winning position:
	 * checks that there are at least three of one character, and only a difference of one in frequency between x's and o's
	 * @param board the configuration to be checked
	 * @return Whether the board position has the potential to be a valid winning position
	 */
	public static boolean valid(char[][] board) {

		// Ensure there are at least 3 xs and 2 os, or 3 os and 2 xs
		// Make sure there are at least one more x or one more o
		int numX = numChars(board, 'x');
		int numO = numChars(board, 'o');
		if (! (numX > 2 || numO > 2)) 
			return false;
		if ((numX == numO + 1) || (numO == numX + 1)) 
			return true;
		return false;
	}
	
	/**
	 * Connverts a board configuration to a String and returns it
	 * @param b the board configuration to be converted to a String
	 * @return a String representation of b
	 */
	public static String boardToString(char[][] b) {
		String result = "";
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++) 
				result += b[r][c];
			//     result += "\n";
		}
		return result;
	}
	
	/**
	 * Takes in a String representation of a board configuration and returns the a char[][] equivalent
	 * @param board the String representation of the char[][] board configuration
	 * @return the char[][] board configuration equivalent of the input String
	 */
	public static char[][] stringToBoard(String board) {
		char[][] b = new char[ROWS][COLS];
		int index = 0;
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++) 
				b[r][c] = whichLetter(board.charAt(index++));
		}
		return b;
	}

	/**
	 * Returns the char equivalent of a given number char from {0-2}:
	 * 1=x, 2=o, 0=' '
	 * @param ch the number (as a char) to be checked
	 * @return the associated TTT char for the given number ch
	 */
	public static char whichLetter(char ch) {
		switch (ch) {
		case '1' : 
			return 'x';
		case '2' : 
			return 'o';
		case '0'  : 
			return ' ';
		default: 
			return ch;
		}
	}
	
	/**
	 * Takes in a String representation of a board configuration and returns the a char[][] equivalent
	 * @param board the String representation of the char[][] board configuration
	 * @return the char[][] board configuration equivalent of the input String
	 */
	public static char[][] makeBoard(String s) {
		char[][] b = new char[ROWS][COLS];
		int ch = 0;
		for (int r = 0; r < ROWS; r++)
			for (int c = 0; c < COLS; c++){         
				b[r][c] = whichLetter(s.charAt(ch));
				ch++;
			}
		return b;
	}
	
	/**
	 * s is a 9 character string, composed of 0s, 1s, and 2s.  Add 1 to the last char, adjusting 
	 * all the rest of the characters as necessary.
	 * @param s 9 character string, composed of 0s, 1s, and 2s
	 * @return s with 1 added to the last char and adjusted as necessary
	 */
	private static String addOne(String s) {
		// s is a 9 character string, composed of 0s, 1s, and 2s.  Add 1 to the last char, adjusting
		// all the rest of the characters as necessary.
		boolean carry = false;
		char ch[] = s.toCharArray();
		ch[ch.length-1] =  (char)((int)(ch[ch.length-1])+1);
		for (int n = ch.length-1; n >=0; n--) {
			if (carry) ch[n] = (char)((int)(ch[n])+1);
			if (ch[n] == '3') {
				carry = true;
				ch[n] = '0';
			}
			else
				carry = false;      
		}
		return new String(ch);
	}

	/**
	 * Returns a String[] array containing every board configuration as each a 9-char string composed of 0s, 1s, and 2s
	 * @return String[] with all potential board configurations as 9-char Strings of 0s, 1s, and 2s
	 */
	public static String[] fillValues() {
		String strBoard = "000000000";
		String[] values = new String[POSSIBILITIES];
		int index = 0;
		values[index++] = strBoard;
		while (!strBoard.equals("222222222") ) {
			strBoard = addOne(strBoard);
			values[index++] = strBoard;
		}
		return values;
	}
	
	/**
	 * Checks if a given board configuration has a three-in-a-row diagonally for either x's or o's
	 * @param board the board configuration to be analyzed
	 * @return whether the given configuration has a diagonal win
	 */
	public static boolean diagonalWin(char[][] board) {
		int wins = 0;
		if ((board[0][0] == 'x' && board[1][1] == 'x' && board[2][2] == 'x') || 
				(board[0][0] == 'o' && board[1][1] == 'o' && board[2][2] == 'o')) 
			wins++;


		if ((board[0][2] == 'x' && board[1][1] == 'x' && board[2][0] == 'x') ||
				(board[0][2] == 'o' && board[1][1] == 'o' && board[2][0] == 'o')) 
			wins++;

		return wins == 1;
	}
	
	/**
	 * Checks if a given board configuration has exactly one three-in-a-row win horizontally across a row
	 * @param board the board configuration to be analyzed
	 * @return whether the board configuration has exactly one horizontal win
	 */
	public static boolean rowWin(char[][] board) {
		char ch;
		int wins = 0;
		boolean win = true;
		for (int r = 0; r < ROWS; r++) {
			win = true;
			ch = board[r][0];
			for (int c = 0; c < COLS; c++) 
				if (ch != board[r][c]) {
					win = false;
					break;
				}
			if (win && ch != ' ')
				wins++;
		} 
		return wins == 1;
	}
	
	/**
	 * Checks if a given board configuration has exactly one three-in-a-row win vertically across a column
	 * @param board the board configuration to be analyzed
	 * @return whether the board configuration has exactly one vertical win
	 */
	public static boolean colWin(char[][] board) {
		char ch;
		int wins = 0;
		boolean win = true;
		for (int c = 0; c < COLS; c++) {
			win = true;
			ch = board[0][c];
			for (int r = 0; r < ROWS; r++) 
				if (ch != board[r][c]) {
					win = false;
					break;
				}
			if (win && ch != ' ') 
				wins++;
		} 
		return wins == 1;
	}
	
	/**
	 * Checks whether a given board configuration is a valid position and has exactly one three-in-a-row win
	 * @param b the board configuration to be analyzed
	 * @return whether the board is valid and has exactly one win
	 */
	public static boolean isWin(char[][]b) {
		int wins = 0;
		if (valid(b)) {
			if (rowWin(b)) wins++;
			if (colWin(b)) wins++;
			if (diagonalWin(b)) wins++;
		}
		return wins == 1;
		//     return valid(b) && (rowWin(b) ^ colWin(b) ^ diagonalWin(b));
	}

	/**
	 * Checks whether a given board configuration is a valid position and has exactly one three-in-a-row win.
	 * Specifies whether the win was vertically, horizontally, or diagonally, or if it was not a win
	 * @param b the board configuration to be analyzed
	 * @return "loser" if there is no win, otherwise will specify "Row", "col", or "Dia" depending on the type of win
	 */
	public static String isWinString(char[][]b) {
		boolean v = valid(b);
		if (v) {
			boolean r = rowWin(b);
			boolean c = colWin(b);
			boolean d = diagonalWin(b);
			int wins = 0;
			if (r) wins++;
			if (c) wins++;
			if (d) wins++;
			if (wins == 1) {
				if (r) 
					return "Row";
				if (c) 
					return "Col";
				if (d) 
					return "Dia";
				return "winner";

			}
		}   
		return "loser";
	}
	
	/**
	 * Checks whether a given board configuration (given in String form) is a valid position and has exactly one three-in-a-row win
	 * @param b the board configuration (in String form) to be analyzed
	 * @return whether the board is valid and has exactly one win
	 */
	public static boolean isWin(String s) {
		char[][] b = stringToBoard(s);
		return isWin(b);
	}
	
	/**
	 * Checks whether a given board configuration (given in String form) is a valid position and has exactly one three-in-a-row win.
	 * Specifies whether the win was vertically, horizontally, or diagonally, or if it was not a win
	 * @param b the board configuration (in String form) to be analyzed
	 * @return "loser" if there is no win, otherwise will specify "Row", "col", or "Dia" depending on the type of win
	 */
	public static String isWinString(String s) {
		char[][] b = stringToBoard(s);
		return isWinString(b);
	}
	
	/**
	 * Has a board configuration and passes it though a series of tests to observe what each win-check method returns
	 * when it is passed to them. Prints the behavior of the methods
	 * @param args the command line arguments to be passed to this method. Ignored in this implementation.
	 */
	public static void main(String[] args) {
		String s = "ooooxxxox";
		char[][] b = stringToBoard(s);
		System.out.println("Valid:   " + valid(b));
		System.out.println("Row Win: " + rowWin(b));
		System.out.println("Col Win: " + colWin(b));
		System.out.println("Dia Win: " + diagonalWin(b));
		System.out.println(isWin(b));

	}    
}
