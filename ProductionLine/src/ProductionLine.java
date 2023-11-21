/**
 * @author Rory McGuire
 */


import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class ProductionLine extends JComponent implements ActionListener {
	private InputLine input;
	private OutputLine output;
	private RobotArm robotArm;
	
	private JFrame frame;
	private Timer timer;
	
	public final static int SPEED = 4;
	public final int WIDTH = 900, HEIGHT = 400;
	public final int TREADHEIGHT = 300;
	
	public ProductionLine(boolean noGraphics) {
		input = new InputLine(TREADHEIGHT);
		output = new OutputLine(TREADHEIGHT);
		robotArm = new RobotArm(WIDTH / 2, HEIGHT / 20, input, output);
		
		if(!noGraphics) {
			frame = new JFrame("ProductionLine");
			
			frame.setSize(WIDTH, HEIGHT);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			
			Container content = frame.getContentPane();
			content.setBackground(Color.WHITE);
			content.add(this);
			
			timer = new Timer(1, this);
			timer.start();
		}
	}
	
	public void addDisk(Disk d) {
		input.add(d);
	}
	
	//For use with the non-graphical implementation
	public void unloadRobot() {
		Tower newTower = new Tower();
		//System.out.print("Robot" + robotArm);
		while(!robotArm.getTower().empty()) {
			newTower.push(robotArm.getTower().pop());
		}
		output.add(newTower);
		//System.out.println(" NewTower" + newTower);
	}
	
	//For Use with the non-graphical implementation
	public void process() {
		while(!input.isEmpty()) {
			if(robotArm.getTower().empty() || robotArm.getTower().compareTop(input.peek()) <= 0) {
				robotArm.getTower().push(input.remove());
			} else {
				unloadRobot();
			}
		}
	}
	
	public Tower removeTower() {
		return output.remove();
	}
	
	public boolean outputIsEmpty() {
		return output.isEmpty();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//System.out.println(input);
		input.draw(g);
		output.draw(g);
		robotArm.draw(g);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		input.update();
		output.update();
		robotArm.update();
		repaint();
	}
	
}
