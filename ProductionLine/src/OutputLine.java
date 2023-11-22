import java.awt.Point;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.NoninvertibleTransformException;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Represents an assembly line of Towers.
 * Functions as the output for the ProductionLine class.
 * Can create a graphical representation of itself as an assembly line that can move to the left as Disks are added.
 * @extends LinkedList&lt;Tower&gt;
 * @implements AssemblyLine
 * @author Rory McGuire
 */
public class OutputLine extends LinkedList<Tower> implements AssemblyLine {
	private static final long serialVersionUID = 1L;
	
	private final Point POS; //x pos of the leftmost point when at rest, the y of the top of the tread (not the top of the Towers)
	
	private boolean completed; //whether this has finished moving into position
	private int currentX; //actual position of the rightmost point while moving
	private int targetX; //The x that currentX is moving toward
	private int rotation; //the rotation of the cog in degrees
	
	/**
	 * Constructor for the OutputLine class; assigns the int value to this.y and instantiates all other fields
	 * @param y the value to be assigned to y - the y-pos of the tread
	 */
	public OutputLine(int y) {
		super();
		POS = new Point(600, y); 
		currentX = POS.x;
		targetX = currentX;
		completed = true;
		rotation = 0;
	}
	
	/**
	 * Draws this OutputLine: draws the Towers, the tread, and the cogs in the tread
	 * @param g the Graphics object to draw upon
	 */
	@Override
	public void draw(Graphics g) {
		//draw the Towers
		ListIterator<Tower> l = listIterator(size());
		int tempX = currentX;
		while(l.hasPrevious()) {
			Tower prev = l.previous();
			tempX += prev.getRadius() + BUFFER;
			prev.draw(tempX, POS.y, g);
			tempX += prev.getRadius();
		}
		
		//draw the assembly tread
		g.setColor(Color.BLACK);
		g.drawRoundRect(POS.x, POS.y, 330, HEIGHT, HEIGHT, HEIGHT);
		
		Graphics2D g2 = (Graphics2D)g;
		
		//draw the cogs
		for(double i = POS.x + HEIGHT * 0.5; i <= 930; i += HEIGHT * 1.2) {
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
	 * Updates this OutputLine. Will move this a step toward the targetX and increase the cogs' rotation by one step if necessary. Only will do something if currentX != targetX.
	 */
	@Override
	public void update() {
		if(currentX < targetX) {
			completed = false;
			currentX += SPEED;
			rotation += ROTATIONSPEED;
			rotation %= 360;
		} else {
			completed = true;
			currentX = targetX;
		}
	}
	
	/**
	 * Sets this up for moving forward to make room for a new Tower. Will set the targetX relative to the radius of the Tower to be added.
	 * @param rad the radius of the next Tower that will be added to this
	 */
	public void next(int rad) {
		targetX += rad*2 + BUFFER;
		completed = false;
	}
	
	/**
	 * Adds a new Tower to this. Resets the current x-pos of this to account for the new Tower.
	 * Inherited from LinkedList&lt;Tower&gt;
	 * @param t the Tower to be added to this
	 * @return boolean indicating whether this method was successful
	 */
	@Override
	public boolean add(Tower t) {
		currentX = POS.x;
		targetX = currentX;
		return super.add(t);
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
		String s = "(FOF) \n";//First-Out is displayed as "first" in the list
		int count = 0;
		for(Tower t : this) {
			s += "Tower " + count + ": " + t + "\n";
			count ++;
		}
		return s.trim();
	}
}
