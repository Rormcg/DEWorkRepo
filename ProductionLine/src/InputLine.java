/**
 * @author Rory McGuire
 */

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.ListIterator;

public class InputLine extends LinkedList<Disk> {
	private boolean completed;
	private int y;
	private int x;
	
	public final int BUFFER = 2; //Space between elements in the line
	
	public InputLine(int y) {
		super();
		this.y = y;
		x = 0;
		completed = true;
	}
	
	public void draw(Graphics g) {
		ListIterator<Disk> l = listIterator(size());
		int curX = x;// - (int)(0.5 * Disk.LARGEST + BUFFER);
		while(l.hasPrevious()) {
			Disk prev = l.previous();
			curX -= prev.getRadius() + (int)(Disk.LARGEST + BUFFER);
			//g.fillOval(20, 20, 20, 20);
			prev.draw(curX, y, g);
			curX -= Disk.LARGEST - prev.getRadius();
		}
	}
	
	public void update() {
		
	}
	
	@Override
	public Disk remove() {
		completed = false;
		return super.remove();
	}
	
	public boolean getCompleted() {
		return completed;
	}
}
