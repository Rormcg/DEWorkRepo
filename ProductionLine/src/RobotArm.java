/**
 * @author Rory McGuire
 */

import java.awt.Graphics;

public class RobotArm extends Tower {
	private boolean completed;
	private boolean unloading;
	
	private Disk loadingDisk;
	
	public RobotArm() {
		super();
		
		completed = true;
		unloading = false;
		loadingDisk = null;
	}
	
	public void draw(Graphics g) {
		
	}
	
	public void update() {
		if(!completed) {
			if(unloading) {
				
			} else {
				
			}
		}
	}
	
	public boolean getCompleted() {
		return completed;
	}

	public void beginUnloading() {
		unloading = true;
		completed = false;
	}

	public void addDisk(Disk d) {
		loadingDisk = d;
		completed = false;
	}
}
