import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.NoninvertibleTransformException;

/**
 * Represents a robot arm that selects and sorts Disks.
 * Contains an arm, a Tower, and the doors for the Tower, as well as references to the InputLine and OutputLine to be modified/sorted.
 * Will switch between a series of phases in order to carry out a series of steps necessary to sort the Disks.
 * Can create a graphical representation of this RobotArm, which includes the arm, any Disk that the arm is currently carrying,
 * the Tower, and the box with doors around the Tower.
 * @author Rory McGuire
 */
public class RobotArm {
	private Tower tower;
	
	private InputLine in;
	private OutputLine out;
	
	/*represents each of the phases that this RobotArm can be in:
	 * WAIT = awaiting next phase
	 * REMOVE = removing Disk from input
	 * PLACE_INPUT = placing Disk from input onto the Stack
	 * CLOSE = rotating the Stack (closing the doors)
	 * OPEN = opening the doors
	 * GRAB_STACK = grabbing the rotated Stack
	 * PLACE_STACK = placing the rotated Stack onto the output
	 * RETURN = returning to original position
	 * DONE = completely done
	 */
	private enum Phases {WAIT, REMOVE, PLACE_INPUT, CLOSE, OPEN, GRAB_STACK, PLACE_STACK, RETURN, DONE};
	private Phases phase; //the current phase
	
	
	private final Point pos; //the constant center position
	private final Point stackPos; //the constant position of this RobotArm's stack of Disks
	private Point currentPos;
	private Point targetPos;
	
	private int doorProgress; //shows the amount the stack doors have until closed
	private Disk loadingDisk;
	
	public final int MAX_STACK = 10; //the most Disks this is willing to include in a single stack
	public final int SPEED = ProductionLine.SPEED;
	
	/**
	 * Constructor for instantiating an object of type RobotArm.
	 * Instantiates all fields relative to the parameters sent to this constructor.
	 * @param x the x value of the RobotArm's origin position
	 * @param y the y value of the RobotArm's origin position
	 * @param input the InputLine to be used by the RobotArm
	 * @param output the OutputLine to be used by the RobotArm
	 */
	public RobotArm(int x, int y, InputLine input, OutputLine output) {
		tower = new Tower();
		
		pos = new Point(x, y);
		currentPos = new Point(pos.x, pos.y + 30);
		targetPos = new Point(currentPos);
		stackPos = new Point(pos.x, pos.y + MAX_STACK * Disk.HEIGHT + 120);
		
		in = input;
		out = output;
		
		phase = Phases.WAIT;
		loadingDisk = null;
	}
	
	/**
	 * Draws this RobotArm. Draws tower, the doors surrounding it, the arm, and the Disk the arm is holding.
	 * @param g the Graphics object to be drawn upon
	 */
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
		if(phase == Phases.OPEN || phase == Phases.GRAB_STACK) {
			Graphics2D g2 = (Graphics2D)g;
			g2.translate(stackPos.x, stackPos.y - tower.getHeight());
			g2.rotate(Math.PI);
			tower.draw(0, 0, g);
			try {
				g2.transform(g2.getTransform().createInverse());
			} catch(NoninvertibleTransformException e) {
				e.printStackTrace();
			}
		} else if(phase == Phases.PLACE_STACK) {
			Graphics2D g2 = (Graphics2D)g;
			g2.translate(currentPos.x, currentPos.y);
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
		g.fillRoundRect(pos.x - 350, pos.y - 10, 700, 10, 10, 10);
		g.fillPolygon(new int[] {currentPos.x + 15, currentPos.x - 15, currentPos.x - 10, currentPos.x + 10}, new int[] {pos.y, pos.y, pos.y + 15, pos.y + 15}, 4);
		
		g.fillRect(currentPos.x - 10, pos.y + 15, 20, currentPos.y - pos.y - 30);
		g.fillPolygon(new int[] {currentPos.x + 10, currentPos.x - 10, currentPos.x - 15, currentPos.x + 15}, new int[] {currentPos.y - 15, currentPos.y - 15, currentPos.y, currentPos.y}, 4);
		
		if(loadingDisk != null) {
			//Draw the loadingDisk
			loadingDisk.draw(currentPos.x - loadingDisk.getRadius(), currentPos.y, g);
		}
	}
	
