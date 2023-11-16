/**
 * @author Rory McGuire
 */

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
		int count = 0;
		for(Disk d : this) {
			s += "Disk " + count + ": " + d + " ";
			count ++;
		}
		return s.trim();
	}
	
}
