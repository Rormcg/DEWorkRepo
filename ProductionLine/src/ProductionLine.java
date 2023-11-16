/**
 * @author Rory McGuire
 */

import java.util.LinkedList;
import java.util.Queue;

public class ProductionLine {
	Queue<Disk> input;
	Queue<Tower> output;
	Tower robotArm;
	
	public ProductionLine() {
		input = new LinkedList<Disk>();
		output = new LinkedList<Tower>();
		robotArm = new Tower();
	}
	
	public void addDisk(Disk d) {
		input.add(d);
	}
	
	public void unloadRobot() {
		Tower newTower = new Tower();
		
		while(!robotArm.empty()) {
			newTower.push(robotArm.pop());
		}
		output.add(newTower);
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
	
}
