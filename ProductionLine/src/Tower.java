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
		for(Disk d : this) {
			s += d + ", "; 
		}
		
		//Bottom to Top
		return "(B-T) [" + s.substring(0, s.length() - 2) + "]";
	}
	
}
