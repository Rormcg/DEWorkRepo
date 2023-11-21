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
		
	}
	
	public int getHeight() {
		int h = 0;
		for(Disk d : this) {
			h += Disk.HEIGHT;
		}
		return h;
	}
	
}
