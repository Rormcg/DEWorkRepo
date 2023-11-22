import java.awt.Color;
import java.awt.Graphics;

/**
 * Represents a single "disk" with a radius value.
 * To be used within InputLine, Tower, and RobotArm to represent an "element" containing a single int value.
 * @author Rory McGuire
 * @implements Comparable&lt;Disk&gt;
 */
public class Disk implements Comparable<Disk> {
	private int radius;
	
	public static final int LARGEST = 30;
	public static final int SMALLEST = 5;
	public static final int HEIGHT = 10;
	public final Color COLOR = Color.BLACK;
	
	/**
	 * Constructor to instantiate a Disk. Will create Disk with the radius value passed into this constructor.
	 * @param rad the radius of the Disk
	 */
	public Disk(int rad) {
		if(rad > LARGEST) throw new IllegalArgumentException("rad must be smaller than or equal to Disk.LARGEST");
		if(rad < SMALLEST) throw new IllegalArgumentException("rad must be greater than or equal to Disk.SMALLEST");
		radius = rad;
	}
	
	/**
	 * Draws a Graphical representation of this at the specified location.
	 * Will draw this Disk as a roundedRect of width 2*radius and height HEIGHT
	 * @param x the x-pos to draw the rect
	 * @param y the y-pos to draw the rect
	 * @param g the Graphics object to be drawn upon
	 */
	public void draw(int x, int y, Graphics g) {
		
		//System.out.println("H");
		g.setColor(COLOR);
		//g.fillOval(20, 20, 20, 20);
		g.fillRoundRect(x, y, radius * 2, HEIGHT, 2, 2);
	}
	
	/**
	 * Compares this Disk with another Disk. Performs (and returns) the operation: radius - d.radius
	 * @param d the Disk to be compared to this
	 * @return radius - d.radius (will be positive if this&gt;d, negative if this&lt;d, 0 if this==d)
	 */
	@Override
	public int compareTo(Disk d) {
		return radius - d.radius;
	}
	
	/**
	 * Returns a String representation of this Disk.
	 * @return String representation of this: will be a String containing this.radius
	 */
	@Override
	public String toString() {
		//return "Radius=" + radius;
		return ""+radius;
	}
	
	/**
	 * Getter for this.radius
	 * @return radius
	 */
	public int getRadius() {
		return radius;
	}
}
