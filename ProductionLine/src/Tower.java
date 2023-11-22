import java.awt.Graphics;
import java.util.Stack;

/**
 * Represents a Stack of Disks. Can be modified as a Stack, but can also be more easily incorporated into the ProductionLine class with time-saving methods.
 * Can create a graphical representation of itself as multiple Disks stacked on top of each other.
 * @extends Stack&lt;Disk&gt;
 * @author Rory McGuire
 */
public class Tower extends Stack<Disk> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor for instantiating a Tower. Calls the constructor for Stack&lt;Disk&gt;.
	 */
	public Tower() {
		super();
	}
	
	/**
	 * Using the Disk.compareTo() method, compares the top element of this Stack with another Disk.
	 * @param d
	 * @return
	 */
	public int compareTop(Disk d) {
		return peek().compareTo(d);
	}
	
	/**
	 * Returns a String representation of this Tower object.
	 * Will be in the format "(B-T) [x, y, z]" where x, y, and z are the radius values of the Disks
	 * contained in this Stack ordered from the bottom element to the top element of this Stack.
	 */
	@Override
	public String toString() {
		String s = "";
		for(Disk d : this) {
			s += d + ", "; 
		}
		
		//Bottom to Top
		return "(B-T) [" + s.substring(0, s.length() - 2) + "]";
	}
	
	/**
	 * Draws A graphical representation of this Tower based upon the parameters.
	 * Will be displayed as a pile of Disks
	 * @param x the x-pos of the image; will be the center x value of the Tower
	 * @param y the y-pos of the image; will be the bottom (greatest) y value of the Tower
	 * @param g the Graphics object to be drawn upon
	 */
	public void draw(int x, int y, Graphics g) {
		for(Disk d : this) {
			y -= Disk.HEIGHT;
			d.draw(x - d.getRadius(), y, g);
		}
	}
	
	/**
	 * Finds and returns the greatest radius among this Tower's Disks.
	 * @return the greatest radius found among this Tower's Disks
	 */
	public int getRadius() {
		int r = 0;
		for(Disk d : this) {
			if(r < d.getRadius()) {
				r = d.getRadius();
			}
		}
		return r;
	}
	
	/**
	 * Calculates and returns the height of this Tower
	 * @return the height of this Tower
	 */
	public int getHeight() {
		return size() * Disk.HEIGHT;
	}
	
	/**
	 * Empties all elements from this Tower.
	 */
	public void clear() {
		while(!empty()) {
			pop();
		}
	}
}
