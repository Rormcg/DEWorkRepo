import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

/**
 * Abstract class implementing JFrame.
 * Is a JFrame containing a graphical representation of a tic tac toe board;
 * displays its corresponding hashcode and whether it is a winner
 */
abstract class Board extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton buttons[][];
	private JLabel lblHashCode;
	private JLabel lblWinTitle;

	private String boardString = "";
	
	/**
	 * Instantiates a Board object
	 * @param title The name of the board object
	 */
	public Board(String title) {
		super(title);
		setupFrame();
	}

	/**
	 * Sets the hashcode displayed on the label
	 * @param hashcode the hashcode to be diplayed on the label
	 */
	public void setHashCodeLabel(int hashcode) {
		lblHashCode.setText("" + hashcode);
	}
	
	/**
	 * Sets whether the position is a winning one or not
	 * @param result whether the configuration is a winner
	 */
	public void setWinner(String result) {
		lblWinTitle.setText(result);
	}
	
	/**
	 * Sets whether the position is a winning one or not
	 * @param result whether the current configuration is a win
	 */
	public void setWinner(boolean result) {
		if (result)
			setWinner("Winner");
		else
			setWinner("Loser");
	}
	
	//  required because of abstract method, but not used   
	@Override
	public void actionPerformed(ActionEvent e) { }
	
	/**
	 * Initiates the top JPanel containing the title, hashcode, and win boolean
	 * @return the JPanel
	 */
	JPanel setupPanelOne() {
		JPanel panel = new JPanel();
		JLabel lblHCTitle = new JLabel("Hash Code");;
		lblHashCode = new JLabel("" + myHashCode());
		lblWinTitle = new JLabel(""); // Will say either Winner or Loser 
		setWinner(TicTacToe.isWin(boardString));
		panel.setLayout(new FlowLayout());    
		panel.add(lblHCTitle);
		panel.add(lblHashCode);  
		panel.add(lblWinTitle);  
		return panel;
	}
	
	/**
	 * Initiates the bottom JPanel, containing the tic-tac-toe board configuration
	 * @return the JPanel
	 */
	private JPanel setupPanelTwo() {
		JButton b;
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(TicTacToe.ROWS,TicTacToe.COLS));
		buttons = new JButton[TicTacToe.ROWS][TicTacToe.COLS];

		//int count = 1;

		for (int r = 0; r < TicTacToe.ROWS; r++) 
			for (int c = 0; c < TicTacToe.COLS; c++) {
				char ch = randomXO();           
				b = new JButton("" + ch);
				boardString += ch;
				b.setActionCommand("" + r + ", " + c);
				b.addActionListener(
						new ActionListener(){  
							public void actionPerformed(ActionEvent e){  
								JButton btn = (JButton) e.getSource();
								btn.setText("" + cycleValue(btn.getText().charAt(0)));
								resetBoardString();
								lblHashCode.setText("" + myHashCode());
							}  
						});              
				panel.add(b);
				buttons[r][c] = b;           
			}

		return panel;
	}
	
	/**
	 * Cycles through the sequence of chars: {'x', 'o', ' '}, outputting the next char in the sequence after the input char
	 * @param ch the current char that will determine the proceeding char which will be returned
	 * @return the next char in the sequence {'x', 'o', ' '} after ch
	 */
	private static char cycleValue(char ch) {
		switch (ch) {
		case 'x' : 
			return 'o';
		case 'o' : 
			return ' ';
		default  : 
			return 'x';
		}
	}
	
	/**
	 * Sets up the JFrame containing both JPanels (The entire graphics for the TTT Board)
	 */
	private void setupFrame() {
		JPanel panel2 = new JPanel();

		// Setup Frame
		super.setSize(250, 200);  
		super.setLocationRelativeTo(null);  
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		super.setLayout(new BoxLayout(getContentPane(),BoxLayout.PAGE_AXIS));  

		// Setup Panels
		panel2 = setupPanelTwo();  //panelOne displays a value that requires panelTwo to be ready
		super.add(setupPanelOne());     
		super.add(panel2);  

		super.setVisible(true);  
	}
	
	/**
	 * Returns a random char out of {'x', 'o', ' '}
	 * @return a random char from the selection: {'x', 'o', ' '}
	 */
	private char randomXO() {
		int rnd = (int) (Math.random()*TicTacToe.CHAR_POSSIBILITIES);
		switch(rnd) {
		case 1 : 
			return 'x';
		case 2 : 
			return 'o';
		default: 
			return ' ';
		}
	}
	abstract int myHashCode();
	
	/**
	 * Returns the character found at the given row and column
	 * @param row the row of the char
	 * @param col the column of the character
	 * @return the char at (row, col), if there is none, returns '*'
	 */
	public char charAt(int row, int col) {
		String value = buttons[row][col].getText();
		if (value.length() > 0)
			return value.charAt(0);
		else
			return '*';
	}
	
	/**
	 * Converts a given String board configuration to a series of 0s, 1s, and 2s, representing ' 's, x's, and o's (respectively)
	 * To be used when converting to hashcode or for being used as the board configuration for this Board
	 * @param s the String representative of the TicTacToeHashCode
	 * @return the new String replaced with 0, 1, and 2
	 */
	public static String convertToBoardString(String s) {
		String s2 = "";
		for(int i = 0; i < s.length(); i++) {
			switch(s.charAt(i)) {
				case 'o':
				case 'O':
					s2 += "2";
					break;
				case 'x':
				case 'X':
					s2 += "1";
					break;
				case '*':
				default:
					s2 += "0";
			}
		}
		return s2;
	}
	
	/**
	 * Displays the current board configuration on the JPanel
	 * @param s the current Board configuration as a String
	 */
	public void show(String s) {
		int pos = 0;
		//String p = "";
		String letter;
		for (int r = 0; r < TicTacToe.ROWS; r++)
			for (int c = 0; c < TicTacToe.COLS && r*3+c < s.length(); c++){
				char ch = s.charAt(pos);
				switch (ch) {
				case '1' : letter = "x"; 
				break;
				case '2' : letter = "o"; 
				break;
				case '0'  : letter = " "; 
				break;
				default : letter = "" + ch;
				//xx: letter = " ";
			}

				buttons[r][c].setText(letter);
				//p += letter;
				pos++;
		}
	}
	
	/**
	 * Resets boardString to the current actual board configuration
	 */
	public void resetBoardString() {
		for (int r = 0; r < TicTacToe.ROWS; r++)
			for (int c = 0; c < TicTacToe.COLS; c++){
				boardString += buttons[r][c].getText();
			}
	} 
	
	/**
	 * Sets boardString to s
	 * @param s the value to set boardString to
	 */
	public void setBoardString(String s) {
		boardString = s;
		show(s);
	}
	
	/**
	 * Returns boardString
	 * @return boardString
	 */
	public String getBoardString() {
		return boardString;
	}


}