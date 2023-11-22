import java.awt.Color;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * Main class for the ProductionLine Project.
 * Organizes the project components into a cohesive unit; however DOES NOT run the program.
 * Specifically, ProductionLine contains an InputLine, an OutputLine, and a RobotArm
 * Contains functionality for both a graphical and a command-line version of ProductionLine.
 * For the graphical portion, ProductionLine holds a JFrame and associated objects necessary for displaying graphics.
 * Additionally, this class contains a Timer, which it uses with its implementation of ActionListener in order to coordinate changes/updates to this class's components
 * and to repaint the canvas.
 * NOTE: The speed of this class (SPEED) can be increased indefinitely (within typical java constraints) without breaking the code.
 * @extends ActionListener
 * @author Rory McGuire
 */

public class ProductionLine extends JComponent implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private InputLine input;
	private OutputLine output;
	private RobotArm robotArm;
	
	private JFrame frame;
	private Timer timer;
	
	private boolean completed;
	
	public final static int SPEED = 8;
	public final int WIDTH = 900, HEIGHT = 400;
	public final int TREADHEIGHT = 300;
	
	/**
	 * Constructor for a ProductionLine object. Initializes the fields of the new object, and determines whether the object will be used for Graphics.
	 * If to be used for Graphics, this object will create and display a JFrame to display Graphics.
	 * @param graphics whether this ProductionLine will run with Graphics
	 */
	public ProductionLine(boolean graphics) {
		input = new InputLine(TREADHEIGHT);
		output = new OutputLine(TREADHEIGHT);
		robotArm = new RobotArm(WIDTH / 2, HEIGHT / 20, input, output);
		completed = false;
		
		if(graphics) {
			frame = new JFrame("ProductionLine");
			
			frame.setSize(WIDTH + 17, HEIGHT + 40);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			
			Container content = frame.getContentPane();
			content.setBackground(Color.WHITE);
			content.add(this);
			
			timer = new Timer(1, this);
		}
	}
	
	/**
	 * Called when this should begin to run.<br> Begins the updating process for this: calls timer.start().
	 * (Used to avoid conflicts due to back-end multi-threading with this.actionPerformed() being called at the same time that input Disks are being added)
	 */
	public void start() {
		timer.start();
	}
	
	/**
	 * Adds a Disk to input
	 * @param d the Disk to be added
	 */
	public void addDisk(Disk d) {
		input.add(d);
	}
	
	/**
	 * "Unloads" robotArm. Reverses robotArm's Tower and places the new Tower into output.
	 * To be used with the non-graphical implementation of ProductionLine.
	 */
	public void unloadRobot() {
		Tower newTower = new Tower();
		//System.out.print("Robot" + robotArm);
		while(!robotArm.getTower().empty()) {
			newTower.push(robotArm.getTower().pop());
		}
		output.add(newTower);
		//System.out.println(" NewTower" + newTower);
	}
	/**
	 * Runs the algorithm to sort input Disks into ascending Towers in output.
	 * Places Disks into robotArm.tower until the Disk is smaller than the top Disk on robotArm.tower. Then flips the Tower and places it in output.
	 * To be used with the non-graphical implementation.
	 */
	public void process() {
		while(!input.isEmpty()) {
			if(robotArm.getTower().empty() || robotArm.getTower().compareTop(input.peek()) <= 0) {
				robotArm.getTower().push(input.remove());
			} else {
				unloadRobot();
			}
		}
	}
	
	/**
	 * Removes and returns a Tower from output
	 * @return the removed Tower
	 */
	public Tower removeTower() {
		return output.remove();
	}
	
	/**
	 * Getter for output.isEmpty(). Returns whether output has no elements.
	 * @return output.isEmpty()
	 */
	public boolean outputIsEmpty() {
		return output.isEmpty();
	}
	
	/**
	 * Draws the components of this: Draws the InputLine, the OutputLine, and the RobotArm.
	 * @param g the Graphics object to be drawn upon
	 */
	@Override
	public void paintComponent(Graphics g) {
		//System.out.println(input);
		input.draw(g);
		output.draw(g);
		robotArm.draw(g);
	}
	
	/**
	 * Updates the components of this, repaints them, and then verifies if this process has been completed.
	 * If so, will end the Timer causing this method to be called and thus will stop updating.
	 * @param e ActionEvent that triggers this method to be called. Will be this.timer firing.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		input.update();
		output.update();
		robotArm.update();
		repaint();
		
		if(robotArm.completed() && completed != true) { //when the process is done
			//print out the output:
			printOutput();
			completed = true;
			timer.stop();
		}
	}
	
	/**
	 * Prints out a String representation of output.
	 */
	public void printOutput() {
		System.out.println("Output: " + output);
	}
	
	/**
	 * Prints out a String representation of input.
	 */
	public void printInput() {
		System.out.println("Input: \n" + input);
	}
}
