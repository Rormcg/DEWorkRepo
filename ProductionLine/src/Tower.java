/**
 * @author Rory McGuire
 */

import java.awt.Graphics;
import java.util.Stack;

public class Tower extends Stack<Disk> {
	
	public Tower() {
		super();
	}
	
	public int compareTop(Disk d) {
		return peek().compareTo(d);
	}
	
	@Override
	public String toString() {
		String s = "";
		for(Disk d : this) {
			s += d + ", "; 
		}
		
		//Bottom to Top
		return "(B-T) [" + s.substring(0, s.length() - 2) + "]";
	}
	
	public void draw(int x, int y, Graphics g) {
		for(Disk d : this) {
			y -= Disk.HEIGHT;
			d.draw(x - d.getRadius(), y, g);
		}
	}
	
	public int getRadius() {
		int r = 0;
		for(Disk d : this) {
			if(r < d.getRadius()) {
				r = d.getRadius();
			}
		}
		return r;
	}
	
	public int size() {
		int s = 0;
		for(Disk d : this) {
			s++;
		}
		return s;
	}
	
	public int getHeight() {
		return size() * Disk.HEIGHT;
	}
	
	public void clear() {
		while(!empty()) {
			pop();
		}
	}
}
