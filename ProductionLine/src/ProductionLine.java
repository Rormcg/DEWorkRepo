/**
 * @author Rory McGuire
 */


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
	private Tower robotArm;
	
	private JComponent comp;
	private JFrame frame;
	private Container content;
	private Timer timer;
	
	public final int WIDTH = 500, HEIGHT = 400;
	public final int TREADHEIGHT = 250;
	
	public ProductionLine() {
		input = new InputLine(TREADHEIGHT);
		output = new OutputLine(TREADHEIGHT);
		robotArm = new Tower();
		
		frame = new JFrame("ProductionLine");
		content = frame.getContentPane();
		content.add(this);
		
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		timer = new Timer(1, this);
		timer.start();
	}
	
	public void addDisk(Disk d) {
		input.add(d);
	}
	
	public void unloadRobot() {
		Tower newTower = new Tower();
		//System.out.print("Robot" + robotArm);
		while(!robotArm.empty()) {
			newTower.push(robotArm.pop());
		}
		output.add(newTower);
		//System.out.println(" NewTower" + newTower);
	}
	
	public void process() {
		while(!input.isEmpty()) {
			if(robotArm.empty() || robotArm.compareTop(input.peek()) <= 0) {
				robotArm.push(input.remove());
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
		input.draw(g);
		output.draw(g);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		input.update();
		output.update();
		repaint();
		
	}
	
}
