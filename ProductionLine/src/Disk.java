/**
 * @author Rory McGuire
 */

import java.awt.Color;
import java.awt.Graphics;

public class Disk implements Comparable<Disk> {
	private int radius;
	
	public static final int LARGEST = 30;
	public final int HEIGHT = 10;
	public final Color COLOR = Color.BLACK;
	
	public Disk(int rad) {
		if(rad >= LARGEST) throw new IllegalArgumentException("rad must be smaller than or equal to Disk.Largest");
		radius = rad;
	}
	
	public void draw(int x, int y, Graphics g) {
		
		//System.out.println("H");
		g.setColor(COLOR);
		//g.fillOval(20, 20, 20, 20);
		g.fillRoundRect(x, y, radius * 2, HEIGHT, 2, 2);
	}
	
	@Override
	public int compareTo(Disk d) {
		return radius - d.radius;
	}
	
	@Override
	public String toString() {
		//return "Radius=" + radius;
		return ""+radius;
	}
	
	public int getRadius() {
		return radius;
	}
}
