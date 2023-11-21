/**
 * @author Rory McGuire
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class RobotArm extends Tower {
	private InputLine in;
	private OutputLine out;
	
	private int phase = 0;
	//0 = awaiting next phase
	//1 = removing Disk from input
	//2 = placing Disk from input onto the Stack
	//3 = rotating the Stack
	//4 = placing the rotated Stack onto the output
	//5 = returning to original position
	//6 = completely done
	
	private final Point pos; //the constant center position
	private final Point stackPos; //the constant position of this RobotArm's stack of Disks
	private Point currentPos;
	private Point targetPos;
	
	private Disk loadingDisk;
	
	public final int SPEED = 2;
	
	public RobotArm(int x, int y, InputLine input, OutputLine output) {
		super();
		
		pos = new Point(x, y);
		currentPos = new Point(pos.x, pos.y + 30);
		targetPos = new Point(currentPos);
		stackPos = new Point(pos.x, pos.y + 100);
		
		in = input;
		out = output;
		
		phase = 0;
		loadingDisk = null;
	}
	
	public void draw(Graphics g) {
		//Draw the Hand
		g.setColor(Color.BLACK);
		g.fillRect(pos.x - 350, pos.y - 10, 700, 10);
		g.fillPolygon(new int[] {currentPos.x + 15, currentPos.x - 15, currentPos.x - 10, currentPos.x + 10}, new int[] {pos.y, pos.y, pos.y + 15, pos.y + 15}, 4);
		
		g.fillRect(currentPos.x - 10, pos.y + 15, 20, currentPos.y - pos.y - 30);
		g.fillPolygon(new int[] {currentPos.x + 10, currentPos.x - 10, currentPos.x - 15, currentPos.x + 15}, new int[] {currentPos.y - 15, currentPos.y - 15, currentPos.y, currentPos.y}, 4);
		
		if(loadingDisk != null) {
			//Draw the loadingDisk
			loadingDisk.draw(currentPos.x - loadingDisk.getRadius(), currentPos.y, g);
		}
		
		//Draw the Pile
		draw(stackPos.x, stackPos.y, g);
	}
	
	public void update() {
		switch(phase) {
			case 0:
				if(in.getCompleted() && out.getCompleted()) {
					processOneDisk();
				}
				break;
			case 1: //removing the disk
				if(!moveXFirst()) {
					loadingDisk = in.remove();
					targetPos.setLocation(stackPos.x, stackPos.y - super.getHeight());
					phase = 2;
				}
				break;
			case 2: //placing it on the stack
				if(!moveYFirst()) {
					push(loadingDisk);
					loadingDisk = null;
					phase = 0;
				}
				break;
			case 3: //rotating the stack
				if(!moveYFirst()) {
					
				}
				break;
			case 4: //placing the stack onto the output
				if(!moveXFirst()) {
					
				}
				break;
			case 5: //returning to original position
				if(!moveYFirst()) {
					phase = 6;
				}
				break;
			case 6:
				
		}
	}
			
	//move with priority to x direction
	//returns true if it was able to move, false otherwise
	public boolean moveXFirst() {
		if(targetPos.x != currentPos.x && Math.abs(targetPos.x - currentPos.x) <= SPEED) {
			currentPos.x = targetPos.x;
		} else if(targetPos.x > currentPos.x) {
			currentPos.x += SPEED;
		} else if(targetPos.x < currentPos.x) {
			currentPos.x -= SPEED;
		} else if(targetPos.y != currentPos.y && Math.abs(targetPos.y - currentPos.y) <= SPEED) {
			currentPos.y = targetPos.y;
		} else if(targetPos.y > currentPos.y) {
			currentPos.y += SPEED;
		} else if(targetPos.y < currentPos.y) {
			currentPos.y -= SPEED;
		} else {
			return false;
		}
		return true;
	}
	
	//move with priority to y direction
	//returns true if it was able to move, false otherwise
	public boolean moveYFirst() {
		if(targetPos.y != currentPos.y && Math.abs(targetPos.y - currentPos.y) <= SPEED) {
			currentPos.y = targetPos.y;
		} else if(targetPos.y > currentPos.y) {
			currentPos.y += SPEED;
		} else if(targetPos.y < currentPos.y) {
			currentPos.y -= SPEED;
		} else if(targetPos.x != currentPos.x && Math.abs(targetPos.x - currentPos.x) <= SPEED) {
			currentPos.x = targetPos.x;
		} else if(targetPos.x > currentPos.x) {
			currentPos.x += SPEED;
		} else if(targetPos.x < currentPos.x) {
			currentPos.x -= SPEED;
		} else {
			return false;
		}
		return true;
	}
	
	public void processOneDisk() {
		if(!in.isEmpty()) {
			if(empty() || compareTop(in.peek()) <= 0) {
				//robotArm.push(input.remove());
				System.out.println("H");
				phase = 1;
				targetPos.setLocation(in.getX() - in.getFirst().getRadius() - in.BUFFER, in.getY() - Disk.HEIGHT);
				//addDisk(in.remove());
			} else {
				//unloadRobot();
				phase = 3;
				targetPos.setLocation(stackPos.x, stackPos.y - 70);
			}
		} else {
			//Once the Disks have been processed completely:
			
		}
	}
	
	public boolean getCompleted() {
		return phase == 6;
	}
}
