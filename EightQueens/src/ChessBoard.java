import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChessBoard extends JFrame {
	private static final long serialVersionUID = 1L;


	public static final int W = 8, L = 8; //number of squares long/wide
	
	
	private ChessSquarePanel[][] squares;
	private JPanel board;
	
	public ChessBoard() {
		super("Chess Board");
		board = new JPanel(new GridLayout(W, L));
		
		squares = new ChessSquarePanel[L][W];
		for(int r = 0; r < L; r++) {
			for(int c = 0; c < W; c++) {
				board.add(squares[r][c] = new ChessSquarePanel((r * (L + 1) + c) % 2 == 0 ? true : false , false));
			}
		}
		getContentPane().add(board);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(W * ChessSquarePanel.SIZE, L * ChessSquarePanel.SIZE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public ChessBoard(String s) {
		this();
		createBoard(s);
	}
	
	public void createBoard(String s) {
		for(int r = 0; r < L; r++) {
			for(int c = 0; c < W; c ++) {
				squares[r][c].setHasQueen(s.length() > c && Integer.parseInt(s.charAt(c)+"") == r ? true : false);
				//System.out.println(r + ", " + c);
			}
		}
		repaint();
	}

}