	/**
	 * Updates the positions of this - to be called before each repaint(). Moves this a step toward completion of its task to sort InputLine in into OutputLine out.
	 * Depending upon the current phase that this is in, will move the arm or remove/add Stacks/Disks to in, out, or to tower as necessary.
	 */
	public void update() {
		switch(phase) {
			case WAIT:
				//if(in.getCompleted() && out.getCompleted()) {
					processOneDisk();
				//}
				break;
			case REMOVE: //removing the disk
				if(!moveXFirst()) {
					loadingDisk = in.remove();
					targetPos.setLocation(stackPos.x, stackPos.y - tower.getHeight() - Disk.HEIGHT);
					phase = Phases.PLACE_INPUT;
				}
				break;
			case PLACE_INPUT: //placing it on the stack
				if(!moveYFirst()) {
					tower.push(loadingDisk);
					loadingDisk = null;
					phase = Phases.WAIT;
				}
				break;
			case CLOSE: //rotating the stack/closing the doors
				int doorWidth = (int)(MAX_STACK * Disk.HEIGHT * 0.81); //the width of one door (of two)
				doorWidth *= 1.6; //deliberately longer than the actual doors in order to have a brief period when the doors appear to stay closed
				if(moveYFirst()) {
					//if the arm is moving
				} else if (doorProgress != doorWidth && Math.abs(doorProgress - doorWidth) <= SPEED) {
					doorProgress = doorWidth;
				} else if(doorProgress < doorWidth) {
					doorProgress += SPEED;
				} else {
					phase = Phases.OPEN;
					out.next(tower.getRadius());
				}
				break;
			case OPEN: //opening the doors
				if(doorProgress != 0 && Math.abs(doorProgress - 0) <= SPEED) {
					doorProgress = 0;
				} else if(doorProgress > 0) {
					doorProgress -= SPEED;
				} else {
					phase = Phases.GRAB_STACK;
					targetPos.setLocation(stackPos.x, stackPos.y - tower.getHeight());
				}
				break;
			case GRAB_STACK: //grabbing the newly rotated stack
				if(!moveYFirst()) {
					phase = Phases.PLACE_STACK;
					targetPos.setLocation(out.getX() + tower.getRadius() + AssemblyLine.BUFFER, out.getY() - tower.getHeight());
				}
				break;
			case PLACE_STACK: //placing the stack onto the output
				if(!moveXFirst()) {
					//move our tower into the outputline
					Tower tempTower = new Tower();
					while(!tower.empty()) {
						tempTower.push(tower.pop());
					}
					out.add(tempTower);
					
					targetPos.setLocation(pos.x, pos.y + 30);
					phase = Phases.RETURN;
				}
				break;
			case RETURN: //returning to original position
				if(!moveYFirst()) {
					phase = Phases.WAIT;
				}
				break;
			default:
				//When the Arm is finished...
				
		}
	}
	
	/**
	 * Moves currentPos one step closer to targetPos, with priority to the x direction.
	 * @return whether this was able to move (if it has not already reached targetPos)
	 */
	private boolean moveXFirst() {
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

	/**
	 * Moves currentPos one step closer to targetPos, with priority to the y direction.
	 * @return whether this was able to move (if it has not already reached targetPos)
	 */
	private boolean moveYFirst() {
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
	
	/**
	 * Decides which phase to begin next, then sets the variables necessary to begin that phase.
	 * Will determine whether to unload the next Disk, to unload tower, or to mark the progress as complete. To be called when phase == Phases.WAIT
	 */
	public void processOneDisk() {
		if(!in.isEmpty() && (tower.empty() || (tower.compareTop(in.peek()) <= 0 && tower.size() < MAX_STACK))) {
			//robotArm.push(input.remove());
			phase = Phases.REMOVE;
			targetPos.setLocation(in.getX() - in.getFirst().getRadius() - AssemblyLine.BUFFER, in.getY() - Disk.HEIGHT);
			//addDisk(in.remove());
		} else if(!tower.empty()){
			//unloadRobot();
			phase = Phases.CLOSE;
			targetPos.setLocation(stackPos.x, stackPos.y - (int)(MAX_STACK * Disk.HEIGHT * 1.3));
		} else {
			//Once the Disks have been processed completely:
			phase = Phases.DONE;
		}
	}
	
	/**
	 * Will check and return whether the current phase is Phases.Done.
	 * @return whether the current phase is Phases.DONE (whether this is complete)
	 */
	public boolean completed() {
		return phase == Phases.DONE;
	}
	
	/**
	 * Getter for this.Tower
	 * @return Tower
	 */
	public Tower getTower() {
		return tower;
	}
}
