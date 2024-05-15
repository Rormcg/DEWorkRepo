import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * A single chess square, which can be either a dark or light square, with or without a Queen.
 * As a JPanel, it can be added to a frame and drawn
 * @author Rory McGuire
 */
public class ChessSquarePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final Color LIGHT = new Color(255, 182, 89), DARK = new Color(150, 108, 53);
	public static final int SIZE = 50;
	
	private boolean isLight, hasQueen;
	
	/**
	 * 
	 * @param light
	 * @param queen
	 */
	ChessSquarePanel(boolean light, boolean queen) {
		super();
		isLight = light;
		hasQueen = queen;
		setBackground(isLight ? LIGHT : DARK);
		setSize(SIZE, SIZE);
	}
	
	/**
	 * 
	 * @param
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(hasQueen) {
			g.setColor(Color.BLACK);
			g.setFont(new Font("monospace", Font.BOLD, (int)(SIZE * .7)));
			//System.out.println("H");
			g.drawString("\u2655", (int)(SIZE * .128), (int)(SIZE * .72));
			//g.drawString("Q", (int)(SIZE * .25), (int)(SIZE * .6));
		}
	}
	
	/**
	 * Sets the value of hasQueen
	 * @param b the boolean value to assign to hasQueen
	 */
	public void setHasQueen(boolean b) {
		hasQueen = b;
	}
	
	/**
	 * Returns whether this square is a light square
	 * @return isLight
	 */
	public boolean getIsLight() {
		return isLight;
	}
	
	/**
	 * Returns whether this square has a queen on it
	 * @return hasQueen
	 */
	public boolean getHasQueen() {
		return hasQueen;
	}
	
	/**
	 * Returns a String representation of this object
	 * @return a String representation of this object: "ChessSquarePanel- isLight: &lt;isLight&gt;, hasQueen: &lt;hasQueen&gt;"
	 */
	public String toString() {
		return "ChessSquarePanel- isLight: " + isLight + ", hasQueen: " + hasQueen;
	}
}
