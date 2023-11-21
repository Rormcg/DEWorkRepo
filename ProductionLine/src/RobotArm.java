/**
 * @author Rory McGuire
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.NoninvertibleTransformException;

public class RobotArm {
	private Tower tower;
	
	private InputLine in;
	private OutputLine out;
	
	private int phase = 0;
	//0 = awaiting next phase
	//1 = removing Disk from input
	//2 = placing Disk from input onto the Stack
	//3 = rotating the Stack (closing the doors)
	//4 = opening the doors
	//5 = grabbing the rotated Stack
	//6 = placing the rotated Stack onto the output
	//7 = returning to original position
	//8 = completely done
	
	private final Point pos; //the constant center position
	private final Point stackPos; //the constant position of this RobotArm's stack of Disks
	private Point currentPos;
	private Point targetPos;
	
	private int doorProgress; //shows the amount the stack doors have until closed
	private Disk loadingDisk;
	
	public final int MAX_STACK = 10; //the most Disks this is willing to include in a single stack
	public final int SPEED = ProductionLine.SPEED;
	
	public RobotArm(int x, int y, InputLine input, OutputLine output) {
		tower = new Tower();
		
		pos = new Point(x, y);
		currentPos = new Point(pos.x, pos.y + 30);
		targetPos = new Point(currentPos);
		stackPos = new Point(pos.x, pos.y + MAX_STACK * Disk.HEIGHT + 120);
		
		in = input;
		out = output;
		
		phase = 0;
		loadingDisk = null;
	}
	
	public void draw(Graphics g) {
		//Draw the Doors
		g.setColor(Color.BLACK);
		int rad = (int)(MAX_STACK * Disk.HEIGHT * 0.9);
		g.fillRect(stackPos.x - rad, (int)(stackPos.y - rad * 1.4), rad * 2, rad * 2);
		g.setColor(Color.WHITE);
		int rad2 = (int)(rad * 0.9);
		g.fillRect(stackPos.x - rad2, (int)(stackPos.y - rad * 1.4 + (rad - rad2)), rad2 * 2, rad2 * 2);
		
		g.setColor(Color.BLACK);
		g.fillRect(stackPos.x - rad2, (int)(stackPos.y - rad * 1.4 + (rad - rad2)), doorProgress, rad2 * 2);//left door
		g.fillRect(stackPos.x + rad2 - doorProgress, (int)(stackPos.y - rad * 1.4 + (rad - rad2)), doorProgress, rad2 * 2);//right door
		
		g.fillRect((int)(stackPos.x - rad2), stackPos.y, rad2 * 2, 4);//shelf
		//g.fillRect((int)(stackPos.x - rad2*0.9), (int)(stackPos.y - rad2), (int)(rad2 * 1.9), 4);
		
		//Draw the Stack
		if(phase == 4 || phase == 5) {
			Graphics2D g2 = (Graphics2D)g;
			g2.translate(stackPos.x, stackPos.y - tower.getHeight());
			g2.rotate(Math.PI);
			tower.draw(0, 0, g);
			try {
				g2.transform(g2.getTransform().createInverse());
			} catch(NoninvertibleTransformException e) {
				e.printStackTrace();
			}
		} else if(phase == 6) {
			Graphics2D g2 = (Graphics2D)g;
			g2.translate(currentPos.x, currentPos.y - tower.getHeight());
			g2.rotate(Math.PI);
			tower.draw(0, 0, g);
			try {
				g2.transform(g2.getTransform().createInverse());
			} catch(NoninvertibleTransformException e) {
				e.printStackTrace();
			}
		} else if(!tower.empty()) {
			tower.draw(stackPos.x, stackPos.y, g);
		}
		
		//maximum stack:
		//g.fillRect(stackPos.x - 30, stackPos.y - 100, 60, 100);
		
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
	}
	
	public void update() {
		switch(phase) {
			case 0:
				//if(in.getCompleted() && out.getCompleted()) {
					processOneDisk();
				//}
				break;
			case 1: //removing the disk
				if(!moveXFirst()) {
					loadingDisk = in.remove();
					targetPos.setLocation(stackPos.x, stackPos.y - tower.getHeight() - Disk.HEIGHT);
					phase ++;
				}
				break;
			case 2: //placing it on the stack
				if(!moveYFirst()) {
					tower.push(loadingDisk);
					loadingDisk = null;
					phase = 0;
				}
				break;
			case 3: //rotating the stack/closing the doors
				if(moveYFirst()) {
					//if the arm is moving
				} else if (doorProgress != (int)(MAX_STACK * Disk.HEIGHT * 0.81) && Math.abs(doorProgress - (int)(MAX_STACK * Disk.HEIGHT * 0.81)) <= SPEED) {
					doorProgress = (int)(MAX_STACK * Disk.HEIGHT * 0.81);
				} else if(doorProgress < (int)(MAX_STACK * Disk.HEIGHT * 0.81)) {
					doorProgress += SPEED;
				} else {
					phase ++;
					out.next(tower.getRadius());
				}
				break;
			case 4: //opening the doors
				if(doorProgress != 0 && Math.abs(doorProgress - 0) <= SPEED) {
					doorProgress = 0;
				} else if(doorProgress > 0) {
					doorProgress -= SPEED;
				} else {
					phase ++;
					targetPos.setLocation(stackPos.x, stackPos.y - tower.getHeight());
				}
				break;
			case 5: //grabbing the newly rotated stack
				if(!moveYFirst()) {
					phase ++;
					targetPos.setLocation(out.getX() + in.getLast().getRadius()*2 + out.BUFFER, out.getY() - tower.getHeight() );//- Disk.HEIGHT);
				}
				break;
			case 6: //placing the stack onto the output
				if(!moveXFirst()) {
					//move our tower into the outputline
					Tower tempTower = new Tower();
					while(!tower.empty()) {
						tempTower.push(tower.pop());
					}
					out.add(tempTower);
					
					targetPos.setLocation(pos.x, pos.y + 30);
					phase ++;
				}
				break;
			case 7: //returning to original position
				if(!moveYFirst()) {
					phase = 0;
				}
				break;
			case 8:
				
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
		if(!in.isEmpty() && tower.empty() || (tower.compareTop(in.peek()) <= 0 && tower.size() < MAX_STACK)) {
			//robotArm.push(input.remove());
			System.out.println("H");
			phase = 1;
			targetPos.setLocation(in.getX() - in.getFirst().getRadius() - in.BUFFER, in.getY() - Disk.HEIGHT);
			//addDisk(in.remove());
		} else if(!tower.empty()){
			//unloadRobot();
			phase = 3;
			targetPos.setLocation(stackPos.x, stackPos.y - (int)(MAX_STACK * Disk.HEIGHT * 1.3));
		} else {
			//Once the Disks have been processed completely:
			phase = 8;
		}
	}
	
	public boolean completed() {
		return phase == 8;
	}
	
	public Tower getTower() {
		return tower;
	}
}
