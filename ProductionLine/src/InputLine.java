/**
 * @author Rory McGuire
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.NoninvertibleTransformException;
import java.util.LinkedList;
import java.util.ListIterator;

public class InputLine extends LinkedList<Disk> implements AssemblyLine {
	private static final long serialVersionUID = 1L;
	
	private final Point POS; //x pos of the rightmost point when at rest, the y of the top of the tread (not the top of the disks)
	
	private boolean completed;
	private int currentX; //actual position of the rightmost point while moving
	private int rotation; //the rotation of the cog in degrees
	
	/**
	 * Constructor for the InputLine class; assigns the int value to this.y and instantiates all other fields
	 * @param y the value to be assigned to y - the y-pos of the tread
	 */
	public InputLine(int y) {
		super();
		POS = new Point(300, y);
		currentX = POS.x;
		completed = true;
		rotation = 0;
	}
	
	/**
	 * Draws this InputLine: draws the Towers, the tread, and the cogs in the tread
	 * @param g the Graphics object to draw upon
	 */
	@Override
	public void draw(Graphics g) {
		//draw the disks
		ListIterator<Disk> l = listIterator();
		int tempX = currentX;
		while(l.hasNext()) {
			Disk nxt = l.next();
			tempX -= nxt.getRadius() * 2 + BUFFER;
			nxt.draw(tempX, POS.y - Disk.HEIGHT, g);
		}
		
		//draw the assembly tread
		g.setColor(Color.BLACK);
		g.drawRoundRect(-30, POS.y, POS.x + HEIGHT, HEIGHT, HEIGHT, HEIGHT);
		
		Graphics2D g2 = (Graphics2D)g;
		
		//draw the cogs
		for(double i = POS.x - HEIGHT * 0.5; i >= -HEIGHT * 0.5; i -= HEIGHT * 1.2) {
			g2.translate(i, POS.y + HEIGHT * 0.5);
			g2.rotate(Math.toRadians(rotation + i));
			g2.drawPolygon(new int[] {(int)(-HEIGHT * 0.4), (int)(-HEIGHT * 0.25), (int)(HEIGHT * 0.25), (int)(HEIGHT * 0.4), (int)(HEIGHT * 0.25), (int)(-HEIGHT * 0.25)},
					new int[] {0, (int)(-HEIGHT * 0.35), (int)(-HEIGHT * 0.35), 0, (int)(HEIGHT * 0.35), (int)(HEIGHT * 0.35)}, 6);
			g2.fillRect((int)(-HEIGHT * 0.06), (int)(-HEIGHT * 0.35), (int)(HEIGHT * 0.12), (int)(HEIGHT * 0.7));
			try {
				g2.transform(g2.getTransform().createInverse());
			} catch(NoninvertibleTransformException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Updates this OutputLine. Will move this a step toward x and increase the cogs' rotation by one step if necessary. Only will do something if currentX != x.
	 */
	@Override
	public void update() {
		if(currentX < POS.x) {
			completed = false;
			currentX += SPEED;
			rotation += ROTATIONSPEED;
			rotation %= 360;
		} else {
			completed = true;
			currentX = POS.x;
		}
	}
	
	/**
	 * Removes the next Disk element from this, then prepares this to begin moving. Will tell this to move to the right by an amount relative to the radius of the removed Disk.
	 * @return boolean indicating whether this method successfully removed the element
	 */
	@Override
	public Disk remove() {
		completed = false;
		currentX = POS.x - getFirst().getRadius() * 2 - BUFFER;
		return super.remove();
	}
	
	/**
	 * Returns this.completed (whether this has finished its motion)
	 * @return completed
	 */
	@Override
	public boolean getCompleted() {
		return completed;
	}
	
	/**
	 * Returns this.POS.x (the constant leftmost position of this)
	 * @return POS.x
	 */
	@Override
	public int getX() {
		return POS.x;
	}
	
	/**
	 * Returns this.POS.y (the constant topmost position of the tread - not the top of the Towers)
	 * @return POS.y
	 */
	@Override
	public int getY() {
		return POS.y;
	}
	
	/**
	 * Returns a String representation of this. Will return a formatted list of the Towers contained in this in First-Out-First order.
	 * Format of String should be: "(FOF) \nTower 0: [1, 2, 3]\nTower 1: [6, 7, 8]"...etc.
	 * @return String representation of this
	 */
	@Override
	public String toString() {
		String s = "(FOF) Disk Radii: [";//First-Out is displayed as "first" (index 0) in the list
		//int count = 0;
		for(Disk d : this) {
			//s +=  count + ": ";
			s += d + ", ";
			//count ++;
		}
		return s.substring(0, s.length() - 2) + "]";
	}
}
