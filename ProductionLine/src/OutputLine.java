/**
 * @author Rory McGuire
 */

import java.util.LinkedList;

import java.awt.Graphics;

public class OutputLine extends LinkedList<Tower> {
	private int height;
	private boolean completed;
	
	public OutputLine(int y) {
		super();
		height = y;
		completed = true;
	}
	
	public void draw(Graphics g) {
		
	}
	
	public void update() {
		
	}
	
	public boolean getCompleted() {
		return completed;
	}
}
