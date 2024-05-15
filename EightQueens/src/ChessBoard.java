import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A JFrame containing an eight queens chess board layout; can be changed to fit any eight queens solution
 * @author Rory McGuire
 */
public class ChessBoard extends JFrame {
	private static final long serialVersionUID = 1L;


	public static final int W = 8, L = 8; //number of squares long/wide
	
	
	private ChessSquarePanel[][] squares;
	private JPanel board;
	
	/**
	 * Instantiates a new ChessBoard object; fills the board with empty ChessSquarePanels and calls the necessary JFrame methods to
	 * display the board
	 */
	public ChessBoard() {
		super("Chess Board");
		board = new JPanel(new GridLayout(W, L));
		
		squares = new ChessSquarePanel[L][W];
		for(int r = 0; r < L; r++) {
			for(int c = 0; c < W; c++) {
				board.add(squares[r][c] = new ChessSquarePanel((r * (L % 2 == 0 ? L + 1 : L) + c) % 2 == 0, false));
			}
		}
		getContentPane().add(board);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(W * ChessSquarePanel.SIZE, L * ChessSquarePanel.SIZE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Instantiates a new ChessBoard object and calls the relevant inherited JFrame methods but fills the new board
	 * with a pattern of empty and occupied spaces according to a given queens configuration
	 * @param s the configuration of queens; a String of ints of the row #s of the queens in each column
	 */
	public ChessBoard(String s) {
		this();
		createBoard(s);
	}
	
	/**
	 * Restructures this board to align to a given queens configuration and repaints the JPanel
	 * @param s the configuration of queens; a String of ints of the row #s of the queens in each column
	 */
	public void createBoard(String s) {
		for(int r = 0; r < L; r++) {
			for(int c = 0; c < W; c ++) {
				squares[r][c].setHasQueen(s.length() > c && Integer.parseInt(s.charAt(c)+"") == r);
				//System.out.println(r + ", " + c);
			}
		}
		repaint();
	}
	
	/**
	 * Returns a String representation of this ChessBoard; returns the configuration of queens on this
	 * @return the configuration of queens on this (a String of ints showing the row numbers of the queens in each column)
	 */
	public String toString() {
		String s = "";
		
		for(int c = 0; c < squares[0].length; c++) {
			for(int r = 0; r < squares.length; r++) {
				if(squares[r][c].getHasQueen()) {
					s += c;
					break;
				}
			}
			if(s.length() <= c) {
				s += "-";
			}
		}
		
		return s;
	}

}
