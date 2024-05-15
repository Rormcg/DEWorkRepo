import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ChessSquarePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final Color LIGHT = new Color(255, 182, 89), DARK = new Color(150, 108, 53);
	public static final int SIZE = 50;
	
	private boolean isLight, hasQueen;
	
	ChessSquarePanel(boolean light, boolean queen) {
		super();
		isLight = light;
		hasQueen = queen;
		setBackground(isLight ? LIGHT : DARK);
		setSize(SIZE, SIZE);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(hasQueen) {
			g.setColor(Color.BLACK);
			g.setFont(new Font("monospace", Font.BOLD, (int)(SIZE * .7)));
			//System.out.println("H");
			g.drawString("\u2655", (int)(SIZE * .12), (int)(SIZE * .68));
			//g.drawString("Q", (int)(SIZE * .25), (int)(SIZE * .6));
		}
	}
	
	public void setHasQueen(boolean b) {
		hasQueen = b;
	}
	
	public boolean getIsLight() {
		return isLight;
	}
	
	public boolean getHasQueen() {
		return hasQueen;
	}
	
	public String toString() {
		return "ChessSquarePanel- isLight: " + isLight + ", hasQueen: " + hasQueen;
	}
}
