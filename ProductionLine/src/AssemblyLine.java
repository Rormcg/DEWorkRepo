/**
 * @author Rory McGuire
 * Interface to be used as template for InputLine and OutputLine class
 */

import java.awt.Graphics;

public interface AssemblyLine {
	public final int HEIGHT = 30; //the height of the tread
	public final int BUFFER = 12; //Space between elements in the line
	public final int SPEED = ProductionLine.SPEED / 2 + 1; //How fast the line will move
	public final int ROTATIONSPEED = SPEED * 2; //How fast the cog will spin
	
	public void draw(Graphics g);
	public void update();
	
	public int getX();
	public int getY();
	public boolean getCompleted();
	
	@Override
	public String toString();

}
