/**
 * @author Rory McGuire
 */

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.ListIterator;

public class InputLine extends LinkedList<Disk> {
	private boolean isMoving;
	private int y;
	private int x;
	
	public final int HEIGHT = 10;
	public final int BUFFER = 2;
	
	public InputLine(int y) {
		super();
		this.y = y;
		x = 0;
		isMoving = false;
	}
	
	public void draw(Graphics g) {
		ListIterator<Disk> l = listIterator(size());
		int curX = x - (int)(0.5 * Disk.LARGEST + BUFFER + getFirst().getRadius());
		while(l.hasPrevious()) {
			g.drawRoundRect(curX, y, l.previous().getRadius() * 2, HEIGHT, 2, 2);
			// TO DO: curX -= 0;
		}
	}
	
	public void update() {
		
	}
	
	@Override
	public Disk remove() {
		isMoving = true;
		return super.remove();
	}

}
