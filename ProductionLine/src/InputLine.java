/**
 * @author Rory McGuire
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.NoninvertibleTransformException;
import java.util.LinkedList;
import java.util.ListIterator;

public class InputLine extends LinkedList<Disk> {
	private boolean completed;
	private int y; //the y of the top of the tread (not the top of the disks)
	private int x; //x pos of the rightmost point when at rest
	private int currentX; //actual position of the rightmost point while moving
	private int rotation; //the rotation of the cog in degrees
	
	public final int HEIGHT = 30; //the height of the tread
	public final int BUFFER = 12; //Space between elements in the line
	public final int SPEED = ProductionLine.SPEED / 2 + 1; //How fast the line will move
	public final int ROTATIONSPEED = SPEED * 2; //How fast the cog will spin
	
	public InputLine(int y) {
		super();
		this.y = y;
		x = 300; 
		currentX = x;
		completed = true;
		rotation = 0;
	}
	
	public void draw(Graphics g) {
		//draw the disks
		ListIterator<Disk> l = listIterator();
		int tempX = currentX;
		while(l.hasNext()) {
			Disk nxt = l.next();
			tempX -= nxt.getRadius() * 2 + BUFFER;
			nxt.draw(tempX, y - Disk.HEIGHT, g);
		}
		
		//draw the assembly tread
		g.setColor(Color.BLACK);
		g.drawRoundRect(-30, y, x + HEIGHT, HEIGHT, HEIGHT, HEIGHT);
		
		Graphics2D g2 = (Graphics2D)g;
		
		//draw the cogs
		for(double i = x - HEIGHT * 0.5; i >= -HEIGHT * 0.5; i -= HEIGHT * 1.2) {
			g2.translate(i, y + HEIGHT * 0.5);
			g2.rotate(Math.toRadians(rotation + i));
			g2.drawPolygon(new int[] {(int)(-HEIGHT * 0.4), (int)(-HEIGHT * 0.25), (int)(HEIGHT * 0.25), (int)(HEIGHT * 0.4), (int)(HEIGHT * 0.25), (int)(-HEIGHT * 0.25)},
					new int[] {0, (int)(-HEIGHT * 0.35), (int)(-HEIGHT * 0.35), 0, (int)(HEIGHT * 0.35), (int)(HEIGHT * 0.35)}, 6);
			g2.fillRect((int)(-HEIGHT * 0.06), (int)(-HEIGHT * 0.35), (int)(HEIGHT * 0.12), (int)(HEIGHT * 0.7));
			try {
				g2.transform(g2.getTransform().createInverse());
			} catch(NoninvertibleTransformException e) {
				e.printStackTrace();
			}
			//g2.translate(-i, -(y + HEIGHT * 0.5));
			//g2.rotate(Math.toRadians(-rotation));
		}
		
	}
	
	public void update() {
		if(currentX < x) {
			completed = false;
			currentX += SPEED;
			rotation += ROTATIONSPEED;
			rotation %= 360;
		} else {
			completed = true;
			currentX = x;
		}
	}
	
	@Override
	public Disk remove() {
		completed = false;
		currentX = x - getFirst().getRadius() * 2 - BUFFER;
		return super.remove();
	}
	
	public boolean getCompleted() {
		return completed;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public String toString() {
		String s = "(FOF) Disk Radii: [";//First-Out is displayed as "first" (index 0) in the list
		int count = 0;
		for(Disk d : this) {
			//s +=  count + ": ";
			s += d + ", ";
			count ++;
		}
		return s.substring(0, s.length() - 2) + "]";
	}
}
