import java.awt.Graphics;

/**
 * Interface to be used as template for InputLine and OutputLine class.<br>
 * Contains the methods and constants shared by the two classes.
 * @author Rory McGuire
 */
public interface AssemblyLine {
	public final int HEIGHT = 30; //the height of the tread
	public final int BUFFER = 12; //Space between elements in the line
	public final int SPEED = ProductionLine.SPEED / 2 + 1; //How fast the line will move
	public final int ROTATIONSPEED = SPEED * 2; //How fast the cog will spin
	
	/**
	 * Draw this object on Graphics g
	 * @param g the Graphics object to be drawn upon
	 */
	public void draw(Graphics g);
	/**
	 * update the values of this - to be called before every repaint();
	 */
	public void update();
	
	/**
	 * Getter for x
	 * @return x
	 */
	public int getX();
	
	/**
	 * Getter for y
	 * @return y
	 */
	public int getY();
	
	/**
	 * Getter for completed
	 * @return completed
	 */
	public boolean getCompleted();
	
	/**
	 * Creates and returns a String representation of this
	 * @return String representation of this
	 */
	@Override
	public String toString();

}
